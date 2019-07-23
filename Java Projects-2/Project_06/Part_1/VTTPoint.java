/************************************************************
*   Program: Project 6 - Part 1 - VTTPoint.java             *
*    Author: Victor Trucker                                 *
*      Date: 03/14/2000                                     *
*   Purpose: This class manages point objects, maintaining  *
*            X and Y coordinates...                         *
************************************************************/

//package VTTCircles;


public class VTTPoint
{
  private int
    x = 0,
    y = 0,
    z = 0;


  public VTTPoint(int nX, int nY, int nZ )
  {
    setPoint( nX, nY, nZ );
  } // end VTTPoint( int, int )


  public void setPoint( int nX, int nY, int nZ )
  {
    x = nX;
    y = nY;
    z = nZ;
  } // end setPoint( int, int )


  public void setX( int nX )
  {
    x = nX;
  } // end setX()


  public void setY( int nY )
  {
    y = nY;
  } // end setY()


  public void setZ( int nZ )
  {
    z = nZ;
  } // end setZ()


  public int getX()
  {
    return x;
  } // end getX()


  public int getY()
  {
    return y;
  } // end getY()


  public int getZ()
  {
    return z;
  } // end getZ()

} // end class VTTPoint
