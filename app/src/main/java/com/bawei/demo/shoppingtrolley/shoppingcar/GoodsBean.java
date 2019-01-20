package com.bawei.demo.shoppingtrolley.shoppingcar;

import com.bawei.demo.shoppingtrolley.home_fragment.PageFragment;

import java.io.Serializable;
import java.util.List;

public class GoodsBean implements Serializable{
    private String status;
    private String message;
    private List<Result> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result implements Serializable{
        private int commodityId;
        private String commodityName;
        private String pic;
        private double price;
        private int count;
        public boolean isCheck;

        public Result(int commodityId, String commodityName, String pic, double price, int count, boolean isCheck) {
            this.commodityId = commodityId;
            this.commodityName = commodityName;
            this.pic = pic;
            this.price = price;
            this.count = count;
            this.isCheck = isCheck;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
