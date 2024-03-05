package com.example.ecommerce.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerce.Activity.LoginActivity;
import com.example.ecommerce.Adapter.CartAdapter;
import com.example.ecommerce.Adapter.UsersAdapter;
import com.example.ecommerce.ModelClass.Caer.Cart;
import com.example.ecommerce.ModelClass.User.Users;
import com.example.ecommerce.R;
import com.example.ecommerce.ViewModel.CartViewModel;
import com.example.ecommerce.ViewModel.UsersViewModel;
import com.example.ecommerce.databinding.FragmentCartBinding;
import com.example.ecommerce.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;
    private LinearLayoutManager layoutManager;
    private ArrayList<Users> cartArrayList = new ArrayList<>();
    UsersViewModel usersViewModel;
    private UsersAdapter usersAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(getLayoutInflater());
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

        int loggedInUserId = getLoggedInUserId();
        if (loggedInUserId != -1) {
            getUserById(loggedInUserId);
        } else {
            Toast.makeText(getActivity(), "NO Users", Toast.LENGTH_SHORT).show();
            mBinding.userprogressbar.setVisibility(View.GONE);
        }

    }

    private void getUserById(int userId) {
        usersViewModel.callsingleuser(userId);
        usersViewModel.getArticleResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {
                if (!users.isEmpty()) {
                    usersAdapter.updateUserlist(users);
                }
                mBinding.userprogressbar.setVisibility(View.GONE);
            }
        });

        usersViewModel.mErrorMessage().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                mBinding.userprogressbar.setVisibility(View.GONE);

            }
        });
    }

    private void init() {
        layoutManager = new LinearLayoutManager(getActivity());
        mBinding.Userrec.setLayoutManager(layoutManager);
        mBinding.Userrec.setHasFixedSize(true);
        usersAdapter = new UsersAdapter(getActivity());
        mBinding.Userrec.setAdapter(usersAdapter);
        usersViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);
        mBinding.userprogressbar.setVisibility(View.VISIBLE); // Show the progress bar initially
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(LoginActivity.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("USER_ID", -1);
    }
}
