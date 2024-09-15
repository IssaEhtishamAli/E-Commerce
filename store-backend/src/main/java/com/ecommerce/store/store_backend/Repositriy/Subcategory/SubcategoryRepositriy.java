package com.ecommerce.store.store_backend.Repositriy.Subcategory;

import com.ecommerce.store.store_backend.Mappers.SubCategoryRowMapper;
import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class SubcategoryRepositriy implements ISubCategoryRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // SQL Queries
    private final String INSERT_SUBCATEGORY = "INSERT INTO subcategories (subcategory_name, description, category_id) VALUES (?, ?, ?)";
    private final String UPDATE_SUBCATEGORY = "UPDATE subcategories SET subcategory_name = ?, description = ?, category_id = ? WHERE subcategory_id = ?";
    private final String DELETE_SUBCATEGORY = "DELETE FROM subcategories WHERE subcategory_id = ?";
    private final String FIND_SUBCATEGORY_BY_ID = "SELECT * FROM subcategories WHERE subcategory_id = ?";
    private final String FIND_ALL_SUBCATEGORIES = "SELECT * FROM subcategories";

    @Override
    public mGeneric.mApiResponse save(mCategory.SubCategory subCategory) {
        try {
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_SUBCATEGORY);
                ps.setString(1, subCategory.getSubcategoryName());
                ps.setString(2, subCategory.getDescription());
                ps.setInt(3, subCategory.getCategoryId());
                return ps;
            });
            if (rowsAffected > 0) {
                return new mGeneric.mApiResponse(1, "SubCategory saved successfully", subCategory);
            } else {
                return new mGeneric.mApiResponse(0, "Failed to save SubCategory");
            }
        } catch (Exception ex) {
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse update(mCategory.SubCategory subCategory) {
        try {
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(UPDATE_SUBCATEGORY);
                ps.setString(1, subCategory.getSubcategoryName());
                ps.setString(2, subCategory.getDescription());
                ps.setInt(3, subCategory.getCategoryId());
                ps.setInt(4, subCategory.getSubcategoryId());
                return ps;
            });
            if (rowsAffected > 0) {
                return new mGeneric.mApiResponse(1, "SubCategory updated successfully", subCategory);
            } else {
                return new mGeneric.mApiResponse(0, "Failed to update SubCategory");
            }
        } catch (Exception ex) {
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse delete(int id) {
        try {
            int rowsAffected = jdbcTemplate.update(DELETE_SUBCATEGORY, id);
            if (rowsAffected > 0) {
                return new mGeneric.mApiResponse(1, "SubCategory deleted successfully");
            } else {
                return new mGeneric.mApiResponse(0, "Failed to delete SubCategory");
            }
        } catch (Exception ex) {
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse findById(int id) {
        try {
            mCategory.SubCategory subCategory = jdbcTemplate.queryForObject(FIND_SUBCATEGORY_BY_ID, new Object[]{id}, new SubCategoryRowMapper());
            return new mGeneric.mApiResponse(1, "SubCategory found", subCategory);
        } catch (Exception ex) {
            return new mGeneric.mApiResponse(0, "SubCategory not found");
        }
    }

    @Override
    public mGeneric.mApiResponse findAll() {
        try {
            List<mCategory.SubCategory> subCategories = jdbcTemplate.query(FIND_ALL_SUBCATEGORIES, new SubCategoryRowMapper());
            return new mGeneric.mApiResponse(1, "SubCategories found", subCategories);
        } catch (Exception ex) {
            return new mGeneric.mApiResponse(0, "No SubCategories found");
        }
    }
}
