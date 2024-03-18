package com.example.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class JustActivity extends AppCompatActivity {
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just);
        Observable observable=getObservableUser();
        Observer<Serializable> observer=getObserverUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
    private Observable getObservableUser(){
        User user1=new User(1,"User1");
        User user2=new User(2,"User2");
        User user4=new User(3,"User4");
//        return Observable.fromArray(user1,user2);c1
        //cách 2
        List<User> listUser=getListUsers();
        String strData="User3";
        User[] userArray=new User[]{user1,user2};
        return Observable.just(userArray,strData,user4,listUser);
//        return Observable.just(user1,user2)
//                .map(user -> user.toString());
    }
    private Observer<Serializable> getObserverUser(){
        return new Observer<Serializable>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("Huy","OnSubscribe");
                disposable=d;
            }

            @Override
            public void onNext(@NonNull Serializable user) {
                Log.e("Huy","OnNext: "+user.toString());
                if (user instanceof User[]){
                    User[] users=(User[]) user;
                    for (User u:users){
                        Log.e("Huy","User: "+u.toString());
                    }
                }
                else  if(user instanceof String){
                    String mystr=(String) user;
                    Log.e("Huy","User: "+mystr);
                }
                else  if(user instanceof User){
                    User mystr=(User) user;
                    Log.e("Huy","User: "+mystr.toString());
                }
                else  if(user instanceof List){
                   List<User> mystr=(List<User>) user;
                    for (User u: mystr
                         ) {
                        Log.e("Huy","User: "+u.toString());
                    }
                }
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
    private List<User> getListUsers(){
        List<User> list=new ArrayList<>();
        list.add(new User(1,"Hồ Nhật Huy"));
        list.add(new User(2,"Hồ Nhật Huy"));
        list.add(new User(3,"Hồ Nhật Huy"));
        list.add(new User(4,"Hồ Nhật Huy"));
        list.add(new User(5,"Hồ Nhật Huy"));
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