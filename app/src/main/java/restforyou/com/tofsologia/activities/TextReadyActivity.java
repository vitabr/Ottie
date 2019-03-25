package restforyou.com.tofsologia.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import restforyou.com.tofsologia.R;
import restforyou.com.tofsologia.model.Record;

public class TextReadyActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView content;
    private EditText contentEditable;
    private Record record;
    private boolean isEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_text);
        getRecord();
        initViews();

    }

    private void getRecord() {
        record = new Record(
                -1,
                "Text.txt",
                getResources().getString(R.string.text),
                "photo.png",
                "text.txt");
    }

    private void initViews() {
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btn_edit).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);

        content = findViewById(R.id.tv_content);
        contentEditable = findViewById(R.id.content_editable);

        content.setText(record.getDescription());
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

    private void editContent() {
        if (!isEditMode) {
            content.setVisibility(View.INVISIBLE);
            contentEditable.setVisibility(View.VISIBLE);
            contentEditable.setText(content.getText());
            isEditMode = true;
        }
    }

    private void saveToRecord() {
        record.setDescription(content.getText().toString());
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
