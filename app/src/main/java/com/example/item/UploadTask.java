package com.example.item;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class UploadTask extends AsyncTask<String, Void, StringBuilder> {

    private Activity mActivity;

    public UploadTask(Activity activity) {
        mActivity = activity;
    }


    // 非同期処理
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected StringBuilder doInBackground(String... params) {

        // 使用するサーバーのURLに合わせる
        String urlSt = "http://192.168.1.9/textPost.php";

        HttpURLConnection httpConn;

        String word = "itemsName=" + params[0] + "&price=" + params[1];
        StringBuilder sb = new StringBuilder();

        try {
            // URL設定
            URL url = new URL(urlSt);

            // HttpURLConnection
            httpConn = (HttpURLConnection) url.openConnection();

            // request POST
            httpConn.setRequestMethod("POST");

            // no Redirects
            httpConn.setInstanceFollowRedirects(false);

            // データを書き込む
            httpConn.setDoOutput(true);

            // 時間制限
            httpConn.setReadTimeout(10000);
            httpConn.setConnectTimeout(20000);

            // 接続
            httpConn.connect();

            try (// POSTデータ送信処理
                 OutputStream outStream = httpConn.getOutputStream()) {
                outStream.write(word.getBytes(StandardCharsets.UTF_8));
                outStream.flush();
                Log.d("debug", "flush");

                // データを受け取る
                InputStream is = httpConn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null)
                    sb.append(line);
                is.close();
            } catch (IOException e) {}
        }
        catch (IOException e){}
        return sb;
    }
    // 非同期処理が終了後、結果をメインスレッドに返す
    public void onPostExecute(StringBuilder result){

        // 戻り値をViewにセット
        TextView textView = mActivity.findViewById(R.id.textView);
        textView.setTextColor(Color.WHITE);

        textView.setBackgroundColor(Color.GREEN);
        textView.setTextSize(16);
        textView.setHeight(20);
        textView.setText(result);

    }

}