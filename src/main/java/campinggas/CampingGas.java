/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package campinggas;

import Controlador.UserControlador;
import Modelo.Modelo;
import Patrones.DAO;

/**
 *
 * @author alumno
 */
public class CampingGas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DAO.crearDAO();
        Modelo modelo = new Modelo();
        VentanaPrincipalView yoLoHagoEnCMasPlas = new VentanaPrincipalView();
        UserControlador controlador = new UserControlador(yoLoHagoEnCMasPlas,modelo);
        /*********************************************************
         * https://github.com/pabol97/Puente4.git
         */
        
    }
    
}
