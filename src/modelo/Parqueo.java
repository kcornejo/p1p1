package modelo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
    
    public static void deshusarParqueo(int parqueo, int posicion) throws FileNotFoundException {
        String ubicacion = "src/soporte/base.txt";
        String ubicacion_soporte = "src/soporte/base_soporte.txt";
        Scanner in = new Scanner(new FileReader(ubicacion));
        String linea;
        try {
            PrintWriter writer = new PrintWriter(ubicacion_soporte, "UTF-8");
            String copiar;
            while (in.hasNextLine()) {
                linea = in.nextLine();
                copiar = linea;
                String[] linea_arreglo = linea.split("\\|");
                if (linea_arreglo[0].equals("NIVEL" + parqueo)) {
                    String[] posicion_en_archivo = linea_arreglo[posicion].split("\\=");
                    Date dNow = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String fecha = ft.format(dNow);
                    
                    if (posicion_en_archivo[1].equals("1")) {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                        Date date = format.parse(posicion_en_archivo[2]);
                        double horas = (double)Parqueo.horasDiferencia(date, dNow);
                        Soporte.Alerta("Estuviste por " + String.valueOf(horas) + " horas");
                        linea_arreglo[posicion] = posicion + "=0=" + fecha;
                    } else {
                        Soporte.Alerta("Parqueo no utilizado");
                    }
                    copiar = String.join("|", linea_arreglo);
                }
                writer.println(copiar);
            }
            writer.close();
            in.close();
            Scanner in2 = new Scanner(new FileReader(ubicacion_soporte));
            PrintWriter writer2 = new PrintWriter(ubicacion, "UTF-8");
            while (in2.hasNextLine()) {
                String linea_soporte = in2.nextLine();
                writer2.println(linea_soporte);
            }
            in2.close();
            writer2.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static double horasDiferencia(Date date1, Date date2) {
        long duration = date2.getTime() - date1.getTime();
        long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
        double horas = (double) diffInHours;
        
        int diferencia;
        System.out.println(date1.getMinutes());
        System.out.println(date2.getMinutes());
        if (date1.getMinutes()<= date2.getMinutes()) {
            diferencia = date2.getMinutes() - date1.getMinutes();
        } else {
            diferencia = date1.getMinutes() + 60 - date2.getMinutes();
        }
        if (diferencia > 0 && diferencia <= 30) {
            horas = horas + 0.5;
        } else {
            horas = horas + 1;
        }
        return horas;
//        final int MILLI_TO_HOUR = 1000 * 60 * 60;
//        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }
    
    public static void usarParqueo(int parqueo, int posicion) throws FileNotFoundException {
        String ubicacion = "src/soporte/base.txt";
        String ubicacion_soporte = "src/soporte/base_soporte.txt";
        Scanner in = new Scanner(new FileReader(ubicacion));
        String linea;
        try {
            PrintWriter writer = new PrintWriter(ubicacion_soporte, "UTF-8");
            String copiar;
            while (in.hasNextLine()) {
                linea = in.nextLine();
                copiar = linea;
                String[] linea_arreglo = linea.split("\\|");
                if (linea_arreglo[0].equals("NIVEL" + parqueo)) {
                    String[] posicion_en_archivo = linea_arreglo[posicion].split("\\=");
                    Date dNow = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String fecha = ft.format(dNow);
                    if (posicion_en_archivo[1].equals("0")) {
                        linea_arreglo[posicion] = posicion + "=1=" + fecha;
                    } else {
                        Soporte.Alerta("Parqueo ya utilizado");
                    }
                    copiar = String.join("|", linea_arreglo);
                }
                writer.println(copiar);
            }
            
            writer.close();
            in.close();
            Scanner in2 = new Scanner(new FileReader(ubicacion_soporte));
            PrintWriter writer2 = new PrintWriter(ubicacion, "UTF-8");
            while (in2.hasNextLine()) {
                String linea_soporte = in2.nextLine();
                writer2.println(linea_soporte);
            }
            in2.close();
            writer2.close();
        } catch (Exception e) {
            
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
