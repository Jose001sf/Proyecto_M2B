/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

import java.util.Date;

/**
 *
 * @author castr
 */
public class Persona {
    private String Ced_perso;
    private String Nom1_person;
    private String Nom2_person;
    private String Apell1_person;
    private String Apell2_person;
    private String Num_celu_person;
    private String Num_tel_person;
    private String Gene_person;
    private Date Fech_nac_perso;
    private String Corr_elec_perso;
    private Date Fech_registro_person;

    public Persona() {
    }

    public Persona(String Ced_perso, String Nom1_person, String Nom2_person, String Apell1_person, String Apell2_person, String Num_celu_person, String Num_tel_person, String Gene_person, Date Fech_nac_perso, String Corr_elec_perso, Date Fech_registro_person) {
        this.Ced_perso = Ced_perso;
        this.Nom1_person = Nom1_person;
        this.Nom2_person = Nom2_person;
        this.Apell1_person = Apell1_person;
        this.Apell2_person = Apell2_person;
        this.Num_celu_person = Num_celu_person;
        this.Num_tel_person = Num_tel_person;
        this.Gene_person = Gene_person;
        this.Fech_nac_perso = Fech_nac_perso;
        this.Corr_elec_perso = Corr_elec_perso;
        this.Fech_registro_person = Fech_registro_person;
    }

    public String getCed_perso() {
        return Ced_perso;
    }

    public void setCed_perso(String Ced_perso) {
        this.Ced_perso = Ced_perso;
    }

    public String getNom1_person() {
        return Nom1_person;
    }

    public void setNom1_person(String Nom1_person) {
        this.Nom1_person = Nom1_person;
    }

    public String getNom2_person() {
        return Nom2_person;
    }

    public void setNom2_person(String Nom2_person) {
        this.Nom2_person = Nom2_person;
    }

    public String getApell1_person() {
        return Apell1_person;
    }

    public void setApell1_person(String Apell1_person) {
        this.Apell1_person = Apell1_person;
    }

    public String getApell2_person() {
        return Apell2_person;
    }

    public void setApell2_person(String Apell2_person) {
        this.Apell2_person = Apell2_person;
    }

    public String getNum_celu_person() {
        return Num_celu_person;
    }

    public void setNum_celu_person(String Num_celu_person) {
        this.Num_celu_person = Num_celu_person;
    }

    public String getNum_tel_person() {
        return Num_tel_person;
    }

    public void setNum_tel_person(String Num_tel_person) {
        this.Num_tel_person = Num_tel_person;
    }

    public String getGene_person() {
        return Gene_person;
    }

    public void setGene_person(String Gene_person) {
        this.Gene_person = Gene_person;
    }

    public Date getFech_nac_perso() {
        return Fech_nac_perso;
    }

    public void setFech_nac_perso(Date Fech_nac_perso) {
        this.Fech_nac_perso = Fech_nac_perso;
    }

    public String getCorr_elec_perso() {
        return Corr_elec_perso;
    }

    public void setCorr_elec_perso(String Corr_elec_perso) {
        this.Corr_elec_perso = Corr_elec_perso;
    }

    public Date getFech_registro_person() {
        return Fech_registro_person;
    }

    public void setFech_registro_person(Date Fech_registro_person) {
        this.Fech_registro_person = Fech_registro_person;
    }

    @Override
    public String toString() {
        return "Persona{" + "Ced_perso=" + Ced_perso + ", Nom1_person=" + Nom1_person + ", Nom2_person=" + Nom2_person + ", Apell1_person=" + Apell1_person + ", Apell2_person=" + Apell2_person + ", Num_celu_person=" + Num_celu_person + ", Num_tel_person=" + Num_tel_person + ", Gene_person=" + Gene_person + ", Fech_nac_perso=" + Fech_nac_perso + ", Corr_elec_perso=" + Corr_elec_perso + ", Fech_registro_person=" + Fech_registro_person + '}';
    }
    
}
