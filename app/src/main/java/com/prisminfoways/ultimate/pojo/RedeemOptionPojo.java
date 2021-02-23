package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class RedeemOptionPojo {


    /**
     * status : 1
     * data : [{"id":"2","payment_method":"PayTm","sub_title":"25 points = 25 INR","placeholder":"Enter your phone number","amount":"25"},{"id":"1","payment_method":"PayTm","sub_title":"100 points = 100 INR","placeholder":"Enter your phone number","amount":"100"}]
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
         * payment_method : PayTm
         * sub_title : 25 points = 25 INR
         * placeholder : Enter your phone number
         * amount : 25
         */

        private String id;
        private String payment_method;
        private String sub_title;
        private String placeholder;
        private String amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getSub_title() {
            return sub_title;
        }

        public void setSub_title(String sub_title) {
            this.sub_title = sub_title;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
