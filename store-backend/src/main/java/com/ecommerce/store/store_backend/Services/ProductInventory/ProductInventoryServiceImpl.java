package com.ecommerce.store.store_backend.Services.ProductInventory;

import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Products.mProduct;
import com.ecommerce.store.store_backend.Repositriy.ProductInventory.IProductInventoryRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductInventoryServiceImpl implements IProductInventoryService{
    @Autowired
    private IProductInventoryRepositriy productInventoryRepositriy;
    @Override
    public mGeneric.mApiResponse addInventory(mProduct.ProductInventory inventory) {
        return productInventoryRepositriy.addInventory(inventory);
    }

    @Override
    public mGeneric.mApiResponse getInventoryByProductId(int productId) {
        return productInventoryRepositriy.getInventoryByProductId(productId);
    }

    @Override
    public mGeneric.mApiResponse<mProduct.ProductInventory> getInventoryById(int inventoryId) {
        return productInventoryRepositriy.getInventoryById(inventoryId);
    }

    @Override
    public mGeneric.mApiResponse<mProduct.ProductInventory> updateInventory(mProduct.ProductInventory inventory) {
        return productInventoryRepositriy.updateInventory(inventory);
    }

    @Override
    public mGeneric.mApiResponse deleteInventory(int inventoryId) {
        return productInventoryRepositriy.deleteInventory(inventoryId);
    }
}
