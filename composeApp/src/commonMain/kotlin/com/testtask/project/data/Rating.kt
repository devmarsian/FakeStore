package com.testtask.project.data

import kotlinx.serialization.Serializable


@Serializable
data class Rating(
    val count: Int,
    val rate: Double
)