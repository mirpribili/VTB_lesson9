package com.geekbrains.lesson9;

import java.sql.*;

public class MainSQlite {
    private static Connection connection;
    private static Statement statement;

    public static void main(String[] args) {
        try {
            connect();
            // что нужно, чтобы отправить запрос в базу
                // insertExample();


            // вычитываем данные из таблицы
            //  получаем из метода таблицу NxN
            //  в таблице есть курсор и по началу он никуда не указывает, делаем Next
            // next вернет false когда таблица кончится
                // selectExample();

            //  заменяем строчку
                // updateExample();

            // удаление
                // deleteEx();
                // clearTableExample();
                // dropTableExample();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disconnect();
        }

    }

    private static void dropTableExample() throws SQLException {
        statement.executeUpdate("DROP TABLE students");
    }

    private static void clearTableExample() throws SQLException {
        statement.executeUpdate("DELETE FROM students;");
    }

    private static void deleteEx() throws SQLException {
        statement.executeUpdate("DELETE FROM students WHERE id = 7;");
    }

    private static void updateExample() throws SQLException {
        statement.executeUpdate("UPDATE students SET score = 37 WHERE id = 1;");
    }

    private static void selectExample() {
        try(ResultSet rs = statement.executeQuery("SELECT * FROM students;"))  {
            while (rs.next()){
                System.out.println(rs.getInt(1) + " " + rs.getString("name") + " " + rs.getInt("score"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void insertExample() throws SQLException {
        statement.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob3', 100);");
    }

    public static void connect() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC"); // устаревшая строка загружающая класс в память
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");// тут порт localhost, login, pass
            statement = connection.createStatement();
        }catch (ClassNotFoundException | SQLException e){
            throw new SQLException("Unable to connect");
        }

    }

    public static void disconnect(){
        // закрываем в обратном порядке по важности
        try {
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
