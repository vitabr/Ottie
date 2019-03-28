package restforyou.com.tofsologia.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.model.DbManager;
import restforyou.com.tofsologia.model.Record;
import restforyou.com.tofsologia.utils.Constants;
import restforyou.com.tofsologia.utils.photo.PhotoUtils;

public class ScanActivity extends AppCompatActivity implements Constants, AdapterCallback {


    @BindView(R.id.btn_make_picture)
    Button buttonMakePicture;
    @BindView(R.id.btn_choose_from_gallery)
    Button buttonChooseFromGallery;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private File capturedPhotoFile = null;

    private AdapterCallback onItemClickedListener = new AdapterCallback() {
        @Override
        public void onItemClick(Record record, int position) {
            Toast.makeText(ScanActivity.this, "clicked", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();

    }


    private void setInitialUiElements() {
        getRecords();
    }

    private void getRecords() {
        DbManager.getInstance().getAllRecords(new DbManager.OnResult<List<Record>>() {
            @Override
            public void onResult(List<Record> result) {
                recycler.setLayoutManager(new GridLayoutManager(ScanActivity.this, 2));
                recycler.setAdapter(new HistoryAdapter(result, onItemClickedListener));
            }
        });

    }

    private void setListeners() {
        buttonMakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dispatchTakePictureIntent();
                showTextRecognitionActivity(null);
            }
        });

        buttonChooseFromGallery.setOnClickListener(new View.OnClickListener() {
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
                if(resultCode == RESULT_OK){
                    final Uri imageUri = Uri.fromFile(capturedPhotoFile);
                    showTextRecognitionActivity(imageUri);
                }else if (resultCode == RESULT_CANCELED || data == null){
                    Toast.makeText(ScanActivity.this, "Something went wrong...", Toast.LENGTH_LONG).show();
                }

                break;
        }


    }

    private void showTextRecognitionActivity(Uri imageUri) {
        logIt(imageUri + " ");

        logIt(imageUri + " ");
        Intent toTextRecActivityIntent = new Intent(ScanActivity.this, PlayActivity.class);
        //Intent toTextRecActivityIntent = new Intent(ScanActivity.this, TextRecognitionActivity.class);
        //Intent toTextRecActivityIntent = new Intent(ScanActivity.this, WorkingExampleActivity.class);
        //toTextRecActivityIntent.setAction(Constants.RECEIVED_IMAGE);
        if (imageUri != null) {
            toTextRecActivityIntent.putExtra(Constants.IMAGE_URL, imageUri.toString());
        }
        startActivity(toTextRecActivityIntent);
    }

    @Override
    public void onItemClick(Record record, int position) {
        Log.d("TAG", "onItemClick: " + position);
    }

}