package my.neuralnetwork.tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static my.neuralnetwork.tutorial.Main.LEARNING_RATE;

public class Connection {

    private static final Random random = new Random();

    private double w;
    private List<Double> pendingAdjustments = new ArrayList<>();
    private final Neuron from;
    private final Neuron to;

    public Connection(Neuron from, Neuron to) {
        this.from = from;
        this.from.addOutgoingConnection(this);
        this.to = to;
        this.to.addIncomingConnection(this);
        this.w = random.nextDouble() - 0.5;
    }

    public static void connect(Neuron from, Neuron to) {
        new Connection(from, to);
    }

    public void applyPendingAdjustments() {
        w = w - getAveragePendingAdjustment() * LEARNING_RATE;
        pendingAdjustments.clear();
    }

    private double getAveragePendingAdjustment() {
        if (pendingAdjustments.isEmpty()) return 0;

        double sum = 0;
        for (Double pa : pendingAdjustments) {
            sum = sum + pa;
        }

        return sum / pendingAdjustments.size();
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

    public void addPendingAdjustment(double pendingAdjustment) {
        pendingAdjustments.add(pendingAdjustment);
    }
}
