package com.example.photokotlin.presentation

import com.example.photokotlin.contract.PhotoActivityContract
import com.example.photokotlin.data.model.Album
import com.example.photokotlin.data.model.Photo
import com.example.photokotlin.utils.Callback
import com.example.photokotlin.utils.UserTask
import io.reactivex.disposables.CompositeDisposable


class PhotoPresenter internal constructor(var user_id: Int) :
    PhotoActivityContract.Presenter {
    lateinit var mView: PhotoActivityContract.View
    override fun attachView(view: PhotoActivityContract.View) {
        this.mView = view
        loadUserPhotos(user_id)
    }

    override fun detachView() {
    }

    override fun loadPhotos() {
        UserTask.getPhotos(object : Callback<List<Photo>>() {
            override fun returnResult(photos: List<Photo>) {
                mView.loadPhotoInList(photos)
            }

            override fun returnError(message: String) {
                mView.showError(message)
            }
        })
    }

    override fun loadUserPhotos(userId: Int) {
        UserTask.getAlbums(userId, object : Callback<List<Album>>() {
            override fun returnResult(albums: List<Album>) {
                mView.initAdapter()
                UserTask.getPhotosByUser(userId, object : Callback<List<Photo>>() {
                    override fun returnResult(photos: List<Photo>) {
                        mView.updateAdapter(ArrayList(photos))
                    }

                    override fun returnError(message: String) {
                        mView.showError(message)
                    }
                })
            }

            override fun returnError(message: String) {
                mView.showError(message)
            }
        })
    }

}