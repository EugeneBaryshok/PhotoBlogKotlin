package com.example.photokotlin.utils;

import com.example.photokotlin.data.model.service.RetrofitAdapter;
import com.example.photokotlin.data.model.service.UserService;

import java.util.Objects;

public class NetworkingUtils {
    private static UserService userService;

    public static UserService getUserApiInstance() {
        if (userService == null)
            userService = Objects.requireNonNull(RetrofitAdapter.INSTANCE.getInstance()).create(UserService.class);

        return userService;
    }
}
