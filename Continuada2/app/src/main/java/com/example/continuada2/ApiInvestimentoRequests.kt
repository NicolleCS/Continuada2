package com.example.continuada2

import retrofit2.Call
import retrofit2.http.*


interface ApiInvestimentoRequests {

    @GET("/investimentos")
    fun getInvestimentos(): Call<List<Investimento>>

    @GET("/investimentos")
    fun getInvestimentosPorMesEAno(
        @Query("mes") texto: String,
        @Query("ano") ano: String
    ): Call<List<Investimento>?>

    @POST("/investimentos")
    fun postInvestimento(@Body novoInvestimento: Investimento): Call<Void>

    @DELETE("/investimentos/{id}")
    fun deleteInvestimentoPorId(@Path("id") id: Int?): Call<Void>

}