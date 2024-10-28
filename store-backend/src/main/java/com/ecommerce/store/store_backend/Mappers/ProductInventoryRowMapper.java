package com.ecommerce.store.store_backend.Mappers;

import com.ecommerce.store.store_backend.Models.Products.mProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ProductInventoryRowMapper implements RowMapper<mProduct.ProductInventory> {
    public mProduct.ProductInventory mapRow(ResultSet rs, int rowNum) throws SQLException {
        mProduct.ProductInventory productInventory = new mProduct.ProductInventory();
        productInventory.setInventoryId(rs.getInt("inventory_id"));
        productInventory.setProductId(rs.getInt("product_id"));
        productInventory.setUserId(rs.getInt("user_id"));
        productInventory.setChangeAmount(rs.getInt("change_amount"));
        Timestamp timestamp = rs.getTimestamp("change_date");
        if (timestamp != null ) {
            productInventory.setChangeDate(timestamp);
        } else {
            productInventory.setChangeDate(null);// or handle default value
        }
        productInventory.setRemainingStock(rs.getInt("remaining_stock"));
        return productInventory;
    }
}
