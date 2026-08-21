
package com.mycompany.proyecto_m2b.modelo;

public class Empresa_recicladora {
    private String ID_empresa_rec;
    private String Nom_empresa_rec;
    private String ID_direccion_empresa_recicladora;
    private String Telf_empresa_rec;
    private String ID_tipo_emp;
    private String descTipoEmp;
    private String direccionCompleta;

    public Empresa_recicladora(String ID_empresa_rec, String Nom_empresa_rec, String ID_direccion_empresa_recicladora, String Telf_empresa_rec, String ID_tipo_emp) {
        this.ID_empresa_rec = ID_empresa_rec;
        this.Nom_empresa_rec = Nom_empresa_rec;
        this.ID_direccion_empresa_recicladora = ID_direccion_empresa_recicladora;
        this.Telf_empresa_rec = Telf_empresa_rec;
        this.ID_tipo_emp = ID_tipo_emp;
    }

    public Empresa_recicladora(String ID_empresa_rec, String Nom_empresa_rec, String descTipoEmp, String direccionCompleta) {
        this.ID_empresa_rec = ID_empresa_rec;
        this.Nom_empresa_rec = Nom_empresa_rec;
        this.descTipoEmp = descTipoEmp;
        this.direccionCompleta = direccionCompleta;
    }

    public String getID_empresa_rec() {
        return ID_empresa_rec;
    }

    public void setID_empresa_rec(String ID_empresa_rec) {
        this.ID_empresa_rec = ID_empresa_rec;
    }

    public String getNom_empresa_rec() {
        return Nom_empresa_rec;
    }

    public void setNom_empresa_rec(String Nom_empresa_rec) {
        this.Nom_empresa_rec = Nom_empresa_rec;
    }

    public String getID_direccion_empresa_recicladora() {
        return ID_direccion_empresa_recicladora;
    }

    public void setID_direccion_empresa_recicladora(String ID_direccion_empresa_recicladora) {
        this.ID_direccion_empresa_recicladora = ID_direccion_empresa_recicladora;
    }

    public String getTelf_empresa_rec() {
        return Telf_empresa_rec;
    }

    public void setTelf_empresa_rec(String Telf_empresa_rec) {
        this.Telf_empresa_rec = Telf_empresa_rec;
    }

    public String getID_tipo_emp() {
        return ID_tipo_emp;
    }

    public void setID_tipo_emp(String ID_tipo_emp) {
        this.ID_tipo_emp = ID_tipo_emp;
    }

    public String getDescTipoEmp() {
        return descTipoEmp;
    }

    public void setDescTipoEmp(String descTipoEmp) {
        this.descTipoEmp = descTipoEmp;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public void setDireccionCompleta(String direccionCompleta) {
        this.direccionCompleta = direccionCompleta;
    }

    @Override
    public String toString() {
        return Nom_empresa_rec;
    }
}