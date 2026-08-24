/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author usuario
 */
public class Accesos {

    private String accesos;
    private String desc_persmisos;
    
    /**
     * @return the accesos
     */
    public String getAccesos() {
        return accesos;
    }

    /**
     * @param accesos the accesos to set
     */
    public void setAccesos(String accesos) {
        this.accesos = accesos;
    }

    /**
     * @return the desc_persmisos
     */
    public String getDesc_persmisos() {
        return desc_persmisos;
    }

    /**
     * @param desc_persmisos the desc_persmisos to set
     */
    public void setDesc_persmisos(String desc_persmisos) {
        this.desc_persmisos = desc_persmisos;
    }

    public Accesos() {
    }

    public Accesos(String accesos, String desc_persmisos) {
        this.accesos = accesos;
        this.desc_persmisos = desc_persmisos;
    }

    @Override
    public String toString() {
        return desc_persmisos;
    }
    
    
    
}
