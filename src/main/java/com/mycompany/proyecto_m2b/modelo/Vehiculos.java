
package com.mycompany.proyecto_m2b.modelo;

import java.util.*;

public class Vehiculos {
    private String ID_vehi;
    private Date Anio_sal_vehi;
    private String Num_chasis_vehi;
    private String Color_vehi;
    private String Cilindraje_vehi;
    private String Transmision_vehi;
    private int Num_puertas_vehi;
    private int Kilometraje_vehi;
    private String Num_motor_vehi;
    private String ID_propietario_vehi;
    private String ID_mode_vehi;
    private String Placa_carro;

    public Vehiculos(String ID_vehi, Date Anio_sal_vehi, String Num_chasis_vehi, String Color_vehi, String Cilindraje_vehi, String Transmision_vehi, int Num_puertas_vehi, int Kilometraje_vehi, String Num_motor_vehi, String ID_propietario_vehi, String ID_mode_vehi, String Placa_carro) {
        this.ID_vehi = ID_vehi;
        this.Anio_sal_vehi = Anio_sal_vehi;
        this.Num_chasis_vehi = Num_chasis_vehi;
        this.Color_vehi = Color_vehi;
        this.Cilindraje_vehi = Cilindraje_vehi;
        this.Transmision_vehi = Transmision_vehi;
        this.Num_puertas_vehi = Num_puertas_vehi;
        this.Kilometraje_vehi = Kilometraje_vehi;
        this.Num_motor_vehi = Num_motor_vehi;
        this.ID_propietario_vehi = ID_propietario_vehi;
        this.ID_mode_vehi = ID_mode_vehi;
        this.Placa_carro = Placa_carro;
    }

    public String getID_vehi() {
        return ID_vehi;
    }

    public void setID_vehi(String ID_vehi) {
        this.ID_vehi = ID_vehi;
    }

    public Date getAnio_sal_vehi() {
        return Anio_sal_vehi;
    }

    public void setAnio_sal_vehi(Date Anio_sal_vehi) {
        this.Anio_sal_vehi = Anio_sal_vehi;
    }

    public String getNum_chasis_vehi() {
        return Num_chasis_vehi;
    }

    public void setNum_chasis_vehi(String Num_chasis_vehi) {
        this.Num_chasis_vehi = Num_chasis_vehi;
    }

    public String getColor_vehi() {
        return Color_vehi;
    }

    public void setColor_vehi(String Color_vehi) {
        this.Color_vehi = Color_vehi;
    }

    public String getCilindraje_vehi() {
        return Cilindraje_vehi;
    }

    public void setCilindraje_vehi(String Cilindraje_vehi) {
        this.Cilindraje_vehi = Cilindraje_vehi;
    }

    public String getTransmision_vehi() {
        return Transmision_vehi;
    }

    public void setTransmision_vehi(String Transmision_vehi) {
        this.Transmision_vehi = Transmision_vehi;
    }

    public int getNum_puertas_vehi() {
        return Num_puertas_vehi;
    }

    public void setNum_puertas_vehi(int Num_puertas_vehi) {
        this.Num_puertas_vehi = Num_puertas_vehi;
    }

    public int getKilometraje_vehi() {
        return Kilometraje_vehi;
    }

    public void setKilometraje_vehi(int Kilometraje_vehi) {
        this.Kilometraje_vehi = Kilometraje_vehi;
    }

    public String getNum_motor_vehi() {
        return Num_motor_vehi;
    }

    public void setNum_motor_vehi(String Num_motor_vehi) {
        this.Num_motor_vehi = Num_motor_vehi;
    }

    public String getID_propietario_vehi() {
        return ID_propietario_vehi;
    }

    public void setID_propietario_vehi(String ID_propietario_vehi) {
        this.ID_propietario_vehi = ID_propietario_vehi;
    }

    public String getID_mode_vehi() {
        return ID_mode_vehi;
    }

    public void setID_mode_vehi(String ID_mode_vehi) {
        this.ID_mode_vehi = ID_mode_vehi;
    }

    public String getPlaca_carro() {
        return Placa_carro;
    }

    public void setPlaca_carro(String Placa_carro) {
        this.Placa_carro = Placa_carro;
    }

    @Override
    public String toString() {
        return "Vehiculos{" + "ID_vehi=" + ID_vehi + ", Anio_sal_vehi=" + Anio_sal_vehi + ", Num_chasis_vehi=" + Num_chasis_vehi + ", Color_vehi=" + Color_vehi + ", Cilindraje_vehi=" + Cilindraje_vehi + ", Transmision_vehi=" + Transmision_vehi + ", Num_puertas_vehi=" + Num_puertas_vehi + ", Kilometraje_vehi=" + Kilometraje_vehi + ", Num_motor_vehi=" + Num_motor_vehi + ", ID_propietario_vehi=" + ID_propietario_vehi + ", ID_mode_vehi=" + ID_mode_vehi + ", Placa_carro=" + Placa_carro + '}';
    }
}
