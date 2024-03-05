package com.example.ecommerce.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.Activity.CartDActivity;
import com.example.ecommerce.Activity.ProductDActivity;
import com.example.ecommerce.Adapter.CartAdapter;
import com.example.ecommerce.Adapter.ProductAdapter;
import com.example.ecommerce.ModelClass.Caer.Cart;
import com.example.ecommerce.ModelClass.Caer.Product1;
import com.example.ecommerce.ModelClass.Product.Product;
import com.example.ecommerce.R;
import com.example.ecommerce.ViewModel.CartViewModel;
import com.example.ecommerce.ViewModel.ProductViewModel;
import com.example.ecommerce.databinding.FragmentCartBinding;
import com.example.ecommerce.databinding.FragmentHomeBinding;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CartFragment extends Fragment {

    FragmentCartBinding mBinding;
    private LinearLayoutManager layoutManager;
    private ArrayList<Cart> cartArrayList = new ArrayList<>();
    CartViewModel cartViewModel;
    private CartAdapter cartAdapter;
    private Dialog dialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentCartBinding.inflate(inflater, container, false);
        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        getCartitem();
        cartViewModel.callApiOne();

        mBinding.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog1();
            }
        });

    }

    private void showDialog1() {

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomlayout);
        SelectedDate();
        setDefaultDate();
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        Button saveButton = dialog.findViewById(R.id.save);
        TextView fromdate = dialog.findViewById(R.id.Fdate);
        TextView todate = dialog.findViewById(R.id.Tdate);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedDate = fromdate.getText().toString();
                String olddate = todate.getText().toString();
                mBinding.Time1.setText(selectedDate); // Set selected date to Time1 TextView
                mBinding.time2.setText(olddate);
                dialog.dismiss();
            }
        });

    }
    private void setDefaultDate() {
        TextView fDateTextView = dialog.findViewById(R.id.Fdate);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        fDateTextView.setText(currentDate);
    }
    private void SelectedDate() {
        TextView tDateTextView = dialog.findViewById(R.id.Tdate);
        CalendarView calendarView = dialog.findViewById(R.id.calendarView);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    month += 1;
                    String selectedDate = year + "/" + month + "/" + dayOfMonth;
                    tDateTextView.setText(selectedDate);
                }
            });
    }
    private void getCartitem() {
        cartViewModel.getArticleResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> carts) {
                if (!carts.isEmpty()) {

                    cartAdapter.updateCArtList(carts);

                }
                // Hide the progress bar after data is loaded
                mBinding.cartprogressbar.setVisibility(View.GONE);
            }
        });
        cartViewModel.mErrorMessage().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();

                mBinding.cartprogressbar.setVisibility(View.VISIBLE); // Show the progress bar initially
            }
        });
    }

    private void init() {
        layoutManager = new LinearLayoutManager(getActivity());
        mBinding.Cartrec.setLayoutManager(layoutManager);
        mBinding.Cartrec.setHasFixedSize(true);
        cartAdapter = new CartAdapter(getActivity());
        mBinding.Cartrec.setAdapter(cartAdapter);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
        mBinding.cartprogressbar.setVisibility(View.VISIBLE); // Show the progress bar initially

        cartAdapter.setOnCartClickListener(new CartAdapter.OnCartClickListener() {
            @Override
            public void onCartClick(Cart cart) {
                openCartDetailsActivity(cart);
            }
        });
    }

    private void openCartDetailsActivity(Cart cart) {

        Intent intent = new Intent(getActivity(), CartDActivity.class);
        intent.putExtra("CartDate", cart.getDate());
        intent.putExtra("CartPId", cart.getId());
        if (cart.getProducts() != null && !cart.getProducts().isEmpty()) {
            Product1 product = cart.getProducts().get(0); // Assuming only one product in the list
            intent.putExtra("ProductId", product.getProductId());
            intent.putExtra("Quantity", product.getQuantity());
        }
        startActivity(intent);

    }
}
