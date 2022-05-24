public abstract class Interpolation {
    protected Polinom polinom;
    protected Grid grid;

    Interpolation(Grid grid) {
        this.grid = grid;
        this.polinom = new Polinom();
    }

    public double value(double point) {
        return polinom.value(point);
    };

    public void print() {
        polinom.print();
    };

    public Grid getGrid() {
        return this.grid;
    }
}
