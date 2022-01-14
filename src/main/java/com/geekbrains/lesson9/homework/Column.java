package com.geekbrains.lesson9.homework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
1.
    Реализуйте возможность разметки класса с помощью набора ваших собственных аннотаций
    (@Table(title), @Column). Напишите обработчик аннотаций, который позволит по размеченному
    классу построить таблицу в базе данных.
2.
    * Второй обработчик аннотаций должен уметь добавлять объект размеченного класса в
    полученную таблицу. Замечание: Считаем что в проекте не связанных между собой сущностей, чтобы не
    продумывать логику их взаимодействия.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
}