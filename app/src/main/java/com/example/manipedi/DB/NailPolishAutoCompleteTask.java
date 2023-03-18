package com.example.manipedi.DB;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.manipedi.DB.room.ManiPediApplication;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class NailPolishAutoCompleteTask extends AsyncTask<ArrayList<NailPolish>,ArrayList<NailPolish>,ArrayList<NailPolish>> {
    private ProgressDialog progressDialog;
    private AutoCompleteTextView nailPolish;

    public NailPolishAutoCompleteTask(AutoCompleteTextView nailPolish) {
        this.nailPolish = nailPolish;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        progressDialog = new ProgressDialog(ManiPediApplication.getMyContext());
//        progressDialog.setMessage("processing results");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<NailPolish> arr) {
//        progressDialog.dismiss();

        ArrayAdapter<NailPolish> nailAdapter = new ArrayAdapter(ManiPediApplication.getMyContext(), android.R.layout.select_dialog_item, arr);
        nailPolish.setThreshold(1);
        nailPolish.setAdapter(nailAdapter);
    }

    @Override
    protected ArrayList<NailPolish> doInBackground(ArrayList<NailPolish>... voids) {
        try {
            URL nailPolishApiUrl;
            HttpsURLConnection myConnection = null;

            try {
                nailPolishApiUrl = new URL("https://makeup-api.herokuapp.com/api/v1/products.json?product_type=nail_polish");
                myConnection = (HttpsURLConnection) nailPolishApiUrl.openConnection();
                myConnection.setRequestProperty("Accept", "application/json");

                if (myConnection.getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    return NailPolishParser.parseArray(jsonReader);
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

        return new ArrayList();
    }
}
