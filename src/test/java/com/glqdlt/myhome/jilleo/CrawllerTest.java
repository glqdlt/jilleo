package com.glqdlt.myhome.jilleo;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

class CrawllerTest {

    @Test
    void aaaa() {
        String text = "와우전용 10% 할인";
        String aaa = text.replaceAll("[^0-9%]", "");
        Assertions.assertEquals("10%",aaa);
    }

    @Ignore
    @Test
    void name() {

        List<Url> queue = new LinkedList<>();
        queue.add(new Url(1,"https://www.coupang.com/vp/products/4833372179?vendorItemId=73535487636&isAddedCart="));
        queue.add(new Url(2,"https://www.coupang.com/vp/products/1648405737?itemId=2808814534&vendorItemId=70798384730&sourceType=CATEGORY&categoryId=497035&isAddedCart="));
        queue.add(new Url(3, "https://www.coupang.com/vp/products/5423816438?vendorItemId=75486225012"));

        Crawller.CoupandCraller coupandCraller = new Crawller.CoupandCraller();
        List<CoupangParseLog> result = queue.stream().filter(x -> coupandCraller.isSupported(x)).map(x -> coupandCraller.crawlling(x)).collect(Collectors.toList());

        Assertions.assertEquals(3,result.size());




    }
}