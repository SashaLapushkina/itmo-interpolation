public class Newton extends Interpolation{
    private double[] deltasY;     // распределённые разности

    Newton(Grid grid) {
        super(grid);

        int n = grid.getSize();

        initDeltasY(n);

        polinom = new Polinom(new double[] { grid.getY(0) });

        Polinom basis = new Polinom(new double[] {1});
        Polinom binom = new Polinom(new double[]{0, 1}); //полином вида 0 + x //убрать

        for (int i = 0; i < n - 1; i++) {
            polinom.add(new Polinom(basis.multiply(binom.setFreeMonom(-grid.getX(i)))).multiply(deltasY[0][i + 1]));
        }
    }

    private void initDeltasY(int n) {
        deltasY = new double[n][n];

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n - k; i++) {
                if (k == 0) {
                    deltasY[i][k] = grid.getY(i);
                } else {
                    deltasY[i][k] = (deltasY[i + 1][k - 1] - deltasY[i][k - 1]) / (grid.getX(i + k) - grid.getX(i));
                }
            }
        }
    }
}