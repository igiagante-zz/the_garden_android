package com.example.igiagante.thegarden.core.repository.restAPI.authentication;

import android.content.Context;
import android.util.Log;

import com.example.igiagante.thegarden.core.Session;
import com.example.igiagante.thegarden.core.domain.entity.User;
import com.example.igiagante.thegarden.core.repository.network.HttpStatus;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.realm.UserRealmRepository;
import com.example.igiagante.thegarden.core.repository.restAPI.services.UserRestApi;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class RestUserApi {

    private static final String TAG = RestUserApi.class.getSimpleName();

    private final UserRestApi api;
    private final UserRealmRepository realmRepository;

    private HttpStatus httpStatus;
    private Session session;

    public RestUserApi(Context context, Session session) {
        this.api = ServiceFactory.createRetrofitService(UserRestApi.class);
        this.httpStatus = new HttpStatus();
        this.session = session;
        this.realmRepository = new UserRealmRepository(context);
    }

    public Observable<String> registerUser(User user) {

        Observable<Response<InnerResponse>> result = api.createUser(user.getUserName(), user.getPassword());

        return result.flatMap(response -> {
            String httpStatusValue = "";
            if (response.isSuccessful()) {
                session.setUserName(user.getUserName());
                session.setToken(response.body().getToken());
                httpStatusValue = httpStatus.getHttpStatusValue(response.code());

                realmRepository.add(user);
            } else {
                try{
                    String error = response.errorBody().string();
                    MessageError messageErrorKey = new Gson().fromJson(error, MessageError.class);
                    httpStatusValue = httpStatus.getMessage(messageErrorKey.getMessage());
                }catch (IOException ie) {
                    Log.d(TAG, ie.getMessage());
                }
            }

            return Observable.just(httpStatusValue);
        });
    }

    public Observable<String> loginUser(User user) {

        Observable<Response<InnerResponse>> result = api.loginUser(user.getUserName(), user.getPassword());

        return result.flatMap(response -> {
            String httpStatusValue = "";
            if (response.isSuccessful()) {
                session.setUserName(user.getUserName());
                session.setToken(response.body().getToken());
                httpStatusValue = httpStatus.getHttpStatusValue(response.code());
            }
            return Observable.just(httpStatusValue);
        });
    }

    public class InnerResponse {

        private boolean success;
        private String token;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public class MessageError {

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
