package com.ecommerce.store.store_backend.Mappers;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<mCategory.Category> {
    @Override
    public mCategory.Category mapRow(ResultSet rs,int rowNum) throws SQLException{
        mCategory.Category category = new mCategory.Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        return  category;
    }

}