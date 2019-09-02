package com.example.photokotlin.contract

import com.example.photokotlin.data.model.Photo
import com.example.photokotlin.data.model.User
import com.example.photokotlin.mvp.BasePresenter

interface PhotoActivityContract {
    interface View {
        fun init()

        fun showError(message: String)

        fun loadPhotoInList(photoList: List<Photo>)

        fun initAdapter()

        fun updateAdapter(usersList: ArrayList<Photo>)
    }

    interface Presenter: BasePresenter<PhotoActivityContract.View> {
//        fun start()

        fun loadPhotos()

        fun loadUserPhotos(userId:Int)
    }
}