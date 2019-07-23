/************************************************************
*   Program: Project 5 - Part 1 - VTTRegionMatch.java       *
*    Author: Victor Trucker                                 *
*      Date: 03/09/2000                                     *
*   Purpose: This applet accepts input of String values     *
*            and compares substrings within the input       *
*            Strings, and tells whether A is greater than,  *
*            equal to, or less than B...                    *
************************************************************/

//package VTTRegionMatch;


import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class VTTRegionMatch extends Applet implements ActionListener, KeyListener
{
  private boolean
    bEqual     = false,
    bCompared  = false;
  private int
    nSourceIdx = 0,
    nSearchIdx = 0,
    nNumChars  = 0,
    nLup       = 0;
  private String
    strResponse[] = { "- A is LESS than B -", "- A is Equal to B -", "- A is GREATER than B -" },
    strSource   = new String( "" ),
    strSearch   = new String( "" );
  private Checkbox
    chkIgnoreCase = new Checkbox( "Ignore Case", true );
  private Button
    cmdCompare  = new Button( "Compare" ),
    cmdClear    = new Button( "    Clear    " ),
    cmdClose    = new Button( "    Close    " );
  private Label
    lblResponse = new Label( "Comparison not yet performed" ),
    lblSource   = new Label( "String to Compare [ A ]" ),
    lblSearch   = new Label( "String to Compare with [ B ]" ),
    lblSrcIdx   = new Label( "Start Index" ),
    lblSchIdx   = new Label( "Start Index" ),
    lblNumChars = new Label( "Number of Characters to Compare" ),
    lblTitle    = new Label( "- Region Match -               " );
  private TextField
    txtSource   = new TextField( 35 ),
    txtSearch   = new TextField( 35 ),
    txtSrcIdx   = new TextField( 6 ),
    txtSchIdx   = new TextField( 6 ),
    txtNumChars = new TextField( 6 );


  public void init()
  {
    lblTitle.setFont( VTTUtils.courierFont18b );
    lblSource.setFont( VTTUtils.arialFont12p );
    lblSearch.setFont( VTTUtils.arialFont12p );
    lblSrcIdx.setFont( VTTUtils.arialFont12p );
    lblSchIdx.setFont( VTTUtils.arialFont12p );
    lblNumChars.setFont( VTTUtils.arialFont12p );
    txtSource.setFont( VTTUtils.courierFont12p );
    txtSearch.setFont( VTTUtils.courierFont12p );
    txtSrcIdx.setFont( VTTUtils.courierFont12p );
    txtSchIdx.setFont( VTTUtils.courierFont12p );
    txtNumChars.setFont( VTTUtils.courierFont12p );
    lblResponse.setFont( VTTUtils.courierFont12p );

    add( txtSource );
    add( txtSearch );
    add( txtSrcIdx );
    add( txtSchIdx );
    add( txtNumChars );
    add( lblSource );
    add( lblSearch );
    add( lblSrcIdx );
    add( lblSchIdx );
    add( lblNumChars );
    add( lblTitle );
    add( lblResponse );
    add( cmdCompare );
    add( cmdClear );
    add( cmdClose );
    add( chkIgnoreCase );

    lblResponse.setBackground( Color.lightGray );
    lblSource.setBackground( Color.lightGray );
    lblSearch.setBackground( Color.lightGray );
    lblSrcIdx.setBackground( Color.lightGray );
    lblSchIdx.setBackground( Color.lightGray );
    lblNumChars.setBackground( Color.lightGray );
    lblTitle.setBackground( Color.lightGray );
    this.setBackground( Color.lightGray );
    chkIgnoreCase.setBackground( Color.lightGray );
    txtSrcIdx.setForeground( Color.red );
    txtSchIdx.setForeground( Color.red );
    txtNumChars.setForeground( Color.blue );
    cmdCompare.setEnabled( false );
    txtSrcIdx.setEditable( false );
    txtSchIdx.setEditable( false );
    txtNumChars.setEditable( false );
    txtSource.addKeyListener( this );
    txtSearch.addKeyListener( this );
    cmdClose.addActionListener( this );
    cmdClear.addActionListener( this );
    cmdCompare.addActionListener( this );
    txtSource.setText( "" );
    txtSearch.setText( "" );

    VTTUtils.say( "Project 5 - Part 1...\nRegion Match...", "Here we go again..." );
  } // end Init()


  public void start()
  {
    txtSource.requestFocus();
  } // end Start()


  public void keyPressed( KeyEvent event )
  {
    ;
  }


  public void keyReleased( KeyEvent event )
  {
    cmdCompare.setEnabled( ( txtSource.getText().length() > 0 && txtSearch.getText().length() > 0 ) );
  }


  public void keyTyped( KeyEvent event )
  {
    ;
  }


  public void actionPerformed( ActionEvent event )
  {
    int
      nRet = -1;
    if ( event.getSource() == cmdClose )
    {
      if ( VTTUtils.askYesNo( "Quit? Are you Crazy?", "Exit App" ) == VTTUtils.YES )
      {
        System.exit( 0 );
      }
    }
    else if ( event.getSource() == cmdClear )
    {
      lblTitle.setText( "- Region Match -" );
      lblResponse.setText( "Waiting for new Comparison" );
      strSource  = "";
      strSearch  = "";
      nSourceIdx = 0;
      nSearchIdx = 0;
      txtSource.setText( "" );
      txtSearch.setText( "" );
      txtSrcIdx.setText( "" );
      txtSchIdx.setText( "" );
      txtNumChars.setText( "" );
      txtSrcIdx.setBackground( Color.lightGray );
      txtSchIdx.setBackground( Color.lightGray );
      txtNumChars.setBackground( Color.lightGray );
      chkIgnoreCase.setState( true );
      cmdCompare.setEnabled( false );
      txtSource.requestFocus();
      bCompared = false;
    }
    else if ( event.getSource() == cmdCompare )
    {
      int
        nSourceTail = 0,
        nSearchTail = 0,
        nAvailable  = 0;
      VTTValue
        value     = new VTTValue();

      nRet       = 0;
      nSourceIdx = -1;
      nSearchIdx = -1;
      nNumChars  = 0;
      strSource  = "";
      strSearch  = "";
      strSource += txtSource.getText();
      strSearch += txtSearch.getText();

      if ( strSource.length() < 1 )
      {
        strSource = "None Supplied";
        txtSource.setText( strSource );
      }
      
      if ( strSearch.length() < 1 )
      {
        strSearch = "None Supplied";
        txtSearch.setText( strSearch );
      }

      while ( nSourceIdx < 0 )
      {
        value = VTTUtils.getInput( "Input Index at which you would " +
                                   "\nlike to begin the comparison " +
                                   "\nin the [ A ] string:", VTTUtils.GET_INTEGER );
        nSourceIdx = value.nValue - 1;
        if ( nSourceIdx < 0 || nSourceIdx > strSource.length() )
        {
          nSourceIdx = -1;
          VTTUtils.say( "Invalid Index!\n- Positive Integers Only -\nValid Range: 1 to " +
                         strSource.length(), "Error" );
        }
      }
      txtSrcIdx.setText( VTTUtils.formatInt.format( nSourceIdx + 1 ) );
      txtSrcIdx.setBackground( Color.white );

      while ( nSearchIdx < 0 )
      {
        value = VTTUtils.getInput( "Input Index at which you would " +
                                   "\nlike to begin the comparison " +
                                   "\nin the [ B ] string:", VTTUtils.GET_INTEGER );
        nSearchIdx = value.nValue - 1;
        if ( nSearchIdx < 0 || nSearchIdx > strSearch.length() )
        {
          nSearchIdx = -1;
          VTTUtils.say( "Invalid Index!\n- Positive Integers Only -\nValid Range: 1 to " +
                         strSearch.length(), "Error" );
        }
      }
      txtSchIdx.setText( VTTUtils.formatInt.format( nSearchIdx + 1 ) );
      txtSchIdx.setBackground( Color.white );

      while ( nNumChars < 1 )
      {
        value = VTTUtils.getInput( "Input Number of Characters you " +
                                   "\nwould like to compare ", VTTUtils.GET_INTEGER );
        nNumChars = value.nValue;
        nSourceTail = strSource.length() - nSourceIdx;
        nSearchTail = strSearch.length() - nSearchIdx;
        nAvailable = ( ( nSourceTail < nSearchTail ) ? nSourceTail : nSearchTail );

        if ( nNumChars < 1 || nNumChars > nAvailable )
        {
          nNumChars = 0;
          VTTUtils.say( "Invalid Number of Characters!\n- Positive Integers Only -\nValid Range: 1 to "
                         + nAvailable, "Error" );
        }
      }
      txtNumChars.setText( VTTUtils.formatInt.format( nNumChars ) );
      txtNumChars.setBackground( Color.white );

      bEqual = strSource.regionMatches( chkIgnoreCase.getState(), nSourceIdx, strSearch, nSearchIdx, nNumChars );

      if ( !bEqual )
        lblResponse.setText( "- A is NOT Equal to B -" );
      else
        lblResponse.setText( "- A is Equal to B -" );
      if ( chkIgnoreCase.getState() )
        nRet = strSource.substring( nSourceIdx, nSourceIdx + nNumChars ).compareToIgnoreCase( strSearch.substring( nSearchIdx, nSearchIdx + nNumChars ) );
      else
        nRet = strSource.substring( nSourceIdx, nSourceIdx + nNumChars ).compareTo( strSearch.substring( nSearchIdx, nSearchIdx + nNumChars ) );

      lblTitle.setText( strResponse[( nRet < 0 ) ? 0 : ( nRet == 0 ) ? 1 : 2] );
      bCompared = true;
    }
  } // end actionPerformed( ActionEvent )


  public void paint( Graphics graphicItem )
  {
    cmdClose.setLocation( 190, 175 );
    cmdClear.setLocation( 105, 175 );
    cmdCompare.setLocation( 20, 175 );
    chkIgnoreCase.setLocation( 20, 145 );

    lblTitle.setLocation( 20, 10 );
    lblSource.setLocation( 23, 40 );
    lblSearch.setLocation( 23, 90 );
    lblSrcIdx.setLocation( 302, 40 );
    lblSchIdx.setLocation( 302, 90 );
    lblNumChars.setLocation( 171, 140 );
    lblResponse.setLocation( 22, 205 );

    txtSource.setLocation( 20, 63 );
    txtSearch.setLocation( 20, 113 );
    txtSrcIdx.setLocation( 298, 63 );
    txtSchIdx.setLocation( 298, 113 );
    txtNumChars.setLocation( 298, 163 );
  } // end paint( Graphics )

} // end class VTTRegionMatch
