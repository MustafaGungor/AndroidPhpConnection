package com.example.speakingjoy.android_php;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Listele extends ListActivity {

    ListView lv;

    @SuppressLint("ResourceAsColor")
    @Override
    /////////////////////////////////////////////////////////
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lv = (ListView) findViewById(R.id.listView);
        ornek2();
      }



    ////////// JSON //////////////////

    public void ornek2(){

        ArrayList list = new ArrayList();

        JSONArray array = null;
        String restWebServerResponse = getFromInternet1("http://www.mustafagungor.xyz/Android/listecek.php");
        try{
            array = new JSONArray(restWebServerResponse);
        }catch (JSONException e) {
            Log.e("Hata", e.toString());
        }

        for (int i = 0; i < array.length(); i++) {
            try {

                JSONObject jsonItem = array.getJSONObject(i);

                list.add(jsonItem.getString("ad")+" - "+jsonItem.getString("mail"));


                setListAdapter(new ArrayAdapter(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        list

                ));


            } catch (JSONException e) {
                Log.e("Hata", e.toString());
            }


        }


    }



    public String getFromInternet1(String url){
        String response_str = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        // Get the response
        ResponseHandler responseHandler = new BasicResponseHandler();
        try {
    response_str = (String) client.execute(request, responseHandler);
} catch (ClientProtocolException e) {
        e.printStackTrace();
        } catch (IOException e) {
        e.printStackTrace();
        }
        return response_str;
        }

    ////////// JSON ////////////////////

}