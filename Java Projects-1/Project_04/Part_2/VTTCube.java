/************************************************************
*   Program: Project 4 - Part 2 - VTTCube.java              *
*    Author: Victor Trucker                                 *
*      Date: 03/04/2000                                     *
*   Purpose: This class manages Cube Objects. It offers     *
*            interfaces to Corner Points, Surface Area,     *
*            and Volume...  Essentially, it just extends    *
*            VTTSquare with a new dimension...  Height...   *
************************************************************/

//package VTTCubeInherit;


public class VTTCube extends VTTSquare
{
  private StringBuffer
    strCubeOut = new StringBuffer( "" );
  private int
    nDepth     = 0;


  public VTTCube()
  {
    VTTPoint.setOffset( 30 );
    setCube();
  } // End VTTCube()


  public void setCube()
  {
    int
      nLup      = 0;
    super.initSquare( 0 );
    nDepth      = super.getSide();
    VTTSquare
      sqTop     = new VTTSquare( super );

    strCubeOut.append( "\n\n>> Resulting Cube:" );
    strCubeOut.append( "\n\n>> Base Square:" );
    strCubeOut.append( super.toString() );
    strCubeOut.append( "\n\n>> Top Square:" );
    strCubeOut.append( sqTop.toString() );
    strCubeOut.append( "\n\nLength:       " + VTTUtils.formatLine.format( super.getSide() ) );
    strCubeOut.append( "\nWidth:        "   + VTTUtils.formatLine.format( super.getSide() ) );
    strCubeOut.append( "\nHeight:       "   + VTTUtils.formatLine.format( nDepth ) );
    strCubeOut.append( "\nSurface Area: "   + VTTUtils.formatArea.format( getSurfaceArea() ) );
    strCubeOut.append( "\nVolume:       "   + VTTUtils.formatVolume.format( getVolume() ) );
  } // End setCube()


  public int getSide()
  {
    return nDepth;
  } // End getDimension()


  public int getSurfaceArea()
  {
    return ( super.getArea() * 6 );
  } // End getSurfaceArea()


  private int getVolume()
  {
    return ( super.getArea() * nDepth );
  } // End getVolume()


  public String toString()
  {
    return strCubeOut.toString();
  } // End toString()

} // end class VTTCube

