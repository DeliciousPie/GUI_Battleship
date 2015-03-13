
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

public class TugBoatGUI extends Ship implements DisplayableCell
{  
    private final static int TUG_BOAT_SIZE = 2;
    private final static Image[] TUG_BOAT_IMAGES = 
        { new Image( "battleship/battTug1.gif"), 
          new Image( "battleship/battTug2.gif")};
    
    public TugBoatGUI()
    {
        shipPartHit = new boolean[ TUG_BOAT_SIZE ];
    }

    @Override
    public Image displayCell(int col)
    {
        Image cell = EmptyCell.START_CELL_IMAGE;
        // Determine difference from front of ship since x value is actual x value
        int relativeX =  col - this.col;
        if ( shipPartHit[relativeX] )
        {
            cell = TUG_BOAT_IMAGES[relativeX];
        }
        return cell;
    }
}
