/************************************************************
*   Program: Project 8 - Part 1 - VTTPieceWork.java         *
*    Author: Victor Trucker                                 *
*      Date: 04/13/2000                                     *
*   Purpose: This Application allows user to input workers  *
*            Peice Work and Pay Grade and calculate pay.    *
*            Keeps a running total for final report...      *
************************************************************/

import java.awt.*;
import javax.swing.UIManager;


public class VTTPieceWork
{
  private boolean
    packFrame = false;


  public VTTPieceWork()
  {
    VTTPieceWorkFrame
      frame = new VTTPieceWorkFrame();
    Dimension
      frameSize  = frame.getSize(),
      screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    if ( packFrame )
      frame.pack();
    else
      frame.validate();

    if ( frameSize.height > screenSize.height )
      frameSize.height = screenSize.height;
    if ( frameSize.width > screenSize.width )
      frameSize.width  = screenSize.width;
      
    frame.setLocation( ( screenSize.width  - frameSize.width  ) / 2,
                       ( screenSize.height - frameSize.height ) / 2 );
    frame.setVisible( true );
  } // end VTTPieceWork()


  public static void main( String[] args )
  {
    try 
    {
      UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
    }
    catch( Exception e )
    {
      e.printStackTrace();
      VTTUtils.say( "UI Manager Failed to Set \"Look and Feel\"", "UI Error" );
    }
    new VTTPieceWork();
  }
  
} // End class VTTPieceWork
 