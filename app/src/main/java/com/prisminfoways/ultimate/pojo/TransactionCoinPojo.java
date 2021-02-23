package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class TransactionCoinPojo {

    /**
     * status : 1
     * data : [{"id":"165699","title":"Sign up Bonus","amount":"5000","created_date":"2019-04-09 00:00:00"}]
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
         * id : 165699
         * title : Sign up Bonus
         * amount : 5000
         * created_date : 2019-04-09 00:00:00
         */

        private String id;
        private String title;
        private String amount;
        private String created_date;

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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }
}
