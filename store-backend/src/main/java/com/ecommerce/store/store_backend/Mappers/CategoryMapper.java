package com.ecommerce.store.store_backend.Mappers;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryMapper implements RowMapper<mCategory.Category> {
    @Override
    public mCategory.Category mapRow(ResultSet rs,int rowNum) throws SQLException{
        mCategory.Category category = new mCategory.Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setDescription(rs.getString("description"));

        // Assuming subcategories come in the same result set
        List<mCategory.SubCategory> subcategories = new ArrayList<>();
        do {
            if (rs.getInt("subcategory_id") != 0) { // Check if there are subcategories
                mCategory.SubCategory subcategory = new mCategory.SubCategory();
                subcategory.setSubcategoryId(rs.getInt("subcategory_id"));
                subcategory.setSubcategoryName(rs.getString("subcategory_name"));
                subcategory.setDescription(rs.getString("subcategory_description"));
                subcategory.setCategoryId(rs.getInt("category_id")); // Foreign Key reference

                subcategories.add(subcategory);
            }
        } while (rs.next() && rs.getInt("category_id") == category.getCategoryId()); // Continue for the same category

        category.setSubcategories(subcategories);
        return category;
    }
}