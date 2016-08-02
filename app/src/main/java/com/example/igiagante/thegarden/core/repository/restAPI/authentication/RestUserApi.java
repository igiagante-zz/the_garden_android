package com.example.igiagante.thegarden.core.repository.restAPI.authentication;

import com.example.igiagante.thegarden.core.repository.network.HttpStatus;
import com.example.igiagante.thegarden.core.repository.network.ServiceFactory;
import com.example.igiagante.thegarden.core.repository.restAPI.BaseRestApi;
import com.example.igiagante.thegarden.core.repository.restAPI.services.UserRestApi;

import retrofit2.Response;
import rx.Observable;

/**
 * @author Ignacio Giagante, on 2/8/16.
 */
public class RestUserApi extends BaseRestApi {

    private HttpStatus httpStatus;
    private final UserRestApi api;

    public RestUserApi() {
        this.api = ServiceFactory.createRetrofitService(UserRestApi.class);
        this.httpStatus = new HttpStatus();
    }

    public Observable<String> registerUser(String userName, String password) {

        Observable<Response<InnerResponse>> result = api.createUser(userName, password);

        return result.flatMap(response -> {
            String httpStatusValue = "";
            if (response.isSuccessful()) {
                session.setToken(response.body().getToken());
                httpStatusValue = httpStatus.getHttpStatusValue(response.code());
            }
            return Observable.just(httpStatusValue);
        });
    }

    public Observable<String> loginUser(String userName, String password) {

        Observable<Response<InnerResponse>> result = api.loginUser(userName, password);

        return result.flatMap(response -> {
            String httpStatusValue = "";
            if (response.isSuccessful()) {
                session.setToken(response.body().getToken());
                httpStatusValue = httpStatus.getHttpStatusValue(response.code());
            }
            return Observable.just(httpStatusValue);
        });
    }

    public class InnerResponse {

        private boolean success;
        private String token;

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
    }
}
