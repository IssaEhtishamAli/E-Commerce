package com.ecommerce.store.store_backend.Services.Product;

import com.ecommerce.store.store_backend.GlobalExceptionHandling.ResourceNotFoundException;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Products.mProduct;
import com.ecommerce.store.store_backend.Repositriy.Product.IProductRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepositriy productRepositriy;

    @Override
    public mGeneric.mApiResponse<mProduct.Product> save(mProduct.Product product) {
        return productRepositriy.save(product);
    }

    @Override
    public mGeneric.mApiResponse update(mProduct.Product product) {
        return productRepositriy.update(product);
    }

    @Override
    public mGeneric.mApiResponse delete(int productId) {
        return productRepositriy.delete(productId);
    }

    @Override
    public mGeneric.mApiResponse<List<mProduct.Product>> findById(int subcategoryId, int page, int pageSize) {
        return productRepositriy.findById(subcategoryId,page,pageSize);
    }
    @Override
    public mGeneric.mApiResponse<mProduct.Product> findByProductId(int productId){
        return productRepositriy.findByProductId(productId);
    }
    @Override
    public mGeneric.mApiResponse<List<mProduct.Product>> findAll() {
        return productRepositriy.findAll();
    }
}
