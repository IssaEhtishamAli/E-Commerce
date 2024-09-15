package com.ecommerce.store.store_backend.Controllers;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Services.Category.ICategoryService;
import com.ecommerce.store.store_backend.Services.Subcategory.ISubcategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/subcategory/")
public class SubcategoryController {
    @Autowired
    private ISubcategoryService subcategoryService;

    @Operation(summary = "Add a new subcategory", description = "Adds a new subcategory to the e-commerce system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully"),
            @ApiResponse(responseCode = "404", description = "Category creation failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @RequestMapping(value = "addSubCategory",method = RequestMethod.POST)
    public ResponseEntity<?> addSubCategory(@RequestBody mCategory.SubCategory subcategory) {
        try {
            mGeneric.mApiResponse response = subcategoryService.save(subcategory);
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update an existing subcategory", description = "Updates the details of an existing subcategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "404", description = "Category update failed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @RequestMapping(value = "updateSubCategory",method = RequestMethod.PUT)
    public ResponseEntity<?> updateSubCategory(@RequestBody mCategory.SubCategory subcategory) {
        try {
            mGeneric.mApiResponse response = subcategoryService.update(subcategory);
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all subcategories", description = "Retrieves a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of categories"),
            @ApiResponse(responseCode = "404", description = "Categories not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @RequestMapping(value = "getAllSubCategory",method = RequestMethod.GET)
    public ResponseEntity<?> getAllSubCategories() {
        try {
            mGeneric.mApiResponse response = subcategoryService.findAll();
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get subcategory by ID", description = "Retrieves a category by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved category"),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @RequestMapping(value = "getSubCategoryById",method = RequestMethod.GET)
    public ResponseEntity<?> getSubCategoryById(@RequestParam(value = "id") int id) {
        try {
            mGeneric.mApiResponse response = subcategoryService.findById(id);
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a subcategory", description = "Deletes a subcategory by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @RequestMapping(value = "deleteSubCategory",method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSubCategory(@RequestParam(value = "id") int id) {
        try {
            mGeneric.mApiResponse response = subcategoryService.delete(id);
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
