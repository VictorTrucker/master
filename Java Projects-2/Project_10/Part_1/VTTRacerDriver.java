/************************************************************
*   Program: Project 10 - Part 1 - VTTRacerDriver.java      *
*    Author: Victor Trucker                                 *
*      Date: 05/01/2000                                     *
*   Purpose: Driver Class for VTTRacer...                   *
*            This Application Generates a mock-up of the    *
*            historic race between the Tortoise and the     *
*            Hare using multi threading to manage the       *
*            racers...                                      *
************************************************************/

import java.awt.*;
import javax.swing.UIManager;


public class VTTRacerDriver
{
  private boolean
    packFrame = false;

  public VTTRacerDriver()
  {
    VTTRacerFrame
      frame      = new VTTRacerFrame();
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
  } // end VTTRacerDriver()


  public static void main( String[] args )
  {
    try 
    {
      UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
    }
    catch( Exception e )
    {
      e.printStackTrace();
      VTTUtils.cry( "UI Manager Failed to Set \"Look and Feel\"", "UI Error" );
    }
    new VTTRacerDriver();
  } // end main( String[] )
  
} // end class VTTRacerDriver

