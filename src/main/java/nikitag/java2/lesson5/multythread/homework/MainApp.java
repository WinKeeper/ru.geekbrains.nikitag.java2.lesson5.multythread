package nikitag.java2.lesson5.multythread.homework;

public class MainApp {
    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        //Простое замещение без разделения потока
        firstMethod();







    }

    public static void firstMethod() {
        float[] arr = new float[SIZE];
        long a = System.currentTimeMillis();
        Thread addOnes = new Thread(() -> {
            System.out.println();
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
            System.out.println();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        useMathFormula.start();
        System.out.println(System.currentTimeMillis() - a);
    }

    public static void secondMethod() {
        float[] arr = new float[SIZE];
        //Создаём два массива для перезаписи половинок
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];
        //Инициируем из одного массива два других с перезаписью половин
        System.arraycopy(arr, 0, a1, 0, HALF);
        System.arraycopy(arr, HALF, a2, 0, HALF);
        long a = System.currentTimeMillis();
        Thread firstHalf = new Thread(() -> {
            System.out.println();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = 1;
            }
        });
        Thread secondHalf = new Thread(() -> {
            System.out.println();
            for (int i = 0; i < arr.length; i++) {
                arr[i] = 1;
            }
        });
        System.out.println(System.currentTimeMillis() - a);
    }
}
