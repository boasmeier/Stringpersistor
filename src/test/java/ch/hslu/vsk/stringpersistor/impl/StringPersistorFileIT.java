/*
 * StringPersistorFileIT.java
 * Created on 05.10.2020
 *
 * Copyright(c) 2020 Tobias Heller.
 * This software is the proprietary information of Boas Meier.
 */
package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

/**
 * Integrationtest for the quality requirements of the StringPersistor.
 *
 * @author Tobias Heller
 */
public class StringPersistorFileIT {

    public static final String FILE_NAME_1 = "." + File.separator + "tmp_spfIT1.log";
    public static final String FILE_NAME_2 = "." + File.separator + "tmp_spfIT2.log";

    @AfterAll
    public static void tearDown() {
        new File(FILE_NAME_1).delete();
        new File(FILE_NAME_2).delete();
    }

    /**
     * Test of save() method, of class StringPersistorFile to test the following
     * requirement. With save() at least 1‘000 Objects can be saved.
     */
    @Test
    public void testSave1001Objects() {
        new File(FILE_NAME_1).delete();
        int totalObjects = 1001;
        StringPersistor persistor = new StringPersistorFile();
        File file = new File(FILE_NAME_1);
        persistor.setFile(file);
        List<String> savedStrings = new ArrayList<>();
        for (int i = 1; i <= totalObjects; i++) {
            String value = "String: " + String.valueOf(i);
            savedStrings.add(value);
            persistor.save(Instant.now(), value);
        }
        List<PersistedString> allStrings = persistor.get(Integer.MAX_VALUE);
        assertThat(allStrings.size()).isEqualTo(totalObjects);
        for (int i = totalObjects - 1; i <= 0; i--) {
            assertThat(allStrings.get(i)).isEqualTo("String: " + (totalObjects - i));
        }
    }

    /**
     * Test of get() method, of class StringPersistorFile to test the following
     * requirement. With get() 100 Objects with 1000 characters each can be read
     * in maximum 200ms.
     */
    @Test
    public void testGet100ObjectsIn200ms() {
        new File(FILE_NAME_2).delete();
        int totalObjects = 100;
        List<Long> messwerte = new ArrayList<>();
        StringPersistor persistor = new StringPersistorFile();
        for (int m = 0; m < 20; m++) {
            File file = new File(FILE_NAME_2);
            persistor.setFile(file);
            List<String> savedStrings = new ArrayList<>();
            for (int i = 1; i <= totalObjects; i++) {
                String value = "";
                for (int x = 1; x <= 1000; x++) {
                    value += i;
                }
                savedStrings.add(value);
                persistor.save(Instant.now(), value);
            }
            List<PersistedString> allStrings;
            long start = System.currentTimeMillis();
            allStrings = persistor.get(100);
            long finish = System.currentTimeMillis();
            assertThat(allStrings.size()).isEqualTo(totalObjects);
            new File(FILE_NAME_2).delete();
            messwerte.add(finish - start);
        }

        long sum = 0L;
        for (long me : messwerte) {
            sum += me;
        }
        assertThat(sum / messwerte.size()).isLessThan(200);
    }
}
