package com.ecommerce.store.store_backend.Services.Subcategory;

import com.ecommerce.store.store_backend.Models.Category.mCategory;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Repositriy.Subcategory.ISubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryService implements ISubcategoryService{
    @Autowired
    private ISubCategoryRepository subCategoryRepository;
    @Override
    public mGeneric.mApiResponse save(mCategory.SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    @Override
    public mGeneric.mApiResponse update(mCategory.SubCategory subCategory) {
        return subCategoryRepository.update(subCategory);
    }

    @Override
    public mGeneric.mApiResponse delete(int id) {
        return subCategoryRepository.delete(id);
    }

    @Override
    public mGeneric.mApiResponse findById(int id) {
        return subCategoryRepository.findById(id);
    }

    @Override
    public mGeneric.mApiResponse findAll() {
        return subCategoryRepository.findAll();
    }
}
