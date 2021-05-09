package com.glqdlt.myhome.jilleo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author glqdlt
 */
@Entity
@Table(name ="parse")
public class ParserLogEntity {
    private Long seq;
    private UrlEntity urlEntity;
    private Boolean soldOut = false;
    private Integer price = 0;
    private LocalDateTime workTime;
    private List<ParserAttirubte> parserAttirubteList;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    @ManyToOne()
    @JoinColumn(name ="urlId")
    public UrlEntity getUrlEntity() {
        return urlEntity;
    }

    public void setUrlEntity(UrlEntity urlEntity) {
        this.urlEntity = urlEntity;
    }

    public Boolean getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(Boolean soldOut) {
        this.soldOut = soldOut;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDateTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(LocalDateTime workTime) {
        this.workTime = workTime;
    }

    @OneToMany(mappedBy = "parserLogEntity", cascade = CascadeType.PERSIST)
    public List<ParserAttirubte> getParserAttirubteList() {
        return parserAttirubteList;
    }

    public void setParserAttirubteList(List<ParserAttirubte> parserAttirubteList) {
        this.parserAttirubteList = parserAttirubteList;
    }

    public void comapreToUpdated(ParserLogEntity source){

        String message = "";

        switch (this.getSoldOut().compareTo(source.getSoldOut())){
            case -1:
                message += "재입고 되었습니다!";
                break;
            case 1:
                message += "품절 상태가 되었습니다. ";
                break;
            case 0:
                if(this.getSoldOut()){
                    return;
                }
            default:
                break;
        }

        switch (this.getPrice().compareTo(source.getPrice())){
            case -1:
                message += String.format("가격이 %s 에서 %s 만큼 가격이 하강 했습니다!! ",source.getPrice(), source.getPrice() - this.getPrice());
                break;
            case 1:
                message += String.format("가격이 %s 에서 %s 만큼 가격이 상승 했습니다. ",source.getPrice(), this.getPrice()- source.getPrice() );
                break;
            default:
                break;
        }

        if(message.length() != 0){
            throw new UpdatePriceException(message,source,this);
        }
    }
}
