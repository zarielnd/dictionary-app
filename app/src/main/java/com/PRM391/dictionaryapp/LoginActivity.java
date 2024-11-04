    package com.PRM391.dictionaryapp;

    import android.content.Intent;
    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;

    public class LoginActivity extends AppCompatActivity {
        private FirebaseAuth mAuth;
        private EditText emailField, passwordField;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();

            // Initialize views
            emailField = findViewById(R.id.email);
            passwordField = findViewById(R.id.password);
            Button loginButton = findViewById(R.id.loginButton);
            Button registerButton = findViewById(R.id.registerButton);

            loginButton.setOnClickListener(v -> signIn());

            registerButton.setOnClickListener(v -> {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            });
        }

        private void signIn() {
            String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Navigate to main activity
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // Sign in failed
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }