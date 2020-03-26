package com.example.mynote.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.mynote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Signup extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText mE_Mail, mPassword;
    Button mLogIn, mSignUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        instview();
    }


    private void instview() {
        mE_Mail = findViewById(R.id.e_mail);
        mPassword = findViewById(R.id.password);
        mLogIn = findViewById(R.id.login);
        mSignUp = findViewById(R.id.signUp);
        mSignUp.setOnClickListener(this);
        mLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.signUp:
                if (validate()) {
                    Rigester();
                }

                break;
            case R.id.login:
                startActivity(new Intent(Signup.this,LogIn.class));
                finish();
                break;
        }
    }


    private void Rigester() {
        mAuth.createUserWithEmailAndPassword(mE_Mail.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(Signup.this,MainActivity.class);
                            final String USER_ID  = FirebaseAuth.getInstance().getUid();
                            intent.putExtra("NOD_NAME",USER_ID);
                            startActivity(intent);


                        } else {
                            // If sign in fails, display a message to the user.

                            Snackbar.make(findViewById(android.R.id.content), "Sign Up failed", Snackbar.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });

    }

    private boolean validate() {


        if (mE_Mail.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your E-Mail", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (mPassword.getText().toString().isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), "Please enter your Password", Snackbar.LENGTH_LONG).show();
            return false;
        } else {

            return true;

        }
    }
}
