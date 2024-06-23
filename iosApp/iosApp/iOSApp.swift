import SwiftUI
import GoogleSignIn
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        let appComponent = IosApplicationComponent()
        let initKoinIosClassInstance = initKoinIosClass()
        initKoinIosClassInstance.doInitKoinIos(appComponent: appComponent)
    }
    
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
        WindowGroup {
            ContentView().onOpenURL { url in
                GIDSignIn.sharedInstance.handle(url)
            }
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    
    func application(
        _ app: UIApplication,
        open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]
    ) -> Bool {
        var handled: Bool
        
        // Let Google Sign-In handle the URL if it's related to Google Sign-In
        handled = GIDSignIn.sharedInstance.handle(url)
        if handled {
            return true
        }
        
        // Handle other custom URL types
        
        // If not handled by this app, return false
        return false
    }
}
