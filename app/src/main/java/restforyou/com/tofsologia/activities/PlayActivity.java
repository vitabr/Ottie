package restforyou.com.tofsologia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import restforyou.com.tofsologia.R;

public class PlayActivity extends AppCompatActivity {

    private String[] letters = {"a","b","c"};
    private String[] words = {"momy", "dady", "child", "world"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }


    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }
}
