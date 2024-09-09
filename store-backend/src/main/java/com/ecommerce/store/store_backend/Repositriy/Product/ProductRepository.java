package com.ecommerce.store.store_backend.Repositriy.Product;

import com.ecommerce.store.store_backend.Mappers.ProductRowMapper;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Products.mProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public class ProductRepository implements  IProductRepositriy{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);
    private final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    private final String SELECT_PRODUCT_BY_ID = "SELECT * FROM products WHERE product_id = ?";
    private final String INSERT_PRODUCT = "INSERT INTO products (name, description, price, stock_quantity, category_id) VALUES (?, ?, ?, ?, ?)";
    private final String UPDATE_PRODUCT = "UPDATE products SET name = ?, description = ?, price = ?, stock_quantity = ? WHERE product_id = ?";
    private final String DELETE_PRODUCT = "DELETE FROM products WHERE product_id = ?";

    @Override
    public mGeneric.mApiResponse<mProduct.Product> save(mProduct.Product product) {
        try {
            int rowsAffected = jdbcTemplate.update(INSERT_PRODUCT,new ProductRowMapper());
            if (rowsAffected > 0){
                return new mGeneric.mApiResponse<>(1,"Record saved!",product);
            }
            else {
                return new mGeneric.mApiResponse<>(0,"Failed to saved!");
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse<>(0,ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse update(mProduct.Product product) {
        try {
            int rowsAffected = jdbcTemplate.update(UPDATE_PRODUCT,new ProductRowMapper());
            if(rowsAffected > 0){
                return new mGeneric.mApiResponse<>(1,"Record saved!",product);
            }else{
                return new mGeneric.mApiResponse<>(0,"Failed to update!");
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse<>(0,ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse delete(int productId) {
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_PRODUCT,productId);
            if (rowsAffected >0){
                return new mGeneric.mApiResponse(1,"Record Deleted",productId);
            }
            else{
                return new mGeneric.mApiResponse(0,"Failed to delete");
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse<>(0,ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse<mProduct.Product> findById(int productId) {
        try {
            int rowsAffected = jdbcTemplate.update(SELECT_PRODUCT_BY_ID,productId);
            if (rowsAffected >0){
                return new mGeneric.mApiResponse(1,"Fetched record",productId);
            }
            else{
                return  new mGeneric.mApiResponse<>(0,"Failed to fetched record");
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse<>(0,ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse<List<mProduct.Product>> findAll() {
        try {
            List<mProduct.Product> response = jdbcTemplate.query(SELECT_ALL_PRODUCTS,new ProductRowMapper());
            if (response==null){
                return new mGeneric.mApiResponse<>(0,"Failed to fetched record");
            }
            else{
                return  new mGeneric.mApiResponse<>(1,"Fetched record",response);
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse<>(0,ex.getMessage());
        }
    }

    // Insert a new product


}