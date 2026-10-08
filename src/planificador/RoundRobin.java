package planificador;

import java.util.List;

public class RoundRobin extends Planificador {

    int quantum;
    int tiempoQuantum;

    public RoundRobin(List<Proceso> procesos, int quantum)
    {
        super(procesos);
        this.quantum=quantum;
        this.tiempoQuantum=0;
    }

    @Override
    protected Proceso seleccionarSiguiente() {
        tiempoQuantum = 0;
        return colaListos.get(0);
    }

    @Override
    protected void verificarFinOQuantum() {
        if (procesoEnCPU.getTiempoRestante()==0)
        {
            procesoEnCPU.setEstado(EstadoProceso.TERMINADO);
            procesoEnCPU.setInstanteFin(tiempoActual);
            procesoEnCPU=null;
            tiempoQuantum=0;
        } else if (tiempoQuantum==quantum) {
            procesoEnCPU.setEstado(EstadoProceso.LISTO);
            colaListos.add(procesoEnCPU);
            procesoEnCPU = null;
            tiempoQuantum =0;
        }


    }

    @Override
    protected void ejecutarUnidadPropia()
    {
        tiempoQuantum++;
    }

}
