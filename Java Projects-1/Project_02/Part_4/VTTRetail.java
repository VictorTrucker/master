/**********************************************************
*   Program: Project 2 - Part 4 - VTTRetail.java          *
*    Author: Victor Trucker                               *
*      Date: 02/17/2000                                   *
*   Purpose: This applet accepts the input of Product     *
*            sales by product number and quantity and     *
*            keeps product totals and calculates the      *
*            weekly total for all products sold.          *
**********************************************************/

import java.applet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class VTTRetail extends Applet implements ActionListener
{
  private boolean
    bTotal = false;
  private double
    dItemPrice[] = { 0.00, 2.98, 4.50, 9.98, 4.49, 6.87 },
//    dItemPrice   = 0.00,
    dItemTotal[] = { 0.00, 0.00, 0.00, 0.00, 0.00, 0.00 },
    dWeeklyTotal = 0.00;
  private int
    nLup         = 0,
    nItem        = 0,
    nQty         = 0,
    nItemCount[] = { 0, 0, 0, 0, 0, 0 };
  private StringBuffer
    strItem   = new StringBuffer( "" ),
    strQty    = new StringBuffer( "" ),
    strSideC  = new StringBuffer( "" );
  private Button
    cmdCalculate = new Button( "Calculate" ),
    cmdTotal     = new Button( "    Total    " ),
    cmdClear     = new Button( "    Clear    " ),
    cmdReset     = new Button( "    Reset   " );
  private Label
    lblItemAmount[] = new Label[6],
    lblTotalAmount  = new Label( "        ", Label.RIGHT ),
    lblItem         = new Label( "Item Number (1 to 5)" ),
    lblQty          = new Label( "Item Quantity (1 to 99)" ),
    lblTitle        = new Label( "- Weekly Sales -" );
  private TextField
    txtItem   = new TextField( 10 ),
    txtQty    = new TextField( 10 );
  private Font
    arialFont12p   = new Font( "Arial",   Font.PLAIN, 12 ),
    courierFont12p = new Font( "Courier", Font.PLAIN, 12 ),
    courierFont18b = new Font( "Courier", Font.BOLD,  18 );
  private DecimalFormat
    formatAmount   = new DecimalFormat( "$ 0.00" );


  public void init()
  {
    lblTitle.setFont( courierFont18b );
    lblItem.setFont( arialFont12p );
    lblQty.setFont( arialFont12p );
    txtItem.setFont( courierFont12p );
    txtQty.setFont( courierFont12p );
    lblTotalAmount.setFont( courierFont12p );

    for ( nLup = 1; nLup < 6; nLup++ )
    {
      lblItemAmount[nLup]  = new Label( "        ", Label.RIGHT );
      lblItemAmount[nLup].setFont( courierFont12p );
      add( lblItemAmount[nLup] );
      lblItemAmount[nLup].setSize( 65, 23 );
      lblItemAmount[nLup].setBackground( Color.lightGray );
      lblItemAmount[nLup].setText( "        " );
    }

    add( txtItem );
    add( txtQty );
    add( lblItem );
    add( lblQty );
    add( lblTitle );
    add( lblTotalAmount );
    add( cmdCalculate );
    add( cmdTotal );
    add( cmdClear );
    add( cmdReset );

    lblTotalAmount.setBackground( Color.lightGray );
    lblItem.setBackground( Color.lightGray );
    lblQty.setBackground( Color.lightGray );
    lblTitle.setBackground( Color.lightGray );
    this.setBackground( Color.lightGray );
    say( "Project 2 - Part 4...\nRetail Sales...", "Here we go again..." );
  } // End Init()


  public void start()
  {
    cmdReset.addActionListener( this );
    cmdClear.addActionListener( this );
    cmdTotal.addActionListener( this );
    cmdCalculate.addActionListener( this );

    txtItem.setText( "" );
    txtQty.setText( "" );
    txtItem.requestFocus();
  } // End Start()


  public void actionPerformed( ActionEvent event )
  {
    if ( event.getSource() == cmdClear )
    {
      nItem   = 0;
      nQty    = 0;
      strItem.setLength( 0 );
      strQty.setLength( 0 );
      txtItem.setText( "" );
      txtQty.setText( "" );
      txtItem.requestFocus();
//      bTotal = false;
      return;
    }
    else if ( event.getSource() == cmdCalculate )
    {
      strItem.setLength( 0 );
      strItem.append( txtItem.getText() );
      strQty.setLength( 0 );
      strQty.append( txtQty.getText() );

      if ( strItem.length() < 1 ) strItem.append( "0" );
      if ( strQty.length() < 1 ) strQty.append( "0" );

      nItem = Integer.parseInt( strItem.toString() );
      nQty  = Integer.parseInt( strQty.toString() );

      if ( nItem < 1 || nItem > 5 )
      {
        say( "Invalid Item Number,\nValid Range: 1 to 5", "Invalid Input" );
        txtItem.requestFocus();
        txtItem.selectAll();
        return;
      }

      if ( nQty < 1 || ( nQty + nItemCount[nItem] ) > 99 )
      {
        say( "Invalid Quantity,\nValid Range: 1 to 99 (Total)", "Invalid Input" );
        txtQty.requestFocus();
        txtQty.selectAll();
        return;
      }
/*
      switch ( nItem )
      {
        case 1:
          dItemPrice = 2.98;
          break;
        case 2:
          dItemPrice = 4.50;
          break;
        case 3:
          dItemPrice = 9.98;
          break;
        case 4:
          dItemPrice = 4.49;
          break;
        case 5:
          dItemPrice = 6.87;
          break;
      }
*/
      nItemCount[nItem] += nQty;
      dItemTotal[nItem] = dItemPrice[nItem] * nItemCount[nItem];
//      dItemTotal[nItem] = dItemPrice * nItemCount[nItem];
      dWeeklyTotal += dItemTotal[nItem];

      txtItem.setText( strItem.toString() );
      txtQty.setText( strQty.toString() );
//      bTotal = false;
    }
    else if ( event.getSource() == cmdReset )
    {
      int
        nRet = askYesNo( "This will Reset ALL Values to '0'\nContinue Anyway?",
                         "Reset Contents" );

      if ( nRet != JOptionPane.YES_OPTION ) return;

      for (nLup = 0; nLup < 6; nLup++ )
      {
        dItemTotal[nLup] = 0.00;
        nItemCount[nLup] = 0;
      }

      dWeeklyTotal = 0.00;
      bTotal = false;
    }
    else
    {
      bTotal = true;
    }
    repaint();
  } // End actionPerformed()


  public void paint( Graphics graphicItem )
  {
    cmdCalculate.setLocation( 20, 160 );
    cmdClear.setLocation( 105, 160 );
    cmdTotal.setLocation( 20, 195 );
    cmdReset.setLocation( 105, 195 );
    lblTitle.setLocation( 20, 10 );
    lblItem.setLocation( 20, 40 );
    txtItem.setLocation( 20, 63 );
    lblQty.setLocation( 20, 90 );
    txtQty.setLocation( 20, 113 );
    lblTotalAmount.setLocation( 340, 190 );
    lblTotalAmount.setText( "" );

    graphicItem.setColor( Color.black );
    graphicItem.drawRect( 210, 20, 210, 200 );
    graphicItem.drawLine( 210, 50, 420, 50 );
    graphicItem.drawLine( 210, 185, 420, 185 );
    graphicItem.drawString( "Item", 240, 40 );
    graphicItem.drawString( "Qty.", 305, 40 );
    graphicItem.drawString( "Amt.", 375, 40 );
    graphicItem.drawString( "Week Total", 255, 207 );

    for ( nLup = 1; nLup < 6; nLup++ )
    {
      lblItemAmount[nLup].setLocation( 345, 32 + ( nLup * 23 ) );
      lblItemAmount[nLup].setSize( 65, 23 );
      lblItemAmount[nLup].setText( "" );
    }
    
    if ( bTotal )
    {
      for ( nLup = 1; nLup < 6; nLup++ )
      {
        graphicItem.drawString( "" + nLup, 248, 50 + ( nLup * 23 ) );
        graphicItem.drawString( "" + nItemCount[nLup], 310, 50 + ( nLup * 23 ) );
        lblItemAmount[nLup].setText( formatAmount.format( dItemTotal[nLup] ) );
      }
      lblTotalAmount.setText( formatAmount.format( dWeeklyTotal ) );
    }
  } // End paint()


  private void say( String strMessage, String strTitle )
  {
    JOptionPane.showMessageDialog( this,
                                   strMessage,
                                   strTitle,
                                   JOptionPane.INFORMATION_MESSAGE );
  } // End say()


  private int askYesNo( String strPrompt, String strTitle )
  {
    return JOptionPane.showConfirmDialog( this,
                                          strPrompt,
                                          strTitle,
                                          JOptionPane.YES_NO_OPTION,
                                          JOptionPane.QUESTION_MESSAGE );
  } // End askYesNo()

} // End class VTTRetail
