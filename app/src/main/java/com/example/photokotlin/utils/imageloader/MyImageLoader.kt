package com.example.photokotlin.utils.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.LruCache
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import java.lang.ref.WeakReference
import java.util.*



internal interface DataLoadCallback {
    fun loadImageIntoImageView(imageView: ImageView, bitmap: Bitmap?, progressBar: ProgressBar, imageUrl: String)
    fun isImageViewReused(imageRequest: MyImageLoader.ImageRequest): Boolean
}

class MyImageLoader: DataLoadCallback {


    private val maxCacheSize: Int = (Runtime.getRuntime().maxMemory() / 1024).toInt() / 8
    private val memoryCache: LruCache<String, Bitmap>


    private val imageViewMap = Collections.synchronizedMap(WeakHashMap<ImageView, String>())


    init {
        memoryCache = object : LruCache<String, Bitmap>(maxCacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than number of items.
                return bitmap.byteCount / 1024
            }
        }


    }

    companion object {

        private var INSTANCE: MyImageLoader? = null

        @Synchronized
        fun with(context: Context): MyImageLoader {

            require(context != null) {
                "ImageLoader:with - Context should not be null."
            }

            return INSTANCE ?: MyImageLoader().also {
                INSTANCE = it
            }

        }
    }

    fun load(imageView: ImageView, progressBar: ProgressBar, imageUrl: String) {

        require(imageView != null) {
            "ImageLoader:load - ImageView should not be null."
        }

        require(imageUrl != null && imageUrl.isNotEmpty()) {
            "ImageLoader:load - Image Url should not be empty"
        }

        imageView.setImageResource(0)
        progressBar.visibility = View.VISIBLE
        imageViewMap[imageView] = imageUrl

        val bitmap = checkImageInCache(imageUrl)
        bitmap?.let {
            loadImageIntoImageView(imageView, it, progressBar, imageUrl)
        } ?: run {
            DownloadImage(ImageRequest(imageUrl, imageView, progressBar), memoryCache, this).execute()
//            executorService.submit(PhotosLoader(ImageRequest(imageUrl, imageView)))
        }

    }

    @Synchronized
    override fun loadImageIntoImageView(
        imageView: ImageView,
        bitmap: Bitmap?,
        progressBar: ProgressBar,
        imageUrl: String
    ) {

        require(bitmap != null) {
            "ImageLoader:loadImageIntoImageView - Bitmap should not be null"
        }

//        val scaledBitmap = Utils.scaleBitmapForLoad(bitmap, imageView.width, imageView.height)

//        scaledBitmap?.let {
        if (!isImageViewReused(ImageRequest(imageUrl, imageView, progressBar))) imageView.setImageBitmap(bitmap)
//        }
//        imageView.setImageBitmap(bitmap)
        progressBar.visibility=View.GONE
    }

    override fun isImageViewReused(imageRequest: ImageRequest): Boolean {
        val tag = imageViewMap[imageRequest.imageView]
        return tag == null || tag != imageRequest.imgUrl
    }

    inner class ImageRequest(var imgUrl: String, var imageView: ImageView, var progressBar: ProgressBar)

    @Synchronized
    private fun checkImageInCache(imageUrl: String): Bitmap? = memoryCache.get(imageUrl)


    private class DownloadImage(
        var image: ImageRequest, var memoryCache: LruCache<String, Bitmap>, var callback: DataLoadCallback
    ) : AsyncTask<String, Void, Bitmap>() {
        private var callbackRef: WeakReference<DataLoadCallback>? = null

         override fun onPreExecute() {
            super.onPreExecute()
            callbackRef = WeakReference(callback)
            image.progressBar.visibility = View.VISIBLE
        }

         override fun doInBackground(vararg URL: String): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                // Download Image from URL
                val input = java.net.URL(image.imgUrl).openStream()
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bitmap
        }

         override fun onPostExecute(result: Bitmap) {
            // Set the bitmap into ImageView
            memoryCache.put(image.imgUrl, result)

            val ref = callbackRef?.get()
            if (!(ref?.isImageViewReused(image)!!))
                ref.loadImageIntoImageView(image.imageView, result, image.progressBar, image.imgUrl)

            image.progressBar.visibility = View.GONE
        }


    }


}

