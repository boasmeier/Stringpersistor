/**
 * StringPersistorFile.java
 * Created on 05.10.2020
 *
 * Copyright(c) 2020 Boas Meier.
 * This software is the proprietary information of Boas Meier.
 *
 */
package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Persists strings with a timestamp into a file using a FileWriter. It also
 * reads out those Files and returns the persisted strings in form of a list
 * which stores objects from of class PersistedString.
 *
 * @author Boas Meier
 * @version JDK 12.0.2
 */
public final class StringPersistorFile implements StringPersistor {

    private static final Logger LOGGER = Logger.getLogger(StringPersistorFile.class.getName());

    private File file;

    public final File getFile() {
        return this.file;
    }

    @Override
    public final void setFile(final File file) {
        if (file == null || file.getPath().equals("")) {
            throw new IllegalStateException("Can not set file without a path.");
        }
        this.file = file;

        try {
            if (this.file.createNewFile()) {
                LOGGER.info("File created: " + this.file.getName());
            } else {
                LOGGER.info("File already exists.");
            }
        } catch (IOException e) {
            LOGGER.severe("An error occurred while set file: " + e.getMessage());
        }
    }

    @Override
    public final void save(final Instant timestamp, final String payload) {
        try ( BufferedWriter buffer = new BufferedWriter(new FileWriter(this.file, true))) {
            buffer.write(timestamp.toString() + " | " + payload);
            buffer.newLine();
        } catch (IOException ex) {
            System.out.println("An error occurred while writing: " + ex.getMessage());
        }
    }

    @Override
    public final List<PersistedString> get(final int count) {
        if (this.file == null) {
            throw new NullPointerException("File is not set.");
        }
        try ( RandomAccessFile raf = new RandomAccessFile(this.file, "r")) {
            return readLines(count, raf);
        } catch (IOException e) {
            LOGGER.severe("An error occurred while reading: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private List<PersistedString> readLines(final int count, RandomAccessFile raf) throws IOException {
        List<PersistedString> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        long fileLength = file.length() - 2;

        for (long i = fileLength; i >= 0; i--) {
            raf.seek(i);
            char c = (char) raf.read();
            if (c == '\n') {
                builder.reverse();
                String[] s = builder.toString().split(" \\| ");
                list.add(new PersistedString(Instant.parse(s[0]), s[1]));
                builder.setLength(0);
                if (list.size() >= count) {
                    return list;
                }
            }
            if (c != '\n' && c != '\r') {
                builder.append(c);
            }
        }
        if (count != 0) {
            builder.reverse();
            String[] s = builder.toString().split(" \\| ");
            list.add(new PersistedString(Instant.parse(s[0]), s[1]));
            builder.setLength(0);
        }

        return list;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(this.file);
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StringPersistorFile other = (StringPersistorFile) obj;
        return Objects.equals(this.file, other.getFile());
    }

    @Override
    public final String toString() {
        return "StringPersistorFile{" + "file=" + this.file + '}';
    }
}
