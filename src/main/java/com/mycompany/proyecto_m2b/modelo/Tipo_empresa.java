/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto_m2b.modelo;

/**
 *
 * @author castr
 */
public class Tipo_empresa {
    private String ID_tipo_emp;
    private String Desc_emp;

    public Tipo_empresa() {
    }

    public Tipo_empresa(String ID_tipo_emp, String Desc_emp) {
        this.ID_tipo_emp = ID_tipo_emp;
        this.Desc_emp = Desc_emp;
    }

    public String getID_tipo_emp() {
        return ID_tipo_emp;
    }

    public void setID_tipo_emp(String ID_tipo_emp) {
        this.ID_tipo_emp = ID_tipo_emp;
    }

    public String getDesc_emp() {
        return Desc_emp;
    }

    public void setDesc_emp(String Desc_emp) {
        this.Desc_emp = Desc_emp;
    }

    @Override
    public String toString() {
        return "Tipo_empresa{" + "ID_tipo_emp=" + ID_tipo_emp + ", Desc_emp=" + Desc_emp + '}';
    }
    
}
