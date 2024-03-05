package com.example.ecommerce.API;

import com.example.ecommerce.ModelClass.Caer.Cart;
import com.example.ecommerce.ModelClass.Login.LoginUser;
import com.example.ecommerce.ModelClass.Product.Product;
import com.example.ecommerce.ModelClass.User.Users;
import com.example.ecommerce.Response.LoginResponse;
import com.example.ecommerce.Response.UserResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRequest {

    @GET("products")
    Observable<List<Product>> getproducts();

    @GET("carts/user/{id}")
    Observable<List<Cart>> getCart(@Path("id")int userId);

    @GET("users")
    Observable<List<Users>> getUsers();

    @GET("users/{id}")
    Observable<Users> getsingleuser(@Path("id")int userId);

    @POST("users")
    Observable<UserResponse>getnewUser(@Body Users users);

    @POST("auth/login")
    Observable<LoginResponse>getLogin(@Body LoginUser loginUser);



}

