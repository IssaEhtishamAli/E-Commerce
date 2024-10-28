package com.ecommerce.store.store_backend.Controllers;

import com.ecommerce.store.store_backend.Models.Audit.mAuditLog;
import com.ecommerce.store.store_backend.Models.Generic.mGeneric;
import com.ecommerce.store.store_backend.Services.Audit.IAuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/audit/")
public class AuditController {

    @Autowired
    private IAuditService auditService;

    @Operation(summary = "Users Action Logs", description = "Adds a user Action Logs to the e-commerce store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User action logs created successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "User action logs creation failed", content = @Content)
    })
    @RequestMapping(value = "logAction",method = RequestMethod.POST)
    public ResponseEntity<?> logAction(@RequestBody mAuditLog auditLog){
        try {
            mGeneric.mApiResponse response = auditService.logAction(auditLog.getUserId(),auditLog.getAction(),auditLog.getActionTimestamp());
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Get All Users Action Logs", description = "Get All User Action Logs to the e-commerce store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get All Action Logs", content = @Content),
            @ApiResponse(responseCode = "400", description = "Get All Action Logs Failed", content = @Content)
    })
    @RequestMapping(value = "getAllLogs",method = RequestMethod.GET)
    public ResponseEntity<?> getAllLogs(){
        try {
            mGeneric.mApiResponse response = auditService.getAllLogs();
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Get User By Id Action Logs", description = "Get User By Id Action Logs to the e-commerce store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get User BY Id Action Logs", content = @Content),
            @ApiResponse(responseCode = "400", description = "Get User By Id Action Logs Failed", content = @Content)
    })
    @RequestMapping(value = "getLogsByUserId",method = RequestMethod.GET)
    public ResponseEntity<?> getLogsByUserId(@PathVariable int userId){
        try {
            mGeneric.mApiResponse response = auditService.getLogsByUserId(userId);
            if (response.getRespCode() == 1) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
