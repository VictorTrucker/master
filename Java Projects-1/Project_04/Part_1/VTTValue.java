/************************************************************
*   Program: Project 4 - Part 1 - VTTValue.java             *
*    Author: Victor Trucker                                 *
*      Date: 03/02/2000                                     *
*   Purpose: To provide Universal return type for an Input  *
*            Dialog.  Used by the Utilities class...        *
************************************************************/

//package VTTCubeCompose;


public class VTTValue
{
  public int
    nMin      = 0,
    nMax      = 0,
    nValue    = 0;
  public double
    dMin      = 0.00,
    dMax      = 0.00,
    dValue    = 0.00;
  public StringBuffer
    strValue  = new StringBuffer( "" );


  public VTTValue()
  {
    initValues();
  } // End VTTValues()


  public void initValues()
  {
    nValue  = -1;
    dValue  = -1.00;
    strValue.setLength( 0 );
  } // End initValues()

} // end class VTTValue

