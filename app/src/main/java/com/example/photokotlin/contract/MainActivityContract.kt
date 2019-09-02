package com.example.photokotlin.contract

import com.example.photokotlin.data.model.User
import com.example.photokotlin.mvp.BasePresenter

interface MainActivityContract {
    interface View {
        fun init()

        fun showError(message: String)

        fun loadDataInList(users: List<User>)

        fun navigateToPhotos(userId:Int)
    }

    interface Presenter: BasePresenter<View> {
        fun loadUsers()
    }
}
