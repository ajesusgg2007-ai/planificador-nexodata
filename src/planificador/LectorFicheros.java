package planificador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LectorFicheros {

    public static List<Proceso> cargarDesdeFichero(String rutaFichero) throws IllegalArgumentException, IOException {
        List<Proceso> resultado = new ArrayList<>();

        List<String> lineas = Files.readAllLines(Paths.get(rutaFichero));
        int numLinea = 0;

        for (String LineaPrinciopal : lineas) {
            numLinea++;
            String linea = LineaPrinciopal.trim();

            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            if (linea.toLowerCase().startsWith("proceso") || linea.toLowerCase().startsWith("nombre")) {
                continue;
            }

            String[] partes = linea.split("[,;\\s]+");
            if (partes.length < 3) {
                throw new IllegalArgumentException("Línea " + numLinea + " mal formada: Debe contener al menos Nombre, Llegada y Ráfaga. Contenido: '" + linea + "'");
            }

            String nombre = partes[0].trim();
            int llegada;
            int rafaga;
            try {
                llegada = Integer.parseInt(partes[1].trim());
                rafaga = Integer.parseInt(partes[2].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Línea " + numLinea + " mal formada: La llegada y la ráfaga deben ser números enteros. Contenido: '" + linea + "'");
            }

            if (llegada < 0) {
                throw new IllegalArgumentException("Error en línea " + numLinea + ": El tiempo de llegada no puede ser negativo (" + llegada + ") para el proceso '" + nombre + "'.");
            }

            if (rafaga <= 0) {
                throw new IllegalArgumentException("Error en línea " + numLinea + ": La ráfaga debe ser un número entero mayor que cero (" + rafaga + ") para el proceso '" + nombre + "'.");
            }

            resultado.add(new Proceso(nombre, llegada, rafaga));

        }

        return resultado;
    }

}