package com.example.administrator.zzzz;

import java.util.List;

/**
 * Created by Administrator on 2019/5/6.
 */

public class CommitOrderResultModel {

    /**
     * from : backend
     * cmd : delivery
     * order_sn : RE5ccfa61ade172442681200
     * dev_no : 3030303030303039
     * order_goods : [{"goods_id":"3","num":"1"}]
     */

    private String from;
    private String cmd;
    private String order_sn;
    private String dev_no;
    private int status;  // 1 成功  0 失败
    private List<OrderGoodsBean> order_goods;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getDev_no() {
        return dev_no;
    }

    public void setDev_no(String dev_no) {
        this.dev_no = dev_no;
    }

    public List<OrderGoodsBean> getOrder_goods() {
        return order_goods;
    }

    public void setOrder_goods(List<OrderGoodsBean> order_goods) {
        this.order_goods = order_goods;
    }

    public static class OrderGoodsBean {
        /**
         * goods_id : 3
         * num : 1
         */

        private String goods_id;
        private String num;
private String run;

        public String getRun() {
            return run;
        }

        public void setRun(String run) {
            this.run = run;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
