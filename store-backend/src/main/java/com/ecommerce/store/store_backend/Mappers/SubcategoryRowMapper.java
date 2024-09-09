package com.ecommerce.store.store_backend.Mappers;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubcategoryRowMapper implements RowMapper<mCategory.SubCategory>{
    @Override
    public mCategory.SubCategory mapRow(ResultSet rs,int rowNum)throws SQLException {
        mCategory.SubCategory subCategory = new mCategory.SubCategory();
        subCategory.setCategoryId(rs.getInt("subcategoryId"));
        subCategory.setSubcategoryId(rs.getInt("subcategoryId"));
        subCategory.setSubcategoryName(rs.getString("subcategoryName"));
        subCategory.setDescription(rs.getString("subcategoryName"));
        return subCategory;
    }
}
