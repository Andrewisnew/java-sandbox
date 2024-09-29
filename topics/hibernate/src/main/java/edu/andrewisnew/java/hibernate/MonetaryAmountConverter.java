package edu.andrewisnew.java.hibernate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

//@Converter(autoApply = true)// Автоматически применяется к полям типа MonetaryAmount
public class MonetaryAmountConverter implements AttributeConverter<Item.MonetaryAmount, String> {//также конвертеры можно использовать при наследовании
    @Override
    public String convertToDatabaseColumn(Item.MonetaryAmount monetaryAmount) {
        return monetaryAmount.toString();
    }
    @Override
    public Item.MonetaryAmount convertToEntityAttribute(String s) {
        return Item.MonetaryAmount.fromString(s);
    }
}
