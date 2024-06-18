package com.example.passidapp

import com.example.passidapp.network.RetrofitInstance
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceTest {

    @Test
    fun testRetrofitInstanceNotNull() {
        val retrofitInstance = RetrofitInstance.api
        assertNotNull(retrofitInstance)
    }

    @Test
    fun testRetrofitCreation() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val baseUrl = retrofit.baseUrl().toString()
        println("Base URL: $baseUrl")  // Добавлено для отладки
        assertEquals("http://10.0.2.2:8000/", baseUrl)
    }

}
