package org.ucb.c5.composition.checkers;

/**
 * Checks that the G and C content within a sequence is above lower threshold and below upper threshold.
 * Supports different threshold values depending on sequence length.
 *
 * @author Emma Wawrzynek
 */
public class GCChecker {

    //Customizable parameters that allow shorter sequences to be analyzed differently than longer sequences.
    //The threshold percentages should be updated after reading more papers.

    //Define length cutoffs for sequences.
    private final int MIN_LENGTH = 5;
    private final int SMALL_LENGTH = 20;
    private final int MEDIUM_LENGTH = 100;

    //Threshold percentages for CG where %CG should be < THRESHOLD_UPPER and > THRESHOLD_LOWER
    //%CG is found by dividing the count of C and G nucleotides by the total length of the sequence.
    private final double SMALL_THRESHOLD_LOWER = 0.4;
    private final double SMALL_THRESHOLD_UPPER = 0.55;
    private final double MEDIUM_THRESHOLD_LOWER = 0.4;
    private final double MEDIUM_THRESHOLD_UPPER = 0.55;
    private final double LONG_THRESHOLD_LOWER = 0.4;
    private final double LONG_THRESHOLD_UPPER = 0.55;

    public boolean run(String seq) throws Exception {

        //If sequence does not contain ATCG nucleotides:
        if (!seq.toUpperCase().matches("[ATCG]+")) {
            throw new Exception("sequence must contain valid nucleotides.");
        }

        int seqLength = seq.length();

        //Use appropriate threshold numbers for the size of the sequence.
        if (seqLength < MIN_LENGTH) {
            return true;
        } else if (seqLength < SMALL_LENGTH) {
            double CGPercentage = CGpercent(seq);
            return (CGPercentage > SMALL_THRESHOLD_LOWER && CGPercentage < SMALL_THRESHOLD_UPPER);
        } else if (seqLength < MEDIUM_LENGTH) {
            double CGPercentage = CGpercent(seq);
            return (CGPercentage > MEDIUM_THRESHOLD_LOWER && CGPercentage < MEDIUM_THRESHOLD_UPPER);
        } else { //Need to implement breaking long sequences into small pieces and checking those.
            double CGPercentage = CGpercent(seq);
            return (CGPercentage > LONG_THRESHOLD_LOWER && CGPercentage < LONG_THRESHOLD_UPPER);
        }
    }

    //Calculates the percent C and G in the entire sequence.
    public double CGpercent(String seq) {

        int seqLength = seq.length();
        int CCount = 0;
        int GCount = 0;

        //Count C's and G's separately to support any G or C specific operations.
        for (int i = 0; i < seqLength; i++) {
            if (seq.charAt(i) == 'C') {
                CCount++;
            } else if (seq.charAt(i) == 'G') {
                GCount++;
            } else {
                continue;
            }
        }
        double CGPercent = (CCount + GCount) / seqLength;
        return CGPercent;
    }

    public static void main(String[] args) throws Exception {

    }

}
