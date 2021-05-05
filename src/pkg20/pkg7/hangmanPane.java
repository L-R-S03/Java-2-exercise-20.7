/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg20.pkg7;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;


public class hangmanPane extends BorderPane {
    //declares items I need to be accessible throughout the whole class 
    PathTransition ani; 
//regular pane just for the hangman drawing itself 
    Pane drawing=new Pane(); 
    Pane man=new Pane(); 
    //string to display the word with *
    String gameWord=""; 
    //String ArrayList to hold all the letters in the word
    ArrayList<String> gameLetters=new ArrayList<String>();
    //string used to display missed letters 
    String display=""; 
    //label to display gameWord and display strings 
    Label word=new Label(); 
    Label progress=new Label(); 
    //int to keep track of number of missed letters 
    int missed=0; 
    //TextField to get user input (guessed letter)
    TextField tfInput=new TextField();
    //makes a boolean to check for success 
    boolean success=false; 
    //sets missedLetters string to messed letters ready to be followed by incorrect letters
    String missedLetters="Missed Letters: "; 
    //below makes each part of the hangman but doesn't add it to the pane 
    Line stand=new Line(110,30,110,40); 
    Circle head=new Circle(110,50,12); 
    Line leftArm=new Line(100,55,65,85); 
    Line rightArm=new Line(120,55,160,85); 
    Line body=new Line(110,60,110,120); 
    Line leftLeg=new Line(110,120,65,150); 
    Line rightLeg=new Line(110,120,160,150); 

    //constructor for the class that draws the stand and runs the setWords method
    public hangmanPane() throws FileNotFoundException 
    {
        reset(); 
    } 
    
    //method that reads word list into the file and pulls a random word from the file 
    //to guess. Sets up other visuals of labels and textboxt for the pane as well
    public void setWords() throws FileNotFoundException 
    { 
        //ArrayList to hold string of hangman words for the game 
        ArrayList<String> words=new ArrayList<String>(); 
        //opens file I made full of words 
        File file=new File("words.txt"); 
        //scanner to read the file set to the file 
        Scanner read=new Scanner(file); 
        //while the scanner has more to read 
        while(read.hasNext()) 
        {
            //read the word 
            String word=read.next();
            //add it to the list 
            words.add(word); 
        } 
        
        //once list is filled sets the word being used for the game by generating a 
        //random int in range of the list size and sets the word to the word 
        //at that randomlly generated index 
        gameWord=words.get((int)(Math.random()*words.size()));
        
       
        for(int i=0; i<gameWord.length(); i++) 
        {
            //adds each letter to an arraylist by using charAt
            gameLetters.add(gameWord.substring(i,i+1)); 
             //generates display string of * 
            display+="*"; 
        } 
        
        //sets text of lebel to guess a word followed by the word with * for letters
        word.setText("Guess a word: "+display); 
        
        //ALL THESE LINES BELOW ARE JUST FORMATTING BOTH LABELS CORRECTLY ON THE PANE 
        //makes a hbox for the textField input as well as a label behind describing what to do
        HBox hbox3=new HBox(); 
        //creates and adds label to hbox. 
        hbox3.getChildren().add(new Label("Guess a letter:"));
        //adds textfield to the hbox
        hbox3.getChildren().add(tfInput);
        //sets the hbox to the bottomLeft of the hbox 
        tfInput.setAlignment(Pos.BOTTOM_LEFT); 
        //makes textField only have one collumn as it just needs to fit a letter
        tfInput.setPrefColumnCount(1);
        //sets alignment of hbox items to the center
        hbox3.setAlignment(Pos.CENTER); 
        //sets the hbox to the top of the borderPane 
        setTop(hbox3); 
        //Makes a VBox to hold both labels in. Makes an Hbox for label 1 which holds hidden word
        VBox vbox=new VBox();
        HBox hbox=new HBox();
        //adds the word label to the hbox 
        hbox.getChildren().add(word); 
        //sets label to the center of the hbox 
        hbox.setAlignment(Pos.CENTER);
        //adds padding above and below the hbox 
        hbox.setPadding(new Insets(5,0,5,0));
        //adds the hbox to the vbox 
        vbox.getChildren().add(hbox);
        //repeats the same steps for label 2 which holds incorrect letters
        HBox hbox2=new HBox(); 
        hbox2.getChildren().add(progress); 
        
        hbox2.setAlignment(Pos.CENTER);
        //only adds padding on the bottom as opposed to the other label which has bottom and top
        hbox2.setPadding(new Insets(0,0,5,0));
        //adds the hbox to the vbox
        vbox.getChildren().add(hbox2); 
        //sets the vbox to the bottom of the borderpane 
        setBottom(vbox); 
        
    } 
    
    //method that takes userInput from textField as a parameter and sees if it is a 
    //correct or incorrect letter
    public void testLetter(String input) 
    {
        //clears textField for next letter 
        tfInput.clear(); 
        //sees if letter is in the word by checking the frequency of the input in the 
        //gameLetters arrayList 
        int count=Collections.frequency(gameLetters, input); 
        //if count is greater than 0 the letter is in the word 
        if(count>0) 
        {
            //run correctLetter method passing the input as a parameter
            correctLetter(input); 
        } 
        //else, the letter is incorrect 
        else
        {
            //run incorrectLetter method passing the input as a parameter 
            incorrectLetter(input); 
        }
    }
    //method for if entered letter is correct with input letter as param
    public void correctLetter(String input) 
    { 
        //string for the new display based on input 
        String updateDisplay=""; 
        //loops through the length of the word 
        for(int i=0; i<gameWord.length(); i++) 
        { 
            //if the letter the loop is at in guessWord doesn't equal input 
            if(!(gameWord.substring(i,i+1).equals(input))) 
            {
                //if the display string is currently an *
                if(display.substring(i,i+1).equals("*")) 
                {
                    //add an * to updated display as it doesn't change 
                    updateDisplay+="*"; 
                } 
                else 
                    //if the letter isn't an * and already has a letter there 
                {
                    //add the letter that's already there to update display
                    updateDisplay+=display.substring(i,i+1); 
                }
            } 
            //if the letter does equal input
            else 
            {
                //add the input to that spot in updateDisplay
                updateDisplay+=input; 
            } 
        }
        //updates the label to updateDisplay 
        word.setText("Guess a word: "+ updateDisplay); 
        //sets display=updateDisplay so it is up to date for next letter 
        display=updateDisplay; 
        
       } 
    
    
    //method called with a letter is incorrect with input as a parameter
    public void incorrectLetter(String input) 
    {
        //increments the missed variable 
        missed++; 
        //switch statement based off the value of missed int 
        switch(missed) 
        {
            //first incorrect letter 
            case 1: 
            {
                //adds the stand to the man pane 
                drawing.getChildren().add(stand); 
                //adds the missed letter to the missedLetter string 
                missedLetters+=input; 
                //updates progress label text to the new missedLetters string 
                progress.setText(missedLetters); 
            }
            break; 
            //second missed letter 
            case 2: 
            { 
                //sets the head of hangman to have a black outline and white fill
                head.setFill(javafx.scene.paint.Color.WHITE);
                head.setStroke(javafx.scene.paint.Color.BLACK);
                //adds the head to the man pane 
                man.getChildren().add(head); 
                //adds the input to the missedLetters string 
                missedLetters+=input; 
                //updates progress label with updated missedLetters string 
                progress.setText(missedLetters); 
            }
            break; 
            //third missed letter 
            case 3: 
            {
                //adds the leftArm to the man pane 
                man.getChildren().add(leftArm); 
                //adds the input to the missedLetters string 
                missedLetters+=input; 
                //updates progress label to missedLetters string 
                progress.setText(missedLetters); 
            }
            break; 
            //fourth missed letter 
            case 4: 
            {
                //adds the rightArm to the pane 
                man.getChildren().add(rightArm);
                //adds the input to the missedLetters string 
                missedLetters+=input; 
                //sets the progress label to missedLetters string
                progress.setText(missedLetters); 
            } 
            break; 
            //fifth missed letter 
            case 5: 
            {
              //adds the body to the man pane 
              man.getChildren().add(body); 
              //adds the input to the missedLetters string 
                missedLetters+=input; 
              //sets the progress label to missedLetters string
                progress.setText(missedLetters); 
            } 
            break; 
            //sixth missed letter 
            case 6: 
            {
                //adds the leftLeg to the man pane 
                man.getChildren().add(leftLeg);
                //adds the input to the missedLetters string 
                missedLetters+=input; 
                //sets the progress label to missedLetters string
                progress.setText(missedLetters); 
            } 
            break; 
            //seventh missed letter 
            case 7: 
            {
                //adds the rightLeg to the man pane 
                man.getChildren().add(rightLeg);
                //displays what the word is 
                word.setText("The word is: "+gameWord);
                //explains to hit a to continue the game 
                progress.setText("To continue the game, enter a");
                animate(); 
                
            }
        }
    } 
 
    //method for both constructor and resetting for new game after game over 
    public void reset() throws FileNotFoundException 
    {
        //resets missed and displays 
        missed=0; 
        display=""; 
        //clears all children of the main pane 
        getChildren().clear(); 
        //clears children of pane with just hangman 
        man.getChildren().clear(); 
        //hangs pane with the stand and man pane in it 
        drawing.getChildren().clear();
        //resets progress and text on both labels 
        progress.setText(""); 
        word.setText("Guess a word: "); 
        //draws arc for the bottom of the stand 
        Arc arc=new Arc(35,240,20,20,-180,-180);
        //sets visual prefs like an open ArcType with a black outline and white fill
        arc.setType(ArcType.OPEN); 
        arc.setStroke(javafx.scene.paint.Color.BLACK);
        arc.setFill(javafx.scene.paint.Color.WHITE);  
        //adds the arc to the drawing pane 
        drawing.getChildren().add(arc); 
        //creates and adds the two lines to the drawing pane 
        drawing.getChildren().add(new Line(33,220,33,30)); 
        drawing.getChildren().add(new Line(33,30,110,30)); 
        drawing.getChildren().add(man); 
        //adds the drawing pane to the main borderPane 
        getChildren().add(drawing); 
        //runs setWords method
        setWords(); 
    }
    //method used to animate swinging when game over 
    public void animate() 
    {
        //makes an arch for the path to appear as swinging 
        Arc path=new Arc(100,55,5,5,180,180); 
        path.setType(ArcType.OPEN); 
        //sets path to transparent so it cant be seen 
        path.setStroke(javafx.scene.paint.Color.TRANSPARENT);
        path.setFill(javafx.scene.paint.Color.TRANSPARENT);
        //adds the path to the pane 
        getChildren().add(path);
        //makes pathTransition set to the arc path and the man pane 
        ani=new PathTransition(Duration.millis(10000),path,man); 
        //runs indefinitely 
        ani.setCycleCount(Timeline.INDEFINITE); 
        //will reverse to appear swinging 
        ani.setAutoReverse(true);
        //moves slowly 
        ani.setRate(5); 
        //plays the animation
        ani.play(); 
    }
   
    
   
}
