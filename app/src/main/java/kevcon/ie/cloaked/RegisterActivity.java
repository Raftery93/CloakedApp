package kevcon.ie.cloaked;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * * <h1>RegisterActivity</h1>
 * RegisterActivity is used to register a new user using Firebase Authentication.
 *
 * @author Conor Raftery
 * @Since 22/02/2018
 */
public class RegisterActivity extends AppCompatActivity {

    //Variables
    private FirebaseAuth mAuth;
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mPasswordView;
    private Button btnSignUp;

    /**
     *<h2>onCreate</h2>
     *onCreate is used to serve the layout, along with taking in the user entered details,
     * and sending them to Firebase.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        //Get Firebase authentication instance
        mAuth = FirebaseAuth.getInstance();

        //Get layout instances
        btnSignUp = findViewById(R.id.submit_registration);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);

        //Add a listener for when the register button is clicked
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Convert user entered details into a workable string
                String email = mEmailView.getText().toString().trim();
                String password = mPasswordView.getText().toString().trim();

                //If statements for error checking, with prompts to the user
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user via Firebase authentication
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                Toast.makeText(RegisterActivity.this, "Success! Please log in.", Toast.LENGTH_LONG).show();

                                if (!task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });



    }



}
