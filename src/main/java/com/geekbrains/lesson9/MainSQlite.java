package com.geekbrains.lesson9;

import java.sql.*;

public class MainSQlite {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement; // для защиты от добавления sql команд в gui пользователем

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


            //transactionAndPreparedStatementExample();


            //batchEx();

            // rollbackEx();

            clearTableExample();
//  до этого момента мы работали с интерфейсами и совсем не создавали объектов(
// а тогда вопрос а где реализация? Если мы посмотрим
                // Connection
                // Statement
                // PreparedStatement
            // то все это интерфейсы sqlite-jdbc.... а где реализация?
                    // в JDBC  драйвере.
// Плюсы?
                // Не работая с реализацией мы спокойно можем менять БД SQLite  на БД PostgeSQL  и тд.


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            disconnect();
        }

    }

    private static void rollbackEx() throws SQLException {
        clearTableExample();
        statement.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob1', 80);");
        Savepoint savepoint1 = connection.setSavepoint(); //  запомнили состояние таблицы
        statement.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob2', 181);");
        connection.rollback(savepoint1);
        statement.executeUpdate("INSERT INTO students (name, score) VALUES ('Bob3', 80);");
// Вопрос сколько студентов будет в таблице?
        // всего 1 и 1й. тк включен автокомит а почему еще?
    }

    private static void batchEx() throws SQLException {
        long time = System.currentTimeMillis();
        clearTableExample();

        // for speed
        connection.setAutoCommit(false);

        for (int i = 0; i < 100; i++ ){
            preparedStatement.setString(1, "Bob" + (i + 1));
            //preparedStatement.setInt(2, 50);
            preparedStatement.setObject(2, 50); // <------------!!!
            //preparedStatement.executeUpdate(); // возвращает int кол-ва затронутых строк в бд.
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch(); // вернет массив где, будет отображено кол-во строк для каждого запроса.

        // for speed
        connection.commit();  // connection.setAutoCommit(true);

        System.out.println(System.currentTimeMillis() - time);
    }

    private static void transactionAndPreparedStatementExample() throws SQLException {
        long time = System.currentTimeMillis();
        clearTableExample();

        // for speed
        connection.setAutoCommit(false);

        for (int i = 0; i < 100; i++ ){
            preparedStatement.setString(1, "Bob" + (i + 1));
            //preparedStatement.setInt(2, 50);
            preparedStatement.setObject(2, 50); // <------------!!!
            preparedStatement.executeUpdate(); // возвращает int кол-ва затронутых строк в бд.
        }

        // for speed
        connection.commit();  // connection.setAutoCommit(true);

        System.out.println(System.currentTimeMillis() - time); // OMG 11.2s for 100 queries!!
        // after improvement for speed  100 queries == 0.3s !!

        // Почему такая разница в скорости? тк по умолч. каждый запрос выполнялся как самостоятельная транзакция
        // Плюсы - скорость
        // Минусы - при ошибке в цикле мы потеряем все запросы не записав в базу
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

            //  плюс пре компиляция см ниже. Защита от gui инъекций
            preparedStatement = connection.prepareStatement("INSERT INTO students (name, score) VALUES (?, ?);");
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
