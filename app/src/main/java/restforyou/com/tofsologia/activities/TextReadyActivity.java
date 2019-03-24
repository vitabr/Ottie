package restforyou.com.tofsologia.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import restforyou.com.tofsologia.R;

public class TextReadyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_text);

    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }
}
