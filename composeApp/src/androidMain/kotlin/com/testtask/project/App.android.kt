package com.testtask.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.retainedComponent
import com.testtask.project.presentation.root.DefaultRootComponent
import com.testtask.project.presentation.root.RootComponent

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val root: RootComponent = retainedComponent {
            DefaultRootComponent(it)
        }

        setContent {
            App(root)
        }
    }
}

