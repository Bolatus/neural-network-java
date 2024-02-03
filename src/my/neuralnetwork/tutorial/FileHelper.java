package my.neuralnetwork.tutorial;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileHelper {

    private BufferedReader bufferedReader;

    public FileHelper(String fileName) {
        try {
            String absolutePathToDataFolder = new File("").getAbsolutePath() + "/data/";
            File file = new File(absolutePathToDataFolder + fileName);
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

            // exclude first line in CSV file
            bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InputOutputPair readNextLine() {
        try {
            String line = bufferedReader.readLine();
            String[] splitted = line.split(",");

            List<Double> expectedOutput = prepareExpectedOutput(splitted[0]);

            Double[] inputArray = new Double[784];
            for (int i = 1; i <= 784; i++) {
                inputArray[i - 1] = Double.parseDouble(splitted[i]) > 0 ? 1d : 0d;
            }
            List<Double> inputs = Arrays.asList(inputArray);

            return new InputOutputPair(inputs, expectedOutput);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * For example, 3 -> [0, 0, 0, 1, 0, 0, 0, 0, 0, 0]
     *
     * @param label a regular number.
     * @return an array of 10 with position of the number.
     */
    private List<Double> prepareExpectedOutput(String label) {
        int index = Integer.parseInt(label);
        Double[] outputList = new Double[10];
        for (int i = 0; i < 10; i++) outputList[i] = 0d;

        outputList[index] = 1d;

        return Arrays.asList(outputList);
    }

    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
