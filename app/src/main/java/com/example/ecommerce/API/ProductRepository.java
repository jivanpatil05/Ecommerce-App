package com.example.ecommerce.API;

import com.example.ecommerce.ModelClass.Caer.Cart;
import com.example.ecommerce.ModelClass.Login.LoginUser;
import com.example.ecommerce.ModelClass.Product.Product;
import com.example.ecommerce.ModelClass.User.Users;
import com.example.ecommerce.Response.LoginResponse;
import com.example.ecommerce.Response.UserResponse;

import java.util.List;

import io.reactivex.Observable;

public class ProductRepository {

    private static final String TAG = ProductRepository.class.getSimpleName();
    private final ApiRequest apiRequest = RetrofitRequest.getClient().create(ApiRequest.class);


    public Observable<List<Product>> callApi() {
        return apiRequest.getproducts();
    }

    public Observable<List<Cart>> callCart(int userId) {
        return apiRequest.getCart(userId);
    }

    public Observable<List<Users>> callUsers() {
        return apiRequest.getUsers();
    }

    public Observable<Users> callsingleuser(int userId) {
        return apiRequest.getsingleuser(userId);
    }

    public Observable<UserResponse> callnewUser(Users users) {
        return apiRequest.getnewUser(users);}

    public Observable<LoginResponse> callLogin(LoginUser loginUser) {
        return apiRequest.getLogin(loginUser);}

}
