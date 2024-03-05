package com.example.ecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.ModelClass.Caer.Cart;
import com.example.ecommerce.ModelClass.Product.Product;
import com.example.ecommerce.ModelClass.User.Users;
import com.example.ecommerce.R;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private final Context context;
    private List<Users> UsersArrayList;

    public UsersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        if (UsersArrayList != null && UsersArrayList.size() > 0) {
            Users firstUser = UsersArrayList.get(position);
            holder.username.setText(String.valueOf(firstUser.getUsername()));
            holder.email.setText(String.valueOf(firstUser.getEmail()));
            holder.fname.setText(String.valueOf(firstUser.getName().getFirstname()));
            holder.lname.setText(String.valueOf(firstUser.getName().getLastname()));
            holder.pnum.setText(String.valueOf(firstUser.getPhone()));
        }

    }

    @Override
    public int getItemCount() {
        if (UsersArrayList == null) {
            return 0;
        }
        return UsersArrayList.size();
        //return UsersArrayList != null && UsersArrayList.size() > 0 ? 1 : 0;//for singl users
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username,email,fname,lname,pnum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.Username);
            fname = itemView.findViewById(R.id.fname);
            lname = itemView.findViewById(R.id.lname);
            email = itemView.findViewById(R.id.email);
            pnum = itemView.findViewById(R.id.Pnum);
        }
    }
    public void updateUserlist(List<Users> articleArrayList) {
        this.UsersArrayList = articleArrayList;
        notifyDataSetChanged();

    }
}
