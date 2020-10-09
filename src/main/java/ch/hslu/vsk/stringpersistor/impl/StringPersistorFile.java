/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hslu.vsk.stringpersistor.impl;

import ch.hslu.vsk.stringpersistor.api.PersistedString;
import ch.hslu.vsk.stringpersistor.api.StringPersistor;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Persists strings with a timestamp into a file using a FileWriter. It also reads out those Files and returns the
 * persisted strings in form of a list which stores objects from of class PersistedString.
 *
 * @author Boas Meier
 * @version JDK 12.0.2
 */
public final class StringPersistorFile implements StringPersistor {

    private File file;

    public final File getFile() {
        return this.file;
    }

    @Override
    public final void setFile(final File file) {
        this.file = file;
        try {
            if (this.file.createNewFile()) {
                System.out.println("File created: " + this.file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public final void save(final Instant timestamp, final String payload) {
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(this.file, true))) {
            buffer.write(timestamp.toString() + " | " + payload);
            buffer.newLine();
        } catch (IOException ex) {
            System.out.println("An error occurred while writing: " + ex.getMessage());
        }
    }

    @Override
    public final List<PersistedString> get(final int count) {
        List<PersistedString> list = new ArrayList<>();
        try (BufferedReader buffer = new BufferedReader(new FileReader(this.file))) {
            for (int i = 0; i < count; i++) {
                String[] s;
                s = buffer.readLine().split(" \\| ");
                list.add(new PersistedString(Instant.parse(s[0]), s[1]));
            }
        } catch (IOException ex) {
            System.out.println("An error occurred while reading: " + ex.getMessage());
        }
        return list;
    }

    @Override
    public final int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.file);
        return hash;
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
        if (!Objects.equals(this.file, other.file)) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        return "StringPersistorFile{" + "file=" + file + '}';
    }
}
