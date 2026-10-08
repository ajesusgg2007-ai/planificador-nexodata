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
    protected void FinQuantum() {
        if (proceso != null && proceso.getTiempoRestante() == 0) {
            proceso.setEstado(EstadoProceso.TERMINADO);
            proceso.setInstanteFin(tiempoActual);
            proceso = null;
        }
    }

}
