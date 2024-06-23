import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
//        MainViewControllerKt.MainViewController()
        MainViewControllerKt.MainViewController(
            topSafeArea: 0,
            bottomSafeArea: 0
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea() // Compose has own keyboard handler
    }
}



