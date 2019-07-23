/************************************************************
*   Program: Project 2 - Part 1 - VTTSmallest.java          *
*    Author: Victor Trucker                                 *
*      Date: 02/09/2000                                     *
*   Purpose: This app accepts the input of integers and     *
*            determines the Smallest integer entered.       *
*                                                           *
************************************************************/

import javax.swing.JOptionPane;

public class VTTSmallest
{
  public static void main( String args[] )
  {
    int
      nLup        = 0,
      nValue      = 0,
      nSmallest   = 32768,
      nIterations = 0;
    String
      strMsg         = "";

    say( "Project 2 - Part 1,\nSmallest Integer..." );

    nIterations = getInput( "Input Number of Iterations\n(3 to 25)..." );

    nValue = getInput( "Input First Integer:" );
    nSmallest = ( nSmallest < nValue ) ? nSmallest : nValue;
    nIterations--;

    for ( nLup = 1; nLup <= nIterations; nLup++ )
    {
      nValue = getInput( "Input Next Integer:" );
      nSmallest = ( nSmallest < nValue ) ? nSmallest : nValue;
    }

    strMsg = "The Smallest Integer Entered was: [ " + nSmallest + " ]   ";
    say( strMsg );

    System.exit( 0 );
  } // end main()

  private static int getInput(String strPrompt)
  {
    int
      nValue   = 0;
    String
      strValue = "";

    strValue = JOptionPane.showInputDialog( strPrompt );

    if (( strValue == null ) || ( strValue.length() < 1 ))
      strValue = "0";

    nValue = Integer.parseInt( strValue );

    return nValue;
  } // end getInput()

  private static void say( String strWhat )
  {
    JOptionPane.showMessageDialog( null,
                                   strWhat,
                                   "Yup, Sure...",
                                   JOptionPane.INFORMATION_MESSAGE );
  } // end say()
} // end class VTTGross
