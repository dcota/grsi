package model;

import java.time.LocalDate;
import java.util.Date;

public class Cliente {
    //atributos
    private int numCliente;
    private String nome;
    private int nif;
    private String morada;
    private int codLoc;
    private int codRua;
    private String local;
    private LocalDate dataNasc;
    private String obs;
    //construtor

    public Cliente(int numCliente, String nome, int nif, String morada, int codLoc, int codRua, String local, LocalDate dataNasc, String obs) {
        this.numCliente = numCliente;
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.codLoc = codLoc;
        this.codRua = codRua;
        this.local = local;
        this.dataNasc = dataNasc;
        this.obs = obs;
    }

    public Cliente(int numCliente, String nome, int nif, String morada) {
        this.numCliente = numCliente;
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
    }

    //gets e sets
    public int getNumCliente() {
        return numCliente;
    }

    public void setNumCliente(int numCliente) {
        this.numCliente = numCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNif() {
        return nif;
    }

    public void setNif(int nif) {
        this.nif = nif;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public int getCodLoc() {
        return codLoc;
    }

    public void setCodLoc(int codLoc) {
        this.codLoc = codLoc;
    }

    public int getCodRua() {
        return codRua;
    }

    public void setCodRua(int codRua) {
        this.codRua = codRua;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

}
