package io.github.fridahkanario.med_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.SignInButton;

public class MainActivity extends AppCompatActivity {
    public SignInButton signin;
   // private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signin=findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign=new Intent(MainActivity.this,AddMedication.class);
                startActivity(sign);
            }
        });
    }
}
