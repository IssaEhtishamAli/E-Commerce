package com.ecommerce.store.store_backend.Controllers;

import com.ecommerce.store.store_backend.Models.Products.mProduct;
import com.ecommerce.store.store_backend.Services.Product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/product/")
@Tag(name = "Products", description = "E-Commerce Store")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * Create a new product.
     *
     * @param product Product details for the new product.
     * @return Success or failure message.
     */
    @Operation(summary = "Create a new product", description = "Adds a new product to the e-commerce store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Product creation failed", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> createProduct(
            @RequestBody @Schema(description = "Details of the product to be created", required = true) mProduct.Product product) {
        int result = productService.save(product).getRespCode();
        return result > 0 ? ResponseEntity.ok("Product created successfully") : ResponseEntity.badRequest().body("Product creation failed");
    }

    /**
     * Update an existing product.
     *
     * @param id The ID of the product to update.
     * @param product Updated product details.
     * @return Success or failure message.
     */
    @Operation(summary = "Update a product", description = "Updates the details of an existing product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Product update failed", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(
            @Parameter(description = "ID of the product to be updated", required = true) @PathVariable int id,
            @RequestBody @Schema(description = "Updated details of the product", required = true) mProduct.Product product) {
        product.setProductId(id);
        int result = productService.update(product).getRespCode();
        return result > 0 ? ResponseEntity.ok("Product updated successfully") : ResponseEntity.badRequest().body("Product update failed");
    }

    /**
     * Delete a product.
     *
     * @param id The ID of the product to delete.
     * @return Success or failure message.
     */
    @Operation(summary = "Delete a product", description = "Deletes a product from the store by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Product deletion failed", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @Parameter(description = "ID of the product to be deleted", required = true) @PathVariable int id) {
        int result = productService.delete(id).getRespCode();
        return result > 0 ? ResponseEntity.ok("Product deleted successfully") : ResponseEntity.badRequest().body("Product deletion failed");
    }

    /**
     * Get product by ID.
     *
     * @param id The ID of the product.
     * @return Product details or 404 if not found.
     */
    @Operation(summary = "Get product by ID", description = "Fetches the details of a product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = mProduct.Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<mProduct.Product> getProductById(
            @Parameter(description = "ID of the product to retrieve", required = true) @PathVariable int id) {
        mProduct.Product product = productService.findById(id).getRespData();
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    /**
     * Get all products.
     *
     * @return List of all products.
     */
//    @Operation(summary = "Get all products", description = "Fetches a list of all products in the store.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "List of products",
//                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = mProduct.Product.class)))
//    })
    @GetMapping
    public ResponseEntity<List<mProduct.Product>> getAllProducts() {
        List<mProduct.Product> products = productService.findAll().getRespData();
        return ResponseEntity.ok(products);
    }
}