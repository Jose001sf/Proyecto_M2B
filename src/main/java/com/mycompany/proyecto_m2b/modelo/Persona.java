/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

import java.sql.Date;

/**
 *
 * @author castr
 */
public class Persona {
    private String ced_perso;
    private String nom1_person;
    private String nom2_person;
    private String apell1_person;
    private String apell2_person;
    private String num_celu_person;
    private String num_tel_person;
    private String gene_person;
    private Date fech_nac_perso;
    private String corr_elec_perso;
    private Date fech_registro_person;
    private String id_direccion;

    public String getCed_perso() {
        return ced_perso;
    }

    public void setCed_perso(String ced_perso) {
        this.ced_perso = ced_perso;
    }

    public String getNom1_person() {
        return nom1_person;
    }

    public void setNom1_person(String nom1_person) {
        this.nom1_person = nom1_person;
    }

    public String getNom2_person() {
        return nom2_person;
    }

    public void setNom2_person(String nom2_person) {
        this.nom2_person = nom2_person;
    }

    public String getApell1_person() {
        return apell1_person;
    }

    public void setApell1_person(String apell1_person) {
        this.apell1_person = apell1_person;
    }

    public String getApell2_person() {
        return apell2_person;
    }

    public void setApell2_person(String apell2_person) {
        this.apell2_person = apell2_person;
    }

    public String getNum_celu_person() {
        return num_celu_person;
    }

    public void setNum_celu_person(String num_celu_person) {
        this.num_celu_person = num_celu_person;
    }

    public String getNum_tel_person() {
        return num_tel_person;
    }

    public void setNum_tel_person(String num_tel_person) {
        this.num_tel_person = num_tel_person;
    }

    public String getGene_person() {
        return gene_person;
    }

    public void setGene_person(String gene_person) {
        this.gene_person = gene_person;
    }

    public Date getFech_nac_perso() {
        return fech_nac_perso;
    }

    public void setFech_nac_perso(Date fech_nac_perso) {
        this.fech_nac_perso = fech_nac_perso;
    }

    public String getCorr_elec_perso() {
        return corr_elec_perso;
    }

    public void setCorr_elec_perso(String corr_elec_perso) {
        this.corr_elec_perso = corr_elec_perso;
    }

    public Date getFech_registro_person() {
        return fech_registro_person;
    }

    public void setFech_registro_person(Date fech_registro_person) {
        this.fech_registro_person = fech_registro_person;
    }

    public String getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(String id_direccion) {
        this.id_direccion = id_direccion;
    }

    public Persona() {
    }

    public Persona(String ced_perso, String nom1_person, String nom2_person, String apell1_person, String apell2_person, String num_celu_person, String num_tel_person, String gene_person, Date fech_nac_perso, String corr_elec_perso, Date fech_registro_person, String id_direccion) {
        this.ced_perso = ced_perso;
        this.nom1_person = nom1_person;
        this.nom2_person = nom2_person;
        this.apell1_person = apell1_person;
        this.apell2_person = apell2_person;
        this.num_celu_person = num_celu_person;
        this.num_tel_person = num_tel_person;
        this.gene_person = gene_person;
        this.fech_nac_perso = fech_nac_perso;
        this.corr_elec_perso = corr_elec_perso;
        this.fech_registro_person = fech_registro_person;
        this.id_direccion = id_direccion;
    }

    @Override
    public String toString() {
        return "Persona{" + "ced_perso=" + ced_perso + ", nom1_person=" + nom1_person + ", nom2_person=" + nom2_person + ", apell1_person=" + apell1_person + ", apell2_person=" + apell2_person + ", num_celu_person=" + num_celu_person + ", num_tel_person=" + num_tel_person + ", gene_person=" + gene_person + ", fech_nac_perso=" + fech_nac_perso + ", corr_elec_perso=" + corr_elec_perso + ", fech_registro_person=" + fech_registro_person + ", id_direccion=" + id_direccion + '}';
    }
    
}
