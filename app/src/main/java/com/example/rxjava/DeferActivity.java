package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DeferActivity extends AppCompatActivity {
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defer);

        User user=new User(1,"Ho Nhat Huy");
        Observable<String> observable=user.getNameObservable();
        Observer<String> observer=getObserverUser();
        user.setName("Ho Nhat Huy 2");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    private Observer<String> getObserverUser(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("Huy","OnSubscribe");
                disposable=d;
            }

            @Override
            public void onNext(@NonNull String aLong) {
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