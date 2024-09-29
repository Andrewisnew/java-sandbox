package edu.andrewisnew.java.generics.lesson01;

public class Launcher {
    public static void main(String[] args) {
        Box<String> stringBox = new Box<String>("Gina");
        String value = stringBox.getValue(); //в класс файле String value = (String)stringBox.getValue();

        //Integer i = stringBox.getValue();
        //edu\andrewisnew\java\generics\lesson01\Launcher.java:8: error: incompatible types: String cannot be converted to Integer
        //        Integer i = stringBox.getValue();
        Box<String> a = null;
        Box<? super Object> b;// = new Box<Integer>(1);
        //a = b;
        Box<Number> d = new Box<>(1);
        Box<? super Integer> supInt = d;
        Box<Integer> inT = new Box<>(1);
        Box<? extends Integer> extInt = new Box<>(null);
        extInt = inT;
        supInt = inT;
        String value1 = a.getValue();

        Box c = new Box("");

        c = stringBox;
        stringBox = c;
//        b = a;

    }
}
