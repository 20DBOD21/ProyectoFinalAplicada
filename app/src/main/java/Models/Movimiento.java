package Models;

import java.util.Date;

public class Movimiento {
    protected double monto;
    protected String descripcion;
    protected Date fecha;
    protected boolean esGasto;
    protected int idCategoria;

    public Movimiento() {
    }

    public Movimiento(double monto, String descripcion, Date fecha, boolean esGasto, int idCategoria) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.esGasto = esGasto;
        this.idCategoria = idCategoria;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isEsGasto() {
        return esGasto;
    }

    public void setEsGasto(boolean esGasto) {
        this.esGasto = esGasto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
