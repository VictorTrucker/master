/************************************************************
*   Program: Project 7 - Part 1 - VTTGuess.java             *
*    Author: Victor Trucker                                 *
*      Date: 04/09/2000                                     *
*   Purpose: This applet accepts input numbers as guesses   *
*            toward figuring out the unknown number...      *
************************************************************/

//package VTTGuess;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VTTGuess extends JApplet implements ActionListener
{
  private int
    nNumber       = 0,
    nLastGuess    = 0,
    nThisGuess    = 0;
  private String
    strResponse   = new String( "" ),
    strClues[]    = { "- Let's Play -", "- Too High -", "- Too Low -", "- Correct -" },
    strBtnLabel[] = { "Submit Guess", "Reset", "Close" };
  private JPanel
    textPanel     = new JPanel(),
    controlPanel  = new JPanel(),
    buttonPanel   = new JPanel();
  private JButton
    cmdMain[]     = new JButton[strBtnLabel.length];
  private JLabel
    lblSpace[]    = new JLabel[4],
    lblTitle      = new JLabel( " ", JLabel.CENTER ),
    lblPrompt     = new JLabel( " ", JLabel.CENTER ),
    lblLastGuess  = new JLabel( " ", JLabel.CENTER ),
    lblStatus     = new JLabel( " ", JLabel.CENTER );
  private JTextField
    txtInput      = new JTextField( 10 );
  private Color
    color[]       = new Color[4];
  private Container
    c             = getContentPane();


  public void init()
  {
    int
      nLup   = 0;

    buttonPanel.setLayout( new GridLayout( 1, cmdMain.length ) );
    controlPanel.setLayout( new GridLayout( 9, 1 ) );
    textPanel.setLayout( new GridLayout( 1, 3 ) );
    c.setLayout( new BorderLayout() );
    lblTitle.setFont( VTTUtils.courierFont18b );

    for ( nLup = 0; nLup < 4; nLup++ )
      lblSpace[nLup] = new JLabel( "" );

    controlPanel.add( lblSpace[0] );
    controlPanel.add( lblTitle );
    controlPanel.add( lblSpace[1] );
    controlPanel.add( lblPrompt );
    controlPanel.add( textPanel );
    controlPanel.add( lblStatus );
    controlPanel.add( lblLastGuess );

    textPanel.add( lblSpace[2] );
    textPanel.add( txtInput );
    textPanel.add( lblSpace[3] );

    for ( nLup = 0; nLup < strBtnLabel.length; nLup++ )
    {
      cmdMain[nLup] = new JButton( strBtnLabel[nLup] );
      buttonPanel.add( cmdMain[nLup] );
      cmdMain[nLup].addActionListener( this );
    }

    c.add( controlPanel, BorderLayout.CENTER );
    c.add( buttonPanel, BorderLayout.SOUTH );

    loadPallet();
    initGame();

    VTTUtils.say( "Project 7 - Part 1...\nThe Guessing Game...", "Guess What..." );
  } // end Init()


  public void start()
  {
    setVisible( true );
    txtInput.requestFocus();
  } // end Start()


  public void actionPerformed( ActionEvent event )
  {
    boolean
      bNix = false;
    int
      nLup = 0,
      nRet = -1;

    if ( event.getSource() == cmdMain[2] )
    {
      fileExit();
    }
    else if ( event.getSource() == cmdMain[1] )
    {
      initGame();
      setVisible( true );
    }
    else if ( event.getSource() == cmdMain[0] )
    {
      strResponse = txtInput.getText();
      if ( !VTTUtils.validateInt( strResponse ) || strResponse.equals( "" ) )
      {
        bNix = true;
      }
      else
      {
        nThisGuess = Integer.parseInt( strResponse );
        if ( nThisGuess < 1 || nThisGuess > 1000 )
          bNix = true;
        else
          checkGuess();
      }

      if (bNix )
      {
        VTTUtils.say( "Input Value is out of Range!\nValid Range: 1 to 1000",
                      "Invalid Input" );
        txtInput.selectAll();
      }
    }
    txtInput.requestFocus();
    System.gc();
  } // end actionPerformed( ActionEvent )


  public void loadPallet()
  {
    color[0] = new Color( 255,   0,   0 ); // Red
    color[1] = new Color(   0,   0, 255 ); // Blue
    color[2] = new Color( 255, 255, 255 ); // White
    color[3] = new Color(   0,   0,   0 ); // Black
  } // end loadPallet()


  public void initGame()
  {
    int
      nLup = 0;

    for ( nLup = 0; nLup < 4; nLup++ )
      lblSpace[nLup].setText( " " );

    lblTitle.setText( "- Guess the Number -");
    lblPrompt.setText( "I have a number between 1 and 1000, Can you guess my Number?" );
    lblLastGuess.setText( "No Guess Yet" );
    lblStatus.setText( strClues[0] );
    txtInput.setText( "" );
    txtInput.setBackground( color[2] );
    txtInput.setForeground( color[3] );
    nNumber = generateNumber();
    setVisible( true );
    txtInput.setEditable( true );
    txtInput.requestFocus();
  }

  
  public int generateNumber()
  {
    return ( int )( ( Math.random() * 1000) + 1 );
  } // end generateNumber()


  public void checkGuess()
  {
    int
      nThisDiff = Math.abs( nNumber - nThisGuess ),
      nLastDiff = Math.abs( nNumber - nLastGuess );

    if ( nThisDiff < nLastDiff )
    {
      txtInput.setBackground( color[0] );
      txtInput.setForeground( color[3] );
    }
    else if ( nThisDiff > nLastDiff )
    {
      txtInput.setBackground( color[1] );
      txtInput.setForeground( color[2] );
    }

    nLastGuess = nThisGuess;
    lblStatus.setText( strClues[(nThisGuess > nNumber) ? 1 : (nThisGuess < nNumber) ? 2 : 3] );

    if ( nThisGuess == nNumber )
    {
      txtInput.setBackground( color[2] );
      txtInput.setForeground( color[3] );
      txtInput.setEditable( false );
    }

    lblLastGuess.setText( "" + nThisGuess );
    txtInput.setText( "" );
  } // end checkGuess()


  public void fileExit()
  {
    if ( VTTUtils.askYesNo( "So, you've had enough, eh?", "Exit App" ) == VTTUtils.YES )
      System.exit(0);
  } // end fileExit()

} // end class VTTGuess
