package restforyou.com.tofsologia.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.R;

import static restforyou.com.tofsologia.utils.Constants.IMAGE_URL;
import static restforyou.com.tofsologia.utils.Constants.RECEIVED_IMAGE;

public class TextRecognitionActivity extends AppCompatActivity {

    @BindView(R.id.et_recognized_text)
    EditText editTextRecognized;
    @BindView(R.id.iv_image_for_recognition)
    ImageView imageViewForRecognition;
//    @BindView(R.id.go_image_for_recognition)
//    GraphicOverlay imageForRecognition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();


        Intent receivedIntent = getIntent();
        if (receivedIntent.getAction() == RECEIVED_IMAGE && receivedIntent.getAction() != null ){
            //String imageUri = receivedIntent.getStringExtra(IMAGE_URL);

            Uri imageUri = Uri.parse(receivedIntent.getStringExtra(IMAGE_URL));

            //Bitmap selectedImage = BitmapFactory.decodeFile("file://" + receivedIntent.getStringExtra(IMAGE_URL).replace("content://", ""));
            //imageViewForRecognition.setImageBitmap(selectedImage);
            imageViewForRecognition.setImageURI(imageUri);

            logIt("action " + receivedIntent.getAction()+ " url " + receivedIntent.getStringExtra(IMAGE_URL));

            final InputStream imageStream;
           // try {
                //imageStream = getContentResolver().openInputStream(imageUri);
                //final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //imageViewForRecognition.setImageBitmap(selectedImage);

            //} catch (FileNotFoundException e) {
            //    e.printStackTrace();
            //}


        }
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
//        buttonReadyActivity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showReadyActivity();
//            }
//        });
    }

    private void showExample(){
        Intent toExampleIntent = new Intent(TextRecognitionActivity.this, WorkingExampleActivity.class);
        startActivity(toExampleIntent);
    }

    private void showReadyActivity(){
        Intent toReadyActivityIntent = new Intent(TextRecognitionActivity.this, ReadyTextActivity.class);
        startActivity(toReadyActivityIntent);
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }

}
