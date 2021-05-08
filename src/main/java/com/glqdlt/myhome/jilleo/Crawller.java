package com.glqdlt.myhome.jilleo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author glqdlt
 */
public interface Crawller<T extends Log> {

    T crawlling(Url url);

    String getHostDomain();

    default Boolean isSupported(Url url){
        if(url instanceof UrlEntity){
            return ((UrlEntity) url).getHost().startsWith(getHostDomain());
        }
        try {
            URI aaa = new URI(url.getUrl());
            return aaa.getHost().startsWith(getHostDomain());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Component
    class CoupandCraller implements Crawller{

        @Override
        public CoupangParseLog crawlling(Url url) {
            try {
                return parse(url);
            }catch (Throwable e){
                throw new RuntimeException(e);
            }
        }

        @Override
        public String getHostDomain() {
            return "www.coupang.com";
        }


        private static final String ONLY_NUMBER_FORMAT ="[^0-9]";

        private CoupangParseLog parse(Url url) throws IOException {
            Connection.Response zxc = Jsoup.connect(url.getUrl())
                    .method(Connection.Method.GET)
                    .execute();

            Document doc = zxc.parse();

            List<String> etc = new LinkedList<>();
            Optional.of(doc.getElementsByClass("aos-label")).map(Elements::text).filter(x ->!x.equals("")).map(etc::add);
            Boolean soldOut = Optional.of(doc.getElementsByClass("oos-label")).map(Elements::text).filter(x -> !x.equals("")).map(x-> x.contains("품절")).orElse(false) ;
            Elements title = doc.getElementsByClass("prod-buy-header__title");
            String titleString = Optional.of(title).filter(x -> x.size() > 0).map(Elements::text).orElse("");
            Elements price = doc.getElementsByClass("total-price");
            List<Integer> prices = price.stream()
                    .map(Element::text)
                    .filter(x -> !x.equals(""))
                    .filter(x -> !x.equals("원"))
                    .map(x -> x.replaceAll(ONLY_NUMBER_FORMAT,""))
                    .map(Integer::valueOf)
                    .sorted()
                    .collect(Collectors.toList());

            Elements cardsBonus = doc.getElementsByClass("ccid-detail-benefits");
            List<Integer> cards = cardsBonus.stream()
                    .map(Element::text)
                    .filter(x -> !x.equals(""))
                    .map(x -> x.replaceAll(ONLY_NUMBER_FORMAT, ""))
                    .map(Integer::valueOf)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

            int salePrice =  prices.stream().findFirst().map(x -> cards.stream()
                    .findFirst()
                    .map(y -> y / 100.0).map(y -> y * x)
                    .map(y -> x-y)
                    .orElse(x.doubleValue())).orElse(0.0).intValue();


            CoupangParseLog coupangWorkLog = new CoupangParseLog();
            coupangWorkLog.setUrl(url);
            coupangWorkLog.setSoldOut(soldOut);
            coupangWorkLog.setTitle(titleString);
            coupangWorkLog.setCardBonus(cards);
            coupangWorkLog.setPrices(prices);
            coupangWorkLog.setEtc(etc);
            coupangWorkLog.setWorkTime(LocalDateTime.now());
            coupangWorkLog.setSalePrice(salePrice);
            return coupangWorkLog;

        }
    }
}
