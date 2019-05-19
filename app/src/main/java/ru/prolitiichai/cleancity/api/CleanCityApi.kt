package ru.prolitiichai.cleancity.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query
import ru.prolitiichai.cleancity.dto.*

interface CleanCityApi {

    @POST("/api/login")
    fun login(@Query("username") login: String, @Query("password") password: String): Call<Void>

    @POST("/api/registration")
    fun registration(@Body body: RegistrationDto): Call<Void>

    @GET("/api/users/info")
    fun getUserData(): Call<User>

    @POST("/api/points/create")
    fun createPoint(@Body body: PointDto): Call<Long>

    @POST("/api/points/findBySquare")
    fun findBySquare(@Body body: PointSearchSquareDto): Call<List<Point>>

}