package entities;


public class Step {

    private int[] xs;
    private int[] ys;

    private int resize = 20;

    public Step (int[] steps) {
        int length = steps.length / 2;
        int cycle = length / resize;
        int[] xs = new int[resize];
        int[] ys = new int[resize];
        for (int i = 0; i < resize; i++) {
            xs[i] = steps[i * cycle * 2];
            ys[i] = steps[i * cycle * 2 + 1];
        }
        xs[resize - 1] = steps[steps.length - 2];
        ys[resize - 1] = steps[steps.length - 1];
        this.xs = xs;
        this.ys = ys;
    }

    public int[] getXs () {
        return xs;
    }

    public int[] getYs () {
        return ys;
    }
}
