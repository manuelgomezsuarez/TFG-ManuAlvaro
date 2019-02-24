package tfg_manualvaro.androidmotogp;

class PosicionCampeonato {

    private String piloto;
    private String pais;
    private Integer puntos;
    private String moto;
    private Integer posicion;

    @Override
    public String toString() {
        return "PosicionCampeonato{" +
                "piloto='" + piloto + '\'' +
                ", pais='" + pais + '\'' +
                ", puntos=" + puntos +
                ", moto='" + moto + '\'' +
                ", posicion=" + posicion +
                '}';
    }

    public PosicionCampeonato(String piloto, String pais, Integer puntos, String moto, Integer posicion) {
        this.piloto = piloto;
        this.pais = pais;
        this.puntos = puntos;
        this.moto = moto;
        this.posicion = posicion;
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

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public String getMoto() {
        return moto;
    }

    public void setMoto(String moto) {
        this.moto = moto;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }


}
