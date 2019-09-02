package com.example.photokotlin.utils


import android.arch.core.util.Function
import android.util.Log
import com.example.photokotlin.data.model.Album
import com.example.photokotlin.data.model.Photo
import com.example.photokotlin.data.model.User
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.function.BiFunction

object UserTask {

    fun getUsers(callback: Callback<List<User>>) {
        NetworkingUtils.getUserApiInstance()
            .users
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<List<User>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(users: List<User>) {
                    callback.returnResult(users)
                }

                override fun onError(e: Throwable) {
                    callback.returnError(e.message)
                }

                override fun onComplete() {

                }
            })
    }
    fun getPhotos(callback: Callback<List<Photo>>) {
        NetworkingUtils.getUserApiInstance()
            .photos
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<List<Photo>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(photos: List<Photo>) {
                    callback.returnResult(photos)
                }

                override fun onError(e: Throwable) {
                    callback.returnError(e.message)
                }

                override fun onComplete() {

                }
            })
    }
    fun getAlbums(userId:Int, callback: Callback<List<Album>>) {
        NetworkingUtils.getUserApiInstance()
            .getAlbums(userId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Observer<List<Album>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(albums: List<Album>) {
                    callback.returnResult(albums)
                }

                override fun onError(e: Throwable) {
                    callback.returnError(e.message)
                }

                override fun onComplete() {

                }
            })
    }
    fun getPhotosByAlbum(albumId: Int?, callback: Callback<List<Photo>>) {
        if (albumId != null) {
            NetworkingUtils.getUserApiInstance()
                .getUserPhotos(albumId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<List<Photo>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(photos: List<Photo>) {
                        callback.returnResult(photos)
                    }

                    override fun onError(e: Throwable) {
                        callback.returnError(e.message)
                    }

                    override fun onComplete() {

                    }
                })
        }
    }

    fun getPhotosByUser(userId: Int?, callback: Callback<List<Photo>>) {
        if (userId != null) {

            NetworkingUtils.getUserApiInstance()
                .getAlbums(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Observer<List<Album>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(albums: List<Album>) {
                        for (item in albums) {
                            item.id?.let {
                                NetworkingUtils.getUserApiInstance()
                                    .getUserPhotos(it)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(object : Observer<List<Photo>> {
                                        override fun onSubscribe(d: Disposable) {

                                        }

                                        override fun onNext(photos: List<Photo>) {
                                            callback.returnResult(photos)
                                        }

                                        override fun onError(e: Throwable) {
                                            callback.returnError(e.message)
                                        }

                                        override fun onComplete() {
                                            Log.d("TAG","sa");
                                        }
                                    })
                            }
                        }
//                        callback.returnResult(photos)
                    }

                    override fun onError(e: Throwable) {
                        callback.returnError(e.message)
                    }

                    override fun onComplete() {
                        Log.d("TAG","s");
                    }
                })
        }
    }


}