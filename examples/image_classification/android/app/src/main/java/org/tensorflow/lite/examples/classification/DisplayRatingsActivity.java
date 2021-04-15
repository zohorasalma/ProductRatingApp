package org.tensorflow.lite.examples.classification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.tensorflow.lite.examples.classification.model.Product;

import java.util.ArrayList;
import java.util.List;

import static org.tensorflow.lite.examples.classification.AppData.PRODUCT_NAME;
import static org.tensorflow.lite.examples.classification.AppData.currentProduct;
import  org.tensorflow.lite.examples.classification.BaseActivity.*;

public class DisplayRatingsActivity extends AppCompatActivity
{
    DatabaseReference databaseReference;
    final List<Product> allproducts= new ArrayList<>();
    String productName = PRODUCT_NAME;
    TextView tvproductName;
    TextView tvProductDes;
    TextView tvProductRating;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ratings);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");

        ProgressDialog mProgressDialogCancelable = new ProgressDialog(this);
        mProgressDialogCancelable.setIndeterminate(true);
        mProgressDialogCancelable.setMessage("Request is being processed");
        databaseReference = FirebaseDatabase.getInstance().getReference("Products");
        tvproductName = findViewById(R.id.productNameId);
        tvProductDes = findViewById(R.id.productDescriptionId);
        tvProductRating = findViewById(R.id.productRatingId);

        fetchData();


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
            }


        }

        hideProgressDialog();
        Log.d("displayRatingActivity", "all product : "+allproducts.toString());

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
}