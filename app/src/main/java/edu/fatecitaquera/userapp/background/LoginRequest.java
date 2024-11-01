package edu.fatecitaquera.userapp.background;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import edu.fatecitaquera.userapp.util.ConnectionFactory;

public class LoginRequest extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL login = new URL("http://" + ConnectionFactory.serverIP + ":8080/usuarios/login");
            HttpURLConnection connection = (HttpURLConnection) login.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.println(strings[0]);
            connection.connect();

            if (connection.getResponseCode() == 200) return new Scanner(connection.getInputStream()).next();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
