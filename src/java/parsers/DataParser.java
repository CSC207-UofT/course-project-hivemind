package parsers;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataParser {
    public static final String FOOD_FILE = "src/data/fooddata.csv";
    public static final String RECIPE_FILE = "src/data/recipedata.csv";

    public static void writeToFile(String data, boolean useFood) throws IOException {
        FileWriter fw;
        if (useFood){ fw = new FileWriter(FOOD_FILE); }
        else { fw = new FileWriter(RECIPE_FILE); }

        fw.write(data + "\n");
        fw.close();
    }

    public static ArrayList<String> readFile(boolean useFood) throws IOException {
        FileReader fr;
        if (useFood){ fr = new FileReader(FOOD_FILE); }
        else { fr = new FileReader(RECIPE_FILE); }
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

    public static void main (String[] args) {
        try {
            writeToFile("test", true);
        }
        catch (IOException e) {
            System.out.println("error");
        }
    }
}
