package com.example.junburg.phonebookpractice;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class DetailActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 10; // 갤러리 호출에 사용할 인텐트 상수
    private ImageView selfie_img;
    private EditText name_edit, number_edit, descriptioin_edit;
    private Button save_btn;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        selfie_img = (ImageView) findViewById(R.id.selfie);

        // 이미지 뷰를 클릭했을 때 갤러리를 띄우는 인텐트 처리
        selfie_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickUpPicture();
            }
        });

        name_edit = (EditText) findViewById(R.id.name_edit);
        number_edit = (EditText) findViewById(R.id.number_edit);
        descriptioin_edit = (EditText) findViewById(R.id.description_edit);
        save_btn = (Button) findViewById(R.id.save_btn);

        // Save 버튼을 눌렀을 때 다이얼로그를 띄우고 리스트 액티비티로 값 반환
        save_btn.setOnClickListener(new View.OnClickListener() {
            // 리스트 액티비티에서 보낸 인텐트를 가져옴
            Intent intent = getIntent();

            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DetailActivity.this);
                alertBuilder.setTitle("주소록 추가");
                alertBuilder.setMessage("주소록에 추가하시겠습니까?");

                // 확인을 클릭했을 때 처리할 코드
                alertBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 사용자 이름과 전화번호를 무조건 입력하게 하기 위함
                        if (name_edit.getText().length() > 0 && number_edit.getText().length() > 0) {
                            // 사용자가 사진을 등록하지 않으면 기본 이미지의 Uri를 가져옴
                            if (uri == null) getDrawableUri();

                            // 인텐트에 각 뷰의 값 반환
                            intent.putExtra("name", name_edit.getText().toString());
                            intent.putExtra("number", number_edit.getText().toString());
                            intent.putExtra("description", descriptioin_edit.getText().toString());
                            intent.putExtra("uri", uri.toString());
                            setResult(RESULT_OK, intent); // 인텐트 결과 set OK
                            finish(); // 액티비티 종료 함수

                        } else {
                            Toast.makeText(DetailActivity.this, "입력을 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                // 취소를 눌렀을 때 처리할 코드
                alertBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(DetailActivity.this, "입력을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_CANCELED, intent); // 인텐트 결과 set CANCELED
                        finish(); // 액티비티 종료 함수
                    }
                });

                AlertDialog alertDialog = alertBuilder.create(); // 설정한 다이얼로그를 생성
                alertDialog.show(); // 생성한 다이얼로그를 보여줌
            }
        });
    }

    /*
    갤러리를 실행 후 값을 받아오기 위해 정의된 함수
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            // 받아온 이미지 Uri
            uri = data.getData();
            // 이미지 처리 라이브러리 Glide를 사용
            Glide.with(this)
                    .load(uri)
                    // 사진을 동그랗게 변환함
                    .bitmapTransform(new CropCircleTransformation(this))
                    // 이미지 효과
                    .crossFade()
                    .into(selfie_img);
        }
    }

    /*
    갤러리를 불러와서 이미지 가지고 오는 인텐트를 정의한 함수
     */
    private void pickUpPicture() {
        // 데이터에서 하나(URI)를 선택하여 정보를 반환하라는 액션
        Intent intent = new Intent(Intent.ACTION_PICK);

        /*
        setType(): 명시적인 MIME 데이터 형식을 설정, 반환할 데이터의 타입을 나타내기 위해 사용
        MediaStore: 안드로이드 시스템에서 제공하는 미디어 데이터 DB
        시스템은 파일 시스템에 저장되어 있는 미디어 파일들을 이 DB에 추가하여, 여러 앱에서 이용할 수 있도록 함
        MediaStore.Images: 사용가능한 모든 이미지 파일의 Meta data를 포함하고 있다
        MediaStore.Images.Media.CONTENT_TYPE: 이미지 디렉토리 MIME TYPE
        MIME Type에 대한 참고자료http://www.gnujava.com/board/article_view.jsp?article_no=2731&board_no=3&table_cd=EPAR01&table_no=01
        */
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        // 이미지 데이를 반환하도록 설정
        intent.setType("image/*");

        /*
        public void startActivityForResult(Intent intent, int requestCode)
        호출당하는 액티비티로부터 데이터를 넘겨받기 위해 사용
        intent - 인텐트
        requestCode - 이 액티비티를 호출하는 액티비티가 여러 개가 있을 경우, 어떤 액티비티가 호출했는지를 알기 위해 사용
        */
        startActivityForResult(intent, GALLERY_CODE);
    }

    /*
     drawable 디렉토리 파일의 Uri를 얻기 위한 함수
     */
    private Uri getDrawableUri() {

        Uri drawableUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://"
                + getResources().getResourcePackageName(R.drawable.ic_face_black_24dp)
                + '/'
                + getResources().getResourceTypeName(R.drawable.ic_face_black_24dp)
                + '/'
                + getResources().getResourceEntryName(R.drawable.ic_face_black_24dp));

        return drawableUri;
    }

}

