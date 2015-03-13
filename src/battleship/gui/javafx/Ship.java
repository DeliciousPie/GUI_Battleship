
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

import javax.swing.ImageIcon;

/**
 *  Purpose: Starting template for a new program. Replace these comments with
 *  your own. Briefly describe the purpose of the class, how the class is used
 *  in the program and how it interacts with other classes.
 *
 * @author YOUR NAME AND CST NUMBER GO HERE
 */

abstract public class Ship implements DisplayableCell
{
    protected int col;
    private int row;
    protected boolean[] shipPartHit;
    
    public final static int TUG_BOAT = 0;
    public final static int SUBMARINE = 1;
    public final static int AIRCRAFT_CARRIER = 2;
    
    public int getSize()
    {
        return shipPartHit.length;
    }
    
    public void setStartLocation( int row, int col )
    {
       this.row = row;
       this.col = col;
    }
    
    public boolean isComplete()
    {
        boolean complete = true;
        for( int i = 0; i < shipPartHit.length && complete; i++ )
        {
            if ( !shipPartHit[i] )
            {
                complete = false;
            }
        }
        return complete;
    }
    
    public void locationSelected(int row, int col)
    {
        if (  row == this.row )
        {
            int diff =  col - this.col;
            
            if ( diff < shipPartHit.length )
            {
                shipPartHit[diff] = true;
            }
        }
        
    }
}
