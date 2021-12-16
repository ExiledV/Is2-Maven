package Patrones;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Modelo.Cliente;
import static Patrones.Facade.crearActividadAdmin;
import static Patrones.Facade.ventanaAlquilerAdminView;
import Modelo.Modelo;
import Modelo.Parcelas;
import Modelo.Reserva;
import campinggas.ActividadesAdmin;
import campinggas.ActividadesUser;
import campinggas.AlquilerAdminView;
import campinggas.ConsultarParcelaView;
import campinggas.CrearActividadAdmin;
import campinggas.DisponibilidadCliente;
import campinggas.HubUserView;
import campinggas.MisReservasView;
import campinggas.PagoAdmin;
import campinggas.VentanaPrincipalView;
import campinggas.VisualizarActividadesAdmin;
import campinggas.emparejamientosView;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author alumno
 */
public class Factory {
    
    public static AlquilerAdminView crearAlquilerAdminView(Parcelas p){
        return new AlquilerAdminView();
    }
    
    public static CrearActividadAdmin creaUnaActividadAdmin(){
        return new CrearActividadAdmin();
    }
    
    public static ActividadesAdmin crearActividadesAdmin(){
        return new ActividadesAdmin();
    }
    
    public static void crearActividad(Modelo modelo, CrearActividadAdmin crearActividadAdmin){
        modelo.crearActividad(crearActividadAdmin.getTitulo(),crearActividadAdmin.getdate());
    }
    
    public static emparejamientosView crearEmparejamientosView(ArrayList<String> parejas){
        return new emparejamientosView(parejas);
    }
    
    public static VisualizarActividadesAdmin crearVisualizarActividadesAdmin(Modelo modelo){
        return new VisualizarActividadesAdmin(modelo.getListaActividades(),modelo.getListaActividades().get(0).getGanador());
    }
    
    public static Cliente crearCliente(String nombre){
        return new Cliente(nombre);
    }
    
    public static Reserva crearReserva(int id, Cliente cliente, Date fechaIni, Date fechaFin, int fila, int columna){
        return new Reserva(id,cliente, fechaIni, fechaFin, fila, columna);
    }
    
    public static void crearMisReservasView(Modelo modelo, Cliente cliente){
        new MisReservasView(modelo, cliente).setVisible(true);
    }
    
    public static VentanaPrincipalView crearVentanaPrincipalView(){
        return new VentanaPrincipalView();
    } 
    
    public static void crearConsultarParcelaView(Parcelas parcela, Modelo modelo ,Cliente cliente){
        new ConsultarParcelaView(parcela, modelo, cliente).setVisible(true);
    }
    
    public static DisponibilidadCliente crearDisponibilidadCliente(Modelo modelo){
        return new DisponibilidadCliente(modelo.getParcelas());
    }
    
    public static ActividadesUser crearActividadesUser(Modelo modelo){
        return new ActividadesUser( modelo.getListaActividades());
    }
    
    public static MisReservasView crearMisReservasView(Modelo modelo, VentanaPrincipalView vista){
        return new MisReservasView(modelo, vista.getUsuario());
    }
    
    public static PagoAdmin crearPagoAdmin(Modelo modelo){
        return new PagoAdmin(modelo);
    }
    
}
