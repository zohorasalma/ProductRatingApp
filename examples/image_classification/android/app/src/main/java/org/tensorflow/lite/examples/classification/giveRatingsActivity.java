package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static org.tensorflow.lite.examples.classification.AppData.currentProduct;


public class giveRatingsActivity extends AppCompatActivity
{
    DatabaseReference databaseReference;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_ratings);
        ratingBar = findViewById(R.id.ratingbarid);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");

    }

    public void onClick(View view)
    {
        float rating = ratingBar.getRating();
        if(rating>0)
        {
            float sumRating = currentProduct.getSumRating()+rating;
            int ratingCount =currentProduct.getRatingCount()+1;
            float results = (float) (sumRating/ratingCount);
            databaseReference.child(String.valueOf(currentProduct.getProductId())).child("ratingCount").setValue(ratingCount);
            databaseReference.child(String.valueOf(currentProduct.getProductId())).child("rating").setValue(results);
            databaseReference.child(String.valueOf(currentProduct.getProductId())).child("sumRating").setValue(sumRating);
            currentProduct.setRating(results);
            currentProduct.setRatingCount(ratingCount);
            currentProduct.setSumRating((int) sumRating);
            Toast.makeText(giveRatingsActivity.this,"Thanks for your feedback!!",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(giveRatingsActivity.this,DisplayRatingsActivity.class);
            startActivity(intent);
            finish();
        }

    }
}