
package com.mycompany.proyecto_m2b.modelo;

public class Proveedor {
    private String idProveedor;
    private String rucProveedor;
    private String nomEmpresa;
    private String numTelelEmpresa;
    private String idTipoProveedor;

    public Proveedor(String idProveedor, String rucProveedor, String nomEmpresa, String numTelelEmpresa, String idTipoProveedor) {
        this.idProveedor = idProveedor;
        this.rucProveedor = rucProveedor;
        this.nomEmpresa = nomEmpresa;
        this.numTelelEmpresa = numTelelEmpresa;
        this.idTipoProveedor = idTipoProveedor;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(String rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public String getNomEmpresa() {
        return nomEmpresa;
    }

    public void setNomEmpresa(String nomEmpresa) {
        this.nomEmpresa = nomEmpresa;
    }

    public String getNumTelelEmpresa() {
        return numTelelEmpresa;
    }

    public void setNumTelelEmpresa(String numTelelEmpresa) {
        this.numTelelEmpresa = numTelelEmpresa;
    }

    public String getIdTipoProveedor() {
        return idTipoProveedor;
    }

    public void setIdTipoProveedor(String idTipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
    }
        

    @Override
    public String toString() {
        return nomEmpresa; 
    }
}