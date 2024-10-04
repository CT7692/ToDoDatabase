package org.example;

import java.util.Arrays;
import java.util.Scanner;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ToDoOperation {
    int option;
    Scanner sc;
    Configuration configuration;
    SessionFactory sessionFactory;
    Session session;
    TaskDao taskDao;
    String[] strCategories = {"PERSONAL", "WORK", "HOBBY", "OTHER"};
    List<String> categories = Arrays.asList(strCategories);

    public ToDoOperation() {
        option = 7;
        sc = new Scanner(System.in);
        configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        taskDao = TaskDaoFactory.getTaskDao(session);
    }

    public void menu() {
        System.out.println("################# TO DO #################");
        System.out.println("1: Add Task");
        System.out.println("2: Update Task");
        System.out.println("3: Delete Task");
        System.out.println("4: View Tasks");
        System.out.println("5: Exit");
        System.out.print("Enter option: ");
    }

    public void start() {
        while(option != 6) {
            menu();
            option = sc.nextInt();

            switch (option) {
                case 1: {
                    Task newTask = createTask();
                    if(categories.contains(newTask.getCategory()))
                        taskDao.addTask(newTask);
                    else
                        System.out.println("Task not added from invalid category.");
                    break;
                }

                case 2: {
                    updateTask();
                    break;
                }

                case 3: {
                    findTasktoDelete();
                    break;
                }

                case 4: {
                    printAllTasks();
                    break;
                }
            }
        }
        System.out.println("Good-bye.");
    }

    public Task createTask() {
        System.out.println("################# NEW TASK #################");
        System.out.print("Enter Task ID Number: ");
        int num = sc.nextInt();

        System.out.print("Enter Task Description: ");
        String description = sc.next();
        description += sc.nextLine();

        System.out.print("Enter Task Category (PERSONAL, WORK, HOBBY, OTHER): ");
        String category = sc.next();
        category += sc.nextLine();

        return new Task(num, description, category);
    }

    public void updateTask() {
        System.out.println("################# UPDATE TASK #################");
        System.out.print("Enter Task ID Number: ");
        int num = sc.nextInt();

        System.out.print("Task complete? (true/false): ");
        String status = sc.next();
        status += sc.nextLine();

        taskDao.updateTask(num, status);
    }

    public void findTasktoDelete() {
        System.out.println("################# DELETE TASK #################");
        System.out.print("Enter Task ID Number: ");
        int num = sc.nextInt();

        taskDao.deleteTask(num);
    }

    public void printAllTasks() {
        System.out.println("################# ALL TASKS #################");
        List<Task> tasks = taskDao.getTasks();
        if(tasks.size() > 0) {
            for(Task task: tasks) System.out.println(task);
            System.out.print("\n");
        }
        else
            System.out.println("No tasks yet.");
    }
}
