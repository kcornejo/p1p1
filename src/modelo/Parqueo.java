package modelo;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import soporte.Soporte;

public class Parqueo {

    public int getCantidadDisponible(String parqueo) {
        int retorno = 0;
        return retorno;
    }

    static public Boolean generarArchivo() {
        //verificacion de arcihvo
        Boolean error = false;
        String ubicacion = "src/soporte/base.txt";
        Path archivo = Paths.get(ubicacion);
        if (Files.notExists(archivo)) {
            try {
                Soporte.Alerta("creando el archivo");
                PrintWriter writer = new PrintWriter(ubicacion, "UTF-8");
                writer.println("--------UMG---------");
                for (int i = 1; i <= 3; i++) {
                    int contador = Parqueo.getCantidadParqueo(i);
                    String texto = "|";
                    for (int c = 1; c <= contador; c++) {
                        texto = texto + c + "=0|";
                    }
                    writer.println("PARQUEO" + i + texto);
                }
                writer.close();
            } catch (Exception e) {
                Soporte.Alerta("Error de escritura en archivo base " + e.getMessage());
                error = true;
            }
        } else {
            Soporte.Alerta("archivo encontrado");
        }
        return error;
    }

    static int getCantidadParqueo(int i) {
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
