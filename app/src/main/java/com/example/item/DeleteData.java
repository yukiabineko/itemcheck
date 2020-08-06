package com.example.item;


import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class DeleteData extends AsyncTask<String, Void, String> {


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... strings) {

        String urlSt = "http://yukiabineko.sakura.ne.jp/items/deletePost.php";
        HttpURLConnection httpConn;
        String line;

        String word = "id=" + strings[0];
        StringBuilder sb = new StringBuilder();
        try{
            URL url = new URL(urlSt);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setInstanceFollowRedirects(false);
            httpConn.setDoOutput(true);
            httpConn.setReadTimeout(10000);
            httpConn.setConnectTimeout(20000);
            httpConn.connect();
            try
                (OutputStream outStream = httpConn.getOutputStream()){
                    outStream.write(word.getBytes(StandardCharsets.UTF_8));
                    outStream.flush();

                InputStreamReader in = new InputStreamReader(httpConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(in);

                System.out.println("3");

                while((line = br.readLine()) != null){
                    System.out.println(line);
                    if(sb.length() > 0) sb.append('\n');//追加
                    sb.append(line);//追加
                }

                br.close();
                in.close();
                httpConn.disconnect();
            }
            catch (Exception e){}
        }
        catch (Exception e){}

        return sb.toString();
    }
    public void onPostExecute(String result){

    }
}
