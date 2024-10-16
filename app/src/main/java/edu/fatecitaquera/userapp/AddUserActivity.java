package edu.fatecitaquera.userapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

import edu.fatecitaquera.userapp.dao.UserDAO;
import edu.fatecitaquera.userapp.model.User;

public class AddUserActivity extends AppCompatActivity {
    private TextInputLayout editTextName, editTextEmail, editTextPassword;
    private UserDAO userDAO;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDAO = new UserDAO();
        button = findViewById(R.id.buttonSave);

        editTextName = findViewById(R.id.userName);
        editTextEmail = findViewById(R.id.userEmail);
        editTextPassword = findViewById(R.id.userPassword);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isNameFilled = editTextName.getEditText().getText().toString().trim().isEmpty();
                boolean isEmailFilled = editTextEmail.getEditText().getText().toString().trim().isEmpty();
                boolean isPasswordFilled = editTextPassword.getEditText().getText().toString().trim().isEmpty();

                button.setEnabled(!isNameFilled && !isEmailFilled && !isPasswordFilled);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        editTextName.getEditText().addTextChangedListener(textWatcher);
        editTextEmail.getEditText().addTextChangedListener(textWatcher);
        editTextPassword.getEditText().addTextChangedListener(textWatcher);

        button.setOnClickListener((event) -> {
            User user = new User();
            user.setName(editTextName.getEditText().getText().toString());
            user.setEmail(editTextEmail.getEditText().getText().toString());
            user.setPassword(editTextPassword.getEditText().getText().toString());

            String id = userDAO.insert(user);
            Toast.makeText(getApplicationContext(), "Usuário inserido com sucesso! ID: " + id, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}