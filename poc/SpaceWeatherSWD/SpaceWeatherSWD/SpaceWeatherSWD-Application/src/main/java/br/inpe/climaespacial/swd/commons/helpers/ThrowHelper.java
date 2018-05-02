package br.inpe.climaespacial.swd.commons.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.exceptions.InvalidCustomException;

public final class ThrowHelper {

    private ThrowHelper() {
    }

    public static <T> void throwIfNull(T argument, String argumentName) {

        throwIfNullOrEmpty(argumentName, argumentName);

        if (argument == null) {
            throw createIfNull(argumentName);
        }
    }

    public static ArgumentException createIfNull(String argumentName) {
        return create("Argument \"" + argumentName + "\" cannot be null.");
    }

    public static <T> void throwIfNullOrEmpty(String argument, String argumentName) {

        if (argumentName == null || "".equals(argumentName)) {
            throw createIfNullOrEmpty("argumentName");
        }

        if (argument == null || "".equals(argument)) {
            throw createIfNullOrEmpty(argumentName);
        }
    }

    public static <T> void throwIfNullOrEmpty(List<T> argument, String argumentName) {

        if (argumentName == null || "".equals(argumentName)) {
            throw createIfNullOrEmpty("argumentName");
        }

        if (argument == null || argument.isEmpty()) {
            throw createIfNullOrEmpty(argumentName);
        }
    }

    public static ArgumentException createIfNullOrEmpty(String argumentName) {
        return create("Argument \"" + argumentName + "\" cannot be null or empty.");
    }

    public static <T extends RuntimeException> void throwCustom(String message, Class<T> clazz) {
        throw createCustom(message, clazz);
    }

    @SuppressWarnings("unchecked")
    public static <T extends RuntimeException> T createCustom(String message, Class<T> clazz) {
        try {
            Constructor<?> c = clazz.getConstructor(String.class);
            RuntimeException re = (RuntimeException) c.newInstance(message);
            return (T) re;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InvalidCustomException("Custom exception does not have a monadic constructor accepting String.");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends RuntimeException> T createCustom(String message, Throwable cause, Class<T> clazz) {
        try {
            Constructor<?> c = clazz.getConstructor(String.class, Throwable.class);
            RuntimeException re = (RuntimeException) c.newInstance(message, cause);
            return (T) re;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InvalidCustomException(
                    "Custom exception does not have a dyadic constructor accepting String and Throwable.");
        }
    }

    private static ArgumentException create(String message) {
        return new ArgumentException(message);
    }

}
