package com.glqdlt.myhome.jilleo;

/**
 * @author glqdlt
 */
public class Url {
    private Integer id;
    private String url;

    public Url(Integer id, String url) {
        this.id = id;
        this.url = url;
    }

    public Url() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
