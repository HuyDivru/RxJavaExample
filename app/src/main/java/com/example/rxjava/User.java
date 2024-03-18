package com.example.rxjava;

import java.io.Serializable;

<<<<<<< HEAD
import io.reactivex.rxjava3.core.Observable;

=======
>>>>>>> c07b5a720ccd25b00fb518d4f9537dae6cbf192d
public class User implements Serializable {
    private int id;
    private String name;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
<<<<<<< HEAD
    public Observable<String> getNameObservable(){
        return Observable.just(name);
    }
    public Observable<String> getNameDeferObservable(){
        return Observable.defer(()->Observable.just(name));
    }
=======
>>>>>>> c07b5a720ccd25b00fb518d4f9537dae6cbf192d
}
