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

    private String id_accesos;
    private String accesos;
    private String desc_persmisos;
    private String id_tip_usuario;
    private boolean estado_acti_acceso;

    public boolean isEstado_acti_acceso() {
        return estado_acti_acceso;
    }

    public void setEstado_acti_acceso(boolean estado_acti_acceso) {
        this.estado_acti_acceso = estado_acti_acceso;
    }
    

    public String getId_tip_usuario() {
        return id_tip_usuario;
    }

    public void setId_tip_usuario(String id_tip_usuario) {
        this.id_tip_usuario = id_tip_usuario;
    }

    public String getId_accesos() {
        return id_accesos;
    }

    public void setId_accesos(String id_accesos) {
        this.id_accesos = id_accesos;
    }
    
    
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

    public Accesos(String accesos, String desc_persmisos, String id_tip_usuario) {
        this.accesos = accesos;
        this.desc_persmisos = desc_persmisos;
        this.id_tip_usuario = id_tip_usuario;
    }

    public Accesos(String id_accesos, String accesos, String desc_persmisos, String id_tip_usuario) {
        this.id_accesos = id_accesos;
        this.accesos = accesos;
        this.desc_persmisos = desc_persmisos;
        this.id_tip_usuario = id_tip_usuario;
    }

    public Accesos(String id_accesos, String accesos, String desc_persmisos, String id_tip_usuario, boolean estado_acti_acceso) {
        this.id_accesos = id_accesos;
        this.accesos = accesos;
        this.desc_persmisos = desc_persmisos;
        this.id_tip_usuario = id_tip_usuario;
        this.estado_acti_acceso = estado_acti_acceso;
    }

    @Override
    public String toString() {
        return accesos+"-"+desc_persmisos;
    }
    
    
    
}
