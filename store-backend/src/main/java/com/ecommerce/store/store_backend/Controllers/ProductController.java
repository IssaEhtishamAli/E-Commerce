package com.ecommerce.store.store_backend.Controllers;

import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/product/")
//@RequestMapping({"/local-category", "/prod-category"})
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
            @RequestBody mProduct.Product product) {
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
     * Get products by subcategory ID with pagination.
     *
     * @param subcategoryId The ID of the subcategory.
     * @param page The page number for pagination (default is 1).
     * @param pageSize The number of products per page (default is 10).
     * @return A list of products or 404 if not found.
     */
    @Operation(summary = "Get products by subcategory ID", description = "Fetches a list of products by subcategory ID with pagination.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = mProduct.Product.class))),
            @ApiResponse(responseCode = "404", description = "Products not found", content = @Content)
    })
    @GetMapping("/subcategory/{subcategoryId}")
    public ResponseEntity<List<mProduct.Product>> getProductsBySubcategoryId(
            @Parameter(description = "ID of the subcategory to retrieve products from", required = true) @PathVariable int subcategoryId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<mProduct.Product> products = productService.findById(subcategoryId, page, pageSize).getRespData();
        return products != null && !products.isEmpty() ? ResponseEntity.ok(products) : ResponseEntity.notFound().build();
    }
    // Fetch a product by productId
    @Operation(summary = "Get product by product ID", description = "Fetches a product by product ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{productId}")
    public ResponseEntity<mGeneric.mApiResponse<mProduct.Product>> getProductById(@PathVariable("productId") int productId) {
        mGeneric.mApiResponse<mProduct.Product> response = productService.findByProductId(productId);

        if (response.getRespCode() == 1) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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