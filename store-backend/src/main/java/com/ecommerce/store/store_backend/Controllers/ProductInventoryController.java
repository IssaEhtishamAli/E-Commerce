package com.ecommerce.store.store_backend.Controllers;

import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Products.mProduct;
import com.ecommerce.store.store_backend.Services.ProductInventory.IProductInventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class ProductInventoryController {
    @Autowired
    private IProductInventoryService productInventoryService;

    @Operation(summary = "Add inventory change", description = "Adds a new inventory record when stock is adjusted.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory record added successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to add inventory record", content = @Content)
    })    @PostMapping("/add")
    public mGeneric.mApiResponse<mProduct.ProductInventory> addInventory(@RequestBody mProduct.ProductInventory inventory) {
        return productInventoryService.addInventory(inventory);
    }

    @Operation(summary = "Get inventory changes by product ID", description = "Fetches all inventory records for a given product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory records retrieved successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "No inventory records found", content = @Content)
    })
    // Get all inventory changes for a product
    @GetMapping("/product/{productId}")
    public mGeneric.mApiResponse<List<mProduct.ProductInventory>> getInventoryByProductId(@PathVariable int productId) {
        return productInventoryService.getInventoryByProductId(productId);
    }

    @Operation(summary = "Get inventory changes by product ID", description = "Fetches all inventory records for a given product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory records retrieved successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "No inventory records found for this product", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    // Get a specific inventory change by its ID
    @GetMapping("/{inventoryId}")
    public mGeneric.mApiResponse<mProduct.ProductInventory> getInventoryById(@PathVariable int inventoryId) {
        return productInventoryService.getInventoryById(inventoryId);
    }

    @Operation(summary = "Update inventory record", description = "Updates an existing inventory record for a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory record updated successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to update inventory record", content = @Content)
    })
    // Update an existing inventory record
    @PutMapping("/update")
    public mGeneric.mApiResponse<mProduct.ProductInventory> updateInventory(@RequestBody mProduct.ProductInventory inventory) {
        return productInventoryService.updateInventory(inventory);
    }

    @Operation(summary = "Delete inventory record", description = "Deletes an inventory record for a product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventory record deleted successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Failed to delete inventory record", content = @Content)
    })
    // Delete an inventory record by ID
    @DeleteMapping("/delete/{inventoryId}")
    public mGeneric.mApiResponse<String> deleteInventory(@PathVariable int inventoryId) {
        return productInventoryService.deleteInventory(inventoryId);
    }
}
