package com.prisminfoways.ultimate.pojo;

import java.util.List;

public  class RewardListPojo {


    /**
     * status : 1
     * data : [{"id":"1","title":"Video reward","coins":"50.00"}]
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
         * id : 1
         * title : Video reward
         * coins : 50.00
         */

        private String id;
        private String title;
        private String coins;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCoins() {
            return coins;
        }

        public void setCoins(String coins) {
            this.coins = coins;
        }
    }
}
