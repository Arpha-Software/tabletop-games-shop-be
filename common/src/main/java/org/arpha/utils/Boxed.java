package org.arpha.utils;


import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Boxed<T> {
    private final T value;

    private Boxed(T value) {
        this.value = value;
    }

    public static <T> Boxed<T> of(T value) {
        return new Boxed<>(value);
    }

    public static <T> Boxed<T> empty() {
        return new Boxed<>(null);
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    public T orElse(T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        return value != null ? value : supplier.get();
    }

    public <Y extends Throwable> T orElseThrow(Supplier<? extends Y> exceptionSupplier) throws Y {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public Boxed<T> filter(Predicate<? super T> predicate) {
        if (value != null && predicate.test(value)) {
            return this;
        } else {
            return empty();
        }
    }

    public <U> Boxed<U> to(Function<? super T, ? extends U> mapper) {
        if (value != null) {
            return Boxed.of(mapper.apply(value));
        } else {
            return empty();
        }
    }

    public <U> Boxed<U> flatMap(Function<? super T, Boxed<U>> mapper) {
        if (value != null) {
            return mapper.apply(value);
        } else {
            return empty();
        }
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    public <U> Boxed<U> mapToBoxed(Function<? super T, ? extends U> mapper) {
        if (value != null) {
            return Boxed.of(mapper.apply(value));
        } else {
            return empty();
        }
    }

    public Optional<T> toOptional() {
        return Optional.ofNullable(value);
    }

    public Boxed<T> doWith(Consumer<? super T> action) {
        if (value != null) {
            action.accept(value);
        }
        return this;
    }

    public void apply(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

}

