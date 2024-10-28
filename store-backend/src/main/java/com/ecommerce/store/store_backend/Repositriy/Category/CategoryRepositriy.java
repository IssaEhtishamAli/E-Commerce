package com.ecommerce.store.store_backend.Repositriy.Category;

import com.ecommerce.store.store_backend.Mappers.CategoryMapper;
import com.ecommerce.store.store_backend.Mappers.SubCategoryRowMapper;
import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class CategoryRepositriy implements ICategoryRepositriy{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String SELECT_ALL_CATEGORIES = "SELECT c.category_id, c.category_name, c.description, " +
            "sc.subcategory_id, sc.subcategory_name, sc.description as subcategory_description " +
            "FROM categories c " +
            "LEFT JOIN subcategories sc ON c.category_id = sc.category_id " +
            "WHERE c.is_active = true";
    private final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE category_id = ?";
    private final String INSERT_CATEGORY ="INSERT INTO categories (category_name, description) VALUES (?, ?)";
    private final String UPDATE_CATEGORY = "UPDATE categories SET category_name = ?, description = ? WHERE category_id = ?";
    private final String DELETE_CATEGORY ="DELETE FROM categories WHERE category_id = ?";
    private final String SELECT_SUBCATEGORY_BY_ID ="SELECT * FROM subcategories WHERE category_id = ?";
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
                return new mGeneric.mApiResponse(0, "Failed to fetch category");
            }
            else{
                return new mGeneric.mApiResponse(1,"Success",response);
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse save(mCategory.Category category) {
        try {
            GeneratedKeyHolder gn = new GeneratedKeyHolder();
            int rowsAffected =  jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_CATEGORY);
                ps.setString(1, category.getCategoryName());
                ps.setString(2, category.getDescription());
                return ps;
            },gn);
            System.out.print("<-----------PreparedStatement---------->"+gn.getKeyList());
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
            int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(UPDATE_CATEGORY);
                ps.setString(1, category.getCategoryName()); // Set category name first
                ps.setString(2, category.getDescription());  // Set description second
                ps.setInt(3, category.getCategoryId());      // Set category id for the WHERE clause
                return ps;
            });

            return rowsAffected > 0
                    ? new mGeneric.mApiResponse(1, "Category updated successfully", category)
                    : new mGeneric.mApiResponse(0, "Failed to update record");
//            if(rowsAffected >0){
//                return new mGeneric.mApiResponse(0,"Falied to update record");
//            }
//            else{
//                return new mGeneric.mApiResponse(1,"Category updated successfully",category);
        } catch (Exception ex) {
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
    public mGeneric.mApiResponse<List<mCategory.SubCategory>> getSubcategoriesByCategoryId(int categoryId) {
        try {
            List<mCategory.SubCategory> response = jdbcTemplate.query(SELECT_SUBCATEGORY_BY_ID, new SubCategoryRowMapper(), categoryId);
            if(response==null){
                return new mGeneric.mApiResponse(0, "Not FOund!",HttpStatus.NOT_FOUND);
            }else{
                return  new mGeneric.mApiResponse(1,"Success",HttpStatus.OK);
            }
        }catch (Exception ex){
            return  new mGeneric.mApiResponse(0,ex.getMessage());
        }
    }
}
