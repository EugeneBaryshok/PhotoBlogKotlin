package com.example.photokotlin.data.model.service;


import com.example.photokotlin.data.model.Album;
import com.example.photokotlin.data.model.Photo;
import com.example.photokotlin.data.model.User;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface UserService {
    @GET("/users/")
    Observable<List<User>> getUsers();

    @GET("/photos?albumId=1")
    Observable<List<Photo>> getPhotos();

    @GET("/photos")
    Observable<List<Photo>> getUserPhotos(@Query(value="albumId", encoded=true) int id);

    @GET("/albums")
    Observable<List<Album>> getAlbums(@Query(value="userId", encoded=true) int id);
}
