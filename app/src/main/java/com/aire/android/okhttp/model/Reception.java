package com.aire.android.okhttp.model;

import android.util.Log;

/**
 * @Author: Zhupeipei
 * @CreateDate: 2021/10/28 11:02 上午
 */
public class Reception {
    private int status;
    private ResponseData data;

    public void show() {
        Log.d("zimotag", "staus" + status);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public static class ResponseData {
        private String serviceId;
        private String appName;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }
    }
}
