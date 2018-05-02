package br.inpe.climaespacial.swd.commons.helpers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createCustom;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createIfNull;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.createIfNullOrEmpty;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwCustom;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;
import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNullOrEmpty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.exceptions.InvalidCustomException;

public class ThrowHelperTest {

    private static final String ARGUMENT_CANNOT_BE_NULL = "Argument \"name\" cannot be null.";

    private static final String ARGUMENT_NAME = "name";

    private static final String ARGUMENT_NAME_NULL_OR_EMPTY = "Argument \"argumentName\" cannot be null or empty.";

    private static final String ARGUMENT_CANNOT_BE_NULL_OR_EMPTY = "Argument \"name\" cannot be null or empty.";

    private static final String STRING_VALUE = "value";

    private static final String CUSTOM_EXCEPTION_MESSAGE = "Custom exception.";

    private static final String CONSTRUCTOR_ACCEPTING_STRING = "Custom exception does not have a monadic constructor accepting String.";

    private static final String CONSTRUCTOR_ACCEPTING_STRING_AND_THROWABLE = "Custom exception does not have a dyadic constructor accepting String and Throwable.";

    @Test
    public void throwIfNull_calledWithNullArgumentName_throws() {
        RuntimeException re = null;

        try {
            throwIfNull(null, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_NAME_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNull_calledWithEmptyArgumentName_throws() {
        RuntimeException re = null;

        try {
            throwIfNull(null, "");
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_NAME_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNull_called_throws() {
        RuntimeException re = null;

        try {
            throwIfNull(null, ARGUMENT_NAME);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_CANNOT_BE_NULL, re.getMessage());
    }

    @Test
    public void throwIfNull_called_succeeds() {
        RuntimeException re = null;

        try {
            throwIfNull("", ARGUMENT_NAME);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNull(re);
    }

    @Test
    public void createIfNull_called_returnsException() {
        RuntimeException re = createIfNull(ARGUMENT_NAME);

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_CANNOT_BE_NULL, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForString_calledWithNullArgumentName_throws() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty((String) null, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_NAME_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForString_calledWithEmptyArgumentName_throws() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty((String) null, "");
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_NAME_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForString_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty((String) null, ARGUMENT_NAME);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_CANNOT_BE_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForString_calledWithEmptyArgument_throws() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty("", ARGUMENT_NAME);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_CANNOT_BE_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForString_called_succeeds() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty(STRING_VALUE, ARGUMENT_NAME);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNull(re);
    }

    @Test
    public void throwIfNullOrEmptyForList_calledWithNullArgumentName_throws() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty((List<?>) null, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_NAME_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForList_calledWithEmptyArgumentName_throws() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty((List<?>) null, "");
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_NAME_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForList_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty((List<?>) null, ARGUMENT_NAME);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_CANNOT_BE_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForList_calledWithEmptyArgument_throws() {
        RuntimeException re = null;

        try {
            throwIfNullOrEmpty(new ArrayList<>(), ARGUMENT_NAME);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_CANNOT_BE_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwIfNullOrEmptyForList_called_succeeds() {
        RuntimeException re = null;

        try {
            List<Object> ol = new ArrayList<>();
            ol.add(new Object());
            throwIfNullOrEmpty(ol, ARGUMENT_NAME);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNull(re);
    }

    @Test
    public void createIfNullOrEmpty_called_returnsException() {
        RuntimeException re = createIfNullOrEmpty(ARGUMENT_NAME);

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(ARGUMENT_CANNOT_BE_NULL_OR_EMPTY, re.getMessage());
    }

    @Test
    public void throwCustom_calledWithCustomExceptionWithoutRequiredConstructor_throws() {
        InvalidCustomException ice = null;

        try {
            throwCustom(CUSTOM_EXCEPTION_MESSAGE, ImproperException.class);
        } catch (InvalidCustomException e) {
            ice = e;
        }

        assertNotNull(ice);
        assertEquals(InvalidCustomException.class, ice.getClass());
        assertEquals(CONSTRUCTOR_ACCEPTING_STRING, ice.getMessage());
    }

    @Test
    public void throwCustom_calledWithCustomException_throwsCustomException() {
        ProperException pe = null;
        String message = CUSTOM_EXCEPTION_MESSAGE;

        try {
            throwCustom(message, ProperException.class);
        } catch (ProperException e) {
            pe = e;
        }

        assertNotNull(pe);
        assertEquals(ProperException.class, pe.getClass());
        assertEquals(message, pe.getMessage());
    }

    @Test
    public void createCustom_calledWithCustomExceptionWithoutRequiredConstructor_throws() {
        InvalidCustomException ice = null;

        try {
            createCustom(CUSTOM_EXCEPTION_MESSAGE, ImproperException.class);
        } catch (InvalidCustomException e) {
            ice = e;
        }

        assertNotNull(ice);
        assertEquals(InvalidCustomException.class, ice.getClass());
        assertEquals(CONSTRUCTOR_ACCEPTING_STRING, ice.getMessage());
    }

    @Test
    public void createCustom_called_returnsException() {
        ProperException re = createCustom(CUSTOM_EXCEPTION_MESSAGE, ProperException.class);

        assertNotNull(re);
        assertEquals(ProperException.class, re.getClass());
        assertEquals(CUSTOM_EXCEPTION_MESSAGE, re.getMessage());
    }

    @Test
    public void createCustomWithCause_calledWithCustomExceptionWithoutRequiredConstructor_throws() {
        RuntimeException re = new RuntimeException();
        InvalidCustomException ice = null;

        try {
            createCustom(CUSTOM_EXCEPTION_MESSAGE, re, ImproperException.class);
        } catch (InvalidCustomException e) {
            ice = e;
        }

        assertNotNull(ice);
        assertEquals(InvalidCustomException.class, ice.getClass());
        assertEquals(CONSTRUCTOR_ACCEPTING_STRING_AND_THROWABLE, ice.getMessage());
    }

    @Test
    public void createCustomWithCause_called_returnsException() {
        RuntimeException re = new RuntimeException();
        ProperException pe = createCustom(CUSTOM_EXCEPTION_MESSAGE, re, ProperException.class);

        assertNotNull(pe);
        assertEquals(ProperException.class, pe.getClass());
        assertEquals(CUSTOM_EXCEPTION_MESSAGE, pe.getMessage());
        assertEquals(re, pe.getCause());
    }

}
