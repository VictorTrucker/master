/************************************************************
*   Program: Project 2 - Part 2 - VTTOdd.java               *
*    Author: Victor Trucker                                 *
*      Date: 02/09/2000                                     *
*   Purpose: This app calculates the product of the odd     *
*            integers from 1 to 37.                         *
*                                                           *
************************************************************/

import java.text.DecimalFormat;
import javax.swing.JOptionPane;

public class VTTOdd
{
  public static void main( String args[] )
  {
    int
      nLup   = 0;
    double
      dValue = 1.0;
    String
      strMsg = "";
    DecimalFormat
      formatOddProduct = new DecimalFormat( "0" );

    say( "Project 2 - Part 2,\nOdd Product..." );

    for (nLup = 3; nLup <= 37; nLup++ )
    {
      dValue = ( nLup % 2 != 0 ) ? dValue *= nLup : dValue;
    }

    strMsg = "The Product of the Odd Integers from\n1 to 37 is:  ";
    strMsg += formatOddProduct.format( dValue ) + "   ";
    say( strMsg );

    System.exit( 0 );
  } // end main()

  private static void say( String strWhat )
  {
    JOptionPane.showMessageDialog( null,
                                   strWhat,
                                   "Yup, Sure...",
                                   JOptionPane.INFORMATION_MESSAGE );
  } // end say()
} // end class VTTGross
