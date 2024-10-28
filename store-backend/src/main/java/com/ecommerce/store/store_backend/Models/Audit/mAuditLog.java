package com.ecommerce.store.store_backend.Models.Audit;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class mAuditLog {
    private int logId;
    private int userId;
    private String action;
    private Timestamp actionTimestamp;

    public mAuditLog(int logId, int userId, String action, Timestamp actionTimestamp) {
        this.logId = logId;
        this.userId = userId;
        this.action = action;
        this.actionTimestamp = actionTimestamp;
    }
}
