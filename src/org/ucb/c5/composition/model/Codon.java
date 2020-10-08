package org.ucb.c5.composition.model;

import java.util.ArrayList;

public class Codon {

    private String codon = new String();
    private char pLetter = 'A';
    private Double fraction = 0.0;
    private Double frequency = 0.0;
    private String number = new String();

    public Codon(String codon, char pLetter,
                    Double fraction, Double frequency, String number) {

        this.codon = codon;
        this.pLetter = pLetter;
        this.fraction = fraction;
        this.frequency = frequency;
        this.number = number;

    }

    public String getCodon() {
        return this.codon;
    }

    public char getpLetter() {
       return this.pLetter;
    }

    public Double getFraction() {
        return this.fraction;
    }

    public Double getFrequency() {
        return this.frequency;
    }

    public String getNumber() {
        return this.number;
    }
}
