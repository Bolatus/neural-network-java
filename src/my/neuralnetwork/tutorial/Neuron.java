package my.neuralnetwork.tutorial;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private final String name;
    private double bias;
    private double outputValue;

    private List<Connection> incomingConnections = new ArrayList<>();
    private List<Connection> outgoingConnections = new ArrayList<>();

    public Neuron(String name, double bias) {
        this.name = name;
        this.bias = bias;
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
