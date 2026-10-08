package planificador;

import java.util.List;

public class FCFS extends Planificador{

    public FCFS(List<Proceso> procesos)
    {
        super(procesos);
    }

    @Override
    protected Proceso seleccionarSiguiente() {
        return colaListos.get(0);
    }

    @Override
    protected void FinQuantum() {
        if(proceso !=null && proceso.getTiempoRestante()==0)
        {
            proceso.setEstado(EstadoProceso.TERMINADO);
            proceso.setInstanteFin(tiempoActual);
            proceso = null;
        }

    }
}
