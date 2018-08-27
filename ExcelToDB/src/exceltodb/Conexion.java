/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author programacion
 */
public class Conexion {
    private Connection conexionGlobal = null;
    
    public void CrearConexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            //conexionGlobal = DriverManager.getConnection("jdbc:mysql://192.168.107.116/overxakmovies?user=practicante&password=Overx@k2018&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
            conexionGlobal = DriverManager.getConnection("jdbc:mysql://localhost/db_ronald_tests?user=ronald&password=ronald&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        }catch(SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexionGlobal() {
        return conexionGlobal;
    }
    
    
}
