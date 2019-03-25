package restforyou.com.tofsologia.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.ml.vision.text.FirebaseVisionText;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.MLKit;
import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.views.GraphicOverlay;
import restforyou.com.tofsologia.views.TextGraphic;

import static restforyou.com.tofsologia.utils.Constants.IMAGE_URL;

public class TextRecognitionActivity extends AppCompatActivity {

    @BindView(R.id.tv_recognized_text)
    TextView mTextRecognized;
    @BindView(R.id.et_recognized_text)
    EditText editTextRecognized;
    @BindView(R.id.iv_image_for_recognition)
    ImageView mImageForRecognition;
    @BindView(R.id.go_image_for_recognition)
    GraphicOverlay mGraphicOverlay;
    @BindView(R.id.btn_text_ready)
    ImageButton mTextReady;

    private Bitmap mBitmapForRecognition;
    private String mResultText;


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);
        ButterKnife.bind(this);

        mTextReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTextReadyActivity = new Intent(TextRecognitionActivity.this, TextReadyActivity.class);
                startActivity(toTextReadyActivity);
            }
        });

       handleIntent();
    }


    private void handleIntent(){
        Intent receivedIntent = getIntent();
        String imageUriString = receivedIntent.getStringExtra(IMAGE_URL);
        Uri imageUri = Uri.parse(imageUriString);

        logIt("action " + receivedIntent.getAction()+ " url " + receivedIntent.getStringExtra(IMAGE_URL));

        final InputStream imageStream;
        try {
            imageStream = getContentResolver().openInputStream(imageUri);
            mBitmapForRecognition = BitmapFactory.decodeStream(imageStream);
            mImageForRecognition.setImageBitmap(mBitmapForRecognition);
            mBitmapForRecognition = mBitmapForRecognition.copy(mBitmapForRecognition.getConfig(), true);

            MLKit.recognize(mBitmapForRecognition, new MLKit.OnRecognizeListener() {
                @Override
                public void onSuccess(FirebaseVisionText texts) {
                    processTextRecognitionResult(texts);
                }

                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void processTextRecognitionResult(FirebaseVisionText texts){
        List<FirebaseVisionText.TextBlock> blocks = texts.getTextBlocks();

        logIt(texts.getText());

        if (blocks.size() == 0) {
            return;
        }

        mGraphicOverlay.clear();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < blocks.size(); i++) {
            List<FirebaseVisionText.Line> lines = blocks.get(i).getLines();
            stringBuffer.append(blocks.get(i).getText());

            for (int j = 0; j < lines.size(); j++) {
                List<FirebaseVisionText.Element> elements = lines.get(j).getElements();
                stringBuffer.append(lines.get(j).getText());

                for (int k = 0; k < elements.size(); k++) {

                    //stringBuffer.append(elements.get(k).getText());
                    if(k < elements.size()-1){
                        //stringBuffer.append(" ");
                    }

                    GraphicOverlay.Graphic textGraphic = new TextGraphic(mGraphicOverlay, elements.get(k));
                    mGraphicOverlay.add(textGraphic);
                }
                stringBuffer.append("\n");
            }
            stringBuffer.append("\n");
        }

        // Create bitmap from overlay view
        Bitmap textBitmap = Bitmap.createBitmap(mBitmapForRecognition.getWidth(), mBitmapForRecognition.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas textCanvas = new Canvas(textBitmap);
        mGraphicOverlay.draw(textCanvas);

        // Draw text bitmap over original image bitmap
        Canvas resultCanvas = new Canvas(mBitmapForRecognition);
        resultCanvas.drawBitmap(mBitmapForRecognition,0,0, null);
        resultCanvas.drawBitmap(textBitmap, 0,0, null);

        mImageForRecognition.setImageBitmap(mBitmapForRecognition);
        mTextRecognized.setText(stringBuffer.toString());
        editTextRecognized.setText(stringBuffer.toString());
    }

    private void logIt(String message){
        String TAG = this.getClass().getSimpleName();
        Log.e(TAG, message);
    }

}
