/************************************************************
*   Program: Project 4 - Part 1 - VTTSquare.java            *
*    Author: Victor Trucker                                 *
*      Date: 02/29/2000                                     *
*   Purpose: This class manages Squares.  It uses a single  *
*            dimension to calculate each of it's verticies. *
*            It stores the master corner in a VTTPoint.     *
*            It calculates the other points from this point *
*            and the user provided nLength...               *
************************************************************/

//package VTTCubeCompose;


public class VTTSquare
{
  private VTTPoint
    pointUL;
  private int
    nLengthMin   = 1,
    nLengthMax   = 30,
    nLength      = 0;
  private StringBuffer
    strSquareOut = new StringBuffer( "" );


  public VTTSquare()
  {
    pointUL = new VTTPoint( 1, 1, 1 );
    pointUL.setX();
    pointUL.setY();
    pointUL.setZ();

    setSquare( pointUL );
  } // End VTTRectangle()


  public VTTSquare( VTTPoint ptUL )
  {
    setSquare( ptUL );
  } // End VTTSquare( VTTPoint )


  public VTTSquare( VTTPoint ptUL, int nWidth, int nDepth )
  {
    setSquare( ptUL, nWidth, nDepth );
  } // End VTTSquare( VTTPoint, int, int )


  public void setSquare( VTTPoint ptUL )
  {
    VTTValue
      value = new VTTValue();

    nLength = 0;
    while ( nLength < 1 )
    {
      value = VTTUtils.getInput( "Input Length of square" +
                                 "\n( Integers Only )"    +
                                 "\nValid Range: 1 to 30",
                                 VTTUtils.GET_INTEGER );
      nLength = ( value.nValue > 0 && value.nValue < 31 ) ? value.nValue : 0;
      if ( setSide( nLength ) < 1 )
        VTTUtils.say( "Length is Invalid!\nValid Range: 1 to 30", "Input Error" );
    }

    setSquare( ptUL, nLength, 0 );
  } // End setSquare()


  public void setSquare( VTTPoint ptUL, int nLength, int nDepth )
  {
    VTTPoint.setOffset( ( nDepth > 0 ) ? 0 : 30 );
    pointUL = new VTTPoint( ptUL.getX(),           ptUL.getY(),           ptUL.getZ() + nDepth );
    VTTPoint.setOffset( ( nLength > 0 ) ? 0 : 30 );
    VTTPoint
      pointUR = new VTTPoint( ptUL.getX() + nLength, ptUL.getY(),           ptUL.getZ() + nDepth ),
      pointLL = new VTTPoint( ptUL.getX(),           ptUL.getY() + nLength, ptUL.getZ() + nDepth ),
      pointLR = new VTTPoint( ptUL.getX() + nLength, ptUL.getY() + nLength, ptUL.getZ() + nDepth );

    strSquareOut.append( pointUL.toString( 1 ) );
    strSquareOut.append( pointUR.toString( 2 ) );
    strSquareOut.append( pointLL.toString( 3 ) );
    strSquareOut.append( pointLR.toString( 4 ) );

    if ( nDepth < 1 )
    {
      strSquareOut.append( "\n\nLength:       " + VTTUtils.formatLine.format( nLength ) );
      strSquareOut.append( "\nWidth:        "   + VTTUtils.formatLine.format( nLength ) );
      strSquareOut.append( "\nSquare Area:  "   + VTTUtils.formatArea.format( getArea() ) );
    }
  } // End setSquare()


  public int setSide( int nNewValue )
  {
    nLength = ( nNewValue >= nLengthMin && nNewValue <= nLengthMax ) ? nNewValue : 0;

    return nLength;
  } // End setSide()


  public int getSide()
  {
    return nLength;
  } // End getDimension()


  public int getArea()
  {
    return ( getSide() * getSide() );
  } // End getArea()


  public String toString()
  {
    return strSquareOut.toString();
  } // End toString()

} // end class VTTSquare

