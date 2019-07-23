/************************************************************
*   Program: Project 9 - Part 1 - VTTExceptions.java        *
*    Author: Victor Trucker                                 *
*      Date: 04/23/2000                                     *
*   Purpose: This Application Generates Errors, kinda' like *
*            the real world...                              *
************************************************************/

import java.awt.*;
import java.util.Vector;


public class VTTExceptions
{
  private int
    nLup   = 0,
    yes    = VTTUtils.YES,
    no     = VTTUtils.NO;
  private Label
    label  = new Label( "ABC" );


  public void go()
  {
    if ( VTTUtils.askYesNo( "Project 9 - Part 1\nExceptions...", "Ooops..." ) == yes )
    {
      runTests();

      VTTUtils.say( "Exception Testing Complete...", "Finished" );
      System.out.println( "Later JOptionPane..." );
      VTTUtils.say( "Buh-Bye, DOS Window...", "Still Finished" );
    }

    System.exit( 0 );
  } // end main()


  private void runTests()
  {
    String
      strMessage = "";

    if ( VTTUtils.askYesNo( "Test Null Pointer Exception", "Test Case" ) == yes )
    {
      try
      {
        nullPointerException();
      }
      catch ( NullPointerException e )
      {
        System.out.println( e.getMessage() );
        e.printStackTrace();
        strMessage = new String( adjustMessage( ( e.getMessage() == null )
                                                  ? "null (no message)"
                                                  : e.getMessage() ) );

        strMessage = new String( "Error Message: " + strMessage );
        VTTUtils.cry( strMessage, "Null Pointer Exception" );
      }
      finally
      {
        System.err.println( strMessage );
        System.err.println( "Null Pointer Exception Test Complete" );
      }
    }

    if ( VTTUtils.askYesNo( "Test Negative Array Size Exception", "Test Case" ) == yes )
    {
      try
      {
        negativeArraySizeException();
      }
      catch ( NegativeArraySizeException e )
      {
        System.out.println( e.getMessage() );
        e.printStackTrace();
        strMessage = new String( adjustMessage( ( e.getMessage() == null )
                                                  ? "null (no message)"
                                                  : e.getMessage() ) );

        strMessage = new String( "Error Message: " + strMessage );
        VTTUtils.cry( strMessage, "Negative Array Size Exception" );
      }
      finally
      {
        System.err.println( strMessage );
        System.err.println( "Negative Array Size Exception Test Complete" );
      }
    }

    if ( VTTUtils.askYesNo( "Test Index Out Of Bounds Exception", "Test Case" ) == yes )
    {
      try
      {
        indexOutOfBoundsException();
      }
      catch ( IndexOutOfBoundsException e )
      {
        System.out.println( e.getMessage() );
        e.printStackTrace();
        strMessage = new String( adjustMessage( ( e.getMessage() == null )
                                                  ? "null"
                                                  : e.getMessage() ) );

        strMessage = new String( "Error Message: " + strMessage );
        VTTUtils.cry( strMessage, "Index Out Of Bounds Exception" );
      }
      finally
      {
        System.err.println( strMessage );
        System.err.println( "Index Out Of Bounds Exception Test Complete" );
      }
    }
  } // end runTests()


  private void nullPointerException() throws NullPointerException
  {
    label = null;
    label.setEnabled( false );
  } // end nullPointerException()


  private void negativeArraySizeException() throws NegativeArraySizeException
  {
    String
      str[] = new String[-1];
  } // end negativeArraySizeException()


  private void indexOutOfBoundsException() throws IndexOutOfBoundsException
  {
    Vector
      vector = new Vector( 1, 1 );
    String
      strArray[] = { "First", "Second", "Third", "Fourth", "Fifth" };

    vector.removeAllElements();
    for ( nLup = 0; nLup < 5; nLup++ )
    {
      vector.addElement( strArray[nLup] );
    }

    for ( nLup = 0; nLup < 6; nLup++ )
    {
      String str = new String( ( String )vector.elementAt( nLup ) );
    }
  } // end indexOutOfBoundsException()


  private String adjustMessage( String str )
  {
    return ( str.equals( "" ) ) ? new String( "No Error Message Provided" ) : str;
  } // end adjustMessage( String )

} // end class VTTExceptions

