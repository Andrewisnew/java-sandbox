package edu.andrewisnew.java.spring.lesson01.block6;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

@Component
public class RangedValuesFormatter implements Formatter<RangedValues> {
    @Override
    public RangedValues parse(String text, Locale locale) throws ParseException {
        ArrayList<RangedValues.ValueWithUpperBoard> rv = new ArrayList<>();
        for (String s : text.split(";")) {
            String[] split = s.split(",");
            rv.add(new RangedValues.ValueWithUpperBoard(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
        return new RangedValues(rv);    }

    @Override
    public String print(RangedValues object, Locale locale) {
        return object.toString();
    }
}
