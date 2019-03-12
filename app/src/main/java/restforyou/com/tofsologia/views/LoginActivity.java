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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_jump_in)
    Button buttonJumpIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();
    }

    private void setInitialUiElements(){

    }

    private void setListeners(){
        buttonJumpIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showScanActivity();
            }
        });
    }

    private void showScanActivity(){
        Intent toScanActivityIntent = new Intent(LoginActivity.this, ScanActivity.class);
        startActivity(toScanActivityIntent);
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }
}
