package edu.fatecitaquera.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import edu.fatecitaquera.userapp.dao.UserDAO;
import edu.fatecitaquera.userapp.model.User;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout editTextEmail, editTextPassword;
    private UserDAO userDAO;
    private TextView errorMessage;
    private Button buttonLogin, buttonSingUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDAO = new UserDAO();
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonSingUp = findViewById(R.id.buttonSingUp);
        errorMessage = findViewById(R.id.errorMessage);
        editTextEmail = findViewById(R.id.userEmail);
        editTextPassword = findViewById(R.id.userPassword);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isEmailFilled = editTextEmail.getEditText().getText().toString().trim().isEmpty();
                boolean isPasswordFilled = editTextPassword.getEditText().getText().toString().trim().isEmpty();

                buttonLogin.setEnabled(!isEmailFilled && !isPasswordFilled);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        editTextEmail.getEditText().addTextChangedListener(textWatcher);
        editTextPassword.getEditText().addTextChangedListener(textWatcher);

        buttonLogin.setOnClickListener((event) -> {
            if (userDAO.login(editTextEmail.getEditText().getText().toString(), editTextPassword.getEditText().getText().toString())) {
                User user = userDAO.findByEmail(editTextEmail.getEditText().getText().toString());
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("userId", user.getId());
                startActivity(intent);
            } else {
                errorMessage.setText("E-mail ou senha informados estão incorretos");
            }
        });

        buttonSingUp.setOnClickListener((event) -> {
            startActivity(new Intent(this, AddUserActivity.class));
        });
    }
}