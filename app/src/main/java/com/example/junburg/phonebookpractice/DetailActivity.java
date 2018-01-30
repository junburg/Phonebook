package com.example.junburg.phonebookpractice;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {
    private static final int GALLERY_CODE = 10;
    private ImageView selfie_img;
    private EditText name_edit, number_edit, descriptioin_edit;
    private Button save_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        selfie_img = (ImageView)findViewById(R.id.selfie);
        selfie_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUpPicture();
            }
        });

        name_edit = (EditText)findViewById(R.id.name_edit);
        number_edit = (EditText)findViewById(R.id.number_edit);
        descriptioin_edit = (EditText)findViewById(R.id.description_edit);
        save_btn = (Button)findViewById(R.id.save_btn);


    }

    private void pickUpPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }
}
