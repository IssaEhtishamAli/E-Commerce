package com.ecommerce.store.store_backend.Mappers;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<mCategory.Category> {
    @Override
    public mCategory.Category mapRow(ResultSet rs,int rowNum) throws SQLException{
        mCategory.Category category = new mCategory.Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("category_name"));
        category.setDescription(rs.getString("description"));
        category.setParentCategoryId(rs.getInt("parent_category_id"));
        return  category;
    }

}
