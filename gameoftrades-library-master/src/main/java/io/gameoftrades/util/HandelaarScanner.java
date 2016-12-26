package io.gameoftrades.util;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import io.gameoftrades.model.Handelaar;

/**
 * Scant de <b>io.gameoftrades.studentXX</b> packages op zoek naar <b>HandelaarImpl</b> klassen. 
 */
public final class HandelaarScanner {

    public static NavigableMap<String, Handelaar> vindImplementaties() {
        return vindImplementaties(HandelaarScanner.class.getClassLoader());
    }

    public static NavigableMap<String, Handelaar> vindImplementaties(ClassLoader loader) {
        RuntimeException failure = null;
        NavigableMap<String, Handelaar> results = new TreeMap<>();
        String base = "io.gameoftrades.student";
        for (int t = 0; t < 100; t++) {
            String nr = (t < 10 ? "0" : "") + String.valueOf(t);
            failure = vindEnInstantieer(results, loader, base, nr, failure);
        }
        if (results.isEmpty()) {
            failure = vindEnInstantieer(results, loader, base, "NN", failure);
            if (!results.isEmpty()) {
                System.err.println("Waarschuwing: Je hebt de 'studentNN' package nog niet aangepast met je groepsnummer!");
            }
        }
        if (results.isEmpty() && failure != null) {
            throw failure;
        }
        return results;
    }

    private static RuntimeException vindEnInstantieer(Map<String, Handelaar> results, ClassLoader loader, String base, String nr, RuntimeException failure) {
        Class<?> found;
        try {
            found = loader.loadClass(base + nr + ".HandelaarImpl");
            results.put(nr, (Handelaar) found.newInstance());
            return failure;
        } catch (ClassNotFoundException ex) {
            // Ignore
            return failure;
        } catch (InstantiationException e) {
            String msg = e.getMessage() + " kon niet geinstantieerd worden met een no-args constructor.";
            System.err.println(msg);
            return new RuntimeException(msg, e);
        } catch (IllegalAccessException e) {
            System.err.println(e.getMessage());
            return new RuntimeException(e.getMessage(), e);
        }
    }

}
