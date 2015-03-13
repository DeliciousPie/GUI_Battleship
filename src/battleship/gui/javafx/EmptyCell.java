
/*
 *  [File header includes information about the file being submitted.]
 *  Date submitted:
 *  Assignment number:
 *  Course name:  COSC 190
 *  Instructors: Andrea Grzesina, Sharon McDonald
 *  File path and name: J:\CST\ACOSC190\
 */


package battleship.gui.javafx;

import java.util.Scanner;

import javafx.scene.image.Image;

import javax.swing.*;

/**
 *  Purpose: Starting template for a new program. Replace these comments with
 *  your own. Briefly describe the purpose of the class, how the class is used
 *  in the program and how it interacts with other classes.
 *
 * @author YOUR NAME AND CST NUMBER GO HERE
 */

public class EmptyCell implements DisplayableCell
{
    private boolean selected = false;
    
    public final static Image START_CELL_IMAGE = 
        new Image( "battleship/StartCell.gif" );
    public final static Image EMPTY_CELL_IMAGE = 
        new Image( "battleship/EmptyCell.gif" );
    

    public Image displayCell( int col )
    {
        Image image = START_CELL_IMAGE;
        if ( selected )
        {
            image = EMPTY_CELL_IMAGE;
        }
        return image;
    }


    @Override
    public void locationSelected(int row, int col)
    {
        // Location selected
        selected = true;
    }

}
