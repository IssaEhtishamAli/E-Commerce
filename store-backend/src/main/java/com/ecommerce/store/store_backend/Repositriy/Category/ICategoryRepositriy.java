package com.ecommerce.store.store_backend.Repositriy.Category;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;

import java.util.List;

public interface ICategoryRepositriy{
    mGeneric.mApiResponse<List<mCategory.Category>> findAll();
    mGeneric.mApiResponse<mCategory.Category> findById(int id);
    mGeneric.mApiResponse save(mCategory.Category category);
    mGeneric.mApiResponse update(mCategory.Category category);
    mGeneric.mApiResponse delete(int id);
    mGeneric.mApiResponse<List<mCategory.SubCategory>> getSubcategoriesByCategoryId(int categoryId);
}
