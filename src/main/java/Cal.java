import java.util.ArrayList;
import java.util.Scanner;

import tasks.Deadline;
import tasks.Event;
import tasks.Task;
import tasks.Todo; 

public class Cal {
    static String line = "____________________________________________________________";
    private static ArrayList<Task> tasks = new ArrayList<>();

    public static void greet() {
        String name = "Cal";
        // String logo = " ____        _        \n"
        //         + "|  _ \\ _   _| | _____ \n"
        //         + "| | | | | | | |/ / _ \\\n"
        //         + "| |_| | |_| |   <  __/\n"
        //         + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        //System.out.println(logo);
    }
    
    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void list() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            String str = String.format("%d. %s", i + 1, t);
            System.out.println(str);
        }
    }

    public static void add(String description) {
        Task t = new Todo(description);
        tasks.add(t);
        System.out.println("Got it. I've added this task:");
        System.out.println(t);
        System.out.println(String.format("Now you have %d tasks in the list.", tasks.size()));
    }

    public static void add(String description, String by) {
        Task t = new Deadline(description, by);
        tasks.add(t);
        System.out.println("Got it. I've added this task:");
        System.out.println(t);
        System.out.println(String.format("Now you have %d tasks in the list.", tasks.size()));
    }

    public static void add(String description, String startDate, String endDate) {
        Task t = new Event(description, startDate, endDate);
        tasks.add(t);
        System.out.println("Got it. I've added this task:");
        System.out.println(t);
        System.out.println(String.format("Now you have %d tasks in the list.", tasks.size()));
    }

    public static void mark(int taskNum) throws CalException {
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new CalException("Invalid task number!");
        }
        Task t = tasks.get(taskNum - 1);
        t.setStatus();
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(t);
    }

    public static void unmark(int taskNum) throws CalException {
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new CalException("Invalid task number!");
        }
        Task t = tasks.get(taskNum - 1);
        t.setStatus();  
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(t);
    }

    public static void run() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            try {
                String input = sc.nextLine();
                String[] tokens = input.split(" ");
                String command = tokens[0].toLowerCase();
                
                String description = "";

                System.out.println(line);
                switch(command) {
                    case "bye":
                        sc.close();
                        return;
                    case "list":
                        list();
                        break;
                    case "mark":
                        if (tokens.length < 2) {
                            throw new CalException("Task number not provided!");
                        }
                        mark(Integer.parseInt(tokens[1]));
                        break;
                    case "unmark":
                        if (tokens.length < 2) {
                            throw new CalException("Task number not provided!");
                        }
                        unmark(Integer.parseInt(tokens[1]));
                        break;
                    case "todo":
                        description = input.substring(5);
                        if (description.isBlank()) {
                            throw new CalException("Task description not provided!");
                        }
                        add(description);
                        break;
                    case "deadline":
                        int byIndex = input.indexOf("/by");
                        String by = "";

                        try {
                            description = input.substring(9, byIndex).strip();
                        } catch (StringIndexOutOfBoundsException e){
                            throw new CalException("Task description not provided!");
                        }

                        try {
                            by = input.substring(byIndex + 4).strip();
                        } catch (StringIndexOutOfBoundsException e) {
                            throw new CalException("Task due date (by) not provided!");
                        }
                        
                        add(description, by);
                        break;
                    case "event":
                        int fromIndex = input.indexOf("/from");
                        int toIndex = input.indexOf("/to");
                        String startDate = "";
                        String endDate = "";

                        try {
                            description = input.substring(6, fromIndex).strip();
                        } catch (StringIndexOutOfBoundsException e){
                            throw new CalException("Task description not provided!");
                        }

                        try {
                            startDate = input.substring(fromIndex + 5, toIndex).strip();
                        } catch (StringIndexOutOfBoundsException e){
                            throw new CalException("Event start date not provided!");
                        }

                        try {
                            endDate = input.substring(toIndex + 3).strip();
                        } catch (StringIndexOutOfBoundsException e){
                            throw new CalException("Event end date not provided!");
                        }

                        add(description, startDate, endDate);
                        break;
                    default:
                        throw new CalException("Command not recognized.");
                }
                System.out.println(line);
            } catch(Exception e) {
                System.err.println(e);
                break;
            }
            
        }
    }

    public static void main(String[] args) {
        greet();
        run();
        exit();
    }
}
