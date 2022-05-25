public class Main {
    public static void main(String[] args) {
//        Polinom polinom1 = new Polinom(new double[]{1, 1, 3});
//        Polinom polinom2 = new Polinom(new double[]{1, -1, 0});
//        polinom2.multiply(polinom1).print();
        init2();
    }

    public static void init1() {
        double a = 0;
        double b = 1;
        int count = 6;
        double step = (b - a) / count;
        double[] x = new double[(int) ((b - a) / step)];
        for (int i = 0; i < x.length; i++) {
            x[i] = a + step * i;
        }
        Grid grid = new Grid(x, o -> (o*o*o*o*o - 4.378*o*o*o*o - 2.177 * o * o + 0.331));

        Lagrange lagrange = new Lagrange(grid);
        System.out.println("Полином Лагранжа");
        lagrange.print();
        print(lagrange);

        Newton newton = new Newton(grid);
        System.out.println("Полином Ньютона");
        newton.print();
        print(newton);
    }

    public static void init2() {
        double a = 0;
        double b = 1;
        int count = 7;
        double step = (b - a) / (count - 1);
        double[] x = new double[count];
        for (int i = 0; i < x.length; i++) {
            x[i] = a + step * i;
        }
        Grid grid = new Grid(x, o -> (Math.sin(o * o / 2)));

        Lagrange lagrange = new Lagrange(grid);
        System.out.println("Полином Лагранжа");
        lagrange.print();
        print(lagrange);

        Newton newton = new Newton(grid);
        System.out.println("Полином Ньютона");
        newton.print();
        print(newton);
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
