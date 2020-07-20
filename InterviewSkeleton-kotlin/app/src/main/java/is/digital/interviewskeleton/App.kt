package `is`.digital.interviewskeleton

import `is`.digital.interviewskeleton.BuildConfig.*
import `is`.digital.interviewskeleton.data.TodoRepository
import `is`.digital.interviewskeleton.data.TodoService
import android.app.Application
import android.content.Context
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDex
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


internal class App : Application() {

    lateinit var okHttpClient: OkHttpClient
    lateinit var retrofit: Retrofit;
    lateinit var todoService: TodoService;
    lateinit var todoRepository: TodoRepository

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        setupDependencies()
    }


    private fun setupDependencies() {
        okHttpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    var request = chain.request()
                    request = request.newBuilder()
                            .addHeader("Authorization", "Bearer $BEARER_TOKEN")
                            .build()
                    chain.proceed(request)
                })
                .build()
        retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .baseUrl(QuickUtils.HOST)
                .build()
        todoService = retrofit.create(TodoService::class.java)
        todoRepository = TodoRepository(todoService)
    }

}
