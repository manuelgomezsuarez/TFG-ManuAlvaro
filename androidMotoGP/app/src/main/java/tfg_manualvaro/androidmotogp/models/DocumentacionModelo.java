package tfg_manualvaro.androidmotogp.models;

import java.util.List;

public class DocumentacionModelo {
    private String temporada;
    private String categoria;
    private String abreviatura;
    private String titulo;
    private String lugar;
    private String fecha;
    private List<Url> urls;

    @Override
    public String toString() {
        return "DocumentacionModelo{" +
                ", temporada=" + temporada +
                ", categoria='" + categoria + '\'' +
                ", titulo='" + titulo + '\'' +
                ", lugar='" + lugar + '\'' +
                ", fecha='" + fecha + '\'' +
                ", urls=" + urls +
                '}';
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

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }


}
