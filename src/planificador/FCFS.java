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
    protected void verificarFinOQuantum() {
        if(procesoEnCPU !=null && procesoEnCPU.getTiempoRestante()==0)
        {
            procesoEnCPU.setEstado(EstadoProceso.TERMINADO);
            procesoEnCPU.setInstanteFin(tiempoActual);
            procesoEnCPU = null;
        }

    }
}
