package sample.runnable;

public class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("Поток начал работу:::" + Thread.currentThread().getName());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Поток отработал:::" + Thread.currentThread().getName());
    }
}
