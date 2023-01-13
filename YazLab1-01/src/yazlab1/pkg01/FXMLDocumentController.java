/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab1.pkg01;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author arda_
 */
public class FXMLDocumentController implements Initializable {
    
    private double boardHeight;
    private double boardWidth;
    private int numCols = 20;
    private int numRows = 20;
    
    private Color lightColor = Color.GREY;
    private Color darkColor = Color.GOLD;
    private Color money = Color.GOLD;
    private Color stepped = Color.DARKGRAY;
    
    private Scene scene;
    
    @FXML private AnchorPane anchorPane;
    @FXML private Menu gridMenu;
    @FXML private Menu colorsMenu;
    @FXML private MenuBar menuBar;
    @FXML private MenuItem defaultMenuItem;
    @FXML private MenuItem blueMenuItem;
    @FXML private VBox vBox;
    @FXML private VBox vBoxDisplayArea;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }    

    @FXML private void changeColor(ActionEvent event) {
        MenuItem menuItem = (MenuItem)(event.getSource());
        
        switch(menuItem.getId()) {
            case "defaultMenuItem":
                lightColor = Color.LIGHTGREY;
                darkColor = Color.DARKGRAY;
                money = Color.GOLD;
                stepped = Color.DARKGRAY;
                break;
            default:
                lightColor = Color.LIGHTGREY;
                darkColor = Color.GOLD;
                money = Color.GOLD;
                stepped = Color.DARKGRAY;
                break;
        }
        
        // Reset gameboard
        setGameBoard();
    }
    
    

    
    
    public void setGameBoard() {   
        boardWidth = vBox.getWidth();
        boardHeight = vBox.getHeight() - menuBar.getHeight();
        
        // Generate new gameboard
        Board board = new Board(numRows, numCols, boardWidth, boardHeight, lightColor, darkColor, money, stepped);
        AnchorPane gameboard = board.build();
        
        // Clear previous gameboard 
        anchorPane.getChildren().clear();
        
        // Calculate horizontal and vertical padding using remaining space
        double horizontalPadding = (boardWidth - (board.getRectangleWidth() * board.getNumCols())) / 2;
        double verticalPadding = (boardHeight - (board.getRectangleHeight() * board.getNumRows())) / 2;
        
        // Add padding
        Insets insets = new Insets(verticalPadding, horizontalPadding, verticalPadding, horizontalPadding);
        vBoxDisplayArea.setPadding(insets);
        
        // Set new gameboard configuration
        anchorPane.getChildren().addAll(gameboard); 
    }  
}
