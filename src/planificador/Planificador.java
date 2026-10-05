package planificador;

import java.util.ArrayList;
import java.util.List;

public abstract class Planificador {

    protected List<Proceso> procesos;
    protected List<Proceso> colaListos;
    protected Proceso procesoEnCPU;
    protected int tiempoActual;
    protected int cambiosDeContexto;
    protected String ultimoProcesoEjecutado;
    protected List<String> trazaGantt;

    public Planificador(List<Proceso> procesosEntrada) {
        this.procesos = new ArrayList<>();
        for (Proceso proceso : procesosEntrada) {
            this.procesos.add(proceso.copiar());
        }
        this.colaListos = new ArrayList<>();
        this.procesoEnCPU = null;
        this.tiempoActual = 0;
        this.cambiosDeContexto = 0;
        this.ultimoProcesoEjecutado = null;
        this.trazaGantt = new ArrayList<>();
    }

    public void ejecutar() {
        while (!esFinDeSimulacion()) {

            for (Proceso proceso : procesos) {
                if (proceso.getLlegada() == tiempoActual && proceso.getEstado() == EstadoProceso.NUEVO) {
                    proceso.setEstado(EstadoProceso.LISTO);
                    colaListos.add(proceso);
                }
            }


            verificarFinOQuantum();

            if (procesoEnCPU == null && !colaListos.isEmpty()) {
                procesoEnCPU = seleccionarSiguiente();
                colaListos.remove(procesoEnCPU);
                procesoEnCPU.setEstado(EstadoProceso.EJECUCION);

                if (!procesoEnCPU.haUsadoCPU(true)) {
                    procesoEnCPU.setTiempoPrimerUsoCPU(tiempoActual);
                    procesoEnCPU.haUsadoCPU(true);
                }

                if (ultimoProcesoEjecutado != null && !ultimoProcesoEjecutado.equals(procesoEnCPU.getNombre())) {
                    cambiosDeContexto++;
                }
                ultimoProcesoEjecutado = procesoEnCPU.getNombre();
            }

            if (procesoEnCPU != null) {
                trazaGantt.add(procesoEnCPU.getNombre());
                procesoEnCPU.setTiempoPrimerUsoCPU(procesoEnCPU.getTiempoRestante() - 1);
                ejecutarUnidadPropia();
            } else {
                trazaGantt.add("-");
            }

            tiempoActual++;
        }
    }
            protected abstract Proceso seleccionarSiguiente ();
            protected abstract void verificarFinOQuantum ();
            protected void ejecutarUnidadPropia () {
            }

            protected boolean esFinDeSimulacion () {
                for (Proceso p : procesos) {
                    if (p.getEstado() != EstadoProceso.TERMINADO) {
                        return false;
                    }
                }
                return true;
            }


            public List<Proceso> getProcesos () {
                return procesos;
            }
            public int getCambiosDeContexto () {
                return cambiosDeContexto;
            }
            public List<String> getTrazaGantt () {
                return trazaGantt;
            }
        }



