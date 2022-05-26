package be.howest.nicolas.loontjens.f1stories.network

import be.howest.nicolas.loontjens.f1stories.network.data.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

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

    @POST("users/{id}/race")
    suspend fun addRace(@Path("id") id: Int, @Body race: AddRace, @Header("Authorization") auth: String)

    @POST("stories/{id}/interact")
    suspend fun likeStory(@Path("id") id: Int, @Body interact: AddInteraction, @Header("Authorization") auth:String)

    @GET("stories/{id}/comments")
    suspend fun getComments(@Path("id") id: Int):List<Comment>

    /*
    @Multipart
    @POST("stories")
    suspend fun addStory(@Part("content") content: RequestBody, @Part("country") country: RequestBody,
                         @Part("raceid") raceid: Int,@Part img : MultipartBody.Part, @Header("Authorization") auth: String)
     */

    @Multipart
    @POST("stories")
    suspend fun addStory(@Part("content") content: RequestBody, @Part("country") country: RequestBody,
                         @Part("raceid") raceid: Int,@Header("Authorization") auth: String)
}

object F1StoriesApi {
    val retrofitService: F1StoriesApiService by lazy{
        retrofit.create(F1StoriesApiService::class.java)
    }
}

