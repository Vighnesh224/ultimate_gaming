package com.prisminfoways.ultimate.pojo;

import java.util.List;

public class NotificationPojo {

    /**
     * status : 1
     * data : [{"id":"1","title":"multi game","text":"Join a match","image":"https://test5.prisminfoways.com/multi_game/assets/images/542f7c2ce48b2d6ed8c51cbe4f61808b.png","external_link":"https://www.google.com/","created_date":"2020-07-01 14:10:22"}]
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
         * title : multi game
         * text : Join a match
         * image : https://test5.prisminfoways.com/multi_game/assets/images/542f7c2ce48b2d6ed8c51cbe4f61808b.png
         * external_link : https://www.google.com/
         * created_date : 2020-07-01 14:10:22
         */

        private String id;
        private String title;
        private String text;
        private String image;
        private String external_link;
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

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getExternal_link() {
            return external_link;
        }

        public void setExternal_link(String external_link) {
            this.external_link = external_link;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }
}
