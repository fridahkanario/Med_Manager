package io.github.fridahkanario.med_manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import io.github.fridahkanario.med_manager.Models.Medicine;

public class AddMedication extends AppCompatActivity {

    EditText medName, medDesc ;
    DatePicker startDte;
    TextView error;
    Spinner  medprescription;
    Button save;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicine);

        medName = findViewById(R.id.name);
        medDesc = findViewById(R.id.desc);
        startDte = findViewById(R.id.startDate);
        medprescription = findViewById(R.id.prescription);
        error = findViewById(R.id.error);



        save = findViewById(R.id.save);
        image = findViewById(R.id.medImage);

        final DatabaseCon databaseCon = new DatabaseCon(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name =  medName.getText().toString();
                String desc =  medDesc.getText().toString();
                String start = startDte.getDayOfMonth() +":" + startDte.getMonth()+":" + startDte.getYear();

                String prescription = medprescription.getSelectedItem().toString();


                Medicine med = new Medicine(name,desc,prescription,start);
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(start) && !TextUtils.isEmpty(prescription)) {
                    databaseCon.addNewMedication(med);
                    medName.setText("");
                    medDesc.setText("");
                   // medprescription.setSelected(0);

                    Toast.makeText(getApplicationContext(), " data has been saved succesfully ", Toast.LENGTH_SHORT).show();

                } else{

                    error.setText("Please fill all the entries");
                }

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.medication) {
            Intent i = new Intent(this, Home.class);
            startActivity(i);
        }else if(id == R.id.logout){
            logout();

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AddMedication.this, SignInActivity.class);
    }
}
