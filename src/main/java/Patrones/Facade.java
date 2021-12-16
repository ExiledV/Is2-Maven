/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patrones;

import Controlador.GerenteControlador;
import Modelo.Actividad;
import Modelo.Modelo;
import Modelo.Parcelas;
import Modelo.Reserva;
import campinggas.ActividadesAdmin;
import campinggas.ActividadesUser;
import campinggas.AlquilerAdminView;
import campinggas.CrearActividadAdmin;
import campinggas.DisponibilidadCliente;
import campinggas.HubAdmin_View;
import campinggas.HubUserView;
import campinggas.MisReservasView;
import campinggas.PagoAdmin;
import campinggas.VentanaPrincipalView;
import campinggas.VisualizarActividadesAdmin;
import campinggas.emparejamientosView;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author alumno
 */
public class Facade {
    
    static AlquilerAdminView ventanaAlquilerAdminView;
    static ActividadesAdmin actividadesAdmin;
    static CrearActividadAdmin crearActividadAdmin;
    static VisualizarActividadesAdmin visualizarActividadesAdmin;
    static emparejamientosView emparejamientos_View;
    static PagoAdmin ventanaPagoAdmin;
    
    private Facade(){
        
    }
    
    public static AlquilerAdminView botonAlquilarAdminView(Parcelas p, ActionListener aL, ListSelectionListener LSLR){
        ventanaAlquilerAdminView = Factory.crearAlquilerAdminView(p);
        ventanaAlquilerAdminView.setVisible(true);
        ventanaAlquilerAdminView.setMiActionListener(aL);
        ventanaAlquilerAdminView.setMiListSelectionListener(LSLR);
        
        return ventanaAlquilerAdminView;
    }
    
    public static AlquilerAdminView AlquilarParcelaAdmin(AlquilerAdminView ventanaAlquilerAdminView, Modelo modelo){
        Reserva nuevaReserva;
        boolean nueva;
        nuevaReserva = ventanaAlquilerAdminView.getReserva(); //Le pasamos por referencia el booleano.
        nueva = ventanaAlquilerAdminView.esNueva();

        if(nueva){ //Si la reserva es nueva
            if(!modelo.addNuevaReserva(nuevaReserva)) //Si es inválida muestra error
                ventanaAlquilerAdminView.mostrarError();
            else
            {
                ventanaAlquilerAdminView.actualizarLista(); //Si es válida, modelo modificado y hay que actualizar la lista
            }
        }
        else{
            if(!modelo.modificaReserva(nuevaReserva)) //Si no puede modificar la reserva correctamente:
                ventanaAlquilerAdminView.mostrarError();
            else{
                ventanaAlquilerAdminView.actualizarLista();
            }
        }
        return ventanaAlquilerAdminView;  
    }
    
    public static CrearActividadAdmin ventanaCrearActividad(ActionListener aL){
        crearActividadAdmin = Factory.creaUnaActividadAdmin();
        crearActividadAdmin.setVisible(true);
        crearActividadAdmin.setMiActionListener(aL);
        return crearActividadAdmin;
    }
    
    public static VisualizarActividadesAdmin visualizarActividad(ActionListener aL, ListSelectionListener LSL, Modelo modelo){
        visualizarActividadesAdmin = Factory.crearVisualizarActividadesAdmin(modelo);
        visualizarActividadesAdmin.setVisible(true);
        visualizarActividadesAdmin.setMiActionListener(aL);
        visualizarActividadesAdmin.setMiListListener(LSL);
        return visualizarActividadesAdmin;
    }
    
    public static ActividadesAdmin botonVisualizarActividades(ActionListener aL){
        actividadesAdmin = Factory.crearActividadesAdmin();
        actividadesAdmin.setVisible(true);
        actividadesAdmin.setMiActionListener(aL);
        return actividadesAdmin;
    }
    
    public static CrearActividadAdmin crearActividad(Modelo modelo){
        Factory.crearActividad(modelo, crearActividadAdmin);
        crearActividadAdmin.setVisible(false);
        crearActividadAdmin.dispose();
        return crearActividadAdmin;
    }
        
    public static emparejamientosView Emparejamientos(VisualizarActividadesAdmin visualizarActividadesAdmin){
        Actividad actividad = visualizarActividadesAdmin.getActividad();
        ArrayList<String> parejas_aux = actividad.emparejar();

        emparejamientos_View = Factory.crearEmparejamientosView(parejas_aux);
 
        emparejamientos_View.setVisible(true);
        return emparejamientos_View;
    }
    
    public static VisualizarActividadesAdmin Sancionar(VisualizarActividadesAdmin visualizarActividadesAdmin){
        visualizarActividadesAdmin.getClienteSeleccionado().aplicarSancion(visualizarActividadesAdmin.getFechaActividad());
        visualizarActividadesAdmin.actualizarClientes(visualizarActividadesAdmin.getActividad().getGanador());  
        return visualizarActividadesAdmin;
    }
         
    public static VisualizarActividadesAdmin AnotarGanador(VisualizarActividadesAdmin visualizarActividadesAdmin){
        visualizarActividadesAdmin.getActividad().anotarGanador(visualizarActividadesAdmin.getClienteSeleccionado());     
        visualizarActividadesAdmin.setGanador(visualizarActividadesAdmin.getClienteSeleccionado().getNombre());
        return visualizarActividadesAdmin;
    }
    
    public static PagoAdmin verFecha(Modelo modelo,PagoAdmin ventanaPagoAdmin, Date aux_inicio, Date aux_fin){
        aux_inicio = modelo.getFechaInicio(ventanaPagoAdmin.getIndex());
        aux_fin = modelo.getFechaInicio(ventanaPagoAdmin.getIndex());
        ventanaPagoAdmin.setFechas( aux_inicio, aux_fin);
        return ventanaPagoAdmin;
    }
    public static PagoAdmin actualizarFecha(Modelo modelo,PagoAdmin ventanaPagoAdmin, Date aux_inicio, Date aux_fin){
        Date inicio = ventanaPagoAdmin.getFechaInicio();
        Date fin = ventanaPagoAdmin.getFechaFin();
        modelo.setFechaInicioFin(ventanaPagoAdmin.getIndex(), inicio, fin);
        return ventanaPagoAdmin;
    }
    
    public static DisponibilidadCliente botonVisualizarParcelas(Modelo modelo){
        DisponibilidadCliente disponibilidadCliente = Factory.crearDisponibilidadCliente(modelo); 
        disponibilidadCliente.setVisible(true);
        return disponibilidadCliente;
    }         
    
    public static ActividadesUser botonActividades(Modelo modelo){
        ActividadesUser actividades = Factory.crearActividadesUser(modelo);
        actividades.setVisible(true);
        return actividades;
    }
    
    public static void botonAnadirUsuarioActividad(Modelo modelo, ActividadesUser actividadesUsuario){
        System.out.println(actividadesUsuario.dameInfoActividad().getTitulo());
        modelo.añadirPersonaActividad(actividadesUsuario.dameInfoCliente(), actividadesUsuario.dameInfoActividad() );
        
        ArrayList<Actividad> a = modelo.getListaActividades();
        for(int i = 0; i < a.size(); i++){
            System.out.println("ACTIVIDAD: " + a.get(i).getTitulo());
            a.get(i).visualizarTodosLosClientesApuntados();
        }
    }
    
    public static MisReservasView botonReservasYActividades(Modelo modelo, VentanaPrincipalView vista){
        MisReservasView reservasView = Factory.crearMisReservasView(modelo, vista);
        reservasView.setVisible(true);
        return reservasView;
    }
    
    public static PagoAdmin botonPago(Modelo modelo){
        PagoAdmin ventanaPagoAdmin = Factory.crearPagoAdmin(modelo);
        ventanaPagoAdmin.setVisible(true);
        return ventanaPagoAdmin;
    }
    
    public static void botonRegistrarPago(PagoAdmin ventanaPagoAdmin, Modelo modelo){
        System.out.println("Boton Resistrar Pago pulsado");
        
        if(ventanaPagoAdmin.setReservaCompletada(ventanaPagoAdmin.getReserva()) && ventanaPagoAdmin.precioCalculado()){
            modelo.setReservaCompletada(ventanaPagoAdmin.getReserva());
            ventanaPagoAdmin.dispose();
            System.out.println("Registro terminado");
            }
        else if(ventanaPagoAdmin.precioCalculado())
            System.out.println("Error al introducir los datos de la reserva. Por favor, compruebe que:\nLas "
            + "fechas de inicio y fin sean la primera posterior a la segunda");
        else
            System.out.println("Por favor, primero calcule el precio a pagar...");
    }
}