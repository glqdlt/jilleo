package com.glqdlt.myhome.jilleo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author glqdlt
 */
public class CoupangParseLog implements Log{
    private Url url;
    private Boolean soldOut;
    private List<String> etc = new LinkedList<>();

    public List<String> getEtc() {
        return etc;
    }

    public void setEtc(List<String> etc) {
        this.etc = etc;
    }

    public Boolean getSoldOut() {
        return soldOut;
    }

    public void setSoldOut(Boolean soldOut) {
        this.soldOut = soldOut;
    }

    private String title;
    private List<Integer> prices = new LinkedList<>();
    private List<Integer> cardBonus = new LinkedList<>();
    private LocalDateTime workTime;
    private Integer salePrice;

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Url getUrl() {
        return url;
    }

    @Override
    public Integer getPrice() {
        return getSalePrice();
    }

    @Override
    public ParserLogEntity convertEntity(UrlEntity u) {
        ParserLogEntity parserLogEntity = new ParserLogEntity();
        parserLogEntity.setPrice(this.getSalePrice());
        parserLogEntity.setWorkTime(LocalDateTime.now());
        parserLogEntity.setSoldOut(this.getSoldOut());
        parserLogEntity.setUrlEntity(u);
        parserLogEntity.setTitle(this.getTitle());

        List<ParserAttirubte.EtcString> e = etc.stream().map(x -> {
            ParserAttirubte.EtcString etcString = new ParserAttirubte.EtcString();
            etcString.setParserLogEntity(parserLogEntity);
            etcString.setEtc(x);
            return etcString;
        }).collect(Collectors.toList());
        List<ParserAttirubte.Pricess> p = prices.stream().map(x -> {
            ParserAttirubte.Pricess pricess = new ParserAttirubte.Pricess();
            pricess.setParserLogEntity(parserLogEntity);
            pricess.setPrices(x);
            return pricess;
        }).collect(Collectors.toList());

        List<ParserAttirubte.CaldSale> c = cardBonus.stream().map(x -> {
            ParserAttirubte.CaldSale caldSale = new ParserAttirubte.CaldSale();
            caldSale.setCardSalePercent(x);
            caldSale.setParserLogEntity(parserLogEntity);
            return caldSale;
        }).collect(Collectors.toList());

        List<ParserAttirubte> zxc = Stream.of(e, p, c).flatMap(Collection::stream).collect(Collectors.toList());
        parserLogEntity.setParserAttirubteList(zxc);
        return parserLogEntity;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    @Override
    public Boolean isSoldOut() {
        return getSoldOut();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Integer> getPrices() {
        return prices;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }

    public List<Integer> getCardBonus() {
        return cardBonus;
    }

    public void setCardBonus(List<Integer> cardBonus) {
        this.cardBonus = cardBonus;
    }

    public LocalDateTime getWorkTime() {
        return workTime;
    }

    public void setWorkTime(LocalDateTime workTime) {
        this.workTime = workTime;
    }
}
