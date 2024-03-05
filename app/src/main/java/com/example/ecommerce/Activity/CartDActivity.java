package com.example.ecommerce.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.ModelClass.Caer.Product1;
import com.example.ecommerce.R;
import com.example.ecommerce.databinding.ActivityCartDactivityBinding;
import com.example.ecommerce.databinding.ActivityProductDactivityBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;

public class CartDActivity extends AppCompatActivity implements PaymentResultListener {
    ActivityCartDactivityBinding mainxml;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_dactivity);
        mainxml = ActivityCartDactivityBinding.inflate(getLayoutInflater());
        setContentView(mainxml.getRoot());
        Checkout.preload(getApplicationContext());




        Intent intent = getIntent();
        if (intent != null) {
            String cartDate = intent.getStringExtra("CartDate");
            int cartPId = intent.getIntExtra("CartPId", -1);
            int quantity = intent.getIntExtra("Quantity", -1);


            if (mainxml.CArtUid != null && mainxml.date != null) {
                //productImage.setImageResource(Integer.parseInt(image));
                mainxml.CArtUid.setText(String.valueOf(cartPId));
                mainxml.date.setText(cartDate);
                mainxml.qty.setText(String.valueOf(quantity));

            }
        }

        mainxml.cartbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment();
            }
        });

    }

    private void payment() {
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
                options.put("amount","50000"); // Convert price to integer and into paise
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
        Toast.makeText(this, "Successfull Payment ID: "+s, Toast.LENGTH_SHORT).show();



    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Failed and Cause is:  "+s, Toast.LENGTH_SHORT).show();

    }
}