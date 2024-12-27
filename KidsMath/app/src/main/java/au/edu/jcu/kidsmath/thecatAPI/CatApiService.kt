package au.edu.jcu.kidsmath.thecatAPI

import retrofit2.Call
import retrofit2.http.GET

interface CatApiService {
    @GET("images/search")
    fun getCatImages(): Call<List<CatImageResponse>>
}
