package com.prisminfoways.ultimate.pojo;

public class GenerateCheckSumPojo {

    /**
     * status : 1
     * data : {"CHECKSUMHASH":"jDXhiaCk2e4n3zX8d5ydocWCY/rmcDiQ4en72jw3Ks3PYOoqvQ3uttUBsJo2UkyzC68Aa/8NbbWYELPYIM1zxI9erizB/IkiuINBvVMcsP8=","ORDER_ID":"1mpNh5dOa","payt_STATUS":"1","MID":"EqgXln49007310873254","INDUSTRY_TYPE_ID":"Retail","WEBSITE":"WEBSTAGING","CALLBACK_URL":"https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=","CHANNEL_ID":"WAP"}
     */

    private String status;
    private DataBean data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * "status": "1",
         * "data": {
         * "CHECKSUMHASH": "szc1N+tAdEdr34qtBg8PeSrwfGlWq7X5SyIkDJaIxYCPSwZVvjNJ5IrzupF75eOfjEC8LJVvwS5gmKDgoKFe21+2urcxT1uA4B9M                                +DsKEQE=",
         * "ORDER_ID": "45jVRd96O",
         * "payt_STATUS": "1",
         * "MID": "waxRAW66752158367679",
         * "INDUSTRY_TYPE_ID": "Retail",
         * "WEBSITE": "DEFAULT",
         */

        private String CHECKSUMHASH;
        private String ORDER_ID;
        private String payt_STATUS;
        private String MID;
        private String INDUSTRY_TYPE_ID;
        private String WEBSITE;
        private String CALLBACK_URL;
        private String CHANNEL_ID;

        public String getCHECKSUMHASH() {
            return CHECKSUMHASH;
        }

        public void setCHECKSUMHASH(String CHECKSUMHASH) {
            this.CHECKSUMHASH = CHECKSUMHASH;
        }

        public String getORDER_ID() {
            return ORDER_ID;
        }

        public void setORDER_ID(String ORDER_ID) {
            this.ORDER_ID = ORDER_ID;
        }

        public String getPayt_STATUS() {
            return payt_STATUS;
        }

        public void setPayt_STATUS(String payt_STATUS) {
            this.payt_STATUS = payt_STATUS;
        }

        public String getMID() {
            return MID;
        }

        public void setMID(String MID) {
            this.MID = MID;
        }

        public String getINDUSTRY_TYPE_ID() {
            return INDUSTRY_TYPE_ID;
        }

        public void setINDUSTRY_TYPE_ID(String INDUSTRY_TYPE_ID) {
            this.INDUSTRY_TYPE_ID = INDUSTRY_TYPE_ID;
        }

        public String getWEBSITE() {
            return WEBSITE;
        }

        public void setWEBSITE(String WEBSITE) {
            this.WEBSITE = WEBSITE;
        }

        public String getCALLBACK_URL() {
            return CALLBACK_URL;
        }

        public void setCALLBACK_URL(String CALLBACK_URL) {
            this.CALLBACK_URL = CALLBACK_URL;
        }

        public String getCHANNEL_ID() {
            return CHANNEL_ID;
        }

        public void setCHANNEL_ID(String CHANNEL_ID) {
            this.CHANNEL_ID = CHANNEL_ID;
        }
    }
}
