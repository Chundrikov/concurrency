public class DemonstrateDeadlock {
    private static final int NUM_THREADS = 20;
    private static final int NUM_ACCOUNTS = 5;
    private static final int NUM_ITERATIONS = 10000000;

    public static void main(String[] args) {
        // multiple threads accessing and modifying Random and Account
        // concurrently without proper synchronization
        final Random rnd = new Random();
        final Account[] accounts = new Account[NUM_ACCOUNTS];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account();
        }

        class TransferThread extends Thread {
            public void run() {
                for (int i = 0; i < NUM_ITERATIONS; i++) {
                    int fromAcc = rnd.nextInt(NUM_ACCOUNTS);
                    int toAcc = rnd.nexInt(NUM_ACCOUNTS);
                    DollarAmount amount = new DollarAmount(rnd.nextInt(1000));

                    transferMoney(accounts[fromAcc],
                            accounts[toAcc], amount);
                }
            }
        }

        // the transferMoney method is called within the run method
        // of each Transfer Thread.
        // This method involves acquiring locks on both the
        // from Account and to Account objects before performing the transfer.
        // If multiple threads try to transfer money between the same
        // accounts concurrently, it can result in a deadlock situation
        for (int i = 0; i < NUM_THREADS; i++) {
            new TransferThread().start();
        }
    }
}