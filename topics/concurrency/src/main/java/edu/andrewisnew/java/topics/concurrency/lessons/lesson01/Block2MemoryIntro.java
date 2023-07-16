package edu.andrewisnew.java.topics.concurrency.lessons.lesson01;

public class Block2MemoryIntro {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3}; //в HEAP - 4 + 4 + 4 + 4(arr.length) + 12(header) + padding
        A[] aArr = {new A(), new A(), new A()}; //в HEAP - 8 + 8 + 8 + 4(arr.length) + 12(header) + padding
        //8 - размер reference. Также в HEAP будет создано 3 A по 24 байта каждый
    }
}

/*
byte       1
short      2
int        4
float      4
long       8
double     8
char       2
boolean    ? (одно минимально адресуемое адресное пространство, зачастую 1)
reference  ? (от разрядности jvm 4 - 32-bit, 8 - 64-bit)
 */
class A {
    //header (8 - 32-bit, 12 - 64-bit)
    private int field1; //4
    private short field2; //2
    //padding (до кратности 8)
}
//12 + 4 + 2 + padding = 18 + padding = 24

/*
Память делится на HEAP и NON-HEAP. В HEAP адреса начала объектов кратны 8 (?). Это нужно для того чтобы, например,
хорошо работать со всеми архитектурами (некоторые не поддерживают невыравненные)

https://habr.com/ru/post/142409/ - Размеры массивов в Java
http://www.eclipse.org/mat/ - анализатор памяти
jmh - замер производительности


jol (https://github.com/openjdk/jol):

> C:\Users\Andrey\IdeaProjects\java\topics\concurrency\target\classes>java -jar C:\Users\Andrey\tools\jol-cli-latest.jar internals -cp . edu.andrewisnew.java.topics.concurrency
.lessons.lesson01.A
# WARNING: Unable to attach Serviceability Agent. Unknown address size: 12
# Running 64-bit HotSpot VM.
# Using compressed oop with 3-bit shift.
# Using compressed klass with 3-bit shift.
# WARNING | Compressed references base/shifts are guessed by the experiment!
# WARNING | Therefore, computed addresses are just guesses, and ARE NOT RELIABLE.
# WARNING | Make sure to attach Serviceability Agent to get the reliable addresses.
# Objects are 8 bytes aligned.
#                       ref, bool, byte, char, shrt,  int,  flt,  lng,  dbl
# Field sizes:            4,    1,    1,    2,    2,    4,    4,    8,    8
# Array element sizes:    4,    1,    1,    2,    2,    4,    4,    8,    8
# Array base offsets:    16,   16,   16,   16,   16,   16,   16,   16,   16

Instantiated the sample instance via default constructor.

edu.andrewisnew.java.topics.concurrency.lessons.lesson01.A object internals:
OFF  SZ    TYPE DESCRIPTION               VALUE
  0   8         (object header: mark)     0x0000000000000005 (biasable; age: 0)
  8   4         (object header: class)    0x00123840
 12   4     int A.field1                  0
 16   2   short A.field2                  0
 18   6         (object alignment gap)
Instance size: 24 bytes
Space losses: 0 bytes internal + 6 bytes external = 6 bytes total

 */
