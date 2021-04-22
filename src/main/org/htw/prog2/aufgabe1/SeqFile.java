package org.htw.prog2.aufgabe1;

import java.util.LinkedHashSet;

public class SeqFile {
    /**
     * Reads the specified FASTA file and stores sequences. In case the file does not exist or is not a valid FASTA
     * file, the Constructor does not throw an Exception. Instead, isValid() on the resulting object will return false.
     * @param filename
     */
    public SeqFile(String filename) {
    }

    /**
     *
     * @return The number of sequences read from the FASTA file, or 0 is isValid() is false.
     */
    public int getNumberOfSequences() {
        return 0;
    }

    /**
     *
     * @return The sequences read from the FASTA file, or an empty HashSet is isValid() is false.
     */
    public LinkedHashSet<String> getSequences() {
        return null;
    }

    /**
     *
     * @return The first sequence read from the FASTA file, or an empty String is isValid() is false.
     */
    public String getFirstSequence() {
        return "";
    }

    /**
     *
     * @return true if the FASTA file was read successfully, false otherwise.
     */
    public boolean isValid() {
        return false;
    }
}
