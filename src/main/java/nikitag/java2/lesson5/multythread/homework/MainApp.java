package nikitag.java2.lesson5.multythread.homework;

public class MainApp {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) throws InterruptedException {
        //Простое замещение без разделения потока
        firstMethod();
        secondMethod();
        thirdMethod();

    }

    public static void firstMethod() {
        long a = System.currentTimeMillis();
        float[] arr = new float[SIZE];
        Thread addOnes = new Thread(() -> {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = 1;
            }
        });
        addOnes.start();
        try {
            addOnes.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread useMathFormula = new Thread(() -> {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        useMathFormula.start();

        System.out.println("Время завершения операции с одним потоком: " + (System.currentTimeMillis() - a));
    }

    public static void secondMethod() {
        //Создаём отправную точку таймера
        long a = System.currentTimeMillis();
        float[] arr = new float[SIZE];
        //Создаём два массива для перезаписи половинок
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        //Инициируем из одного массива два других с перезаписью половин
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        Thread firstHalf = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = 1;
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(a1, 0, arr, 0, HALF);
        });
        firstHalf.start();

        Thread secondHalf = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = 1;
                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(a2, 0, arr, HALF, HALF);
        });
        secondHalf.start();

        System.out.println("Время завершения операции с двумя потоками: " + (System.currentTimeMillis() - a));
    }

    public static void thirdMethod() throws InterruptedException {
        long a = System.currentTimeMillis();
        float[] arr = new float[SIZE];
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        Thread addOnes = new Thread(() -> {
            System.out.println();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = 1;
            }
        });
        addOnes.start();
        addOnes.join();
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        Thread firstHalf = new Thread(() -> {
            for (int i = 0; i < a1.length; i++) {
                a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(a1, 0, arr, 0, HALF);
        });
        firstHalf.start();

        Thread secondHalf = new Thread(() -> {
            for (int i = 0; i < a2.length; i++) {
                a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            System.arraycopy(a2, 0, arr, HALF, HALF);
        });
        secondHalf.start();

        System.out.println("Время завершения операции с двумя потоками по заданию: " + (System.currentTimeMillis() - a));
    }
}
