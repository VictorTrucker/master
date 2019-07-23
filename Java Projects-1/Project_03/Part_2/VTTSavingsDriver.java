/************************************************************
*   Program: Project 3 - Part 2 - VTTSavingsDriver.java     *
*    Author: Victor Trucker                                 *
*      Date: 02/27/2000                                     *
*   Purpose: This class is the driver for the VTTSavings    *
*            class.  Tests the aspects of the account...    *
************************************************************/

import javax.swing.JOptionPane;
import java.text.DecimalFormat;


public class VTTSavingsDriver
{
  private static String
    strBreak   = "\n==========================================\n";
  private static StringBuffer
    strOutput  = new StringBuffer( "" );
  private static DecimalFormat
    formatRate = new DecimalFormat( "0.00 %" ),
    formatAmt  = new DecimalFormat( "$ 0.00" );

  public static void main( String args[] )
  {
    int
      nLup  = 0;
    double
      dRate      = 0.00,
      dInterest1 = 0.00,
      dInterest2 = 0.00,
      dTestAmt1  = 0.00,
      dTestAmt2  = 0.00;
    String
      strAcctNo1 = new String( "" ),
      strAcctNo2 = new String( "" );
    VTTSavings
      saver1     = new VTTSavings( 1.00 ),
      saver2     = new VTTSavings( 1.00 );

    VTTSavings.say( "Project 3 - Part 2\nSavings Account...", "Yadda, Yadda, Yadda" );
    System.out.println( strBreak );

    for ( nLup = 1; nLup < 3; nLup++ )
    {
      if ( nLup > 1 )
        while ( VTTSavings.setInterestRate() != true );
      else
        VTTSavings.setInterestRate( 4.00 );
        
      dRate = VTTSavings.getInterestRate();
      saver1.setAccountNumber( strAcctNo1 = "0001" );
      saver2.setAccountNumber( strAcctNo2 = "0002" );

      if ( nLup == 1)
      {
        dTestAmt1 = saver1.setBalance( 2000.00 );
        dTestAmt2 = saver2.setBalance( 3000.00 );
//        dTestAmt1 = saver1.setBalance( strAcctNo1 );
//        dTestAmt2 = saver2.setBalance( strAcctNo2 );
      }
      else
      {
        dTestAmt1 = saver1.getBalance();
        dTestAmt2 = saver2.getBalance();
      }

      strOutput.setLength( 0 );

      if ( dTestAmt1 > 0.00 )
      {
        buildOutputLine( saver1, dTestAmt1 );
      }
      else
      {
        strOutput.append( "Account Number:   " + strAcctNo1 + "\n >> Has a Zero Balance <<" );
      }

      if ( dTestAmt2 > 0.00 )
      {
        strOutput.append( "\n\n" );
        buildOutputLine( saver2, dTestAmt2 );
      }
      else
      {
        strOutput.append( "\n\nAccount Number:   " + strAcctNo2 + "\n >> Has a Zero Balance <<" );
      }

      printDOS( strOutput.toString() );
      VTTSavings.say( strBreak + strOutput.toString() + strBreak, "Savings Account" );
    }
    
    printDOS( "Adios! JOptionPane..." );
    VTTSavings.say( "Buh-Bye, DOS Window...", "Finished" );
    System.exit( 0 );
  } // end main()


  private static void buildOutputLine( VTTSavings which, double dBal )
  {
    double
      dInterest   = which.calculateIntrest();
    String
      strAcctNo   = which.getAccountNumber(),
      strBalance  = formatAmt.format( which.getBalance() ),
      strRate     = formatRate.format( which.getInterestRate() ),
      strInterest = formatAmt.format( dInterest );

    strOutput.append( "Account Number:   "     + strAcctNo );
    strOutput.append( "\nInitial Balance:  "   + strBalance );
    strOutput.append( "\nIterest Rate:       " + strRate );
    strOutput.append( "\nMonthly Interest: "   + strInterest );
    which.deposit( dInterest );
    strOutput.append( which.toString() );
  } // End buildOutputLine()


  private static void printDOS( String strMessage )
  {
    System.out.println( strMessage );
    System.out.println( strBreak );
  } // end print()

} // end class VTTSavingsDriver

