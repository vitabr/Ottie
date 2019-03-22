package restforyou.com.tofsologia.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.GraphicOverlay;
import restforyou.com.tofsologia.R;

import static restforyou.com.tofsologia.utils.Constants.IMAGE_URL;
import static restforyou.com.tofsologia.utils.Constants.RECEIVED_IMAGE;

public class TextRecognitionActivity extends AppCompatActivity {
//    @BindView(R.id.btn_to_ready_text_activity)
//    Button buttonReadyActivity;
//    @BindView(R.id.go_image_for_recognition)
//    GraphicOverlay ImageForRecognition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();


        Intent receivedIntent = getIntent();
        if (receivedIntent.getAction() == RECEIVED_IMAGE && receivedIntent.getAction() != null ){
            receivedIntent.getStringExtra(IMAGE_URL);
            logIt("action " + receivedIntent.getAction()+ " url " + receivedIntent.getStringExtra(IMAGE_URL));
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
