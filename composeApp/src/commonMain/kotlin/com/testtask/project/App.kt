package com.testtask.project

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.testtask.project.presentation.root.RootComponent
import com.testtask.project.presentation.root.RootContent
import com.testtask.project.theme.AppTheme


@Composable
fun App(rootComponent: RootComponent) = AppTheme {

        RootContent(
            component = rootComponent,
            modifier = Modifier.fillMaxSize(),
        )
}
