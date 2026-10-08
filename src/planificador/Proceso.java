package planificador;


public class Proceso {

        String nombre;
        int llegada;
        int rafaga;
        int tiempoRestante;
        EstadoProceso estado;
        int instanteFin;
        int tiempoPrimero;
        boolean UsadoCPU;




        //Constructores
    public Proceso(String nombre, int llegada, int rafaga) {
        this.nombre = nombre;
        this.llegada = llegada;
        this.rafaga = rafaga;
        this.tiempoRestante = rafaga;
        this.estado = EstadoProceso.NUEVO;
        this.instanteFin = -1;
        this.tiempoPrimero = -1;
        this.UsadoCPU = false;

    }

    //getters



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


    public int getInstanteFin() {


        return instanteFin;
    }

    public boolean haUsadoCPU(boolean b) {

        return UsadoCPU;
    }

    public int getTiempovuelta() {

        return instanteFin - llegada;
    }
    public int getTiempoEspera() {

        return getTiempovuelta() - rafaga;
    }
    public int getTiempoRespuesta() {

        return tiempoPrimero - llegada;
    }



    //seters
    public void setTiempoPrimerUsoCPU(int tiempoPrimerCPU){
        this.tiempoPrimero=tiempoPrimerCPU;
    }


    public void setInstanteFin(int momentoFin) {
        this.instanteFin = momentoFin;
    }

    public void setHaIniciado(boolean UsadoCPU) {
        this.UsadoCPU = UsadoCPU;
    }


    public void setEstado(EstadoProceso estado) {

        this.estado = estado;
    }



    //Metodo

public Proceso copiar(){
        return new Proceso(this.nombre, this.llegada, this.rafaga);
}

}
