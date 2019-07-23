/************************************************************
*   Program: Project 3 - Part 1 - VTTRectangle.java         *
*    Author: Victor Trucker                                 *
*      Date: 02/23/2000                                     *
*   Purpose: This class manages rectangles.  Calculates     *
*            Height, Width, Perimeter and Area...           *
************************************************************/

import java.text.DecimalFormat;


public class VTTRectangle
{
  private VTTPoint
    pointUL = new VTTPoint(),
    pointUR = new VTTPoint(),
    pointLL = new VTTPoint(),
    pointLR = new VTTPoint();
  private int
    nErrorCode   = 0;
  private DecimalFormat
    formatValue  = new DecimalFormat( "00.0" );
  private String
    strMessage[] = { "\n\nIs a valid Rectangle.",
                     "\n\nIs also a Square.",
                     "\n\nIs skewed (not perpendicular).",
                     "\n\nCoordinate(s) out of range!\nValid Range: 0.0 to 20.0" };


  public VTTRectangle()
  {
    setRectangle( pointUL, pointUR, pointLL, pointLR );
  } // End VTTRectangle()


  public VTTRectangle( VTTPoint ptUL, VTTPoint ptUR,
                       VTTPoint ptLL, VTTPoint ptLR )
  {
    setRectangle( ptUL, ptUR, ptLL, ptLR );
  } // End VTTRectangle( VTTPoint, ... )


  public boolean setRectangle( VTTPoint ptUL, VTTPoint ptUR,
                               VTTPoint ptLL, VTTPoint ptLR )
  {
    pointUL = ptUL;
    pointUR = ptUR;
    pointLL = ptLL;
    pointLR = ptLR;

    if ( !isValidRange() )
    {
      nErrorCode = 3;
    }
    else if ( isSkewed() )
    {
      nErrorCode = 2;
    }
    else if ( isSquare() )
    {
      nErrorCode = 1;
    }
    else
    {
      nErrorCode = 0;
    }
    
    return true;
  } // End setRectangle()


  public double getLength()
  {
    double
      dSide1 = pointLL.getY() - pointUL.getY(),
      dSide2 = pointUR.getX() - pointUL.getX();

    return ( dSide1 > dSide2 ) ? dSide1 : dSide2;
  } // End getHeight()


  public double getWidth()
  {
    double
      dSide1 = pointLL.getY() - pointUL.getY(),
      dSide2 = pointUR.getX() - pointUL.getX();

    return ( dSide1 < dSide2 ) ? dSide1 : dSide2;
  } // End getWidth()


  public double getPerimeter()
  {
    return ( getLength() + getWidth() ) * 2;
  } // End getPerimeter()


  public double getArea()
  {
    return getLength() * getWidth();
  } // End getArea()


  private boolean isValidRange()
  {
    return ( inRange( pointUL ) && inRange( pointUR ) &&
             inRange( pointLL ) && inRange( pointLR ) );
  } // End isValidRange()


  private boolean inRange( VTTPoint pt )
  {
    return ( pt.getX() >= 0.0 && pt.getX() <= 20.0 &&
             pt.getY() >= 0.0 && pt.getY() <= 20.0 );
  } // End inRange()


  private boolean isSkewed()
  {
    return ( pointUL.getY() != pointUR.getY() ||
             pointUL.getX() != pointLL.getX() ||
             pointLL.getY() != pointLR.getY() ||
             pointUR.getX() != pointLR.getX() );
  } // End isSkewed()


  private boolean isSquare()
  {
    return ( getLength() == getWidth() );
  } // End isSquare()


  public String toString()
  {
    StringBuffer
      strOutput = new StringBuffer( "" );

    strOutput.append( pointUL.toString( 1 ) );
    strOutput.append( pointUR.toString( 2 ) );
    strOutput.append( pointLL.toString( 3 ) );
    strOutput.append( pointLR.toString( 4 ) );

    strOutput.append( strMessage[nErrorCode] );
    strOutput.append( "\n\nLength: "    + formatValue.format( getLength() ) );
    strOutput.append( "\nWidth:  "      + formatValue.format( getWidth() ) );
    strOutput.append( "\n\nArea:      " + formatValue.format( getArea() ) );
    strOutput.append( "\nPerimeter: "   + formatValue.format( getPerimeter() ) );

    return strOutput.toString();
  } // End toString()

} // end class VTTRectangle

