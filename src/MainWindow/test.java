package MainWindow;


import java.util.Arrays;

/**
 * @author Yu Mingzheng
 * @date 2022/10/18 20:17
 * @apiNote
 */
public class test {
    public static void main(String[] args) {
        long cur1 = System.currentTimeMillis();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long cur2 = System.currentTimeMillis();
        System.out.println((cur2 - cur1)/ 1000.0);


    }

}
