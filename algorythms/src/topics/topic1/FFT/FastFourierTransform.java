package topics.topic1.FFT;

/**
 * Алгоритм быстрого преобразования Фурье
 */
public class FastFourierTransform {

    public static void main(String[] args) {
        int n = 4;
        Complex[] x = new Complex[n];

        // Введенные данные
        for (int i = 0; i < n; i++) {
            x[i] = new Complex(i, 0);
            x[i] = new Complex(-2*Math.random() + 1, 0);
        }
        show(x, "x");

        // FFT введенных данных
        Complex[] y = fft(x);
        show(y, "y = fft(x)");

    }

    /**
     * Вывод списка комплесных чисел
     */
    public static void show(Complex[] x, String title)
    {
        System.out.println(title);
        for (Complex aX : x) {
            System.out.println(aX);
        }
        System.out.println();
    }

    /**
     * Вычислить БПФ переденных комплексных чисел
     * Кол-во чисел должна быть равна степени 2
     */
    public static Complex[] fft(Complex[] x)
    {
        int N = x.length;

        /**
         * Самый простой случай
         */
        if (N == 1)
            return new Complex[] { x[0] };

        /**
         * Длина не является степенью 2
         */
        if (N % 2 != 0)
        {
            System.out.println("Кол-во чисел не является степенью 2");
        }

        /**
         * БПФ четных членов
         */
        Complex[] even = new Complex[N / 2];
        for (int k = 0; k < N / 2; k++)
        {
            even[k] = x[2 * k];
        }
        Complex[] q = fft(even);

        /**
         * БПФ нечетных членов
         */
        Complex[] odd = even;
        for (int k = 0; k < N / 2; k++)
        {
            odd[k] = x[2 * k + 1];
        }
        Complex[] r = fft(odd);

        /**
         * Слияние
         */
        Complex[] y = new Complex[N];
        for (int k = 0; k < N / 2; k++)
        {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k] = q[k].plus(wk.times(r[k]));
            y[k + N / 2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }


    /**
     * Вычислить обратное БПФ x[]
     * Кол-во чисел должна быть равна степени 2
     */
    public static Complex[] ifft(Complex[] x)
    {
        int N = x.length;
        Complex[] y = new Complex[N];

        for (int i = 0; i < N; i++)
        {
            y[i] = x[i].conjugate();
        }

        y = fft(y);

        for (int i = 0; i < N; i++)
        {
            y[i] = y[i].conjugate();
        }

        for (int i = 0; i < N; i++)
        {
            y[i] = y[i].times(1.0 / N);
        }

        return y;

    }

    /**
     * Класс для комплексного числа
     */
    public static class Complex {
        /**
         * Реальная часть
         */
        private final double re;
        /**
         * Воображаемая часть
         */
        private final double im;

        /**
         * Создание нового объекта с переданными данными
         * @param real
         * @param imag
         */
        public Complex(double real, double imag)
        {
            re = real;
            im = imag;
        }

        public String toString()
        {
            if (im == 0)
                return re + "";
            if (re == 0)
                return im + "i";
            if (im < 0)
                return re + " - " + (-im) + "i";
            return re + " + " + im + "i";
        }

        public double abs()
        {
            return Math.hypot(re, im);
        }

        public double phase()
        {
            return Math.atan2(im, re);
        }

        public Complex plus(Complex b)
        {
            Complex a = this;
            double real = a.re + b.re;
            double imag = a.im + b.im;
            return new Complex(real, imag);
        }

        public Complex minus(Complex b)
        {
            Complex a = this;
            double real = a.re - b.re;
            double imag = a.im - b.im;
            return new Complex(real, imag);
        }

        public Complex times(Complex b)
        {
            Complex a = this;
            double real = a.re * b.re - a.im * b.im;
            double imag = a.re * b.im + a.im * b.re;
            return new Complex(real, imag);
        }


        /**
         * Скалярное умножение
         * @param alpha
         * @return
         */
        public Complex times(double alpha)
        {
            return new Complex(alpha * re, alpha * im);
        }

        public Complex conjugate() {
            return new Complex(re, -im);
        }

        public Complex reciprocal()
        {
            double scale = re * re + im * im;
            return new Complex(re / scale, -im / scale);
        }

        public double re()
        {
            return re;
        }

        public double im()
        {
            return im;
        }

        public Complex divides(Complex b)
        {
            Complex a = this;
            return a.times(b.reciprocal());
        }


        /**
         * Возвращает новое комплексное число, значение которого экспоненциальное текущего
         * @return
         */
        public Complex exp()
        {
            return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re)
                    * Math.sin(im));
        }

        public Complex sin()
        {
            return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re)
                    * Math.sinh(im));
        }

        public Complex cos()
        {
            return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re)
                    * Math.sinh(im));
        }

    }
}