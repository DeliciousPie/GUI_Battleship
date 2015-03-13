/*
 *  [File header includes information about the file being submitted.]
 *  Date submitted:
 *  Assignment number:
 *  Course name:  COSC 190
 *  Instructors: Sharon McDonald, Nelson Wong
 * 
 */

package battleship.gui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * @author ins209
 *
 */
public class TryImage extends Application
{

    /* (non-Javadoc)
     * @see javafx.application.Application#start(javafx.stage.Stage)
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        FlowPane fp = new FlowPane();
        Button tryButton = new Button();
        Button t = new Button();
        TugBoatGUI tg = new TugBoatGUI();
        tg.setStartLocation(0,  0 );
        tg.locationSelected( 0, 0 );
        tg.locationSelected(0, 1);
        tryButton.setGraphic( new ImageView( tg.displayCell( 0 ) ) );
        t.setGraphic( new ImageView( tg.displayCell( 1 ) ) );
        fp.getChildren().addAll( tryButton, t );
        
        stage.setScene( new Scene( fp ) );
        stage.show();
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        launch();
    }

}
