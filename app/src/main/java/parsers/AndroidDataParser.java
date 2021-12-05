package parsers;

import android.content.Context;

import java.io.*;
import java.util.ArrayList;

public class AndroidDataParser {
    public Context context;
    final public static String FOOD_FILE = "fooddata.csv";
    final public static String RECIPE_FILE = "recipedata.csv";

    public AndroidDataParser(Context givenContext){
        this.context = givenContext;
    }

    private ArrayList<String> readResourceFile(String file) throws IOException {
        InputStream is = context.getAssets().open(file);
        return readerHelper(is);
    }

    /**
     * Write to the data file according to the file specified
     *
     * @param data The string that needs to be added to the csv files
     * @throws IOException exception for writing file errors
     */
    public void writeFile(String data, String file) throws IOException {
        OutputStream os = context.openFileOutput(file, Context.MODE_APPEND);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write(data);
        bw.newLine();
        bw.flush();
        bw.close();
        os.close();
    }

    public void writeFile(ArrayList<String> data, String file) throws IOException {
        OutputStream os = context.openFileOutput(file, Context.MODE_APPEND);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        for (String data_piece: data) {
            bw.write(data_piece);
            bw.newLine();
            bw.flush();
        }
        bw.close();
        os.close();
    }

    /**
     * Reads all lines in a specified csv file
     *
     * @return An ArrayList of strings for every line in the csv file that was read
     * @throws IOException exception for writing file errors
     */
    private ArrayList<String> readLocalFile(String file) throws IOException {
        InputStream inStream = context.openFileInput(file);
        return readerHelper(inStream);
    }

    public ArrayList<String> readRecipeFile() {
        ArrayList<String> read_list = new ArrayList<>();
        try {
            read_list = readLocalFile(AndroidDataParser.RECIPE_FILE);
        }
        catch (IOException e) {
            try{
                read_list = readResourceFile(AndroidDataParser.RECIPE_FILE);
            }
            catch(IOException e2){
                System.out.println("An error has occurred: Critical resource file missing");

            }
        }
        return read_list;
    }

    public ArrayList<String> readFoodFile() {
        ArrayList<String> read_list = new ArrayList<>();
        try {
            read_list = readLocalFile(AndroidDataParser.FOOD_FILE);
        }
        catch (IOException e) {
                System.out.println("Error: local food file not found");
        }
        return read_list;
    }

    private ArrayList<String> readerHelper (InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ArrayList<String> read_lines = new ArrayList<>();
        String current_line = br.readLine();
        while (current_line != null) {
            read_lines.add(current_line);
            current_line = br.readLine();
        }
        br.close();
        is.close();

        return read_lines;
    }

    public void deleteRowFromFoodFile(int deletedFoodIndex) throws IOException {
        ArrayList<String> foodFile = readFoodFile();
        foodFile.remove(deletedFoodIndex);
        clearFileHelper();
        for (String item: foodFile){
            writeFile(item, "fooddata.csv");
        }
    }

    private void clearFileHelper() throws IOException {
        OutputStream os = context.openFileOutput("fooddata.csv", Context.MODE_PRIVATE);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        bw.write("");
        bw.flush();
        bw.close();
        os.close();
    }
}
