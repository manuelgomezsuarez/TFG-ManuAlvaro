package tfg_manualvaro.androidmotogp.models;
import java.util.List;

import tfg_manualvaro.androidmotogp.models.PosicionCampeonato;

public class CampeonatoModelo {

    private String id;
    private Integer temporada;
    private String categoria;
    private List<PosicionCampeonato> posiciones;


    @Override
    public String toString() {
        return "CampeonatoModelo{" +
                "id='" + id + '\'' +
                ", temporada=" + temporada +
                ", categoria='" + categoria + '\'' +
                ", posiciones=" + posiciones +
                '}';
    }

    public String getNombre(){
        return "Campeonato de la temporada "+temporada+" y categoria "+ categoria;
    }


    public CampeonatoModelo(String id, Integer temporada, String categoria, List<PosicionCampeonato> posiciones) {
        this.id = id;
        this.temporada = temporada;
        this.categoria = categoria;
        this.posiciones = posiciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<PosicionCampeonato> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<PosicionCampeonato> posiciones) {
        this.posiciones = posiciones;
    }



}
