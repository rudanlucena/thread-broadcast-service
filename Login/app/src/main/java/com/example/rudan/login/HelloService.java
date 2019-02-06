package com.example.rudan.login;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HelloService extends Service {

    private String email;
    private String password;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        new MyAsync().execute();
        return  super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MyAsync extends AsyncTask {

        Intent intent = new Intent();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Object doInBackground(Object[] objects) {

            final String url = "http://ag-ifpb-sgd-server.herokuapp.com/login";

            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("email", email)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

                intent.setAction("LOGAR");
            try {
                intent.putExtra("code", client.newCall(request).execute().code());

            } catch (IOException e) {
                e.printStackTrace();
            }

            /*for (int x=0; x<3; x++){
                publishProgress();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Toast.makeText(HelloService.this, "fui", Toast.LENGTH_SHORT).show();
            sendBroadcast(intent);
        }

    }
}
