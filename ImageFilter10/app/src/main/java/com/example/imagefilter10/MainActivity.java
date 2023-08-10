package com.example.imagefilter10;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public static final Uri image = null;
    Uri selectedImageUri;

    Button SelectImage, Submit;
    ImageView PreviewImage;
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SelectImage = findViewById(R.id.SelectImage);
        PreviewImage = findViewById(R.id.PreviewImage);
        Submit = findViewById(R.id.submit);

        Submit.setOnClickListener(a->submit());
        SelectImage.setOnClickListener(b -> imageChooser());
    }
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    PreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

    public void submit(){
        if (null != selectedImageUri) {
            Intent intent2 = new Intent(this, FilterImage.class);
            intent2.putExtra("Image", selectedImageUri.toString());
            startActivity(intent2);
        }
    }
}
