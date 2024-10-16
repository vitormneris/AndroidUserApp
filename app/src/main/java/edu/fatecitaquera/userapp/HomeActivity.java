package edu.fatecitaquera.userapp;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.fatecitaquera.userapp.dao.UserDAO;
import edu.fatecitaquera.userapp.model.User;

public class HomeActivity extends AppCompatActivity {
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userDAO = new UserDAO();
        String userId = getIntent().getStringExtra("userId");
        User user = userDAO.findById(userId);

        TextView id = findViewById(R.id.user_id);
        TextView name = findViewById(R.id.user_name);
        TextView email = findViewById(R.id.user_email);
        TextView password = findViewById(R.id.user_password);
        Button buttonEdit = findViewById(R.id.buttonEdit);
        Button buttonQRCodeRead = findViewById(R.id.buttonQRCodeRead);

        buttonEdit.setOnClickListener((event) -> {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("userId", user.getId());
            startActivity(intent);
        });

        buttonQRCodeRead.setOnClickListener((event) -> {
            Intent intent = new Intent(this, ScannerActivity.class);
            intent.putExtra("userId", user.getId());
            startActivity(intent);
        });

        id.setText("ID: " + user.getId());
        name.setText("Nome: " + user.getName());
        email.setText("E-mail: " + user.getEmail());
        password.setText("Senha: " + user.getPassword());
    }
}