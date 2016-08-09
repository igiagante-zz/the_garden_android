package com.example.igiagante.thegarden.core.repository.restAPI.services;

import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.restAPI.authentication.RestUserApi;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public interface UserRestApi {

    @GET("user/{username}")
    Observable<User> getUser(@Path("username") String userName);

    @FormUrlEncoded
    @POST("user/refreshToken")
    Observable<Response<RestUserApi.InnerResponse>> refreshToken(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("user/signup")
    Observable<Response<RestUserApi.InnerResponse>> createUser(@Field("username") String userName, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/login")
    Observable<Response<RestUserApi.InnerResponse>> loginUser(@Field("username") String userName, @Field("password") String password);
}
