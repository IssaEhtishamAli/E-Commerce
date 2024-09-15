package com.ecommerce.store.store_backend.Mappers;


import com.ecommerce.store.store_backend.Models.Category.mCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubCategoryRowMapper  implements RowMapper<mCategory.SubCategory> {
    @Override
    public  mCategory.SubCategory mapRow(ResultSet rs,int rowNum)throws SQLException{
        mCategory.SubCategory subCategory = new mCategory.SubCategory();
        return new mCategory.SubCategory(rs.getInt("subcategory_id"),
                rs.getString("subcategory_name"),
                rs.getString("description"),
                rs.getInt("category_id"));

    }
}
