package edu.fatecitaquera.userapp.background;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import edu.fatecitaquera.userapp.util.ConnectionFactory;

public class DeleteRequest extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL delete = new URL("http://" + ConnectionFactory.serverIP + ":8080/usuarios/" + strings[0] + "/deletar");
            HttpURLConnection connection = (HttpURLConnection) delete.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(15000);
            connection.connect();

            connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}