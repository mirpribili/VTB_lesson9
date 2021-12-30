package com.geekbrains.lesson9;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* где видна наша анотация (RUNTIME = во время выполнения)*/
@Retention(RetentionPolicy.RUNTIME)
// куда подключаем тут к методам
@Target(ElementType.METHOD)
public @interface MyAnno {
    int priority() default 1; // к сожаленю сюда нельзя добавить проверку
}
