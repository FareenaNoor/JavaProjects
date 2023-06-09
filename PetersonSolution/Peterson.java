package peterson;



class Peterson {
  static boolean[] flag = {false, false};
  static int turn = 0;
  static int N = 2;

  static Thread process(int i) {
    return new Thread(() -> {
      int j = 1 - i;
      for (int n=0; n<N; n++) {
        System.out.println("p"+i+": want to enter in CS"); // LOCK
        flag[i] = true; // 1
        turn = j;       // 2
        while (flag[j] && turn == j) Thread.yield(); // 3
        
        System.out.println("p"+i+":entered in CS");
        sleep(1000 * Math.random()); // 4

        System.out.println("p"+i+":exit CS"); // UNLOCK
        flag[i] = false; // 5
      }
    });
  }


  public static void main(String[] args) {
    try {
    System.out.println("Starting 2 processes (threads) ...");
    Thread p0 = process(0);
    Thread p1 = process(1);
    p0.start();
    p1.start();
    p0.join();
    p1.join();
    }
    catch (InterruptedException e) {}
  }

  static void sleep(double t) {
    try { Thread.sleep((long)t); }
    catch (InterruptedException e) {}
  }

}