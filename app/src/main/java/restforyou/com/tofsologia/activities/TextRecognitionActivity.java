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
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.App;
import restforyou.com.tofsologia.MLKit;
import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.model.DbManager;
import restforyou.com.tofsologia.model.Record;
import restforyou.com.tofsologia.views.GraphicOverlay;
import restforyou.com.tofsologia.views.TextGraphic;

import static restforyou.com.tofsologia.utils.Constants.CURRENT_RECORD_ID;
import static restforyou.com.tofsologia.utils.Constants.IMAGE_URL;
import static restforyou.com.tofsologia.utils.Constants.RECORD_EXIST;

public class TextRecognitionActivity extends AppCompatActivity {

    @BindView(R.id.et_recognized_text)
    EditText editTextRecognized;
    @BindView(R.id.iv_image_for_recognition)
    ImageView imageViewForRecognition;

    private Bitmap mBitmapForRecognition;
    private String mResultText;
    private String imageUriString;
    private Record mRecord;

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




       handleIntent();
    }

            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageViewForRecognition.setImageBitmap(selectedImage);

    private void handleIntent(){
        Intent receivedIntent = getIntent();
        imageUriString = receivedIntent.getStringExtra(IMAGE_URL);
        Uri imageUri = Uri.parse(imageUriString);


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

        // Draw text bitmap over original image bitmap
        Canvas resultCanvas = new Canvas(mBitmapForRecognition);
        resultCanvas.drawBitmap(mBitmapForRecognition,0,0, null);
        resultCanvas.drawBitmap(textBitmap, 0,0, null);

        mImageForRecognition.setImageBitmap(mBitmapForRecognition);
        mTextRecognized.setText(stringBuffer.toString());
        editTextRecognized.setText(stringBuffer.toString());
        logIt(stringBuffer.toString());
        createRecord(stringBuffer.toString());
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }

    private void addRecordToDb(Record record){
        DbManager.getInstance().addRecord(record, new DbManager.onRecordAdded() {
            @Override
            public void onRecordAdded() {
                //todo result returned to main thread??? already
                App.getAppMainExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (mRecord != null){
                            mTextReady.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    logIt("added to db ");
                                    Intent toTextReadyActivity = new Intent(TextRecognitionActivity.this, TextReadyActivity.class);
                                    toTextReadyActivity.setAction(RECORD_EXIST);
                                    toTextReadyActivity.putExtra(CURRENT_RECORD_ID, mRecord.getId());
                                    startActivity(toTextReadyActivity);
                                    finish();
                                }
                            });
                        } else {
                            Toast.makeText(TextRecognitionActivity.this, "Please wait", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }

    private void createRecord(String description){
        mRecord = new Record();
        int id = (int)System.currentTimeMillis()%100000000;
        mRecord.setId(id);
        mRecord.setDescription(description);
        //here we don't have file name yet and uri yet
        mRecord.setFileName("");
        mRecord.setPhotoURI(imageUriString);
        mRecord.setTextFileURI("");
        addRecordToDb(mRecord);
        logIt(" id" + mRecord.getId());
    }

}
