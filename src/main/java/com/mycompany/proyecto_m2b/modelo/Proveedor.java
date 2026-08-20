
package com.mycompany.proyecto_m2b.modelo;

public class Proveedor {
    private String ID_proveedor;
    private String RUC_proveedor;
    private String Nom_empresa;
    private String Num_telel_empresa;
    private String ID_tipo_proveedor;

    public Proveedor(String ID_proveedor, String RUC_proveedor, String Nom_empresa, String Num_telel_empresa, String ID_tipo_proveedor) {
        this.ID_proveedor = ID_proveedor;
        this.RUC_proveedor = RUC_proveedor;
        this.Nom_empresa = Nom_empresa;
        this.Num_telel_empresa = Num_telel_empresa;
        this.ID_tipo_proveedor = ID_tipo_proveedor;
    }

    public String getID_proveedor() {
        return ID_proveedor;
    }

    public void setID_proveedor(String ID_proveedor) {
        this.ID_proveedor = ID_proveedor;
    }

    public String getRUC_proveedor() {
        return RUC_proveedor;
    }

    public void setRUC_proveedor(String RUC_proveedor) {
        this.RUC_proveedor = RUC_proveedor;
    }

    public String getNom_empresa() {
        return Nom_empresa;
    }

    public void setNom_empresa(String Nom_empresa) {
        this.Nom_empresa = Nom_empresa;
    }

    public String getNum_telel_empresa() {
        return Num_telel_empresa;
    }

    public void setNum_telel_empresa(String Num_telel_empresa) {
        this.Num_telel_empresa = Num_telel_empresa;
    }

    public String getID_tipo_proveedor() {
        return ID_tipo_proveedor;
    }

    public void setID_tipo_proveedor(String ID_tipo_proveedor) {
        this.ID_tipo_proveedor = ID_tipo_proveedor;
    }

    @Override
    public String toString() {
        return "Proveedor{" + "ID_proveedor=" + ID_proveedor + ", RUC_proveedor=" + RUC_proveedor + ", Nom_empresa=" + Nom_empresa + ", Num_telel_empresa=" + Num_telel_empresa + ", ID_tipo_proveedor=" + ID_tipo_proveedor + '}';
    }
}
