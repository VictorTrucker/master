/************************************************************
*   Program: VTTUtils.java                                  *
*    Author: Victor Trucker                                 *
*      Date: 02/29/2000                                     *
*   Purpose: This class manages some simple utilities.      *
*            Like input, message boxes, and some formats... *
************************************************************/

//package VTTColorChars;
package VTTSkyLine;

import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import java.awt.*;


public class VTTUtils
{
  public static final int
    GET_INTEGER  = 1,
    GET_DOUBLE   = 2,
    GET_STRING   = 3,
    OK           = JOptionPane.OK_OPTION,
    CANCEL       = JOptionPane.CANCEL_OPTION,
    YES          = JOptionPane.YES_OPTION,
    NO           = JOptionPane.NO_OPTION;
  public static final DecimalFormat
    formatInt    = new DecimalFormat( "00" ),
    formatDbl    = new DecimalFormat( "00.00" ),
    formatMoney  = new DecimalFormat( "$ 0.00" ),
    formatPCT    = new DecimalFormat( "0.00 %" ),
    formatLine   = new DecimalFormat( "00 Linear Units" ),
    formatArea   = new DecimalFormat( "00 Square Units" ),
    formatVolume = new DecimalFormat( "00 Cubic  Units" );
  public static final Font
    arialFont10p   = new Font( "Arial",   Font.PLAIN,  10 ),
    arialFont10b   = new Font( "Arial",   Font.BOLD,   10 ),
    arialFont12p   = new Font( "Arial",   Font.PLAIN,  12 ),
    arialFont12b   = new Font( "Arial",   Font.BOLD,   12 ),
    arialFont12i   = new Font( "Arial",   Font.ITALIC, 12 ),
    arialFont18p   = new Font( "Arial",   Font.PLAIN,  18 ),
    arialFont18b   = new Font( "Arial",   Font.BOLD,   18 ),
    arialFont18i   = new Font( "Arial",   Font.ITALIC, 18 ),
    arialFont24p   = new Font( "Arial",   Font.PLAIN,  24 ),
    arialFont24b   = new Font( "Arial",   Font.BOLD,   24 ),
    arialFont24i   = new Font( "Arial",   Font.ITALIC, 24 ),
    courierFont12p = new Font( "Courier", Font.PLAIN,  12 ),
    courierFont12b = new Font( "Courier", Font.BOLD,   12 ),
    courierFont12i = new Font( "Courier", Font.ITALIC, 12 ),
    courierFont18p = new Font( "Courier", Font.PLAIN,  18 ),
    courierFont18b = new Font( "Courier", Font.BOLD,   18 ),
    courierFont18i = new Font( "Courier", Font.ITALIC, 18 ),
    courierFont24p = new Font( "Courier", Font.PLAIN,  24 ),
    courierFont24b = new Font( "Courier", Font.BOLD,   24 ),
    courierFont24i = new Font( "Courier", Font.ITALIC, 24 );


  public static VTTValue getInput( String strPrompt, int nType )
  {
//    int
//      nLup    = 0;
    String
      strVal  = new String( "" );
    VTTValue
      value   = new VTTValue();

    strVal = JOptionPane.showInputDialog( strPrompt );

    if ( strVal == null )
    {
      if ( askYesNo( "Exit Application?", "Cancel" ) == YES )
        System.exit( 0 );
      else
        strVal = "-32768";
    }

    switch ( nType )
    {
      case GET_INTEGER:
        if ( !validateInt( strVal ) ) strVal = "";
        break;
      case GET_DOUBLE:
        if ( !validateDbl( strVal ) ) strVal = "";
        break;
    }

    value.strValue.setLength( 0 );
    value.strValue.append( strVal );

    if ( strVal.equals( "" ) )
    {
      value.strValue.setLength( 0 );
      
      switch ( nType )
      {
        case GET_INTEGER:
          value.strValue.append( 0 );
          break;
        case GET_DOUBLE:
          value.strValue.append( 0.00 );
          break;
        case GET_STRING:
          value.strValue.append( "" );
          break;
      }
    }

    switch ( nType )
    {
      case GET_INTEGER:
        value.nValue = Integer.parseInt( value.strValue.toString() );
        value.dValue = 0.00;
        value.strValue.setLength( 0 );
        break;
      case GET_DOUBLE:
        value.nValue = 0;
        value.dValue = Double.parseDouble( value.strValue.toString() );
        value.strValue.setLength( 0 );
         break;
      case GET_STRING:
        value.nValue = 0;
        value.dValue = 0.00;
    }
    
    return value;
  } // end getInput()


  public static boolean validateInt( String strValue )
  {
    boolean
      bRet    = true;
    int
      nLup    = 0;
    char
      chTest  = 'X';
    String
      strInts = new String( "-0123456789" );

    for ( nLup = 0; nLup < strValue.length(); nLup++ )
    {
      chTest = strValue.charAt( nLup );
      if ( strInts.indexOf( chTest ) < 0 ) bRet = false;
    }
    return bRet;
  }


  public static boolean validateDbl( String strValue )
  {
    boolean
      bRet    = true;
    int
      nLup    = 0;
    char
      chTest  = 'X';
    String
      strDbls = new String( "-0123456789." );

    for ( nLup = 0; nLup < strValue.length(); nLup++ )
    {
      chTest = strValue.charAt( nLup );
      if ( strDbls.indexOf( chTest ) < 0 ) bRet = false;
      if ( strValue.indexOf( '.' ) != strValue.lastIndexOf( '.' ) ) bRet = false;
    }
    return bRet;
  }


  public static void say( String strMessage, String strTitle )
  {
    JOptionPane.showMessageDialog( null,
                                   strMessage,
                                   strTitle,
                                   JOptionPane.INFORMATION_MESSAGE );
  } // end say( String, String )


  public static int askYesNo( String strPrompt, String strTitle )
  {
    return JOptionPane.showConfirmDialog( null,
                                          strPrompt,
                                          strTitle,
                                          JOptionPane.YES_NO_OPTION,
                                          JOptionPane.QUESTION_MESSAGE );
  } // end askYesNo( String, String )


  public static int askOkCancel( String strPrompt, String strTitle )
  {
    return JOptionPane.showConfirmDialog( null,
                                          strPrompt,
                                          strTitle,
                                          JOptionPane.OK_CANCEL_OPTION,
                                          JOptionPane.INFORMATION_MESSAGE );
  } // end askOkCancel( String, String )

} // end class VTTUtils

