package com.example.item;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;

public class ListgetData extends AsyncTask<Void, Void, String > {

    private  Activity  activity;
    private  CustomList customList;
    private List<ViewItemParam> list;
    private Dialog dialog;


    public ListgetData(Activity activity,CustomList customList, List<ViewItemParam> list){
        super();
        this.activity = activity;
        this.customList = customList;
        this.list = list;
    }
    @Override
    protected void onPreExecute() {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.progress);
        dialog.setCancelable(false);
        dialog.setTitle("更新中");
        dialog.show();
    }

    @Override
    protected String doInBackground(Void ... v){

        String line;
        StringBuilder sb = new StringBuilder();//追加

        try{
            System.out.println("1");

            URL url = new URL("http://yukiabineko.sakura.ne.jp/items/viewJson.php");
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
            try {
                JSONArray jsonArray = new JSONArray(data);
                for(int i=0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id =   jsonObject.getString("id");
                    String path = jsonObject.getString("path");
                    String name = jsonObject.getString("name");
                    String price = jsonObject.getString("price");
                    String memo = jsonObject.getString("info");


                    ViewItemParam param = new ViewItemParam();
                    param.setId(Integer.parseInt(id));
                    param.setBitmap(path);
                    param.setName(name);
                    param.setPrice(price);
                    param.setMemo(memo);
                    list.add(param);

                    customList.notifyDataSetChanged();
                }
                if(jsonArray.length() == 0 || jsonArray==null){
                    customList.clear();
                    customList.notifyDataSetChanged();
                    activity.findViewById(R.id.item_list_area).setVisibility(View.GONE);
                    activity.findViewById(R.id.not_list_item).setVisibility(View.VISIBLE);
                    activity.findViewById(R.id.not_list_item_button).setVisibility(View.VISIBLE);
                }
                else {
                    activity.findViewById(R.id.item_list_area).setVisibility(View.VISIBLE);
                    activity.findViewById(R.id.not_list_item).setVisibility(View.GONE);
                    activity.findViewById(R.id.not_list_item_button).setVisibility(View.GONE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
           activity.findViewById(R.id.table_header).setVisibility(View.INVISIBLE);
        }
        dialog.dismiss();
    }
}


