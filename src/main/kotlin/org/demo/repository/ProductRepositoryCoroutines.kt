package org.demo.repository;


import org.demo.model.Products
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository

import org.springframework.stereotype.Repository


@Repository
interface ProductRepositoryCoroutines: ReactiveCrudRepository<Products, Long> {
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProductById(id: Int): Products?
    @Query("INSERT INTO products (name, price) VALUES(:name, :price)")
    suspend fun addNewProduct(name: String, price: Float)
    @FlowPreview
    @Query("select p.* from products p")
    fun getAllProducts(): Flow<Products>
 }