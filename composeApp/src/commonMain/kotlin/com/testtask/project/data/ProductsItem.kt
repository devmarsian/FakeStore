package com.testtask.project.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductsItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)