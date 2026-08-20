
package com.mycompany.proyecto_m2b.modelo;

public class Tipo_de_proveedor {
    private String ID_tipo_proveedor;
    private String Nom_tip_proveedor;
    private String Descrip_tipo_proveedor;

    public Tipo_de_proveedor(String ID_tipo_proveedor, String Nom_tip_proveedor, String Descrip_tipo_proveedor) {
        this.ID_tipo_proveedor = ID_tipo_proveedor;
        this.Nom_tip_proveedor = Nom_tip_proveedor;
        this.Descrip_tipo_proveedor = Descrip_tipo_proveedor;
    }

    public String getID_tipo_proveedor() {
        return ID_tipo_proveedor;
    }

    public void setID_tipo_proveedor(String ID_tipo_proveedor) {
        this.ID_tipo_proveedor = ID_tipo_proveedor;
    }

    public String getNom_tip_proveedor() {
        return Nom_tip_proveedor;
    }

    public void setNom_tip_proveedor(String Nom_tip_proveedor) {
        this.Nom_tip_proveedor = Nom_tip_proveedor;
    }

    public String getDescrip_tipo_proveedor() {
        return Descrip_tipo_proveedor;
    }

    public void setDescrip_tipo_proveedor(String Descrip_tipo_proveedor) {
        this.Descrip_tipo_proveedor = Descrip_tipo_proveedor;
    }

    @Override
    public String toString() {
        return "Tipo_de_proveedor{" + "ID_tipo_proveedor=" + ID_tipo_proveedor + ", Nom_tip_proveedor=" + Nom_tip_proveedor + ", Descrip_tipo_proveedor=" + Descrip_tipo_proveedor + '}';
    }
}
