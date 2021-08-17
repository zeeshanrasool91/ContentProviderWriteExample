package com.example.contentproviderexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contentproviderexample.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        onClickShowDetails(binding.btnRetrieve);
        Objects.requireNonNull(binding.txtName.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.txtName.setError(null);
            }
        });
    }

    public void onClickAddDetails(View view) {
        String name = Objects.requireNonNull(binding.txtName.getEditText()).getText().toString();
        if(TextUtils.isEmpty(name)){
            binding.txtName.setError("Cannot be Empty!");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(UsersProvider.name, name);
        getContentResolver().insert(UsersProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
        onClickShowDetails(binding.btnRetrieve);
    }

    public void onClickShowDetails(View view) {
        // Retrieve employee records
        TextView resultView = (TextView) findViewById(R.id.res);
        Cursor cursor = getContentResolver().query(Uri.parse("content://com.example.readfromcontentprovider.Birthday/users"), null, null, null, null);
        if (cursor.moveToFirst()) {
            StringBuilder strBuild = new StringBuilder();
            while (!cursor.isAfterLast()) {
                int idIndex = cursor.getColumnIndex("id");
                String id = cursor.getString(idIndex);
                int nameIndex = cursor.getColumnIndex("name");
                String name = cursor.getString(nameIndex);
                strBuild.append("\n" + id + "-" + name);
                cursor.moveToNext();
            }
            resultView.setText(strBuild);
        } else {
            resultView.setText("No Records Found");
        }
    }
}
