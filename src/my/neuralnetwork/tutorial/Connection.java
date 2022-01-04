package my.neuralnetwork.tutorial;

import java.util.Random;

public class Connection {

    private static final double LEARNING_RATE = 0.5;
    private static final Random random = new Random();

    private double w;
    private double pendingAdjustment;
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

    public void applyPendingAdjustment() {
        w = w - pendingAdjustment * LEARNING_RATE;
        pendingAdjustment = 0;
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

    public double getPendingAdjustment() {
        return pendingAdjustment;
    }

    public void setPendingAdjustment(double pendingAdjustment) {
        this.pendingAdjustment = pendingAdjustment;
    }
}
