package edu.fatecitaquera.userapp;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private LinearLayout userListView;
    private Button buttonQRCodeRead;

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

        userListView = findViewById(R.id.userListView);
        buttonQRCodeRead = findViewById(R.id.buttonQRCodeRead);

        userDAO = new UserDAO();
        String userId = getIntent().getStringExtra("userId");
        User user = userDAO.findById(userId);

        buttonQRCodeRead.setOnClickListener((event) -> {
            Intent intent = new Intent(this, QRCodeActivity.class);
            intent.putExtra("userId", user.getId());
            startActivity(intent);
        });

        LayoutInflater inflater = LayoutInflater.from(this);

        if (user != null) {
            View userView = inflater.inflate(R.layout.user_info, userListView, false);

            TextView id = userView.findViewById(R.id.user_id);
            TextView name = userView.findViewById(R.id.user_name);
            TextView email = userView.findViewById(R.id.user_email);
            TextView password = userView.findViewById(R.id.user_password);
            Button buttonEdit = userView.findViewById(R.id.buttonEdit);

            buttonEdit.setOnClickListener((event) -> {
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra("userId", user.getId());
                startActivity(intent);
            });

            id.setText("ID: " + user.getId());
            name.setText("Nome: " + user.getName());
            email.setText("E-mail: " + user.getEmail());
            password.setText("Senha: " + user.getPassword());

            userListView.addView(userView);
        } else {
            View userView = inflater.inflate(R.layout.message_error, userListView, false);
            TextView message = userView.findViewById(R.id.message);
            message.setText("Não há conexão com a internet");
            buttonQRCodeRead.setEnabled(false);
            userListView.addView(userView);
        }
    }
}