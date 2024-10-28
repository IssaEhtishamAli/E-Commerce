package com.ecommerce.store.store_backend.Mappers;

import com.ecommerce.store.store_backend.Models.Audit.mAuditLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AuditRowMapper implements RowMapper<mAuditLog> {
    @Override
    public mAuditLog mapRow(ResultSet rs,int rowNum) throws SQLException{
        mAuditLog auditLog = new mAuditLog();
        auditLog.setUserId(rs.getInt("user_id"));
        auditLog.setAction(rs.getString("action"));
        Timestamp timestamp = rs.getTimestamp("action_time");
        if (timestamp != null) {
            auditLog.setActionTimestamp(timestamp);
        } else {
            auditLog.setActionTimestamp(null); // or handle default value
        }
        return auditLog;
    }
}
