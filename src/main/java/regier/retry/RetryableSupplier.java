package regier.retry;

@FunctionalInterface
public interface RetryableSupplier<R> {

    public R get() throws DependencyFailure;
}
