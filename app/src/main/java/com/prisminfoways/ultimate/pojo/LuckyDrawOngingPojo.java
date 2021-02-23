package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class LuckyDrawOngingPojo {


    /**
     * status : 1
     * data : [{"id":"2","title":"Title","entry_fee_coin":"100","entry_fee_rs":"50","winning_price":"500","joining_limit":"50","start_date":"2020-06-29 13:30:00","end_date":"2020-07-10 23:50:00","result_date":"2020-08-08 19:05:00","image":"216a3f2c0edccdfc1448ae2a1086a894.png","status":"0","created_date":"2020-06-29 15:44:24","total":"0","is_joined":0}]
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
         * id : 2
         * title : Title
         * entry_fee_coin : 100
         * entry_fee_rs : 50
         * winning_price : 500
         * joining_limit : 50
         * start_date : 2020-06-29 13:30:00
         * end_date : 2020-07-10 23:50:00
         * result_date : 2020-08-08 19:05:00
         * image : 216a3f2c0edccdfc1448ae2a1086a894.png
         * status : 0
         * created_date : 2020-06-29 15:44:24
         * total : 0
         * is_joined : 0
         */

        private String id;
        private String title;
        private String entry_fee_coin;
        private String entry_fee_rs;
        private String winning_price;
        private String joining_limit;
        private String start_date;
        private String end_date;
        private String result_date;
        private String image;
        private String status;
        private String created_date;
        private String total;
        private int is_joined;

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

        public String getEntry_fee_coin() {
            return entry_fee_coin;
        }

        public void setEntry_fee_coin(String entry_fee_coin) {
            this.entry_fee_coin = entry_fee_coin;
        }

        public String getEntry_fee_rs() {
            return entry_fee_rs;
        }

        public void setEntry_fee_rs(String entry_fee_rs) {
            this.entry_fee_rs = entry_fee_rs;
        }

        public String getWinning_price() {
            return winning_price;
        }

        public void setWinning_price(String winning_price) {
            this.winning_price = winning_price;
        }

        public String getJoining_limit() {
            return joining_limit;
        }

        public void setJoining_limit(String joining_limit) {
            this.joining_limit = joining_limit;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getResult_date() {
            return result_date;
        }

        public void setResult_date(String result_date) {
            this.result_date = result_date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public int getIs_joined() {
            return is_joined;
        }

        public void setIs_joined(int is_joined) {
            this.is_joined = is_joined;
        }
    }
}
