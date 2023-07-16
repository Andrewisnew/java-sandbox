package edu.andrewisnew.java.topics.concurrency.lessons.lesson01;

public class Block4OperandStack {
    public static void main(String[] args) {
        int a = 42;
        int b = 2;
        int c = a + b + 3 * 4;
    }
}

/*
// class version 55.0 (55)
// access flags 0x21
public class edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example4 {

  // compiled from: Example4.java

  // access flags 0x1
  public <init>()V
   L0
    LINENUMBER 3 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
    RETURN
   L1
    LOCALVARIABLE this Ledu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example4; L0 L1 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x9
  public static main([Ljava/lang/String;)V
   L0
    LINENUMBER 5 L0
    BIPUSH 42
    ISTORE 1
   L1
    LINENUMBER 6 L1
    ICONST_2
    ISTORE 2
   L2
    LINENUMBER 7 L2
    ILOAD 1
    ILOAD 2
    IADD
    BIPUSH 12
    IADD
    ISTORE 3
   L3
    LINENUMBER 8 L3
    RETURN
   L4
    LOCALVARIABLE args [Ljava/lang/String; L0 L4 0
    LOCALVARIABLE a I L1 L4 1
    LOCALVARIABLE b I L2 L4 2
    LOCALVARIABLE c I L3 L4 3
    MAXSTACK = 2
    MAXLOCALS = 4
}


Рассботрим метод main.
В конце можно видеть объявление переменных их имя, тип, область видимости, индекс

LINENUMBER 5 L0
BIPUSH 42
ISTORE 1

на вершину стека кладется 42 и значение с вершины записывается в переменную с индексом 1

...
Рассмотрим как будет выглядеть operand стэк после каждой команды (предположим, что стэк цикличен, судя по MAXSTACK = 2 так оно и есть):
LINENUMBER 7 L2
_
_ <-
ILOAD 1
_
42 <-
ILOAD 2
1 <-
42
IADD
_
43 <-
BIPUSH 12
12 <-
43
IADD
_
55 <-
ISTORE 3 - сохраняем 55 в переменную 3
_
_ <-

NOTE: На входе и выходе операнд стэк должен быт пустым

 */