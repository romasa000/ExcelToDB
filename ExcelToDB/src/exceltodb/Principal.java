/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceltodb;

import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

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
        
        FileChooser lectorDeArchivos = new FileChooser();
        lectorDeArchivos.setTitle("Abrir...");
        
        abrirBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                archivoSeleccionado = lectorDeArchivos.showOpenDialog(escenarioPrincipal);
                if(!archivoSeleccionado.equals(null)){
                    System.out.println(archivoSeleccionado.getName());
                }else{
                    JOptionPane.showMessageDialog(null, "No seleccionó ningún archivo.");
                }
                
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(abrirBtn);
        
        Scene scene = new Scene(root, 300, 250);
        
        escenarioPrincipal.setTitle("ExcelToDB");
        escenarioPrincipal.setScene(scene);
        escenarioPrincipal.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
