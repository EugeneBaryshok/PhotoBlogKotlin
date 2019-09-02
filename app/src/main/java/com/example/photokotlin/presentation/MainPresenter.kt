package com.example.photokotlin.presentation

import com.example.photokotlin.contract.MainActivityContract
import com.example.photokotlin.data.model.User
import com.example.photokotlin.utils.Callback
import com.example.photokotlin.utils.UserTask
import java.util.ArrayList

class MainPresenter internal constructor() :
    MainActivityContract.Presenter {

    lateinit var mView: MainActivityContract.View
    private var usersList = ArrayList<User>()

    override fun attachView(view: MainActivityContract.View) {
        this.mView = view
        loadUsers()
    }

    override fun detachView() {


    }


    override fun loadUsers() {
        UserTask.getUsers(object : Callback<List<User>>() {
            override fun returnResult(users: List<User>) {
                mView.loadDataInList(users)
                usersList = users as ArrayList<User>
            }

            override fun returnError(message: String) {
                mView.showError(message)
            }
        })
    }
    fun onItemClick(position: Int) {
        mView?.navigateToPhotos(usersList[position].id!!)
    }

}