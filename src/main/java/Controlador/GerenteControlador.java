/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Patrones.Facade;
import Modelo.Actividad;
import Modelo.Cliente;
import Modelo.Modelo;
import Modelo.Reserva;
import campinggas.ActividadesAdmin;
import campinggas.AlquilerAdminView;
import campinggas.CrearActividadAdmin;
import campinggas.emparejamientosView;
import campinggas.HubAdmin_View;
import campinggas.PagoAdmin;
import campinggas.VisualizarActividadesAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Raúl
 */

/**
 * MaestroDeLosColoresViewJ1 vista1;
    MaestroDeLosColoresViewJ2 vista2;
    
    MaestroDeLosColoresModel modelo;
    AcercaDeView acerca_de;
    FinPartidaView fin;
    MDLCActionListener aL = new MDLCActionListener();
    
    public MaestroDeLosColoresController(MaestroDeLosColoresViewJ1 v1, MaestroDeLosColoresViewJ2 v2, MaestroDeLosColoresModel m)
    {
        vista1 = v1;
        vista2 = v2;
        modelo = m;
        
        
        
        vista1.setActionListener(aL);
        vista2.setActionListener(aL);
        
    }
    * 
    * class MDLCActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae){
            String command = ae.getActionCommand();
            int[] j1, j2, solucion;
            int intentos;
            boolean terminado = false;
            ...
 * @author Raúl
 */
public class GerenteControlador {
    HubAdmin_View vista;
    Modelo modelo;
    Cliente cliente;
    
    miActionListener aL = new miActionListener();
    miChangeListener cL = new miChangeListener();
    miListSelectionListener LSL = new miListSelectionListener();
    miListSelectionListenerReservas LSLR = new miListSelectionListenerReservas();
    
    AlquilerAdminView ventanaAlquilerAdminView;
    ActividadesAdmin actividadesAdmin;
    CrearActividadAdmin crearActividadAdmin;
    VisualizarActividadesAdmin visualizarActividadesAdmin;
    emparejamientosView emparejamientosView;
    PagoAdmin ventanaPagoAdmin;
    
    Date aux_inicio = new Date(), aux_fin = new Date();
    
    
    public GerenteControlador(HubAdmin_View v, Modelo m){
        vista = v;
        modelo = m;
        
        vista.setMiActionListener(aL);
    }

    class miActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            
            //SWITCH TOCHO
            switch(command){
                case "botonAlquilarAdminView":
                    ventanaAlquilerAdminView = Facade.botonAlquilarAdminView(modelo.getParcelas(), aL, LSLR);
                    break;
                case "AlquilarParcelaAdmin":
                    ventanaAlquilerAdminView = Facade.AlquilarParcelaAdmin(ventanaAlquilerAdminView, modelo);
                    break;
                case "ventanaCrearActividad":
                    crearActividadAdmin = Facade.ventanaCrearActividad(aL);
                    break;
                case "visualizarActividad":
                    visualizarActividadesAdmin = Facade.visualizarActividad(aL, LSL, modelo);
                    break;

                case "botonVisualizarActividades":
                    actividadesAdmin = Facade.botonVisualizarActividades(aL);
                    break;
                    
                case "crearActividad":
                    crearActividadAdmin = Facade.crearActividad(modelo);
                    break;
                case "Emparejamientos":
                    emparejamientosView = Facade.Emparejamientos(visualizarActividadesAdmin);
                    break;
                case "Sancionar":
                    visualizarActividadesAdmin = Facade.Sancionar(visualizarActividadesAdmin);
                    break;
                    
                case "AnotarGanador":
                    visualizarActividadesAdmin = Facade.AnotarGanador(visualizarActividadesAdmin);
                break;
                case "verFecha":
                    //aux_ini y aux_fin son las fechas de inicio y fin
                    ventanaPagoAdmin = Facade.verFecha(modelo, ventanaPagoAdmin, aux_inicio, aux_fin);
                break;
                case "actualizarFecha":
                    ventanaPagoAdmin = Facade.actualizarFecha(modelo, ventanaPagoAdmin, aux_inicio, aux_fin);
                break;
                case "botonPago":
                    Facade.botonPago(modelo);
                    ventanaPagoAdmin.setMiActionListener(aL);
                break;
                case "Calcular Pago":
                    ventanaPagoAdmin.CalcularPago();
                    break;
                case "Registrar Pago":
                    Facade.botonRegistrarPago(ventanaPagoAdmin, modelo);
                    break;
                //case:
                default:
                    break;
            }
        }
    }
    
    class miChangeListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            String evento = e.getSource().toString(); //Creo que es así, habria que mirarlo
            
            //SWITCH o lo que sea.
            switch(evento){
                case "botonAlquilar":
                    AlquilerAdminView ventanaAlqulerAdminView = new AlquilerAdminView();
                    break;
                default:
                    break;
            }
        }
        
    }
    
    class miListSelectionListener implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {  
            visualizarActividadesAdmin.actualizarClientes(visualizarActividadesAdmin.getActividad().getGanador());
        }
    }
    
    class miListSelectionListenerReservas implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            ventanaAlquilerAdminView.actualizarReservaSeleccionada();
        }
    }
}
