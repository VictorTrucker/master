/************************************************************
*   Program: Project 6 - Part 2 - VTTColorChars.java        *
*    Author: Victor Trucker                                 *
*      Date: 03/15/2000                                     *
*   Purpose: This applet Randomly displays random Chars in  *
*            random colors, vague, yet meaningless...       *
************************************************************/

//package VTTColorChars;


import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class VTTColorChars extends Applet implements ActionListener
{
  private int
    nFontSize[]  = { 8, 10, 12, 18, 24, 32, 48, 72 },
    nFontStyle[] = { Font.PLAIN, Font.BOLD, Font.ITALIC },
    nRed         = 0,
    nGreen       = 0,
    nBlue        = 0,
    nLup         = 0;
  private String
    strStop       = "   Stop   ",
    strBtnLabel[] = { "   Start   ", " Clear ", " Close " };
  private Button
    cmdMain[] = new Button[strBtnLabel.length];
  private Timer
    timer     = new Timer( 10, ( ActionListener )this );
  private String
    strFontName[] = { "Courrier",
                      "Arial",
                      "Times New Roman",
                      "Brush Script",
                      "Garamound",
                      "MS San Serif" };
  private Color
    color[] = new Color[8];


  public void init()
  {
    for ( nLup = 0; nLup < strBtnLabel.length; nLup++ )
    {
      cmdMain[nLup] = new Button ( strBtnLabel[nLup] );
      add( cmdMain[nLup] );
      cmdMain[nLup].addActionListener( this );
    }

    this.setSize( 750, 750 );
    this.setBackground( Color.lightGray );
    loadPallet();

    VTTUtils.say( "Project 6 - Part 2...\nRandom Characters...",
                  "Oh, No!  Not again..." );
  } // end Init()


  public void start()
  {
    ;
  } // end Start()


  public void actionPerformed( ActionEvent event )
  {
    int
      nRet = -1;

    if ( event.getSource() == cmdMain[2] )
    {
      if ( VTTUtils.askYesNo( "Quit the Applet? Are you Crazy?", "Exit App" ) == VTTUtils.YES )
      {
        System.exit( 0 );
      }
    }
    else if ( event.getSource() == cmdMain[1] )
    {
      invalidate();
      repaint();
    }
    else if ( event.getSource() == cmdMain[0] )
    {
      if ( cmdMain[0].getLabel().equals( strBtnLabel[0] ) )
      {
        cmdMain[0].setLabel( strStop );
        timer.start();
      }
      else
      {
        cmdMain[0].setLabel( strBtnLabel[0] );
        timer.stop();
      }
    }
    else if ( event.getSource() == timer )
    {
      doCharacter();
    }
  } // end actionPerformed( ActionEvent )


  public void doCharacter()
  {
    int
      nFont    = ( int )( Math.random() * strFontName.length ),
      nStyle   = ( int )( Math.random() * nFontStyle.length ),
      nSize    = ( int )( Math.random() * nFontSize.length ),
      nColor   = ( int )( Math.random() * color.length ),
      nX       = ( int )( Math.random() * ( this.getWidth() - 30 ) ),
      nY       = ( int )( Math.random() * ( this.getHeight() ) );
    char
      ch       = ( char )( ( Math.random() * 94 ) + 33 ); // 33 to 126
    Graphics
      gr       = getGraphics();
    Color
      colorHold = gr.getColor();

    gr.setFont( new Font( strFontName[nFont], nFontStyle[nStyle], nFontSize[nSize] ) );
    gr.setColor( color[nColor] );
    gr.drawString( "" + ch, nX, nY );
    gr.setColor( colorHold );
    System.gc();
  } // end doCharacter()


  public void loadPallet()
  {
    color[0] = new Color(   0,   0,   0 );
    color[1] = new Color(   0,   0, 128 );
    color[2] = new Color(   0, 128,   0 );
    color[3] = new Color(   0, 128, 128 );
    color[4] = new Color( 128,   0,   0 );
    color[5] = new Color( 128,   0, 128 );
    color[6] = new Color( 128, 128,   0 );
    color[7] = new Color( 128, 128, 128 );
  } // end loadPallet()


  public void paint( Graphics gr )
  {
    int
      nTop = ( this.getHeight() - cmdMain[0].getHeight() ) - 8;

    cmdMain[0].setLocation( 8, nTop );
    cmdMain[1].setLocation( cmdMain[0].getX() + cmdMain[0].getWidth() + 4, nTop );
    cmdMain[2].setLocation( cmdMain[1].getX() + cmdMain[1].getWidth() + 4, nTop );
    gr.setFont( VTTUtils.courierFont24b );
    gr.drawString( "- Random Characters -", 20, 30 );
  } // end paint( Graphics )

} // end class VTTColorChars
