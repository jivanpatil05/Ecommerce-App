package com.example.ecommerce.ViewModel;
import android.app.Application;
import android.util.Log;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ecommerce.API.ProductRepository;
import com.example.ecommerce.ModelClass.Product.Product;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ProductViewModel extends AndroidViewModel {
    private final ProductRepository productRepository = new ProductRepository();
    private MutableLiveData<List<Product>> okTestData = new MutableLiveData<>();
    private MutableLiveData<String> mErrormsg = new MutableLiveData<>();

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }

    public void callApiOne() {
        Observable<List<Product>> mObservable = Observable.defer(new Callable<Observable<? extends List<Product>>>() {
            @Override
            public Observable<List<Product>> call() throws Exception {
                return productRepository.callApi();
            }
        });

        mObservable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(mObserver);

    }

    Observer<List<Product>> mObserver = new Observer<List<Product>>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.i("APIERROR", "subscribe");
        }

        @Override
        public void onNext(List<Product> articleResponse) {
            Log.i("APIERROR", articleResponse.toString());
            okTestData.setValue(articleResponse);
        }

        @Override
        public void onError(Throwable e) {
            if (e instanceof HttpException) {
                ResponseBody body = ((HttpException) e).response().errorBody();
                Gson gson = new Gson();
                TypeAdapter<Product> adapter = gson.getAdapter
                        (Product.class);
                try {
                    Product errorParser =
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

    public MutableLiveData<List<Product>> getArticleResponseLiveData(){
        return okTestData;
    }
}