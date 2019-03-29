package restforyou.com.tofsologia.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import restforyou.com.tofsologia.MLKit;
import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.utils.Constants;
import restforyou.com.tofsologia.utils.audimanager.AudioManager;
import restforyou.com.tofsologia.utils.audimanager.IAudioManager;
import restforyou.com.tofsologia.utils.photo.PhotoUtils;

public class PlayActivity extends AppCompatActivity implements Constants {

    private String[] letters = {"a", "b", "c"};
    private String[] words = {"child", "daddy", "mommy", "child", "world"};
    private File capturedPhotoFile = null;
    private int index = 0;
    private String mode;
    private AudioManager audioManager = new AudioManager(this);

    private Bitmap mBitmapForRecognition;
    private String foundTexts = "";
    private TextView container;
    private Button nextQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        container = findViewById(R.id.container);


        mode = getIntent().getStringExtra(MODE);
        if (mode.equals(MODE_LETTER)) {
            container.setText(letters[index].toUpperCase());
            audioManager.playWelcomeLetter(letters[index]);
        } else {
            container.setText(words[index]);
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
                if (resultCode == RESULT_OK) {
                    final Uri imageUri = Uri.fromFile(capturedPhotoFile);
                    foundTexts = "";
                    if (mode.equals(MODE_LETTER)) {
                        showPositiveView();
                    } else {
                        handleImage(imageUri);
                    }
                } else if (resultCode == RESULT_CANCELED || data == null) {
                    Toast.makeText(PlayActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    private void handleImage(Uri imageUri) {
        final InputStream imageStream;
        try {
            imageStream = getContentResolver().openInputStream(imageUri);
            mBitmapForRecognition = BitmapFactory.decodeStream(imageStream);
            mBitmapForRecognition = mBitmapForRecognition.copy(mBitmapForRecognition.getConfig(), true);

            Bitmap rotatedBitmap = rotateImage(mBitmapForRecognition, 90);

            mBitmapForRecognition = rotatedBitmap;
            recognize(mBitmapForRecognition, 0.2f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void recognize(final Bitmap bitmap, final float scale) {
        final Bitmap newBitmap = scaleImage(mBitmapForRecognition, scale);
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        MLKit.recognize(newBitmap, new MLKit.OnRecognizeListener() {
            @Override
            public void onSuccess(FirebaseVisionText texts) {
                Log.e("xxx", "recognize text on image with scale:" + scale);
                if (scale < 2 && scale != -1) {
                    foundTexts += texts.getText();
                    final float newScale = scale + 0.2f;
                    recognize(newBitmap, newScale);
                } else {// if(scale != -1){
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

    private Bitmap scaleImage(Bitmap source, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        return Bitmap.createBitmap(source, 0, 0, (int) (Math.ceil(source.getWidth())), (int) (Math.ceil(source.getHeight())),
                matrix, true);
    }

    private void processTextRecognitionResult(FirebaseVisionText texts) {
        findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        String foundText = foundTexts.toLowerCase(); //texts.getText();
        String expectedText = (mode.equals(MODE_LETTER)) ? letters[index] : words[index];
        expectedText = expectedText.toLowerCase();
        logIt("expected:" + expectedText + ", found:" + foundText);
        if (foundText.contains(expectedText)) {
            showPositiveView();
        } else {
            showNegativeView();
        }
    }

    private void showPositiveView() {
        if (mode.equals(MODE_LETTER)) {
            findViewById(R.id.view_positive_letters).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.view_positive_word).setVisibility(View.VISIBLE);
        }
    }

    private void showNegativeView() {
        if (mode.equals(MODE_LETTER)) {
            findViewById(R.id.view_negative_letters).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.view_negative_word).setVisibility(View.VISIBLE);
        }
    }

    public void hidePositiveView(View v) {
        index++;
        logIt("index " + index);
        if (mode.equals(MODE_LETTER)) {
            findViewById(R.id.view_positive_letters).setVisibility(View.GONE);
            if (index > letters.length - 1) {
                index = 0;
                Intent toModesActivity = new Intent(PlayActivity.this, ModesActivity.class);
                startActivity(toModesActivity);
                finish();
                return;
            }
            audioManager.playNextLetter(letters[index]);
            container.setText(letters[index].toUpperCase());
        } else {
            findViewById(R.id.view_positive_word).setVisibility(View.GONE);
            if (index > words.length - 1) {
                index = 0;
                Intent toModesActivity = new Intent(PlayActivity.this, ModesActivity.class);
                startActivity(toModesActivity);
                finish();
                return;
            }
            audioManager.playNextWord(words[index]);
            container.setText(words[index]);
        }
    }

    public void hideNegativeView(View v) {
        if (mode.equals(MODE_LETTER)) {
            findViewById(R.id.view_negative_letters).setVisibility(View.GONE);
        }else{

            findViewById(R.id.view_negative_word).setVisibility(View.GONE);
        }
    }

    private void logIt(String message) {
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }

    @Override
    protected void onDestroy() {
        audioManager.stop();
        super.onDestroy();
    }
}
