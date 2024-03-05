package com.example.ecommerce.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ecommerce.API.ProductRepository;
import com.example.ecommerce.ModelClass.Caer.Cart;
import com.example.ecommerce.ModelClass.Product.Product;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class CartViewModel extends AndroidViewModel {

    private final ProductRepository productRepository = new ProductRepository();
    private MutableLiveData<List<Cart>> okTestData = new MutableLiveData<>();
    private MutableLiveData<String> mErrormsg = new MutableLiveData<>();

    SharedPreferences sharedPreferences;

    public CartViewModel(@NonNull Application application) {
        super(application);
        sharedPreferences=application.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    }

    public void callApiOne() {
        int userId = sharedPreferences.getInt("USER_ID",-1);
        Observable<List<Cart>> mObservable = Observable.defer(new Callable<Observable<? extends List<Cart>>>() {
            @Override
            public Observable<List<Cart>> call() throws Exception {
                return productRepository.callCart(userId);
            }
        });

        mObservable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(mObserver);

    }

    Observer<List<Cart>> mObserver = new Observer<List<Cart>>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.i("APIERROR", "subscribe");
        }

        @Override
        public void onNext(List<Cart> CartResponse) {
            Log.i("APIERROR", CartResponse.toString());
            okTestData.setValue(CartResponse);
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {
                ResponseBody body = ((HttpException) e).response().errorBody();
                Gson gson = new Gson();
                TypeAdapter<Cart> adapter = gson.getAdapter
                        (Cart.class);
                try {
                    Cart errorParser =
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

    public MutableLiveData<List<Cart>> getArticleResponseLiveData(){
        return okTestData;
    }
}
