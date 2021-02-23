package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class ProductListPojo {


    /**
     * status : 1
     * data : [{"id":"1","brand":"Jasmin","title":"Utea","image":"https://test5.prisminfoways.com/multi_game/assets/images/b5224878746e03ea41545b02decf4f57.jpg","price":"200","discount":"100","description":"The jasmine plant is believed to have been introduced into China from eastern South Asia via India during the Han Dynasty","url":"https://www.google.com/","created_date":"2020-06-27 11:46:05"}]
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
         * brand : Jasmin
         * title : Utea
         * image : https://test5.prisminfoways.com/multi_game/assets/images/b5224878746e03ea41545b02decf4f57.jpg
         * price : 200
         * discount : 100
         * description : The jasmine plant is believed to have been introduced into China from eastern South Asia via India during the Han Dynasty
         * url : https://www.google.com/
         * created_date : 2020-06-27 11:46:05
         */

        private String id;
        private String brand;
        private String title;
        private String image;
        private String price;
        private String discount;
        private String description;
        private String url;
        private String created_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }
}
