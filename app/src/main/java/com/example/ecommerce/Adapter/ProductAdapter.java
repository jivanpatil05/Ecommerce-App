package com.example.ecommerce.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.ModelClass.Product.Product;
import com.example.ecommerce.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final Context context;
    List<Product> productarrayList;
    private OnProductClickListener onProductClickListener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context) {
        this.context = context;
    }
    public List<Product> getProductArrayList() {
        return productarrayList;
    }

    public void setOnProductClickListener(OnProductClickListener onProductClickListener) {
        this.onProductClickListener = onProductClickListener;
    }


    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productarrayList.get(position);
        holder.tvTitle.setText(product.getTitle());
        Double price = product.getPrice();
        String formattedPrice = String.format("%.2f", price); // Format the double value as a string with two decimal places
        holder.textViewPrice.setText(formattedPrice);
        holder.textViewDescription.setText(product.getDescription());
        holder.textViewCategory.setText(product.getCategory());
        holder.Rating.setText(String.valueOf(product.getRating().getRate()));

        Glide.with(context)
                .load(product.getImage())
                .into(holder.imageViewCover);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onProductClickListener != null) {
                    onProductClickListener.onProductClick(product);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (productarrayList == null) {
            return 0;
        }
        return productarrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageViewCover;
        private final TextView tvTitle, textViewPrice, textViewDescription, textViewCategory,Rating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCover = itemView.findViewById(R.id.imageViewCover);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewCategory = itemView.findViewById(R.id.textViewCategory);
            Rating=itemView.findViewById(R.id.Rating);
        }
    }


    public void updateList(List<Product> articleArrayList) {
        this.productarrayList = articleArrayList;
        notifyDataSetChanged();
    }
}
