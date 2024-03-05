package com.example.ecommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Fragment.CartFragment;
import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityProductDactivityBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class ProductDActivity extends AppCompatActivity implements PaymentResultListener {
    ActivityProductDactivityBinding mainxml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dactivity);
        mainxml= ActivityProductDactivityBinding.inflate(getLayoutInflater());
        setContentView(mainxml.getRoot());
        Checkout.preload(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            String image="";
            String id="";
            String name="";
            Double price = 0.0;
            String description="";
            Double rating = 0.0;
            String Ratingcount="";

            image=extras.getString("Productimage");
            id=extras.getString("productId");
            name=extras.getString("productName");
            price=extras.getDouble("productPrice");
            rating=extras.getDouble("productRating");
            description=extras.getString("productDesc");
            Ratingcount=extras.getString("productRatingCount");


            if (mainxml.productimage != null && mainxml.productId != null && mainxml.productName != null && mainxml.productprice != null && mainxml.productdescription != null && mainxml.productRating  != null && mainxml.productRatingcount != null) {
                //productImage.setImageResource(Integer.parseInt(image));
                mainxml.productId.setText(id);
                mainxml.productName.setText( name);
                mainxml.productprice.setText(String.valueOf(price));
                mainxml.productdescription.setText( description);
                mainxml.productRating.setText(String.valueOf(rating));
                mainxml.productRatingcount.setText(Ratingcount);

                Glide.with(getApplicationContext())
                        .load(image)
                        .into(mainxml.productimage);
            }
        }
        mainxml.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartFragment cartFragment = new CartFragment();

                // Get the FragmentManager
                FragmentManager fragmentManager = getSupportFragmentManager(); // Use getSupportFragmentManager() if you're in AppCompatActivity

                // Begin the transaction
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Replace the current fragment with the new one
                transaction.replace(R.id.fragment_container, cartFragment); // R.id.fragment_container should be the id of the container layout in your activity where you want to place the fragment

                // Add the transaction to the back stack
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });

        mainxml.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double productPrice = Double.parseDouble(mainxml.productprice.getText().toString());
                payment(productPrice);
            }
        });

    }

    private void payment(double productPrice) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_Wcsnm0OWQD4Xcr");

        checkout.setImage(R.drawable.png11);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();

            options.put("name", "JpMarket");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#FFB300");
            options.put("currency", "INR");
            options.put("amount", String.valueOf((int)(productPrice * 100))); // Convert price to integer and into paise
            options.put("prefill.email", "G1patil0509@gmail.com");
            options.put("prefill.contact","70307733371");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);

        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        mainxml.Paytost.setText("Successfull Payment ID: "+s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        mainxml.Paytost.setText("Failed and Cause is: "+s);
    }
}