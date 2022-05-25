public class Lagrange extends Interpolation {
    Lagrange(Grid grid) {
        super(grid);
        for (int i = 0; i < grid.getSize(); i++) {
            polinom.add(basis(i).multiply(grid.getY(i)));
        }
    }

    private Polinom basis(int i) {
        Polinom basis = new Polinom(new double[]{1}); //полином вида 1 + 0
        double divisor = 1;
        for (int j = 0; j < grid.getSize(); j++) {
            if (i != j) {
                double xj = grid.getX(j);
                double xi = grid.getX(i);
                basis.multiply(new Polinom(new double[]{1, -xj}));
                divisor *= 1.0 / (xi - xj);
            }
        }
        return basis.multiply(divisor);
    }
}
