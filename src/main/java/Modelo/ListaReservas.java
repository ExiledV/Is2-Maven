/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Patrones.DAO;
import java.util.ArrayList;

/**
 *
 * @author Raúl
 */
public class ListaReservas {
    ArrayList<Reserva> reservas;
    private String descripcion;

    public ListaReservas(ArrayList<Reserva> r,String descrip){
        reservas = r;
        descripcion = descrip;
    }

    public boolean compruebaReserva(Reserva r){
        boolean sePuede = true;
        int r_fila, r_columna, numReservas;

        r_fila = r.getFila();
        r_columna = r.getColumna();
        
        numReservas = reservas.size(); //Me llevo el numero de reservas que tengo guardado

 
        for(int i = 0; i < numReservas; i++){
            if(sePuede){
                //Si mi entrada está entre medias de una reserva = FALSE
                if(r.getFechaEntrada().compareTo(reservas.get(i).getFechaEntrada()) >= 0 && r.getFechaEntrada().compareTo(reservas.get(i).getFechaSalida()) <= 0) {
                    sePuede = false;
                }
                //Si mi salida está entre medias de una reserva = FALSE
                else if(r.getFechaSalida().compareTo(reservas.get(i).getFechaEntrada()) <= 0 && r.getFechaSalida().compareTo(reservas.get(i).getFechaSalida()) >= 0)
                    sePuede = false;
                //SI mi tengo entre medias de mi entrada y salida una reserva, FALSE.
                else if(r.getFechaEntrada().compareTo(reservas.get(i).getFechaEntrada()) <= 0 && r.getFechaSalida().compareTo(reservas.get(i).getFechaSalida()) >= 0)
                    sePuede = false;
            }
        }
        return sePuede;
    }
    
    public boolean addReserva(Reserva nuevaReserva){
        boolean sePuede;
        sePuede = compruebaReserva(nuevaReserva); //Compruebas si la reserva está en fechas válidas
        if (sePuede){
            reservas.add(nuevaReserva);
            DAO.anyadirReserva(nuevaReserva);
        }
        
        return sePuede;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }
    
    public int numeroReservas(){
        return reservas.size();
    }
    
    public Reserva getReserva(int i){
        return reservas.get(i);
    }

    void setFilasYColumnasEnReservas(int filas, int columnas) {
        for(int i = 0; i < numeroReservas(); i++){
            reservas.get(i).setFila(filas);
            reservas.get(i).setColumna(columnas);
        }
    }
    
    boolean modificaReserva(Reserva reservaMod) {
        Reserva reservaAux;
        int posicion;
        boolean sePuede;
        
        posicion = reservaMod.getPosicionAModificar();
        reservaAux = reservas.get(posicion);
        
        reservas.remove(posicion);
        
        if(!addReserva(reservaMod)){
            reservas.add(reservaAux); //Si no se ha podido modificar, volvemos a meter la reserva eliminada en el vector.
            DAO.modificarReserva(posicion + 1, reservaMod);
            sePuede = false;
        }
        else
            sePuede = true;

        return sePuede;
    }
    public String getdescripcion()
    {
        return descripcion;
    }
}
