package com.ecommerce.store.store_backend.Mappers;

import com.ecommerce.store.store_backend.Models.Products.mProduct;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<mProduct.Product> {
    @Override
    public mProduct.Product mapRow(ResultSet rs, int rowNum) throws SQLException{
        mProduct.Product product = new mProduct.Product();
        product.setProductId(rs.getInt("productId"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setStockQuantity(rs.getInt("stockQuantity"));
        product.setSubcategory_id(rs.getInt("subcategory_id"));
        return product;
    }
}
