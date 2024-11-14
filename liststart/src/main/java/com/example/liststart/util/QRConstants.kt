package com.example.liststart.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.liststart.service.DispatchAPIService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QRConstants {

    private const val BASE_URL = "http://192.168.219.195:8787/android/api/"

    // GSON 인스턴스 (setLenient 추가)
    private val gson = GsonBuilder()
        .setLenient()  // 이 옵션을 추가하여 JSON 파싱을 더 유연하게 합니다.
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // '/'로 끝나는지 확인
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // DispatchAPIService 인스턴스를 생성
    val dispatchApiService: DispatchAPIService by lazy {
        retrofit.create(DispatchAPIService::class.java)
    }

    // Retrofit 객체를 동적으로 가져오고 싶은 경우
    fun createRetrofit(baseUrl: String = BASE_URL): DispatchAPIService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DispatchAPIService::class.java)
    }

    // 인터넷 연결 상태 확인
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }
}
