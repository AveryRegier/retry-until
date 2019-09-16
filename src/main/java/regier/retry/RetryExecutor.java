package regier.retry;

import java.util.Random;
import java.util.function.Supplier;

public class RetryExecutor {
    private static final Random random = new Random();

    public <R> R get(RetryableSupplier<R> command,
                     long willingness,
                     Supplier<Boolean> canceller)
            throws DependencyFailure, InterruptedException {
        long until = System.currentTimeMillis() + willingness;
        int tries = 0;
        do {
            long nextWait;
            try {
                return command.get();
            } catch (ClientFailure cf) {
                throw cf;
            } catch (RetryAfterException re) {
                nextWait = re.getRetryAfterMillis();
                if (patienceWillExpire(until, nextWait, canceller)) {
                    throw re;
                }
                notify(re, nextWait);
            } catch (ServerFailure sf) {
                nextWait = exponentialDelay(tries++);
                if (patienceWillExpire(until, nextWait, canceller)) {
                    throw sf;
                }
                notify(sf, nextWait);
            }
            Thread.sleep(nextWait + jitter());
        } while (true);
    }

    private boolean patienceWillExpire(long until, long nextWait,
                                       Supplier<Boolean> canceller) {
        return canceller.get() || System.currentTimeMillis() + nextWait >= until;
    }

    private void notify(DependencyFailure failure, long nextWait) {
        // notify the customer
    }

    private long exponentialDelay(int tries) {
        return ((int) Math.pow(2, tries)) * 1000L;
    }

    private long jitter() {
        return random.nextInt(1000) - 500;
    }
}
