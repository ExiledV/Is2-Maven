/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;
import Patrones.DAO;
import Patrones.Factory;
import java.sql.*;
import java.util.*;
/**
 *
 * @author alumno
 */
public class Modelo {
    private ArrayList<Reserva> reservasCompletadas;
    private ArrayList<Reserva> reservasActuales = new ArrayList<Reserva>(); // Hay que hacer una función que lo llene/hacer que se llene con cada nueva reserva
    private Parcelas parcelas;
    private Map<Cliente,ArrayList<Parcelas>> reservas = new HashMap<Cliente, ArrayList<Parcelas>>();
    private ArrayList<Actividad> lista_actividades = new ArrayList<Actividad>();
    private String usuarioAdmin = "admin";
    private String contraseñaAdmin = "admin";
    private String usuarioUser = "user";
    private String contraseñaUser = "user";
    
    private String[] nombresClientesTest = {"raul","carlos"};
     
    public Modelo(){
        cargarDatos();
    }
    
    private void cargarDatos(){
        lista_actividades = DAO.cargarActividades();
        parcelas = DAO.cargarParcelas();
    }
    
    public ArrayList<Reserva> getReservasCompletadas(){        
        return reservasCompletadas;
    }
    
    public void setReservaCompletada(Reserva reserva){
        reservasCompletadas.add(reserva);
    }
    
    public ArrayList<Parcelas> cargarReservas(Cliente cliente){
        return reservas.get(cliente);
    }
    
    /*
    ESTO ES COMO DEBERIA SER PARA SER CONSISTENTE PERO NO VA XQ FALTAN INSERTAR LA PARCELA EN TODAS LAS RESERVAS
    
    public ArrayList<Parcelas> cargarReservas(Cliente cliente){
        ArrayList<Parcelas> parcelas_aux = new ArrayList<Parcelas>();
        
        for (int i = 0; i < parcelas.getFilas(); i++)
            for (int j = 0; j < parcelas.getColumnas(); j++)
                for (int z = 0; z < parcelas.getListaReservas()[i][j].numeroReservas(); z++)
                    if(parcelas.getListaReservas()[i][j].getReserva(z).getCliente().getNombre() == cliente.getNombre())
                        parcelas_aux.add(parcelas.getParcela(i, z));
    
        return parcelas_aux;
    }   
    */
    
    public ArrayList<Actividad> cargarActividades(Cliente cliente){
        ArrayList<Actividad> actividades_aux = new ArrayList<>();
        
        for (int i = 0; i < lista_actividades.size(); i++)
            for (int j = 0; j < lista_actividades.get(i).clientesApuntados.size(); j++)
                if(lista_actividades.get(i).clientesApuntados.get(j).getNombre().equals(cliente.getNombre()))
                    actividades_aux.add(lista_actividades.get(i));
        
        return actividades_aux;
    }

    public Parcelas getParcelas() {
        return parcelas;
    }
    
    public boolean addNuevaReserva(Reserva nuevaReserva) {
        return parcelas.addNuevaReserva(nuevaReserva);
    }

    public boolean modificaReserva(Reserva reserva) {
        return parcelas.modificaReserva(reserva);
    }
    
    public void crearActividad(String titulo, Calendar fecha){
        Actividad act = new Actividad(titulo,fecha);
        lista_actividades.add(act);
        DAO.anyadirActividad(act);
    }
    
    public ArrayList<Actividad> getListaActividades(){
        return lista_actividades;
    }
    
    public String getIDAdmin(){
        return usuarioAdmin;
    }
    public String getPwrdAdmin(){
        return contraseñaAdmin;
    }
    public String getIDUser(){
        return usuarioUser;
    }
    public String getPwrdUser(){
        return contraseñaUser;
    }
    /*
    public void creaReservasTest(){
        this.parcelas = new Parcelas(10,10);    //constantes? subirlo al constructor??
        java.util.Date d1 = new java.util.Date(2021,10,15);
        java.util.Date d2 = new java.util.Date(2021,10,25);
        for ( Object var : nombresClientesTest) //Cada uno de los clientes predefinidos tiene una oportunidad para hacer la reserva de la parcela
            for(int i = 0; i < parcelas.getFilas(); i++)
                for(int j = 0; j < parcelas.getColumnas(); j++)   
                    if(Math.random() < 0.05 ){ //he bajado la probabilidad para que hayan vacias, como todos los clientes pasan por todas las parcelas es facil que todas esten reservadas
                        parcelas.addNuevaReserva(new Reserva(new Cliente((String)var),d1,d2,i,j)); //PREGUNTAR A RAUL SI ESTO FUNCIONA PORQUE YO TENGO LA VERSION ANTERIOR
                        //System.out.println("RESERVA AÑADIDA(" +i+j+") por:"+var );
                    }
    }
    */
    
    
    /* XQ NO ESTA COMENTADO SI NO SE USA EN NINGUNA PARTE ????????
    
    public Map<Cliente,ArrayList<Actividad>> getActividades(){
        return actividades;
    }
    */
    
    public void añadirPersonaActividad(Cliente c, Actividad a){
        int i = 0;
        while(lista_actividades.get(i).getTitulo() != a.getTitulo())
            i++;       
        if(lista_actividades.get(i).getTitulo() == a.getTitulo()){
        Actividad actAux = lista_actividades.get(i);
        actAux.addCliente(c);
        lista_actividades.remove(i);
        lista_actividades.add(actAux);
        }
          
    }
    public ArrayList<Reserva> getArraylistReservas()
    {
        return reservasActuales;
    }
    
    public java.util.Date getFechaInicio(int i){
        Reserva r_aux; 
        r_aux = reservasActuales.get(i);
        return r_aux.getFechaEntrada();
    }
    
    public java.util.Date getFechaFin(int i){
        Reserva r_aux; 
        r_aux = reservasActuales.get(i);
        return r_aux.getFechaSalida();
    }
    
    public void setFechaInicioFin(int i,java.util.Date inicio, java.util.Date fin)
    {
        Reserva r_aux;
        r_aux = reservasActuales.get(i);
        r_aux.setFechas(inicio, fin);
        reservasActuales.set(i, r_aux);
    }
        
}
