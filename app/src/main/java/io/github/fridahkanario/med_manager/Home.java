package io.github.fridahkanario.med_manager;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import io.github.fridahkanario.med_manager.Adapters.SqliteDatabaseAdapter;
import io.github.fridahkanario.med_manager.Models.Medicine;

public class Home extends AppCompatActivity {
    ImageView image;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<Medicine> loadedMedication;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleSignInClient signInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();

                if (user == null){

                    Intent signinIntent = new Intent(Home.this, SignInActivity.class);
                    signinIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signinIntent);

                    finish();

                }
            }
        };

        loadedMedication = new ArrayList<>();

        image = findViewById(R.id.medImage);
        recyclerView = findViewById(R.id.medication_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SqliteDatabaseAdapter( loadedMedication);
        recyclerView.setHasFixedSize(true);

        refresh();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_event){
            Intent i = new Intent(this, AddMedication.class);
            startActivity(i);
        } else if (id == R.id.refresh){

           Intent i = new Intent(this, MyService.class);
           startService(i);

        }else if (id == R.id.logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }
    private void logout() {
        signInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(Home.this, SignInActivity.class);

                }

            }
        });


    }
    private void refresh() {

        DatabaseCon databaseCon = new DatabaseCon(this);
        Cursor cursor = databaseCon.getALLMedication();

        while(cursor.moveToNext()){

                loadedMedication.clear();

                //int m_id = cursor.getInt(0);
                String name = cursor.getString(0);
                String desc = cursor.getString(1);
                String presc = cursor.getString(2);
                String start = cursor.getString(3);

                Medicine med = new Medicine("medicine name :" + name, "medicine description :" + desc, "medicine prescription :" + presc, "medicine start date :" + start);

                loadedMedication.add(med);

                cursor.moveToNext();
        }
       // cursor.moveToNext();


        if(!(loadedMedication.size() <1 )){

            recyclerView.setAdapter(adapter);

        } else {
            Toast.makeText(this, "no item in the database ", Toast.LENGTH_SHORT).show();
        }
    }




}
