package com.jsp.studyShare.model;import java.util.List;public class ResponseUser {    private String nickname;    private int gender;    private String avatarUrl;    private String country;    private String province;    private String city;    private List<ResponseData> collection;    private List<ResponseData> uploaded;    private List<ResponseData> downloaded;    public String getNickname() {        return nickname;    }    public void setNickname(String nickname) {        this.nickname = nickname;    }    public int getGender() {        return gender;    }    public void setGender(int gender) {        this.gender = gender;    }    public String getAvatarUrl() {        return avatarUrl;    }    public void setAvatarUrl(String avatarUrl) {        this.avatarUrl = avatarUrl;    }    public String getCountry() {        return country;    }    public void setCountry(String country) {        this.country = country;    }    public String getProvince() {        return province;    }    public void setProvince(String province) {        this.province = province;    }    public String getCity() {        return city;    }    public void setCity(String city) {        this.city = city;    }    public List<ResponseData> getCollection() {        return collection;    }    public void setCollection(List<ResponseData> collection) {        this.collection = collection;    }    public List<ResponseData> getUploaded() {        return uploaded;    }    public void setUploaded(List<ResponseData> uploaded) {        this.uploaded = uploaded;    }    public List<ResponseData> getDownloaded() {        return downloaded;    }    public void setDownloaded(List<ResponseData> downloaded) {        this.downloaded = downloaded;    }}