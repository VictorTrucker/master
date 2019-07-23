/************************************************************
*   Program: Project 4 - Part 1 - VTTCube.java              *
*    Author: Victor Trucker                                 *
*      Date: 02/29/2000                                     *
*   Purpose: This class manages Cube Objects. It offers     *
*            interfaces to Corner Points, Surface Area,     *
*            and Volume...                                  *
************************************************************/

//package VTTCubeCompose;


public class VTTCube
{
  private VTTSquare
    sqTop,
    sqBase;
  private int
    nDepth   = 0;


  public VTTCube()
  {
    VTTPoint.setOffset( 30 );
    
    VTTPoint
      pointUL  = new VTTPoint();

    setCube( pointUL );
  } // End VTTCube()


  public VTTCube( VTTPoint ptUL )
  {
    setCube( ptUL );
  } // End VTTCube( VTTPoint )


  public void setCube( VTTPoint ptUL )
  {
    sqBase = new VTTSquare( ptUL );
    nDepth = sqBase.getSide();
    sqTop  = new VTTSquare( ptUL, nDepth, nDepth );
  } // End setCube()


  public int getSide()
  {
    return nDepth;
  } // End getSide()


  public int getSurfaceArea()
  {
    return ( sqBase.getArea() * 6 );
  } // End getSurfaceArea()


  private int getVolume()
  {
    return ( sqBase.getArea() * nDepth );
  } // End getVolume()


  public String toString()
  {
    int
      nLup      = 0;
    StringBuffer
      strCubeOut = new StringBuffer( "" );

    strCubeOut.append( "\n\n>> Resulting Cube:" );
    strCubeOut.append( "\n\n>> Base Square:" );
    strCubeOut.append( sqBase.toString() );
    strCubeOut.append( "\n\n>> Top Square:" );
    strCubeOut.append( sqTop.toString() );
    strCubeOut.append( "\n\nLength:       " + VTTUtils.formatLine.format( sqBase.getSide() ) );
    strCubeOut.append( "\nWidth:        "   + VTTUtils.formatLine.format( sqBase.getSide() ) );
    strCubeOut.append( "\nHeight:       "   + VTTUtils.formatLine.format( nDepth ) );
    strCubeOut.append( "\nSurface Area: "   + VTTUtils.formatArea.format( getSurfaceArea() ) );
    strCubeOut.append( "\nVolume:       "   + VTTUtils.formatVolume.format( getVolume() ) );

    return strCubeOut.toString();
  } // End toString()

} // end class VTTCube

