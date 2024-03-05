package com.example.ecommerce.API;

import com.example.ecommerce.ModelClass.Product.Product;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    @SerializedName("articles")
    @Expose
    private List<Product> articles;

    public List<Product> getArticles() {
        return articles;
    }

    public void setArticles(List<Product> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "BashboardNewsResponse{"+
                "articles"+ articles +'}';
    }
}
