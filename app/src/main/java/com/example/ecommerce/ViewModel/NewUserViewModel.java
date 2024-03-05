package com.example.ecommerce.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ecommerce.API.ProductRepository;
import com.example.ecommerce.ModelClass.User.Users;
import com.example.ecommerce.Response.UserResponse;
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

public class NewUserViewModel extends AndroidViewModel {

    private final ProductRepository productRepository = new ProductRepository();
    private MutableLiveData<UserResponse> okTestData = new MutableLiveData<>();
    private MutableLiveData<String> mErrormsg = new MutableLiveData<>();

    public NewUserViewModel(@NonNull Application application) {
        super(application);
    }

    public void callApiOne(Users users) {
        Observable<UserResponse> mObservable = Observable.defer(new Callable<Observable<? extends UserResponse>>() {
            @Override
            public Observable<UserResponse> call() throws Exception {
                return productRepository.callnewUser(users);
            }
        });

        mObservable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(mObserver);
    }

    Observer<UserResponse> mObserver = new Observer<UserResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.i("APIERROR", "subscribe");
        }

        @Override
        public void onNext(UserResponse userResponse) {
            Log.i("APIERROR", userResponse.toString());
            okTestData.setValue(userResponse);
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

    public MutableLiveData<UserResponse> getCreateUserObserver(){
        return okTestData;
    }
}
