/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltodb;

import exceltodb.models.Category;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import exceltodb.models.Movie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;


/**
 *
 * @author programacion
 */
public class Principal extends Application {
    
    private File archivoSeleccionado;
    
    @Override
    public void start(Stage escenarioPrincipal) {
        Button abrirBtn = new Button();
        abrirBtn.setText("Abrir...");
        ProgressBar loadingBar = new ProgressBar(0);
        loadingBar.setMaxWidth(300);
        loadingBar.setProgress(0);
        
        FileChooser lectorDeArchivos = new FileChooser();
        lectorDeArchivos.setTitle("Abrir...");
        
        ArrayList<Movie> data = new ArrayList<Movie>();
        
        Conexion conexion = new Conexion();
        
        abrirBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                archivoSeleccionado = lectorDeArchivos.showOpenDialog(escenarioPrincipal);
                conexion.CrearConexion();
                if(!(archivoSeleccionado == null)){
                    try {
                        loadingBar.setProgress(0);
                        System.out.println(archivoSeleccionado.getName());
                        POIFSFileSystem POIfs = new POIFSFileSystem(archivoSeleccionado);
                        HSSFWorkbook libro = new HSSFWorkbook(POIfs);
                        HSSFSheet hoja = libro.getSheetAt(0);
                        String row1 = "";
                        String row2 = "";
                        String row3 = "";
                        String rows = "";
                        String[] rowArr;
                        Set<String> categoriesAll =  new HashSet<String>();
                        //double dataInd = (100/Double.parseDouble(hoja.getPhysicalNumberOfRows() + ""))/100;
                        for (int i = 0; i < hoja.getPhysicalNumberOfRows(); i++) {
                            Movie newMovie = new Movie();
                            row1 = hoja.getRow(i).getCell(0).toString();
                            if(!(hoja.getRow(i).getCell(1) == null)){
                                row2 = hoja.getRow(i).getCell(1).toString();
                            }
                            if(!(hoja.getRow(i).getCell(2) == null)){
                                row3 = hoja.getRow(i).getCell(2).toString();
                            }
                            
                            rows = row1 + row2 + row3;
                            rowArr = rows.split("::");

                            newMovie.setId(Integer.parseInt(rowArr[0]));
                            
                            newMovie.setName(getName(rowArr[1]));
                            newMovie.setYear(getYear(rows));
                            newMovie.setCategories(rowArr[2]);
                            String[] categories = getCategoriesFromMovie(newMovie.getCategories());
                            //System.out.println(newMovie.getCategories());
                            //System.out.println(newMovie.toString());
                            //System.out.println(loadingBar.getProgress() + dataInd);
                            //System.out.println();
                            //loadingBar.setProgress(loadingBar.getProgress() + dataInd);
                            //conexion.getConexionGlobal().createStatement().execute(String.format("INSERT INTO Movies (Id, Name, Year) VALUES (%d, %s, %s);", newMovie.getId(), "\"" + newMovie.getName() + "\"", "\"" + newMovie.getYear() + "\""));
                            for(String cat : categories){
                                categoriesAll.add(cat);
                            }
                            data.add(newMovie);
                            row1 = row2 = row3 = "";
                        }
                        sendDatatoDB(categoriesAll, data, conexion);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } /*catch (SQLException ex) {
                        ex.printStackTrace();
                    }*/
                }else{
                    JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo.");
                }
                
            }
        });
 
        StackPane root = new StackPane();
        root.getChildren().add(abrirBtn);
        root.getChildren().add(loadingBar);
        root.setAlignment(loadingBar, Pos.BOTTOM_CENTER);
        
        Scene scene = new Scene(root, 300, 250);
        
        escenarioPrincipal.setTitle("ExcelToDB");
        escenarioPrincipal.setScene(scene);
        escenarioPrincipal.show();
    }
    
    private String getYear(String data){
        String[] splRes = data.split("[()]");
        return splRes[splRes.length - 2];
    }
    
    private String getName(String data){
        String[] splRes = data.split("[(]");
        String retVal = "";
        if(splRes.length > 2){       
            retVal = splRes[0] + splRes[1];
        }else{
            retVal = splRes[0];
        }
        return retVal;
    }
    
    private void sendDatatoDB(Set Categories, ArrayList<Movie> Movies, Conexion conexion){
        ArrayList<Category> categoriesArr = new ArrayList<Category>();
        for(Object cat : Categories){
            try {
                conexion.getConexionGlobal().createStatement().execute(String.format("INSERT INTO Categories (CategoryName) VALUES (%s);", "\"" + cat + "\""));
                System.out.println(String.valueOf(cat));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try{
            ResultSet categoriesFromDB = conexion.getConexionGlobal().createStatement().executeQuery(String.format("SELECT * FROM Categories;"));
            while(categoriesFromDB.next()){
                categoriesArr.add(new Category(categoriesFromDB.getInt("Id"), categoriesFromDB.getString("CategoryName")));
            }
            for(Movie movie : Movies){
                conexion.getConexionGlobal().createStatement().execute(String.format("INSERT INTO Movies (Id, Name, Year) VALUES (%d, %s, %s);", movie.getId(), "\"" + movie.getName() + "\"", "\"" + movie.getYear() + "\"")); 
                for(String categoryStr : getCategoriesFromMovie(movie.getCategories())){
                    Category categoryMovie = new Category(getCategoryId(categoriesArr, categoryStr), categoryStr);
                    conexion.getConexionGlobal().createStatement().execute(String.format("INSERT INTO MoviesCategories (IdMovie, IdCategory) VALUES (%d, %d);", movie.getId(), getCategoryId(categoriesArr, categoryMovie.getCategoryName())));
                    System.out.println(categoryStr);
                }
                //System.out.println(getCategoriesFromMovie(movie.getCategories())[0]);
                //System.out.println(getCategoryId(categoriesArr, getCategoriesFromMovie(movie.getCategories())[0]));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
    }
    
    private String[] getCategoriesFromMovie(String catStr){
        return catStr.split("\\|");
    }
    
    private int getCategoryId(ArrayList<Category> Categories, String searchVal){
        int selId = 0;
        for(Category cat : Categories){
            if(cat.getCategoryName().equals(searchVal)){
                selId = cat.getId();
                break;
            }else{
                selId = 0;
                //break;
            }
        }
        return selId;
    }
    
    //private String orderName(String name){
        //String[] splRes = name.split(" ");
        //return 
    //}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
