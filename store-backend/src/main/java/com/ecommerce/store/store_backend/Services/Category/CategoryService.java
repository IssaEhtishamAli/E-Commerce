package com.ecommerce.store.store_backend.Services.Category;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Repositriy.Category.ICategoryRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private ICategoryRepositriy categoryRepositriy;
    @Override
    public mGeneric.mApiResponse<List<mCategory.Category>> findAll() {
        return categoryRepositriy.findAll();
    }

    @Override
    public mGeneric.mApiResponse<mCategory.Category> findById(int id) {
        return categoryRepositriy.findById(id);
    }

    @Override
    public mGeneric.mApiResponse save(mCategory.Category category) {
        return categoryRepositriy.save(category);
    }

    @Override
    public mGeneric.mApiResponse update(mCategory.Category category) {
        return categoryRepositriy.update(category);
    }

    @Override
    public mGeneric.mApiResponse delete(int id) {
        return categoryRepositriy.delete(id);
    }
}
