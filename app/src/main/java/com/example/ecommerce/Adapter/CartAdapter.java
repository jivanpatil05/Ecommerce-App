package com.example.ecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.ModelClass.Caer.Cart;
import com.example.ecommerce.ModelClass.Caer.Product1;
import com.example.ecommerce.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    List<Cart> cartarrayList;
    private CartAdapter.OnCartClickListener onCartClickListener;

    public interface OnCartClickListener{
        void onCartClick(Cart cart);
    }

    public CartAdapter(Context context) {
        this.context = context;
    }

    public void setOnCartClickListener(OnCartClickListener onCartClickListener) {
        this.onCartClickListener = onCartClickListener;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Cart cart = cartarrayList.get(position);

        holder.Cartdate.setText(cart.getDate());
        List<Product1> products = cart.getProducts();
        if (products != null && !products.isEmpty()) {
            Product1 product1 = products.get(0);
            if (product1 != null) {
                holder.CartProductid.setText(String.valueOf(product1.getProductId()));
                holder.CartProductqty.setText(String.valueOf(product1.getQuantity()));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCartClickListener != null) {
                    onCartClickListener.onCartClick(cart);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartarrayList == null) {
            return 0;
        }
        return cartarrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView Cartdate,CartProductid,CartProductqty ;//Cartid,Cartuid


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Cartid = itemView.findViewById(R.id.Cartid);
            //Cartuid = itemView.findViewById(R.id.CArtUid);
            Cartdate = itemView.findViewById(R.id.date);
            CartProductid = itemView.findViewById(R.id.CArtUid);
            CartProductqty = itemView.findViewById(R.id.qty);

        }
    }

    public void updateCArtList(List<Cart> cartarrayList) {
        this.cartarrayList = cartarrayList;
        notifyDataSetChanged();
    }
}
