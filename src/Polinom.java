public class Polinom {
    private Monom start;
    private static final double EPS = 1e-9;

    public Polinom() {
    }

    //создаем полином из массива коэфициентов
    public Polinom(double[] rations) {
        Monom current = null;
        for (int i = 0; i < rations.length; i++) {
            if (rations[i] != 0) {
                if (start == null) {
                    start = new Monom(rations[i], rations.length - i - 1);
                    current = start;
                } else {
                    current.next = new Monom(rations[i], rations.length - i - 1);
                    current = current.next;
                }
            }
        }
    }

    //копирующий конструктор
    public Polinom(Monom from) {
            this.start = new Monom(from);
            Monom thisCurrent = this.start;
            Monom current = from.next;
            while (current != null) {
                thisCurrent.next = new Monom(current);

                thisCurrent = thisCurrent.next;
                current = current.next;
            }
    }

    public Monom getStart() {
        return start;
    }

    // +=
    public Polinom add(Polinom polinom) {
        //если нечего прибавлять
        if (polinom.start == null) return this;

        //если не к чему прибавлять
        if (this.start == null) {
            this.start = new Polinom(polinom.start).start;
            return this;
        }

        Monom current = polinom.start; //то, что мы прибавляем
        Monom thisCurrent = start; //то, к чему мы прибавляем

        while (current != null) {
            if (current.degree > thisCurrent.degree) {
                Monom newMonom = new Monom(current);
                start = newMonom;
                newMonom.next = thisCurrent;
                thisCurrent = newMonom;
                current = current.next;
            } else if (current.degree == thisCurrent.degree) {
                double sum = current.ration + thisCurrent.ration;
                if (Math.abs(sum) < EPS) { // == 0
                    start = thisCurrent.next;
                } else {
                    thisCurrent.ration = sum;
                }
                current = current.next;
            } else if (thisCurrent.next == null) {
                thisCurrent.next = new Monom(current);
                thisCurrent = thisCurrent.next;
                current = current.next;
            } else if (current.degree > thisCurrent.next.degree) {
                Monom newMonom = new Monom(current);
                newMonom.next = thisCurrent.next;
                thisCurrent.next = newMonom;
                current = current.next;
                thisCurrent = thisCurrent.next;
            } else if (current.degree == thisCurrent.next.degree) {
                double sum = current.ration + thisCurrent.next.ration;
                if (Math.abs(sum) < EPS) { // == 0
                    thisCurrent.next = thisCurrent.next.next;
                } else {
                    thisCurrent.next.ration = sum;
                }
                current = current.next;
            } else {
                thisCurrent = thisCurrent.next;
            }
        }
        return this;
    }

    //умножить на полином
    public Polinom multiply(Polinom polinom) {
        if (start == null || polinom.start == null) {
            start = null;
            return this;
        }
        Polinom result = new Polinom();
        Monom current = start;

        while (current != null) {
            Monom resultCurrent = result.start;
            Monom polinomCurrent = polinom.start;

            while (polinomCurrent != null) {
                int degree = current.degree + polinomCurrent.degree;
                double ration = current.ration * polinomCurrent.ration;

                if (result.start == null) {
                    result.start = new Monom(ration, degree);
                    resultCurrent = result.start;
                    polinomCurrent = polinomCurrent.next;
                } else if (resultCurrent.next == null) {
                    resultCurrent.next = new Monom(ration, degree);
                    resultCurrent = resultCurrent.next;
                    polinomCurrent = polinomCurrent.next;
                } else if (resultCurrent.next.degree == degree) {
                    resultCurrent.next.ration += ration;
                    if (Math.abs(resultCurrent.next.ration) < EPS) {
                        resultCurrent.next = resultCurrent.next.next;
                    }
                    polinomCurrent = polinomCurrent.next;
                } else if (resultCurrent.next.degree < degree) {
                    Monom newMonom = new Monom(ration, degree);
                    newMonom.next = resultCurrent.next;
                    resultCurrent.next = newMonom;
                    resultCurrent = resultCurrent.next;
                    polinomCurrent = polinomCurrent.next;
                } else {
                    resultCurrent = resultCurrent.next;
                }
            }
            current = current.next;
        }

        start = result.start;
        return this;
    }

    //умножить на число
    public Polinom multiply(Double number) {
        if (Math.abs(number) < EPS) {
            start = null;
            return this;
        }
        if (start != null) {
            Monom current = start;
            do {
                current.ration *= number;
                current = current.next;
            } while (current != null);
        }
        return this;
    }

    //значение в точке
    public double value(double point) {
        if (start == null) return 0d;

        double result = 0d;
        Monom current = start;

        for (int i = start.degree; i >= 0; i--) {
            if (current != null && i == current.degree) {
                result += current.ration;
                current = current.next;
            }
            if (i != 0) {
                result *= point;
            }
        }
        return result;
    }

    //печать
    public void print() {
        if (start != null) {
            Monom current = start;
            do {
                current.print();
                System.out.print(current.next == null ? "" : " + ");
                current = current.next;
            } while (current != null);
            System.out.println();
        }
    }

    class Monom {
        double ration; //коэффициент
        int degree; //степень
        Monom next; //следующий моном

        Monom(double ration, int degree) {
            this.ration = ration;
            this.degree = degree;
        }

        Monom(Monom newMonom) {
            ration = newMonom.ration;
            degree = newMonom.degree;
        }

        public void print() {
            System.out.printf("%15.6e x^%d", ration, degree);
        }
    }
}
