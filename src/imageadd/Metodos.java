/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageadd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author djvatio
 */
public class Metodos {

    private static Metodos metodos;
    private String rutaxml;
    private String pathxml;
    
    public String getpathxml()
    {
        return pathxml;
    }
    
    public void setpathxml(String ruta)
    {
        pathxml = ruta;
    }
    public static String obtennombrearchivo(String origen) {
        String nombre = "";
        boolean pasadobarra = false;
        boolean sal = false;
        int cont=origen.length()-1;
        while(!sal)
        {
            if(cont>=0)
            {
                if(origen.charAt(cont)=='\\')
                {
                    sal =true;
                }
                else
                {
                    nombre = origen.charAt(cont) + nombre;
                    cont--;
                }     
            }
            else
            {
                sal = true;
            }
            return nombre;   
        }
        return nombre;
    }
    private Trabajadom trabajadom;
    ArrayList<Juego> juegos = new ArrayList<>();

    private Metodos() {

    }
    
    
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }


    
     public void anadejuego(String rutarom, String nombre, String rutaimagen)
     {
         trabajadom.anadejuego(rutarom,nombre,rutaimagen);
     }
    
     public void modificajuego(String rutarom, String nombre, String rutaimagen)
     {
         trabajadom.modificajuego(rutarom,nombre,rutaimagen);
     }
     
    public void copiaimagen(String origen, String destino) throws IOException {
    File source = new File(origen);
    File dest = new File(destino);
    copyFileUsingStream(source,dest);
}

    public String cambiacontrabarra(String archivo)
    {     
        return archivo.replace("\\", "/");
    }
    
    private String quitanombre() {
        String path = "";
        boolean pasadobarra = false;
        for (int cont = rutaxml.length()-1; cont >= 0; cont--) {
            if (!pasadobarra) {
                if (rutaxml.charAt(cont) == '\\') {
                    pasadobarra = true;
                }
            } else {
                path = rutaxml.charAt(cont) + path;
            }
        }
        return path;
    }

    public String quitaextension(String juego) {
        String nombre = "";
        boolean pasadobarra = false;
        for (int cont = juego.length()-1; cont >= 0; cont--) {
            if (!pasadobarra) {
                if (juego.charAt(cont) == '.') {
                    pasadobarra = true;
                }
            } else {
                nombre = juego.charAt(cont) + nombre;
            }
        }
        return nombre;
    }
    
    
    public String obtenrelativa(String path) {
        String pathxml = quitanombre();
        System.out.println(pathxml);
        String salida;
        boolean sal = false;
        boolean encontrado = false;
        int cont = 0;
        while (!sal) {
            if (cont == pathxml.length()) {
                encontrado = true;
                sal = true;
            } else {
                if (pathxml.charAt(cont) == path.charAt(cont)) {
                    cont++;
                } else {
                    sal = true;
                }
            }
        }
        if(encontrado)
        {
            cont++;
            salida = "./";
            sal=false;
            while(!sal)
            {
                if(cont<path.length())
                {
                    salida = salida + path.charAt(cont);
                    cont++;
                }
                else
                {
                    sal = true;
                }
            }
            return salida;
        }
        else
        {
            return null;
        }
    }

    public Juego damejuego(String rom) {
        boolean encontrado = false;
        boolean sal = false;
        int cont = 0;

        while (!sal) {
            if (cont < juegos.size()) {
                if (juegos.get(cont).getRom().equals(rom)) {
                    encontrado = true;
                    sal = true;
                } else {
                    cont++;
                }
            } else {
                sal = true;
            }
        }
        if (encontrado) {
            return juegos.get(cont);
        } else {
            return null;
        }
    }

    public static Metodos getinstancia() {
        if (metodos == null) {
            metodos = new Metodos();
        }
        return metodos;
    }

    public void cargaxml(String ruta) {
        rutaxml = ruta;
        trabajadom = new Trabajadom(ruta);
        juegos = trabajadom.gettodosjuegos();

    }

}
