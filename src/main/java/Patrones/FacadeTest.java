/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patrones;

import Modelo.Actividad;
import Modelo.Cliente;
import Modelo.Modelo;
import Modelo.Parcelas;
import Modelo.Reserva;
import static Patrones.Facade.ventanaAlquilerAdminView;
import campinggas.AlquilerAdminView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alumno
 */
public class FacadeTest {
    
    public FacadeTest() {
    }

    @Test
    public void testAlquilarParcelaAdmin() {
        AlquilerAdminView ventana = Factory.crearAlquilerAdminView(new Parcelas(10,10));
        AlquilerAdminView ventanaPrueba = Facade.AlquilarParcelaAdmin(ventana, new Modelo());
        
        assertTrue(ventanaPrueba.getNumReservas() != ventana.getNumReservas()); //Devuelve el length de reservasParcela    
    }

    @Test
    public void testEmparejamientosArrayVacio() {
        Calendar rightNow = Calendar.getInstance();
        Actividad act = new Actividad("test",rightNow);
        
        ArrayList<String> a = new ArrayList<String>();
        
        assertTrue(act.emparejar() == a);
    }

    @Test
    public void testEmparejamientosArrayImpar() {
        Calendar rightNow = Calendar.getInstance();
        Actividad act = new Actividad("test",rightNow);
        
        Cliente cli1 = Factory.crearCliente("Paquito");
        Cliente cli2 = Factory.crearCliente("Paquillo");
        Cliente cli3 = Factory.crearCliente("Paquete");
        act.addCliente(cli1);
        act.addCliente(cli2);
        act.addCliente(cli3);
        
        assertTrue(act.emparejar().size() == 2);
        
        
        Actividad act1 = new Actividad("test2",rightNow);
        act.addCliente(cli1);
        
        assertTrue(act.emparejar().size() == 0);
    }
    
    @Test
    public void testEmparejamientosArrayPar() {
        Calendar rightNow = Calendar.getInstance();
        Actividad act = new Actividad("test",rightNow);
        
        Cliente cli1 = Factory.crearCliente("Paquito");
        Cliente cli2 = Factory.crearCliente("Paquillo");
        act.addCliente(cli1);
        act.addCliente(cli2);
        
        assertTrue(act.emparejar().size() == 2);
    }
    
    @Test
    public void testSancionar() {
        Cliente cli = Factory.crearCliente("Juan");
        Calendar rightNow = Calendar.getInstance();
        cli.aplicarSancion(rightNow);
        
        assertTrue(cli.getSancion() == rightNow);
    }

    @Test
    public void testAnotarGanador() {
        Calendar rightNow = Calendar.getInstance();
        Actividad act = new Actividad("test",rightNow);
        
        Cliente cli1 = Factory.crearCliente("Paquito");
        act.addCliente(cli1);
        
        act.anotarGanador(cli1);
        
        assertTrue(act.getGanador().equals(cli1.getNombre()));
        
        Cliente cli2 = Factory.crearCliente("Pepito");
        act.addCliente(cli2);
        
        act.anotarGanador(cli2);
        
        assertTrue(act.getGanador().equals(cli2.getNombre()));
    }

    @Test
    public void testBotonAnadirUsuarioActividad() {
        Modelo modelo = new Modelo();
        Calendar rightNow = Calendar.getInstance();
        Actividad act = new Actividad("ski",rightNow);
        Cliente cli = new Cliente("Joan");
        
        modelo.añadirPersonaActividad(cli,act);
        
        assertTrue(modelo.getListaActividades().size() == 1);
    }

    @Test
    public void testBotonRegistrarPago() {
        Modelo modelo = new Modelo();
        Reserva reserva = new Reserva();
        
        modelo.setReservaCompletada(reserva);
        
        assertTrue(modelo.getReservasCompletadas().get(0) == reserva);
    }
    
    @Test
    public void testBotonCalcularPago() {
        Date ini = new Date(10), fin = new Date(15);
        Reserva reserva = new Reserva();
        
        reserva.setFechas(ini, fin);
        
        assertTrue(reserva.calcPrecio() == 5);
    }
    
}
