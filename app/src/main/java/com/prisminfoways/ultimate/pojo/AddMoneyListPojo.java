package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class AddMoneyListPojo {


    /**
     * status : 1
     * data : [{"id":"1","title":"Paytm","amount":"10"},{"id":"10","title":"Paytm","amount":"100"},{"id":"2","title":"Paytm","amount":"20"},{"id":"3","title":"Paytm","amount":"30"},{"id":"4","title":"Paytm","amount":"40"},{"id":"5","title":"Paytm","amount":"50"},{"id":"6","title":"Paytm","amount":"60"},{"id":"7","title":"Paytm","amount":"70"},{"id":"8","title":"Paytm","amount":"80"},{"id":"9","title":"Paytm","amount":"90"}]
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
         * title : Paytm
         * amount : 10
         */

        private String id;
        private String title;
        private String amount;

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
    }
}
