package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import soporte.Soporte;

public class Parqueo {

    public static int getCantidadDisponible(int parqueo) throws FileNotFoundException {
        int retorno = 0;
        int max = Parqueo.getCantidadParqueo(parqueo);
        String ubicacion = "src/soporte/base.txt";
        Scanner in = new Scanner(new FileReader(ubicacion));
        String linea;
        while (in.hasNextLine()) {
            linea = in.nextLine();
            String[] linea_arreglo = linea.split("\\|");
            if (linea_arreglo[0].equals("NIVEL" + parqueo)) {
                String[] parqueo_posicion_arreglo;
                for (int i = 1; i <= max; i++) {
                    parqueo_posicion_arreglo = linea_arreglo[i].split("\\=");
                    if (parqueo_posicion_arreglo[1].equals("0")) {
                        retorno++;
                    }
                }
                break;
            }
        }
        return retorno;
    }

    public static void usarParqueo(int parqueo, int posicion) throws FileNotFoundException {
        int max = Parqueo.getCantidadParqueo(parqueo);
        String ubicacion = "src/soporte/base.txt";
        Scanner in = new Scanner(new FileReader(ubicacion));
        String linea;
        while (in.hasNextLine()) {
            linea = in.nextLine();
            String[] linea_arreglo = linea.split("\\|");
            if (linea_arreglo[0].equals("NIVEL" + parqueo)) {
                String[] parqueo_posicion_arreglo;
                for (int i = 1; i <= max; i++) {
                    parqueo_posicion_arreglo = linea_arreglo[i].split("\\=");
                    if (parqueo_posicion_arreglo[1].equals("0")) {
                        
                    } else {
                        Soporte.Alerta("Parqueo ya utilizado");
                    }
                }
                break;
            }
        }
    }

    public static Boolean generarArchivo() {
        //verificacion de arcihvo
        Boolean error = false;
        String ubicacion = "src/soporte/base.txt";
        Path archivo = Paths.get(ubicacion);
        if (Files.notExists(archivo)) {
            try {
                Soporte.Alerta("creando el archivo");
                PrintWriter writer = new PrintWriter(ubicacion, "UTF-8");
                writer.println("--------UMG---------");
                Date dNow = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String fecha = ft.format(dNow);
                for (int i = 1; i <= 3; i++) {
                    int contador = Parqueo.getCantidadParqueo(i);
                    String texto = "|";
                    for (int c = 1; c <= contador; c++) {
                        texto = texto + c + "=0=" + fecha + "|";
                    }
                    writer.println("NIVEL" + i + texto);
                }
                writer.close();
            } catch (Exception e) {
                Soporte.Alerta("Error de escritura en archivo base " + e.getMessage());
                error = true;
            }
        } else {
            Soporte.Alerta("Archivo Encontrado");
        }
        return error;
    }

    public static int getCantidadParqueo(int i) {
        int contador = 0;
        switch (i) {
            case 1:
                contador = 14;
                break;
            case 2:
                contador = 11;
                break;
            case 3:
                contador = 13;
                break;
        }
        return contador;
    }
}
