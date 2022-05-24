public class Newton extends Interpolation{
    private double[][] deltasY;     // распределённые разности

    Newton(Grid grid) {
        super(grid);

        initDeltasY(grid.getSize());
        solvePolinom(grid.getSize());
    }

    private void initDeltasY(int n) {
        deltasY = new double[n][n + 1];

        for (int k = 0; k <= n; k++) {
            for (int i = 0; i < n - k; i++) {
                if (k == 0) {
                    deltasY[i][k] = grid.getY(i);
                } else {
                    deltasY[i][k] = (deltasY[i + 1][k - 1] - deltasY[i][k - 1]) / (grid.getX(i + k) - grid.getX(i));
                }
            }
        }
    }

    private void solvePolinom(int n) {
        polinom = new Polinom(new double[] { grid.getY(0) });

        Polinom currentPolinom = new Polinom(new double[] {1});
        Polinom basis = new Polinom(new double[]{0, 1}); //полином вида 0 + x

        for (int i = 0; i < n; i++) {
            basis.setFreeMonom(-grid.getX(i));
            currentPolinom.multiply(basis);

            Polinom tmp = new Polinom(currentPolinom);
            tmp.multiply(deltasY[0][i + 1]);
            polinom.add(tmp);
        }
    }
}