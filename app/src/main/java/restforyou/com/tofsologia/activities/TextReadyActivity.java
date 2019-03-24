package restforyou.com.tofsologia.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.model.Record;

public class TextReadyActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView content;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_text);
        getRecord();
        initViews();

    }

    private void getRecord() {
        record = new Record(-1, "fineName", "Recognized text", "photo.png", "text.txt");
    }

    private void initViews() {
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);

        content = findViewById(R.id.tv_content);
        content.setText(record.getDescription());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                returnToMainActivity();
                break;
            case R.id.btn_edit:
                setEditable();
                break;
            case R.id.btn_save: {
                saveRecord();
            }
            break;
        }

    }

    private void saveRecord() {
    }

    private void setEditable() {

        content.setFocusable(true);
        content.setCursorVisible(true);
        content.setClickable(true);
        content.setFocusableInTouchMode(true);
        content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                System.out.println(v);
                return true;
            }
        });

    }

    private void returnToMainActivity() {

    }

    private void logIt(String message) {
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }
}
