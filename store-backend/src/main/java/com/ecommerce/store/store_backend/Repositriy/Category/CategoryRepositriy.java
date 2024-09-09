package com.ecommerce.store.store_backend.Repositriy.Category;

import com.ecommerce.store.store_backend.Mappers.CategoryMapper;
import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositriy implements ICategoryRepositriy{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    private final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id = ?";
    private final String INSERT_CATEGORY ="INSERT INTO categories (name, description) VALUES (?, ?)";
    private final String UPDATE_CATEGORY ="UPDATE categories SET name = ?, description = ? WHERE id = ?";
    private final String DELETE_CATEGORY ="DELETE FROM categories WHERE id = ?";

    @Override
    public mGeneric.mApiResponse<List<mCategory.Category>> findAll() {
        try {
            List<mCategory.Category> categoryResponse = jdbcTemplate.query(SELECT_ALL_CATEGORIES,new CategoryMapper());
            if (categoryResponse==null){
                return new mGeneric.mApiResponse(0, "Failed to fetch category!");
            }
            else{
                return new mGeneric.mApiResponse(1, "Success",categoryResponse);
            }

        }catch (Exception ex){
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse<mCategory.Category> findById(int id) {
        try {
            mCategory.Category response = jdbcTemplate.queryForObject(SELECT_CATEGORY_BY_ID,new Object[]{id},new CategoryMapper());
            if (response==null){
                return new mGeneric.mApiResponse(1,"Success",response);
            }
            else{
                return new mGeneric.mApiResponse(0, "Failed to save category");
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse save(mCategory.Category category) {
        try {
            int rowsAffected = jdbcTemplate.update(INSERT_CATEGORY,category.getName(),category.getDescription());
            if (rowsAffected > 0) {
                return new mGeneric.mApiResponse(1, "Category saved successfully", category);
            } else {
                return new mGeneric.mApiResponse(0, "Failed to save category");
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse(0,ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse update(mCategory.Category category) {
        try {
            mCategory.Category rowsAffected = jdbcTemplate.queryForObject(UPDATE_CATEGORY,new CategoryMapper());
            if(rowsAffected==null){
                return new mGeneric.mApiResponse(0,"Falied to update record");
            }
            else{
                return new mGeneric.mApiResponse(1,"Category updated successfully",category);
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse delete(int id) {
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_CATEGORY,id);
            if (rowsAffected > 0){
                return new mGeneric.mApiResponse(1,"Record deleted",id);
            }
            else{
                return new mGeneric.mApiResponse(0,"Falied to update record");
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }
}
