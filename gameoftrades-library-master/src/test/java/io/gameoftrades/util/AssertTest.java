package io.gameoftrades.util;

import org.junit.Test;

public class AssertTest {

    @Test
    public void testAssertOk() {
        Assert.equals("a", "a");
        Assert.notEmpty("a");
        Assert.notNegative(0);
        Assert.notNull("a");
        Assert.positive(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertEqualsFail() {
        Assert.equals("a", "b");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertNotEmptyFail() {
        Assert.notEmpty("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertNotNullFail() {
        Assert.notNull(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertPositiveFail() {
        Assert.positive(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertNotNegativeFail() {
        Assert.positive(-1);
    }

}
