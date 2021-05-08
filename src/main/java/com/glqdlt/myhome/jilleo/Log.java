package com.glqdlt.myhome.jilleo;

public interface Log {
    Boolean isSoldOut();
    String getTitle();
    Url getUrl();
    Integer getPrice();

    ParserLogEntity convertEntity(UrlEntity u);
}
