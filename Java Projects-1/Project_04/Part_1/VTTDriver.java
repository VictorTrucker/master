/************************************************************
*   Program: Project 4 - Part 1 - VTTDriver.java            *
*    Author: Victor Trucker                                 *
*      Date: 03/04/2000                                     *
*   Purpose: This class is the driver for the VTTCube       *
*            class.  Tests the aspects of the Point,        *
*            Square and Cube for the composition model...   *
************************************************************/

//package VTTCubeCompose;


public class VTTDriver
{
  public static void main( String args[] )
  {
    int
      nLup  = 0;
    String
      strIntroSlogan = "Project 3 - Part 1\nPoint, Square, Cube...",
      strModelType   = "(composition)",
      strBreak       = "\n==========================================\n";
    StringBuffer
      strDriverOut   = new StringBuffer( "" );
    VTTCube
      cube;

    VTTUtils.say( strIntroSlogan, strModelType );
    System.out.println( strBreak );
    
    for ( nLup = 1; nLup < 5; nLup++ )
    {
      cube = new VTTCube();

      strDriverOut.setLength( 0 );
      strDriverOut.append( "Cube [ " + nLup + " ]" );
      strDriverOut.append( cube.toString() );

      System.out.println( strDriverOut.toString() );
      System.out.println( strBreak );
      VTTUtils.say( strBreak + strDriverOut.toString() + strBreak,
                    strModelType + " Point, Square, Cube..." );
    }

    System.out.println( "Adios! JOptionPane...\n" + strBreak );
    VTTUtils.say( "Buh-Bye, DOS Window...", "Finished" );
    System.exit( 0 );
  } // end main()

} // end class VTTDriver

