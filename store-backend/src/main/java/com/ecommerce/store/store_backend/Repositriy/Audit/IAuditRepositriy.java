package com.ecommerce.store.store_backend.Repositriy.Audit;

import com.ecommerce.store.store_backend.Models.Audit.mAuditLog;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;

import java.sql.Timestamp;
import java.util.List;

public interface IAuditRepositriy {
    mGeneric.mApiResponse<Integer> logAction(int userId, String action, Timestamp actionTimestamp);
    mGeneric.mApiResponse<List<mAuditLog>> getAllLogs();
    mGeneric.mApiResponse<mAuditLog> getLogsByUserId(int userId);
}
