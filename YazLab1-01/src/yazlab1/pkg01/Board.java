/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yazlab1.pkg01;


import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
/**
 *
 * @author arda_
 */
public class Board {
    
    private double boardHeight;
    private double boardWidth;
    private double rectangleHeight;
    private double rectangleWidth;
    private int numCols;
    private int numRows;
  
    private AnchorPane gameboard = null;
    private Color lightColor;
    private Color darkColor;
    private Color money;
    private Color stepped;
    
    public Board(int numRows, int numCols, double boardWidth, double boardHeight) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }
    
    public Board(int numRows, int numCols, double boardWidth, double boardHeight, Color lightColor, Color darkColor, Color money, Color stepped) {
        this(numRows, numCols, boardWidth, boardHeight);
        this.lightColor = lightColor;
        this.darkColor = darkColor;
        this.money = money;
        this.stepped = stepped;
    }
    
    public AnchorPane build() {
        // Calculate the max width and height of the board squares using the 
        // smallest board dimension
        if (boardWidth < boardHeight) {
            rectangleWidth = boardWidth / numCols;
            rectangleHeight = boardWidth / numRows;
        }
        else {
           rectangleWidth = boardHeight / numCols;
           rectangleHeight = boardHeight / numRows; 
        }
        
        gameboard = new AnchorPane();
                 
        // Create board squares
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                
                // Create board square
                Rectangle boardSquare = new Rectangle();
                boardSquare.setWidth(rectangleWidth);
                boardSquare.setHeight(rectangleHeight);
                boardSquare.setX(rectangleWidth * j);
                boardSquare.setY(rectangleHeight * i);
            
                // Assign board square color
                if ((j % 2 == 0 && i % 2 != 0) || (j % 2 != 0 && i % 2 == 0)) {
                    boardSquare.setFill(darkColor);
                }
                else {
                    boardSquare.setFill(lightColor);
                }
            
                // Add board square to anchor pane
                gameboard.getChildren().add(boardSquare);
            }
        }            
        return gameboard;
    }
    
    public AnchorPane getBoard() {
        return gameboard;
    }
    
    public int getNumRows() {
        return numRows;
    }
    
    public int getNumCols() {
        return numCols;
    }
    
    public double getWidth() {
        return boardWidth;
    }
    
    public double getHeight() {
        return boardHeight;
    }
    
    public Color getLightColor() {
        return lightColor;
    }
    
    public Color getDarkColor() {
        return darkColor;
    }
    
    public Color getMoney() {
        return money;
    }
    
    public Color getStepped() {
        return stepped;
    }
    
    public double getRectangleWidth() {
        return rectangleWidth;
    }
    
    public double getRectangleHeight() {
        return rectangleHeight;
    }
    
}
