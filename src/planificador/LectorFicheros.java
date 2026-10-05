package planificador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectorFicheros {

    public static List<Proceso> cargarDesdeFichero(String rutaFichero) throws IllegalArgumentException, IOException {
    public static List<Proceso> CSV(String rutaFichero) {

            List<Proceso> resultado= new ArrayList<>();
 try(BufferedReader br = new BufferedReader(new FileReader(rutaFichero)) ) {
     String linea;
    int numLinea=0;

     while ((linea = br.readLine()) != null) {
         numLinea++;
         linea = linea.trim();
     }

     if (linea.isEmpty() || linea.startsWith("#")) {
         continue;
     }
 }
