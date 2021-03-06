package restforyou.com.tofsologia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.model.DbManager;
import restforyou.com.tofsologia.model.Record;

import static restforyou.com.tofsologia.utils.Constants.CURRENT_RECORD_ID;
import static restforyou.com.tofsologia.utils.Constants.RECORD_EXIST;

public class TextReadyActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_content)
    TextView content;
    @BindView(R.id.content_editable)
    EditText contentEditable;
    @BindView(R.id.tv_file_name)
    TextView fileName;

    private Record mRecord;
    private boolean isEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_text);
        ButterKnife.bind(this);
        getRecord();
        initViews();

    }

    private void getRecord() {
//        record = new Record(
//                -1,
//                "Text.txt",
//                getResources().getString(R.string.text),
//                "photo.png",
//                "text.txt");

        Intent fromTextRecognition = getIntent();
        if (fromTextRecognition.getAction() == RECORD_EXIST){
            long id = fromTextRecognition.getLongExtra(CURRENT_RECORD_ID, -1);
            Log.e("xxx", id+" ");
            DbManager.getInstance().getRecordById(id, new DbManager.OnResult<Record>() {
                @Override
                public void onResult(Record record) {
                    mRecord = record;
                    content.setText(mRecord.getDescription());
                    fileName.setText(mRecord.getFileName());
                }
            });

        }
    }

    private void initViews() {
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);

        //content.setText(record.getDescription());
        //fileName.setText(record.getFileName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home:
                returnToMainActivity();
                break;
            case R.id.btn_edit:
                editContent();
                break;
            case R.id.btn_save:
                saveContent();
                break;
        }
    }

    private void saveContent() {
        if (isEditMode) {
            contentEditable.setVisibility(View.INVISIBLE);
            content.setText(contentEditable.getText());
            content.setVisibility(View.VISIBLE);
            closeKeyboard();
            saveToRecord();
            isEditMode = false;
        }
    }

    private void getAllRecords(){
        DbManager.getInstance().getAllRecords(new DbManager.OnResult<List<Record>>() {
            @Override
            public void onResult(List<Record> result) {
                for (Record record : result){
                    Log.e("xxx", "records :" + record);
                }
            }
        });
    }

    private void editContent() {
        if (!isEditMode) {
            content.setVisibility(View.INVISIBLE);
            contentEditable.setVisibility(View.VISIBLE);
            contentEditable.setText(content.getText());
            isEditMode = true;
        }
    }

    private void saveToRecord() {
        mRecord.setDescription(content.getText().toString());
        DbManager.getInstance().updateRecord(mRecord, new DbManager.OnResult<Void>(){
            @Override
            public void onResult(Void result) {
                getAllRecords();
            }
        });
    }

    private void returnToMainActivity() {
        final Intent intent = new Intent(this, ScanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
}
