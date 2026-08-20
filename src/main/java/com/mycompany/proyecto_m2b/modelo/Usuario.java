/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Usuario {
    private String ID_usuario;
    private String Nombre_usuario;
    private String contra_usuario;
    private boolean Estado_acti_usuario;
    private String id_empleado;
    private String tip_usuario;

    public Usuario() {
    }

    public Usuario(String ID_usuario, String Nombre_usuario, String contra_usuario, boolean Estado_acti_usuario, String id_empleado, String tip_usuario) {
        this.ID_usuario = ID_usuario;
        this.Nombre_usuario = Nombre_usuario;
        this.contra_usuario = contra_usuario;
        this.Estado_acti_usuario = Estado_acti_usuario;
        this.id_empleado = id_empleado;
        this.tip_usuario = tip_usuario;
    }

    public String getID_usuario() {
        return ID_usuario;
    }

    public void setID_usuario(String ID_usuario) {
        this.ID_usuario = ID_usuario;
    }

    public String getNombre_usuario() {
        return Nombre_usuario;
    }

    public void setNombre_usuario(String Nombre_usuario) {
        this.Nombre_usuario = Nombre_usuario;
    }

    public String getContra_usuario() {
        return contra_usuario;
    }

    public void setContra_usuario(String contra_usuario) {
        this.contra_usuario = contra_usuario;
    }

    public boolean isEstado_acti_usuario() {
        return Estado_acti_usuario;
    }

    public void setEstado_acti_usuario(boolean Estado_acti_usuario) {
        this.Estado_acti_usuario = Estado_acti_usuario;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getTip_usuario() {
        return tip_usuario;
    }

    public void setTip_usuario(String tip_usuario) {
        this.tip_usuario = tip_usuario;
    }

    @Override
    public String toString() {
        return "Usuario{" + "ID_usuario=" + ID_usuario + ", Nombre_usuario=" + Nombre_usuario + ", contra_usuario=" + contra_usuario + ", Estado_acti_usuario=" + Estado_acti_usuario + ", id_empleado=" + id_empleado + ", tip_usuario=" + tip_usuario + '}';
    }
    
}
