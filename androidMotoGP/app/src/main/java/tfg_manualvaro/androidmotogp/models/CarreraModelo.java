package tfg_manualvaro.androidmotogp.models;
import java.util.List;

public class CarreraModelo {

    private String id;
    private String temporada;
    private String categoria;
    private String lugar;
    private String titulo;
    private String fecha;
    private List<PosicionCarrera> posiciones;


    @Override
    public String toString() {
        return "CarreraModelo{" +
                "id='" + id + '\'' +
                ", temporada=" + temporada +
                ", categoria='" + categoria + '\'' +
                ", posiciones=" + posiciones +
                '}';
    }

    public String getNombre(){
        return "Gran Premio de "+titulo;
    }


    public CarreraModelo() {
        this.id = id;
        this.temporada = temporada;
        this.categoria = categoria;
        this.posiciones = posiciones;
        this.lugar=lugar;
        this.fecha=fecha;
        this.titulo=titulo;
        this.lugar=lugar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<PosicionCarrera> getPosiciones() {
        return posiciones;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setPosiciones(List<PosicionCarrera> posiciones) {
        this.posiciones = posiciones;
    }



}
