/************************************************************
*   Program: Project 4 - Part 2 - VTTSquare.java            *
*    Author: Victor Trucker                                 *
*      Date: 03/03/2000                                     *
*   Purpose: This class manages Squares.  It uses a single  *
*            dimension to calculate each of it's verticies. *
*            It stores the Upper Left Hand Corner in it's   *
*            Parent class VTTPoint.  It uses this parent to *
*            calculate all of it's dimensions relative to   *
*            the ULHC using the user provided nLength...    *
************************************************************/

//package VTTCubeInherit;


public class VTTSquare extends VTTPoint
{
  private int
    nLengthMin   = 1,
    nLengthMax   = 30,
    nLength      = 0;
  private StringBuffer
    strSquareOut = new StringBuffer( "" );


  public VTTSquare()
  {
    initSquare( 1 );
  } // End VTTSquare


  public VTTSquare( VTTSquare square )
  {
    getPoint().setX( square.getParent().getX() );
    getPoint().setY( square.getParent().getY() );
    getPoint().setZ( square.getParent().getZ() );

    setSquare( square.getSide(), square.getSide() );
  } // End VTTSquare( VTTSquare )


  public VTTSquare( int nDepth )
  {
    initSquare( nDepth );
  } // End VTTSquare( int )


  public void initSquare( int nDepth )
  {
    if ( nDepth < 1 )
    {
      VTTValue
        value = new VTTValue();

      while ( !super.setX() );
      while ( !super.setY() );
      while ( !super.setZ() );

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
    }

    setSquare( nLength, nDepth );
  } // End initSquare( int )


  public void setSquare( int nLength, int nDepth )
  {
    VTTPoint.setOffset( ( nDepth > 0 ) ? 0 : 30 );
    VTTPoint
      pointUL = new VTTPoint( super.getX(),           super.getY(),           super.getZ() + nDepth );
    VTTPoint.setOffset( ( nLength > 0 ) ? 0 : 30 );
    VTTPoint
      pointUR = new VTTPoint( super.getX() + nLength, super.getY(),           super.getZ() + nDepth ),
      pointLL = new VTTPoint( super.getX(),           super.getY() + nLength, super.getZ() + nDepth ),
      pointLR = new VTTPoint( super.getX() + nLength, super.getY() + nLength, super.getZ() + nDepth );

    strSquareOut.setLength( 0 );
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

  } // End setSquare( int, int )


  public int setSide( int nNewValue )
  {
    nLength = ( nNewValue >= nLengthMin && nNewValue <= nLengthMax ) ? nNewValue : 0;

    return nLength;
  } // End setSide( int )


  public int getSide()
  {
    return nLength;
  } // End getSide()


  public int getArea()
  {
    return ( nLength * nLength );
  } // End getArea()


  public VTTPoint getParent()
  {
    return super;
  } // End getParent()


  public String toString()
  {
    return strSquareOut.toString();
  } // End toString()

} // end class VTTSquare

