package org.ucb.c5.composition;

import org.ucb.c5.composition.model.RBSOption;
import org.ucb.c5.sequtils.Translate;

import java.util.HashSet;
import java.util.Set;

public class RBSChooser2Test {

    public void test1() throws Exception {
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

        //Add the first selection to the list of things to ignore
        ignores.add(selected1);

        //Choose again with an ignore added
        RBSOption selected2 = chooser.run(cds, ignores);
        ignores.add(selected2);

        RBSOption selected3 = chooser.run(cds,ignores);
        ignores.add(selected3);

        RBSOption selected4 = chooser.run(cds, ignores);
        ignores.add(selected4);

        RBSOption selected5 = chooser.run(cds, ignores);

        //Print out the two options, which should be different
        System.out.println("CDS starts with:");
        System.out.println(cds.substring(0, 18));
        System.out.println(translation.run(cds.substring(3, 21)));
        System.out.println();
        System.out.println("Selected1:\n");
        System.out.println(selected1.toString());
        System.out.println();
        System.out.println("Selected2:\n");
        System.out.println(selected2.toString());
        System.out.println();
        System.out.println("Selected3:\n");
        System.out.println(selected3.toString());
        System.out.println();
        System.out.println("Selected4:\n");
        System.out.println(selected4.toString());
        System.out.println();
        System.out.println("Selected5:\n");
        System.out.println(selected5.toString());
        System.out.println();

    }

    public void test2() throws Exception {

        Translate translation = new Translate();
        translation.initiate();

        //Create an example
        String cds = "ATGAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";


        //Initiate the chooser
        RBSChooser2 chooser = new RBSChooser2();
        chooser.initiate();

        //Make the first choice with an empty Set of ignores
        Set<RBSOption> ignores = new HashSet<>();
        RBSOption selected1 = chooser.run(cds, ignores);

        //Add the first selection to the list of things to ignore
        ignores.add(selected1);

        //Choose again with an ignore added
        RBSOption selected2 = chooser.run(cds, ignores);

        //Print out the two options, which should be different
        System.out.println("CDS starts with:");
        System.out.println(cds.substring(0, 18));
        System.out.println(translation.run(cds.substring(3, 21)));
        System.out.println();
        System.out.println("Selected1:\n");
        System.out.println(selected1.toString());
        System.out.println();
        System.out.println("Selected2:\n");
        System.out.println(selected2.toString());
    }



    public void test3() throws Exception {

        Translate translation = new Translate();
        translation.initiate();

        //Create an example
        String cds = "ATGACTGAAAAAAAATATATCGTTGCGCTCGACCAGGGCACCACCAGCTCCCGCGCGGTCGTAATGGATCACGATGCCAATATCATTAGCGTGTCGCAGCGCGAATTTGAGCAAATCTACCCAAAACCAGGTTGGGTAGAACACGACCCAATGGAAATCTGGGCCACCCAAAGCTCCACGCTGGTAGAAGTGCTGGCGAAAGCCGATATCAGTTCCGATCAAATTGCAGCTATCGGTATTACGAACCAGCGTGAAACCACTATTGTCTGGGAAAAAGAAACCGGCAAGCCTATCTATAACGCCATTGTCTGGCAGTGCCGTCGTACCGCAGAAATCTGCGAGCATTTAAAACGTGACGGTTTAGAAGATTATATCCGCAGCAATACCGGTCTGGTGATTGACCCGTACTTTTCTGGCACCAAAGTGAAGTGGATCCTCGACCATGTGGAAGGCTCTCGCGAGCGTGCACGTCGTGGTGAATTGCTGTTTGGTACGGTTGATACGTGGCTTATCTGGAAAATGACTCAGGGCCGTGTCCATGTGACCGATTACACCAACGCCTCTCGTACCATGTTGTTCAACATCCATACCCTGGACTGGGACGACAAAATGCTGGAAGTGCTGGATATTCCGCGCGAGATGCTGCCAGAAGTGCGTCGTTCTTCCGAAGTATACGGTCAGACTAACATTGGCGGCAAAGGCGGCACGCGTATTCCAATCTCCGGGATCGCCGGTGACCAGCAGGCCGCGCTGTTTGGTCAGTTGTGCGTGAAAGAAGGGATGGCGAAGAACACCTATGGCACTGGCTGCTTTATGCTGATGAACACTGGCGAGAAAGCGGTGAAATCAGAAAACGGCCTGCTGACCACCATCGCCTGCGGCCCGACTGGCGAAGTGAACTATGCGTTGGAAGGTGCGGTGTTTATGGCAGGCGCATCCA";


        //Initiate the chooser
        RBSChooser2 chooser = new RBSChooser2();
        chooser.initiate();

        //Make the first choice with an empty Set of ignores
        Set<RBSOption> ignores = new HashSet<>();
        RBSOption selected1 = chooser.run(cds, ignores);

        //Add the first selection to the list of things to ignore
        ignores.add(selected1);

        //Choose again with an ignore added
        RBSOption selected2 = chooser.run(cds, ignores);

        //Print out the two options, which should be different
        System.out.println("CDS starts with:");
        System.out.println(cds.substring(0, 18));
        System.out.println(translation.run(cds.substring(3, 21)));
        System.out.println();
        System.out.println("Selected1:\n");
        System.out.println(selected1.toString());
        System.out.println();
        System.out.println("Selected2:\n");
        System.out.println(selected2.toString());

    }

    public void test4() throws Exception {

        Translate translation = new Translate();
        translation.initiate();

        //Create an example
        String cds = "ATGACT";

        //Initiate the chooser
        RBSChooser2 chooser = new RBSChooser2();
        chooser.initiate();

        //Make the first choice with an empty Set of ignores
        Set<RBSOption> ignores = new HashSet<>();
        RBSOption selected1 = chooser.run(cds, ignores);
    }

    public static void main(String[] args) throws Exception {
        RBSChooser2Test testers = new RBSChooser2Test();
        System.out.println("Prof given test:\n");
        testers.test1();
        System.out.println("\n\nAAAA+ test:\n");
        testers.test2();
        System.out.println("\n\nglpK test:\n");
        testers.test3();
        System.out.println("\n\nfail test:");
        testers.test4();



    }
}
