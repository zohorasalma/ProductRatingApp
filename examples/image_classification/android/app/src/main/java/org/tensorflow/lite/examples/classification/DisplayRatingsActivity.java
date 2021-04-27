package org.tensorflow.lite.examples.classification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.tensorflow.lite.examples.classification.model.Product;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static org.tensorflow.lite.examples.classification.AppData.PRODUCT_NAME;
import static org.tensorflow.lite.examples.classification.AppData.currentProduct;
import  org.tensorflow.lite.examples.classification.BaseActivity.*;
import org.tensorflow.lite.examples.classification.model.ScannedProducts;
import org.tensorflow.lite.examples.classification.model.SensorDataProvider;

public class DisplayRatingsActivity extends AppCompatActivity implements SensorEventListener
{
    DatabaseReference databaseReference,mref,dref;
    final List<Product> allproducts= new ArrayList<>();
    String productName = PRODUCT_NAME;
    TextView tvproductName;
    TextView tvProductDes;
    TextView tvProductRating;
    ImageView productImage;
    private FirebaseAuth auth;

    private ProgressDialog mProgressDialog;

    DatabaseHelper databaseHelper = new DatabaseHelper(this);

    private SensorManager sensorManager;
    private Sensor accelerometer, gyroscope, magnetometer, humidity, light, pressure, temperature;

    String str_xAcc, str_yAcc, str_zAcc;
    double acc_x, acc_y, acc_z;

    String str_gyroXValue, str_gyroYValue, str_gyroZValue;
    double gyro_x, gyro_y, gyro_z;

    String str_xMagnetoValue, str_yMagnetoValue, str_zMagnetoValue;
    double mag_x, mag_y, mag_z;
    String str_Latitude,str_Longitude,str_Address;
    double Latitude, Longitude;
    String Address = "";
    String str_light;
    double lightData;
    String userEmail;

    long timeInMillis;
    FusedLocationProviderClient fusedLocationProviderClient;


    private Handler dataHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ratings);
        tvproductName = findViewById(R.id.productNameId);
        tvProductDes = findViewById(R.id.productDescriptionId);
        tvProductRating = findViewById(R.id.productRatingId);
        productImage = findViewById(R.id.productImageId);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        ProgressDialog mProgressDialogCancelable = new ProgressDialog(this);
        mProgressDialogCancelable.setIndeterminate(true);
        mProgressDialogCancelable.setMessage("Request is being processed");

        databaseReference = getInstance().getReference("Products");
        mref = getInstance().getReference("ScannedProducts");
        dref = getInstance().getReference("SensorTable");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = user.getEmail();

        fetchData();
        //my coding's
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE); //string

        //sensor initialization
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        if (accelerometer != null)
        {
            sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);//alt+sft+ent hardware cast
        }
        if (gyroscope != null)
        {
            sensorManager.registerListener((SensorEventListener) this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);//alt+sft+ent hardware cast
        }
        if (magnetometer != null)
        {
            sensorManager.registerListener((SensorEventListener) this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);//alt+sft+ent hardware cast
        }
        if (light != null)
        {
            sensorManager.registerListener((SensorEventListener) this, light, SensorManager.SENSOR_DELAY_NORMAL);//alt+sft+ent hardware cast
        }
        if (pressure != null)
        {
            sensorManager.registerListener((SensorEventListener) this, pressure, SensorManager.SENSOR_DELAY_NORMAL);//alt+sft+ent hardware cast
        }
        if (temperature != null)
        {
            sensorManager.registerListener((SensorEventListener) this, temperature, SensorManager.SENSOR_DELAY_NORMAL);//alt+sft+ent hardware cast
        }
        if (humidity != null)
        {
            sensorManager.registerListener((SensorEventListener) this, humidity, SensorManager.SENSOR_DELAY_NORMAL);//alt+sft+ent hardware cast
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>()
            {
                @Override
                public void onComplete(@NonNull Task<Location> task)
                {
                    Location location = task.getResult();
                    if (location != null)
                    {
                        try
                        {
                            Geocoder geocoder = new Geocoder(DisplayRatingsActivity.this, Locale.getDefault());

                            List<android.location.Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            Latitude = addresses.get(0).getLatitude();
                            Longitude = addresses.get(0).getLongitude();
                            Address = addresses.get(0).getAddressLine(0);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }

                }
            });

        }
        else
        {
            ActivityCompat.requestPermissions(DisplayRatingsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
        sensorDataRunnable.run();
        firebaseRunnable.run();




    }
    private Runnable firebaseRunnable = new Runnable() {
        @Override
        public void run()
        {
            Intent intent=new Intent(DisplayRatingsActivity.this,myService.class);
            startService(intent);
            List<SensorDataProvider> listSensorDataProvider = databaseHelper.getAllSensorData();
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
            String userId= currentFirebaseUser.getUid();
            dref.child(userId).push().setValue(listSensorDataProvider);
          //  emailRef.push().setValue(listSensorDataProvider);
            Log.d("OptionActivity","Sensor values in 2 min "+listSensorDataProvider.toString());
            databaseHelper.deleteSensorValues();
            dataHandler.postDelayed(firebaseRunnable,120000);
        }
    };
    private Runnable sensorDataRunnable =new Runnable() {
        @Override
        public void run() {

            Intent intent=new Intent(DisplayRatingsActivity.this,myService.class);
            startService(intent);
            str_xAcc = Double.toString(acc_x);
            str_yAcc = Double.toString(acc_y);
            str_zAcc = Double.toString(acc_z);
            str_gyroXValue= Double.toString(gyro_x);
            str_gyroYValue= Double.toString(gyro_y);
            str_gyroZValue= Double.toString(gyro_z);
            str_xMagnetoValue = Double.toString(mag_x);
            str_yMagnetoValue = Double.toString(mag_y);
            str_zMagnetoValue = Double.toString(mag_z);
            str_light = Double.toString(lightData);
            str_Latitude = Double.toString(Latitude);
            str_Longitude = Double.toString(Longitude);
            str_Address = Address;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("-hh-mm-ss-dd-MM-yyyy");
            String timestamp = simpleDateFormat.format(new Date());

            SensorDataProvider sensorDataProvider = new SensorDataProvider(-1,timestamp,str_xAcc,str_yAcc,str_zAcc,
                    str_gyroXValue,str_gyroYValue,str_gyroZValue,str_xMagnetoValue,str_yMagnetoValue,str_zMagnetoValue,
                    str_light,
                    str_Longitude,str_Latitude,str_Address);


            boolean check=databaseHelper.insertData(sensorDataProvider);
            if(check==true){
                //     Toast.makeText(OptionActivity.this,"Sensor values Inserted Successfully",Toast.LENGTH_SHORT).show();
                Log.d("OptionActivity", "Sensor values : "+sensorDataProvider.toString()+" is inserted to sqlite");
            }
            else {
                Toast.makeText(DisplayRatingsActivity.this,"Sensor value Insertion Failed",Toast.LENGTH_SHORT).show();
            }

            dataHandler.postDelayed(sensorDataRunnable,30000);

        }
    };


    private void inputScannedProduct()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("-hh-mm-ss-dd-MM-yyyy");
        String timestamp = simpleDateFormat.format(new Date());


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        Log.d("MainActivity", "Current userId: " + currentFirebaseUser.getUid() );
        if(currentFirebaseUser!= null)
        {
            String userEmail = currentFirebaseUser.getEmail();
            String key = mref.push().getKey();
            int productId = currentProduct.getProductId();
            String productName = currentProduct.getProductName();
            int categoryId = currentProduct.getCategoryId();
            int varientNo = currentProduct.getVarientNo();
            ScannedProducts scannedProducts = new ScannedProducts(userEmail,productId,productName,categoryId,varientNo,timestamp);
            mref.child(key).setValue(scannedProducts);
        }
    }

    private void fetchData()
    {
        showProgressDialog();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Product product = dataSnapshot.getValue(Product.class);
                    allproducts.add(product);

                }
                matchData();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    private void matchData()
    {
        for(int i = 0; i<allproducts.size();i++)
        {
            if(productName.toLowerCase().contains(allproducts.get(i).getProductName().toLowerCase()))
            {
                currentProduct= allproducts.get(i);
                tvproductName.setText(currentProduct.getProductName());
                tvProductDes.setText(currentProduct.getProductDescription());
                tvProductRating.setText(Float.toString(currentProduct.getRating()));
                String imageUri= currentProduct.getPhotoUrl();
                Picasso.with(DisplayRatingsActivity.this).load(imageUri).fit().centerCrop()
                        //  .placeholder(R.drawable.user_placeholder)
                        //.error(R.drawable.user_placeholder_error)
                        .into(productImage);

               // Glide.with(this).load(currentProduct.getPhotoUrl()).centerCrop().into(productImage);
            }
        }

        hideProgressDialog();
        Log.d("displayRatingActivity", "all product : "+allproducts.toString());
        inputScannedProduct();

    }

    public void onClick(View view)
    {
        Intent intent = new Intent(DisplayRatingsActivity.this,giveRatingsActivity.class);
        startActivity(intent);

    }
    public void showProgressDialog()
    {

        if (mProgressDialog != null && !mProgressDialog.isShowing()) mProgressDialog.show();
    }

    public void hideProgressDialog()
    {

        if (mProgressDialog != null && mProgressDialog.isShowing()) mProgressDialog.dismiss();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        Sensor sensor = sensorEvent.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            acc_x = sensorEvent.values[0];
            acc_y = sensorEvent.values[1];
            acc_z = sensorEvent.values[2];
        }

        if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            gyro_x = sensorEvent.values[0];
            gyro_y = sensorEvent.values[1];
            gyro_z = sensorEvent.values[2];
        }

        if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {

            mag_x = sensorEvent.values[0];
            mag_y = sensorEvent.values[1];
            mag_z = sensorEvent.values[2];
        }
        if (sensor.getType() == Sensor.TYPE_LIGHT) {
            lightData = sensorEvent.values[0];
        }
       /* if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            hum = sensorEvent.values[0];
        }


        if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            pressureData = sensorEvent.values[0];
        }
        if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temperatureData = sensorEvent.values[0];
        }*/
        timeInMillis =System.currentTimeMillis();


        //        timeInMillis = (new Date()).getTime()
        //                + (sensorEvent.timestamp - System.nanoTime()) / 1000000L;


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}