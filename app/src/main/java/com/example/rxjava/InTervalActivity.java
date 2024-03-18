package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InTervalActivity extends AppCompatActivity {
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_terval);
        Observable<Long> observable=getObservableUser();
        Observer<Long> observer=getObserverUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    private Observable<Long> getObservableUser(){
        return Observable.interval(3,5, TimeUnit.SECONDS);
    }
    private Observer<Long> getObserverUser(){
        return new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("Huy","OnSubscribe");
                disposable=d;
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.e("Huy","OnNext: "+aLong.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Huy","OnError: "+e.toString());
            }

            @Override
            public void onComplete() {
                Log.e("Huy","OnComplete");
            }
        };
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable!=null){
            disposable.dispose();
        }
    }
}