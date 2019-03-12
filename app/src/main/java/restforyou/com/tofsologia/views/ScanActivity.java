package restforyou.com.tofsologia.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.R;

public class ScanActivity extends AppCompatActivity {
    @BindView(R.id.btn_scan_picture)
    Button buttonScanPicture;
    @BindView(R.id.btn_make_picture)
    Button buttonMakePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();
    }


    private void setInitialUiElements(){

    }

    private void setListeners(){
        buttonMakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTextRecognitionActivity();
            }
        });

        buttonScanPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showTextRecognitionActivity();
            }
        });
    }

    private void showTextRecognitionActivity(){
        Intent toTextRecognitionActivity = new Intent(ScanActivity.this, TextRecognitionActivity.class);
        startActivity(toTextRecognitionActivity);
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }
}
