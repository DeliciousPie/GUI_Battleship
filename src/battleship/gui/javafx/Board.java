/*
 *  Keith Warman, Wes Reid, CST148, CST142
 *  Date submitted: mar 24, 2015
 *  Assignment number: 3
 *  Course name:  COSC 190
 *  Instructors: Sharon McDonald, Nelson Wong
 * 
 */

package battleship.gui.javafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author SkynetP2P
 *
 */
public class Board extends Application
{
    private Stage st;//holds stage object for using with action events
    
    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     * main stage that holds all the nodes & title 
     * also shows it & sets a global variable for manipulation
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        //main pane to be set to scene
        BorderPane board = new BorderPane();
        
        //sets a VBox with a menu bar & a title
        board.setTop(createTop());
        
        //set a HBox with two buttons
        board.setBottom(createBottomButtons());
        
        //set a VBox with some updating messages
        board.setRight(createSideMessage());
        
        //set a gridPane holding 10 by 10 grid of buttons
        board.setCenter(createGridPane());
        
        //puts pane into scene & adds that to the stage
        stage.setScene(new Scene(board));
        stage.show();//show the stage
        stage.setTitle("CST Battleship");//creates title for stage
        st = stage;//sets global variable for manipulation
    }
    
    /**
     * @param args
     * calls launch method from application
     */
    public static void main(String[] args)
    {
        launch();

    }
    /**
     * 
     * @return HBox containing two buttons for the bottom
     */
    private HBox createBottomButtons()
    {
        Button newGame = new Button("New Game");//to start new game
        Button close = new Button("Close");//to close game
        //sets button to close stage
        close.setOnAction((ActionEvent)->{
            st.close();
        });
        
        HBox row = new HBox();//creates an HBox to add buttons 
        row.getChildren().addAll(newGame, close);
        row.setSpacing(20);
        row.setAlignment(Pos.CENTER); 
        row.setPadding(new Insets(10, 10, 10, 10));
        return row;
    }
    
    /**
     * messages shall be updated as game plays
     * @return a VBox holding right side messages
     */
    private VBox createSideMessage()
    {
        Label time = new Label("Elapsed Game Time:");//label for game time
        time.setFont(new Font("Courier", 18));
        time.setAlignment(Pos.CENTER);
        
        Rectangle r1 = new Rectangle(150, 20);//rect to show text on
        r1.setStyle("-fx-Stroke: Black");//creates a border 
        r1.setFill(Color.WHITE);
        Text txt1 = new Text("0.0");//text for placement on rect
        StackPane stack1 = new StackPane();//adds text on top rect
        stack1.getChildren().addAll(r1, txt1);
        
        Label guessed = new Label("Cells Guessed:");//label for guesses
        guessed.setFont(new Font("Courier", 18));
        guessed.setAlignment(Pos.CENTER);
        
        Rectangle r2 = new Rectangle(150, 20);//rect to show text on
        r2.setStyle("-fx-Stroke: Black");
        r2.setFill(Color.WHITE);
        Text txt2 = new Text("0");
        StackPane stack2 = new StackPane();//adds text on top rect
        stack2.getChildren().addAll(r2, txt2);
        
        Label bomb = new Label("Bomb Detector:");//label for bomb detect
        bomb.setFont(new Font("Courier", 18));
        bomb.setAlignment(Pos.CENTER);
        
        Rectangle r3 = new Rectangle(150, 20);//rect to show text on
        r3.setStyle("-fx-Stroke: Black");
        r3.setFill(Color.WHITE);
        Text txt3 = new Text("Unknown");
        StackPane stack3 = new StackPane();//adds text on top rect
        stack3.getChildren().addAll(r3, txt3);
        
        VBox row = new VBox();//VBox to add 3 lbls and 3 stackPanes
        row.getChildren().addAll(time, stack1, guessed, stack2, bomb, stack3 );
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10);
        row.setPadding(new Insets(10, 10, 10, 10));
        return row;
    }
    /**
     * buttons of grid will hold pictures
     * @return grid pane containing 10 X 10 button grid
     */
    private GridPane createGridPane()
    {
        GridPane grid = new GridPane();//holds 10 x 10 grid of buttons
        grid.setPadding(new Insets(10, 10, 10, 10));
        
        for (int row = 0; row < 10; row++)
        {
            for (int col = 0; col < 10; col++)
            {
                grid.add(new Button(row + "" +col), col, row);
            }
        }
        return grid;
    }
    
    /**
     * contains one menu with two items
     * @return menuBar for top most VBox
     */
    private  MenuBar createMenu()
    {
        MenuBar bar = new MenuBar();//bar 
        
        Menu gameMenu = new Menu("Game");//game menu
        MenuItem newGame = new MenuItem("New Game");//item to start new game
        MenuItem closeGame = new MenuItem("Close Game");//item to close game
        //tells button push close stage
        closeGame.setOnAction((ActionEvent)->{
            st.close();
        });
        gameMenu.getItems().addAll(newGame, closeGame);//adds items to menu
        
        bar.getMenus().addAll(gameMenu);//adds menu to bar
        return bar;
    }
    
    /**
     * 
     * @return VBox holding top menu bar & title lbl
     */
    private VBox createTop()
    {
        VBox row = new VBox();//to hold lbl & menu bar
        Label title = new Label("Welcome to CST Single Player BattleShip");
        title.setFont(new Font("Courier", 24));
        
        row.getChildren().addAll(createMenu(), title);//adds nodes
        row.setAlignment(Pos.CENTER);
        return row;
    }
}
