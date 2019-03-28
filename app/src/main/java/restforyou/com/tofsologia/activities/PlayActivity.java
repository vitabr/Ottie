package restforyou.com.tofsologia.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.utils.Constants;
import restforyou.com.tofsologia.utils.photo.PhotoUtils;

public class PlayActivity extends AppCompatActivity implements Constants {

    private String[] letters = {"a","b","c"};
    private String[] words = {"mommy", "daddy", "child", "world"};
    private File capturedPhotoFile = null;
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        String mode = getIntent().getStringExtra(MODE);
        if(mode.equals(MODE_LETTER)){
            //todo
        }else{
            //todo
        }

    }

    public void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go

            try {
                capturedPhotoFile = PhotoUtils.createImageFile(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (capturedPhotoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "restforyou.com.tofsologia.fileprovider",
                        capturedPhotoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {

            case REQUEST_CAPTURE_IMAGE:
                if(resultCode == RESULT_OK){
                    final Uri imageUri = Uri.fromFile(capturedPhotoFile);
                    showPositiveView();
                }else if (resultCode == RESULT_CANCELED || data == null){
                    Toast.makeText(PlayActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }

                break;
        }


    }

    private void showPositiveView(){
        findViewById(R.id.view_positive).setVisibility(View.VISIBLE);
    }

    private void showNegativeView(){

        findViewById(R.id.view_negative).setVisibility(View.VISIBLE);
    }

    public void hidePositiveView(View v){
        findViewById(R.id.view_positive).setVisibility(View.GONE);
    }

    public void hideNegativeView(View v){
        findViewById(R.id.view_negative).setVisibility(View.GONE);
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }
}
