package it.itsrizzoli;


public class Hotel {
    String nome;
    double costo;
    boolean spa;


    public Hotel(String nome, double costo, boolean spa) {
        this.nome = nome;
        this.costo = costo;
        this.spa = spa;
    }

    public Double getCosto() {
        return costo;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getSpa() {
        String isSpa;
        if (spa) {
            isSpa = "with spa";
        } else {
            isSpa = "without spa";
        }
        return isSpa;
    }
}