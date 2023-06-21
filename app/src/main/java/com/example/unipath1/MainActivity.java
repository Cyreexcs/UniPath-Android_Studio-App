package com.example.unipath1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText user, password;
    DataBaseHelper dataBaseHelper;
    int student_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);

        loginBtn = findViewById(R.id.loginButton);

        user = findViewById(R.id.user);
        password = findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _user, _password;
                _user = user.getText().toString();
                _password  = password.getText().toString();
                if (!_user .isEmpty() && !_password.isEmpty()) {
                    if (dataBaseHelper.checkUserData(_user, _password) != -1) {
                        student_id = Integer.parseInt(_user);
                        openHomeScreen(dataBaseHelper.checkUserData(_user, _password), student_id);
                    }
                }
            }
        });


    }
    public void openHomeScreen(int semester_id, int student_id) {
        Intent intent = new Intent(this, HomeScreen.class);
        intent.putExtra("semester_id", semester_id);
        intent.putExtra("student_id", student_id);
        startActivity(intent);
    }
}