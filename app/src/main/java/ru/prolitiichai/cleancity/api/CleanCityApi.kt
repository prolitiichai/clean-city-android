package ru.prolitiichai.cleancity.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import ru.prolitiichai.cleancity.dto.RegistrationDto

interface CleanCityApi {

    @POST("/api/login")
    fun login(@Query("username") login: String, @Query("password") password: String): Call<Void>

    @POST("/api/registration")
    fun registration(@Body body: RegistrationDto): Call<Void>

}