package my.neuralnetwork.tutorial;

import java.util.List;

public class InputOutputPair {

    private List<Double> inputs;
    private List<Double> expectedOutputs;

    public InputOutputPair(List<Double> inputs, List<Double> expectedOutputs) {
        this.inputs = inputs;
        this.expectedOutputs = expectedOutputs;
    }

    public List<Double> getInputs() {
        return inputs;
    }

    public void setInputs(List<Double> inputs) {
        this.inputs = inputs;
    }

    public List<Double> getExpectedOutputs() {
        return expectedOutputs;
    }

    public void setExpectedOutputs(List<Double> expectedOutputs) {
        this.expectedOutputs = expectedOutputs;
    }
}
