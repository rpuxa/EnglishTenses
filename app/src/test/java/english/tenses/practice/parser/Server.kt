package english.tenses.practice.parser

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Server {

        @GET("translate")
        suspend fun getTranslate(
            @Query("text") text: String,
            @Query("lang") language: String,
            @Query("key") apiKey: String
        ): Translate

        companion object {
            fun create() = Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Server::class.java)
    }
}