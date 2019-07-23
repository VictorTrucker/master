/**********************************************************
*   Program: Project 2 - Part 3 - VTTHypotenuse.java      *
*    Author: Victor Trucker                               *
*      Date: 02/14/2000                                   *
*   Purpose: This applet accepts the input of the sides   *
*            A and B of a right triangle and calculates   *
*            the Hypotenuse, Side C. Hense the name...    *
**********************************************************/

import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class VTTHypotenuse extends Applet implements ActionListener
{
  private boolean
    bCalculate   = false;
  private double
    dSideA       = 0.00,
    dSideB       = 0.00,
    dSideC       = 0.00;
  private StringBuffer
    strSideA     =  new StringBuffer( "" ),
    strSideB     =  new StringBuffer( "" ),
    strSideC     =  new StringBuffer( "" );
  private Button
    cmdCalculate = new Button( "Calculate" ),
    cmdClear     = new Button( "    Clear    " );
  private Label
    lblAValue    = new Label( "000" ),
    lblBValue    = new Label( "000" ),
    lblCValue    = new Label( "000" ),
    lblSideA     = new Label( "Side 'A'  (1.00 to 70.00)" ),
    lblSideB     = new Label( "Side 'B'  (1.00 to 70.00)" ),
    lblSideC     = new Label( "Side 'C'" ),
    lblTitle     = new Label( "- Hypotenuse -" );
  private TextField
    txtSideA     = new TextField( 10 ),
    txtSideB     = new TextField( 10 ),
    txtSideC     = new TextField( 10 );
  private Font
    arialFont12p   = new Font( "Arial",   Font.PLAIN, 12 ),
    courierFont12p = new Font( "Courier", Font.PLAIN, 12 ),
    courierFont12b = new Font( "Courier", Font.BOLD,  12 ),
    courierFont18b = new Font( "Courier", Font.BOLD,  18 );
  private DecimalFormat
    formatSide     = new DecimalFormat( "0.00" );

    
  public void init()
  {
    lblTitle.setFont( courierFont18b );
    lblSideA.setFont( arialFont12p );
    lblSideB.setFont( arialFont12p );
    lblSideC.setFont( arialFont12p );
    txtSideA.setFont( courierFont12p );
    txtSideB.setFont( courierFont12p );
    txtSideC.setFont( courierFont12p );

    add( txtSideA );
    add( txtSideB );
    add( txtSideC );
    add( lblSideA );
    add( lblSideB );
    add( lblSideC );
    add( lblTitle );
    add( lblAValue );
    add( lblBValue );
    add( lblCValue );
    add( cmdCalculate );
    add( cmdClear );
    txtSideC.setEditable( false );
    txtSideC.setForeground( Color.red );
    txtSideC.setBackground( Color.white );
    lblSideA.setBackground( Color.lightGray );
    lblSideB.setBackground( Color.lightGray );
    lblSideC.setBackground( Color.lightGray );
    lblTitle.setBackground( Color.lightGray );
    lblAValue.setBackground( Color.lightGray );
    lblBValue.setBackground( Color.lightGray );
    lblCValue.setBackground( Color.lightGray );
    this.setBackground( Color.lightGray );
    say( "Project 2 - Part 3...\nHypotenuse...", "Here we go again..." );
  } // End Init()


  public void start()
  {
    cmdClear.addActionListener( this );
    cmdCalculate.addActionListener( this );
    txtSideB.addActionListener( this );

    txtSideA.setText( "" );
    txtSideB.setText( "" );
    txtSideC.setText( "" );
    showResults( false );
    txtSideA.requestFocus();
  } // End Start()


  public void actionPerformed( ActionEvent event )
  {
    if ( event.getSource() == cmdClear )
    {
      dSideA   = 0.00;
      dSideB   = 0.00;
      dSideC   = 0.00;
      strSideA.setLength( 0 );
      strSideB.setLength( 0 );
      strSideC.setLength( 0 );
      txtSideA.setText( "" );
      txtSideB.setText( "" );
      txtSideC.setText( "" );
      showResults( false );
      bCalculate = false;
      txtSideA.requestFocus();
    }
    else
    {
      strSideA.replace( 0, 32, txtSideA.getText() );
      strSideB.replace( 0, 32, txtSideB.getText() );

      if ( strSideA.length() < 1 ) strSideA.replace( 0, 32, "0" );
      if ( strSideB.length() < 1 ) strSideB.replace( 0, 32, "0" );
      
      dSideA = Double.parseDouble( strSideA.toString() );
      dSideB = Double.parseDouble( strSideB.toString() );

      if ( dSideA < 1 || dSideB < 1 || dSideA > 70 || dSideB > 70  )
      {
        say( "Invalid input,\nValid Range: 1.00 to 70.00", "Invalid Input" );
        return;
      }

      dSideC   = hypotenuse( dSideA, dSideB );

      strSideA.replace( 0, 32, formatSide.format( dSideA ) );
      strSideB.replace( 0, 32, formatSide.format( dSideB ) );
      strSideC.replace( 0, 32, formatSide.format( dSideC ) );
      txtSideA.setText( strSideA.toString() );
      txtSideB.setText( strSideB.toString() );
      txtSideC.setText( strSideC.toString() );

      showResults( true );
      bCalculate = true;
    }
    repaint();
  } // End actionPerformed()


  public void paint( Graphics graphicItem )
  {
    int
      nUnit = 0,
      nAEnd = 46,
      nBEnd = 393,
      nAMid = 110,
      nBMid = 305;

    if ( dSideA > dSideB )
    {
      nUnit = ( int )( 140 / ( int )dSideA );
      nBEnd = ( 253 + ( int )( dSideB * nUnit ) );
      nBMid = 240 + ( int )( ( nBEnd - 253 ) / 2 );
    }
    else if ( dSideB > dSideA )
    {
      nUnit = ( int )( 140 / ( int )dSideB );
      nAEnd = ( 186 - ( int )( dSideA * nUnit ) );
      nAMid = 180 - ( int )( ( 186 - nAEnd ) / 2 );
    }

    cmdCalculate.setLocation( 25, 202 );
    cmdClear.setLocation( 110, 202 );
    lblTitle.setLocation( 30, 10 );
    lblAValue.setLocation( 218, nAMid );
    lblBValue.setLocation( nBMid, 195 );
    lblCValue.setLocation( 330, 80 );
    lblSideA.setLocation( 20, 40 );
    txtSideA.setLocation( 20, 63 );
    lblSideB.setLocation( 20, 90 );
    txtSideB.setLocation( 20, 113 );
    lblSideC.setLocation( 20, 140 );
    txtSideC.setLocation( 20, 163 );

    graphicItem.setColor( Color.black );
    graphicItem.drawRect( 210, 20, 210, 200 );

    if ( bCalculate )
    {
      graphicItem.setColor( Color.blue );
      graphicItem.drawLine( 253, 186, 253, nAEnd );
      graphicItem.drawLine( 253, 186, nBEnd, 186 );
      // Draw the Hypotenuse and text
      graphicItem.setColor( Color.red );
      graphicItem.drawString( "The Hypotenuse is:", 300, 70 );
      graphicItem.drawLine( 253, nAEnd, nBEnd, 186 );
      // And display the values
      lblAValue.setText( strSideA.toString() );
      lblBValue.setText( strSideB.toString() );
      lblCValue.setText( strSideC.toString() );
    }
    else
    {
      graphicItem.setColor( Color.lightGray );
      graphicItem.drawLine( 253, 186, 253, nAEnd );
      graphicItem.drawLine( 253, 186, nBEnd, 186 );
      // Erase the Hypotenuse and text
      graphicItem.drawString( "The Hypotenuse is:", 300, 70 );
      graphicItem.drawLine( 253, nAEnd, nBEnd, 186 );
      // Reset the value labels
      txtSideC.setText( "" );
      lblAValue.setText( "" );
      lblBValue.setText( "" );
      lblCValue.setText( "" );
    }
  } // End paint()


  private void showResults( boolean bShow )
  {
    lblAValue.setVisible( bShow );
    lblBValue.setVisible( bShow );
    lblCValue.setVisible( bShow );
    lblSideC.setVisible( bShow );
    txtSideC.setVisible( bShow );
  } // End showResults()


  private double hypotenuse( double dA, double dB )
  {
    return Math.sqrt( Math.pow( dA, 2 ) + Math.pow( dB, 2 ) );
  } // End hypotenuse()


  private void say( String strMessage, String strTitle )
  {
    JOptionPane.showMessageDialog( this,
                                   strMessage,
                                   strTitle,
                                   JOptionPane.INFORMATION_MESSAGE );
  } // End say()
} // End class VTTHypotenuse
