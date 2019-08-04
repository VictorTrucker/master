/************************************************************
*   Program: VTTRacerFrame.java                             *
*    Author: Victor Trucker                                 *
*      Date: 05/01/2000                                     *
*   Purpose: To provide a Frame in which we can play...     *
************************************************************/

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class VTTRacerFrame extends JFrame implements ActionListener
{
  private  boolean 
    firstTime     = false;
  private BufferedImage 
    bi            = new BufferedImage( 5, 5, BufferedImage.TYPE_INT_RGB );
  private final int
    TORTOISE      = 0,
    HARE          = 1,
    START         = 0,
    RESET         = 1,
    CLEAR         = 2,
    ABOUT         = 3,
    CLOSE         = 4;
  private String
    strBtnText[]  = { "Start", "Reset Racers", "Clear Scores", "About...", "Close" };
  protected JButton
    cmdMain[]     = new JButton[strBtnText.length];
  private JLabel
    label[]       = new JLabel[16],
    statusBar     = new JLabel( " Ready..." );
  private JPanel
    pnlRacer      = new JPanel(),
    pnlControls   = new JPanel();
  private VTTRacer
    tortoise      = new VTTTortoise( this, 0 ),
    hare          = new VTTHare( this, 0 );
  private ImageIcon
    image[]       = new ImageIcon[2];
  static final long
    serialVersionUID = 0;


  public VTTRacerFrame()
  {
    enableEvents( AWTEvent.WINDOW_EVENT_MASK );

    try
    {
      initFrame();
    }
    catch( Exception e )
    {
      e.printStackTrace();
      VTTUtils.cry( "Failure in initFrame()", "Error" );
    }
  } // end VTTRacerFrame()


  private void initFrame() throws Exception
  {
    int
      nLup = 0;

    pnlRacer.setLayout( new FlowLayout() );
    pnlControls.setLayout( new GridLayout( 1, 4 ) );
    getContentPane().setLayout( new BorderLayout() );
    pnlRacer.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

    for ( nLup = 0; nLup < label.length; nLup++ )
      label[nLup] = new JLabel();

    for ( nLup = 0; nLup < strBtnText.length; nLup++ )
    {
      cmdMain[nLup] = new JButton( strBtnText[nLup] );
      cmdMain[nLup].addActionListener( this );
      pnlControls.add( cmdMain[nLup] );
    }

    setSize( 640, 260 );
    setTitle( "The Tortoise and the Hare..." );
    setResizable( false );

    image[0] = new ImageIcon( getClass().getResource( "Tortoise.gif" ) );
    image[1] = new ImageIcon( getClass().getResource( "Rabbit.gif" ) );

    getContentPane().add( pnlControls, BorderLayout.NORTH );
    getContentPane().add( pnlRacer, BorderLayout.CENTER );
    getContentPane().add( statusBar, BorderLayout.SOUTH );

    if ( VTTUtils.askYesNo( "Project 10 - Part 1...\nThreaded Tortoise and Hare...",
                            "The Race is On..." ) == VTTUtils.NO )
    {
      System.exit( 0 );
    }
  } // end initFrame()


  public void actionPerformed( ActionEvent e )
  {
    if ( e.getSource() == cmdMain[START] )
    {
      if ( cmdMain[START].getText().equals( "Start" ) )
      {
        statusBar.setText( " Stop the Race between the Tortoise and the Hare..." );
        fileStart();
      }
      else
      {
        statusBar.setText( " Start the Race between the Tortoise and the Hare..." );
        fileStop();
      }
    }
    else if ( e.getSource() == cmdMain[RESET] )
    {
      statusBar.setText( " Reset Race Track to Default Data" );
      editReset();
      statusBar.setText( " Ready..." );
    }
    else if ( e.getSource() == cmdMain[CLEAR] )
    {
      statusBar.setText( " Clear Racers Scores" );
      tortoise.nWins = 0;
      hare.nWins = 0;
      repaint();
      statusBar.setText( " Ready..." );
    }
    else if ( e.getSource() == cmdMain[ABOUT] )
    {
      statusBar.setText( " Program Information, Version Number and copyright" );
      helpAbout();
    }
    else if ( e.getSource() == cmdMain[CLOSE] )
    {
      statusBar.setText( " Exit Software" );
      fileExit();
    }
    else
    {
      statusBar.setText( " Ready..." );
    }
    System.gc();
  } // end actionPerformed( ActionEvent )


  public void fileStart()
  {
    int
      nLup = 0;

    hare.go();
    tortoise.go();

    VTTRacer.nWinner = -1;

    cmdMain[START].setText( "Stop" );

    for ( nLup = 1; nLup < cmdMain.length; nLup++ )
      cmdMain[nLup].setEnabled( false );
  } // end fileStart()


  public void fileStop()
  {
    int
      nLup = 0;

    VTTRacer.nWinner = 2;

    cmdMain[START].setText( "Start" );
    cmdMain[START].setEnabled( false );

    for ( nLup = 1; nLup < cmdMain.length; nLup++ )
      cmdMain[nLup].setEnabled( true );
  } // end fileStop()


  public void editReset()
  {
    int
      nTWins = tortoise.nWins,
      nHWins = hare.nWins,
      nLup   = 0;

    tortoise = null;
    hare     = null;
    tortoise = new VTTTortoise( this, nTWins );
    hare     = new VTTHare( this, nHWins );

    VTTRacer.nWinner = -1;

    for ( nLup = 0; nLup < 4; nLup++ )
      cmdMain[nLup].setEnabled( true );

    repaint();
  } // end editReset()


  public void fileExit()
  {
    if ( VTTUtils.askYesNo( "Exit Software, Quitter?", "Adios..." ) == VTTUtils.YES )
    {
      System.exit( 0 );
    }
  } // end fileExit()

//  @SuppressWarnings("deprecation")
  public void helpAbout()
  {
    VTTAboutBox
      dlg = new VTTAboutBox( this );

    Dimension
      frmSize = getSize(),
      dlgSize = dlg.getSize();
    Point
      loc     = getLocation();

    dlg.setLocation( ( frmSize.width  - dlgSize.width  ) / 2 + loc.x,
                     ( frmSize.height - dlgSize.height ) / 2 + loc.y );
    dlg.setModal( true );
    dlg.setVisible( true );
//    dlg.show();
  } // end helpAbout()


  public void paint( Graphics gr )
  {
	update( gr );

    if ( VTTRacer.nWinner > -1 )
    {
      switch ( VTTRacer.nWinner )
      {
        case TORTOISE:
        {
          tortoise.nWins++;
          break;
        }
        case HARE:
        {
          hare.nWins++;
          break;
        }
      }
      fileStop();
    }
  } // end paint( Graphics )

  
  public void update( Graphics gr ) 
  {
    Graphics2D g2 = (Graphics2D)gr;

    if ( firstTime ) 
    {
      Dimension 
        dim = getSize();
//      int 
//        w = dim.width,
//        h = dim.height;
      
//      bi = (BufferedImage)createImage( w, h );
      bi = (BufferedImage)createImage( dim.width, dim.height );
//      bi = (BufferedImage)createImage( getSize().width, getSize().height );
      firstTime = false;
    }

    int
      nLup    = 0;
    Font
      smlFont = new Font( "Arial", Font.BOLD, 12 ),
      bigFont = new Font( "Arial", Font.BOLD, 56 );

    super.paint( gr );
    gr.setFont( bigFont );

    if ( tortoise.nPosition > hare.nPosition )
      gr.setColor( Color.green );
    else
      gr.setColor( Color.red );
    
    gr.drawString( "Tortoise ... " + Integer.toString( tortoise.nWins ), 90, 120 );

    if ( hare.nPosition > tortoise.nPosition )
      gr.setColor( Color.green );
    else
      gr.setColor( Color.red );
    
    gr.drawString( "Hare ......... " + Integer.toString( hare.nWins ), 90, 200 );

    gr.setFont( smlFont );
    gr.setColor( Color.red );
    gr.drawString( "Finish", this.getWidth() - 47, 68 );
    
    for ( nLup = 29; nLup < 32; nLup++ )
    {
      gr.drawLine( this.getWidth() - nLup, 75, this.getWidth() - nLup, 210 );
    }
    
    gr.drawString( "Finish", this.getWidth() - 47, 230 );

    image[0].paintIcon( this, gr, ( tortoise.nPosition * 8 ) + 20,  80 );
    image[1].paintIcon( this, gr, ( hare.nPosition * 8 )     + 20, 160 );

    g2.drawImage( bi, 0, 0, this );
  } // end update( Graphics )


  protected void processWindowEvent( WindowEvent e )
  {
    if ( WindowEvent.WINDOW_CLOSING == e.getID() )
    {
      fileExit();
    }
  } // end processWindowEvent( WindowEvent )

} // End class VTTRacerFrame
