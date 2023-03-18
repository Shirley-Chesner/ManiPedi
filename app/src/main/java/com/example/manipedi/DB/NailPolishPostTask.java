package com.example.manipedi.DB;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.Toast;

import com.example.manipedi.DB.room.ManiPediApplication;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NailPolishPostTask extends AsyncTask<NailPolish, NailPolish, NailPolish> {
    private String url;
    private Listener<NailPolish> listener;

    public NailPolishPostTask(String url, Listener<NailPolish> listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(NailPolish nailPolish) {
        listener.onComplete(nailPolish);
//        progressDialog.dismiss();
    }

    @Override
    protected NailPolish doInBackground(NailPolish... voids) {
        try {
            URL nailPolishApiUrl;
            HttpsURLConnection myConnection = null;

            try {
                nailPolishApiUrl = new URL(url);
                myConnection = (HttpsURLConnection) nailPolishApiUrl.openConnection();
                myConnection.setRequestProperty("Accept", "application/json");

                if (myConnection.getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    return NailPolishParser.parseJson(jsonReader);
                } else {
                    Toast.makeText(ManiPediApplication.getMyContext(), "Fetch request failed " + myConnection.getResponseCode(), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Toast.makeText(ManiPediApplication.getMyContext(), "Fetch request failed " + e , Toast.LENGTH_SHORT).show();
            }
            finally {
                if (myConnection != null) myConnection.disconnect();
            }
        } catch (Exception e) {
            Toast.makeText(ManiPediApplication.getMyContext(), "Fetch request failed " + e , Toast.LENGTH_SHORT).show();
        }

        return null;
    }

}
