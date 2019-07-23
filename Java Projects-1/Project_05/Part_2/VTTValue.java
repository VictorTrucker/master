/************************************************************
*   Program: VTTValue.java                                  *
*    Author: Victor Trucker                                 *
*      Date: 03/02/2000                                     *
*   Purpose: To provide Universal return type for an Input  *
*            Dialog.  Used by the Utilities class...        *
************************************************************/

//package VTTRegionMatch;


public class VTTValue
{
  public int
    nMaxChars = 0,
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
  } // end VTTValues()


  public void initValues()
  {
    nValue  = -1;
    dValue  = -1.00;
    strValue.setLength( 0 );
  } // end initValues()

} // end class VTTValue

