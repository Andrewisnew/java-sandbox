package edu.andrewisnew.java.spring.lesson01.block6;

import java.util.List;

public record RangedValues(List<ValueWithUpperBoard> ranges) {

    record ValueWithUpperBoard(int value, int upperBoard) {}
}
