package com.creaarte.creaarte.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemUserDate implements Parcelable {

    private String USRL_id;
    private String USRT_id;
    private String GEND_id;
    private String USRR_id;
    private String USRL_name;
    private String USRL_lfnm;
    private String USRL_lmnm;
    private String USRL_alias;
    private String CATG_cat_id_main;
    private String USRL_number1_artw;
    private String CATG_cat_id_second;
    private String USRL_number2_artw;
    private String CATG_cat_id_third;
    private String USRL_number3_artw;
    private String USRL_email;
    private String USRL_email_alt;
    private String USRL_passw;
    private String USRL_phnr;
    private String USRL_dt_crea;
    private String USRL_dt_modf;
    private String USRL_img_url;
    private String USRL_ip_addr;
    private String USRL_lastaccess;
    private String USRL_addr1;
    private String USRL_addr2;
    private String USRL_addr3;
    private String USRL_town;
    private String USRL_state;
    private String USRL_country;
    private String USRL_zip;
    private String USRL_facebook;
    private String USRL_twitter;
    private String USRL_instagram;
    private String USRL_youtube;
    private String USRL_social5;
    private String USRL_social6;
    private String USRL_social7;
    private String USRL_qualif_in_avg;
    private String USRL_qualif_in_nmbr;
    private String USRL_qualif_out_avg;
    private String USRL_qualif_out_nmbr;
    private String USRL_s;
    private String USRL_c;

    public ItemUserDate() {

    }

    protected ItemUserDate(Parcel in) {
        USRL_id = in.readString();
        USRT_id = in.readString();
        GEND_id = in.readString();
        USRR_id = in.readString();
        USRL_name = in.readString();
        USRL_lfnm = in.readString();
        USRL_lmnm = in.readString();
        USRL_alias = in.readString();
        CATG_cat_id_main = in.readString();
        USRL_number1_artw = in.readString();
        CATG_cat_id_second = in.readString();
        USRL_number2_artw = in.readString();
        CATG_cat_id_third = in.readString();
        USRL_number3_artw = in.readString();
        USRL_email = in.readString();
        USRL_email_alt = in.readString();
        USRL_passw = in.readString();
        USRL_phnr = in.readString();
        USRL_dt_crea = in.readString();
        USRL_dt_modf = in.readString();
        USRL_img_url = in.readString();
        USRL_ip_addr = in.readString();
        USRL_lastaccess = in.readString();
        USRL_addr1 = in.readString();
        USRL_addr2 = in.readString();
        USRL_addr3 = in.readString();
        USRL_town = in.readString();
        USRL_state = in.readString();
        USRL_country = in.readString();
        USRL_zip = in.readString();
        USRL_facebook = in.readString();
        USRL_twitter = in.readString();
        USRL_instagram = in.readString();
        USRL_youtube = in.readString();
        USRL_social5 = in.readString();
        USRL_social6 = in.readString();
        USRL_social7 = in.readString();
        USRL_qualif_in_avg = in.readString();
        USRL_qualif_in_nmbr = in.readString();
        USRL_qualif_out_avg = in.readString();
        USRL_qualif_out_nmbr = in.readString();
        USRL_s = in.readString();
        USRL_c = in.readString();
    }

    public static final Creator<ItemUserDate> CREATOR = new Creator<ItemUserDate>() {
        @Override
        public ItemUserDate createFromParcel(Parcel in) {
            return new ItemUserDate(in);
        }

        @Override
        public ItemUserDate[] newArray(int size) {
            return new ItemUserDate[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(USRL_id);
        parcel.writeString(USRT_id);
        parcel.writeString(GEND_id);
        parcel.writeString(USRR_id);
        parcel.writeString(USRL_name);
        parcel.writeString(USRL_lfnm);
        parcel.writeString(USRL_lmnm);
        parcel.writeString(USRL_alias);
        parcel.writeString(CATG_cat_id_main);
        parcel.writeString(USRL_number1_artw);
        parcel.writeString(CATG_cat_id_second);
        parcel.writeString(USRL_number2_artw);
        parcel.writeString(CATG_cat_id_third);
        parcel.writeString(USRL_number3_artw);
        parcel.writeString(USRL_email);
        parcel.writeString(USRL_email_alt);
        parcel.writeString(USRL_passw);
        parcel.writeString(USRL_phnr);
        parcel.writeString(USRL_dt_crea);
        parcel.writeString(USRL_dt_modf);
        parcel.writeString(USRL_img_url);
        parcel.writeString(USRL_ip_addr);
        parcel.writeString(USRL_lastaccess);
        parcel.writeString(USRL_addr1);
        parcel.writeString(USRL_addr2);
        parcel.writeString(USRL_addr3);
        parcel.writeString(USRL_town);
        parcel.writeString(USRL_state);
        parcel.writeString(USRL_country);
        parcel.writeString(USRL_zip);
        parcel.writeString(USRL_facebook);
        parcel.writeString(USRL_twitter);
        parcel.writeString(USRL_instagram);
        parcel.writeString(USRL_youtube);
        parcel.writeString(USRL_social5);
        parcel.writeString(USRL_social6);
        parcel.writeString(USRL_social7);
        parcel.writeString(USRL_qualif_in_avg);
        parcel.writeString(USRL_qualif_in_nmbr);
        parcel.writeString(USRL_qualif_out_avg);
        parcel.writeString(USRL_qualif_out_nmbr);
        parcel.writeString(USRL_s);
        parcel.writeString(USRL_c);
    }

    public String getUSRL_id() {
        return USRL_id;
    }

    public void setUSRL_id(String USRL_id) {
        this.USRL_id = USRL_id;
    }

    public String getUSRT_id() {
        return USRT_id;
    }

    public void setUSRT_id(String USRT_id) {
        this.USRT_id = USRT_id;
    }

    public String getGEND_id() {
        return GEND_id;
    }

    public void setGEND_id(String GEND_id) {
        this.GEND_id = GEND_id;
    }

    public String getUSRR_id() {
        return USRR_id;
    }

    public void setUSRR_id(String USRR_id) {
        this.USRR_id = USRR_id;
    }

    public String getUSRL_name() {
        return USRL_name;
    }

    public void setUSRL_name(String USRL_name) {
        this.USRL_name = USRL_name;
    }

    public String getUSRL_lfnm() {
        return USRL_lfnm;
    }

    public void setUSRL_lfnm(String USRL_lfnm) {
        this.USRL_lfnm = USRL_lfnm;
    }

    public String getUSRL_lmnm() {
        return USRL_lmnm;
    }

    public void setUSRL_lmnm(String USRL_lmnm) {
        this.USRL_lmnm = USRL_lmnm;
    }

    public String getUSRL_alias() {
        return USRL_alias;
    }

    public void setUSRL_alias(String USRL_alias) {
        this.USRL_alias = USRL_alias;
    }

    public String getCATG_cat_id_main() {
        return CATG_cat_id_main;
    }

    public void setCATG_cat_id_main(String CATG_cat_id_main) {
        this.CATG_cat_id_main = CATG_cat_id_main;
    }

    public String getUSRL_number1_artw() {
        return USRL_number1_artw;
    }

    public void setUSRL_number1_artw(String USRL_number1_artw) {
        this.USRL_number1_artw = USRL_number1_artw;
    }

    public String getCATG_cat_id_second() {
        return CATG_cat_id_second;
    }

    public void setCATG_cat_id_second(String CATG_cat_id_second) {
        this.CATG_cat_id_second = CATG_cat_id_second;
    }

    public String getUSRL_number2_artw() {
        return USRL_number2_artw;
    }

    public void setUSRL_number2_artw(String USRL_number2_artw) {
        this.USRL_number2_artw = USRL_number2_artw;
    }

    public String getCATG_cat_id_third() {
        return CATG_cat_id_third;
    }

    public void setCATG_cat_id_third(String CATG_cat_id_third) {
        this.CATG_cat_id_third = CATG_cat_id_third;
    }

    public String getUSRL_number3_artw() {
        return USRL_number3_artw;
    }

    public void setUSRL_number3_artw(String USRL_number3_artw) {
        this.USRL_number3_artw = USRL_number3_artw;
    }

    public String getUSRL_email() {
        return USRL_email;
    }

    public void setUSRL_email(String USRL_email) {
        this.USRL_email = USRL_email;
    }

    public String getUSRL_email_alt() {
        return USRL_email_alt;
    }

    public void setUSRL_email_alt(String USRL_email_alt) {
        this.USRL_email_alt = USRL_email_alt;
    }

    public String getUSRL_passw() {
        return USRL_passw;
    }

    public void setUSRL_passw(String USRL_passw) {
        this.USRL_passw = USRL_passw;
    }

    public String getUSRL_phnr() {
        return USRL_phnr;
    }

    public void setUSRL_phnr(String USRL_phnr) {
        this.USRL_phnr = USRL_phnr;
    }

    public String getUSRL_dt_crea() {
        return USRL_dt_crea;
    }

    public void setUSRL_dt_crea(String USRL_dt_crea) {
        this.USRL_dt_crea = USRL_dt_crea;
    }

    public String getUSRL_dt_modf() {
        return USRL_dt_modf;
    }

    public void setUSRL_dt_modf(String USRL_dt_modf) {
        this.USRL_dt_modf = USRL_dt_modf;
    }

    public String getUSRL_img_url() {
        return USRL_img_url;
    }

    public void setUSRL_img_url(String USRL_img_url) {
        this.USRL_img_url = USRL_img_url;
    }

    public String getUSRL_ip_addr() {
        return USRL_ip_addr;
    }

    public void setUSRL_ip_addr(String USRL_ip_addr) {
        this.USRL_ip_addr = USRL_ip_addr;
    }

    public String getUSRL_lastaccess() {
        return USRL_lastaccess;
    }

    public void setUSRL_lastaccess(String USRL_lastaccess) {
        this.USRL_lastaccess = USRL_lastaccess;
    }

    public String getUSRL_addr1() {
        return USRL_addr1;
    }

    public void setUSRL_addr1(String USRL_addr1) {
        this.USRL_addr1 = USRL_addr1;
    }

    public String getUSRL_addr2() {
        return USRL_addr2;
    }

    public void setUSRL_addr2(String USRL_addr2) {
        this.USRL_addr2 = USRL_addr2;
    }

    public String getUSRL_addr3() {
        return USRL_addr3;
    }

    public void setUSRL_addr3(String USRL_addr3) {
        this.USRL_addr3 = USRL_addr3;
    }

    public String getUSRL_town() {
        return USRL_town;
    }

    public void setUSRL_town(String USRL_town) {
        this.USRL_town = USRL_town;
    }

    public String getUSRL_state() {
        return USRL_state;
    }

    public void setUSRL_state(String USRL_state) {
        this.USRL_state = USRL_state;
    }

    public String getUSRL_country() {
        return USRL_country;
    }

    public void setUSRL_country(String USRL_country) {
        this.USRL_country = USRL_country;
    }

    public String getUSRL_zip() {
        return USRL_zip;
    }

    public void setUSRL_zip(String USRL_zip) {
        this.USRL_zip = USRL_zip;
    }

    public String getUSRL_facebook() {
        return USRL_facebook;
    }

    public void setUSRL_facebook(String USRL_facebook) {
        this.USRL_facebook = USRL_facebook;
    }

    public String getUSRL_twitter() {
        return USRL_twitter;
    }

    public void setUSRL_twitter(String USRL_twitter) {
        this.USRL_twitter = USRL_twitter;
    }

    public String getUSRL_instagram() {
        return USRL_instagram;
    }

    public void setUSRL_instagram(String USRL_instagram) {
        this.USRL_instagram = USRL_instagram;
    }

    public String getUSRL_youtube() {
        return USRL_youtube;
    }

    public void setUSRL_youtube(String USRL_youtube) {
        this.USRL_youtube = USRL_youtube;
    }

    public String getUSRL_social5() {
        return USRL_social5;
    }

    public void setUSRL_social5(String USRL_social5) {
        this.USRL_social5 = USRL_social5;
    }

    public String getUSRL_social6() {
        return USRL_social6;
    }

    public void setUSRL_social6(String USRL_social6) {
        this.USRL_social6 = USRL_social6;
    }

    public String getUSRL_social7() {
        return USRL_social7;
    }

    public void setUSRL_social7(String USRL_social7) {
        this.USRL_social7 = USRL_social7;
    }

    public String getUSRL_qualif_in_avg() {
        return USRL_qualif_in_avg;
    }

    public void setUSRL_qualif_in_avg(String USRL_qualif_in_avg) {
        this.USRL_qualif_in_avg = USRL_qualif_in_avg;
    }

    public String getUSRL_qualif_in_nmbr() {
        return USRL_qualif_in_nmbr;
    }

    public void setUSRL_qualif_in_nmbr(String USRL_qualif_in_nmbr) {
        this.USRL_qualif_in_nmbr = USRL_qualif_in_nmbr;
    }

    public String getUSRL_qualif_out_avg() {
        return USRL_qualif_out_avg;
    }

    public void setUSRL_qualif_out_avg(String USRL_qualif_out_avg) {
        this.USRL_qualif_out_avg = USRL_qualif_out_avg;
    }

    public String getUSRL_qualif_out_nmbr() {
        return USRL_qualif_out_nmbr;
    }

    public void setUSRL_qualif_out_nmbr(String USRL_qualif_out_nmbr) {
        this.USRL_qualif_out_nmbr = USRL_qualif_out_nmbr;
    }

    public String getUSRL_s() {
        return USRL_s;
    }

    public void setUSRL_s(String USRL_s) {
        this.USRL_s = USRL_s;
    }

    public String getUSRL_c() {
        return USRL_c;
    }

    public void setUSRL_c(String USRL_c) {
        this.USRL_c = USRL_c;
    }

}
