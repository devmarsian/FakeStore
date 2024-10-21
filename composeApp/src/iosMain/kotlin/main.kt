import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.testtask.project.App
import com.testtask.project.presentation.root.DefaultRootComponent
import com.testtask.project.presentation.root.RootComponent
import platform.UIKit.UIViewController

fun MainViewController() = ComposeUIViewController {
    val root: RootComponent = remember {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(LifecycleRegistry()),
        )
    }
    App(root)
}