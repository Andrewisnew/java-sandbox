package edu.andrewisnew.java.spring.lesson01.block6;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RangedValuesConverter implements Converter<String, RangedValues> {
    @Override
    public RangedValues convert(String source) {
        ArrayList<RangedValues.ValueWithUpperBoard> rv = new ArrayList<>();
        for (String s : source.split(";")) {
            String[] split = s.split(",");
            rv.add(new RangedValues.ValueWithUpperBoard(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }
        return new RangedValues(rv);
    }
}
