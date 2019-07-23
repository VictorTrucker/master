/************************************************************
*   Program: VTTUtils.java                                  *
*    Author: Victor Trucker                                 *
*      Date: 02/29/2000                                     *
*   Purpose: This class manages some simple utilities.      *
*            Like input, message boxes, and some formats... *
************************************************************/

//package VTTUtils;


import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import java.awt.*;


public class VTTUtils
{
  public static final int
    GET_INTEGER  = 1,
    GET_DOUBLE   = 2,
    GET_STRING   = 3,
    FONT_STYLE[] = { Font.PLAIN, Font.BOLD, Font.ITALIC },
    PLAIN        = 0,
    BOLD         = 1,
    ITALIC       = 2,
    OK           = JOptionPane.OK_OPTION,
    CANCEL       = JOptionPane.CANCEL_OPTION,
    YES          = JOptionPane.YES_OPTION,
    NO           = JOptionPane.NO_OPTION;
  public static final DecimalFormat
    formatInt    = new DecimalFormat( "00" ),
    formatDbl    = new DecimalFormat( "0.00" ),
    formatMoney  = new DecimalFormat( "$ 0.00" ),
    formatPCT    = new DecimalFormat( "0.00 %" ),
    formatLine   = new DecimalFormat( "00 Linear Units" ),
    formatArea   = new DecimalFormat( "00 Square Units" ),
    formatVolume = new DecimalFormat( "00 Cubic  Units" );


  public static VTTValue getInput( String strPrompt, int nType )
  {
    int
      nLup    = 0;
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
      if ( chTest == '-' && strValue.lastIndexOf( '-' ) > 0 )  bRet = false;
    }
    return bRet;
  } // end validateInt( String )


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
      if ( chTest == '-' && strValue.lastIndexOf( '-' ) > 0 )  bRet = false;
    }
    return bRet;
  } // end validateDbl( String )


  public static Font createFont( String strName, int nStyle, int nSize )
  {
    return new Font( validateFontName( strName ),
                     FONT_STYLE[( nStyle > -1 && nStyle < 3 ) ? nStyle : Font.BOLD],
                     ( nSize >= 6 && nSize <= 144 ) ? nSize : 12 );
  }


  public static String validateFontName( String strName )
  {
    int
      nLup           = 0;
    boolean
      bGoodFont      = false;
    String
      strHoldName    = new String( strName ),
      strFontName[]  = getFontList();


    for ( nLup = 0; nLup < strFontName.length; nLup++ )
    {
      if ( strName.equals( strFontName[nLup] ) )
      {
        bGoodFont = true;
        nLup      = strFontName.length;
      }
    }

    if ( !bGoodFont ) strHoldName = new String( "Arial" );

    return strHoldName;
  }


  public static String[] getFontList()
  {
    return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
  }


  public static void say( Object strMessage, String strTitle )
  {
    JOptionPane.showMessageDialog( null,
                                   strMessage,
                                   strTitle,
                                   JOptionPane.INFORMATION_MESSAGE );
  } // end say( Object, String )


  public static void cry( Object strMessage, String strTitle )
  {
    JOptionPane.showMessageDialog( null,
                                   strMessage,
                                   strTitle,
                                   JOptionPane.ERROR_MESSAGE );
  } // end cry( Object, String )


  public static int askYesNo( Object strPrompt, String strTitle )
  {
    return JOptionPane.showConfirmDialog( null,
                                          strPrompt,
                                          strTitle,
                                          JOptionPane.YES_NO_OPTION,
                                          JOptionPane.QUESTION_MESSAGE );
  } // end askYesNo( Object, String )


  public static int askOkCancel( Object strPrompt, String strTitle )
  {
    return JOptionPane.showConfirmDialog( null,
                                          strPrompt,
                                          strTitle,
                                          JOptionPane.OK_CANCEL_OPTION,
                                          JOptionPane.INFORMATION_MESSAGE );
  } // end askOkCancel( Object, String )

} // end class VTTUtils

