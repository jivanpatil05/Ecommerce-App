package com.example.ecommerce.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ecommerce.API.ProductRepository;
import com.example.ecommerce.ModelClass.Product.Product;
import com.example.ecommerce.ModelClass.User.Users;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class UsersViewModel extends AndroidViewModel {
    private final ProductRepository productRepository = new ProductRepository();
    private MutableLiveData<List<Users>> okTestData = new MutableLiveData<>();
    private MutableLiveData<String> mErrormsg = new MutableLiveData<>();



    public UsersViewModel(@NonNull Application application) {
        super(application);
    }

    public void callApiOne() {
        Observable<List<Users>> mObservable = Observable.defer(new Callable<Observable<? extends List<Users>>>() {
            @Override
            public Observable<List<Users>> call() throws Exception {
                return productRepository.callUsers();
            }
        });

        mObservable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(mObserver);
    }
    public void callsingleuser(int userId) {
        productRepository.callsingleuser(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Users>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // Add any necessary setup code
                    }

                    @Override
                    public void onNext(@NonNull Users user) {
                        List<Users> userList = new ArrayList<>();
                        userList.add(user);
                        okTestData.setValue(userList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mErrormsg.setValue(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        // Handle completion if needed
                    }
                });
    }

    Observer<List<Users>> mObserver = new Observer<List<Users>>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.i("APIERROR", "subscribe");
        }

        @Override
        public void onNext(List<Users> articleResponse) {
            Log.i("APIERROR", articleResponse.toString());
            okTestData.setValue(articleResponse);
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {
                ResponseBody body = ((HttpException) e).response().errorBody();
                Gson gson = new Gson();
                TypeAdapter<Users> adapter = gson.getAdapter
                        (Users.class);
                try {
                    Users errorParser =
                            adapter.fromJson(body.string());

                    Log.i("okhttp ", "Error: " + errorParser.toString());
                    mErrormsg.setValue(errorParser.toString());

                } catch (IOException t) {
                    t.printStackTrace();
                }
            }
            //  mErrormsg.setValue(e.getMessage());
            Log.d("error", e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.i("APIERROR",okTestData.toString());
        }
    };

    public MutableLiveData<String> mErrorMessage(){
        return mErrormsg;
    }

    public MutableLiveData<List<Users>> getArticleResponseLiveData(){
        return okTestData;
    }
}
