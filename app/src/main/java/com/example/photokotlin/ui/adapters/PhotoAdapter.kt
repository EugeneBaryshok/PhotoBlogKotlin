package com.example.photokotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.photokotlin.R
import com.example.photokotlin.data.model.Photo

import kotlinx.android.synthetic.main.post_item.view.*


import com.example.photokotlin.utils.imageloader.MyImageLoader

import kotlin.collections.ArrayList


class PhotoAdapter() : RecyclerView.Adapter<PhotoAdapter.MyViewHolder>() {

    var photoList = ArrayList<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val photoModel = photoList[position]
        holder.bindData(photoModel)
    }

    public fun update(photoList: List<Photo>) {
        this.photoList.addAll(photoList)
        this.notifyDataSetChanged()
    }

    override fun getItemCount() = photoList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(photoModel: Photo) {
            val photoTitle = photoModel.title
            val photoUrl = photoModel.url


            if (photoUrl != null) {

                MyImageLoader.with(itemView.iv_news_newsImage.getContext()).load(itemView.iv_news_newsImage, itemView.post_progress, photoUrl)
            }
            itemView.tv_news_title.text = photoTitle

        }
    }

}