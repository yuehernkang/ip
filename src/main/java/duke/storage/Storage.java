package duke.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duke.TaskList;
import duke.models.Deadline;
import duke.models.Event;
import duke.models.Note;
import duke.models.Task;
import duke.models.Todo;

/**
 * Storage class to handle file operations
 */
public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Check if saved folder exists. Run at the start of the program
     * to initialize data from saved file
     */
    public void run() {

    }

    /**
     * Helper function to load data into the application from saved file
     * @return A list of tasks
     */
    public List<Task> loadData() {
        List<Task> result = new ArrayList<>();
        try {
            String currentDirectory = System.getProperty("user.dir");
            Path path = java.nio.file.Paths.get(currentDirectory, "/data");
            File myObj = new File(currentDirectory + "/data/saved.txt");
            boolean directoryExists = Files.exists(path);

            if (directoryExists) {
                System.out.println("Directory exists");
            } else {
                System.out.println("Directory does not exist");
                Files.createDirectory(path);
                myObj.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String currentDirectory = System.getProperty("user.dir");
            File myObj = new File(currentDirectory + "/data/saved.txt");
            Scanner myReader = new Scanner(myObj);
            System.out.println(myReader);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] split = data.split("\\|");
                Pattern pattern = Pattern.compile("^[^|]*");
                Matcher matcher = pattern.matcher(data);
                matcher.find();
                switch (matcher.group(0)) {
                case "T":
                    System.out.println(split[2]);
                    result.add(new Todo(split[2], split[1].equals("1")));
                    break;
                case "D":
                    System.out.println(split[0]);
                    result.add(new Deadline(split[2], LocalDate.parse(split[3])));
                    break;
                case "E":
                    System.out.println(split[0]);
                    result.add(new Event(split[2], LocalDate.parse(split[3])));
                    break;
                case "NOTE":
                    System.out.println(split[0]);
                    result.add(new Note(split[1]));
                    break;
                default:
                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result;
    }

    /**
     * Write task to file
     * @param text text to write to the file
     */
    public void write(String text) {
        assert text != null : "Text should not be null";
        try {
            String savedDirectory = System.getProperty("user.dir") + "/data/saved.txt";
            FileWriter myWriter = new FileWriter(savedDirectory, true);
            myWriter.write(text + "\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Some actions like update and delete require a rewrite of the
     * entire text file
     * @param tasks list of tasks to write to the file
     */
    public void rewrite(TaskList tasks) {
        try {
            String savedDirectory = System.getProperty("user.dir") + "/data/saved.txt";
            FileWriter myWriter = new FileWriter(savedDirectory, true);
            PrintWriter pw = new PrintWriter(savedDirectory);
            System.out.println(tasks.getSize());
            for (int i = 1; i < tasks.getSize() + 1; i++) {
                System.out.println(tasks.getTask(i).toString());
                myWriter.write(tasks.getTask(i).stringToWrite() + "\n");
            }
            pw.close();
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
