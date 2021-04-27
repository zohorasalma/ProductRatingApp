package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.tensorflow.lite.examples.classification.model.RatedProducts;
import org.tensorflow.lite.examples.classification.model.ScannedProducts;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.firebase.database.FirebaseDatabase.getInstance;
import static org.tensorflow.lite.examples.classification.AppData.currentProduct;


public class giveRatingsActivity extends AppCompatActivity
{
    int rating;
    DatabaseReference databaseReference,mref;
    RatingBar ratingBar;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_ratings);
        ratingBar = findViewById(R.id.ratingbarid);
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        mref = getInstance().getReference("RatedProducts");
        animationView = findViewById(R.id.thanksAnimationId);


    }

    public void onClick(View view)
    {
        rating = (int) ratingBar.getRating();
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

            inputRatedProducts();

            animationView.setVisibility(View.VISIBLE);
            animationView.playAnimation();

//            Intent intent = new Intent(giveRatingsActivity.this,DisplayRatingsActivity.class);
//            startActivity(intent);
//            finish();
        }

    }

    private void inputRatedProducts()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("-hh-mm-ss-dd-MM-yyyy");
        String timestamp = simpleDateFormat.format(new Date());


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        if(currentFirebaseUser!= null)
        {
            String userEmail = currentFirebaseUser.getEmail();
            String key = mref.push().getKey();
            int productId = currentProduct.getProductId();
            String productName = currentProduct.getProductName();
            int categoryId = currentProduct.getCategoryId();
            int varientNo = currentProduct.getVarientNo();
            RatedProducts ratedProduct= new RatedProducts(userEmail,productId,productName,categoryId,varientNo,timestamp,rating);
            mref.child(key).setValue(ratedProduct);
        }
    }
}