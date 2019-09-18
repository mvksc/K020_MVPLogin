package m.vk.k020_mvplogin

import android.app.Application
import m.vk.k020_mvplogin.manager.Contexttor

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Contexttor.getInstance().init(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        Contexttor.getInstance().clear()
    }
}