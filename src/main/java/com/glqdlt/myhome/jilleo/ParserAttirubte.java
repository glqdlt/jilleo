package com.glqdlt.myhome.jilleo;

import javax.persistence.*;
import java.util.Optional;

/**
 * @author glqdlt
 */
@Entity
@DiscriminatorColumn(name ="type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "parse_attribute")
public abstract class ParserAttirubte {
    private Integer id;
    private ParserLogEntity parserLogEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name ="psrseId")
    public ParserLogEntity getParserLogEntity() {
        return parserLogEntity;
    }

    public void setParserLogEntity(ParserLogEntity parserLogEntity) {
        this.parserLogEntity = parserLogEntity;
    }

    public abstract Integer findType();

    public abstract Object convertValue();

    public abstract String convertAttirubteString();

    @Entity
    @DiscriminatorValue("1")
    public static class Pricess extends ParserAttirubte{

        private Integer prices;

        public Integer getPrices() {
            return prices;
        }

        public void setPrices(Integer prices) {
            this.prices = prices;
        }

        @Override
        public Integer findType() {
            return 1;
        }

        @Override
        public Object convertValue() {
            return getPrices();
        }

        @Override
        public String convertAttirubteString() {
            return getPrices().toString();
        }
    }
    @Entity
    @DiscriminatorValue("2")
    public static class CaldSale extends ParserAttirubte{
        private Integer cardSalePercent;

        public Integer getCardSalePercent() {
            return cardSalePercent;
        }

        public void setCardSalePercent(Integer cardSalePercent) {
            this.cardSalePercent = cardSalePercent;
        }

        @Override
        public Integer findType() {
            return 2;
        }

        @Override
        public Object convertValue() {
            return getCardSalePercent();
        }

        @Override
        public String convertAttirubteString() {
            return Optional.ofNullable(getCardSalePercent()).map(x -> x+"%").orElse("-");
        }
    }

    @Entity
    @DiscriminatorValue("3")
    public static class EtcString extends ParserAttirubte{

        private String etc;

        public String getEtc() {
            return etc;
        }

        public void setEtc(String etc) {
            this.etc = etc;
        }

        @Override
        public Integer findType() {
            return 3;
        }

        @Override
        public Object convertValue() {
            return etc;
        }

        @Override
        public String convertAttirubteString() {
            return etc;
        }
    }
}
