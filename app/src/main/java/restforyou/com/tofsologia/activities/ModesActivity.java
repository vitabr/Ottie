package restforyou.com.tofsologia.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.R;

public class ModesActivity extends AppCompatActivity {

    @BindView(R.id.btn_simple_mode)
    Button buttonSimpleMode;
    @BindView(R.id.btn_middle_mode)
    Button buttonMiddleMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modes);
        ButterKnife.bind(this);

        buttonSimpleMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             toPlayActivity();
            }
        });

        buttonMiddleMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPlayActivity();
            }
        });

    }

    private void toPlayActivity(){
        Intent toPlayActivity = new Intent(ModesActivity.this, PlayActivity.class);
        startActivity(toPlayActivity);
    }
}
