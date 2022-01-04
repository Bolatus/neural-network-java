package my.neuralnetwork.tutorial;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private final String name;
    private double bias;
    private double outputValue;
    private double target;
    private boolean isOutputLayer;

    private List<Connection> incomingConnections = new ArrayList<>();
    private List<Connection> outgoingConnections = new ArrayList<>();

    public Neuron(String name, double bias) {
        this.name = name;
        this.bias = bias;
        this.isOutputLayer = false;
    }

    public Neuron(String name, double bias, boolean isOutputLayer) {
        this.name = name;
        this.bias = bias;
        this.isOutputLayer = isOutputLayer;
    }

    public void calculateOutputValue() {
        double total = bias;
        for (Connection connection : incomingConnections) {
            total = total + connection.getW() * connection.getFrom().getOutputValue();
        }

        this.outputValue = activate(total);
    }

    private double activate(double value) {
        return 1.0 / (1.0 + Math.exp(-value));
    }

    public void computeWeightAdjustment() {
        if (isOutputLayer) {
            for (Connection connection : incomingConnections) {
                double dErrorOutput = -(target - outputValue);
                double dOutputActivation = outputValue * (1 - outputValue);
                double dWeight = connection.getFrom().getOutputValue();
                double weightAdjustment = dErrorOutput * dOutputActivation * dWeight;
                connection.setPendingAdjustment(weightAdjustment);
            }
        } else {
            for (Connection inCon : incomingConnections) {
                double dTotalError = 0;
                for (Connection outCon : outgoingConnections) {
                    Neuron outputNeuron = outCon.getTo();
                    double dErrorOutput = -(outputNeuron.getTarget() - outputNeuron.getOutputValue());
                    double dOutputActivation = outputNeuron.getOutputValue() * (1 - outputNeuron.getOutputValue());
                    double dHidden = outCon.getW();
                    dTotalError = dTotalError + dErrorOutput * dOutputActivation * dHidden;
                }

                double dOutputActivationH = outputValue * (1 - outputValue);
                double dWeight = inCon.getFrom().getOutputValue();
                double weightAdjustment = dTotalError * dOutputActivationH * dWeight;
                inCon.setPendingAdjustment(weightAdjustment);
            }
        }
    }

    public void applyPendingChangesToWeights() {
        for (Connection connection : incomingConnections) {
            connection.applyPendingAdjustment();
        }
    }

    public String getName() {
        return name;
    }

    public double getBias() {
        return bias;
    }

    public double getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(double outputValue) {
        this.outputValue = outputValue;
    }

    public void addIncomingConnection(Connection connection) {
        incomingConnections.add(connection);
    }

    public void addOutgoingConnection(Connection connection) {
        outgoingConnections.add(connection);
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    @Override
    public String toString() {
        String incomingConnectionWeights = "[";
        for (Connection connection : incomingConnections) incomingConnectionWeights += connection.getW() + " ";
        incomingConnectionWeights += "]";

        return "Neuron{" +
                "name='" + name + '\'' +
                ", bias=" + bias +
                ", outputValue=" + outputValue +
                ", incomingConnectionsWeights=" + incomingConnectionWeights +
                '}';
    }
}
