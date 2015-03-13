
/*
 *  [File header includes information about the file being submitted.]
 *  Date submitted:
 *  Assignment number:
 *  Course name:  COSC 190
 *  Instructors: Andrea Grzesina, Sharon McDonald
 *  File path and name: J:\CST\ACOSC190\
 */


package battleship.gui.javafx;

import javafx.scene.image.Image;

/**
 *  Purpose: Starting template for a new program. Replace these comments with
 *  your own. Briefly describe the purpose of the class, how the class is used
 *  in the program and how it interacts with other classes.
 *
 * @author YOUR NAME AND CST NUMBER GO HERE
 */

public class SubmarineGUI extends Ship implements DisplayableCell
{
    private final static int SUBMARINE_BOAT_SIZE = 3;
    private final static Image[] SUBMARINE_BOAT_IMAGES = 
        { new Image( "battleship/battSub1.gif"), 
          new Image( "battleship/battSub2.gif"), 
          new Image( "battleship/battSub3.gif")};
    
    public SubmarineGUI()
    {
        shipPartHit = new boolean[SUBMARINE_BOAT_SIZE];
    }
    

    @Override
    public Image displayCell(int col)
    {
        Image cell = EmptyCell.START_CELL_IMAGE;
        // Determine difference from front of ship since x value is actual x value
        int relativeX =  col - this.col;
        if ( shipPartHit[relativeX] )
        {
            cell = SUBMARINE_BOAT_IMAGES[relativeX];
        }
        return cell;
    }

}
