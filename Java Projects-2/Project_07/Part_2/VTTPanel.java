/************************************************************
*   Program: Project 7 - Part 2 - VTTPanel.java             *
*    Author: Victor Trucker                                 *
*      Date: 04/12/2000                                     *
*   Purpose: This class allows for drawing that doesn't     *
*            blink away instantly upon arriving...          *
************************************************************/

//package VTTPanel;

import java.awt.*;
import javax.swing.*;


public class VTTPanel extends JPanel
{
  public final static int
    CIRCLE    = 0,
    SQUARE    = 1,
    OVAL      = 2,
    RECTANGLE = 3;
  private int
    nLup      = 0,
    nShape    = 0;
  private Color
    color     = new Color( 255, 0, 0 );

  public void paintComponent( Graphics gr )
  {
    int
      nHeight = 0,
      nWidth  = 0,
      nX      = 0,
      nY      = 0,
      nFrameWidth  = this.getWidth(),
      nFrameHeight = this.getHeight();
    Color
      holdColor = gr.getColor();

    super.paintComponent( gr );
    gr.setColor( color );

    for ( nLup = 0; nLup < 20; nLup++ )
    {
      nWidth  = ( int )( Math.random() * 65 ) + 10;
      if ( CIRCLE == nShape || SQUARE == nShape )
        nHeight = nWidth;
      else
        nHeight = ( int )( Math.random() * 65 ) + 10;
      nX      = ( int )( Math.random() * ( nFrameWidth  - nWidth ) );
      nY      = ( int )( Math.random() * ( nFrameHeight - nHeight ) );
      
      switch ( nShape )
      {
        case CIRCLE:
        case OVAL:
          gr.drawOval( nX, nY, nWidth, nHeight );
          break;
        case SQUARE:
        case RECTANGLE:
          gr.drawRect( nX, nY, nWidth, nHeight );
          break;
      }
    }
    gr.setColor( holdColor );
  } // end paintComponent( Graphics )

  public void draw( int nWhich, Color c )
  {
    nShape = nWhich;
    color  = c;
    repaint();
  } // end draw( int )

} // end class VTTPanel
