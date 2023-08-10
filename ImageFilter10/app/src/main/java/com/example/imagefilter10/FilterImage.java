package com.example.imagefilter10;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;


public class FilterImage extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();
        String imgpath = intent.getStringExtra("Image");
        Uri img = Uri.parse(imgpath);
        ImageView image = findViewById(R.id.ImageView);

        Log.d("errimg", String.valueOf(img));
        image.setImageURI(img);

        Button clr = findViewById(R.id.color);
        clr.setOnClickListener(a -> color_change(img));
    }

    public void color_change(Uri image) {
        sendReq("http://127.0.0.1:5000",image,1,0);
    }

    public void sendReq(String url, Uri image,int option, int value) {
        HttpURLConnection conn = null;
        DataOutputStream os = null;

        ByteBuffer img = StandardCharsets. UTF_8. encode(image.toString());
        try {

            URL uri = new URL(url);
            conn = (HttpURLConnection) uri.openConnection();
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            os = new DataOutputStream(conn.getOutputStream());
            os.write(option);
            os.flush();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }finally
        {
            if(conn != null)
            {
                conn.disconnect();
            }
        }
    }
}