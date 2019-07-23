/************************************************************
*   Program: Project 5 - Part 2 - VTTWriter.java            *
*    Author: Victor Trucker                                 *
*      Date: 03/11/2000                                     *
*   Purpose: This class generates random sentences using    *
*            the java random number generator...  Amongst   *
*            other feeble string manipulating crap...       *
************************************************************/

//package VTTWriter;


public class VTTWriter
{
  public static void main( String args[] )
  {
    int
      nRet = 0,
      nLup = 0;
    String
      strBreak     = "\n====================================================\n",
      strCap       = "- Not initialized yet -",
      strArti[]    = {   "the",      "a",  "one",   "some",     "any" },
      strNoun[]    = {   "boy",   "girl",  "dog",   "town",     "car" },
      strVerb[]    = { "drove", "jumped",  "ran", "walked", "skipped" },
      strPrep[]    = {    "to",   "from", "over",  "under",      "on" };
    StringBuffer
      strWriteOut  = new StringBuffer( "" );

    VTTUtils.say( "Project 5 - Part 2\nSentence Writer...", "(application)" );
    System.out.println( strBreak );

    for ( nLup = 1; nLup < 21; nLup++ )
    {
      strWriteOut.setLength( 0 );
      strWriteOut.append( ">> Sentence [ " + nLup + " ] >>  " );
      strCap = capitalize( strArti[( int )( Math.random() * 5 )] + " " );
      strWriteOut.append( strCap );
      strWriteOut.append( strNoun[( int )( Math.random() * 5 )] + " " );
      strWriteOut.append( strVerb[( int )( Math.random() * 5 )] + " " );
      strWriteOut.append( strPrep[( int )( Math.random() * 5 )] + " " );
      strWriteOut.append( strArti[( int )( Math.random() * 5 )] + " " );
      strWriteOut.append( strNoun[( int )( Math.random() * 5 )] );
      strWriteOut.append( '.' );

      System.out.println( strWriteOut.toString() );
      System.out.println( strBreak );
      nRet = VTTUtils.askOkCancel( strBreak + strWriteOut.toString() + strBreak,
                                   "(application) Sentence Writer..." );
      if ( nRet == VTTUtils.CANCEL )
      {
        nRet = VTTUtils.askYesNo( "Quit? Are You Crazy?", "Exit App" );
        if ( nRet == VTTUtils.YES )
        {
          nLup = 21;
        }
      }
    }

    System.out.println( "Adios! J-Op, lil' buddy...\n" + strBreak );
    VTTUtils.say( "Buh-Bye, DOS-a-rama...", "Finished" );
    System.exit( 0 );
  } // end main()


  private static String capitalize( String strWord )
  {
    String
      strCap   = new String( "" + strWord.charAt( 0 ) ),
      strCap2  = new String( strCap.toUpperCase() ),
      strRight = new String(strWord.substring( 1 ) ),
      strFinal = new String( strCap2 + strRight );

    return strFinal;
  } // end capitalize( String )

} // end class VTTWriter

