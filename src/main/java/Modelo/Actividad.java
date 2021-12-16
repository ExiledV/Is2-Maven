/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Cliente;
import Modelo.Cliente;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Actividad {
    
    String tituloActividad;
    ArrayList<Cliente> clientesApuntados = new ArrayList<Cliente>();
    ArrayList<String> parejas = new ArrayList<String>();
    Calendar fecha;
    String lugar;
    Cliente ganador = new Cliente("");
    
    public Actividad(String _tituloActividad, Calendar _fecha){
        tituloActividad = _tituloActividad;
        fecha = _fecha;
        lugar = "Canada";
    }
    
    public String getTitulo() {
        return tituloActividad;
    }

    public ArrayList<Cliente> getListaClientes() {
        return clientesApuntados;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public String getLugar() {
        return lugar;
    }
    
    public ArrayList<String> emparejar() {
        String nombre1 = "";
        for (int i = 0; i < clientesApuntados.size(); i++){
            if(i%2 != 0){
                String par = nombre1 + " " + clientesApuntados.get(i).getNombre();
                
                parejas.add(par);
            }else{
                nombre1 = clientesApuntados.get(i).getNombre();
            }
            if(i%2 == 0 && i == clientesApuntados.size() - 1)
                parejas.add(nombre1);
        }
        return parejas;
    }
    
    public String getGanador(){
        return ganador.getNombre();
    }
    
    public void anotarGanador(Cliente clienteSeleccionado) {
        ganador = clienteSeleccionado;
    }
    
     public void visualizarTodosLosClientesApuntados(){
        for(int i = 0; i < clientesApuntados.size(); i++)
            System.out.println(clientesApuntados.get(i).getNombre());
    }
    
    public void addCliente(Cliente c){
        clientesApuntados.add(c);
    }
    
    public String toString(){
        return (tituloActividad + "\n" + fecha.getTime());
    }
}
