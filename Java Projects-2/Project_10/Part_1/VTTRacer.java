/************************************************************
*   Program: Project 10 - Part 1 - VTTRacer.java            *
*    Author: Victor Trucker                                 *
*      Date: 05/01/2000                                     *
*   Purpose: This class generates a critter to run in the   *
*            historic race between the Tortoise and the     *
*            Hare using multi threading to manage the       *
*            racers and their identities...                 *
************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public abstract class VTTRacer extends Thread
{
  public static int
    nWinner   = -1;
  protected final int
    TORTOISE  = 0,
    HARE      = 1;
  public int
    nPrevious = 0,
    nPosition = 0,
    nWins     = 0,
    nType     = 0;
  protected String
    strRacer  = null;
  protected VTTRacerFrame
    frame     = null;


  public abstract void makeMove();


  public VTTRacer( VTTRacerFrame frm )
  {
    frame = frm;
  } // end VTTRacer( VTTRacerFrame )


  public void go()
  {
    start();
  } // end go()


  public void run()
  {
    super.run();
  } // end run()


  protected void runRace( int nValue )
  {
    int
      nLup   = 0,
      nLup2  = 0,
      nStep  = 1,
      nPos   = 0;

    nPrevious = nPosition;
    nPosition += nValue;
    nStep = ( nPosition >= nPrevious ) ? 1 : -1;

    nPosition = ( nPosition < 0  ) ?  0 :
                ( nPosition > 69 ) ? 70 : nPosition;

    nPos = nPosition;

    if ( nPos == 70 ) nWinner = nType;
    if ( nWinner > -1 )
    {
      frame.repaint();
      VTTUtils.say( "The Winner is:  The " + strRacer, "Winner" );
    }
    else
    {
      try
      {
        switch ( nStep )
        {
          case -1:
            for ( nLup = nPrevious; nLup >= nPos; nLup-- )
            {
              nPosition = nLup;
              sleep( 125 );
            }
            break;

          case 1:
            for ( nLup = nPrevious; nLup <= nPos; nLup++ )
            {
              nPosition = nLup;
              sleep( 125 );
            }
            break;
        }
      }
      catch ( Exception e )
      {
        VTTUtils.cry( "Error Establishing Previous and Current Positions", "Error" );
        e.printStackTrace();
        System.out.println( e.getMessage() );
      }
    }
    frame.repaint();
    yield();
  } // end runRace( int )


  public int getPosition()
  {
    return ( nPosition > 0 ) ? nPosition : 0;
  } // end getPosition()

} // end class VTTRacer

