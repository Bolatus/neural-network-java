package my.neuralnetwork.tutorial;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 2, 2);

        neuralNetwork.printNetwork();

        List<Double> resultValuesBefore = neuralNetwork.calculateOutputs(List.of(1.0, 0.0));

        System.out.println("Results before training:");
        for (Double d: resultValuesBefore) System.out.println(d);

        for (int i = 0; i < 1000; i++)
        neuralNetwork.train(List.of(1.0, 0.0), List.of(0.0, 1.0));


        List<Double> resultValuesAfter = neuralNetwork.calculateOutputs(List.of(1.0, 0.0));

        System.out.println("Results after training:");
        for (Double d: resultValuesAfter) System.out.println(d);

    }
}
