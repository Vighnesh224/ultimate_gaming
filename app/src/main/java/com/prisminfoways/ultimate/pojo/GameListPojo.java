package com.prisminfoways.ultimate.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameListPojo {

    /**
     * status : 1
     * data : [{"id":"10","title":"HTML Games","image":"https://test5.prisminfoways.com/multi_game/assets/images/498a131ac8b4ea75998c6562d335f866.jpg","playstore_url":"https://showcase.codethislab.com/games/katana_fruit/","bundle_id":"asdfsdf","type":"HTML Games"},{"id":"6","title":"Pubg Mobile","image":"https://test5.prisminfoways.com/multi_game/assets/images/1296d23daac359b97eb7086ff90f0af1.jpg","playstore_url":"Pubg Mobile","bundle_id":"Pubg Mobile","type":"Game"},{"id":"3","title":"Pubg Mobile Lite","image":"https://test5.prisminfoways.com/multi_game/assets/images/cf79ad538ea832abe7e8eddd1fabaaf0.jpg","playstore_url":"https://play.google.com/store/apps/details?id=com.tencent.iglite","bundle_id":"com.dts.pubgmobilelite","type":"Game"},{"id":"2","title":"Call of Duty","image":"https://test5.prisminfoways.com/multi_game/assets/images/67457f7684ffbb494e927da8ee8c7c1f.png","playstore_url":"https://play.google.com/store/apps/details?id=com.activision.callofduty.shooter","bundle_id":"com.activision.callofduty.shooter","type":"Game"}]
     */

    @SerializedName("status")
    private String status;
    @SerializedName("data")
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
         * id : 10
         * title : HTML Games
         * image : https://test5.prisminfoways.com/multi_game/assets/images/498a131ac8b4ea75998c6562d335f866.jpg
         * playstore_url : https://showcase.codethislab.com/games/katana_fruit/
         * bundle_id : asdfsdf
         * type : HTML Games
         */

        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;
        @SerializedName("image")
        private String image;
        @SerializedName("playstore_url")
        private String playstoreUrl;
        @SerializedName("bundle_id")
        private String bundleId;
        @SerializedName("type")
        private String type;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getPlaystoreUrl() {
            return playstoreUrl;
        }

        public void setPlaystoreUrl(String playstoreUrl) {
            this.playstoreUrl = playstoreUrl;
        }

        public String getBundleId() {
            return bundleId;
        }

        public void setBundleId(String bundleId) {
            this.bundleId = bundleId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
