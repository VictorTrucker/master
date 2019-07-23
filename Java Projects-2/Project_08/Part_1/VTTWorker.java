/************************************************************
*   Program: VTTWorker.java                                 *
*    Author: Victor Trucker                                 *
*      Date: 04/13/2000                                     *
*   Purpose: Data storage for the piece workers...          *
************************************************************/

//package VTTWorker;


public class VTTWorker
{
  public int
    nPieces[] = { 0, 0, 0 };
  public double
    dAmount[] = { 0.00, 0.00, 0.00 };
  public String
    strWorker;


  public VTTWorker( String strName, int nGrade, int nQty )
  {
    strWorker = new String( strName );
    setPieces( nGrade, nQty );
  } // end VTTWorker()


  public void setPieces( int nGrade, int nQty )
  {
    int
      yes   = VTTUtils.YES;
    String
      strM1 = "A Value already Exists for this Field, Would you like to Replace it?",
      strM2 = "Would you like to Add the New Value to the Existing Value?",
      strT1 = "Replace Value",
      strT2 = "Combine Values";
    double
      dRate = ( nQty > 599 ) ? .65 :
              ( nQty > 399 ) ? .60 :
              ( nQty > 199 ) ? .55 :
                               .50;

    dRate -= ( nGrade * .05 );

    if ( nPieces[nGrade] > 0 )
    {
      if ( VTTUtils.askYesNo( strM1, strT1 ) == yes )
        nPieces[nGrade] = nQty;
      else if ( VTTUtils.askYesNo( strM2, strT2 ) == yes )
        nPieces[nGrade] += nQty;
    }
    else
      nPieces[nGrade] = nQty;

    dAmount[nGrade] = nPieces[nGrade] * dRate;
  } // end setPieces( int, int )


  public String toString()
  {
    int
      nLup         = 0;
    double
      dTotalAmount = 0.00;
    StringBuffer
      strSummary   = new StringBuffer( "" );

    strSummary.append( " " + strWorker + "\r\n" );

    for ( nLup = 0; nLup < 3; nLup++ )
    {
      strSummary.append( " Pay Grade " + ( char )( nLup + 65 ) + " Pieces:  " +
                         nPieces[nLup] + "\r\n" );
      dTotalAmount += dAmount[nLup];
    }

    strSummary.append( " Total Amount:  " +
                       VTTUtils.formatMoney.format( dTotalAmount ) + "\r\n" );

    return strSummary.toString();
  } // end toString()

} // end class VTTWorker

