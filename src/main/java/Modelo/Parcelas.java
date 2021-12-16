/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Patrones.DAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alumno
 */
public class Parcelas {
    //ESTOS VALORES SE PUEDEN MODIFICAR Y CAMBIA LA TABLA
    int filas;
    int columnas;
    ListaReservas[][] parcelas ; //MATRIZ QUE REPRESENTA EL CAMPING; el tercer atributo son las reservas que tiene cada parcela.
    int numeroReservasTotales;
   
    public Parcelas(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        parcelas = new ListaReservas[filas][columnas]; //modelo.getListaReservas() ? 
        numeroReservasTotales = 0;
       // parcelas.llenaDatosTest();
        
    }
    
    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public ListaReservas[][] getListaReservas() {
        return parcelas;
    }
    
    public void setListaReservas(ListaReservas[][] lista){
        parcelas = lista;
    }
    
    public ListaReservas getParcela(int fila, int columna){
        return parcelas[fila][columna];
    }
    
     public void setReservaAParcela(int fila, int columna, ListaReservas reservas){
         parcelas[fila][columna] = reservas;
    }
    
    public boolean addReserva(Reserva r,int fila, int columna){
        boolean sePuede;
        sePuede = parcelas[fila][columna].addReserva(r);
        
        return sePuede;
    }

    public void establecerCoherencia() {
        for(int i = 0; i < getFilas(); i++){
            for(int j = 0; j < getColumnas(); j++){
                if(parcelas[i][j] != null) //Si tiene reservas
                    parcelas[i][j].setFilasYColumnasEnReservas(i, j);
            }
        }
    }

    public boolean addNuevaReserva(Reserva nuevaReserva) {
        int fila, columna;
        fila = nuevaReserva.getFila();
        columna = nuevaReserva.getColumna();
        if(parcelas[fila][columna] != null){
            
            return parcelas[fila][columna].addReserva(nuevaReserva); //SI NO HAY NINGUNA RESERVA ES NULL Y NO SE PUEDE HACER
        }
        else{
            ArrayList<Reserva> arrayReservas = new ArrayList<Reserva>();
            arrayReservas.add(nuevaReserva);
            ListaReservas nuevaListaReservas = new ListaReservas(arrayReservas,"Nueva Reserva");
            DAO.anyadirReserva(nuevaReserva);
            parcelas[fila][columna] = nuevaListaReservas;
            return true;
        }
         
    }

    boolean modificaReserva(Reserva reserva) {
        int fila, columna;
        fila = reserva.getFila();
        columna = reserva.getColumna();
        return parcelas[fila][columna].modificaReserva(reserva);
    }
    
    
    public boolean hayReservas(Date fecha, int fila, int col){ //Comprueba si esa parcela esta reservada para esa fecha
        boolean resultado = false;
     
        if(parcelas[fila][col] != null){
           ArrayList<Reserva> reservasDeParcela = new ArrayList<Reserva>(parcelas[fila][col].reservas);
       
            Reserva reserva;
            for(int i = 0; i < reservasDeParcela.size(); i++)
                if(!resultado)
                {
                    reserva = reservasDeParcela.get(i);
                    
                    String fechaEntradaString = reserva.getFechaEntrada().toString();
                    String[] fechaEntradaVec = fechaEntradaString.split(" ");
                    String mesEntrada = parseMes(fechaEntradaVec[1]);
                  
                    String fechaSalidaString = reserva.getFechaSalida().toString();
                    String[] fechaSalidaVec = fechaSalidaString.split(" ");
                    String mesSalida = parseMes(fechaEntradaVec[1]);
                  
                    String fechaString = fecha.toString();
                    String[] fechaVec = fechaString.split(" ");
                    String mesFecha = parseMes(fechaVec[1]);
                    //ESTOY HASTA LA POLLA DE PROGRAMAR COMO LO SUPISTE
                    /*
                    System.out.println("Dia:" + ( Integer.parseInt(fechaEntradaVec[2]) ) + "Mes:"+ mesEntrada + " Año:" + ( Integer.parseInt(fechaEntradaVec[5]) - 1900));
                    System.out.println("Dia:" + ( Integer.parseInt(fechaSalidaVec[2]) ) + "Mes:"+ mesSalida + " Año:" + ( Integer.parseInt(fechaSalidaVec[5]) - 1900));
                    System.out.println("Dia:" + ( Integer.parseInt(fechaVec[2]) ) + "Mes:"+ mesFecha + " Año:" + ( Integer.parseInt(fechaVec[5])));
                    System.out.println("-----------------------------------------");
                    */
                    if(((Integer.parseInt(fechaEntradaVec[5]) - 1900) <= Integer.parseInt(fechaVec[5])) && (Integer.parseInt(fechaVec[5]) <= (Integer.parseInt(fechaSalidaVec[5]) - 1900)))
                        if((Integer.parseInt(mesEntrada) <= Integer.parseInt(mesFecha)) && ( Integer.parseInt(mesFecha) <= Integer.parseInt(mesSalida)))
                            if(((Integer.parseInt(fechaEntradaVec[2])) < Integer.parseInt(fechaVec[2])) && (Integer.parseInt(fechaVec[2]) < (Integer.parseInt(fechaSalidaVec[2]) )))
                                resultado = true;
                }
        }
        return resultado;
    }
        
    String parseMes(String s){
        switch(s){
            case "Jan":
                return "0";
            case "Feb":
                return  "1";
            case "Mar":
                return "2";
            case "Apr":
                return "3";
            case "May":
                return "4";
            case "Jun":
                return"5";
            case "Jul":
                return "6";
            case "Aug":
                return "7";
            case "Sep":
                return "8";
            case "Oct":
                return "9";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
            default:
                return "-1";
        }
    }

    public int getNumeroReservasTotales() {
        return numeroReservasTotales;
    }
    
    public void setNumeroReservasTotales(int contadorReservas) {
        numeroReservasTotales = contadorReservas;
    }
     
}
