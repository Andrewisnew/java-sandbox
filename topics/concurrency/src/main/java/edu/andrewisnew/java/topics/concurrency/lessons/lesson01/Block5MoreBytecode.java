package edu.andrewisnew.java.topics.concurrency.lessons.lesson01;

public class Block5MoreBytecode {
    public static void main(String[] args) {
        String a = new String("lesson");
        int b = 1;
        System.out.println(concat(a, b));
    }

    private static int concat(String str, int num) {
        return (str + num).length();
    }
}


/*
/ class version 55.0 (55)
// access flags 0x21
public class edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example5 {

  // compiled from: Example5.java
  // access flags 0x19
  public final static INNERCLASS java/lang/invoke/MethodHandles$Lookup java/lang/invoke/MethodHandles Lookup

  // access flags 0x1
  public <init>()V
   L0
    LINENUMBER 3 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
    RETURN
   L1
    LOCALVARIABLE this Ledu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example5; L0 L1 0
    MAXSTACK = 1
    MAXLOCALS = 1

  // access flags 0x9
  public static main([Ljava/lang/String;)V
   L0
    LINENUMBER 5 L0
    NEW java/lang/String
    DUP
    LDC "lesson"
    INVOKESPECIAL java/lang/String.<init> (Ljava/lang/String;)V
    ASTORE 1
   L1
    LINENUMBER 6 L1
    ICONST_1
    ISTORE 2
   L2
    LINENUMBER 7 L2
    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    ALOAD 1
    ILOAD 2
    INVOKESTATIC edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example5.concat (Ljava/lang/String;I)I
    INVOKEVIRTUAL java/io/PrintStream.println (I)V
   L3
    LINENUMBER 8 L3
    RETURN
   L4
    LOCALVARIABLE args [Ljava/lang/String; L0 L4 0
    LOCALVARIABLE a Ljava/lang/String; L1 L4 1
    LOCALVARIABLE b I L2 L4 2
    MAXSTACK = 3
    MAXLOCALS = 3

  // access flags 0xA
  private static concat(Ljava/lang/String;I)I
   L0
    LINENUMBER 11 L0
    ALOAD 0
    ILOAD 1
    INVOKEDYNAMIC makeConcatWithConstants(Ljava/lang/String;I)Ljava/lang/String; [
      // handle kind 0x6 : INVOKESTATIC
      java/lang/invoke/StringConcatFactory.makeConcatWithConstants(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;
      // arguments:
      "\u0001\u0001"
    ]
    INVOKEVIRTUAL java/lang/String.length ()I
    IRETURN
   L1
    LOCALVARIABLE str Ljava/lang/String; L0 L1 0
    LOCALVARIABLE num I L0 L1 1
    MAXSTACK = 2
    MAXLOCALS = 2
}

Рассмотрим main
L0
    LINENUMBER 5 L0
    _
    _
    _ <
    NEW java/lang/String (сознается строка в хипе, адрес кладется на стэк. Адрес 0xFFFFEEEE)
    _
    _
    0xFFFFEEEE <
    DUP
    _
    0xFFFFEEEE <
    0xFFFFEEEE
    LDC "lesson" (положить ссылку на "lesson" в стэк 0xEEEEAAAA)
    0xEEEEAAAA <
    0xFFFFEEEE
    0xFFFFEEEE
    INVOKESPECIAL java/lang/String.<init> (Ljava/lang/String;)V (вызов метода с аргументом на вершине стэка на объекте нахдящимся сразу под вершиной)
    _
    _
    0xFFFFEEEE <
    ASTORE 1 (сохранение в переменную 1)
    _
    _
    _ <
L1
    LINENUMBER 6 L1
    _
    _
    _ <
    ICONST_1
    _
    _
    1 <
    ISTORE 2
    _
    _
    _ <
L2
    LINENUMBER 7 L2
    GETSTATIC java/lang/System.out : Ljava/io/PrintStream; (получение ссылки на java/lang/System.out, 0xAAAAEEEE)
    _
    _
    0xAAAAEEEE <
    ALOAD 1
    _
    0xFFFFEEEE <
    0xAAAAEEEE
    ILOAD 2
    1 <
    0xFFFFEEEE
    0xAAAAEEEE
    INVOKESTATIC edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example5.concat (Ljava/lang/String;I)I (вызывается статик метод Example5.concat с аргументами (Ljava/lang/String;I)) и возвращает на стэк I
    _
    7 <
    0xAAAAEEEE
    INVOKEVIRTUAL java/io/PrintStream.println (I)V
    _
    _
    _ <
L3
    LINENUMBER 8 L3
    RETURN
L4


 */