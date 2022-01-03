package my.neuralnetwork.tutorial;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 2, 2);

        neuralNetwork.printNetwork();

        List<Double> resultValues = neuralNetwork.calculateOutputs(List.of(0.6, 0.1));

        System.out.println("Results:");
        for (Double d: resultValues) System.out.println(d);
    }
}
