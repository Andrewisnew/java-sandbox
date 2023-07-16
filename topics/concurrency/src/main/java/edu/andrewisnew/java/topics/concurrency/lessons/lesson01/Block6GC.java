package edu.andrewisnew.java.topics.concurrency.lessons.lesson01;

import java.math.BigDecimal;

public class Block6GC {
    public static void main(String[] args) {
        System.out.println(1.);
        System.out.println(String.format("Double %s", BigDecimal.valueOf(1.).toPlainString()));
    }
}


/*
GC основан на гипотезе о поколениях: довольно много маложивущих объектов и мало долгоживущих.
Принято разделить на old generation и young generation.
Serial GC - один из сборщиков, его обзор ниже.
Делит heap на old и young 2 к 1.
Young gen делится на eden, survivor1, survivor2 8 к 1 к 1.
При создании объекта он создается в eden.
Если в eden места не хватает, jvm делает stop the world (останавливает все потоки).
Поток останавливается в так называемом safe point. В jvm hotspot это выходиз блока, ...
Поток в safe point проверяет собрался ли его кто-то остановить, если да, то останавливается.
jvm дожидается остановки всех. Приходит gc, строит граф достижимости. Начинает построения графа с GC ROOTS, в
них входят: стэки потоков, класс-литералы, статические переменные, native objects. Далее всех выживших перемещает
в survivor1, после чего считает, что eden пуст. Возобнавляет потоки.
Когда eden снова заполнтся, он выживших перемещает в survivor2 (и всех из survivor1 тоже).
Так он будет кидать попеременно в surv1, surv2.
Как только объект переживет 8 перекидок (дефолт), он отправляется в old. Информация про количество перекидок хранится в
header объекта.
Наличие surv1 и surv2 превентит дефрагментацию.
Гигансткие объекты (акселераты) сразу отправляются в old gen.

Вообще есть minor gc (young gen), major gc (old gen), full gc.
Major gc проводит дефрагментацию после чистки.

Parallel gc разделяет память в old gen между потоками.

https://habr.com/ru/post/269621/

 */