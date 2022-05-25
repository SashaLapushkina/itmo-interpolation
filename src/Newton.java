public class Newton extends Interpolation{
    private double[] deltasY;     // распределённые разности

    Newton(Grid grid) {
        super(grid);

        int n = grid.getSize();

        initDeltasY(n);

        polinom = new Polinom(new double[] { grid.getY(0) });

        Polinom basis = new Polinom(new double[] {1});

        for (int i = 0; i < n - 1; i++) {
            Polinom binom = new Polinom(new double[]{1, -grid.getX(i)});
            polinom.add(new Polinom(basis.multiply(binom).getStart()).multiply(deltasY[i + 1]));
        }
    }

    private void initDeltasY(int n) {
        deltasY = new double[n];

        double[] next = new double[n];

        for (int i = 0; i < n; i++) {
            deltasY[i] = grid.getY(i);
        }

        for (int k = 1; k < n; k++) {
            for (int i = k; i < n; i++) {
                next[i] = (deltasY[i] - deltasY[i - 1]) / (grid.getX(i) - grid.getX(i - k));
            }

            for (int i = k; i < n; i++) {
                deltasY[i] = next[i];
            }
        }
    }
}