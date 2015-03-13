/*
 *  Keith Warman, Wes Reid, CST148, CST142
 *  Date submitted:mar 24, 2015
 *  Assignment number: 3
 *  Course name:  COSC 190
 *  Instructors: Sharon McDonald, Nelson Wong
 * 
 */

package battleship.gui.javafx;

import javafx.scene.image.Image;

/**
 * @author SkynetP2P
 *
 */
public class Bomb implements DisplayableCell
{
    private int row;//holds the row selected
    private int col;//holds the col selected
    //holds t/f value to determine if location has been selected by user
    private boolean selected = false;
    //image object holding pic of bomb
    private static final Image BOMB_IMAGE = new Image("battleship/bomb.gif");
    /**
     * constructor to set location of the bomb object
     * @param row holds row coming in used for setting position of bomb
     * @param col holds col coming in used for setting position of bomb
     */
    public Bomb(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    /* (non-Javadoc)
     * @see battleship.gui.javafx.DisplayableCell#displayCell(int)
     * shows pic of startcell if selected is false
     * image of bomb is displayed if selected is true
     */
    @Override
    public Image displayCell(int col)
    {
        Image image = new Image("battleship/StartCell");
        if ( selected )
        {
            image = BOMB_IMAGE;
        }
        return image;
        
    }

    /* (non-Javadoc)
     * @see battleship.gui.javafx.DisplayableCell#locationSelected(int, int)
     * sets selected to true if bomb has been found
     */
    @Override
    public void locationSelected(int row, int col)
    {
        selected = true;
        
    }
    /**
     * returns t/f value depending on selected value
     * @return true if selected is true other wise false
     */
    public boolean isExploded()
    {
        return selected;
    }
    /**
     * returns a string with a message indicating the proximity to the bomb
     * @param row holds selected row for user fire
     * @param col holds selected col for user fire
     * @return string based on the proximity to the bomb object
     */
    public String proximity(int row, int col)
    {
        String result = "";
        if(row == this.row + 2 || row == this.row - 2 || col == this.col + 2 || 
                col == this.col -2)
        {
            result = "Caution - you are close";
        }
        else if(row == this.row + 1 || row == this.row - 1 || 
                col == this.col + 1 || col == this.col -1)
        {
            result = "Extreme Danger - step back!";
        }
        else if(row == this.row && col == this.col)
        {
            result = "BOOM!";
        }
        else
        {
            result = "Safe";
        }
        return result;
    }
}
