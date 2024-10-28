package com.ecommerce.store.store_backend.Repositriy.ProductInventory;

import com.ecommerce.store.store_backend.Mappers.ProductInventoryRowMapper;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Products.mProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ProductInventoryRepositriyImpl implements IProductInventoryRepositriy {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // SQL Queries
    private final String INSERT_INVENTORY = "INSERT INTO product_inventory (product_id, user_id, change_amount, change_reason, change_date, remaining_stock) VALUES (?, ?, ?, ?, ?, ?)";
    private final String SELECT_INVENTORY_BY_PRODUCT = "SELECT * FROM product_inventory WHERE product_id = ?";
    private final String SELECT_INVENTORY_BY_ID = "SELECT * FROM product_inventory WHERE inventory_id = ?";
    private final String UPDATE_INVENTORY = "UPDATE product_inventory SET product_id = ?, user_id = ?, change_amount = ?, change_reason = ?, change_date = ?, remaining_stock = ? WHERE inventory_id = ?";
    private final String DELETE_INVENTORY = "DELETE FROM product_inventory WHERE inventory_id = ?";

    @Override
    public mGeneric.mApiResponse addInventory(mProduct.ProductInventory inventory) {
        try {
            jdbcTemplate.update(INSERT_INVENTORY,
                    inventory.getProductId(),
                    inventory.getUserId(),
                    inventory.getChangeAmount(),
                    inventory.getChangeReason(),
                    new Timestamp(System.currentTimeMillis()),
                    inventory.getRemainingStock()
            );
            return new mGeneric.mApiResponse<>(1, "Inventory added!", inventory);
        } catch (Exception ex) {
            return new mGeneric.mApiResponse<>(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse getInventoryByProductId(int productId) {
        try {
            List<mProduct.ProductInventory> inventoryList = jdbcTemplate.query(SELECT_INVENTORY_BY_PRODUCT, new ProductInventoryRowMapper(), productId);
            // Check if inventory list has more than 0 records
            if (inventoryList.size() > 0) {
                return new mGeneric.mApiResponse(1, "Inventory fetched!", inventoryList);
            } else {
                return new mGeneric.mApiResponse<>(0, "No inventory records found for this product.");
            }
        } catch (Exception ex) {
            return new mGeneric.mApiResponse<>(0, ex.getMessage());
        }
    }
    @Override
    public mGeneric.mApiResponse<mProduct.ProductInventory> getInventoryById(int inventoryId) {
        try {
            mProduct.ProductInventory inventory = jdbcTemplate.queryForObject(SELECT_INVENTORY_BY_ID, new ProductInventoryRowMapper(), inventoryId);
            return new mGeneric.mApiResponse<>(1, "Inventory fetched!", inventory);
        } catch (Exception ex) {
            return new mGeneric.mApiResponse<>(0, ex.getMessage());
        }
    }
    @Override
    public mGeneric.mApiResponse<mProduct.ProductInventory> updateInventory(mProduct.ProductInventory inventory) {
        try {
            int rowsAffected = jdbcTemplate.update(UPDATE_INVENTORY,
                    inventory.getProductId(),
                    inventory.getUserId(),
                    inventory.getChangeAmount(),
                    inventory.getChangeReason(),
                    inventory.getChangeDate(),
                    inventory.getRemainingStock(),
                    inventory.getInventoryId()
            );

            // Check if any rows were affected
            if (rowsAffected > 0) {
                System.out.println("Rows affected in inventory update: " + rowsAffected);
                return new mGeneric.mApiResponse<>(1, "Inventory updated!", inventory);
            } else {
                // No rows were affected, throw a custom exception
                throw new RuntimeException("No inventory records were updated. Please check the inventory_id and product_id.");
            }
        } catch (Exception ex) {
            // Catch any exception and return the error message
            return new mGeneric.mApiResponse<>(0, "Error updating inventory: " + ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse deleteInventory(int inventoryId) {
        try {
            jdbcTemplate.update(DELETE_INVENTORY, inventoryId);
            return new mGeneric.mApiResponse<>(1, "Inventory deleted!");
        } catch (Exception ex) {
            return new mGeneric.mApiResponse<>(0, ex.getMessage());
        }
    }
}
