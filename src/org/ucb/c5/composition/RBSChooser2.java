package org.ucb.c5.composition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.ucb.c5.composition.model.RBSOption;
import org.ucb.c5.sequtils.HairpinCounter;
import org.ucb.c5.sequtils.Translate;
import org.ucb.c5.sequtils.CalcEditDistance;

/**
 * Second generation RBSChooser algorithm
 *
 * Employs a list of genes and their associated ribosome binding sites for
 * highly-expressed proteins in E. coli.
 *
 * @author J. Christopher Anderson
 * @author Emma Wawrzynek emma-fw
 */
public class RBSChooser2 {

    private List<RBSOption> rbss = new ArrayList<RBSOption>();
    private HashMap<String, String> cdss = new HashMap<String, String>();
    private Translate translation = new Translate();
    private CalcEditDistance distCalc = new CalcEditDistance();
    private HairpinCounter countHairpin = new HairpinCounter();
    static AtomicInteger counter = new AtomicInteger();
    public static int counter2 = 0;

    public void initiate() throws Exception {

        //Initiate needed classed for future use.
        translation.initiate();
        distCalc.initiate();
        countHairpin.initiate();

        // Reads in coli_genes.txt and parses through line by line, putting each tab separated
        // category into a different index of an array.
        // Takes the gene name and code and puts into the cdss hashmap.
        BufferedReader coliData = new BufferedReader(
                new FileReader("src/org/ucb/c5/composition/data/coli_genes.txt"));
        String gene = coliData.readLine(); //One line of file.
        while (gene != null) {
            String[] dataArray = gene.split("\t"); //Split at tabs.
            cdss.put(dataArray[1], dataArray[6]); //Capture gene name and CDS.
            gene = coliData.readLine();
        }
        coliData.close();

        //Reads in rbs_options.txt and parses through line by line, putting each tab separated
        // category into a different index of an array.
        // Creates a new RBSOption object with the RBS data from rbs_options and the corresponding data in cdss.
        BufferedReader rbsOption = new BufferedReader(new FileReader("src/org/ucb/c5/composition/data/rbs_options.txt"));
        String rbs = rbsOption.readLine();
        while (rbs != null) {
            String[] thisRbs = rbs.split("\t");
            String abbSeq = cdss.get(thisRbs[0]).substring(3, 21);
            RBSOption thisRBS = new RBSOption(thisRbs[0], null, thisRbs[1],
                    cdss.get(thisRbs[0]), translation.run(abbSeq));
            rbss.add(thisRBS);
            rbs = rbsOption.readLine();
        }
        rbsOption.close();
    }

    /**
     * Provided an ORF of sequence 'cds', this computes the best ribosome
     * binding site to use from a list of options.
     * It also permits a list of options to exclude.
     *
     * @param cds The DNA sequence, ie ATGCATGAT...
     * @param ignores The list of RBS's to exclude
     * @return
     * @throws Exception
     */
    public RBSOption run(String cds, Set<RBSOption> ignores) throws Exception {


        //Checking validity of input cds:
        //If ignores contains all rbs options:
        if (ignores.size() >= rbss.size()) {
            throw new Exception("no valid rbs options.");
        }
        //If cds is too short to include the 6 compared amino acids:
        if (cds.length() < 21) {
            throw new Exception("cds is too short.");
        }
        //If cds does not contain ATCG nucleotides:
        if (!cds.toUpperCase().matches("[ATCG]+")) {
            throw new Exception("cds must contain valid nucleotides.");
        }
        //If cds does not begin with start codon (as it is ignored when comparing aa):
        if (!cds.toUpperCase().substring(0,3).matches("ATG")) {
            throw new Exception("cds must begin with start codon.");
        }

        //Translate first 6 aa of cds, excluding the start codon.
        String cds6aa = translation.run(cds.substring(3, 21));

        //Create a priority queue for sorting RBS options. Compares first 6 aa of rbs and cds,
        // and uses hairpins to break ties. Queue is sorted from smallest edit distance to largest.
        PriorityQueue<RBSOption> similarityQueue = new PriorityQueue<RBSOption>(rbss.size(), (a,b) -> {
            try {
                if (Integer.compare(distCalc.run(a.getFirst6aas(), cds6aa), distCalc.run(b.getFirst6aas(), cds6aa)) == 0) {
                    //counter2++;
                    //counter.getAndIncrement();
                    return Double.compare(countHairpin.run((a.getRbs() + cds)), countHairpin.run(b.getRbs() + cds));
                } else {
                    return Integer.compare(distCalc.run(a.getFirst6aas(), cds6aa), distCalc.run(b.getFirst6aas(), cds6aa));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        });

        //Add rbs options to priority queue.
        for (RBSOption option : rbss) {
            similarityQueue.offer(option);
        }

        //Poll from priority queue, returning the first rbs that is not in the ignores list.
        RBSOption toReturn = similarityQueue.poll();
        while (ignores.contains(toReturn)) {
            toReturn = similarityQueue.poll();
        }
        return toReturn;
    }


    public static void main(String[] args) throws Exception {

        Translate translation = new Translate();
        translation.initiate();

        //Create an example
        String cds = "ATGGTAAGAAAACAGTTGCAGAGAGTTGAATTATCACCATCGTTATATGACACAGCTTGGGTGGCTATGGTGCCGGAGCGTAGTTCTTCTCAA";


        //Initiate the chooser
        RBSChooser2 chooser = new RBSChooser2();
        chooser.initiate();


        //Make the first choice with an empty Set of ignores
        Set<RBSOption> ignores = new HashSet<>();
        RBSOption selected1 = chooser.run(cds, ignores);
        //System.out.println(counter2);
        System.out.println(counter2);

        //Add the first selection to the list of things to ignore
        ignores.add(selected1);
        //System.out.println(counter2);

        //Choose again with an ignore added
        RBSOption selected2 = chooser.run(cds, ignores);
        //System.out.println(counter2);

        //Print out the two options, which should be different
        System.out.println("CDS starts with:");
        System.out.println(cds.substring(0, 18));
        System.out.println(translation.run(cds.substring(3, 21)));
        System.out.println();
        System.out.println("Selected1:\n");
        System.out.println(selected1.toString());
        System.out.println(counter);
        System.out.println();
        System.out.println("Selected2:\n");
        System.out.println(selected2.toString());
        System.out.println(counter);

    }
}
