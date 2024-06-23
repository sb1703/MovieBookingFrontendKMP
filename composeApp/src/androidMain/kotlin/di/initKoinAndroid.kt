package di

import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import platform.AndroidApplicationComponent

fun initKoinAndroid(
    appComponent: AndroidApplicationComponent,
    appDeclaration: KoinAppDeclaration = {}
) {
    initKoin(
        listOf(module { single { appComponent } }),
        appDeclaration
    )
}