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
        String ubicacion = "ssrc/soporte/base.txt";
        Path archivo = Paths.get(ubicacion);
        if (Files.notExists(archivo)) {
            try {
                Soporte.Alerta("creando el archivo");
                PrintWriter writer = new PrintWriter(ubicacion, "UTF-8");
                writer.println("--------UMG---------");
                for (int i = 1; i <= 3; i++) {
                    writer.println("PARQUEO" + i);
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
}
