/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dek8v5stopwatchfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author Dewi Endah Kharismawati
 * dek8v5 / 14231619
 */
public class FXMLDocumentController implements Initializable {
    
    //variable declaration @FXML for the things we need on the fxml file
    @FXML
    private Label second1;
    @FXML
    private Label second2;
    @FXML
    private Label minute1;
    @FXML
    private Label minute2;
    @FXML
    private Label hour1;
    @FXML
    private Label hour2;
    
    private double secondsElapsed = 0;
    private final double tickTimeInSeconds = 1.0;
    private final double angleDeltaPerSeconds = 6.0;
    
    @FXML
    private Button start;
    @FXML 
    private Button stop;
    @FXML
    private Button reset;
    @FXML
    private ImageView handImageView;
    
    private KeyFrame keyFrame;
    private int hourCount1= 0, hourCount2= 0, minuteCount1 = 0, minuteCount2 = 0, secondCount1=0, secondCount2=0;
    private Timeline timeLine;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startClick();
    } 
    
    
    private void startClick(){
        //to make start event only occur once even when we click it several times when it runs
            boolean running = false;
            if (timeLine != null) {
               if (timeLine.getStatus() == Animation.Status.RUNNING) {
                    running = true;
                    timeLine.stop();
                    }
            }
            //set the keyframe, to make the hand move as we expected
            keyFrame = new KeyFrame(Duration.millis(tickTimeInSeconds * 1000), actionEvent -> update());//durantion is a class.milles is a static method
            timeLine = new Timeline(keyFrame);
            timeLine.setCycleCount(Animation.INDEFINITE);
           
            
            if (running) {
            timeLine.play();
            }
    }
    
    private void update(){
        secondsElapsed++;
        //rotation of the handImageView is secondsElapsed*angleDeltaPerSeconds
        handImageView.setRotate(secondsElapsed * angleDeltaPerSeconds);
       
        //updating the second2 every seconds elapsed
        secondCount2++;
        
        //updating the sequence of digital stopwatch
        //after the first second2 hit 10, we send the increment into the second1
        if (secondCount2 == 10) {
            secondCount1++;
            secondCount2 = 0;
            //when the second hit 60s, we send 1 minute value to minute variable,
            if (secondCount1 == 6) {
                minuteCount2++;
                secondCount1 = 0;
                //when the minute is reach 10, we bumb the minute1 once and set the minute 2 back to 0 
                if (minuteCount2 == 10) {
                    minuteCount1++;
                    minuteCount2 = 0;
                    //the same thing works for minute 1 and bumping hour1
                    if(minuteCount1 ==6){
                        hourCount2++;
                        minuteCount1=0;
                        //last one, bump the hour1 when hour2 reaching 10
                        if(hourCount2 == 10){
                            hourCount1++;
                            hourCount2=0;
                            if (hourCount2 == 9 && hourCount1 == 9){
                                resetStopWatch();
                            }
                            hour1.setText(String.valueOf(hourCount1));
                        }
                        hour2.setText(String.valueOf(hourCount2));
                    }
                    minute1.setText(String.valueOf(minuteCount1));
                }
                minute2.setText(String.valueOf(minuteCount2));
            }
            second1.setText(String.valueOf(secondCount1));
        }
        second2.setText(String.valueOf(secondCount2));
        
    }
    
    //start button handler
    @FXML
    private void handleStartButton(){
        timeLine.play();
    }
    
    //stop button handler
    @FXML
    private void handleStopButton(){
        timeLine.stop();
    }
    
    //reset button handler
    @FXML
    private void handleResetButton(){
        timeLine.stop();
        resetStopWatch();
    }
    
    //reset method
    public void resetStopWatch() {
        secondsElapsed = 0;
        secondCount1 = secondCount2 = minuteCount1 = minuteCount2 = hourCount1 = hourCount2 = 0;
        handImageView.setRotate(0);
        
        hour1.setText("0");
        hour2.setText("0");
        minute1.setText("0");
        minute2.setText("0");
        second1.setText("0");
        second2.setText("0"); 
    }
    
}
