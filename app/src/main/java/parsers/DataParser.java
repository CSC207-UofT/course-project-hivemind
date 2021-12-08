package parsers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataParser {
    public static final String FOOD_FILE = "src/data/fooddata.csv";
    public static final String RECIPE_FILE = "src/data/recipedata.csv";

    /**
     * Write to the data file according to the file specified
     *
     * @param data The string that needs to be added to the csv files
     * @throws IOException exception for writing file errors
     */
    public static void writeToFile(String data, String file) throws IOException {
        FileWriter fw;
        fw = new FileWriter(file, true);
        fw.write(data + "\n");
        fw.close();
    }

    /**
     * Reads all lines in a specified csv file
     *
     * @return An ArrayList of strings for every line in the csv file that was read
     * @throws IOException exception for writing file errors
     */
    public static ArrayList<String> readFile(String file) throws IOException {
        FileReader fr;
        fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> lines = new ArrayList<>();
        String currentLine = br.readLine();
        while (currentLine != null) {
            lines.add(currentLine);
            currentLine = br.readLine();
        }
        br.close();
        fr.close();
        return lines;
    }

    /**
     * Deletes a specified row from fooddata
     * @param deletedFoodIndex an integer specifying the row to be deleted in fooddata
     * @throws IOException exception for writing file errors
     */
    public static void deleteRowFromFoodFile(int deletedFoodIndex) throws IOException {
        ArrayList<String> foodFile = readFile(FOOD_FILE);
        foodFile.remove(deletedFoodIndex);
        clearFileHelper();
        for (String item: foodFile){
            writeToFile(item, FOOD_FILE);
        }
    }

    /**
     * Clears the content of the FOOD_FILE csv file.
     * @throws IOException exception for writing file errors
     */
    private static void clearFileHelper() throws IOException {
        FileWriter fw;
        fw = new FileWriter(FOOD_FILE, false);
        fw.close();
    }
}
