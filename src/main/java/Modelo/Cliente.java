/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Calendar;

/**
 *
 * @author alumno
 */
public class Cliente {
    private String nombre = "";
    private Calendar sancion = null;
    
    public Cliente(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void aplicarSancion(Calendar fecha){
        sancion = fecha;
    }
    
    public Calendar getSancion(){
        return sancion;
    }
}
