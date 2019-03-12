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

public class TextRecognitionActivity extends AppCompatActivity {
    @BindView(R.id.btn_show_example)
    Button buttonShowExample;
    @BindView(R.id.btn_to_ready_text_activity)
    Button buttonReadyActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();
    }


    private void setInitialUiElements(){

    }

    private void setListeners(){
        buttonShowExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showExample();
            }
        });

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
        Intent toReadyActivityIntent = new Intent(TextRecognitionActivity.this, ReadyTextActivity.class);
        startActivity(toReadyActivityIntent);
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }

}
