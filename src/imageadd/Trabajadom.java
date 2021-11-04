/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageadd;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author djvatio
 */
public class Trabajadom {

    Document documento;
    File f;

    public Trabajadom(String archivo) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            f = new File(archivo);
            //Se carga el archivo 
            documento = builder.parse(f);
            Frmanadeimagen ventanaimagen = new Frmanadeimagen();
            ventanaimagen.show();
        } catch (ParserConfigurationException ex) {
            JOptionPane.showMessageDialog(null, "Error XML", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(Trabajadom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            JOptionPane.showMessageDialog(null, "Error XML", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(Trabajadom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error XML", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(Trabajadom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Juego nodetojuego(Node eljuego) {
        NodeList datos;
        String emisor, texto, receptor;
        emisor = "";
        receptor = "";
        texto = "";
        LocalDateTime fecha;
        Juego nuevojuego = new Juego();
        datos = eljuego.getChildNodes();
        //Se recorren los nodos
        for (int cont = 0; cont < datos.getLength(); cont++) {
            switch (datos.item(cont).getNodeName()) {
                case "name":
                    //Nodo emisor - Se recoge el valor del emisor
                    nuevojuego.setNombre(datos.item(cont).getTextContent());
                    break;
                case "path":
                    //Nodo receptor - Se recoge el valor del receptor
                    nuevojuego.setRom(datos.item(cont).getTextContent());
                    break;
            }
        }
        //Se crea un nuevo objeto mensaje con los valores leidos

        //Se devuelve el mensaje
        return nuevojuego;
    }

    /**
     * Obtiene todos los mensajes y los devuelve como mensajes de la clase
     * mensaje
     *
     * @return Lista de mensajes
     */
    public void anadejuego(String rutarom, String nombre, String rutaimagen) {

    }

    public void modificajuego(String rutarom, String nombre, String rutaimagen) {
        System.out.println(rutarom);
        ArrayList<Juego> juegos;
        //Se obtienen los nodos hijos del primer nodo, los mensajes
        NodeList resultado = documento.getFirstChild().getChildNodes();

        boolean sal = false;
        boolean encontrado = false;
        boolean saldos = false;
        boolean encontradodos = false;
        int contdos = 0;
        NodeList datos;
        int cont = 0;
        while (!sal) {
            if (cont < resultado.getLength()) {
                if (resultado.item(cont).getNodeType() == Node.ELEMENT_NODE) {
                    datos = resultado.item(cont).getChildNodes();
                    saldos = false;
                    encontradodos = false;
                    contdos = 0;
                    while (!saldos) {
                        if (contdos < datos.getLength()) {
                            if (!encontrado) {
                                if (datos.item(contdos).getNodeName().equals("path")) {
                                    encontradodos = true;
                                   
                                    if (datos.item(contdos).getTextContent().equals(rutarom)) {
                                        encontrado = true;
                                        contdos++;
                                    } else {
                                        cont++;
                                        saldos=true;
                                    }
                                } else {
                                    contdos++;
                                }
                            } else {
                                if (datos.item(contdos).getNodeName().equals("name")) {
                                    datos.item(contdos).setTextContent(nombre);
                                    contdos++;
                                }
                                else if (datos.item(contdos).getNodeName().equals("image")) {
                                    datos.item(contdos).setTextContent(rutaimagen);
                                    sal=true;
                                    saldos=true;
                                    System.out.println("cambiada imagen");
                                } 
                                else
                                {
                                    contdos++;
                                }
                            }

                        } else {
                            saldos = true;
                        }
                    }
                } else {
                    cont++;
                }
            } else {
                sal = true;
            }
        }
        grabafichero();
        System.out.println("Fichero grabado");
    }

    public ArrayList<Juego> gettodosjuegos() {
        ArrayList<Juego> juegos;
        //Se obtienen los nodos hijos del primer nodo, los mensajes
        NodeList resultado = documento.getFirstChild().getChildNodes();
        juegos = new ArrayList<>();
        //Se recorren los nodos
        for (int i = 1; i < resultado.getLength(); i++) {
            //Si el nodo es del tipo elemento
            if (resultado.item(i).getNodeType() == Node.ELEMENT_NODE) {
                //Se añade el nodo transformado a mensaje en la lista de mensajes
                juegos.add(nodetojuego(resultado.item(i)));
            }
        }
        //Se devuelve la lista de mensajes
        return juegos;
    }

    public void grabafichero() {
        try {     
            OutputFormat format = new OutputFormat(documento);    
            format.setIndenting(true);    
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(f), format);    
            serializer.serialize(documento);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trabajadom.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Trabajadom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
