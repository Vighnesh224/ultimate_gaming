package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class TopPlayerPojo {


    /**
     * status : 1
     * data : [{"earning":"1500","user_id":"191642","name":"jay"},{"earning":"1400","user_id":"191643","name":"ashish"},{"earning":"1200","user_id":"191644","name":"harshil"}]
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * earning : 1500
         * user_id : 191642
         * name : jay
         */

        private String earning;
        private String user_id;
        private String name;

        public String getEarning() {
            return earning;
        }

        public void setEarning(String earning) {
            this.earning = earning;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
