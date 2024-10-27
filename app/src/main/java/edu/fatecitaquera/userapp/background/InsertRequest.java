package edu.fatecitaquera.userapp.background;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.fatecitaquera.userapp.util.ConnectionFactory;

public class InsertRequest extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            URL insert = new URL("http://" + ConnectionFactory.serverIP + ":8080/usuarios/inserir");
            HttpURLConnection connection = (HttpURLConnection) insert.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.println(strings[0]);
            connection.connect();

            if (connection.getResponseCode() == 201) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}