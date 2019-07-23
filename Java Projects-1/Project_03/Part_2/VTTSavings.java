/************************************************************
*   Program: Project 3 - Part 2 - VTTSavings.java           *
*    Author: Victor Trucker                                 *
*      Date: 02/26/2000                                     *
*   Purpose: This class manages savings account objects.    *
*            Calculates the intrest income for a given      *
*            interest rate and given account balance...     *
************************************************************/

import java.text.DecimalFormat;
import javax.swing.JOptionPane;


public class VTTSavings
{
  private static double
    dRate     = 0.00;
  private double
    dBalance  = 0.00,
    dInterest = 0.00;
  private DecimalFormat
    formatMoney  = new DecimalFormat( "$ 0.00" );
  private String
    strAccountID = new String( "0000" );


  public VTTSavings()
  {
    dBalance = getInput( "Input Initial Balance:" );
  }


  public VTTSavings( double dInitialBalance )
  {
    if ( dInitialBalance < 0.00 )
    {
      say( "Invalid Balance: " + dInitialBalance + "\nSetting to 0.00", "Error" );
      dBalance = 0.00;
    }
    else
      dBalance = dInitialBalance;
  }


  public double setBalance( double dAmount )
  {
    if ( dAmount < 0.00 )
    {
      say( "Invalid Balance: " + dAmount + "\nSetting to 0.00", "Error" );
      dBalance = 0.00;
    }
    else
      dBalance = dAmount;
      
    return dBalance;
  } // End setBalance()


  public double setBalance( String strAccountID )
  {
    dBalance = -1.00;
    
    while ( dBalance < 0.00 )
      dBalance = getInput( "Input Balance for Account " + strAccountID + ":" );

    return dBalance;
  } // End setBalance()


  public double getBalance()
  {
    return dBalance;
  } // End getBalance()


  public void deposit( double dAmount )
  {
    dBalance += dAmount;
  } // End makeDeposit()


  public boolean withdrawal( double dAmount )
  {
    if ( dAmount <= dBalance )
    {
      dBalance -= dAmount;
      return true;
    }
    else
    {
      say( "Withdrawal exceeds Balance!", "Transaction Error" );
      return false;
    }
  } // End makeWithdrawal()


  public static void setInterestRate( double dNewRate )
  {
    dRate = dNewRate / 100;
  }


  public static boolean setInterestRate()
  {
    double
      dNewRate = getInput( "Input Interest Rate\nValid Range: 0.01 to 15.00" );

    if ( dNewRate > 0.00 && dNewRate <= 15.00 )
    {
      dRate = dNewRate / 100;
      return true;
    }
    else
    {
      say( "Interest Rate is Out of Range!\nRange: 0.01 to 15.00", "Error" );
      return false;
    }
  } // End setInterestRate()


  public static double getInterestRate()
  {
    return dRate;
  } // End getInterestRate()


  public double calculateIntrest()
  {
    return dInterest = ( dBalance * dRate ) / 12;
  } // End calculateMonthlyIntrest()


  public void setAccountNumber( String strID )
  {
    strAccountID = strID;
  } // End setAccountNumber()


  public String getAccountNumber()
  {
    return strAccountID;
  } // End getAccountNumber()


  public String toString()
  {
    return "\nEnding Balance:   " + formatMoney.format( dBalance );
  } // End toString()


  private static double getInput( String strPrompt )
  {
    String
      strValue = "";

    strValue = JOptionPane.showInputDialog( strPrompt );

    if ( strValue.length() < 1 )
      strValue = "0.00";

    return Double.parseDouble( strValue );
  } // end getInput()


  public static void say( String strMessage, String strTitle )
  {
    JOptionPane.showMessageDialog( null,
                                   strMessage,
                                   strTitle,
                                   JOptionPane.INFORMATION_MESSAGE );
  } // end say()
  
} // end class VTTSavings

