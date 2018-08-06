package com.example.duongtainhan555.asyntaskreadjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnReadJson;
    private ListView lv;
    ArrayList<String> arrayCourse;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Anh Xa
        Init();
        //Event
        btnReadJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ReadJson().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo4.json");
            }
        });
    }
    private void Init()
    {
        lv = findViewById(R.id.lv);
        arrayCourse = new ArrayList<>();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayCourse);
        lv.setAdapter(adapter);
        btnReadJson = findViewById(R.id.btnReadJson);
    }
    class ReadJson extends AsyncTask<String,Void,String>
    {


        @Override
        protected String doInBackground(String... strings) {
            return docNoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String khoahoc = jsonObject.getString("khoahoc");
                    String hocphi = jsonObject.getString("hocphi");
                    arrayCourse.add(khoahoc+" - "+hocphi);
                }
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);
        }
    }
    private String docNoiDung_Tu_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try    {
            // create a url object
            URL url = new URL(theUrl);

            // create a url connection object
            URLConnection urlConnection = url.openConnection();

            // wrap the url connection in a buffered reader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the url connection via the buffered reader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
