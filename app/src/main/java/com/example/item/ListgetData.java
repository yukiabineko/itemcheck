package com.example.item;

import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

public class ListgetData extends AsyncTask<Void, Void, String > {

    private  CustomList customList;
    private List<ViewItemParam> list;

    public ListgetData(CustomList customList, List<ViewItemParam> list){
        super();
        this.customList = customList;
        this.list = list;
    }

    @Override
    protected String doInBackground(Void ... v){

        String line;
        StringBuilder sb = new StringBuilder();//追加
        try{
            System.out.println("1");

            URL url = new URL("http://192.168.1.6/viewJson.php");
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

        if(data != null){
            customList.clear();
            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i=0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String path = jsonObject.getString("path");
                    String name = jsonObject.getString("name");
                    String price = jsonObject.getString("price");


                    ViewItemParam param = new ViewItemParam();
                    param.setBitmap(path);
                    param.setName(name);
                    param.setPrice(price);
                    list.add(param);

                    customList.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{

        }
    }
}


