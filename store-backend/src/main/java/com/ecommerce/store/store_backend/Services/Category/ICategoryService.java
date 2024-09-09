package com.ecommerce.store.store_backend.Services.Category;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;

import java.util.List;

public interface ICategoryService {
    mGeneric.mApiResponse<List<mCategory.Category>> findAll();
    mGeneric.mApiResponse<mCategory.Category> findById(int id);
    mGeneric.mApiResponse save(mCategory.Category category);
    mGeneric.mApiResponse update(mCategory.Category category);
    mGeneric.mApiResponse delete(int id);
}
