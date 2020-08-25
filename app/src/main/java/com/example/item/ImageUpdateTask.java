package com.example.item;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;


import androidx.annotation.RequiresApi;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageUpdateTask extends AsyncTask<UpdateImageParams, Void, StringBuilder> {
    private  Activity activity;

    public  ImageUpdateTask(Activity activity){
        this.activity = activity;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    protected StringBuilder doInBackground(UpdateImageParams... params) {
        UpdateImageParams param = params[0];

        HttpURLConnection connection = null;
        StringBuilder sb = new StringBuilder();



        try {
            // 画像をjpeg形式でstreamに保存
            ByteArrayOutputStream jpg = new ByteArrayOutputStream();
            if(param !=null){
                param.bmp.compress(Bitmap.CompressFormat.JPEG, 100, jpg);
            }



            URL url = new URL(param.uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);//接続タイムアウトを設定する。
            connection.setReadTimeout(3000);//レスポンスデータ読み取りタイムアウトを設定する。
            connection.setRequestMethod("POST");//HTTPのメソッドをPOSTに設定する。
            //ヘッダーを設定する
            connection.setRequestProperty("User-Agent", "Android");
            connection.setRequestProperty("Content-Type","application/octet-stream");
            connection.setDoInput(true);//リクエストのボディ送信を許可する
            connection.setDoOutput(true);//レスポンスのボディ受信を許可する
            connection.setUseCaches(false);//キャッシュを使用しない
            connection.connect();

            // データを投げる
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            out.write(jpg.toByteArray());
            out.flush();



            // データを受け取る
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line);
            is.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }



    public void onPostExecute(StringBuilder string) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }


}
