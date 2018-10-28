package com.example.ricky.parsingdemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<PojoClass>list=new ArrayList<>();
    MyAdapter adapter;
    RecyclerView recyclerView;
    static final String TAG="MainActivity";
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recy);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        new Demo().execute();
    }
    public class Demo extends AsyncTask<String,String,String>{
        JSONObject mainObject;
        HttpURLConnection httpURLConnection;
        StringBuilder stringBuilder=new StringBuilder();
        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url=new URL("https://newsapi.org/v2/top-headlines?country=us&apiKey=afad277cfcc14c96a46dc8696de7f4db");
                httpURLConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String data;
                while ((data=bufferedReader.readLine())!=null){
                 stringBuilder.append(data);
                }
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String json=stringBuilder.toString();
            progressDoalog.dismiss();
            return json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDoalog = new ProgressDialog(MainActivity.this);
//            progressDoalog.setMax(100);
            progressDoalog.setMessage("Its loading....");
            progressDoalog.setTitle("ProgressDialog bar example");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                 mainObject=new JSONObject(s);
                JSONArray artical=mainObject.getJSONArray("articles");
                for (int i=0;i<artical.length();i++){
                    PojoClass pojoClass=new PojoClass();
                    String image=artical.getJSONObject(i).getString("urlToImage");
                    String author=artical.getJSONObject(i).getString("author");
                    JSONObject source=artical.getJSONObject(i).getJSONObject("source");
                    String name=source.getString("name");
                    Log.d(TAG, "onPostExecute: "+name);
                    pojoClass.setAuthor(author);
                    pojoClass.setName(name);
                    pojoClass.setImageToUrl(image);
                    list.add(pojoClass);
                }
                adapter=new MyAdapter(MainActivity.this,list);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
