package com.glqdlt.myhome.jilleo;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author glqdlt
 */
@Entity
@Table(name ="lowest_price_queue")
public class LowestPriceQueueEntity {
    private Long seq;
    private LocalDateTime regDate;
    private Boolean submit;
    private UrlEntity urlEntity;
    private ParserLogEntity source;
    private ParserLogEntity cursor;
    private String summary;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public Boolean getSubmit() {
        return submit;
    }

    public void setSubmit(Boolean submit) {
        this.submit = submit;
    }

    @ManyToOne
    @JoinColumn(name="urlId")
    public UrlEntity getUrlEntity() {
        return urlEntity;
    }

    public void setUrlEntity(UrlEntity urlEntity) {
        this.urlEntity = urlEntity;
    }

    @ManyToOne
    @JoinColumn(name="`source`")
    public ParserLogEntity getSource() {
        return source;
    }

    public void setSource(ParserLogEntity source) {
        this.source = source;
    }

    @ManyToOne
    @JoinColumn(name="`cursor`")
    public ParserLogEntity getCursor() {
        return cursor;
    }

    public void setCursor(ParserLogEntity cursor) {
        this.cursor = cursor;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
