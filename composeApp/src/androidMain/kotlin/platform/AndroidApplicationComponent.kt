package platform

import android.app.Application
import di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidApplicationComponent

fun application(
    component: AndroidApplicationComponent
) {

}

//override fun onCreate() {
//    super.onCreate()
//
//    startKoin {
//        androidContext(this@AndroidApplicationComponent)
//        androidLogger()
//
//        modules(commonModule)
//    }
//}