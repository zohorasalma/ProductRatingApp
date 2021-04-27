package org.tensorflow.lite.examples.classification;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.tensorflow.lite.examples.classification.model.SensorDataProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.google.firebase.database.FirebaseDatabase.getInstance;


public class MainActivity extends AppCompatActivity
{
    EditText userEmail, userPass;
    Button btnRegister;
    SessionManager sessionManager;
    String sUserEmail;
    String sPass;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userEmail =findViewById(R.id.userEmailId);
        userPass =findViewById(R.id.passwordId);
        btnRegister =findViewById(R.id.bt_register);
        auth = FirebaseAuth.getInstance();

        sessionManager=new SessionManager(getApplicationContext());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 sUserEmail = userEmail.getText().toString().trim();
                 sPass = userPass.getText().toString().trim();
                if(sPass.equals("")|| sUserEmail.equals("")){
                    userEmail.setError("Please enter UserName");
                    userPass.setError("Please enter PhoneNumber");
                }
                else{
                    sessionManager.setLogin(true);
                    sessionManager.setUserName(sUserEmail);
                    registerUser(sUserEmail, sPass);


                }



            }
        });
        if(sessionManager.getLogin())
        {
            startActivity(new Intent(getApplicationContext(), ClassifierActivity.class));
        }


    }


    private void registerUser(
            String email,
            String pass)
    {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Registered user successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), ClassifierActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Registration failed !!",Toast.LENGTH_LONG).show();

                }

            }
        });
    }


}