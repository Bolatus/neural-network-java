package my.neuralnetwork.tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {

    private static final Random random = new Random();

    private final List<Neuron> inputLayer = new ArrayList<>();
    private final List<Neuron> hiddenLayer = new ArrayList<>();
    private final List<Neuron> outputLayer = new ArrayList<>();

    public NeuralNetwork(int inputCount, int hiddenCount, int outputCount) {
        for (int i = 1; i <= inputCount; i++) {
            inputLayer.add(new Neuron("input" + i, 0));
        }

        for (int i = 1; i <= hiddenCount; i++) {
            Neuron hidden = new Neuron("hidden" + i, random.nextDouble() - 0.5);
            for (Neuron input : inputLayer) {
                Connection.connect(input, hidden);
            }
            hiddenLayer.add(hidden);
        }

        for (int i = 1; i <= outputCount; i++) {
            Neuron output = new Neuron("output" + i, random.nextDouble() - 0.5, true);
            for (Neuron hidden : hiddenLayer) {
                Connection.connect(hidden, output);
            }
            outputLayer.add(output);
        }
    }

    public List<Double> calculateOutputs(List<Double> inputValues) {
        for (int i = 0; i < inputValues.size(); i++) {
            inputLayer.get(i).setOutputValue(inputValues.get(i));
        }

        for (Neuron hiddenNeuron : hiddenLayer) {
            hiddenNeuron.calculateOutputValue();
        }

        List<Double> resultValues = new ArrayList<>();
        for (Neuron outputNeuron : outputLayer) {
            outputNeuron.calculateOutputValue();
            resultValues.add(outputNeuron.getOutputValue());
        }

        return resultValues;
    }

    public void train(List<Double> inputValues, List<Double> expectedValues) {
        for (int i = 0; i < inputValues.size(); i++) {
            inputLayer.get(i).setOutputValue(inputValues.get(i));
        }

        for (int i = 0; i < expectedValues.size(); i++) {
            outputLayer.get(i).setTarget(expectedValues.get(i));
        }

        // forward propagation
        for (Neuron hiddenNeuron : hiddenLayer) hiddenNeuron.calculateOutputValue();
        for (Neuron outputNeuron : outputLayer) outputNeuron.calculateOutputValue();

        // back propagation
        for (Neuron outputNeuron : outputLayer) outputNeuron.computeWeightAdjustment();
        for (Neuron hiddenNeuron : hiddenLayer) hiddenNeuron.computeWeightAdjustment();

        // apply weight changes
        for (Neuron hiddenNeuron : hiddenLayer) hiddenNeuron.applyPendingChangesToWeights();
        for (Neuron outputNeuron : outputLayer) outputNeuron.applyPendingChangesToWeights();
    }

    public void trainInBatches(List<InputOutputPair> batch) {
        for (InputOutputPair pair : batch) {
            for (int i = 0; i < pair.getInputs().size(); i++) {
                inputLayer.get(i).setOutputValue(pair.getInputs().get(i));
            }

            for (int i = 0; i < pair.getExpectedOutputs().size(); i++) {
                outputLayer.get(i).setTarget(pair.getExpectedOutputs().get(i));
            }

            // forward propagation
            for (Neuron hiddenNeuron : hiddenLayer) hiddenNeuron.calculateOutputValue();
            for (Neuron outputNeuron : outputLayer) outputNeuron.calculateOutputValue();

            // back propagation
            for (Neuron outputNeuron : outputLayer) outputNeuron.computeWeightAdjustment();
            for (Neuron hiddenNeuron : hiddenLayer) hiddenNeuron.computeWeightAdjustment();
        }

        // apply weight changes
        for (Neuron hiddenNeuron : hiddenLayer) hiddenNeuron.applyPendingChangesToWeights();
        for (Neuron outputNeuron : outputLayer) outputNeuron.applyPendingChangesToWeights();
    }

    public void printNetwork() {
        System.out.println("Hidden layer:");
        for (Neuron hiddenNeuron : hiddenLayer) {
            System.out.println(hiddenNeuron);
        }
        System.out.println();

        System.out.println("Output layer:");
        for (Neuron outputNeuron : outputLayer) {
            System.out.println(outputNeuron);
        }
        System.out.println();
    }

}
