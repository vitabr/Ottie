package restforyou.com.tofsologia.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import restforyou.com.tofsologia.MLKit;
import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.utils.Constants;
import restforyou.com.tofsologia.utils.audimanager.AudioManager;
import restforyou.com.tofsologia.utils.audimanager.IAudioManager;
import restforyou.com.tofsologia.utils.photo.PhotoUtils;

public class PlayActivity extends AppCompatActivity implements Constants {

    private String[] letters = {"a","b","c"};
    private String[] words = {"child", "daddy", "mommy", "child", "world"};
    private File capturedPhotoFile = null;
    private int index = 0;
    private String mode;
    private IAudioManager audioManager = new AudioManager(this);
    private Bitmap mBitmapForRecognition;
    private String foundTexts = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mode = getIntent().getStringExtra(MODE);

        if(mode.equals(MODE_LETTER)){
            audioManager.playWelcomeLetter(letters[index]);
        }else{
            audioManager.playWelcomeWord(words[index]);

        }

    }

    public void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

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
                    foundTexts = "";
                    if(mode.equals(MODE_LETTER)) {
                        showPositiveView();
                    }else{
                        handleImage(imageUri);
                    }
                }else if (resultCode == RESULT_CANCELED || data == null){
                    Toast.makeText(PlayActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    private void handleImage(Uri imageUri){


        final InputStream imageStream;
        try {
            imageStream = getContentResolver().openInputStream(imageUri);
            mBitmapForRecognition = BitmapFactory.decodeStream(imageStream);
            mBitmapForRecognition = mBitmapForRecognition.copy(mBitmapForRecognition.getConfig(), true);

            Bitmap rotatedBitmap = rotateImage(mBitmapForRecognition, 90);

            mBitmapForRecognition = rotatedBitmap;
            recognize(mBitmapForRecognition,0.2f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recognize(final Bitmap bitmap, final float scale){
        final Bitmap newBitmap = scaleImage(mBitmapForRecognition, scale);
        MLKit.recognize(newBitmap, new MLKit.OnRecognizeListener() {
            @Override
            public void onSuccess(FirebaseVisionText texts) {
                Log.e("xxx", "recognize text on image with scale:" +scale);
                if(scale < 2 && scale != -1) {
                    foundTexts += texts.getText();
                    final float newScale = scale + 0.2f;
                    recognize(newBitmap, newScale);
                }else{// if(scale != -1){
                    //recognize(newBitmap, -1);
                    processTextRecognitionResult(texts);
                }
            }

            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private  Bitmap scaleImage(Bitmap source, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(source, 0, 0, (int)(Math.ceil(source.getWidth())), (int)(Math.ceil(source.getHeight())),
                matrix, true);
    }

    private void processTextRecognitionResult(FirebaseVisionText texts){
        String foundText = foundTexts.toLowerCase(); //texts.getText();
        String expectedText = (mode.equals(MODE_LETTER))? letters[index] : words[index];
        expectedText = expectedText.toLowerCase();
        logIt("expected:" + expectedText + ", found:" + foundText);
        if(foundText.contains(expectedText)){
            showPositiveView();
        }else{
            showNegativeView();
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
    public static void playAssetSound(Context context, String soundFileName) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();

            AssetFileDescriptor descriptor = context.getAssets().openFd(soundFileName);
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }
}
