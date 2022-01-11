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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainApp {
    public static void main(String[] args) throws Exception {

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

        // This URL for a directory will be searched *recursively*
        URL classes =
                new URL( "file:////home/kde/Документы/java/VTB24/untitled1/target/classes/" );

        ClassLoader custom =
                new URLClassLoader( new URL[] { classes }, systemClassLoader );

        // this class should be loaded from your directory
        Class< ? > clazz = custom.loadClass( "Human" );
        // this class will be loaded as well, because you specified the system
        // class loader as the parent
        Class< ? > clazzString = custom.loadClass( "java.lang.String" );







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


        System.out.println("=============================______");
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
        //File root2 = new File("file:///home/kde/Документы/java/VTB24/lesson9/src/");

        URLClassLoader classLoader3 = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
        Class<?> cls = Class.forName("test.Test", true, classLoader3); // Should print "hello".
        Object instance = cls.newInstance(); // Should print "world".
        System.out.println(instance); // Should print "test.Test@hashcode".

//*/

// благодаря рефлексии можем на ходу подключать классы о которых ничего не знаем
        // загрузим класс прямо в коде

        // TODO Auto-generated catch block
        System.out.println("=============================_ Human");
        String urlString2 = "/home/kde/Документы/java/VTB24/lesson9/src/main/java/";
        ClassLoader classLoader = new URLClassLoader(new URL[] {new File(urlString2).toURL()});
        Class<?> humanClass = classLoader.loadClass("Human");

        File myFolder = new File(urlString2);
        URLClassLoader classLoader2 = new URLClassLoader(new URL[]{myFolder.toURI().toURL()}, Thread.currentThread().getContextClassLoader());
        Class<?> humanClass2 = Class.forName("Human", true, classLoader2);
        System.out.println("=============================^ Human");

        Object humanObj = humanClass.getConstructor(String.class, int.class).newInstance("Booba", 30);
        Method greetingsMethod = humanClass.getDeclaredMethod("greetings");
        greetingsMethod.invoke(humanObj);

        //

        System.out.println("============================= getDeclaredMethods");
        Class testClass = TestClassAnno.class;
        Method[] methods = testClass.getDeclaredMethods();

// переберем все методы и найдем помеченные аннотацией
        for (Method o: methods){
            if(o.isAnnotationPresent(MyAnno.class)){ // MyAnno.class - у интерфейса запросили класс
                o.invoke(null); // тк это статический метод то делаем нулл
            }
        }
        System.out.println("============================= executionList.sort");
// а теперь вызовем методы с аннотацией в зависимости от приоритета
        List<Method> executionList = new ArrayList<>();
        for (Method o: methods){
            if(o.isAnnotationPresent(MyAnno.class)){
                executionList.add(o); // выбрали все с аннотацией в список
            }
        }
        // сортируем по приоритету
        Comparator comparator = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Method o2b = (Method)o2;
                Method o1b = (Method)o1;
                return o2b.getAnnotation(MyAnno.class).priority() - o1b.getAnnotation(MyAnno.class).priority();
            }
        };
        executionList.sort((comparator));
                //(o1, o2) -> o2.getAnnotation(MyAnno.class).priority() - o1.getAnnotation(MyAnno.class).priority() ));
        executionList = (List<Method>) Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(MyAnno.class))
                .sorted(comparator)
                .collect(Collectors.toList());
        for (Method m : executionList){
            m.invoke(null);
        }
    }
}
