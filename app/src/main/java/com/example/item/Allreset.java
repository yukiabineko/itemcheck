package com.example.item;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class Allreset extends AsyncTask<Void, Void, String > {

    private  Activity  activity;

    public Allreset(Activity activity){
        super();
        this.activity = activity;

    }

    @Override
    protected String doInBackground(Void ... v){

        String line;
        StringBuilder sb = new StringBuilder();//追加
        try{
            System.out.println("1");

            URL url = new URL("http://yukiabineko.sakura.ne.jp/items/reset.php");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            System.out.println("2");

            InputStreamReader in = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(in);

            System.out.println("3");

            while((line = br.readLine()) != null){
                System.out.println(line);
                if(sb.length() > 0) sb.append('\n');//追加
                sb.append(line);//追加
            }

            br.close();
            in.close();
            http.disconnect();

        }catch(Exception e){
            System.out.println(e);
        }
        return sb.toString();//変更
    }

    protected void onPostExecute(String data){
        super.onPostExecute(data);
        Toast.makeText(activity,data,Toast.LENGTH_LONG).show();

    }
}


