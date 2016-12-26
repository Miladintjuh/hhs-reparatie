package io.gameoftrades.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.NavigableMap;

import org.junit.Test;

import io.gameoftrades.model.Handelaar;

public class HandelaarScannerTest {

    @Test
    public void shouldLoad() {
        NavigableMap<String, Handelaar> result = HandelaarScanner.vindImplementaties();
        assertNotNull(result);
        assertTrue(result.containsKey("99"));
        assertNotNull(result.get("99"));
    }

}
