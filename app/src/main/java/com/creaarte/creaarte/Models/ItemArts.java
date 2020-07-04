package com.creaarte.creaarte.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemArts implements Parcelable {

    private String ARTW_id;
    private String USRL_id;
    private String USRL_alias;
    private String USRL_name;
    private String USRL_img_url;
    private String ARTW_name;
    private String ARTW_sku;
    private String ARTW_desc;
    private String ARTW_img;
    private String CATG_id;
    private String CATG_name;
    private String ARTW_avail;
    private String ARTW_dt_crea;
    private String ARTW_dt_modf;
    private String ARTW_tax1;
    private String ARTW_tax2;
    private String ARTW_tax3;
    private String ARTW_cost;
    private String ARTW_price;
    private String ARTW_comm;
    private String ARTW_reac;
    private String ARTW_reac_flag;
    private String CURR_id;
    private String CURR_desc;
    private String ARTW_pop;
    private String ARTW_grade_number;
    private String ARTW_grade_avg;
    private String numItems;
    private String totalPage;
    private String page;

    public ItemArts() {

    }

    protected ItemArts(Parcel in) {
        ARTW_id = in.readString();
        USRL_id = in.readString();
        USRL_alias = in.readString();
        USRL_name = in.readString();
        USRL_img_url = in.readString();
        ARTW_name = in.readString();
        ARTW_sku = in.readString();
        ARTW_desc = in.readString();
        ARTW_img = in.readString();
        CATG_id = in.readString();
        CATG_name = in.readString();
        ARTW_avail = in.readString();
        ARTW_dt_crea = in.readString();
        ARTW_dt_modf = in.readString();
        ARTW_tax1 = in.readString();
        ARTW_tax2 = in.readString();
        ARTW_tax3 = in.readString();
        ARTW_cost = in.readString();
        ARTW_price = in.readString();
        ARTW_comm = in.readString();
        ARTW_reac = in.readString();
        ARTW_reac_flag = in.readString();
        CURR_id = in.readString();
        CURR_desc = in.readString();
        ARTW_pop = in.readString();
        ARTW_grade_number = in.readString();
        ARTW_grade_avg = in.readString();
        numItems = in.readString();
        totalPage = in.readString();
        page = in.readString();
    }

    public static final Creator<ItemArts> CREATOR = new Creator<ItemArts>() {
        @Override
        public ItemArts createFromParcel(Parcel in) {
            return new ItemArts(in);
        }

        @Override
        public ItemArts[] newArray(int size) {
            return new ItemArts[size];
        }
    };

    public String getARTW_id() {
        return ARTW_id;
    }

    public void setARTW_id(String ARTW_id) {
        this.ARTW_id = ARTW_id;
    }

    public String getUSRL_id() {
        return USRL_id;
    }

    public void setUSRL_id(String USRL_id) {
        this.USRL_id = USRL_id;
    }

    public String getUSRL_alias() {
        return USRL_alias;
    }

    public void setUSRL_alias(String USRL_alias) {
        this.USRL_alias = USRL_alias;
    }

    public String getUSRL_name() {
        return USRL_name;
    }

    public void setUSRL_name(String USRL_name) {
        this.USRL_name = USRL_name;
    }

    public String getUSRL_img_url() {
        return USRL_img_url;
    }

    public void setUSRL_img_url(String USRL_img_url) {
        this.USRL_img_url = USRL_img_url;
    }

    public String getARTW_name() {
        return ARTW_name;
    }

    public void setARTW_name(String ARTW_name) {
        this.ARTW_name = ARTW_name;
    }

    public String getARTW_sku() {
        return ARTW_sku;
    }

    public void setARTW_sku(String ARTW_sku) {
        this.ARTW_sku = ARTW_sku;
    }

    public String getARTW_desc() {
        return ARTW_desc;
    }

    public void setARTW_desc(String ARTW_desc) {
        this.ARTW_desc = ARTW_desc;
    }

    public String getARTW_img() {
        return ARTW_img;
    }

    public void setARTW_img(String ARTW_img) {
        this.ARTW_img = ARTW_img;
    }

    public String getCATG_id() {
        return CATG_id;
    }

    public void setCATG_id(String CATG_id) {
        this.CATG_id = CATG_id;
    }

    public String getCATG_name() {
        return CATG_name;
    }

    public void setCATG_name(String CATG_name) {
        this.CATG_name = CATG_name;
    }

    public String getARTW_avail() {
        return ARTW_avail;
    }

    public void setARTW_avail(String ARTW_avail) {
        this.ARTW_avail = ARTW_avail;
    }

    public String getARTW_dt_crea() {
        return ARTW_dt_crea;
    }

    public void setARTW_dt_crea(String ARTW_dt_crea) {
        this.ARTW_dt_crea = ARTW_dt_crea;
    }

    public String getARTW_dt_modf() {
        return ARTW_dt_modf;
    }

    public void setARTW_dt_modf(String ARTW_dt_modf) {
        this.ARTW_dt_modf = ARTW_dt_modf;
    }

    public String getARTW_tax1() {
        return ARTW_tax1;
    }

    public void setARTW_tax1(String ARTW_tax1) {
        this.ARTW_tax1 = ARTW_tax1;
    }

    public String getARTW_tax2() {
        return ARTW_tax2;
    }

    public void setARTW_tax2(String ARTW_tax2) {
        this.ARTW_tax2 = ARTW_tax2;
    }

    public String getARTW_tax3() {
        return ARTW_tax3;
    }

    public void setARTW_tax3(String ARTW_tax3) {
        this.ARTW_tax3 = ARTW_tax3;
    }

    public String getARTW_cost() {
        return ARTW_cost;
    }

    public void setARTW_cost(String ARTW_cost) {
        this.ARTW_cost = ARTW_cost;
    }

    public String getARTW_price() {
        return ARTW_price;
    }

    public void setARTW_price(String ARTW_price) {
        this.ARTW_price = ARTW_price;
    }

    public String getARTW_comm() {
        return ARTW_comm;
    }

    public void setARTW_comm(String ARTW_comm) {
        this.ARTW_comm = ARTW_comm;
    }

    public String getARTW_reac() {
        return ARTW_reac;
    }

    public void setARTW_reac(String ARTW_reac) {
        this.ARTW_reac = ARTW_reac;
    }

    public String getARTW_reac_flag() {
        return ARTW_reac_flag;
    }

    public void setARTW_reac_flag(String ARTW_reac_flag) {
        this.ARTW_reac_flag = ARTW_reac_flag;
    }

    public String getCURR_id() {
        return CURR_id;
    }

    public void setCURR_id(String CURR_id) {
        this.CURR_id = CURR_id;
    }

    public String getCURR_desc() {
        return CURR_desc;
    }

    public void setCURR_desc(String CURR_desc) {
        this.CURR_desc = CURR_desc;
    }

    public String getARTW_pop() {
        return ARTW_pop;
    }

    public void setARTW_pop(String ARTW_pop) {
        this.ARTW_pop = ARTW_pop;
    }

    public String getARTW_grade_number() {
        return ARTW_grade_number;
    }

    public void setARTW_grade_number(String ARTW_grade_number) {
        this.ARTW_grade_number = ARTW_grade_number;
    }

    public String getARTW_grade_avg() {
        return ARTW_grade_avg;
    }

    public void setARTW_grade_avg(String ARTW_grade_avg) {
        this.ARTW_grade_avg = ARTW_grade_avg;
    }

    public String getNumItems() {
        return numItems;
    }

    public void setNumItems(String numItems) {
        this.numItems = numItems;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ARTW_id);
        dest.writeString(USRL_id);
        dest.writeString(USRL_alias);
        dest.writeString(USRL_name);
        dest.writeString(USRL_img_url);
        dest.writeString(ARTW_name);
        dest.writeString(ARTW_sku);
        dest.writeString(ARTW_desc);
        dest.writeString(ARTW_img);
        dest.writeString(CATG_id);
        dest.writeString(CATG_name);
        dest.writeString(ARTW_avail);
        dest.writeString(ARTW_dt_crea);
        dest.writeString(ARTW_dt_modf);
        dest.writeString(ARTW_tax1);
        dest.writeString(ARTW_tax2);
        dest.writeString(ARTW_tax3);
        dest.writeString(ARTW_cost);
        dest.writeString(ARTW_price);
        dest.writeString(ARTW_comm);
        dest.writeString(ARTW_reac);
        dest.writeString(ARTW_reac_flag);
        dest.writeString(CURR_id);
        dest.writeString(CURR_desc);
        dest.writeString(ARTW_pop);
        dest.writeString(ARTW_grade_number);
        dest.writeString(ARTW_grade_avg);
        dest.writeString(numItems);
        dest.writeString(totalPage);
        dest.writeString(page);
    }
}
