package planificador;

import java.util.ArrayList;
import java.util.List;

public abstract class Planificador {

    protected List<Proceso> procesos;
    protected List<Proceso> colaListos;
    protected Proceso proceso;
    protected int tiempoActual;
    protected int cambioContexto;
    protected String ultimoEjecutado;
    protected List<String> Gantt;

    //constructor

    public Planificador(List<Proceso> procesosEntrada) {
        this.procesos = new ArrayList<>();
        for (Proceso proceso : procesosEntrada) {
            this.procesos.add(proceso.copiar());
        }
        this.colaListos = new ArrayList<>();
        this.proceso = null;
        this.tiempoActual = 0;
        this.cambioContexto = 0;
        this.ultimoEjecutado = null;
        this.Gantt = new ArrayList<>();
    }

    //metodos

    public void ejecutar() {
        while (!FinSimulacion()) {

            for (Proceso proceso : procesos) {
                if (proceso.getLlegada() == tiempoActual && proceso.getEstado() == EstadoProceso.NUEVO) {
                    proceso.setEstado(EstadoProceso.LISTO);
                    colaListos.add(proceso);
                }
            }


            FinQuantum();

            if (proceso == null && !colaListos.isEmpty()) {
                proceso = seleccionarSiguiente();
                colaListos.remove(proceso);
                proceso.setEstado(EstadoProceso.EJECUCION);

                if (!proceso.haUsadoCPU(true)) {
                    proceso.setTiempoPrimerUsoCPU(tiempoActual);
                    proceso.haUsadoCPU(true);
                }

                if (ultimoEjecutado != null && !ultimoEjecutado.equals(proceso.getNombre())) {
                    cambioContexto++;
                }
                ultimoEjecutado = proceso.getNombre();
            }

            if (proceso != null) {
                Gantt.add(proceso.getNombre());
                proceso.setTiempoPrimerUsoCPU(proceso.getTiempoRestante() - 1);
                ejecutarUnidadPropia();
            } else {
                Gantt.add("-");
            }

            tiempoActual++;
        }
    }
            protected abstract Proceso seleccionarSiguiente ();
            protected abstract void FinQuantum ();
            protected void ejecutarUnidadPropia () {
            }

             protected boolean FinSimulacion() {
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
            public int getCambioContexto() {
                return cambioContexto;
            }
            public List<String> getTrazaGantt () {
                return Gantt;
            }
    private static void ejecutarSimulacion(Planificador planificador, String nombreAlgoritmo, boolean mostrarTraza) {
        // 1. Ejecuta la simulación
        planificador.ejecutar();

        System.out.println("\n============================================================");
        System.out.println("ALGORITMO: " + nombreAlgoritmo);
        System.out.println("============================================================");

        // 2. Muestra el Diagrama de Gantt
        System.out.print("Diagrama de Gantt: | ");
        for (String t : planificador.getTrazaGantt()) {
            System.out.print(t + " | ");
        }
        System.out.println();

        // 3. Imprime la tabla de métricas y calcula las medias
        List<Proceso> lista = planificador.getProcesos();
        System.out.println("\nProceso | Llegada | Ráfaga | Fin | Retorno | Espera | Respuesta");
        System.out.println("------------------------------------------------------------");

        double sumaRetorno = 0, sumaEspera = 0, sumaRespuesta = 0;

        for (Proceso p : lista) {
            sumaRetorno += p.getTiempovuelta();
            sumaEspera += p.getTiempoEspera();
            sumaRespuesta += p.getTiempoRespuesta();

            System.out.printf("%-7s | %-7d | %-6d | %-3d | %-7d | %-6d | %-9d%n",
                    p.getNombre(), p.getTiempovuelta(), p.getRafaga(),
                    p.getInstanteFin(), p.getTiempovuelta(), p.getTiempoEspera(), p.getTiempoRespuesta());
        }

        int n = lista.size();
        System.out.println("------------------------------------------------------------");
        System.out.printf("MEDIAS  |         |        |     | %-7.2f | %-6.2f | %-9.2f%n",
                (sumaRetorno / n), (sumaEspera / n), (sumaRespuesta / n));

        // 4. Muestra recuento de cambios de contexto
        System.out.println("\nCambios de contexto totales: " + planificador.getCambioContexto());
    }

}



