package planificador;

public class Proceso {

        String nombre;
        int llegada;
        int rafaga;
        int tiempoRestante;
        EstadoProceso estado;
        int instanteFin;
        int tiempoPrimerUsoCPU;
        boolean haUsadoCPU;


    public Proceso(String nombre, int llegada, int rafaga) {
        this.nombre = nombre;
        this.llegada = llegada;
        this.rafaga = rafaga;
        this.tiempoRestante = rafaga;
        this.estado = EstadoProceso.NUEVO;
        this.instanteFin = -1;
        this.tiempoPrimerUsoCPU = -1;
        this.haUsadoCPU = false;
    }

    public String getNombre() {

        return nombre;
    }
    public int getLlegada() {

        return llegada;
    }
    public int getRafaga() {

        return rafaga;
    }
    public int getTiempoRestante() {

        return tiempoRestante;
    }

    public EstadoProceso getEstado() {

        return estado;
    }
    public void setEstado(EstadoProceso estado) {

        this.estado = estado;
    }

    public int getInstanteFin() {

        return instanteFin;
    }

    public boolean haUsadoCPU(boolean b) {

        return haUsadoCPU;
    }

    public int getTiempoRetorno() {

        return instanteFin - llegada;
    }
    public int getTiempoEspera() {

        return getTiempoRetorno() - rafaga;
    }
    public int getTiempoRespuesta() {

        return tiempoPrimerUsoCPU - llegada;
    }

    public void setTiempoPrimerUsoCPU(int tiempoPrimerUsoCPU){
        this.tiempoPrimerUsoCPU=tiempoPrimerUsoCPU;
    }

    public void setHaIniciado(boolean haUsadoCPU) {
        this.haUsadoCPU = haUsadoCPU;
    }

public Proceso copiar(){
        return new Proceso(this.nombre, this.llegada, this.rafaga);
}

}
