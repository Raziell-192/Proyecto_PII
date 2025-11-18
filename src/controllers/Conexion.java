/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import java.sql.*;
/**
 *
 * @author macair
 */
public class Conexion {
    public static Connection con;
    public static Statement st;
    private static String url="jdbc:postgresql://localhost:5432/bd_clinica_dental";
    private static String contra="*****";
    
//    public static void conectar(){
//        try{
//            con = DriverManager.getConnection(
//                url, 
//                "postgres", 
//                contra
//            );
//            System.out.println("Conexión establecida");
//            st=con.createStatement();
//        }
//        catch(Exception ex){
//            System.out.println("Error de conexión: " + ex.getMessage());
//        }   
//    }
    
    public static Connection conectar(){
        try{
            con = DriverManager.getConnection(
                url, 
                "postgres", 
                contra
            );
            System.out.println("Conexión establecida");
            st=con.createStatement();
        }
        catch(Exception ex){
            System.out.println("Error de conexión: " + ex.getMessage());
        }
        return con;
    }
    
    public static boolean Operacion(String sql) {
        try {
            st.executeUpdate(sql);
            return true;
        } catch (SQLException ex) {
            System.out.println("Error en SQL: " + ex.getMessage());
            return false;
        }
    }
    
    public static ResultSet Consulta(String sql) {
        try {
            return st.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println("Error en consulta: " + ex.getMessage());
            return null;
        }
    }

}
