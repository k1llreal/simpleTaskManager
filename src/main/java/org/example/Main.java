package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "McLaren";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/taskmanager";

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        while (true) {
            System.out.println("================================");
            System.out.println("1. Показать список всех задач");
            System.out.println("2. Выполнить задачу");
            System.out.println("3. Создать задачу");
            System.out.println("4. Удалить задачу");
            System.out.println("5. Выйти");
            System.out.println("================================");

            int command = sc.nextInt();

            if (command == 1) {
                //объект, с помощью которого будут отправляться запросы в БД
                Statement statement = connection.createStatement();
                String SQL_SELECT_TASKS = "SELECT * FROM task ORDER BY id";
                //объект для хранения результата выполнения sql запроса
                ResultSet result = statement.executeQuery(SQL_SELECT_TASKS);

                while (result.next()) {
                    System.out.println(result.getInt("id") + " "
                            + result.getString("name") + " "
                            + result.getString("state")
                    );
                }
            } else if (command == 2) {
                String SQL_UPDATE_TASK = "UPDATE task SET state = 'DONE' WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_TASK);
                System.out.println("Введите идентификатор задачи:");
                int taskId = sc.nextInt();
                //вставляем вместо ? в запросе введенный id
                preparedStatement.setInt(1, taskId);
                preparedStatement.executeUpdate();
                System.out.println("Задача выполнена!");
            } else if (command == 3) {
                String SQL_INSERT_TASK = "INSERT INTO task (name, state) VALUES (?, 'IN_PROCESS')";
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_TASK);
                System.out.println("Введите название задачи:");
                sc.nextLine();
                String taskName = sc.nextLine();
                //вставляем вместо ? в запросе введенный id
                preparedStatement.setString(1, taskName);
                preparedStatement.executeUpdate();
                System.out.println("Задача " + taskName + " успешно добавлена!");
            } else if (command == 4) {
                String SQL_INSERT_TASK = "DELETE FROM task WHERE name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_TASK);
                System.out.println("Введите название удаляемой задачи:");
                sc.nextLine();
                String taskName = sc.nextLine();
                //вставляем вместо ? в запросе введенный id
                preparedStatement.setString(1, taskName);
                preparedStatement.executeUpdate();
                System.out.println("Задача " + taskName + " успешно удалена!");
            } else if (command == 5) {
                System.exit(0);
            } else {
                System.err.println("Команда не распознана");
            }
        }
    }
}
