package org.demo.model
class ProductStockView(products: Products, var stockQuantity: Int) {
    var id: Int = 0
    var name: String = ""
    var price: Float = 0.0f

    init {
        this.id = products.id
        this.name = products.name
        this.price = products.price
    }
}