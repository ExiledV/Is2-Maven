/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Patrones;

import Modelo.Actividad;
import Modelo.Cliente;
import Modelo.ListaReservas;
import Modelo.Parcelas;
import Modelo.Reserva;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Date;

/**
 *
 * @author dsmcr
 */
public class DAO {
    private static Connection conexionBD;
    
    public DAO(){};
    
    public static void crearDAO(){
        String bd = "jdbc:mysql://localhost:3306/practicapuente4?serverTimezone=" + TimeZone.getDefault().getID();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver de mysql
            // Conexión usando usuario y clave de administrador de la BD
            conexionBD = DriverManager.getConnection(bd, "root", "puente4");
            
        } catch (Exception e) { // Error en la conexión con la BD
            System.out.println("Error de conexión");
        } 
    }
    
    public static ArrayList<Actividad> cargarActividades(){
        ArrayList<Actividad> lista_actividades = new ArrayList<Actividad>();
        Statement declaracion;
        ResultSet res;
        ResultSet res2;
        ArrayList<Integer> idesAct = new ArrayList<Integer>();
        //cargar todas las actividades de la base de datos y los clientes apuntados
        try{
            declaracion = conexionBD.createStatement();
            res = declaracion.executeQuery("SELECT * FROM Actividad");
            
            while (res.next()){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(res.getDate("fecha"));
                Actividad act = new Actividad(res.getString("titulo"),calendar);               
                System.out.println(act.toString());
                lista_actividades.add(act);
                idesAct.add(res.getInt("idActividad"));
            }
            
        }catch(SQLException e){
            System.out.println("Error al cargar la base de datos");
        }
        try{
                int contador = 0;
                int contador2 = 0;
                //cargar los clientes de la actividad
                declaracion = conexionBD.createStatement();
                for (int i = 0; i < lista_actividades.size(); i++){
                    res2 = declaracion.executeQuery("SELECT C.NOMBRE FROM PAREJA P JOIN CLIENTE C ON P.IDCLIENTE1 = C.IDCLIENTE WHERE P.IDACTIVIDAD = " + idesAct.get(contador2));
                    while(res2.next()){

                        String nombre = res2.getString("NOMBRE");
                        Cliente cliente = new Cliente(nombre);
                        lista_actividades.get(contador2).addCliente(cliente);                  
                        System.out.println(contador + " " + nombre);
                        contador++;
                    }
                    contador2++;
                }
        }catch(SQLException e){
            
        }
        
        return lista_actividades;
    }
    
    public static Parcelas cargarParcelas() {
        Statement declaracion;
        ResultSet resFila;
        ResultSet resCol;
        ResultSet resReservas;
        Parcelas parcelas = null;
        int cols = 0;
        int rows = 0;
        
        try{
            declaracion = conexionBD.createStatement();
            
            resCol = declaracion.executeQuery("select count(*) from parcela where columna = 1");
            
            if(resCol.next())
                cols = resCol.getInt(1);
            
            resFila = declaracion.executeQuery("select count(*) from parcela where fila = 1");
            
            if(resFila.next())
                rows = resFila.getInt(1);
            
            System.out.println(cols);
            System.out.println(rows);
            
        }catch(SQLException e){
            System.out.println("Error al cargar la base de datos");
        }
        
        parcelas = new Parcelas(rows,cols);
        
        ListaReservas[][] lista = new ListaReservas[rows][cols];
        String des = "";
        int contadorReservas = 0;
        try{
            declaracion = conexionBD.createStatement();
            
            for(int i = 0; i < rows; i++)
                for(int j = 0; j < cols; j++){
                    ArrayList<Reserva> reservas = new ArrayList<Reserva>();
                    resReservas = declaracion.executeQuery("select p.descripcion,r.idReserva,c.nombre,r.fecha_entrada,r.fecha_salida from (parcela p join reserva r on p.iDparcela = r.idParcela) join cliente c on r.idCliente = c.idCliente where p.fila = " + (i+1) + " and p.columna = " + (j+1));
                    
                    while(resReservas.next()){
                        Cliente cliente = Factory.crearCliente(resReservas.getString("nombre"));                   
                        Reserva reserva = Factory.crearReserva(resReservas.getInt("idReserva"),cliente, resReservas.getDate("fecha_entrada"), resReservas.getDate("fecha_salida"), i, j);
                        des = resReservas.getString("descripcion");
                        reservas.add(reserva);
                        contadorReservas++;
                    }
                    ListaReservas listaAux = new ListaReservas(reservas,des);  
                    lista[i][j] = listaAux;
                }
            
            parcelas.setNumeroReservasTotales(contadorReservas);
            
        }catch(SQLException e){
            System.out.println("Error al cargar la base de datos");
        }
        
        parcelas.setListaReservas(lista);
        return parcelas;
    }
    
    public static void anyadirReserva(Reserva r){
        Statement declaracion;
        ResultSet rescli;
        ResultSet res;
        
        try{
            
            String codigo = "INSERT INTO RESERVA VALUES " + "(?,?,?,?,?)";
            PreparedStatement ps = conexionBD.prepareStatement(codigo);
            
            declaracion = conexionBD.createStatement();
            rescli = declaracion.executeQuery("select idCliente from cliente where nombre like '" + r.getCliente().getNombre() + "'");
            
            int idcli = 0;
            
            if(rescli.next())
                idcli = rescli.getInt(1);
            
            res = declaracion.executeQuery("select idparcela from parcela where fila = " + r.getFila() + " and columna = " + r.getColumna() );
            
            int idparcela = 0;
            
            if(res.next())
                idparcela = res.getInt(1);
            
            ps.setInt(1,r.getId());
            System.out.println(r.getId());
            ps.setInt(2,idcli);
            System.out.println(idcli);
            ps.setInt(3, idparcela);
            System.out.println(idparcela);
            ps.setDate(4, new java.sql.Date(r.getFechaEntrada().getTime()));
            System.out.println(new java.sql.Date(r.getFechaEntrada().getTime()));
            ps.setDate(5, new java.sql.Date(r.getFechaSalida().getTime()));
            System.out.println(new java.sql.Date(r.getFechaSalida().getTime()));
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println("Error al insertar en la base de datos");
        }
    }
     
    public static void modificarReserva(int idReservaAModificar, Reserva rModificada){
        Statement declaracionSelect1, declaracionSelect2, declaracionUpdate;
        int idNuevo, idClienteNuevo = -1, idParcelaNuevo = -1;
        String fechaEntradaNueva, fechaSalidaNueva;
        
        ResultSet resSelect1, resSelect2;
        try{
            declaracionSelect1 = conexionBD.createStatement();
            //SACAR EL ID DEL CLIENTE
            resSelect1 = declaracionSelect1.executeQuery("select idCliente from cliente where nombre like '" + rModificada.getCliente().getNombre() + "'");
            if(resSelect1.next())
                idClienteNuevo = resSelect1.getInt("idCliente");
            
            idNuevo = rModificada.getId();
            //SACAR EL ID DE LA PARCELA
            declaracionSelect2 = conexionBD.createStatement();
            resSelect1 = declaracionSelect1.executeQuery("select idParcela from parcela where fila = " + rModificada.getFila() + ", columna = " + rModificada.getColumna());
            if(resSelect1.next())
                idParcelaNuevo = resSelect1.getInt("idParcela");
            
            fechaEntradaNueva = rModificada.getFechaEntrada().toString();
            fechaSalidaNueva = rModificada.getFechaSalida().toString();
            
            declaracionUpdate = conexionBD.createStatement();
            String query = "UPDATE reservas SET id = " + 
                    idNuevo + ", idCliente = " + idClienteNuevo + ", idParcela = " + idParcelaNuevo + 
                    ", fechaEntrada = TO_DATE('" + fechaEntradaNueva + "', 'YYYY,MM,DD), fechaSalida = TO_DATE('" + fechaSalidaNueva + "', 'YYYY,MM,DD)";
            
            declaracionUpdate.executeUpdate(query);
        }catch(Exception e){
            System.out.println("Error al actualizar en la base de datos");
        }
    }
            
    public static void anyadirActividad(Actividad act){
        Statement declaracion;
        ResultSet resact;
        ResultSet res;
        
        try{
            
            String codigo = "INSERT INTO ACTIVIDAD(idActividad,fecha,lugar,titulo) VALUES " + "(?,?,?,?)";
            PreparedStatement ps = conexionBD.prepareStatement(codigo);
            
            declaracion = conexionBD.createStatement();
            resact = declaracion.executeQuery("select count(*) from actividad");
            
            int numact = 0;
            
            if(resact.next())
                numact = resact.getInt(1);
            
            ps.setInt(1,numact + 1);
            System.out.println(numact + 1);
            ps.setDate(2, new java.sql.Date(act.getFecha().getTime().getTime()));
            System.out.println(new java.sql.Date(act.getFecha().getTime().getTime()));
            ps.setString(3,act.getLugar());
            System.out.println(act.getLugar());
            ps.setString(4,act.getTitulo());
            System.out.println(act.getTitulo());
            
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println("Error al insertar en la base de datos");
        }
    }
   
}
