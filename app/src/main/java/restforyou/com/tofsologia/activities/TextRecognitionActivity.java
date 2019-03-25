package restforyou.com.tofsologia.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.R;

import static restforyou.com.tofsologia.utils.Constants.IMAGE_URL;

public class TextRecognitionActivity extends AppCompatActivity {

    @BindView(R.id.et_recognized_text)
    EditText editTextRecognized;
    @BindView(R.id.iv_image_for_recognition)
    ImageView imageViewForRecognition;

    ImageButton buttonReadyActivity;

//    @BindView(R.id.go_image_for_recognition)
//    GraphicOverlay imageForRecognition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        buttonReadyActivity = findViewById(R.id.btn_text_ready);

        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();

        Intent receivedIntent = getIntent();
        //if (receivedIntent.getAction() == RECEIVED_IMAGE && receivedIntent.getAction() != null ){
            String imageUriString = receivedIntent.getStringExtra(IMAGE_URL);
            Uri imageUri = Uri.parse(imageUriString);

            logIt("action " + receivedIntent.getAction()+ " url " + receivedIntent.getStringExtra(IMAGE_URL));

            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageViewForRecognition.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        //}
    }

    private void setInitialUiElements(){

    }

    private void setListeners(){
//        buttonShowExample.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               showExample();
//            }
//        });
        buttonReadyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReadyActivity();
            }
        });
    }

    private void showExample(){
        Intent toExampleIntent = new Intent(TextRecognitionActivity.this, WorkingExampleActivity.class);
        startActivity(toExampleIntent);
    }

    private void showReadyActivity(){
        Intent toReadyActivityIntent = new Intent(TextRecognitionActivity.this, TextReadyActivity.class);
        startActivity(toReadyActivityIntent);
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }

}
