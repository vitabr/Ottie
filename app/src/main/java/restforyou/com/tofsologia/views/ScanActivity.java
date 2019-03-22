package restforyou.com.tofsologia.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.button.MaterialButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.utils.Constants;
import restforyou.com.tofsologia.utils.photo.PhotoUtils;

public class ScanActivity extends AppCompatActivity implements Constants {


    @BindView(R.id.btn_make_picture)
    Button buttonMakePicture;
    @BindView(R.id.btn_choose_from_galery)
    Button buttonChoosefromGalery;
    @BindView(R.id.img_temporary_picture)
    ImageView imageViewTempPhoto;
    @BindView(R.id.floatingActionButton)
    MaterialButton materialButton;
    @BindView(R.id.tv_empty_text)
    TextView textViewEmptyText;
    @BindView(R.id.img_ready_photo)
    ImageView imageViewReadyImg;

    private File capturedPhotoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();
    }


    private void setInitialUiElements() {


    }

    private void setListeners() {
        buttonMakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        buttonChoosefromGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Constants.REQUEST_CHOOSE_IMAGE);
            }
        });


    }

    private void dispatchTakePictureIntent() {
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



    private void logIt(String message) {
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    final Uri imageUri = data.getData();
                    showTextRecognitionActivity(imageUri);
                } else {
                    Toast.makeText(ScanActivity.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
                }
                break;

            case REQUEST_CAPTURE_IMAGE:
                final Uri imageUri = Uri.fromFile(capturedPhotoFile);
                showTextRecognitionActivity(imageUri);
                break;
        }


    }

    private void showTextRecognitionActivity(Uri imageUri) {
        logIt(imageUri + " ");
        if (imageUri != null) {
            logIt(imageUri + " ");
            Intent toTextRecActivityIntent = new Intent(ScanActivity.this, TextRecognitionActivity.class);
            toTextRecActivityIntent.setAction(Constants.RECEIVED_IMAGE);
            toTextRecActivityIntent.putExtra(Constants.IMAGE_URL, imageUri.toString());
            startActivity(toTextRecActivityIntent);
        }
    }

    //part for text recognition activity
//    private void showPrewiew(Bitmap bitmap) {
//        materialButton.setText("Recognize");
//        materialButton.setVisibility(View.VISIBLE);
//        imageViewTempPhoto.setVisibility(View.GONE);
//        textViewEmptyText.setVisibility(View.GONE);
//        imageViewReadyImg.setVisibility(View.VISIBLE);
//        imageViewReadyImg.setImageBitmap(bitmap);
//    }



}