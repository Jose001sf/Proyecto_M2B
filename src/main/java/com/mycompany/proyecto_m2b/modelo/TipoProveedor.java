
package com.mycompany.proyecto_m2b.modelo;

public class TipoProveedor {
    private String idTipoProveedor;
    private String nomTipProveedor;
    private String descripTipoProveedor;

    public TipoProveedor(String idTipoProveedor, String nomTipProveedor, String descripTipoProveedor) {
        this.idTipoProveedor = idTipoProveedor;
        this.nomTipProveedor = nomTipProveedor;
        this.descripTipoProveedor = descripTipoProveedor;
    }

    public String getIdTipoProveedor(){
        return idTipoProveedor; 
    }
    
    public String getNomTipProveedor(){
        return nomTipProveedor; 
    }

    public String getDescripTipoProveedor() {
        return descripTipoProveedor;
    }

    public void setDescripTipoProveedor(String descripTipoProveedor) {
        this.descripTipoProveedor = descripTipoProveedor;
    }
    
    

    @Override
    public String toString() {
        return nomTipProveedor; 
    }
}