package di

import org.koin.dsl.module
import platform.IosApplicationComponent
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(swiftName = "initKoinIosClass")
class initKoinIosClass {
    @ObjCName(swiftName = "initKoinIos")
    fun initKoinIos(
        @ObjCName(swiftName = "appComponent")
        appComponent: IosApplicationComponent
    ) {
        initKoin(
            listOf(module { single { appComponent } })
        )
    }
}