package org.tensorflow.lite.examples.classification;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.tensorflow.lite.examples.classification.model.SensorDataProvider;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String SENSORDATA_TABLE = "SENSORDATA_TABLE";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_TIMESTAMP = "Timestamp";
    public static final String COLUMN_X_ACC = "X_ACC";
    public static final String COLUMN_Y_ACC = "Y_ACC";
    public static final String COLUMN_Z_ACC = "Z_ACC";
    public static final String COLUMN_X_GYRO = "X_GYRO";
    public static final String COLUMN_Y_GYRO = "Y_GYRO";
    public static final String COLUMN_Z_GYRO = "Z_GYRO";
    public static final String COLUMN_X_MAGNETO = "X_MAGNETO";
    public static final String COLUMN_Y_MAGNETO = "Y_MAGNETO";
    public static final String COLUMN_Z_MAGNETO = "Z_MAGNETO";
    public static final String COLUMN_LONGITUDE = "LONGITUDE";
    public static final String COLUMN_LATITUDE = "LATITUDE";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_LIGHT = "LIGHT";

    public DatabaseHelper(
            @Nullable Context context
    )
    {
        super(context, "SensorDataTable.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String createTableStatement= "CREATE TABLE " + SENSORDATA_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + COLUMN_TIMESTAMP + " TEXT , " +
                "" + COLUMN_X_ACC + " TEXT, " + COLUMN_Y_ACC + " TEXT, " + COLUMN_Z_ACC + " TEXT," +
                COLUMN_X_GYRO + " TEXT," + COLUMN_Y_GYRO + " TEXT," + COLUMN_Z_GYRO + " TEXT," +
                COLUMN_X_MAGNETO + " TEXT," + COLUMN_Y_MAGNETO + " TEXT," + COLUMN_Z_MAGNETO + " TEXT," +
                COLUMN_LIGHT + " TEXT,"
                +COLUMN_LONGITUDE + " TEXT, " + COLUMN_LATITUDE + " TEXT, " + COLUMN_ADDRESS + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);


    }

    @Override
    public void onUpgrade(
            SQLiteDatabase sqLiteDatabase,
            int i,
            int i1)
    {

    }
    public boolean insertData(SensorDataProvider sensorDataProvider)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_TIMESTAMP,sensorDataProvider.getTimestamp());

        cv.put(COLUMN_X_ACC,sensorDataProvider.getxAccelerometer());
        cv.put(COLUMN_Y_ACC,sensorDataProvider.getyAccelerometer());
        cv.put(COLUMN_Z_ACC,sensorDataProvider.getzAccelerometer());

        cv.put(COLUMN_X_GYRO,sensorDataProvider.getxGyroscope());
        cv.put(COLUMN_Y_GYRO,sensorDataProvider.getyGyroscope());
        cv.put(COLUMN_Z_GYRO,sensorDataProvider.getzGyroscope());

        cv.put(COLUMN_X_MAGNETO,sensorDataProvider.getxMagnetometer());
        cv.put(COLUMN_Y_MAGNETO,sensorDataProvider.getyMagnetometer());
        cv.put(COLUMN_Z_MAGNETO,sensorDataProvider.getzMagnetometer());

        cv.put(COLUMN_LIGHT,sensorDataProvider.getLight());

        cv.put(COLUMN_LONGITUDE,sensorDataProvider.getLongitude());
        cv.put(COLUMN_LATITUDE,sensorDataProvider.getLatitude());
        cv.put(COLUMN_ADDRESS,sensorDataProvider.getAddress());


        long l = db.insert(SENSORDATA_TABLE, null, cv);
        if(l==-1){
            return false;
        }
        else {
            return true;
        }
    }
    public List<SensorDataProvider> getAllSensorData()
    {
        List<SensorDataProvider> returnList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor;
        cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+SENSORDATA_TABLE,null);
        if(cursor.moveToFirst())
        {
            do{
                int id= cursor.getInt(0);
                String timestamp = cursor.getString(1);

                String xAcc = cursor.getString(2);
                String yAcc = cursor.getString(3);
                String zAcc = cursor.getString(4);

                String xGyro = cursor.getString(5);
                String yGyro = cursor.getString(6);
                String zGyro = cursor.getString(7);

                String xMagneto = cursor.getString(8);
                String yMagneto = cursor.getString(9);
                String zMagneto = cursor.getString(10);

                String light = cursor.getString(11);

                String longtitude = cursor.getString(12);
                String latitude = cursor.getString(13);
                String address = cursor.getString(14);


                SensorDataProvider sensorDataProvider = new SensorDataProvider(id,timestamp
                        ,xAcc,yAcc,zAcc
                        ,xGyro,yGyro,zGyro
                        ,xMagneto,yMagneto,zMagneto
                        ,light
                        ,longtitude,latitude,address);
                returnList.add(sensorDataProvider);

            }while (cursor.moveToNext());

        }
        else
        {

        }
        sqLiteDatabase.close();
        cursor.close();
        return returnList;
    }
    public void deleteSensorValues(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(SENSORDATA_TABLE,null,null);
        sqLiteDatabase.close();
    }}
