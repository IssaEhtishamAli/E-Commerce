package com.ecommerce.store.store_backend.Services.Audit;

import com.ecommerce.store.store_backend.Models.Audit.mAuditLog;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Repositriy.Audit.IAuditRepositriy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AuditServiceImpl implements IAuditService{
    @Autowired
    private IAuditRepositriy auditRepositriy;
    @Override
    public mGeneric.mApiResponse<Integer> logAction(int userId, String action, Timestamp actionTimestamp) {
        return auditRepositriy.logAction(userId,action,actionTimestamp);
    }

    @Override
    public mGeneric.mApiResponse<List<mAuditLog>> getAllLogs() {
        return auditRepositriy.getAllLogs();
    }

    @Override
    public mGeneric.mApiResponse<mAuditLog> getLogsByUserId(int userId) {
        return auditRepositriy.getLogsByUserId(userId);
    }
}
