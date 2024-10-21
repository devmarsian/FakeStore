package com.testtask.project.api

import com.testtask.project.data.ProductsItem

interface ProductRepository {
    suspend fun fetchProducts(): List<ProductsItem>
}


class ProductRepositoryImpl(private val productApiService: IProductApiService) : ProductRepository {
    override suspend fun fetchProducts(): List<ProductsItem> {
        return productApiService.fetchProducts()
    }
}