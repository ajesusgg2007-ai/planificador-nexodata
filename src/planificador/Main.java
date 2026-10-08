package planificador;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * PSP · Tema 2 · Simulador de planificación para NexoData
 *
 * Punto de entrada. Este fichero YA ESTÁ HECHO: solo lee y comprueba los argumentos.
 * No cambies cómo se leen: el profesor ejecutará tu programa siempre así:
 *
 *   java planificador.Main <fichero.csv> <fcfs|sjf|rr|todos> [quantum] [--traza]
 *
 * Todo lo demás (modelo del proceso, lectura del CSV, algoritmos, métricas,
 * informe por consola...) lo diseñas y programas tú en este mismo paquete.
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Uso: java planificador.Main <fichero.csv> <fcfs|sjf|rr|todos> [quantum] [--traza]");
            System.exit(1);
        }
        Path fichero = Path.of(args[0]);
        String algoritmo = args[1].toLowerCase();
        boolean traza = List.of(args).contains("--traza");
        int quantum = 2;
        if (args.length >= 3 && !args[2].startsWith("--")) {
            try {
                quantum = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.err.println("El quantum debe ser un número entero: " + args[2]);
                System.exit(1);
            }
        }
        if (!Files.exists(fichero)) {
            System.err.println("No encuentro el fichero " + fichero.toAbsolutePath()
                    + "\nComprueba el «Working directory» de la configuración de ejecución.");
            System.exit(1);
        }
        if (!List.of("fcfs", "sjf", "rr", "todos").contains(algoritmo)) {
            System.err.println("Algoritmo desconocido: " + algoritmo + " (usa fcfs, sjf, rr o todos)");
            System.exit(1);
        }

        System.out.println("Fichero: " + fichero + " | algoritmo: " + algoritmo
                + " | quantum: " + quantum + " | traza: " + traza);

        try {
            //1.Lectura del fichero
            List<Proceso> datos = LectorFicheros.cargarDesdeFichero(fichero.toString());

            //2/3.simulaciones

            switch (algoritmo) {
                case "fcfs":
ejecutarSimulacion(new FCFS(datos),"FCFS",traza);

break;

case "sjf":
    ejecutarSimulacion(new SJF(datos),"SJF",traza);

    break;
                case "Rounrobin":
                    ejecutarSimulacion(new RoundRobin(datos, quantum),"FCFS",traza);


                case "todos":
                    ejecutarSimulacion(new FCFS(datos), "FCFS", traza);
                    ejecutarSimulacion(new SJF(datos), "SJF", traza);
                    ejecutarSimulacion(new RoundRobin(datos, quantum), "Round Robin (q=" + quantum + ")", traza);
                    break;
            }




        } catch (IllegalArgumentException e) {
            System.err.println("Error en la lectura del fichero: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error al acceder al fichero: " + e.getMessage());
            System.exit(1);
        }


    }

    public static void ejecutarSimulacion(Planificador planificador, String nombreAlgoritmo, boolean mostrarTraza) {
        planificador.ejecutar();

        System.out.println("\n============================================================");
        System.out.println("ALGORITMO: " + nombreAlgoritmo);
        System.out.println("============================================================");

        // 2.Diagrama de Gantt
        System.out.print("Diagrama de Gantt: | ");
        for (String t : planificador.getTrazaGantt()) {
            System.out.print(t + " | ");
        }
        System.out.println();

        // 3. tabla de métricas/ medias
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

    }

}

