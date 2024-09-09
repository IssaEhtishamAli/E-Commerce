package com.ecommerce.store.store_backend.Models.Generic;

import lombok.Data;
import lombok.NoArgsConstructor;

public class mGeneric {
    @Data
    @NoArgsConstructor
    public static  class mApiResponse<T>{
        private  int RespCode;
        private String RespMsg;
        private T RespData;
        public mApiResponse(int respCode, String respMsg, T  respData){
            RespCode = respCode;
            RespMsg = respMsg;
            RespData = respData;
        }
        public mApiResponse(int respCode, String respMsg){
            RespCode = respCode;
            RespMsg = respMsg;
        }

    }
}
