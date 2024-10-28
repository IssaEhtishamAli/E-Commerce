package com.ecommerce.store.store_backend.Repositriy.Audit;

import com.ecommerce.store.store_backend.Models.Audit.mAuditLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IAuditRepositriyImpl  implements IAuditRepositriy{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int logAction(int userId, String action) {
        try {
            
        }catch (Exception ex){

        }
    }

    @Override
    public List<mAuditLog> getAllLogs() {
        return List.of();
    }

    @Override
    public List<mAuditLog> getLogsByUserId(int userId) {
        return List.of();
    }
}
