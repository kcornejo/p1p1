package controlador;

import java.awt.Color;
import vista.Inicio;
import modelo.Parqueo;
import soporte.Soporte;

public class Principal {

    public static void main(String args[]) {
        Inicio inicio = new Inicio();
        Boolean error = Parqueo.generarArchivo();
        Parqueo parqueo = new Parqueo();
        if (error) {
            Soporte.Alerta("Por motivos de archivo se cierra la aplicacion, vuelve pronto");
            inicio.setVisible(false);
            inicio.dispose();
        } else {
            inicio.setVisible(true);
            int disponible;
            for (int i = 1; i <= 3; i++) {
                disponible = parqueo.getCantidadDisponible("PARQUEO" + i);
                if (disponible == 0) {
                    inicio.setColorParqueo(Color.red, i);
                    inicio.setBotonActivo(false, i);
                } else {
                    inicio.setColorParqueo(Color.green, i);
                    inicio.setBotonActivo(true, i);
                }
            }
        }
    }
}
