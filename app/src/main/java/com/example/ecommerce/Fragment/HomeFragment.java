package com.example.ecommerce.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerce.Activity.ProductDActivity;
import com.example.ecommerce.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.Adapter.ProductAdapter;
import com.example.ecommerce.ModelClass.Product.Product;
import com.example.ecommerce.ViewModel.ProductViewModel;
import com.example.ecommerce.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    FragmentHomeBinding mBinding;
    private LinearLayoutManager layoutManager;
    private ArrayList<Product> productArrayList = new ArrayList<>();
    ProductViewModel productViewModel;
    private ProductAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(getLayoutInflater());
        View rootView=mBinding.getRoot();



        ImageSlider imageSlider = mBinding.imageSlider;
        ArrayList<SlideModel> imagelist = new ArrayList<>();
        imagelist.add(new SlideModel(R.drawable.b1, ScaleTypes.CENTER_CROP));
        imagelist.add(new SlideModel(R.drawable.b2, ScaleTypes.CENTER_CROP));
        imagelist.add(new SlideModel(R.drawable.b3, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imagelist);


        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getProductItem();
        productViewModel.callApiOne();

    }

    private void getProductItem() {
        productViewModel.getArticleResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                if (!products.isEmpty()) {

                    adapter.updateList(products);

                }
                // Hide the progress bar after data is loaded
                mBinding.progressBar.setVisibility(View.GONE);
            }
        });
        productViewModel.mErrorMessage().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                mBinding.progressBar.setVisibility(View.VISIBLE); // Show the progress bar initially
            }
        });
    }

    private void init() {

        layoutManager = new GridLayoutManager(getActivity(),2);
        mBinding.Productrec.setLayoutManager(layoutManager);
        mBinding.Productrec.setHasFixedSize(true);
        adapter = new ProductAdapter(getActivity());
        mBinding.Productrec.setAdapter(adapter);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mBinding.progressBar.setVisibility(View.VISIBLE); // Show the progress bar initially

        adapter.setOnProductClickListener(new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product) {
                openProductDetailsActivity(product);
            }
        });
    }

    private void openProductDetailsActivity(Product product) {

        //Toast.makeText(getActivity(), "Clicked on product: " + product.getPrice()+"Clicked on product: " + product.getRating().getRate(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ProductDActivity.class);

        // Pass the product details to the new activity
        intent.putExtra("Productimage", product.getImage());
        intent.putExtra("productId", product.getId());
        intent.putExtra("productName", product.getTitle());
        intent.putExtra("productPrice", product.getPrice());
        intent.putExtra("productDesc", product.getDescription());
        intent.putExtra("productRating", product.getRating().getRate());
        //intent.putExtra("productRatingCount", product.getRating().getCount());
        // Add more details as needed


        // Start the new activity
        startActivity(intent);
    }
}