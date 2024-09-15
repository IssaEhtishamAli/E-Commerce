package com.ecommerce.store.store_backend.Services.Subcategory;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;

public interface ISubcategoryService {
    mGeneric.mApiResponse save(mCategory.SubCategory subCategory);
    mGeneric.mApiResponse update(mCategory.SubCategory subCategory);
    mGeneric.mApiResponse delete(int id);
    mGeneric.mApiResponse findById(int id);
    mGeneric.mApiResponse findAll();
}
