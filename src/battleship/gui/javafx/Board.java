/*
 *  Keith Warman, Wes Reid, CST148, CST142
 *  Date submitted: mar 24, 2015
 *  Assignment number: 3
 *  Course name:  COSC 190
 *  Instructors: Sharon McDonald, Nelson Wong
 * 
 */

package battleship.gui.javafx;




import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Keith Warman, Wes Reid
 *main board & game play
 */
public class Board extends Application
{
    private static final int NUM_SHIPS = 5;//number of ships for fleet
    private static final int NUM_ROW = 10;//number of rows in 2d grids
    private static final int NUM_COL = 10;//number of cols in 2d grids
    //private GridPane gridPane;//may not need
    //ship objects get set here and bomb object
    private DisplayableCell[][] displayableCellGrid 
        = new DisplayableCell[NUM_ROW][NUM_COL];
    private Ship[] fleet = new Ship[NUM_SHIPS];//ship fleet of 5 gets set here
    private int userGuess = 0;//holds number of user guesses
    private Stage st;//holds stage object for using with action events
    private Button[][] buttonGrid = new Button[NUM_ROW][NUM_COL];//holds buttons
    private TextField t1;//holds & returns game time
    private TextField t2;//holds & returns the user guess total
    private TextField t3;//holds & returns the bomb proximity message
    //private boolean exploded = false;//holds t/f for bomb check
    private Bomb bomb;//holds the bomb object
    private boolean gameOver = false;
    private int gameTime = 0; //The number of seconds that have elapsed since starting the game
    private Timeline timeline; //Timeline to count the number of seconds player has been playing
    private Label time; //experiment
    private boolean hasTimerStarted = false;
    
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
        stage.setWidth(700);
        stage.setHeight(550);
        stage.setResizable(false);
        stage.show();//show the stage
        stage.setTitle("CST Battleship");//creates title for stage
        st = stage;//sets global variable for manipulation
        startGame();   
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
     * purpose is to provide two buttons to start new game or close window/game
     * @return HBox containing two buttons for the bottom
     */
    private HBox createBottomButtons()
    {
        Button newGame = new Button("New Game");//to start new game
        newGame.setOnAction((ActionEvent)->{
            startGame();
        });
        Button close = new Button("Close");//to close game
        //sets button to close stage
        close.setOnAction((ActionEvent)->{
            //st.close();//another way to do same below
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
     * purpose is to display the main portion of the GUI
     * messages shall be updated as game plays
     * @return a VBox holding right side messages
     */
    private VBox createSideMessage()
    {
    	//removed Label time = new....
        time = new Label("Elapsed Game Time:");//label for game time
        time.setFont(new Font("Courier", 18));
        time.setAlignment(Pos.CENTER);
        
        TextField t1 = new TextField();//txt field to show time elapsed
        t1.setText(""+ gameTime);
        t1.setAlignment(Pos.CENTER);
        t1.setEditable(false);//sets field to not be able to type in
        
        Label guessed = new Label("Cells Guessed:");//label for guesses
        guessed.setFont(new Font("Courier", 18));
        guessed.setAlignment(Pos.CENTER);
        
        t2 = new TextField();//txt field to hold # of guesses
        t2.setText("" + userGuess);
        t2.setAlignment(Pos.CENTER);
        t2.setEditable(false);//sets field to not be able to type in
        
        Label bomb = new Label("Bomb Detector:");//label for bomb detect
        bomb.setFont(new Font("Courier", 18));
        bomb.setAlignment(Pos.CENTER);
        
        t3 = new TextField();//txt field to hold bomb proximity
        t3.setText("UnKnown");
        t3.setAlignment(Pos.CENTER);
        t3.setFont(new Font("Courier", 11));     
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
    private GridPane createGridPane()
    {
        GridPane gridPane = new GridPane();//holds 10 x 10 grid of buttons
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        
        for (int row = 0; row < 10; row++)
        {
            for (int col = 0; col < 10; col++)
            { 
                //create buttons and sets to 2d button array
                buttonGrid[row][col] = new Button();
                //adds new button object to gridPane at appropriate space
                gridPane.add(buttonGrid[row][col], col, row);//
                //creates new listener for each button setting constructors 
                buttonGrid[row][col].setOnAction(new buttonPicListener(row, col));
            }
        }
        return gridPane;
    }
    
    /**
     * Purpose holds 2 menu items to start new game or close game
     * contains one menu with two items
     * @return menuBar for top most VBox
     */
    private  MenuBar createMenu()
    {
        MenuBar bar = new MenuBar();//bar 
        
        Menu gameMenu = new Menu("Game");//game menu
        MenuItem newGame = new MenuItem("New Game");//item to start new game
        newGame.setOnAction((ActionEvent)->{
                startGame();
        });
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
     * container to place two nodes at top of borderpane
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
    /**
     * runs the main functions of the game when called
     */
    private void startGame()
    {
        clearGame();//sets attributes of game back to beginning
        createFleet();//creates unique set of 5 ships
        createBomb();//creates bomb & places it on 2d displayableCell array
        randomStartLocation();//places ships on unique locations
         fillGrid();//fills in all unfilled spaces
        //createGridPane();
        setButtonGridPics();//puts pics on buttons
         
    }
    /**
     * Purpose creates a unique set of 5 ships at random
     */
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
    /**
     * Purpose Creates bomb object & sets that object to 
     * the displayableCell array at an un used location
     */
    public void createBomb()
    {
        int randomRow = (int) (Math.random() * 10);
        int randomCol = (int) (Math.random() * 10);
        if (!ifOccupied(randomRow, randomCol))
        {
            bomb = new Bomb(randomRow, randomCol);
            displayableCellGrid[randomRow][randomCol] = bomb;
        } 
    }
    /**
     * Purpose is to select an unused set of spaces on board to set ships to
     * 2d displayableCell array set
     */
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
    /**
     * 
     * @param row used to check row
     * @param col used to check col
     * @param index used for size of ship
     * @return true if there is an occupied space
     */
    public boolean isOverLap(int row, int col, int index)
    {
        boolean result = false;
        for (int i = 0; i < fleet[index].getSize() && result == false; i++)
        {
            // check for grid obj if one: true
            if (displayableCellGrid[row][col + i] != null)
            {
                // sets to true if grid full
                result = displayableCellGrid[row][col + i] != null;
            }
        }
        return result;
    }
    /**
     * 
     * @param row used to check row
     * @param col used to check col
     * @return true if space occupied
     */
    public boolean ifOccupied(int row, int col)
    {
        return displayableCellGrid[row][col] != null;
    }
    /**
     * Purpose fills in all empty spots on the 2d displayableCell array with an
     * emptyCell object for displaying empty cell pics
     */
    public void fillGrid()
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
    /**
     * Purpose is to set a 2d button array with button pics for game play
     * displayCell will show the appropriate pic depending on selected
     */
    public void setButtonGridPics()
    {
        for (int row = 0; row < displayableCellGrid.length; row++)
        {
            for (int col = 0; col < displayableCellGrid[row].length; col++)
            {
                buttonGrid[row][col].setGraphic( 
                        new ImageView( 
                        displayableCellGrid[row][col].displayCell( col ) ) );
            }
        }
    }
    /**
     * Purpose is to set all attributes to a starting state
     */
    public void clearGame()
    {
        for (int row = 0; row < displayableCellGrid.length; row++)
        {
            for (int col= 0; col < displayableCellGrid[row].length; col++)
            {
                displayableCellGrid[row][col] = null;
            }
        }
        userGuess = 0;//returns number of guesses to zero
        gameOver = false;
        //exploded = false;
        gameTime = 0;
        hasTimerStarted = false;
       
    }
    /**
     * Purpose checks to see if all ships are sunk or if bomb has been struck
     * @return true if all ships are sunk
     */
    public boolean isGameOver()
    {
        // if all ships are sunk this is true
        boolean result = false;
        int sunkShips = 0;
        for (int i = 0; i < fleet.length; i++)
        {
            if (fleet[i].isComplete())
            {
                sunkShips++;
            }
        }
        if (sunkShips == fleet.length)
        {
            result = true;
            gameOver = true;
        }
        return result;
    }
    public boolean isBombStruck()
    {
        if(bomb.isExploded())
        {
            gameOver = true;
        }
        return bomb.isExploded();
    }
    /**
     * this class allows for buttons in gridpane to keep track of individual row
     * and cols
     * @author Keith Warman, Wes Reid
     *
     */
    public class buttonPicListener implements EventHandler<ActionEvent>
    {
        private int row;
        private int col;
        /**
         * constructor sets row and col
         * @param row incoming gridpane row for button
         * @param col incoming gridpane col for button
         */
        public buttonPicListener(int row, int col)
        {
            this.row = row;
            this.col = col;
        }
        /* (non-Javadoc)
         * @see javafx.event.EventHandler#handle(javafx.event.Event)
         * called by button press
         */
        @Override
        public void handle(ActionEvent event)
        {    	     
        	       	
            //Timeline experiment
        	if(!hasTimerStarted)
        	{
        		beginTimeLine();
        	}

            boolean temp = false;
            if(!gameOver)
            {
            	
                //sets selected to true for that object
                displayableCellGrid[this.row][this.col].locationSelected( this.row, this.col);
                setButtonGridPics();//re-displays button graphics after change
                userGuess++;//updates user guesses
                t2.setText("" + userGuess);//changes text displayed for guesses
                t3.setText(bomb.proximity(this.row, this.col));
                if (isGameOver())
                {
                    wonPopup();
                }
                if (isBombStruck())
                {
                    lostPopup();
                }
                }
                else
                {
                    if(bomb.isExploded())
                    {
                        lostPopup();
                    }
                    else 
                    {
                        wonPopup();
                    }
                }

        } 
        
        //Start the timeline to update how long player has played
        private void beginTimeLine() 
        {
			hasTimerStarted = true;
        	timeline = new Timeline();
			timeline.setCycleCount(Timeline.INDEFINITE); //cycle until cancelled
			
			timeline.getKeyFrames().add(
					new KeyFrame( Duration.seconds( 1 ), 
							(ActionEvent) -> {
								if( gameOver )
								{
									timeline.stop();
								}
								else
								{
									gameTime++;
									time.setText("" + gameTime);
								}
							})
					);
			timeline.playFromStart();
			
			
			
			
		}
		/**
         * Purpose shows a popup with a condolence message upon losing
         */
        public void lostPopup()
        {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(st);
            VBox dialogVbox = new VBox();
            dialogVbox.getChildren().add(new Text("BOOOOOOOOMMMMM!!!!!!! "
                    + "You exploded the bomb.  Sorry, you lost."));
            Button b = new Button("OK");
            b.setAlignment(Pos.CENTER); 
            b.setOnAction((ActionEvent)->{
                dialog.close();
            });
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.getChildren().add(b);
            Scene dialogScene = new Scene(dialogVbox, 440, 70);
            dialog.setScene(dialogScene); 
            dialog.show();
            dialog.setTitle("Game Over!");
        }
        /**
         * Purpose is to display a popup message when the player wins
         */
        public void wonPopup()
        {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(st);
            VBox dialogVbox = new VBox();
            dialogVbox.getChildren().add(new Text("Congratulations - "
                    + "You got them all!  You needed to select " + userGuess 
                    + " spaces to find all ships"));
            Button b = new Button("OK");
            b.setAlignment(Pos.CENTER); 
            b.setOnAction((ActionEvent)->{
                dialog.close();
            });
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.getChildren().add(b);
            Scene dialogScene = new Scene(dialogVbox, 470, 70);
            dialog.setScene(dialogScene); 
            dialog.show();
            dialog.setTitle("Game Over!");
        }
    }
    
}
