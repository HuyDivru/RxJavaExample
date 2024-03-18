package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Observable<User> observable=getObservableUser();
        Observer<User> observer=getObserverUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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
    private Observable<User> getObservableUser(){
        List<User> listUser=getListUser();
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Throwable {
                if(listUser==null||listUser.isEmpty()){
                    emitter.onError(new Exception());
                }
                for (User u: listUser
                     ) {
                    if(!emitter.isDisposed()){
                        emitter.onNext(u);
                    }
                }
                if (!emitter.isDisposed()){
                    emitter.onComplete();
                }
            }
        });
    }
    private List<User> getListUser(){
        List<User> list=new ArrayList<>();
        list.add(new User(1,"Hồ Nhật Huy 1"));
        list.add(new User(2,"Hồ Nhật Huy 2"));
        list.add(new User(3,"Hồ Nhật Huy 3"));
        list.add(new User(4,"Hồ Nhật Huy 4"));
        list.add(new User(5,"Hồ Nhật Huy 5"));
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable!=null){
            disposable.dispose();
        }
    }
}