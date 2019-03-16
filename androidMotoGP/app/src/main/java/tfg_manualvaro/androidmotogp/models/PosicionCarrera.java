package tfg_manualvaro.androidmotogp.models;

public class PosicionCarrera {

    private String piloto;
    private String pais;
    private String moto;
    private Integer puntos;
    private Integer posicion;
    private Integer numero;
    private String equipo;
    private Integer kmh;
    private String diferencia;



    @Override
    public String toString() {
        return "PosicionCarrera{" +
                "piloto='" + piloto + '\'' +
                ", pais='" + pais + '\'' +
                ", moto='" + moto + '\'' +
                ", posicion=" + posicion +
                '}';
    }


    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getMoto() {
        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public Integer getKmh() {
        return kmh;
    }

    public void setKmh(Integer kmh) {
        this.kmh = kmh;
    }


    public String getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(String diferencia) {
        this.diferencia = diferencia;
    }
}
