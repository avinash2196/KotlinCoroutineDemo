package org.demo.model

import org.springframework.data.relational.core.mapping.Table

@Table("PRODUCTS")
data class Products(
    var id: Int = 0,
    var name: String = "",
    var price: Float = 0.0f
)