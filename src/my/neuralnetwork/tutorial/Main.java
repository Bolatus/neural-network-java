package my.neuralnetwork.tutorial;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final DecimalFormat df = new DecimalFormat("##.####");

    public static void main(String[] args) {
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
}
