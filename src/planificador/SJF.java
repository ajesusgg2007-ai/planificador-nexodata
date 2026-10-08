package planificador;

import java.util.List;

public class SJF extends Planificador {

    public SJF(List<Proceso> procesos) {
        super(procesos);
    }

    @Override
    protected Proceso seleccionarSiguiente() {

        Proceso corto = colaListos.get(0);

        for (Proceso p : colaListos) {
            if (p.getRafaga() < corto.getRafaga()) {
                corto = p;
            }
        }

        return corto;
    }

    @Override
    protected void verificarFinOQuantum() {
        if (procesoEnCPU != null && procesoEnCPU.getTiempoRestante() == 0) {
            procesoEnCPU.setEstado(EstadoProceso.TERMINADO);
            procesoEnCPU.setInstanteFin(tiempoActual);
            procesoEnCPU = null;
        }
    }

}
