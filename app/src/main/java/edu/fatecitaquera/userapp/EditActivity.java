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

public class EditActivity extends AppCompatActivity {
    private TextInputLayout editTexId, editTextName, editTextEmail, editTextPassword;
    private Button buttonDelete, buttonUpadte;
    private UserDAO userDAO;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTexId = findViewById(R.id.userId);
        editTextName = findViewById(R.id.userName);
        editTextEmail = findViewById(R.id.userEmail);
        editTextPassword = findViewById(R.id.userPassword);
        buttonUpadte = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        userDAO = new UserDAO();

        String userId = getIntent().getStringExtra("userId");
        User user = userDAO.findById(userId);

        editTexId.getEditText().setText(user.getId());
        editTextName.getEditText().setText(user.getName());
        editTextEmail.getEditText().setText(user.getEmail());
        editTextPassword.getEditText().setText(user.getPassword());

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean isNameFilled = editTextName.getEditText().getText().toString().trim().isEmpty();
                boolean isEmailFilled = editTextEmail.getEditText().getText().toString().trim().isEmpty();
                boolean isPasswordFilled = editTextPassword.getEditText().getText().toString().trim().isEmpty();

                buttonUpadte.setEnabled(!isNameFilled && !isEmailFilled && !isPasswordFilled);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        editTextName.getEditText().addTextChangedListener(textWatcher);
        editTextEmail.getEditText().addTextChangedListener(textWatcher);
        editTextPassword.getEditText().addTextChangedListener(textWatcher);

        buttonDelete.setOnClickListener((event) -> {
            count++;
            if (count < 2) {
                Toast.makeText(getApplicationContext(), "Clique mais uma vez para deletar o usuário", Toast.LENGTH_LONG).show();
            } else {
                userDAO.deleteById(userId);
                Toast.makeText(getApplicationContext(), "Usuário deletado com sucesso! ID: " + userId, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("userId", userId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        buttonUpadte.setOnClickListener((event) -> {
            User userUpdated = new User();
            userUpdated.setName(editTextName.getEditText().getText().toString());
            userUpdated.setEmail(editTextEmail.getEditText().getText().toString());
            userUpdated.setPassword(editTextPassword.getEditText().getText().toString());

            userDAO.update(userId, userUpdated);
            Toast.makeText(getApplicationContext(), "Usuário atualizado com sucesso! ID: " + userId, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("userId", userId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}