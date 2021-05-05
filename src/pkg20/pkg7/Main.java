/*
 * Project: java 2 exercise 20.7
 * Lauren Smith
 * 3/12/21 
 * Makes a game of hangman using javafx 
 */
package pkg20.pkg7;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application {
    hangmanPane pane; 
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        //makes a hangmanPane  
        pane=new hangmanPane(); 
        //lambda for when input is entered in the textField. runs the game method 
        //with textField input as the parameter 
        pane.tfInput.setOnAction(e->{
            try {
                game(pane.tfInput.getText());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Scene scene = new Scene(pane, 300, 250);
        
        primaryStage.setTitle("Hangman");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void game(String input) throws FileNotFoundException 
    { 
        //if player doesn't have game over by missing >=7
        if(pane.missed<7) 
        {
            //runs the testLetter method in the hangmanPane class
            //passing the textField input as the parameter
            pane.testLetter(input); 
        }  
        //if they've missed >=7 game over 
        else if(pane.missed>=7)  
        { 
            //sees if input==a for a new game 
            if(input.equals("a")) 
            { 
                //stops swinging animation 
                pane.ani.stop(); 
                //clears input box 
                pane.tfInput.clear();
                //runs reset method 
                pane.reset();  
                
            }
        } 
    } 
    
  
    
    
}
