package regier.retry;

public class RetryAfterException extends DependencyFailure {
    private int seconds;

    public RetryAfterException(int seconds) {
        assert(seconds > 1);
        this.seconds = seconds;
    }

    public long getRetryAfterMillis() {
        return seconds * 1000;
    }
}
