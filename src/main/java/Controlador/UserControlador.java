/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Actividad;
import Modelo.Cliente;
import Modelo.Modelo;
import Modelo.Reserva;
import Patrones.Facade;
import campinggas.ActividadesAdmin;
import campinggas.ActividadesUser;
import campinggas.AlquilerAdminView;
import campinggas.ConsultarParcelaView;
import campinggas.CrearActividadAdmin;
import campinggas.DisponibilidadCliente;
import campinggas.HubAdmin_View;
import campinggas.HubUserView;
import campinggas.MisReservasView;
import campinggas.VentanaPrincipalView;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UserControlador {
    
    Modelo modelo;
    Cliente c;
    Actividad a;
    miActionListener aL = new miActionListener();
    miChangeListener cL = new miChangeListener();
    miMouseListener mL = new miMouseListener();
    
    VentanaPrincipalView vista;
    DisponibilidadCliente disponibilidadCliente;
    HubUserView hubUsuario;
    ActividadesUser actividadesUsuario;
    ConsultarParcelaView consultarParcela;
    MisReservasView misReservasView;
    
    public UserControlador(VentanaPrincipalView v, Modelo m){
        vista = v;
        modelo = m;
        vista.setMiActionListener(aL);
    }

    class miActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            //SWITCH TOCHO
            
            String pwrd;
            switch(command){
                 case "botonLogin":
                    if( (vista.getNombreUsuario().equals(modelo.getIDAdmin()))){
                         
                        pwrd = new String(vista.getPwrd());
                        pwrd = pwrd.substring(0, pwrd.length());
                        if(pwrd.equals(modelo.getPwrdAdmin())){
                            HubAdmin_View ventanRaul = new HubAdmin_View();
                            ventanRaul.setVisible(true);
                            GerenteControlador gerenteControlador = new GerenteControlador(ventanRaul,modelo); 
                            vista.dispose();
                        }}
                    if( vista.getNombreUsuario().equals(modelo.getIDUser())){
                        
                        pwrd = new String(vista.getPwrd());
                        pwrd = pwrd.substring(0, pwrd.length());
                        if(pwrd.equals(modelo.getPwrdUser())){
                            hubUsuario = new HubUserView();
                            hubUsuario.setVisible(true);
                            hubUsuario.setMiActionListener(aL);
                        }}
                    break;
                case "VisualizarParcleas":
                    disponibilidadCliente = Facade.botonVisualizarParcelas(modelo);
                    disponibilidadCliente.setMiActionListener(aL);
                    break;
                case "Actividades":
                    actividadesUsuario = Facade.botonActividades(modelo);    
                    actividadesUsuario.setMiActionListener(aL);
                    break;
                case "AñadirUsuarioActividad":
                        //mira a ver si le puedes añadir
                        //añadir datos al modelo
                        //sacar un popup que te diga que has sido añadido
                    Facade.botonAnadirUsuarioActividad(modelo, actividadesUsuario);
                        
                    break;
                case "Mis reservas y actividades":
                    Facade.botonReservasYActividades(modelo, vista);
                    break;
                default:    
                    System.out.println("error");
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
    
    class miMouseListener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent arg0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JTable table =(JTable) e.getSource();
            Point point = e.getPoint();
            int row = table.rowAtPoint(point);
            int column = table.columnAtPoint(point);
            if (e.getClickCount() == 1 && table.getSelectedRow() != -1)
            {
                System.out.println(consultarParcela.getdescripcion(row, column));
            }
        }
    }
    
      private static boolean isPasswordCorrect(char[] input) {
        boolean isCorrect = true;
        char[] correctPassword = { 'b', 'u', 'g', 'a', 'b', 'o', 'o' };
 
        if (input.length != correctPassword.length) {
            isCorrect = false;
        } else {
            isCorrect = Arrays.equals (input, correctPassword);
        }
 
        //Zero out the password.
        Arrays.fill(correctPassword,'0');
 
        return isCorrect;
    }
}