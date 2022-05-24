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
                    start = new Monom(rations[i], i);
                    current = start;
                } else {
                    current.next = new Monom(rations[i], i);
                    current.next.previous = current;
                    current = current.next;
                }
            }
        }
    }

    //создаем полином из хвоста другого полинома
    private Polinom(Monom start) {
        this.start = start;
    }

    //копирующий конструктор
    public Polinom(Polinom newPolinom) {
        if (newPolinom.start != null) {
            this.start = new Monom(newPolinom.start);
            Monom thisCurrent = this.start;
            Monom current = newPolinom.start.next;
            while (current != null) {
                thisCurrent.next = new Monom(current);
                thisCurrent.next.previous = thisCurrent;

                thisCurrent = thisCurrent.next;
                current = current.next;
            }
        }
    }

    // +=
    public Polinom add(Polinom polinom) {
        //если нечего прибавлять
        if (polinom.start == null) return this;

        //если не к чему прибавлять
        if (this.start == null) {
            this.start = new Polinom(polinom).start;
            return this;
        }

        Monom current = polinom.start; //то, что мы прибавляем
        Monom thisCurrent = start; //то, к чему мы прибавляем

        while (current != null) {
            if (current.degree == thisCurrent.degree) {
                double sum = current.ration + thisCurrent.ration;
                if (Math.abs(sum) < EPS) {
                    if (thisCurrent == start) {
                        start = thisCurrent.next;
                        if (start != null) {
                            start.previous = null;
                        }
                    } else {
                        if (thisCurrent.next == null) {
                            thisCurrent.previous.next = null;
                        } else {
                            thisCurrent.previous.next = thisCurrent.next;
                            thisCurrent.next.previous = thisCurrent.previous; //удаляем, если получили 0
                        }
                        thisCurrent = thisCurrent.previous;
                    }
                } else {
                    thisCurrent.ration = sum;
                }
                if (thisCurrent.next == null && current.next != null) {
                    thisCurrent.next = new Polinom(new Polinom(current.next)).start;
                    thisCurrent.next.previous = thisCurrent;
                    return this;
                }
                thisCurrent = thisCurrent.next;
                current = current.next;
            } else if (current.degree < thisCurrent.degree) {
                Monom newMonom = new Monom(current);
                insertBefore(newMonom, thisCurrent);
                if (thisCurrent == start) start = newMonom;
                current = current.next;
            } else {
                if (thisCurrent.next == null) {
                    thisCurrent.next = new Polinom(new Polinom(current)).start;
                    thisCurrent.next.previous = thisCurrent;
                    return this;
                }
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
        do {
            result.add(new Polinom(polinom).multiply(current));
            current = current.next;
        } while (current != null);
        start = result.start;
        return this;
    }

    private Polinom multiply(Monom monom) {
        if (start == null) return this;
        Monom current = start;
        do {
            current.degree += monom.degree;
            current.ration *= monom.ration;

            current = current.next;
        } while (current != null);
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
        double result = 0d;
        int xDegree = 0;
        double x = 1;
        Monom current = start;
        while (current != null) {
            for (; xDegree < current.degree; xDegree++)
                x *= point;
            result += current.ration * x;
            current = current.next;
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

    //изменить свободный член
    public Polinom setFreeMonom(double value) {
        if (start == null) {
            start = new Monom(value, 0);
        } else if (start.degree == 0) start.ration = value;
        else {
            Monom newStart = new Monom(value, 0);
            insertBefore(newStart, start);
            start = newStart;
        }
        return this;
    }

    private void insertBefore(Monom current, Monom place) {
        if (place.previous != null) {
            current.previous = place.previous;
            place.previous.next = current;

        }
        current.next = place;
        place.previous = current;
    }

    class Monom {
        double ration; //коэффициент
        int degree; //степень
        Monom previous;
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
