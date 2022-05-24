public class Main {
    public static void main(String[] args) {
        double a = 0;
        double b = 10;
        int count = 5;
        double step = (b - a) / count;
        double[] x = new double[(int) ((b - a) / step)];
        for (int i = 0; i < x.length; i++) {
            x[i] = a + step * i;
        }
        Grid grid = new Grid(x, Math::sin);
//        Grid grid = new Grid(new double[] {0, 1, 2, 3, 4, 5}, o -> (o * o - 1));

        Lagrange lagrange = new Lagrange(grid);
        System.out.println("Полином Лагранжа");
        lagrange.print();
        print(lagrange);

        Newton newton = new Newton(grid);
        System.out.println("Полином Ньютона");
        newton.print();
        print(newton);
//
//          Polinom polinom2 = new Polinom(new double[]{0, 0});
//          polinom2.setFreeMonom(2).print();
////        Polinom polinom1 = new Polinom(new double[]{0, -1, 1});
////        polinom2.multiply(polinom2).print();
////        polinom1.add(polinom2).print();
    }

    public static void print(Interpolation interpolation) {
        System.out.println("Таблица");
        System.out.printf("%12s\t%12s\t%12s\t%12s\n", "x", "y", "f(x)", "In(x)");
        double n = interpolation.getGrid().getSize();
        double step = interpolation.getGrid().getX(1) - interpolation.getGrid().getX(0);
        for (int i = 0; i < n; i++) {
            double xi = interpolation.getGrid().getX(i);
            System.out.printf("%10.6e\t%10.6e\t%10.6e\t%10.6e\n", xi, interpolation.getGrid().getY(i), interpolation.getGrid().getFunctionValue(xi), interpolation.value(xi));

            if (i != n - 1) {
                xi += step / 2;
                System.out.printf("%10.6e\t%12s\t%10.6e\t%10.6e\n", xi, "", interpolation.getGrid().getFunctionValue(xi), interpolation.value(xi));
            }
        }

    }
}
