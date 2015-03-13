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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * @author SkynetP2P
 *
 */
public class Board extends Application
{
    private static final int NUM_SHIPS = 5;
    private static final int NUM_ROW = 10;
    private static final int NUM_COL = 10;
    private int row;
    private int col;
    private GridPane gridPane;
    private DisplayableCell[][] displayableCellGrid = new DisplayableCell[NUM_ROW][NUM_COL];//ship objects get set here
    private Ship[] fleet = new Ship[NUM_SHIPS];
    private int userGuess = 0;
    private Stage st;//holds stage object for using with action events
    private Button[][] buttonGrid = new Button[NUM_ROW][NUM_COL];//holds buttons
    
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
        
        startGame();
        //set a gridPane holding 10 by 10 grid of buttons
        board.setCenter(gridPane);
        
        //puts pane into scene & adds that to the stage
        stage.setScene(new Scene(board));
        stage.setWidth(700);
        stage.setHeight(550);
        stage.setResizable(false);
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
            //st.close();
            System.exit(0);
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
        
        TextField t1 = new TextField();//txt field to show time elapsed
        t1.setText("0.0");
        t1.setAlignment(Pos.CENTER);
        t1.setEditable(false);//sets field to not be able to type in
        
        Label guessed = new Label("Cells Guessed:");//label for guesses
        guessed.setFont(new Font("Courier", 18));
        guessed.setAlignment(Pos.CENTER);
        
        TextField t2 = new TextField();//txt field to hold # of guesses
        t2.setText("0");
        t2.setAlignment(Pos.CENTER);
        t2.setEditable(false);//sets field to not be able to type in
        
        Label bomb = new Label("Bomb Detector:");//label for bomb detect
        bomb.setFont(new Font("Courier", 18));
        bomb.setAlignment(Pos.CENTER);
        
        TextField t3 = new TextField();//txt field to hold bomb proximity
        t3.setText("UnKnown");
        t3.setAlignment(Pos.CENTER);
        t3.setEditable(false);//sets field to not be able to type in
        
        VBox row = new VBox();//VBox to add 3 lbls and 3 stackPanes
        row.getChildren().addAll(time, t1, guessed, t2, bomb, t3 );
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10);
        row.setPadding(new Insets(10, 10, 10, 10));
        return row;
    }
    /**
     * buttons of grid will hold pictures
     * @return grid pane containing 10 X 10 button grid
     */
    private void createGridPane()
    {
        gridPane = new GridPane();//holds 10 x 10 grid of buttons
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        
        for (int row = 0; row < 10; row++)
        {
            for (int col = 0; col < 10; col++)
            {
                this.row = row;
                this.col = col;
                
                buttonGrid[row][col] = new Button();
                gridPane.add(buttonGrid[row][col], col, row);
                
                buttonGrid[row][col].setOnAction(new buttonPicListener(row, col));
//                buttonGrid[row][col].setOnAction( (EventHandler<ActionEvent>) event -> {
//                    displayableCellGrid[this.row][this.col].locationSelected( this.row, this.col);
//                    setButtonGrid();
//                });
            }
        }
        
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
    
    private void startGame()
    {
        createFleet();
        randomStartLocation();
        createGrid();
        createGridPane();
        setButtonGrid();
        
        
    }
    public void createFleet()
    {
        for (int i = 0; i < fleet.length; i++)
        {
            // roll a random number
            int random = (int) (Math.random() * 3) + 1;
            // assign a specific ship in fleet based on num rolled
            switch (random)
            {
                case 1:
                    fleet[i] = new TugBoatGUI();
                    break;
                case 2:
                    fleet[i] = new SubmarineGUI();
                    break;
                case 3:
                    fleet[i] = new AirCraftCarrierGUI();
                    break;
            }
        }
    }
    public void randomStartLocation()
    {

        for (int i = 0; i < fleet.length; i++)
        {
            int randomCol;// will hold a randomly generated column number
            int randomRow;// will hold a randomly generated row number
            // check to make sure ship dosn't go off grid by length or overlap
            do
            {
                randomRow = (int) (Math.random() * 10);
                randomCol = (int) (Math.random() * 9);
            } while (fleet[i].getSize() + randomCol > 9 // true if over 9
                    || isOverLap(randomRow, randomCol, i));// true if overlap

            // sets start location for each ship in fleet
            fleet[i].setStartLocation(randomRow, randomCol);
            // sets individual fleet ships to grid
            for (int in = 0; in < fleet[i].getSize(); in++)
            {
                displayableCellGrid[randomRow][randomCol + in] = fleet[i];
            }
        }
    }
    public boolean isOverLap(int row, int col, int index)
    {
        boolean result = false;
        for (int i = 0; i < fleet[index].getSize() && result == false; i++)
        {
            if (displayableCellGrid[row][col + i] != null)// check for grid obj if one: true
            {
                result = displayableCellGrid[row][col + i] != null;// sets to true if grid full
            }
        }
        return result;
    }
    public void createGrid()
    {
        // loop through grid and fill each spot with Empty Space
        for (int row = 0; row < displayableCellGrid.length; row++)
        {
            for (int col = 0; col < displayableCellGrid[row].length; col++)
            {
                if (displayableCellGrid[row][col] == null)
                {
                    displayableCellGrid[row][col] = new EmptyCell();
                }

            }
        }
    }
    public void setButtonGrid()
    {
        for (int row = 0; row < displayableCellGrid.length; row++)
        {
            for (int col = 0; col < displayableCellGrid[row].length; col++)
            {
                buttonGrid[row][col].setGraphic( new ImageView( displayableCellGrid[row][col].displayCell( col ) ) );
            }
        }
    }
    public class buttonPicListener implements EventHandler<ActionEvent>
    {
        private int row;
        private int col;
        public buttonPicListener(int row, int col)
        {
            this.row = row;
            this.col = col;
        }
        /* (non-Javadoc)
         * @see javafx.event.EventHandler#handle(javafx.event.Event)
         */
        @Override
        public void handle(ActionEvent event)
        {
            displayableCellGrid[this.row][this.col].locationSelected( this.row, this.col);
            setButtonGrid();
        }
        
    }
}
