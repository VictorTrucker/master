/************************************************************
*   Program: VTTBarGraph.java                               *
*    Author: Victor Trucker                                 *
*      Date: 03/14/2000                                     *
*   Purpose: This applet accepts input of Numbers and draws *
*            a bar graph based on the numbers using Java 2D *
*            drawLine(), drawRect() and fillRect()...       *
************************************************************/

//package VTTBarGraph;


import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class VTTBarGraph extends Applet implements ActionListener
{
  private static final long
    serialVersionUID = 1L;
  private int
    nX          = 142,
    nY          = 240,
    nCX         = 342,
    nCY         = 60,
    nNumCols    = 4;
  private String
    strBtnLabel[] = { " Graph It ", "    Reset    ", "    Close    " };
  private Button
    cmdMain[]   = new Button[strBtnLabel.length];
  private Label
    label[]     = new Label[nNumCols];
  private TextField
    txtColumn[] = new TextField[nNumCols];
  private Color
    color[]     = new Color[nNumCols];


  public void init()
  {
    int
      nLup   = 0;

    loadPallet();
    this.setSize( 450, 375 );
    this.setBackground( Color.lightGray );

    for ( nLup = 0; nLup < nNumCols; nLup++ )
    {
      txtColumn[nLup] = new TextField( 1 );
      label[nLup]     = new Label( "" + ( char )( nLup + 65 ), Label.CENTER );
      label[nLup].setBackground( color[nLup] );
      label[nLup].setFont( VTTUtils.arialFont12b );
      add( txtColumn[nLup] );
      add( label[nLup] );
      txtColumn[nLup].setFont( VTTUtils.courierFont12p );
      txtColumn[nLup].setText( "" );
    }

    for ( nLup = 0; nLup < strBtnLabel.length; nLup++ )
    {
      cmdMain[nLup] = new Button( strBtnLabel[nLup] );
      add( cmdMain[nLup] );
      cmdMain[nLup].addActionListener( this );
      cmdMain[nLup].setForeground( color[2 - nLup] );
    }

    VTTUtils.say( "Simple Bar Graph...", "Where's the Bar..." );
  } // end Init()


  public void start()
  {
	
    txtColumn[0].requestFocus();
  } // end Start()


  public void actionPerformed( ActionEvent event )
  {
    int
      nLup = 0;

    if ( event.getSource() == cmdMain[2] )
    {
      if ( VTTUtils.askYesNo( "Quit the Applet? Are you Crazy?", "Exit App" ) == VTTUtils.YES )
      {
        System.exit( 0 );
      }
    }
    else if ( event.getSource() == cmdMain[1] )
    {
      for ( nLup = 0; nLup < 4; nLup++ )
      {
        txtColumn[nLup].setText( "" );
      }
      initGraph();
      txtColumn[0].requestFocus();
    }
    else if ( event.getSource() == cmdMain[0] )
    {
      drawGraph();
    }
  } // end actionPerformed( ActionEvent )


  public void drawGraph()
  {
    boolean
      bIsValid   = true;
    int
      nLup       = 0,
      nOffset    = 0,
      nBarWidth  = 35,
      nValue[]   = new int[4],
      nBarLeft[] = { nX + 15, nX + 60, nX + 105, nX + 150 },
      nBarTop[]  = { 0, 0, 0, 0 },
      nInput     = 0;
    VTTValue
      value      = new VTTValue();
    StringBuffer
      strInput   = new StringBuffer( "" );
    Graphics
      gr         = getGraphics();
    Color
      colorHold  = gr.getColor();

    for ( nLup = 0; nLup < nNumCols; nLup++ )
    {
      strInput.setLength( 0 );

      if ( txtColumn[nLup].getText().trim().equals( "" ) )
        strInput.append( 0 );
      else
        strInput.append( txtColumn[nLup].getText().trim() );

      bIsValid = VTTUtils.validateInt( strInput.toString() );

      if ( strInput.toString().equals( "0" ) ) bIsValid = false;

      if ( bIsValid )
      {
        if ( Integer.parseInt( strInput.toString() ) > 100 ) bIsValid = false;
      }

      if ( !bIsValid )
      {
        while ( nInput < 1 )
        {
          value = VTTUtils.getInput( "Value is Missing or Out of Range.\n" +
                                     "Valid Range: 1 to 100\n" +
                                     "Input Column [ " + ( char )( nLup + 65 ) +
                                     " ] Value.", VTTUtils.GET_INTEGER );
          nInput = value.nValue;

          if ( nInput < 0 ) return;

          if ( nInput < 1 || nInput > 100 )
          {
            nInput = 0;
            VTTUtils.say( "Invalid Data Value!\n" +
                          "- Positive Integers Only -\n" +
                          "Valid Range: 1 to 100",
                          "Error" );
          }
        }
        nValue[nLup] = nInput;
        txtColumn[nLup].setText( "" + nInput );
        nInput = 0;
      }
      else
      {
        nValue[nLup] = Integer.parseInt( txtColumn[nLup].getText() );
      }
      nBarTop[nLup] = nY - ( int )( nValue[nLup] * 1.8 );
    }
// All of that other stuff was commercial
// crap...  This is the graph...
    initGraph();

    for ( nLup = 0; nLup < 4; nLup++ )
    {
      nOffset = ( nValue[nLup] > 1 ) ? 0 : 1;
      gr.setColor( color[nLup] );
      gr.fillRect( nBarLeft[nLup],
                   nBarTop[nLup],
                   nBarWidth,
                   nY - nBarTop[nLup] );
      gr.setColor( Color.darkGray );
      gr.drawRect( nBarLeft[nLup],
                   nBarTop[nLup] - nOffset,
                   nBarWidth,
                   ( nY + nOffset ) - nBarTop[nLup] );
      gr.drawString( "" + nValue[nLup], nBarLeft[nLup] + 13, nY + 20 );
    }

    gr.setColor( colorHold );
  } // end drawGraph()


  public void initGraph()
  {
    int
      nXBox  = 0,
      nYBox  = 0,
      nWBox  = 0,
      nHBox  = 0,
      nLup   = 0,
      nLabel = 0;
    Graphics
      gr     = getGraphics();
    Color
      colorHold = gr.getColor();
    StringBuffer
      strLabel  = new StringBuffer( "" );

    gr.setColor( Color.lightGray );
    gr.fillRect( nX + 1, nCY - 1, nCX - nX, nY - nCY + 1 );
    gr.fillRect( nX + 1, nY + 5, 200, 20 );
    gr.setColor( Color.blue );
    gr.setFont( VTTUtils.courierFont18b );
    gr.drawString( "- Bar Graph -", 165, 50 );
    gr.setColor( Color.yellow );
    gr.setFont( VTTUtils.arialFont10p );
    gr.drawString( " Input  Data  to", 12, 50 );
    gr.drawString( " be rendered in", 12, 60 );
    gr.drawString( " the Bar Graph.", 12, 70 );
    gr.setColor( Color.red );
    gr.drawString( " Integers  Only.", 12, 88 );
    gr.setColor( Color.blue );
    gr.drawString( "Range: 1 to 100", 12, 105 );

    for ( nLup = 0; nLup < 3; nLup++ )
    {
      nXBox =  95 + nLup;
      nYBox =  30 + nLup;
      nWBox = 280 - ( nLup * 2 );
      nHBox = 250 - ( nLup * 2 );

      gr.setColor( Color.darkGray );
      gr.drawRect( nXBox, nYBox, nWBox, nHBox );
    }

    gr.setColor( Color.darkGray );
    gr.drawLine( nX, nY, nX, nCY );
    gr.drawLine( nX, nY, nCX, nY );

    for ( nLup = 0; nLup < 11; nLup++ )
    {
      strLabel.setLength( 0 );

      switch ( nLup )
      {
        case 0:
          strLabel.append( "    " );
          break;
        case 10:
          strLabel.append( "" );
          break;
        default:
          strLabel.append( "  " );
      }

      nLabel = nLup * 10;
      strLabel.append( nLabel );
      gr.setColor( Color.darkGray );
      gr.drawString( strLabel.toString(), 118, ( nY + 4 ) - ( nLup * 18 ) );
      gr.drawLine( nX - 3, nY - ( nLup * 18 ), nX, nY - ( nLup * 18 ) );

      if ( nLup < 4 )
        gr.drawString( "" + ( char )( nLup + 65 ) + "-" , 159 + ( nLup * 45 ), nY + 20 );
    }

    darwGuideLines();
    gr.setColor( colorHold );
  } // end initGraph()


  public void darwGuideLines()
  {
    int
      nLup = 0;
    Graphics
      gr   = getGraphics();
    Color
      colorHold = gr.getColor();

    gr.setColor( Color.white );

    for ( nLup = 0; nLup < 11; nLup++ )
    {
      if ( nLup > 0 )
        gr.drawLine( nX + 1, nY - ( nLup * 18 ), nCX, nY - ( nLup * 18 ) );
    }

    gr.drawLine( nCX, nY - 1, nCX, nCY );
    gr.setColor( colorHold );
  } // end darwGuideLines()


  public void loadPallet()
  {
    color[0] = new Color( 255,   0,   0 ); // Red
    color[1] = new Color(   0, 255,   0 ); // Blue
    color[2] = new Color(   0,   0, 255 ); // Green
    color[3] = new Color( 255, 255,   0 ); // Yellow
  } // end loadPallet()


  public void paint( Graphics gr )
  {
    int
      nLup        = 0,
      nGraphW     = cmdMain[0].getWidth(),
      nClearW     = cmdMain[1].getWidth(),
      nCloseW     = cmdMain[2].getWidth(),
      nRowSpace   = 8,
      nLabelTop   = 120,
      nLabelLeft  = 20,
      nTextLeft   = nLabelLeft + label[0].getWidth() + nRowSpace,
      nWidth      = ( nGraphW + nClearW + nCloseW + 8 ),
      nButtonTop  = this.getHeight() - ( cmdMain[0].getHeight() + nRowSpace ),
      nButtonLeft = ( int )( this.getWidth() - nWidth ) / 2;
    Color
      colorHold   = gr.getColor();

    nButtonLeft = ( nButtonLeft > 8 ) ? nButtonLeft : 8;
    cmdMain[0].setLocation( nButtonLeft, nButtonTop );
    cmdMain[1].setLocation( cmdMain[0].getX() + nGraphW + 4, nButtonTop );
    cmdMain[2].setLocation( cmdMain[1].getX() + nClearW + nCloseW + 4, nButtonTop );

    for ( nLup = 0; nLup < 4; nLup++ )
    {
      if ( nLup > 0 )
        nLabelTop = label[nLup - 1].getY() + label[nLup].getHeight() + nRowSpace;

      label[nLup].setLocation( nLabelLeft, nLabelTop );
      txtColumn[nLup].setLocation( nTextLeft, label[nLup].getY() );
    }

    gr.setColor( Color.blue );
    gr.setFont( VTTUtils.courierFont18b );
    gr.drawString( "- Bar Graph -", 165, 50 );
    gr.setColor( colorHold );
    initGraph();
  } // end paint( Graphics )

} // end class VTTBarGraph
