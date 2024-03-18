package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RangeActivity extends AppCompatActivity {
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);
        Observable<Integer> observable=getObservableUser();
        Observer<Integer> observer=getObserverUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    private Observable<Integer> getObservableUser(){
        return Observable.range(1,10);
    }
    private Observer<Integer> getObserverUser(){
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("Huy","OnSubscribe");
                disposable=d;
            }

            @Override
            public void onNext(@NonNull Integer aLong) {
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