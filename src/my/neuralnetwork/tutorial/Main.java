package my.neuralnetwork.tutorial;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static final double LEARNING_RATE = 0.4;
    private static final DecimalFormat df = new DecimalFormat("##.####");

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(784, 100, 10);
        FileHelper fileHelper = new FileHelper("mnist.csv");

        try {
            List<InputOutputPair> batch = getNextBatch(fileHelper, 10);

            if (!batch.isEmpty()) {
                System.out.println("Results before training:");
                calculateAndPrintResults(batch.get(0), neuralNetwork);
                calculateAndPrintResults(batch.get(1), neuralNetwork);
                calculateAndPrintResults(batch.get(2), neuralNetwork);
                calculateAndPrintResults(batch.get(3), neuralNetwork);
                calculateAndPrintResults(batch.get(4), neuralNetwork);

                for (int i = 0; i < 100; i++) {
                    neuralNetwork.trainInBatches(batch);
                }

                System.out.println();
                System.out.println("Results after training:");
                calculateAndPrintResults(batch.get(0), neuralNetwork);
                calculateAndPrintResults(batch.get(1), neuralNetwork);
                calculateAndPrintResults(batch.get(2), neuralNetwork);
                calculateAndPrintResults(batch.get(3), neuralNetwork);
                calculateAndPrintResults(batch.get(4), neuralNetwork);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileHelper.close();
        }
    }

    private static List<InputOutputPair> getNextBatch(FileHelper fileHelper, int batchSize) {
        List<InputOutputPair> batch = new ArrayList<>(batchSize);

        for (int i = 0; i < batchSize; i++) {
            InputOutputPair inputOutputPair = fileHelper.readNextLine();

            if (inputOutputPair == null) break;

            batch.add(inputOutputPair);
        }

        return batch;
    }

    private static void simpleExampleTest() {
        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 4, 3);

        neuralNetwork.printNetwork();

        System.out.println("Results before training:");
        calculateAndPrintResults(List.of(1.0, 0.0), neuralNetwork);
        calculateAndPrintResults(List.of(0.0, 1.0), neuralNetwork);
        calculateAndPrintResults(List.of(1.0, 1.0), neuralNetwork);

        for (int i = 0; i < 10000; i++) {
            neuralNetwork.train(List.of(1.0, 0.0), List.of(0.0, 1.0, 0.0));
            neuralNetwork.train(List.of(0.0, 1.0), List.of(1.0, 0.0, 0.0));
            neuralNetwork.train(List.of(1.0, 1.0), List.of(0.0, 0.0, 1.0));
        }

        System.out.println();
        System.out.println("Results after training:");
        calculateAndPrintResults(List.of(1.0, 0.0), neuralNetwork);
        calculateAndPrintResults(List.of(0.0, 1.0), neuralNetwork);
        calculateAndPrintResults(List.of(1.0, 1.0), neuralNetwork);
    }

    private static void calculateAndPrintResults(List<Double> inputParameters, NeuralNetwork neuralNetwork) {
        List<Double> resultValues = neuralNetwork.calculateOutputs(inputParameters);

        String inputParamsText = inputParameters.stream().map(d -> df.format(d)).collect(Collectors.joining(", "));
        System.out.println("- Input parameters: " + inputParamsText);

        String resultValuesText = resultValues.stream().map(d -> df.format(d)).collect(Collectors.joining(", "));
        System.out.println("- Result values: " + resultValuesText);
    }

    /**
     * Forwardpropagates, prints out expected and actual results.
     *
     * @param inputOutputPair
     * @param neuralNetwork
     */
    private static void calculateAndPrintResults(InputOutputPair inputOutputPair, NeuralNetwork neuralNetwork) {
        List<Double> resultValues = neuralNetwork.calculateOutputs(inputOutputPair.getInputs());

        String inputParamsText = inputOutputPair.getInputs().stream().map(d -> df.format(d)).collect(Collectors.joining(", "));
        System.out.println("- Input parameters: " + inputParamsText);
        System.out.println("- Expected: " + expectedValue(inputOutputPair.getExpectedOutputs()));
        System.out.println("- Predicted: " + predictedValue(resultValues));
    }

    private static String expectedValue(List<Double> expectedOutputs) {
        for (int i = 0 ; i < expectedOutputs.size(); i++) {
            if (expectedOutputs.get(i).compareTo(1d) == 0) {
                return String.valueOf(i);
            }
        }
        return "error";
    }

    private static String predictedValue(List<Double> resultValues) {
        double highestValue = resultValues.get(0);
        int predictedNumber = 0;

        for (int i = 1 ; i < resultValues.size(); i++) {
            if (resultValues.get(i) > highestValue) {
                highestValue = resultValues.get(i);
                predictedNumber = i;
            }
        }

        double percentage = (highestValue * 100d);
        return predictedNumber + " (" + df.format(percentage) + "%)";
    }

}
