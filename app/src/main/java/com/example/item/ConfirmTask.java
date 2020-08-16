package com.example.item;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConfirmTask extends AsyncTask<String, Void, StringBuilder> {

    private Activity mActivity;

    public ConfirmTask(Activity activity) {
        mActivity = activity;
    }

    // 非同期処理
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected StringBuilder doInBackground(String... params) {

        // 使用するサーバーのURLに合わせる
        String urlSt = "http://yukiabineko.sakura.ne.jp/items/confirmChange.php";

        HttpURLConnection httpConn;

        String word = "id=" + params[0] + "&confirm=" + params[1];
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

        if(result.toString().equals("発注数を確定しました。")){
            /*mActivity.findViewById(R.id.reuest_send).setVisibility(View.GONE);
            mActivity.findViewById(R.id.reuest_edit).setVisibility(View.VISIBLE);
            confirm.setGravity(Gravity.CENTER);
            confirm.setTextColor(Color.WHITE);
            confirm.setBackgroundColor(Color.parseColor("#6200EE"));*/
            Toast.makeText(mActivity, result.toString(),Toast.LENGTH_LONG).show();
        }
        else if(result.toString().equals("発注数をリセットしました。")){
          /*  mActivity.findViewById(R.id.reuest_send).setVisibility(View.VISIBLE);
            mActivity.findViewById(R.id.reuest_edit).setVisibility(View.GONE);
            confirm.setText("未確定");
            confirm.setGravity(Gravity.CENTER);
            confirm.setTextColor(Color.WHITE);
            confirm.setBackgroundColor(Color.RED);*/
            Toast.makeText(mActivity, result.toString(),Toast.LENGTH_LONG).show();
        }
    }

}