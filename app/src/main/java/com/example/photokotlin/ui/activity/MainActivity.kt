package com.example.photokotlin.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.photokotlin.R
import com.example.photokotlin.contract.MainActivityContract
import com.example.photokotlin.data.model.User
import com.example.photokotlin.presentation.MainPresenter
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.example.photokotlin.ui.OnItemClickListener
import com.example.photokotlin.ui.adapters.UsersAdapter
import  kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainActivityContract.View {
    override fun navigateToPhotos(userId:Int) {
        val intent = Intent(this@MainActivity, PhotoActivity::class.java)
        intent.putExtra("userId", userId)
        startActivity(intent)
//        PhotoActivity.open(this, repositoryModel)
    }

    lateinit var mPresenter: MainPresenter
    var userAdapter: UsersAdapter? = null

    override fun init() {



//        mPresenter = UserPresenter(this)
        mPresenter.loadUsers()
    }
    private fun setupViews() {
        val manager = LinearLayoutManager(this)
        users_rv.setLayoutManager(manager)
    }
    override fun showError(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    override fun loadDataInList(users: List<User>) {
        userAdapter = UsersAdapter(users,itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this)
        users_rv.layoutManager = linearLayoutManager
        users_rv.adapter = userAdapter
    }
    private val itemClickListener = object : OnItemClickListener {
        override fun onClick(view: View, position: Int) {
            mPresenter.onItemClick(position)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        presenter = MainPresenter(HttpClientInjector.inject())


        mPresenter = MainPresenter()
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        mPresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        mPresenter.detachView()
    }
}
