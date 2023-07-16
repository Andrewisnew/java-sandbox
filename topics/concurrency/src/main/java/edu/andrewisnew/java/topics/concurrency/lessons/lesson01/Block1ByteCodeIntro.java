package edu.andrewisnew.java.topics.concurrency.lessons.lesson01;

public class Block1ByteCodeIntro {
    private int field = 42;
}

/*
Java concurrency in practice

 Комментарий расположен снизу для возможности его редактирования с отсутствем влияния на расположения кода относительно строк
 To see bytecode javap -c "class"<br>

 > javap -c edu.andrewisnew.java.topics.concurrency.lessons.lesson01.Example1
 Compiled from "Example1.java"
 public class edu.andrewisnew.java.topics.concurrency.lessons.lesson01.Example1 {
   public edu.andrewisnew.java.topics.concurrency.lessons.lesson01.Example1();
     Code:
        0: aload_0
        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
        4: return
 }

Или из idea View -> Show Bytecode

// class version 55.0 (55)
// access flags 0x21
public class edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1 {

  // compiled from: Example1.java

  // access flags 0x2
  private I field

  // access flags 0x1
  public <init>()V
   L0
    LINENUMBER 3 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
   L1
    LINENUMBER 4 L1
    ALOAD 0
    BIPUSH 42
    PUTFIELD edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1.field : I
    RETURN
   L2
    LOCALVARIABLE this Ledu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1; L0 L2 0
    MAXSTACK = 2
    MAXLOCALS = 1
}


Был создан конструктор по умолчанию
 public <init>()V
   L0
    LINENUMBER 3 L0
    ALOAD 0
    INVOKESPECIAL java/lang/Object.<init> ()V
   L1
    LINENUMBER 4 L1
    ALOAD 0
    BIPUSH 42
    PUTFIELD edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1.field : I
    RETURN
   L2
    LOCALVARIABLE this Ledu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1; L0 L2 0
    MAXSTACK = 2
    MAXLOCALS = 1

Он public и возвращает void.
L0, L1, ... - метки
Тело метода окружено метками (как можно заметить далее, каждая строка тоже начинается с новой метки и инструкции
LINENUMBER с номером строки и предшествующей меткой).

ALOAD 0 - значит загрузить из таблицы локальных переменных для текущего метода переменную с индексом 0, и положить на вершину стека.
Локалные переменные можно увидет в конце метода:
LOCALVARIABLE this Ledu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1; L0 L2 0
MAXSTACK = 2
MAXLOCALS = 1

LOCALVARIABLE - локальная переменная с именем this типа edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1
ограничена метками L0 L2 и имеет индекс 0

INVOKESPECIAL java/lang/Object.<init> ()V - вызов конструктора базового класса

Между L1 и L2 производится инициализация field и завершение метода:
L1
    LINENUMBER 4 L1
    ALOAD 0
    BIPUSH 42
    PUTFIELD edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1.field : I
    RETURN
L2

LINENUMBER 4 L1 - определение строки
ALOAD 0 - размещение this на вершине стека
BIPUSH 42 - на вершину стека кладем int 42 (push a byte onto the stack as an integer value)
PUTFIELD edu/andrewisnew/java/topics/concurrency/lessons/lesson01/Example1.field : I - значение из вершины стека записать
в поле field объекта, который идет следом за вершиной.

Следующие параметры рассчитываются jvm при создании байткода для определеня количества необходимой памяти.
Стек измеряется в количестве интовых значений
MAXLOCALS = 1
MAXSTACK = 2
 */
