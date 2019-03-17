package restforyou.com.tofsologia.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.utils.Constants;

public class ScanActivity extends AppCompatActivity {
    @BindView(R.id.btn_scan_picture)
    Button buttonScanPicture;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        setListeners();
        setInitialUiElements();
    }


    private void setInitialUiElements(){


    }

    private void setListeners(){
        buttonMakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTextRecognitionActivity();
            }
        });

        buttonScanPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showTextRecognitionActivity();
            }
        });
        buttonChoosefromGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Constants.RESULT_LOAD_IMAGE);
            }
        });

    }

    private void showTextRecognitionActivity(){
        Intent toTextRecognitionActivity = new Intent(ScanActivity.this, TextRecognitionActivity.class);
        startActivity(toTextRecognitionActivity);
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                logIt(imageUri+" ");
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //todo close input stream
                materialButton.setText("Recognize");
                materialButton.setVisibility(View.VISIBLE);
                imageViewTempPhoto.setVisibility(View.GONE);
                textViewEmptyText.setVisibility(View.GONE);
                imageViewReadyImg.setVisibility(View.VISIBLE);
                imageViewReadyImg.setImageBitmap(selectedImage);

                //Intent i = new Intent(ScanActivity.this, TextRecognitionActivity.class);
                //startActivity(i);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ScanActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }finally {

            }

        }else {
            Toast.makeText(ScanActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
