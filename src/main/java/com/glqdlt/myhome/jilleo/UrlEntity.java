package com.glqdlt.myhome.jilleo;

import javax.persistence.*;
import java.net.URL;

/**
 * @author glqdlt
 */
@Entity
@Table(name ="url")
public class UrlEntity extends Url{
    private Integer id;
    private String host;
    private String url;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setupFullUrl(URL url){
        this.setHost(url.getHost());
        this.setUrl(url.toString());
    }
}
