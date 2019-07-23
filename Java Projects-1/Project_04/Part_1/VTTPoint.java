/************************************************************
*   Program: Project 4 - Part 1 - VTTPoint.java             *
*    Author: Victor Trucker                                 *
*      Date: 03/02/2000                                     *
*   Purpose: This class manages point objects, maintaining  *
*            X and Y coordinates...                         *
************************************************************/

//package VTTCubeCompose;


public class VTTPoint
{
  private static int
    nOffset = 30;
  private int
    xMin    = 1,
    xMax    = 50,
    x       = 1,
    yMin    = 1,
    yMax    = 50,
    y       = 1,
    zMin    = 1,
    zMax    = 50,
    z       = 1;
  private VTTValue
    value   = new VTTValue();
  private String
    strInputLine  = " Coordinate:\n( Integers Only )\nValid Range: 1 to 20" ,
    strInputError = " Value is Invalid!\nValid Range: 1 to 20",
    strCorner[]   = { "\n",
                      "\nUpper Left:   ", "\nUpper Right:  ",
                      "\nLower Left:   ", "\nLower Right:  " };


  public VTTPoint()
  {
    while ( !setX() );
    while ( !setY() );
    while ( !setZ() );
  } // End VTTPoint()


  public VTTPoint( int nX, int nY, int nZ )
  {
    setPoint( nX, nY, nZ );
  } // End VTTPoint( int, int, int )


  private void setPoint( int nX, int nY, int nZ )
  {
    while ( !setX( nX ) );
    while ( !setY( nY ) );
    while ( !setZ( nZ ) );
  } // End setPoint( int, int, int )


  public static void setOffset( int nNewValue )
  {
    nOffset = nNewValue;
  } // End setOffset()


  public void spawnPoint()
  {
    setPoint( 10, 10, 10 );
  } // End spawnPoint()


  public VTTPoint getPoint()
  {
    return this;
  } // End getPoint()


  public int getX()
  {
    return x;
  } // End getX()


  public int getY()
  {
    return y;
  } // End getY()


  public int getZ()
  {
    return z;
  } // End getZ()


  public boolean setX()
  {
    value.initValues();
    while ( value.nValue < 0 )
      value = VTTUtils.getInput( "Input [ X ]" + strInputLine, VTTUtils.GET_INTEGER );

    return setX( value.nValue );
  } // End setX()


  public boolean setY()
  {
    value.initValues();
    while ( value.nValue < 0 )
      value = VTTUtils.getInput( "Input [ Y ]" + strInputLine, VTTUtils.GET_INTEGER );

    return setY( value.nValue );
  } // End setY()


  public boolean setZ()
  {
    value.initValues();
    while ( value.nValue < 0 )
      value = VTTUtils.getInput( "Input [ Z ]" + strInputLine, VTTUtils.GET_INTEGER );

    return setZ( value.nValue );
  } // End setZ()


  public boolean setX( int nX )
  {
    int
      nHold = set( nX, xMin, xMax, 'X' );

    return ( ( x = ( nHold > 0 ) ? nHold : 0 ) > 0 );
  } // End setX( int )


  public boolean setY( int nY )
  {
    int
      nHold = set( nY, yMin, yMax, 'Y' );

    return ( ( y = ( nHold > 0 ) ? nHold : 0 ) > 0 );
  } // End setY( int )


  public boolean setZ( int nZ )
  {
    int
      nHold = set( nZ, zMin, zMax, 'Z' );

    return ( ( z = ( nHold > 0 ) ? nHold : 0 ) > 0 );
  } // End setZ( int )


  private int set( int nValue, int nMin, int nMax, char cAxis )
  {
    if ( nValue >= nMin && nValue <= ( nMax - nOffset ) )
    {
      return nValue;
    }
    else
    {
      VTTUtils.say( cAxis + strInputError, "Input Error" );
      return 0;
    }
  } // End set( int, int, int, char )


  public String toString()
  {
    return toString( 0 );
  } // End toString()


  public String toString( int nWhich )
  {
    String
      strPointOut = strCorner[nWhich]              + "[ X = "   +
                    VTTUtils.formatInt.format( x ) + " :: Y = " +
                    VTTUtils.formatInt.format( y ) + " :: Z = " +
                    VTTUtils.formatInt.format( z ) + " ]";

    return strPointOut;
  } // End toString( int )

} // End class VTTPoint
