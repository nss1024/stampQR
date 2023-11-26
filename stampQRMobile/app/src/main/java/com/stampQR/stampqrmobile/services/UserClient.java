package com.stampQR.stampqrmobile.services;

import com.stampQR.stampqrmobile.wrappers.QRDataList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserClient {

    @GET("/api/v1/getDataByUser/{username}")
    //Call<QRDataList> getQrDataList();
    Call<QRDataList> getQrDataList(@Path("username") String username, @Header("Authorization") String authHeader);

}
