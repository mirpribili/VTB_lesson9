package com.geekbrains.lesson9.homework;


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
@Table(title = "cats") // в какую бд будем сохранять котов
public class Cat {
    @Column
    int id;

    @Column
    String name;

    @Column
    int age;
/*
    public Cat(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

 */

    @Column
    String color;

    @Column
    int weight;

}
