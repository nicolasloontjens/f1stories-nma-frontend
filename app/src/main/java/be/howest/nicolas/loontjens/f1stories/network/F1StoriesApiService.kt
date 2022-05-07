package be.howest.nicolas.loontjens.f1stories.network

import be.howest.nicolas.loontjens.f1stories.network.data.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val API_URL = "http://f1stories.herokuapp.com/api/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(API_URL)
    .build()

interface F1StoriesApiService{
    @POST("users/register")
    suspend fun register(@Body user:UserAuth):UserToken

    @POST("users/login")
    suspend fun login(@Body user:UserAuth):UserToken

    @GET("races")
    suspend fun getRaces():List<Race>

    @GET("stories")
    suspend fun getStories():List<Story>
}

object F1StoriesApi {
    val retrofitService: F1StoriesApiService by lazy{
        retrofit.create(F1StoriesApiService::class.java)
    }
}

