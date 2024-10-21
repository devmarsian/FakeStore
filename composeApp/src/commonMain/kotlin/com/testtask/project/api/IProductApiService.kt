package com.testtask.project.api

import com.testtask.project.data.ProductsItem

interface IProductApiService {
    suspend fun fetchProducts(): List<ProductsItem>
}