package org.bioe134.crispr;

//import javafx.util.Pair;

/**
 *
 * @author J. Christopher Anderson
 */
public class Design_CRISPR_Oligos {
    
    public void initiate() throws Exception {
        
        //TODO:  write this initiate (if needed)
        
    }
    
    public String[] run(String cds) throws Exception {
        
        //TODO:  write this algorithm, put the oligo sequences in line below
        cds = cds.toUpperCase();
        int indexPAM = cds.indexOf("GG", 21);
        String oligo1 = cds.substring(indexPAM-21, indexPAM-1);
        String oligo2 = "";
        // Alternative way of creating complimentary DNA:
        // oligo2 = oligo1;
        // oligo2 = oligo2.replace("A", "t")
        //                .replace("T", "a")
        //                .replace("C", "g")
        //                .replace("G", "c")
        //                .toUpperCase();
        for(int bp = 0; bp < oligo1.length(); bp++) {
            char currbp = oligo1.charAt(bp);
            if (currbp == 'G') {
                oligo2 += 'C';
            } else if (currbp == 'C') {
                oligo2 += 'G';
            } else if (currbp == 'A') {
                oligo2 += 'T';
            } else {
                oligo2 += 'A';
            }
        }
        String[] out = new String[] {oligo1, oligo2};
        return out;
    }
    
    public static void main(String[] args) throws Exception {
        //Create some exampole arguments, here the amilGFP coding sequence
        String cds = "ATGTCTTATTCAAAGCATGGCATCGTACAAGAAATGAAGACGAAATACCATATGGAAGGCAGTGTCAATGGCCATGAATTTACGATCGAAGGTGTAGGAACTGGGTACCCTTACGAAGGGAAACAGATGTCCGAATTAGTGATCATCAAGCCTGCGGGAAAACCCCTTCCATTCTCCTTTGACATACTGTCATCAGTCTTTCAATATGGAAACCGTTGCTTCACAAAGTACCCGGCAGACATGCCTGACTATTTCAAGCAAGCATTCCCAGATGGAATGTCATATGAAAGGTCATTTCTATTTGAGGATGGAGCAGTTGCTACAGCCAGCTGGAACATTCGACTCGAAGGAAATTGCTTCATCCACAAATCCATCTTTCATGGCGTAAACTTTCCCGCTGATGGACCCGTAATGAAAAAGAAGACCATTGACTGGGATAAGTCCTTCGAAAAAATGACTGTGTCTAAAGAGGTGCTAAGAGGTGACGTGACTATGTTTCTTATGCTCGAAGGAGGTGGTTCTCACAGATGCCAATTTCACTCCACTTACAAAACAGAGAAGCCGGTCACACTGCCCCCGAATCATGTCGTAGAACATCAAATTGTGAGGACCGACCTTGGCCAAAGTGCAAAAGGCTTTACAGTCAAGCTGGAAGCACATGCCGCGGCTCATGTTAACCCTTTGAAGGTTAAATAA";
        
        //Instantiate and initiate the Function
        Design_CRISPR_Oligos func = new Design_CRISPR_Oligos();
        func.initiate();
        
        //Run the function on the example
        String[] oligos = func.run(cds);
        
        //Print out the result
        System.out.println("oligo1: " + oligos[0]);
        System.out.println("oligo2: " + oligos[1]);
    }
}