package com.glqdlt.myhome.jilleo;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author glqdlt
 */
@Entity
@Table(name="url_watcher")
public class Watcher {
    private Integer id;
    private String email;
    private UrlEntity urlEntity;
    private LocalDateTime regDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="urlId")
    public UrlEntity getUrlEntity() {
        return urlEntity;
    }

    public void setUrlEntity(UrlEntity urlEntity) {
        this.urlEntity = urlEntity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }
}
