package edu.fatecitaquera.userapp.background;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import edu.fatecitaquera.userapp.util.ConnectionFactory;

public class FindByEmailRequest extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {

        StringBuilder apiResponse = new StringBuilder();
        try {
            URL findByEmail = new URL("http://" + ConnectionFactory.serverIP + ":8080/usuarios/" + strings[0] + "/encontrarporemail");
            HttpURLConnection connection = (HttpURLConnection) findByEmail.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(15000);
            connection.connect();

            Scanner scanner = new Scanner(findByEmail.openStream());
            while (scanner.hasNext()) {
                apiResponse.append(scanner.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiResponse.toString();
    }
}
