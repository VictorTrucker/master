/************************************************************
*   Program: VTTPieceWorkAboutBox.java                      *
*    Author: Victor Trucker                                 *
*      Date: 04/13/2000                                     *
*   Purpose: Provide an About Box...                        *
************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class VTTPieceWorkAboutBox extends JDialog implements ActionListener
{
  private JPanel
    panel[]        = new JPanel[5];
  private JButton
    cmdMain        = new JButton( "OK" );
  private ImageIcon
    imageIcon;
  private JLabel
    label[]        = new JLabel[5];
  private String
    strAboutInfo[] = { "Victor Trucker",
                       "Piece Work",
                       "Version - 1.0.0",
                       "Copyright (c) 1999   ",
                       "Victor Trucker" };


  public VTTPieceWorkAboutBox( Frame parent )
  {
    super( parent );
    enableEvents( AWTEvent.WINDOW_EVENT_MASK );
    try
    {
      InitAboutBox();
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }

    label[0].setIcon( imageIcon );
    pack();
  } // end VTTPieceWorkAboutBox( Frame )


  private void InitAboutBox() throws Exception
  {
    int
      nLup    = 0;
    try
    {
      for ( nLup = 0; nLup < 5; nLup++ )
      {
        label[nLup] = new JLabel( "", JLabel.LEFT );
        panel[nLup] = new JPanel();
      }

      imageIcon = new ImageIcon( getClass().getResource( "piece.gif" ) );
      this.setTitle( "About Piece Work" );
      setResizable( false );

      panel[0].setLayout( new BorderLayout() );
      panel[1].setLayout( new BorderLayout() );
      panel[2].setLayout( new FlowLayout() );
      panel[3].setLayout( new FlowLayout() );
      panel[4].setLayout( new GridLayout( 4, 1 ) );

      panel[3].setBorder( new EmptyBorder( 20, 25, 10, 10 ) );
      panel[4].setBorder( new EmptyBorder( 10, 20, 10, 10 ) );

      for ( nLup = 1; nLup < 5; nLup++ )
      {
        label[nLup].setText( strAboutInfo[nLup] );
        panel[4].add( label[nLup] );
      }

      panel[3].add( label[0] );
      panel[1].add( panel[3], BorderLayout.WEST );
      panel[1].add( panel[4], BorderLayout.CENTER );
      this.getContentPane().add( panel[0] );

      cmdMain.addActionListener( this );
      panel[2].add( cmdMain );

      panel[0].add( panel[2], BorderLayout.SOUTH );
      panel[0].add( panel[1], BorderLayout.NORTH );
    }
    catch( Exception e )
    {
      e.printStackTrace();
    }

  } // end InitAboutBox()


  protected void processWindowEvent( WindowEvent e )
  {
    super.processWindowEvent( e );
    if( e.getID() == WindowEvent.WINDOW_CLOSING )
    {
      closeDialog();
    }
  } // end processWindowEvent( WindowEvent )


  public void closeDialog()
  {
    dispose();
  } // end closeDialog()


  public void actionPerformed( ActionEvent e )
  {
    if( e.getSource() == cmdMain )
    {
      closeDialog();
    }
  } // end actionPerformed( ActionEvent )

} // End class VTTPieceWorkFrame_AboutBox
