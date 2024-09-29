package edu.andrewisnew.java.hibernate;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "width1", unique = false, nullable = true, insertable = true, updatable = true)
    private int width;

    @Column
    private long height;

    @Column(name = "IMPERIALWEIGHT_LB")
    @ColumnTransformer(
            read = "IMPERIALWEIGHT_LB / 2.20462",
            write = "? * 2.20462"
    )
    protected double metricWeightKg;//HQL запросы также будут трансформировать

    @Formula("width1 * height")
    private long area;


    @CreationTimestamp
    protected Instant createTime;

    @UpdateTimestamp
    protected Instant lastModifyTime;

    @Column(insertable = false)
    @ColumnDefault("1.00")
    protected BigDecimal initialPrice;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Embedded //достаточно отметить что-то одно @Embeddable/@Embedded
    private Category mainCategory;

    @AttributeOverrides({
            @AttributeOverride(name = "group",
                    column = @Column(name = "add_group")),
            @AttributeOverride(name = "name",
                    column = @Column(name = "add_name", length = 5))
    })
    private Category additionalCategory;

    private Comment comment;

    @Convert(converter = MonetaryAmountConverter.class) //не обязателен так как объявлен autoApply
    private MonetaryAmount monetaryAmount;


    @org.hibernate.annotations.Type(
            type = "edu.andrewisnew.java.hibernate.MonetaryAmountUserType",
            parameters = {@Parameter(name = "convertTo", value = "USD")}
    )
    @org.hibernate.annotations.Columns(columns = {
            @Column(name = "BUYNOWPRICE_AMOUNT"),
            @Column(name = "BUYNOWPRICE_CURRENCY", length = 3)
    })
    protected MonetaryAmount buyNowPrice;
    @org.hibernate.annotations.Type(
            type = "edu.andrewisnew.java.hibernate.MonetaryAmountUserType",
            parameters = {@Parameter(name = "convertTo", value = "EUR")}
    )
    @org.hibernate.annotations.Columns(columns = {
            @Column(name = "INITIALPRICE_AMOUNT"),
            @Column(name = "INITIALPRICE_CURRENCY", length = 3)
    })
    protected MonetaryAmount minPrice;

    public Item() {
    }

    public Item(int width, long height, Color color, Category mainCategory, Category additionalCategory, Comment comment, MonetaryAmount monetaryAmount,
                MonetaryAmount buyNowPrice, MonetaryAmount minPrice) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.mainCategory = mainCategory;
        this.additionalCategory = additionalCategory;
        this.comment = comment;
        this.monetaryAmount = monetaryAmount;
        this.buyNowPrice = buyNowPrice;
        this.minPrice = minPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", metricWeightKg=" + metricWeightKg +
                ", area=" + area +
                ", createTime=" + createTime +
                ", lastModifyTime=" + lastModifyTime +
                ", initialPrice=" + initialPrice +
                ", color=" + color +
                ", mainCategory=" + mainCategory +
                ", additionalCategory=" + additionalCategory +
                ", comment=" + comment +
                ", monetaryAmount=" + monetaryAmount +
                ", buyNowPrice=" + buyNowPrice +
                ", minPrice=" + minPrice +
                '}';
    }

    public enum Color {
        RED, GREEN
    }

    @Embeddable
    public static class Category {
        @Column(name = "`group`")
        String group;
        String name;

        public Category(String group, String name) {
            this.group = group;
            this.name = name;
        }

        public Category() {
        }
    }

    public static class Comment implements Serializable {
        String comment;

        public Comment(String comment) {
            this.comment = comment;
        }

        public Comment() {
        }
    }

    public static class MonetaryAmount implements Serializable {
        int amount;
        Currency currency;

        public MonetaryAmount() {
        }

        public MonetaryAmount(int amount, Currency currency) {
            this.amount = amount;
            this.currency = currency;
        }

        public int getAmount() {
            return amount;
        }

        public Currency getCurrency() {
            return currency;
        }

        public String toString() {
            return amount + " " + currency;
        }
        public static MonetaryAmount fromString(String s) {
            String[] split = s.split(" ");
            return new MonetaryAmount(
                    Integer.parseInt(split[0]),
                    Currency.getInstance(split[1])
            );
        }
    }


}
