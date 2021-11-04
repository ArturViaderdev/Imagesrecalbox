/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageadd;

/**
 *
 * @author djvatio
 */
public class Juego {
    private String nombre;
    private String imagen;
    private String rom;

    public Juego() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public String getRom() {
        return rom;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setRom(String rom) {
        this.rom = rom;
    }
    
}
