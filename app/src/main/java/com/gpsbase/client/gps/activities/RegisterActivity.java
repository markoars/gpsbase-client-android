package com.gpsbase.client.gps.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpsbase.client.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.gpsbase.client.gps.models.User;

import java.util.HashMap;
import java.util.Map;

import static com.gpsbase.client.gps.utils.DatabaseHelper.POSITIONS_TEMP_TABLE;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister, btnBack;
    private EditText inputEmail, inputPassword, inputConfirmPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        auth = FirebaseAuth.getInstance();
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnBack = (Button) findViewById(R.id.btn_back);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        inputConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String emailInput = inputEmail.getText().toString().trim();
                final String codedEmail;
                final String password = inputPassword.getText().toString().trim();
                final String confirmPassword = inputConfirmPassword.getText().toString().trim();


                if (TextUtils.isEmpty(emailInput)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
                {
                    Toast.makeText(getApplicationContext(), "Enter a valid email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password and confirm password don`t match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                codedEmail = emailInput.replace(".", "__dot__");

                DatabaseReference invitedUserRef = FirebaseDatabase.getInstance().getReference("InvitedUsers/" + codedEmail);

                invitedUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final User invitedUser = dataSnapshot.getValue(User.class);
                        Boolean foundMatch = false;

                        if(invitedUser != null)
                        {
                            foundMatch = true;
                        }

                        if(foundMatch)
                        {
                            //create user
                            auth.createUserWithEmailAndPassword(emailInput, password)
                                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            DatabaseReference companyWorkerDetailsRef = FirebaseDatabase.getInstance().getReference("CompanyWorkersDetails/Company/" + invitedUser.companyUID + "/Workers/" + codedEmail);

                                            progressBar.setVisibility(View.GONE);

                                            if (!task.isSuccessful()) {
                                                Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                                        Toast.LENGTH_SHORT).show();
                                            } else {

                                                Toast.makeText(RegisterActivity.this, "Congradulations, you`ve sucessfully registered", Toast.LENGTH_SHORT).show();
                                                FirebaseUser fbUser = task.getResult().getUser();
                                                final String userUID = fbUser.getUid();

                                                // 1) Create a new worker with the user Key
                                                companyWorkerDetailsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("");
                                                        final User preCretedUser = dataSnapshot.getValue(User.class);
                                                        preCretedUser.userUID = userUID;
                                                        preCretedUser.status = "active";

                                                        Map multiPathInsertMap = new HashMap();

                                                        // 2) Create a new worker & user with the user Key
                                                        multiPathInsertMap.put("CompanyWorkers/Company/" + invitedUser.companyUID + "/Workers/" + userUID, preCretedUser);
                                                        multiPathInsertMap.put("CompanyWorkersDetails/Company/" + invitedUser.companyUID + "/Workers/" + userUID, preCretedUser);
                                                        multiPathInsertMap.put("Users/" + userUID, preCretedUser);

                                                        rootRef.updateChildren(multiPathInsertMap, new DatabaseReference.CompletionListener() {
                                                            public void onComplete(DatabaseError error, DatabaseReference ref) {
                                                                if(error == null) {
                                                                    // Remove temporary one (invited)
                                                                    FirebaseDatabase.getInstance().getReference("CompanyWorkers/Company/" + invitedUser.companyUID + "/Workers/" + codedEmail).removeValue();
                                                                    FirebaseDatabase.getInstance().getReference("CompanyWorkersDetails/Company/" + invitedUser.companyUID + "/Workers/" + codedEmail).removeValue();
                                                                    FirebaseDatabase.getInstance().getReference("InvitedUsers/" + codedEmail).removeValue();
                                                                }
                                                                else {
                                                                    System.out.println("Firebase. Error = " + error);
                                                                }
                                                            }
                                                        });

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                finish();
                                            }
                                        }
                                    });
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "You need to be invited by your company admin", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}