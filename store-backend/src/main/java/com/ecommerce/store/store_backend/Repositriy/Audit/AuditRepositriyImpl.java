package com.ecommerce.store.store_backend.Repositriy.Audit;

import com.ecommerce.store.store_backend.Mappers.AuditRowMapper;
import com.ecommerce.store.store_backend.Models.Audit.mAuditLog;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class AuditRepositriyImpl  implements IAuditRepositriy{
    String INSERT_AUDIT = "INSERT INTO audit_logs (user_id, action, action_time) VALUES (?, ?, ?)";
    String SELECT_ALL = "SELECT * FROM audit_logs";
    String SELECT_BY_USERID = "SELECT * FROM audit_logs WHERE user_id = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public mGeneric.mApiResponse<Integer> logAction(int userId, String action,Timestamp actionTimestamp) {
        try {
            KeyHolder keyholder = new GeneratedKeyHolder();
            int rowsAffected =jdbcTemplate.update(con -> {
                PreparedStatement pst = con.prepareStatement(INSERT_AUDIT, Statement.RETURN_GENERATED_KEYS);
                pst.setInt(1,userId);
                pst.setString(2,action);
                pst.setTimestamp(3,actionTimestamp);
                return pst;
            },keyholder);
            if (rowsAffected > 0 ){
                return new mGeneric.mApiResponse(1, "User Audit Inserted", HttpStatus.OK);
            }
            else{
                return new mGeneric.mApiResponse(0, "Failed To User Audit Inserted!", HttpStatus.OK);
            }
        }catch (Exception ex){
            return new mGeneric.mApiResponse(0, "", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public mGeneric.mApiResponse<List<mAuditLog>> getAllLogs() {
        try {
            List<mAuditLog> auditLogs=jdbcTemplate.query(SELECT_ALL,new AuditRowMapper());

            if (auditLogs==null){
                return new mGeneric.mApiResponse(0, "Failed to fetch category!");
            }
            else{
                return new mGeneric.mApiResponse(1, "Success",auditLogs);
            }

        }catch (Exception ex){
            return new mGeneric.mApiResponse(0, ex.getMessage());
        }
    }

    @Override
    public mGeneric.mApiResponse<mAuditLog> getLogsByUserId(int userId) {
        try {
        List<mAuditLog> auditLogResponse = jdbcTemplate.query(SELECT_BY_USERID,new AuditRowMapper());
        if (auditLogResponse==null){
            return new mGeneric.mApiResponse(0, "Failed to fetch category!");
        }
        else{
            return new mGeneric.mApiResponse(1, "Success",auditLogResponse);
        }
    }catch (Exception ex){
        return new mGeneric.mApiResponse(0, ex.getMessage());
    }
    }
}
