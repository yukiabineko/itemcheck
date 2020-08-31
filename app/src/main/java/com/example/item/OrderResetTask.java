package com.example.item;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class OrderResetTask extends AsyncTask<Void, Void, String > {

    private Activity activity;


    public OrderResetTask(Activity activity){
        super();
        this.activity = activity;

    }

    @Override
    protected String doInBackground(Void ... v){

        String line;
        StringBuilder sb = new StringBuilder();//追加

        try{

            URL url = new URL("http://yukiabineko.sakura.ne.jp/items/orderAllReset.php");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();


            InputStreamReader in = new InputStreamReader(http.getInputStream(), "UTF-8");
            BufferedReader br = new BufferedReader(in);


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
        Toast.makeText(activity, "削除しました。", Toast.LENGTH_LONG).show();

    }
}


