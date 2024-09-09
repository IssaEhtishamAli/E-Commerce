package com.ecommerce.store.store_backend.Controllers;

import com.ecommerce.store.store_backend.Models.Products.mProduct;
import com.ecommerce.store.store_backend.Services.Product.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/product/")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {
    @Autowired
    private ProductService productService;

    // Create a new product
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody mProduct.Product product) {
        int result = productService.save(product).getRespCode();
        return result > 0 ? ResponseEntity.ok("Product created successfully") : ResponseEntity.badRequest().body("Product creation failed");
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestBody mProduct.Product product) {
        product.setProductId(id);
        int result = productService.update(product).getRespCode();
        return result > 0 ? ResponseEntity.ok("Product updated successfully") : ResponseEntity.badRequest().body("Product update failed");
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        int result = productService.delete(id).getRespCode();
        return result > 0 ? ResponseEntity.ok("Product deleted successfully") : ResponseEntity.badRequest().body("Product deletion failed");
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<mProduct.Product> getProductById(@PathVariable int id) {
        mProduct.Product product = productService.findById(id).getRespData();
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    /**
     * Get all products.
     * @Author
     * @return List of products.
     */
    // Get all products
    @GetMapping
    public ResponseEntity<List<mProduct.Product>> getAllProducts() {
        List<mProduct.Product> products = productService.findAll().getRespData();
        return ResponseEntity.ok(products);
    }

}
