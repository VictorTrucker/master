/************************************************************
*   Program: Project 3 - Part 1 - VTTPoint.java             *
*    Author: Victor Trucker                                 *
*      Date: 02/23/2000                                     *
*   Purpose: This class manages point objects, containing   *
*            x and y coordinates.                           *
************************************************************/

import java.text.DecimalFormat;


public class VTTPoint
{
  private double
    x,
    y;
  private DecimalFormat
    formatValue = new DecimalFormat( "00.0" );
  private String
    strCorner[] = { "\n",
                    "\nUpper Left:   ", "\nUpper Right:  ",
                    "\nLower Left:   ", "\nLower Right:  " };

  public VTTPoint()
  {
    setPoint( 0.0, 0.0 );
  } // End VTTPoint()


  public VTTPoint( double dX, double dY )
  {
    setPoint( dX, dY );
  } // End VTTPoint( int, int )


  public void setPoint( double dX, double dY )
  {
    x = dX;
    y = dY;
  } // End setPoint()


  public double getX()
  {
    return x;
  } // End getX()


  public double getY()
  {
    return y;
  } // End getY()


  public String toString()
  {
    return toString( 0 );
  } // End toString()


  public String toString( int nWhich )
  {
    String
      strOutput = strCorner[nWhich]       + "[ X = "   +
                  formatValue.format( x ) + " :: Y = " +
                  formatValue.format( y ) + " ]";
                  
    return strOutput;
  } // End toString( int )

} // end class VTTPoint

