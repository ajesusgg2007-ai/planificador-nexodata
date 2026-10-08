package planificador;

import java.util.List;

public class RoundRobin extends Planificador {

    int quantum;
    int tiempoQuantum;

    public RoundRobin(List<Proceso> procesos, int quantum)
    {
        super(procesos);
        this.quantum= this.quantum;
        this.tiempoQuantum=0;
    }

    @Override
    protected Proceso seleccionarSiguiente() {
        tiempoQuantum = 0;
        return colaListos.get(0);
    }

    @Override
    protected void FinQuantum() {
        if (proceso.getTiempoRestante()==0)
        {
            proceso.setEstado(EstadoProceso.TERMINADO);
            proceso.setInstanteFin(tiempoActual);
            proceso =null;
            tiempoQuantum=0;
        } else if (tiempoQuantum==quantum) {
            proceso.setEstado(EstadoProceso.LISTO);
            colaListos.add(proceso);
            proceso = null;
            tiempoQuantum =0;
        }


    }

    @Override
    protected void ejecutarUnidadPropia()
    {
        tiempoQuantum++;
    }

}
