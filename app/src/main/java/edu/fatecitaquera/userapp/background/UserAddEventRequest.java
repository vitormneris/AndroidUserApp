package edu.fatecitaquera.userapp.background;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import edu.fatecitaquera.userapp.util.ConnectionFactory;

public class UserAddEventRequest extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        try {
            URL userAddEvent = new URL("http://" + ConnectionFactory.serverIP + ":8080/eventos/usuarioid/" + strings[0] + "/eventoid/" + strings[1]);
            HttpURLConnection connection = (HttpURLConnection) userAddEvent.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(false);
            connection.setConnectTimeout(15000);
            connection.connect();

            Log.i("HTTP METHOD",  "" + connection.getResponseCode());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
