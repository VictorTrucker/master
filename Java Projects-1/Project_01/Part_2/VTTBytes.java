/**************************************************
*   Program: Project 1 - Part 2 - VTTBytes.java   *
*    Author: Victor Trucker                       *
*      Date: 02/04/2000                           *
*   Purpose: This app accepts the input of mega-  *
*            bytes, and return the actual number  *
*            of bytes...                          *
**************************************************/

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class VTTBytes extends Applet implements ActionListener
{
  Label
    lblTitle  = new Label( "Megabyte-to-Byte Calculator" ),
    lblPrompt = new Label( "Enter Amount (Megabytes), then click Calculate" ),
    lblBytes  = new Label(),
    lblSorry  = new Label();
  TextField
    txtMegabytes = new TextField( 4 );
  Button
    cmdConvert = new Button( "Calculate" ),
    cmdClear   = new Button( "    Clear    " );
  Font
    arialFont11p   = new Font( "Arial", Font.PLAIN, 11 ),
    courierFont12p = new Font( "Courier", Font.PLAIN, 12 ),
    courierFont18b = new Font( "Courier", Font.BOLD, 18 );

  public void init()
  {
    lblTitle.setFont( courierFont18b );
    lblBytes.setFont( courierFont12p );
    lblSorry.setFont( arialFont11p );
    lblPrompt.setFont( courierFont12p );
    txtMegabytes.setFont( courierFont12p );

    add( lblTitle );
    add( lblPrompt );
    add( txtMegabytes );

    add( cmdConvert );
    add( cmdClear );
  } // End of Init()

  public void start()
  {
    cmdConvert.addActionListener( this );
    cmdClear.addActionListener( this );

    txtMegabytes.setText( "" );
    txtMegabytes.requestFocus();

    invalidate();
    validate();
  } // End of Start()

  public void actionPerformed( ActionEvent event )
  {
    int
      nInput     = Integer.parseInt( txtMegabytes.getText() ),
      nMegabytes = ( nInput > 2097 ) ? 2097 : nInput; // Limit Input
    long
      lAnswer = nMegabytes * 1024000;
    String
      strMsg = "";

    if ( event.getSource() == cmdClear )
    {
      txtMegabytes.setText( "" );
      remove( lblBytes );
      remove( lblSorry );
      invalidate();
      validate();
      txtMegabytes.requestFocus();
    }
    else
    {
      strMsg = "The Actual Number of Bytes in " +
               nMegabytes + " Megabytes is " + lAnswer;
      lblBytes.setText( strMsg );
      add( lblBytes );
      if ( nMegabytes == 2097 )
      {
        txtMegabytes.setText( "2097" );
        lblSorry.setText( "Sorry, 2097 is the biggest number I can do..." );
        add( lblSorry );
      }
      else
      {
        remove( lblSorry );
      }
      invalidate();
      validate();
      lblBytes.setLocation( 15, 95 );
      lblSorry.setLocation( 50, 113 );
    }
  } // End of actionPerformed()
} // End of class VTTBytes
