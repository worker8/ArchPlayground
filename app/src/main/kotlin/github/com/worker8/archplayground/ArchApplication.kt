package github.com.worker8.archplayground

import android.app.Application
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.interceptors.LogRequestAsCurlInterceptor

class ArchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            FuelManager.instance.addRequestInterceptor(LogRequestAsCurlInterceptor)
        }
    }
}
