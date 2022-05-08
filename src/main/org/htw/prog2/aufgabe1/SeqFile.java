package org.htw.prog2.aufgabe1;

import java.util.HashSet;

public class SeqFile {
    /**
     * Reads the specified FASTA file and stores sequences. In case the file does not exist or is not a valid FASTA
     * file, the Constructor does not throw an Exception. Instead, isValid() on the resulting object will return false.
     * @param filename
     */
    public SeqFile(String filename) {
    }
    
    /**
     * Reads the specified FASTA file.
     * @param filename The path to the FASTA file
     * @return false if the file could not be parsed (wrong format, does not exist), true otherwise.
     */
    private boolean readFile(String filename) {
        return false;
    }

    /**
     * Adds the sequence in the passed StringBuilder to the internal hash set and also sets the first sequence if it
     * is still empty.
     * @param seq SequenceBuilder to get the sequence from.
     * @return The length of the added sequence.
     */
    private int addSequence(StringBuilder seq) {
        return -1;
    }

    /**
     *
     * @return The number of sequences read from the FASTA file, or 0 if isValid() is false.
     */
    public int getNumberOfSequences() {
        return 0;
    }

    /**
     *
     * @return The sequences read from the FASTA file, or an empty HashSet if isValid() is false.
     */
    public HashSet<String> getSequences() {
        return null;
    }

    /**
     *
     * @return The first sequence read from the FASTA file, or an empty String if isValid() is false.
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
