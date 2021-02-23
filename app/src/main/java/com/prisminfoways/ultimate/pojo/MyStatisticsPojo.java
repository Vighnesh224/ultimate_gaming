package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class MyStatisticsPojo {


    /**
     * status : 1
     * data : [{"name":"Match #2","entry_fee":"20","win_amount":0}]
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
         * name : Match #2
         * entry_fee : 20
         * win_amount : 0
         */

        private String name;
        private String entry_fee;
        private int win_amount;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEntry_fee() {
            return entry_fee;
        }

        public void setEntry_fee(String entry_fee) {
            this.entry_fee = entry_fee;
        }

        public int getWin_amount() {
            return win_amount;
        }

        public void setWin_amount(int win_amount) {
            this.win_amount = win_amount;
        }
    }
}
