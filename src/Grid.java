public class Grid {
    private double[] x;
    private double[] y;
    private int size;
    private Function function;

    Grid(double[] x, Function function) {
        if (x.length < 2) throw new IllegalArgumentException();
        this.function = function;
        size = x.length;
        this.x = x;
        this.y = new double[size];
        for (int i = 0; i < size; i++) {
            y[i] = function.value(x[i]);
        }
    }

    public double getX(int i) {
        return x[i];
    }

    public double getY(int i) {
        return y[i];
    }

    public int getSize() {
        return size;
    }

    public double getFunctionValue(double x) {
        return function.value(x);
    }

}
