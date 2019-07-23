/************************************************************
*   Program: Project 7 - Part 2 - VTTColorChooser.java      *
*    Author: Victor Trucker                                 *
*      Date: 04/11/2000                                     *
*   Purpose: This applet draws shapes according to user     *
*            input in colors according to user input...     *
************************************************************/

//package VTTColorChooser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;


public class VTTColorChooser extends JFrame implements ActionListener
{
  private final static int
    SETCOLOR   = 0,
    DRAW       = 1,
    CLEAR      = 2,
    CLOSE      = 3;
  private int
    nSelection = 0;
  private String
    strSnivel       = new String( "" ),
    strButtonText[] = { "Select Color", "Draw", "Clear", "Close" },
    strChioces[]    = { "Circle", "Square", "Oval", "Rectangle" };
  private JButton
    cmdMain[]       = new JButton[strButtonText.length];
  private JComboBox
    cmbShape        = new JComboBox( strChioces );
  private VTTPanel
    easelPanel      = new VTTPanel();
  private JPanel
    controlPanel    = new JPanel();
  private Container
    c               = getContentPane();
  private Color
    color           = new Color( 0, 0, 0 );

////////////////////////////////////////////////////////////////////////////////

  public VTTColorChooser()
  {
    int
      nLup = 0;

    super.setTitle( "Color Selection" );

    c.setLayout( new BorderLayout() );
    controlPanel.setLayout( new GridLayout( 1, 5 ) );
    controlPanel.add( cmbShape );
    color = easelPanel.getBackground();

    cmbShape.addItemListener
    (
      new ItemListener()
      {
        public void itemStateChanged( ItemEvent e )
        {
          nSelection = cmbShape.getSelectedIndex();
          easelPanel.draw( nSelection, color );
        }
      }
    );

    for ( nLup = 0; nLup < strButtonText.length; nLup++ )
    {
      cmdMain[nLup] = new JButton( strButtonText[nLup] );
      cmdMain[nLup].addActionListener( this );
      controlPanel.add( cmdMain[nLup] );
    }

    cmbShape.setSelectedIndex( 0 );
    c.add( controlPanel, BorderLayout.SOUTH );
    c.add( easelPanel, BorderLayout.CENTER );

    setSize( 550, 400 );
    setVisible( true );
    VTTUtils.say( "Project 7 - Part 2...\nColor Chooser...", "Choosy Mothers choose JIF..." );
  } // end VTTColorChooser()


  public static void main( String[] args )
  {
    VTTColorChooser chooser = new VTTColorChooser();

    chooser.addWindowListener
    (
      new WindowAdapter()
      {
        public void windowClosing( WindowEvent e )
        {
          System.exit( 0 );
        }
      }
    );
  } // end main( String[] )


  public void actionPerformed( ActionEvent event )
  {
    int
      nLup         = 0;
    final JColorChooser
      jcc  = new JColorChooser( Color.red );

    nSelection = cmbShape.getSelectedIndex();
    if ( event.getSource() == cmdMain[CLOSE] )
    {
      if ( VTTUtils.askYesNo( "So, you've had enough, eh?", "Exit App" ) == VTTUtils.YES )
      {
        System.exit( 0 );
      }
    }
    else if ( event.getSource() == cmdMain[CLEAR] )
    {
      cmbShape.setSelectedIndex( 0 );
      color = easelPanel.getBackground();
      easelPanel.draw( 0, color );
    }
    else if ( event.getSource() == cmdMain[DRAW] )
    {
      if ( color == easelPanel.getBackground() )
      {
        VTTUtils.say( ">> Please Select a Color <<", "Way to Go..." );
      }
      else
      {
        easelPanel.draw( nSelection, color );
      }
    }
    else if ( event.getSource() == cmdMain[SETCOLOR] )
    {
      color = jcc.showDialog( this, "Please Select a Color for Drawing the " +
                                    strChioces[nSelection] + "s", Color.red );
    }
    System.gc();
  } // end actionPerformed( ActionEvent )

} // End class VTTColorChooser
