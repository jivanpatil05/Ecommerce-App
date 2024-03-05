package com.example.ecommerce.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.ecommerce.API.ProductRepository;
import com.example.ecommerce.ModelClass.Login.LoginUser;
import com.example.ecommerce.Response.LoginResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class LoginViewMode extends AndroidViewModel {

    private final ProductRepository productRepository = new ProductRepository();
    private MutableLiveData<LoginResponse> okTestData = new MutableLiveData<>();
    private MutableLiveData<String> mErrormsg = new MutableLiveData<>();

    public LoginViewMode(@NonNull Application application) {
        super(application);
    }

    public void callApiOne(LoginUser loginUser) {
        Observable<LoginResponse> mObservable = productRepository.callLogin(loginUser);

        mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("APIERROR", "subscribe");
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        Log.i("APIERROR", loginResponse.toString());
                        okTestData.setValue(loginResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            Gson gson = new Gson();
                            TypeAdapter<LoginUser> adapter = gson.getAdapter(LoginUser.class);
                            try {
                                LoginUser errorParser = adapter.fromJson(body.string());

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
                        Log.i("APIERROR", okTestData.toString());
                    }
                });
    }

    public MutableLiveData<String> mErrorMessage() {
        return mErrormsg;
    }

    public MutableLiveData<LoginResponse> getCreateUserObserver() {
        return okTestData;
    }
}