package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TaskManager {
    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) {
        System.out.println(options());
        tasks = loadDataToTab(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String option = scanner.nextLine();
            switch (option) {
                case "add":
                    addOption();
                    System.out.println(options());
                    break;
                case "remove":
                    removeOption();
                    break;
                case "list":
                    printTab(tasks);
                    System.out.println(ConsoleColors.BLUE + "Do you want to close the list?\n"+ConsoleColors.RESET+"YES/NO");
                    if (scanner.nextLine().toLowerCase().equals("yes")) {
                        System.out.println(options());
                    }
                    break;
                case "exit":
                    System.out.println(ConsoleColors.RED + "BYE BYE" + ConsoleColors.RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "Incorrect option" + ConsoleColors.RESET);
                    System.out.println(options());
                    break;
            }
        }
    }

    public static String options() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        return String.join("\n", Arrays.asList(OPTIONS));
    }

    public static String[][] loadDataToTab(String fileName) {
        Path dir = Paths.get(fileName);
        if (!Files.exists(dir)) {
            System.out.println("File not exist.");
            System.exit(0);
        }
        String[][] tab = null;
        try {
            List<String> strings = Files.readAllLines(dir);
            tab = new String[strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tab[i][j] = split[j];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tab;
    }

    public static void printTab(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }
    private static void addOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String description = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task important? true/false");
        String important = scanner.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = important;
    }
    public static void removeOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.BLUE + "Select option to remove: "+ConsoleColors.RESET);
        int option = Integer.parseInt(scanner.nextLine());
        String tab2=String.join(" ", Arrays.asList(tasks[option]));
        System.out.println(ConsoleColors.BLUE+"Are You sure want to remove: "+ConsoleColors.RESET+tab2+"\n"+"YES/NO");
        String agree = scanner.nextLine();
        if(agree.equalsIgnoreCase("yes")) {
            tasks = ArrayUtils.remove(tasks, option);
            System.out.println(options());
        }
        else {
            System.out.println(options());
        }
    }

}