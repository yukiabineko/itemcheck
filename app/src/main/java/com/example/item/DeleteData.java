package com.example.item;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
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


public class DeleteData extends AsyncTask<String, Void, StringBuilder> {

    private Activity mActivity;

    public DeleteData(Activity activity) {
        mActivity = activity;
    }


    // 非同期処理
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected StringBuilder doInBackground(String... params) {

        // 使用するサーバーのURLに合わせる
        String urlSt = "http://yukiabineko.sakura.ne.jp/items/deletePost.php";

        HttpURLConnection httpConn;

        String word = "id=" + params[0];
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

        Toast.makeText(mActivity, result.toString(),Toast.LENGTH_LONG).show();


    }

}