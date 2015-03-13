
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

import javax.swing.ImageIcon;

/**
 *  Purpose: Starting template for a new program. Replace these comments with
 *  your own. Briefly describe the purpose of the class, how the class is used
 *  in the program and how it interacts with other classes.
 *
 * @author YOUR NAME AND CST NUMBER GO HERE
 */

public class AirCraftCarrierGUI extends Ship implements DisplayableCell
{
    private final static int ACC_BOAT_SIZE = 4;
    private final static Image[] ACC_BOAT_IMAGES = 
    { new Image( "battleship/battAirC1.gif") ,
        new Image( "battleship/battAirC2.gif"), 
        new Image( "battleship/battAirC3.gif"), 
        new Image( "battleship/battAirC4.gif")};
    
    public AirCraftCarrierGUI()
    {
        shipPartHit = new boolean[ACC_BOAT_SIZE];
    }
    

    @Override
    public Image displayCell(int col)
    {
        Image cell = EmptyCell.START_CELL_IMAGE;
        // Determine difference from front of ship since x value is actual x value
        int relativeX =  col - this.col;
        if ( shipPartHit[relativeX] )
        {
            cell = ACC_BOAT_IMAGES[relativeX];
        }
        return cell;
    }

}
