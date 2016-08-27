package com.example.speakingjoy.android_php;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.Policy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import  java.net.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @SuppressLint({"ShowToast", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) { //onCreate blogu başlangıcı
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tview = (TextView) findViewById(R.id.txtBildiriBaslik);
        tview.setText("Şikayetinizi Bildirin!");
        final TextView tsonuc=(TextView)findViewById(R.id.txtSonuc);
        final EditText ad = (EditText) findViewById(R.id.etisim);
        final EditText mail = (EditText) findViewById(R.id.etmail);
        final EditText msj_baslik=(EditText)findViewById(R.id.etbaslik);
        final EditText msj=(EditText)findViewById(R.id.etİcerik);
        Button aktion = (Button) findViewById(R.id.button);
        Button listele=(Button)findViewById(R.id.btnListe);
        listele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Listele.class);
                startActivity(i);
            }
        });
        aktion.setOnClickListener(new View.OnClickListener()//butonun tıklanması kısmı
        {
            public void onClick(View v) {
                try {// try catch blogu mutlaka olmak zorunda
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet request = new HttpGet();

                    String query1 = URLEncoder.encode(ad.getText().toString(), "utf-8");// utf-8 yaparak get verisinin boşluklarını%20 olmasını ve programın hata vermesini engelledik
                    String query2 = URLEncoder.encode(msj.getText().toString(), "utf-8");
                    String query3 = URLEncoder.encode(mail.getText().toString(),"utf-8");
                    String query4 = URLEncoder.encode(msj_baslik.getText().toString(),"utf-8");
                    tsonuc.setText(query1 + " # " + query2 +" # "+query3+" # "+query4);

                    request.setURI(new URI("http://mustafagungor.xyz/Android/android.php?type=add&ad=" + query1 + "&msj=" + query2+"&mail="+query3+"&baslik="+query4)); //Get isteğimizi URL yoluyla belirliyoruz.
                    HttpResponse response = httpclient.execute(request); //İsteğimizi gerçekleştiriyoruz.
                    HttpEntity entity = response.getEntity(); //Gelen cevabı işliyoruz.

                    String result = null;
                    InputStream is = entity.getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8); //utf-8 burda önemli
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString(); //Artık result stringi php tarafından ekrana print yada echo komutlarıyla yazdırılmış veriyi içeriyor.
                    tsonuc.setText("Şikayet Numaranız : "+result+"Şikayetiniz hakkında detaylar mail adresine gönderilecektir."); //textviewe yazdık
                    Toast.makeText(getApplicationContext(), result+"Kaydınız Gerçekleşti", Toast.LENGTH_LONG).show();//toast mesajı olarakta gösterdik
                    ad.setText(""); //ad temizledik
                    msj.setText("");//mesaj temizledik
                    mail.setText("");
                    msj_baslik.setText("");
                } catch (Exception ex) {
                    tview.setText("Hata: " + ex.toString());
                }
            }
        });
    }
}
