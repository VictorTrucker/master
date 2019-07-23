/**************************************************
*   Program: Project 1 - Part 1 - VTTGross.java   *
*    Author: Victor Trucker                       *
*      Date: 02/03/2000                           *
*   Purpose: This app accepts the input of sales  *
*            amounts and calculates commissions,  *
*            and gross pay...                     *
**************************************************/

import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class VTTGross
{
  public static void main( String args[] )
  {
    int
      nEmpProcessed = 0;
    double
      dSalesAmt   = 0.0,
      dBasePay    = 250.0,
      dCommission = 0.0,
      dGrossPay   = 0.0;
    String
      strMsg      = "",
      strTitle    = "";
    DecimalFormat
      formatSalesAmt   = new DecimalFormat( "0.00" ),
      formatCommission = new DecimalFormat( "0.00" ),
      formatGrossPay   = new DecimalFormat( "0.00" );

    say( "Project 1 - Part 1,\nGross Pay...", "Introduction" );
    dSalesAmt = getSalesAmt();

    while ( dSalesAmt != 0.00 )
    {
      // determine the commission
      nEmpProcessed += 1;
      if ( dSalesAmt > 0.00 )
      {
        if( dSalesAmt <= 4999.99 )
        {
          dCommission = ( dSalesAmt * .08 );
          strTitle = "Nice Job";
        }
        else if(dSalesAmt <= 7999.99)
        {
          dCommission = ( dSalesAmt * .10 );
          strTitle = "Great Job";
        }
        else if(dSalesAmt <= 10999.99)
        {
          dCommission = ( dSalesAmt * .13 );
          strTitle = "Superior Performance";
        }
        else
        {
          dCommission = ( dSalesAmt * .15 );
          strTitle = "Top Producer!";
        }
      }
      else
      {
        dCommission = -1;
        strTitle = "Hasta La Vista, Baby!";
      }

      // Calculate gross pay and display
      dGrossPay = dBasePay + dCommission;
      formatCommission.format( dCommission );
      formatGrossPay.format( dGrossPay );

      if ( dCommission > 0.00 )
        strMsg = "Sales Amount: " + formatSalesAmt.format( dSalesAmt ) +
          "\nCommission: " + formatCommission.format( dCommission ) +
          "\nGross Pay: " + formatGrossPay.format( dGrossPay );
      else
        strMsg = "You're Fired!";

      say ( strMsg, strTitle );
      dSalesAmt = getSalesAmt();
    }

    say( "Employees Processed:  [ " + nEmpProcessed + " ]", "Yup, Sure..." );

    System.exit( 0 );
  } // end main

  private static double getSalesAmt()
  {
    String
      strSalesAmt = "";
    double
      dSalesAmt   = 0.0;

    strSalesAmt = JOptionPane.showInputDialog( "Enter Sales Amount: " );

    if (( strSalesAmt == null ) || ( strSalesAmt.length() < 1 ))
      strSalesAmt = "0.00";

    dSalesAmt = Double.parseDouble( strSalesAmt );

    return dSalesAmt;
  } // end getSalesAmt()

  private static void say(String strWhat, String strTitle)
  {
    JOptionPane.showMessageDialog( null,
                                   strWhat,
                                   strTitle,
                                   JOptionPane.INFORMATION_MESSAGE );
  } // end say()
} // end class VTTGross
