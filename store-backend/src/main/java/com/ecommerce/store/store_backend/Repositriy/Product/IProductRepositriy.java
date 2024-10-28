package com.ecommerce.store.store_backend.Repositriy.Product;

import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Products.mProduct;

import java.util.List;

public interface IProductRepositriy {
     mGeneric.mApiResponse<mProduct.Product> save(mProduct.Product product);
     mGeneric.mApiResponse update(mProduct.Product product);
     mGeneric.mApiResponse delete(int productId);
     mGeneric.mApiResponse<mProduct.Product> findByProductId(int productId);
     mGeneric.mApiResponse<List<mProduct.Product>> findById(int subcategoryId, int page, int pageSize);
     mGeneric.mApiResponse<List<mProduct.Product>> findAll();
}
