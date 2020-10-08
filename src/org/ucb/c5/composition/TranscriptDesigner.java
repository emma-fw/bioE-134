package org.ucb.c5.composition;

import org.ucb.c5.composition.checkers.*;
import org.ucb.c5.composition.model.Codon;
import org.ucb.c5.composition.model.RBSOption;
import org.ucb.c5.composition.model.Transcript;
import org.ucb.c5.sequtils.HairpinCounter;
import org.ucb.c5.utils.FileUtils;
import org.ucb.c5.utils.TSVParser;

import java.util.*;

/**
 * This reverse translates a protein sequence to a DNA and chooses an RBS. It
 * uses the highest CAI codon for each amino acid in the specified Host.
 *
 * @author J. Christopher Anderson
 */
public class TranscriptDesigner {

    private RBSChooser rbsChooser;
    private Map<Character, ArrayList<Codon>> aminoAcidCodons;
    private Random generator;

    private ForbiddenSequenceChecker forbiddenCheck;
    private InternalRBSChecker rbsCheck;
    private PromoterChecker promoterCheck;
    private RepeatSequenceChecker repeatCheck;
    //private RNAInterferenceChecker rnaCheck;
    private RNAseE_Site_Checker RNAseCheck;
    private NoInternalRBSChecker rbsCheck2;
    private HairpinCounter secondaryCheck;

    public void initiate() throws Exception {

        generator = new Random(0);

        //Initialize the RBSChooser
        rbsChooser = new RBSChooser();  //Delete the 2 to use old algorithm
        rbsChooser.initiate();

        //Initialize checkers
        forbiddenCheck = new ForbiddenSequenceChecker();
        forbiddenCheck.initiate();
        rbsCheck = new InternalRBSChecker();
        rbsCheck.initiate();
        promoterCheck = new PromoterChecker();
        promoterCheck.initiate();
        repeatCheck = new RepeatSequenceChecker();
//        rnaCheck = new RNAInterferenceChecker();
//        rnaCheck.initiate();
        RNAseCheck = new RNAseE_Site_Checker();
        RNAseCheck.initiate();
        rbsCheck2 = new NoInternalRBSChecker();
        rbsCheck2.initiate();
        secondaryCheck = new HairpinCounter();
        secondaryCheck.initiate();


        //Parse codon_usage.txt to get each codon.
        TSVParser parser = new TSVParser();
        List<Map<String,String>> codonUsageData =
                parser.run(FileUtils.readResourceFile("composition/data/codon_usage.txt"));

        aminoAcidCodons = new HashMap<Character, ArrayList<Codon>>();

        aminoAcidCodons.put('A', new ArrayList<Codon>());
        aminoAcidCodons.put('C', new ArrayList<Codon>());
        aminoAcidCodons.put('D', new ArrayList<Codon>());
        aminoAcidCodons.put('E', new ArrayList<Codon>());
        aminoAcidCodons.put('F', new ArrayList<Codon>());
        aminoAcidCodons.put('G', new ArrayList<Codon>());
        aminoAcidCodons.put('H', new ArrayList<Codon>());
        aminoAcidCodons.put('I', new ArrayList<Codon>());
        aminoAcidCodons.put('K', new ArrayList<Codon>());
        aminoAcidCodons.put('L', new ArrayList<Codon>());
        aminoAcidCodons.put('M', new ArrayList<Codon>());
        aminoAcidCodons.put('N', new ArrayList<Codon>());
        aminoAcidCodons.put('P', new ArrayList<Codon>());
        aminoAcidCodons.put('Q', new ArrayList<Codon>());
        aminoAcidCodons.put('R', new ArrayList<Codon>());
        aminoAcidCodons.put('S', new ArrayList<Codon>());
        aminoAcidCodons.put('T', new ArrayList<Codon>());
        aminoAcidCodons.put('V', new ArrayList<Codon>());
        aminoAcidCodons.put('W', new ArrayList<Codon>());
        aminoAcidCodons.put('Y', new ArrayList<Codon>());
        aminoAcidCodons.put('*', new ArrayList<Codon>());

        //Create a new Codon object for each codon in the parsed codon_usage.txt data.
        // Put into a hashmap keeping track of each protein and its codon options.
        for (Map codon : codonUsageData) {
            Codon currCodon = new Codon( (String) codon.get("codon"), ((String) codon.get("protein")).charAt(0),
                    Double.parseDouble((String) codon.get("fraction")),
                    Double.parseDouble((String) codon.get("frequency")), (String) codon.get("number"));
            aminoAcidCodons.get(currCodon.getpLetter()).add(currCodon);
        }

        //Sort codon lists in aminoAcidCodons map by frequency
        aminoAcidCodons.forEach((key,value) -> value.sort(Comparator.comparingDouble(Codon::getFrequency)));
    }

    public Transcript run(String peptide, Set<RBSOption> ignores) throws Exception {

        //Check truisms

        //Starts with a start codon
        if (!peptide.toUpperCase().substring(0,1).matches("M")) {
            throw new Exception("peptide must begin with methionine.");
        }

        String[] codon = new String[peptide.length()];

        int windowStart = 1;
        for (int i = 0; i<peptide.length(); i++) {
            if (i % 5 == 0) {
                if (checkers(codon.toString())) { //checker goes here
                    i = windowStart - 1;
                } else {
                    codon[i] = guidedRandom(generator.nextDouble(), peptide.charAt(i)).getCodon();
                    windowStart = i;
                }
            } else {
                codon[i] = guidedRandom(generator.nextDouble(), peptide.charAt(i)).getCodon();
            }
        }

        RBSOption selectedRBS = rbsChooser.run(codon.toString(), ignores);
        Transcript out = new Transcript(selectedRBS, peptide, codon);
        return out;
    }

    private Codon guidedRandom(double ran, char protein) {
        ArrayList<Codon> options = aminoAcidCodons.get(protein);
        double sum = 0;
        for (Codon c : options) {
            sum += c.getFrequency();
            if (ran <= sum) {
                return c;
            }
        }
        return null;
    }

    private boolean checkers(String sequence) throws Exception {
        if (forbiddenCheck.run(sequence) == false) { return false; }
        if (rbsCheck.run(sequence) == true) { return false; }
        if (promoterCheck.run(sequence) == false) { return false; }
        if ( repeatCheck.run(sequence) == false) {return false; }
        //if ( rnaCheck.run(sequence) == false) {return false; }
        if (RNAseCheck.run(sequence) == false) { return false; }
        if (rbsCheck2.run(sequence) == false) { return false; }
        if (secondaryCheck.run(sequence) > 0.0) { return false; }
        return true;
    }

    public static void main(String[] args) throws Exception {
        TranscriptDesigner test = new TranscriptDesigner();
        test.initiate();
        String protein = "MGASDJTWNMALHSMOAITEMAHSIBDHSZK";
        Transcript transcript = test.run(protein, null);
        System.out.println("Transcript length: " + transcript.toString().length());
        System.out.println("Protein length " + protein.length());

    }
}
