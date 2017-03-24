package controlador;

import java.awt.Color;
import java.io.FileNotFoundException;
import vista.Inicio;
import modelo.Parqueo;
import soporte.Soporte;

public class Principal {
    
    public static void main(String args[]) throws FileNotFoundException {
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
            int espacios_total;
            for (int i = 1; i <= 3; i++) {
                disponible = parqueo.getCantidadDisponible(i);
                espacios_total = parqueo.getCantidadParqueo(i);
                String label_parqueo = "Nivel " + i + " \n(" + disponible + "/" + espacios_total + ")";
                inicio.setLabelParqueo(label_parqueo, i);
                if (disponible == 0) {
                    inicio.setColorParqueo(Color.red, i);
                } else {
                    inicio.setColorParqueo(Color.green, i);
                }
            }
        }
    }
}
