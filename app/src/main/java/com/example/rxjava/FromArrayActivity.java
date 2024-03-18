package com.example.rxjava;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.rxjava.databinding.ActivityFromArrayBinding;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FromArrayActivity extends AppCompatActivity {
    private Disposable disposable;
    private ActivityFromArrayBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFromArrayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Observable<User> observable=getObservableUser();
        Observer<User> observer=getObserverUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FromArrayActivity.this,JustActivity.class));
            }
        });
    }
    private Observable<User> getObservableUser(){
        User user1=new User(1,"User1");
        User user2=new User(2,"User2");
//        return Observable.fromArray(user1,user2);c1
        //cách 2
        User[] userArray=new User[]{user1,user2};
        return Observable.fromArray(userArray);
    }
    private Observer<User> getObserverUser(){
        return new Observer<User>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("Huy","OnSubscribe");
                disposable=d;
            }

            @Override
            public void onNext(@NonNull User user) {
                Log.e("Huy","OnNext: "+user.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Huy","OnError");
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