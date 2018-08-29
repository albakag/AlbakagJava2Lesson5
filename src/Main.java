public class Main{

    static final int SIZE = 10_000_000;
    static final int h = SIZE / 2;

    public static void main(String[] args) {
        Main main = new Main();
        main.methodOne();
        /* Времязатраты
        6731
        6300
        6332
         */
        main.methodTwo();
        /* Времязатраты
        60
        59
        73
         */
    }

    //Первый просто бежит по массиву и вычисляет значения.
    private void methodOne() {

        long a = System.currentTimeMillis();

        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println(System.currentTimeMillis() - a);
    }

    //Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
    //(Выигрыш по времени в сравнении с первым методом, примерно в сотню раз)
    private void methodTwo() {

        float [] a1 = new float[h];
        float [] a2 = new float[h];

        long a = System.currentTimeMillis();

        float[] arr = new float[SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a1.length; i++) {
                a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, h, a2.length);

        System.out.println(System.currentTimeMillis() - a);
    }
}
