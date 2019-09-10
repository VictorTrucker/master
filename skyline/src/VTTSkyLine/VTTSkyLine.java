/************************************************************
*   Program: Project 6 - Part 3 - VTTSkyLine.java           *
*    Author: Victor Trucker                                 *
*      Date: 03/16/2000                                     *
*   Purpose: This applet uses drawLine(), drwaRect(),       *
*            fillRect(), etc. to draw a city skyline...     *
************************************************************/

package VTTSkyLine;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
//import javax.swing.*;


public class VTTSkyLine extends Applet implements ActionListener
{
  private boolean
    bDaytime  = true;
  private int
    nStyle    = 0,
    nBldg[][] = { { 175, 160,  55,  80 }, { 145, 110,  50, 130 },
                  { 255,  40,  60, 200 }, { 340,  25,  60, 215 },
                  { 400, 130,  50, 110 }, { 435,  60,  50, 180 },
                  { 110, 140,  60, 100 }, { 215, 115,  60, 125 },
                  { 305, 100,  70, 140 }, { 460, 145,  60,  95 },
                  {  75, 190,  60,  50 }, { 485, 190,  60,  50 } },
    nWndw[][] = new int[nBldg.length][7];
  private String
    strBntLabel[] = { "  Draw  ", " Reset ", " Close " },
    strStyles[]   = { "Fill Frame", "Wire Frame", "Free Form" };
  private Choice
    dlistStyle    = new Choice();
  private Checkbox
    chkDayNight   = new Checkbox( "Daytime", true );
  private Button
    cmdMain[]     = new Button[strBntLabel.length];
  static final long
    serialVersionUID = 0;

  public void init()
  {
    int
      nLup  = 0;

    for ( nLup = 0; nLup < strBntLabel.length; nLup++ )
    {
      cmdMain[nLup] = new Button( strBntLabel[nLup] );
      add( cmdMain[nLup] );
      cmdMain[nLup].addActionListener( this );
    }

    for ( nLup = 0; nLup < strStyles.length; nLup++ )
    {
      dlistStyle.add( strStyles[nLup] );
    }

    add( chkDayNight );
    add( dlistStyle );
    this.setSize( 660, 330 );
    this.setBackground( Color.lightGray );
    chkDayNight.setBackground( Color.lightGray );

    chkDayNight.setState( true );
    dlistStyle.select( 0 );

    cmdMain[0].requestFocus();

    VTTUtils.say( "Project 6 - Part 3...\nSkyline...", "Here we go again..." );

    for ( nLup = 0; nLup < nWndw.length; nLup++ )
    {
      nWndw[nLup][0] = nBldg[nLup][0] + 6;             // X Coordinate
      nWndw[nLup][1] = nBldg[nLup][1] + 15;            // Y Coordinate
      nWndw[nLup][2] = 6;                              // Width
      nWndw[nLup][3] = 9;                              // Height
      nWndw[nLup][4] = ( int )( nBldg[nLup][3] / 20 ); // Number of Rows
      nWndw[nLup][5] = ( int )( nBldg[nLup][2] / 10 ); // Number of Columns
      nWndw[nLup][6] = 20;                             // Row Offset
    }
  } // end Init()


  public void start()
  {
    cmdMain[0].requestFocus();
  } // end Start()


  public void actionPerformed( ActionEvent event )
  {
    if ( event.getSource() == cmdMain[2] )
    {
      if ( VTTUtils.YES == VTTUtils.askYesNo( "Quit the Applet? Are you Crazy?", "Exit App" ) )
      {
        System.exit( 0 );
      }
    }
    else if ( event.getSource() == cmdMain[1] )
    {
      chkDayNight.setState( true );
      dlistStyle.select( 0 );
      invalidate();
      repaint();
      cmdMain[0].requestFocus();
    }
    else if ( event.getSource() == cmdMain[0] )
    {
      bDaytime = chkDayNight.getState();
      nStyle = dlistStyle.getSelectedIndex();
      initScreen();
      drawBase();

      if ( 2 == nStyle )
        drawFreeForm();
      else
        drawBuildings();

      addStacks( 1 );
      addStacks( 2 );
      addStacks( 3 );
      addStacks( 5 );
      addStacks( 8 );
      addDome( 6 );
      addDome( 7 );
      addDome( 9 );
      addDome( 10 );
      addStacks( 10 );
      addDome( 11 );
      addStacks( 11 );
    }
  } // end actionPerformed( ActionEvent )


  public void drawBase()
  {
    int
      nWidth  = this.getWidth(),
      nDWidth = ( int )( nWidth / 2 ),
      nLup    = 0;
    Graphics
      gr      = getGraphics();
    Color
      colorHold = gr.getColor();

    if ( bDaytime )
      gr.setColor( Color.darkGray );
    else
      gr.setColor( Color.lightGray );

// t 30,240 - 610,240 -- b 320,275 - 321,275
    if ( 1 == nStyle )
    {
      gr.drawLine( nDWidth, 275, 30, 240 );
      gr.drawLine(  30, 240, nWidth - 30, 240 );
      gr.drawLine( nDWidth + 1, 275, nWidth - 30, 240 );
    }
    else
    {
      for ( nLup = 30; nLup < nDWidth + 1; nLup++ )
      {
        if ( 0 == nLup % 2 )
          gr.setColor( Color.darkGray );
        else
          gr.setColor( Color.lightGray );
        gr.drawLine( nDWidth, 275, nLup, 240 );
      }
      for ( nLup = nDWidth + 1; nLup < nWidth - 30; nLup++ )
      {
        if ( 0 == nLup % 2 )
          gr.setColor( Color.darkGray );
        else
          gr.setColor( Color.lightGray );
        gr.drawLine( nDWidth + 1, 275, nLup, 240 );
      }
    }
    gr.setColor( colorHold );
  } // end drawBase()


  public void drawBuildings()
  {
    int
      nLup  = 0,
      nX    = 0,
      nY    = 0,
      nW    = 0,
      nH    = 0;
    Graphics
      gr    = getGraphics();
    Color
      colorHold = gr.getColor();

    for ( nLup = 0; nLup < nBldg.length; nLup++ )
    {
      nX = nBldg[nLup][0];
      nY = nBldg[nLup][1];
      nW = nBldg[nLup][2];
      nH = nBldg[nLup][3];

      if ( bDaytime )
        gr.setColor( Color.lightGray );
      else
        gr.setColor( Color.darkGray );

      if ( 1 != nStyle )
        gr.fillRect( nX, nY, nW, nH );

      if ( bDaytime )
        gr.setColor( Color.black );
      else
        gr.setColor( Color.lightGray );
      gr.drawRect( nX, nY, nW, nH );

      drawWindows( nLup );
    }
    gr.setColor( colorHold );
  } // end drawBuildings()


  public void drawFreeForm()
  {
//    boolean
//      bBase  = false;
    int
      nFrog  = 0,
      nX     = 0,
      nY     = 0,
      nCX    = 0,
      nCY    = 0,
      nLup1  = 0,
      nLup2  = 0;
    Graphics
      gr     = getGraphics();
    Color
      colorHold = gr.getColor();

    if ( bDaytime )
      gr.setColor( Color.darkGray );
    else
      gr.setColor( Color.lightGray );

    for ( nLup1 = 0; nLup1 < nBldg.length; nLup1++ )
    {
      nX  = nBldg[nLup1][0];
      nY  = nBldg[nLup1][1];
      nCX = nBldg[nLup1][0] + nBldg[nLup1][2];
      nCY = nBldg[nLup1][1] + nBldg[nLup1][3];

      for ( nLup2 = 0; ( nY + ( nLup2 * 10 ) ) <= nCY; nLup2++ )
      {
        nFrog  = nY + ( nLup2 * 10 );
        nFrog  = ( nFrog > nCY ) ? nCY : nFrog;
        gr.drawLine( nX, nY, nCX, nFrog );
      }

      for ( nLup2 = 0; ( nX + ( nLup2 * 10 ) ) <= nCX; nLup2++ )
      {
        nFrog = nX + ( nLup2 * 10 );
        nFrog = ( nFrog > nCX ) ? nCX : nFrog;
        gr.drawLine( nX, nY, nFrog, nCY );
      }

      drawWindows( nLup1 );
    }
    gr.setColor( colorHold );
  } // end drawFreeStyle()


  public void drawWindows( int nWhich )
  {
    int
      nLup     = 0,
      nLup2    = 0,
      nX       = nWndw[nWhich][0],
      nY       = nWndw[nWhich][1],
      nW       = nWndw[nWhich][2],
      nH       = nWndw[nWhich][3],
      nRows    = nWndw[nWhich][4],
      nCols    = nWndw[nWhich][5],
      nRowStep = nWndw[nWhich][6],
      nColStep = ( int )( nBldg[nWhich][2] / nCols );
    Graphics
      gr       = getGraphics();
    Color
      colorHold = gr.getColor(),
      color     = new Color( 192, 192, 192 );

    if ( bDaytime )
      gr.setColor( Color.lightGray );
    else
      gr.setColor( Color.darkGray );

    color = gr.getColor();

//    if ( 1 != nStyle )
//    {
    for ( nLup = 1; nLup < nRows; nLup++ )
    {
      nX = nWndw[nWhich][0];
      for ( nLup2 = 1; nLup2 < nCols; nLup2++ )
      {
        if ( 2 == ( int )( Math.random() * 4 ) )
          gr.setColor( Color.white );
        else
          gr.setColor( color );

        gr.fillRect( nX, nY, nW, nH );
        nX += nColStep;
      }
      nY += nRowStep;
    }
//    }

    if ( bDaytime )
      gr.setColor( Color.darkGray );
    else
      gr.setColor( Color.gray );

    nY = nWndw[nWhich][1];

    for ( nLup = 1; nLup < nRows; nLup++ )
    {
      nX = nWndw[nWhich][0];

      for ( int i = 1; i < nCols; i++ )
      {
        gr.drawRect( nX, nY, nW, nH );
        nX += nColStep;
      }

      nY += nRowStep;
    }
    gr.setColor( colorHold );
  } // end drawWindows()


  public void initScreen()
  {
    int
      nLup      = 0;
    String
      strTitle  = new String( "- Skyline -" );
    Graphics
      gr        = getGraphics();
    Color
      colorHold = gr.getColor();

    gr.setFont( VTTUtils.courierFont18b );

    if ( bDaytime )
      gr.setColor( Color.cyan );
    else
      gr.setColor( Color.black );

    for ( nLup = 0; nLup < 240; nLup++ )
      gr.drawLine( 0, nLup, this.getWidth(), nLup );

    if ( bDaytime )
      gr.setColor( Color.lightGray );
    else
      gr.setColor( Color.darkGray );

    for ( nLup = 240; nLup <= this.getHeight() - 38; nLup++ )
      gr.drawLine( 0, nLup, this.getWidth(), nLup );

    if ( bDaytime )
    {
      gr.setColor( Color.black );
    }
    else
    {
      gr.setColor( Color.lightGray );
      strTitle = "- Skyline at Night -";
    }

    if ( bDaytime )
      addClouds();
    else
      addMoon();

    gr.drawString( strTitle, 20, 15 );
    gr.setColor( colorHold );
  } // end initScreen()


  public void addStacks( int nWhich )
  {
    int
      nOffSet = ( int )( ( nBldg[nWhich][2] - 31 ) / 2 ),
      nX      = nBldg[nWhich][0] + nOffSet,
      nY      = nBldg[nWhich][1];
    Graphics
      gr      = getGraphics();
    Color
      colorHold = gr.getColor();

    if ( bDaytime )
      gr.setColor( Color.lightGray );
    else
      gr.setColor( Color.darkGray );

    if ( 1 != nStyle )
    {
      gr.fillRect( nX,      nY - 15,  6, 15 );
      gr.fillRect( nX + 10, nY -  7, 11,  7 );
      gr.fillRect( nX + 25, nY - 15,  6, 15 );
    }

    if ( bDaytime )
      gr.setColor( Color.darkGray );
    else
      gr.setColor( Color.lightGray );

    gr.drawRect( nX,      nY - 15,  6, 15 );
    gr.drawRect( nX + 10, nY -  7, 11,  7 );
    gr.drawRect( nX + 25, nY - 15,  6, 15 );
    gr.setColor( colorHold );
  } // end addStacks( int )


  public void addDome( int nWhich )
  {
    int
      nD  = ( int )( nBldg[nWhich][2] / 2 ),
      nX  = ( int )( ( nBldg[nWhich][2] - nD ) / 2 ),
      nY  = ( int )( nD / 2 ),
      nBX = nBldg[nWhich][0] + nX,
      nBY = nBldg[nWhich][1] - nY;
    Graphics
      gr  = getGraphics();
    Color
      colorHold = gr.getColor();

    if ( bDaytime )
      gr.setColor( Color.lightGray );
    else
      gr.setColor( Color.darkGray );

    if ( 1 != nStyle )
      gr.fillArc( nBX, nBY, nD, nD, 0, 180 );

    if ( bDaytime )
      gr.setColor( Color.black );
    else
      gr.setColor( Color.lightGray );

    gr.drawArc( nBX, nBY, nD, nD, 0, 180 );
    gr.setColor( colorHold );
  } // end addDomes( int )


  public void addMoon()
  {
    int
      nX = ( int )( Math.random() * 550 ) + 5,
      nY = ( int )( Math.random() * 80 ) + 5;
    Graphics
      gr = getGraphics();
    Color
      colorHold = gr.getColor();

    gr.setColor( Color.white );
    gr.fillOval( nX, nY, 60, 60 );
    gr.setColor( Color.black );
    gr.fillOval( nX + 15, nY - 10, 60, 60 );
    gr.setColor( colorHold );
  } // end addMoon()


  public void addClouds()
  {
    int
      nLup = 0,
      nX   = 0,
      nY   = 0,
      nW   = 0,
      nH   = 0,
      nNumberOfClouds = ( int )( ( Math.random() * 6 ) + 3 );
    Graphics
      gr   = getGraphics();
    Color
      colorHold = gr.getColor();

    gr.setColor( Color.white );

    for ( nLup = 1; nLup < nNumberOfClouds; nLup++ )
    {
      nX = ( int )( Math.random() * 550 );
      nY = ( int )( Math.random() * 150 );
      nW = ( int )( ( Math.random() * 15 ) + 75 );
      nH = ( int )( ( Math.random() * 10 ) + 35 );

      if ( 1 != nStyle )
      {
        gr.fillOval( nX, nY,  nW, nH );
        gr.fillOval( nX + 40, nY + 25, nW + 15, nH + 10 );
        gr.fillOval( nX + 100, nY + 5, nW , nH - 15 );
      }

      gr.drawOval( nX, nY,  nW, nH );
      gr.drawOval( nX + 40, nY + 25, nW + 15, nH + 10 );
      gr.drawOval( nX + 100, nY + 5, nW, nH - 15 );
    }
    gr.setColor( colorHold );
  } // end addClouds()


  public void paint( Graphics gr )
  {
    int
      nCmd0W = cmdMain[0].getWidth(),
      nCmd1W = cmdMain[1].getWidth(),
      nCmd2W = cmdMain[2].getWidth(),
      nTop   = ( this.getHeight() - cmdMain[0].getHeight() ) - 8,
      nLeft  = ( this.getWidth() - ( nCmd0W  + nCmd1W + ( nCmd2W * 2 ) +
                                     dlistStyle.getWidth() + 56 ) ) / 2;

    nLeft = ( nLeft > 0 ) ? nLeft : 8;
    cmdMain[0].setLocation( nLeft, nTop );
    cmdMain[1].setLocation( nLeft + nCmd0W + 4, nTop );
    cmdMain[2].setLocation( cmdMain[1].getX()  + nCmd1W + 4, nTop );
    chkDayNight.setLocation( cmdMain[2].getX() + nCmd2W + 16, nTop );
    dlistStyle.setLocation( chkDayNight.getX() + nCmd2W + 32, nTop );
  } // end paint( Graphics )

} // end class VTTSkyLine
