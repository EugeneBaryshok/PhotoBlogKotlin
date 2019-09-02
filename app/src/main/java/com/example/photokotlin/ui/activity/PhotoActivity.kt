package com.example.photokotlin.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.photokotlin.R
import com.example.photokotlin.contract.PhotoActivityContract
import com.example.photokotlin.data.model.Photo
import com.example.photokotlin.presentation.PhotoPresenter
import com.example.photokotlin.ui.adapters.PhotoAdapter
import kotlinx.android.synthetic.main.activity_photo.*

class PhotoActivity : AppCompatActivity(), PhotoActivityContract.View {


    lateinit var mPresenter: PhotoPresenter
    var photoAdapter: PhotoAdapter? = null
    var user_id = 1

    override fun showError(message: String) {
       Toast.makeText(this, "Error on:"+message, Toast.LENGTH_LONG).show()
    }
    override fun updateAdapter(photoList: ArrayList<Photo>) {
        photoAdapter?.update(photoList)
    }

    override fun initAdapter() {
        photoAdapter = PhotoAdapter()
        val linearLayoutManager = LinearLayoutManager(this)
        photos_rv.layoutManager = linearLayoutManager
        photos_rv.adapter = photoAdapter
    }
    override fun loadPhotoInList(photoList: List<Photo>) {
//        photoAdapter = PhotoAdapter(photoList)
//        val linearLayoutManager = LinearLayoutManager(this)
//        photos_rv.layoutManager = linearLayoutManager
//        photos_rv.adapter = photoAdapter
    }

    override fun init() {
        val manager = LinearLayoutManager(this)
        photos_rv.setLayoutManager(manager)

//        mPresenter = PhotoPresenter()
        mPresenter.loadUserPhotos(user_id)
//        mPresenter.loadPhotos()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)

        val manager = LinearLayoutManager(this)
        photos_rv.setLayoutManager(manager)

        var extras = intent.extras
        if (extras != null) {
            //init details view
            user_id = extras.getInt("userId")
            mPresenter = PhotoPresenter(user_id)


        }
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Photos"

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
