/************************************************************
*   Program: Project 3 - Part 1 - VTTRectangleDriver.java   *
*    Author: Victor Trucker                                 *
*      Date: 02/24/2000                                     *
*   Purpose: This class is the driver for the VTTRectangle  *
*            class.  Tests the aspects of the rectangle...  *
************************************************************/

import javax.swing.JOptionPane;


public class VTTRectangleDriver
{
  public static void main( String args[] )
  {
    int
      nLup  = 0;
    double
      dUL[] = { 0,  5.5,  5.5,  3,  8.4, -5.5, -5.5, 20, 30, 10.5, 10.5,  5,  5 },
      dUR[] = { 0, 16.2,  5.5, 15,  8.4, 10.5,  5.5, 30, 30, 15.7, 10.0, 20,  5 },
      dLL[] = { 0,  5.5, 16.2,  3, 12.8,  5.5, 10.5, 20, 40, 10.5, 17.2,  5, 15 },
      dLR[] = { 0, 16.2, 16.2, 15, 12.8, 10.5, 10.5, 30, 40, 15.7, 17.2, 20, 15 };
    String
      strBreak   = "\n==========================================\n";
    StringBuffer
      strOutput  = new StringBuffer( "" );
    VTTPoint
      pointSet[] = new VTTPoint[5];
    VTTRectangle
      rectSet[]  = new VTTRectangle[7];

    say( "Project 3 - Part 1\nRectangle...", "Yadda, Yadda, Yadda" );
    System.out.println( strBreak );
    
    for ( nLup = 1; nLup < 7; nLup++ )
    {
      pointSet[1] = new VTTPoint( dUL[nLup * 2 - 1], dUL[nLup * 2] ); // UL
      pointSet[2] = new VTTPoint( dUR[nLup * 2 - 1], dUR[nLup * 2] ); // UR
      pointSet[3] = new VTTPoint( dLL[nLup * 2 - 1], dLL[nLup * 2] ); // LL
      pointSet[4] = new VTTPoint( dLR[nLup * 2 - 1], dLR[nLup * 2] ); // LR

      rectSet[nLup] = new VTTRectangle( pointSet[1], pointSet[2],
                                        pointSet[3], pointSet[4] );

      strOutput.setLength( 0 );
      strOutput.append( "Rectangle [ " + nLup + " ]\n" );
      strOutput.append( rectSet[nLup].toString() );

      System.out.println( strOutput.toString() );
      System.out.println( strBreak );
      say( strBreak + strOutput.toString() + strBreak, "Rectangle" );
    }

    System.out.println( "Adios! JOptionPane..." );
    say( "Buh-Bye, DOS Window...", "Finished" );
    System.exit( 0 );
  } // end main()


  private static void say( String strMessage, String strTitle )
  {
    JOptionPane.showMessageDialog( null,
                                   strMessage,
                                   strTitle,
                                   JOptionPane.INFORMATION_MESSAGE );
  } // end say()
  
} // end class VTTRectangleDriver

