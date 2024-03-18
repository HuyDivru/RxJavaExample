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

public class MainActivity extends AppCompatActivity {
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Observable<User> observable=getObservableUsers();
        Observer<User> observer=getObserverUser();

        observable.subscribeOn(Schedulers.io())//phát ra dữ liệu
                .observeOn(AndroidSchedulers.mainThread())//nhận dữ liệu
                .subscribe(observer) ;//lấy nghe
    }
    //nhận dữ liệu đc phát ra bởi observable
    private Observer<User> getObserverUser(){
        return new Observer<User>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.e("Huy","onSubscribe");
                disposable=d;
            }

            @Override
            public void onNext(@NonNull User user) {
                Log.e("Huy","onNext: "+user.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Huy","onError");
            }

            @Override
            public void onComplete() {
                Log.e("Huy","onComplete");
            }
        };
    }
    //phát ra dữ liệu
    private Observable<User> getObservableUsers(){
        List<User> listUser=getListUsers();
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Throwable {
                if(listUser==null || listUser.isEmpty()){
                    if(!emitter.isDisposed()){
                        emitter.onError(new Exception());
                    }
                }
                for (User u:listUser
                     ) {
                    if(!emitter.isDisposed()){//nếu đang còn kết nối không ,nếu còn thì phát ra user
                        emitter.onNext(u);
                    }
                }
                if(!emitter.isDisposed()){
                    emitter.onComplete();
                }
            }
        });
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