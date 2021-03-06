package restforyou.com.tofsologia.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import restforyou.com.tofsologia.R;

public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startRelevantActivity();

            }
        }, 1000);
    }

    private void startRelevantActivity(){
//        Intent toLoginActivityIntent = new Intent(SplashActivity.this, LoginActivity.class);
//        startActivity(toLoginActivityIntent);
        Intent toScanActivityIntent = new Intent(SplashActivity.this, ModesActivity.class);
        startActivity(toScanActivityIntent);
        finish();
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }
}
