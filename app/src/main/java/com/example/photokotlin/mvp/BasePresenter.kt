package com.example.photokotlin.mvp

interface BasePresenter<A> {
    fun attachView(view: A)
    fun detachView()
}