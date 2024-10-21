package com.testtask.project.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.testtask.project.presentation.detail.DetailContent
import com.testtask.project.presentation.list.ListContent

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Detail -> DetailContent(
                component = child.component,
            )

            is RootComponent.Child.List -> ListContent(
                component = child.component,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}