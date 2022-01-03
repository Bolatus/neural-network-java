package my.neuralnetwork.tutorial;

import java.util.Random;

public class Connection {

    private static final Random random = new Random();

    private double w;
    private final Neuron from;
    private final Neuron to;

    public Connection(Neuron from, Neuron to) {
        this.from = from;
        this.from.addOutgoingConnection(this);
        this.to = to;
        this.to.addIncomingConnection(this);
        this.w = random.nextDouble();
    }

    public static void connect(Neuron from, Neuron to) {
        new Connection(from, to);
    }

    public double getW() {
        return w;
    }

    public Neuron getFrom() {
        return from;
    }

    public Neuron getTo() {
        return to;
    }
}
