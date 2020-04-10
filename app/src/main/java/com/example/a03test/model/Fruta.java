package com.example.a03test.model;

public class Fruta {

    private String nombre;
    private String descripcion;
    private int imagenFondo;
    private int imagenIcono;
    private int cantidad;

    public Fruta() {}

    public Fruta(String nombre, String descripcion, int imagenFondo, int imagenIcono, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenFondo = imagenFondo;
        this.imagenIcono = imagenIcono;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagenFondo() {
        return imagenFondo;
    }

    public void setImagenFondo(int imagenFondo) {
        this.imagenFondo = imagenFondo;
    }

    public int getImagenIcono() {
        return imagenIcono;
    }

    public void setImagenIcono(int imagenIcono) {
        this.imagenIcono = imagenIcono;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
