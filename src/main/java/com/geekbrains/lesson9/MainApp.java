package com.geekbrains.lesson9;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class MainApp {
    public static void main(String[] args) throws Exception {
        // подходим к аннотациям через рефлексию
        System.out.println(MainApp.class.getName());
        System.out.println(String.class);
        System.out.println(int.class);
        System.out.println(int[].class);
        System.out.println(int[][].class);
        System.out.println(void.class);

        Class stringClass1 = Class.forName("java.lang.String");

        System.out.println("=============================");

        Class classCat = Cat.class;
// есть класс хотим узнать его методы
        Method[] publicMethods = classCat.getMethods(); // даст список всех public методов
        for (Method o : publicMethods) System.out.println(o.getName());

        System.out.println("=============================");

        Method[] allMethods = classCat.getDeclaredMethods(); // даст список всех методов
        for (Method o : allMethods) System.out.println(o.getName());

        System.out.println("=============================");

        Method method = classCat.getMethod("publicMeow");

        System.out.println("=============================");

// попробуем вызвать метод
        Cat cat = new Cat(1,2,3);

        allMethods[0].setAccessible(true);
        allMethods[0].invoke(cat); // call private method !!!
        allMethods[1].setAccessible(true);
        allMethods[1].invoke(cat);

        int mods = allMethods[0].getModifiers();

        System.out.println("isStatic = " + Modifier.isStatic(mods));
        System.out.println("isFinal = " + Modifier.isFinal(mods));
        System.out.println("isPublic = " + Modifier.isPublic(mods));

        System.out.println("=============================");

// узнаем поля
        Field[] fields = classCat.getDeclaredFields();
        // посмотрим все поля
        System.out.println(Arrays.toString(fields));
        // установим значение
        fields[1].set(cat, 20);
        // конкретное
        System.out.println(fields[1].get(cat));

        System.out.println("=============================");

// создаем объект неизвестного класса)
        // newInstance - вернет сырой object
        Cat cat2 = (Cat)classCat.newInstance();
        System.out.println(cat2);
// создадим с использованием конструктора определенного

        // так как конструкторов может быть несколько у нас просят указать аргументы для уточнения
        Cat cat3 = (Cat)classCat
                .getConstructor(int.class, int.class, int.class)
                .newInstance(20, 30, 40);

        System.out.println("=============================");
        System.out.println(ClassLoader.class.getClassLoader());

// благодаря рефлексии можем на ходу подключать классы о которых ничего не знаем
        // загрузим класс прямо в коде

        // TODO Auto-generated catch block

        String urlString1 = "file:///tmp/Human.class";
//        String urlString2 = "file:///home/kde/Документы/java/VTB24/lesson9/src/Human.class";
        String urlString2 = "/home/kde/Документы/java/VTB24/lesson9/src/Human.class";
        String urlString3 = "file:///home/kde/Документы/java/VTB24/lesson9/src/Human.class";
        //URL classUrl;
        //classUrl = new URL(urlString2);
        //URL[] classUrls = { classUrl };
        //URLClassLoader ucl = new URLClassLoader(classUrls);

        ClassLoader classLoader = new URLClassLoader(new URL[] {new File(urlString2).toURL()});

        //System.out.println(classLoader.getParent());

        Class humanClass = classLoader.loadClass("Human");

        File myFolder = new File(urlString3);
        URLClassLoader classLoader2 = new URLClassLoader(new URL[]{myFolder.toURI().toURL()}, Thread.currentThread().getContextClassLoader());
        //Class<?> myClass = Class.forName("Human", true, classLoader2);
        //Human obj = (Human)myClass.newInstance();


        // Prepare source somehow.
        String source = "package test; public class Test { static { System.out.println(\"hello\"); } public Test() { System.out.println(\"world\"); } }";

// Save source in .java file.
        File root = new File("/home/kde/Документы/java/VTB24/lesson9/java_ogo"); // On Windows running on C:\, this is C:\java.
        File sourceFile = new File(root, "test/Test.java");
        sourceFile.getParentFile().mkdirs();

        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

// Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

// Load and instantiate compiled class.
        URLClassLoader classLoader3 = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
        Class<?> cls = Class.forName("test.Test", true, classLoader3); // Should print "hello".
        Object instance = cls.newInstance(); // Should print "world".
        System.out.println(instance); // Should print "test.Test@hashcode".

//*/
    }
}
