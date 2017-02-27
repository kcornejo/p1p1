package controlador;

import vista.Inicio;
import modelo.Parqueo;
import soporte.Soporte;

public class Principal {

    public static void main(String args[]) {
        Inicio inicio = new Inicio();
        Boolean error = Parqueo.generarArchivo();
        if (error) {
            Soporte.Alerta("Por motivos se cierra la aplicacion, vuelve pronto");
            inicio.setVisible(false);
            inicio.dispose();
        } else {
            inicio.setVisible(true);
        }
    }
}
