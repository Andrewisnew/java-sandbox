package edu.andrewisnew.java.generics.lesson01;

public class Box<V> {
    V value;

    public Box(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }
}
//компилятор сотрет V в Object, проверит совместимость типов и добавит касты в местах вызова getValue()
