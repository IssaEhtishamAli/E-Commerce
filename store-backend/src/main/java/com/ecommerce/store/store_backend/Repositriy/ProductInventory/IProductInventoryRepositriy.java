package com.ecommerce.store.store_backend.Repositriy.ProductInventory;

import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Models.Products.mProduct;

public interface IProductInventory {
    mGeneric.mApiResponse addInventory(mProduct.ProductInventory inventory);
    mGeneric.mApiResponse getInventoryByProductId(int productId);
    mGeneric.mApiResponse<mProduct.ProductInventory> getInventoryById(int inventoryId);
    mGeneric.mApiResponse<mProduct.ProductInventory> updateInventory(mProduct.ProductInventory inventory);
    mGeneric.mApiResponse deleteInventory(int inventoryId);
}
