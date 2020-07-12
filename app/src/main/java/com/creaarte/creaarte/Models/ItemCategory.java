package com.creaarte.creaarte.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemCategory implements Parcelable {

    private String idCategoria;
    private String nameCategory;
    private int imgCategory;
    private String descripcion;
    private String CATG_id;
    private String CATG_name;
    private String CATG_desc;
    private String CATG_cat_id;
    private String CATG_img;
    private String CATG_dt_crea;
    private String CATG_dt_modf;
    private String USRL_qualif_out_nmbr;
    private String CATG_s;
    private String CATG_c;
    private String numItems;
    private String totalPage;
    private String page;

    public ItemCategory(String nameCategory, String descripcion, Integer imgCategory, String idCategoria){
        this.idCategoria = idCategoria;
        this.nameCategory = nameCategory;
        this.imgCategory = imgCategory;
        this.descripcion = descripcion;
    }

    public ItemCategory (){

    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public int getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(int imgCategory) {
        this.imgCategory = imgCategory;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getCATG_desc() {
        return CATG_desc;
    }

    public void setCATG_desc(String CATG_desc) {
        this.CATG_desc = CATG_desc;
    }

    public String getCATG_cat_id() {
        return CATG_cat_id;
    }

    public void setCATG_cat_id(String CATG_cat_id) {
        this.CATG_cat_id = CATG_cat_id;
    }

    public String getCATG_img() {
        return CATG_img;
    }

    public void setCATG_img(String CATG_img) {
        this.CATG_img = CATG_img;
    }

    public String getCATG_dt_crea() {
        return CATG_dt_crea;
    }

    public void setCATG_dt_crea(String CATG_dt_crea) {
        this.CATG_dt_crea = CATG_dt_crea;
    }

    public String getCATG_dt_modf() {
        return CATG_dt_modf;
    }

    public void setCATG_dt_modf(String CATG_dt_modf) {
        this.CATG_dt_modf = CATG_dt_modf;
    }

    public String getUSRL_qualif_out_nmbr() {
        return USRL_qualif_out_nmbr;
    }

    public void setUSRL_qualif_out_nmbr(String USRL_qualif_out_nmbr) {
        this.USRL_qualif_out_nmbr = USRL_qualif_out_nmbr;
    }

    public String getCATG_s() {
        return CATG_s;
    }

    public void setCATG_s(String CATG_s) {
        this.CATG_s = CATG_s;
    }

    public String getCATG_c() {
        return CATG_c;
    }

    public void setCATG_c(String CATG_c) {
        this.CATG_c = CATG_c;
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

    protected ItemCategory(Parcel in) {
        idCategoria = in.readString();
        nameCategory = in.readString();
        imgCategory = in.readInt();
        descripcion = in.readString();
        CATG_id = in.readString();
        CATG_name = in.readString();
        CATG_desc = in.readString();
        CATG_cat_id = in.readString();
        CATG_img = in.readString();
        CATG_dt_crea = in.readString();
        CATG_dt_modf = in.readString();
        USRL_qualif_out_nmbr = in.readString();
        CATG_s = in.readString();
        CATG_c = in.readString();
        numItems = in.readString();
        totalPage = in.readString();
        page = in.readString();
    }

    public static final Creator<ItemCategory> CREATOR = new Creator<ItemCategory>() {
        @Override
        public ItemCategory createFromParcel(Parcel in) {
            return new ItemCategory(in);
        }

        @Override
        public ItemCategory[] newArray(int size) {
            return new ItemCategory[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idCategoria);
        dest.writeString(nameCategory);
        dest.writeInt(imgCategory);
        dest.writeString(descripcion);
        dest.writeString(CATG_id);
        dest.writeString(CATG_name);
        dest.writeString(CATG_desc);
        dest.writeString(CATG_cat_id);
        dest.writeString(CATG_img);
        dest.writeString(CATG_dt_crea);
        dest.writeString(CATG_dt_modf);
        dest.writeString(USRL_qualif_out_nmbr);
        dest.writeString(CATG_s);
        dest.writeString(CATG_c);
        dest.writeString(numItems);
        dest.writeString(totalPage);
        dest.writeString(page);
    }
}
