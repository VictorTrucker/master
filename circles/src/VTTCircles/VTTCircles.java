/************************************************************
*   Program: Project 6 - Part 1 - VTTCircles.java           *
*    Author: Victor Trucker                                 *
*      Date: 03/14/2000                                     *
*   Purpose: This applet draws concentric circles on the    *
*            form.  I give the user a little control...     *
************************************************************/

package VTTCircles;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class VTTCircles extends Applet implements ActionListener
{
  private int
    nWhich         = 0,
    nLup           = 0,
    nCurrentCircle = 0,
    nMaxCircle     = 10;
  private VTTPoint
    point[]    = new VTTPoint[10];
  private String
    strBtnLabel[] = { "Draw Next Circle", "Remove Last Circle", "Clear", "Close" };
  private Button
    cmdMain[] = new Button[strBtnLabel.length];
  private Timer
    timer      = new Timer( 100, ( ActionListener )this );
  static final long
    serialVersionUID = 0;


  public void init()
  {
    int
      nLup = 0,
      nX   = ( int )( this.getWidth() ) - 5,  // X Coordinate // '/ 2'
      nY   = ( int )( this.getHeight() ) - 5, // Y Coordinate // '/ 2'
      nZ   = 10;                              // Radius

    timer.stop();

    for ( nLup = 0; nLup < strBtnLabel.length; nLup++ )
    {
      cmdMain[nLup] = new Button( strBtnLabel[nLup] );
      add( cmdMain[nLup] );
      cmdMain[nLup].addActionListener( this );
    }

    this.setBackground( Color.lightGray );
    cmdMain[1].setEnabled( false );

    point[0] = new VTTPoint( nX, nY, nZ );

    for ( nLup = 1; nLup < nMaxCircle; nLup++ )
    {
      point[nLup] = new VTTPoint( point[nLup - 1].getX() - 10,
                                  point[nLup - 1].getY() - 10,
                                  point[nLup - 1].getZ() + 20 );
    }

    VTTUtils.say( "Project 6 - Part 1...\nConcentric Circles...",
                  "Round -n- Round..." );
  } // end Init()


  public void start()
  {
    if ( cmdMain[0].isEnabled() )
    {
      cmdMain[0].requestFocus();
    }
    else
    {
      cmdMain[1].requestFocus();
    }
  } // end Start()


  public void actionPerformed( ActionEvent event )
  {
//    int
//      nRet = -1;

    if ( event.getSource() == cmdMain[3] )
    {
      if ( VTTUtils.askYesNo( "Quit the Applet? Are you Crazy?", "Exit App" ) == VTTUtils.YES )
      {
        System.exit( 0 );
      }
    }
    else if ( event.getSource() == cmdMain[2] )
    {
      cmdMain[1].setEnabled( false );
      cmdMain[0].setEnabled( true );
      cmdMain[0].requestFocus();
      nWhich         = 0;
      nCurrentCircle = 0;
      invalidate();
      repaint();
    }
    else if ( event.getSource() == cmdMain[0] && cmdMain[0].isEnabled() )
    {
      drawCircle( point[nCurrentCircle], true, false );
      nCurrentCircle++;
    }
    else if ( event.getSource() == cmdMain[1] && cmdMain[1].isEnabled() )
    {
      nCurrentCircle--;
      drawCircle( point[nCurrentCircle], false, false );
    }
    else if ( event.getSource() == timer )
    {
      drawCircle( point[nWhich], true, false );
      drawCircle( point[( nWhich > 4 ) ? nWhich - 5 : nWhich], true, false );

      nWhich = ( nWhich < 9 ) ? ++nWhich : 5;
      
      drawCircle( point[nWhich], true, true );
      drawCircle( point[( nWhich > 4 ) ? nWhich - 5 : nWhich], true, true );
      return;
    }

    cmdMain[0].setEnabled( nCurrentCircle < 10 );
    cmdMain[1].setEnabled( nCurrentCircle > 0 );

    if ( nCurrentCircle == 0 )
    {
      cmdMain[0].requestFocus();
    }

    if ( cmdMain[0].isEnabled() )
    {
      timer.stop();
      for ( nLup = 0; nLup < nCurrentCircle; nLup++ )
      {
        drawCircle( point[nLup], true, false );
      }
    }
    else
    {
      cmdMain[1].requestFocus();
      timer.start();
    }
  } // end actionPerformed( ActionEvent )


  public void drawCircle( VTTPoint pt, boolean bAdding, boolean bPulsing )
  {
    Graphics
      gr = getGraphics();
    Color
      colorHold = gr.getColor();

    if ( bAdding )
    {
      gr.setColor( Color.black );

      if ( bPulsing ) gr.setColor( Color.red );

      gr.drawOval( pt.getX(), pt.getY(), pt.getZ(), pt.getZ() );
    }
    else
    {
      gr.setColor(Color.lightGray);
      gr.drawOval( pt.getX(), pt.getY(), pt.getZ(), pt.getZ() );
    }
    gr.setColor( colorHold );
  } // end drawCircle( VTTPoint, boolean, boolean )


  public void paint( Graphics gr )
  {
    int
      nLeft    = this.getWidth() - ( cmdMain[3].getWidth() + 20 ),
      nMinLeft = cmdMain[2].getX() + cmdMain[2].getWidth() + 10,
      nTop     = this.getHeight() - ( cmdMain[0].getHeight() + 8 );
    Font
      font     = gr.getFont();

    cmdMain[0].setLocation( 20, nTop );
    cmdMain[1].setLocation( cmdMain[0].getX() + cmdMain[0].getWidth() + 4, nTop );
    cmdMain[2].setLocation( cmdMain[1].getX() + cmdMain[1].getWidth() + 4, nTop );
    nLeft = ( nLeft > nMinLeft ) ? nLeft : nMinLeft;
    cmdMain[3].setLocation( nLeft, nTop );

    gr.setFont( VTTUtils.courierFont18b );
    gr.drawString( "- Concentric Circles -", 66, 22 );
    gr.setFont( font );
  } // end paint( Graphics )

} // end class VTTCircles
