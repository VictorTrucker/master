/************************************************************
*   Program: VTTFontChooser.java                            *
*    Author: Victor Trucker                                 *
*      Date: 04/25/2000                                     *
*   Purpose: Provide a Font Selection Interface...          *
************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class VTTFontChooser extends JDialog implements ActionListener
{
  int
    nSize          = 0,
    nStyle         = 0;
  boolean
    bBold          = false,
    bItalic        = false,
    bRefreshSample = false;
  private JPanel
    panel[]        = new JPanel[6];
  private String
    strFontName    = new String( "" ),
    strFontSize[]  = { "12", "14", "16", "18" },
    strFontNames[] = getFontList();
  private JButton
    cmdOK          = new JButton( "Ok" ),
    cmdCancel      = new JButton( "Cancel" );
  private JCheckBox
    chkBold        = new JCheckBox( " Bold" ),
    chkItalic      = new JCheckBox( " Italic" );
  private JComboBox
    cmbFontName    = new JComboBox( strFontNames ),
    cmbFontSize    = new JComboBox( strFontSize );
  private JLabel
    label[]        = new JLabel[14];
  public Font
    font           = new Font( "Arial", Font.BOLD, 18 ),
    currentFont    = new Font( "Arial", Font.BOLD, 18 );


  public VTTFontChooser( Frame parent, Font currentFont )
  {
    super( parent );
    enableEvents( AWTEvent.WINDOW_EVENT_MASK );
    this.currentFont = currentFont;

    try
    {
      initFontChooser();
    }
    catch( Exception e )
    {
      e.printStackTrace();
      VTTUtils.cry( "Failure Initializing Font Chooser", "Error" );
    }

    pack();
  } // end VTTFontChooser( Frame )


  private void initFontChooser() throws Exception
  {
    int
      nLup = 0;
    Container
      c    = getContentPane();

    try
    {
      for ( nLup = 0; nLup < label.length; nLup++ )
        label[nLup] = new JLabel( " ", JLabel.LEFT );

      for ( nLup = 0; nLup < panel.length; nLup++ )
        panel[nLup] = new JPanel();

      setTitle( "Select Font for Output Text" );
      setSize( 300, 100 );
      setResizable( false );

      cmdOK.addActionListener( this );
      cmdCancel.addActionListener( this );
      chkBold.addActionListener( this );
      chkItalic.addActionListener( this );
      cmbFontName.addActionListener( this );
      cmbFontSize.addActionListener( this );

      panel[0].setLayout( new GridLayout( 6, 2 ) );
      panel[1].setLayout( new GridLayout( 1, 3 ) );
      panel[2].setLayout( new GridLayout( 1, 3 ) );
      panel[3].setLayout( new GridLayout( 1, 4 ) );
      panel[4].setLayout( new GridLayout( 1, 3 ) );
      panel[0].setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

      label[0].setText( " Font Name" );
      label[1].setText( " Size" );
      panel[0].add( label[0] );

      panel[1].add( label[2] );
      panel[1].add( label[1] );
      panel[1].add( label[3] );
      panel[0].add( panel[1] );

      panel[0].add( cmbFontName );

      panel[2].add( label[4] );
      panel[2].add( cmbFontSize );
      panel[2].add( label[5] );

      panel[0].add( panel[2] );
      panel[0].add( label[6] );
      panel[0].add( label[7] );

      panel[3].add( label[8] );
      panel[3].add( chkBold );
      panel[3].add( chkItalic );
      panel[3].add( label[9] );
      panel[0].add( panel[3] );

      panel[4].add( cmdOK );
      panel[4].add( label[10] );
      panel[4].add( cmdCancel );
      panel[0].add( panel[4] );

      panel[0].add( label[11] );
      panel[0].add( label[12] );
      panel[0].add( label[13] );

      for ( nLup = 0; nLup < strFontNames.length; nLup++ )
      {
        if ( currentFont.getName().equals( strFontNames[nLup] ) )
        {
          strFontName = new String( currentFont.getName() );
          break;
        }
      }
      cmbFontName.setSelectedIndex( nLup );

      for ( nLup = 0; nLup < strFontSize.length; nLup++ )
      {
        if ( currentFont.getSize() == Integer.parseInt( strFontSize[nLup] ) )
        {
          nSize = currentFont.getSize();
          break;
        }
      }
      cmbFontSize.setSelectedIndex( nLup );

      if ( ( currentFont.getStyle() & VTTUtils.BOLD ) == VTTUtils.BOLD )
        chkBold.setSelected( true );
      else
        chkBold.setSelected( false );

      if ( ( currentFont.getStyle() & VTTUtils.ITALIC ) == VTTUtils.ITALIC )
        chkItalic.setSelected( true );
      else
        chkItalic.setSelected( false );

      nStyle = currentFont.getStyle();

      c.add( panel[0], BorderLayout.CENTER );
    }
    catch( Exception e )
    {
      e.printStackTrace();
      VTTUtils.cry( "Failure Initializing Font Chooser", "Error" );
    }

  } // end InitFontChooser()


  public void actionPerformed( ActionEvent e )
  {
    if( e.getSource() == cmdOK )
    {
      createFont();
      closeDialog();
    }

    if( e.getSource() == cmdCancel )
    {
      font = new Font( currentFont.getName(),
                       currentFont.getStyle(),
                       currentFont.getSize() );
      closeDialog();
    }

    if ( chkBold.isSelected()   )
    {
      bBold   = true;
    }
    if ( chkItalic.isSelected() ) bItalic = true;

    if( e.getSource() != cmdCancel )
    {
//VTTUtils.say( "Got Here", "title" );
      createFont();
      label[13].setFont( font );
      label[13].setText( "Sample Text ABC abc 123" );
      label[13].repaint();
    }
  } // end actionPerformed( ActionEvent )


  private void createFont()
  {
    nStyle &= 0;

    if ( bBold   )
      nStyle |= Font.BOLD;
    if ( bItalic )
      nStyle |= Font.ITALIC;
    if ( !bBold && !bItalic )
      nStyle |= Font.PLAIN;

    nSize       = Integer.parseInt( strFontSize[cmbFontSize.getSelectedIndex()] );
    strFontName = new String( cmbFontName.getSelectedItem().toString() );
    font        = new Font( strFontName, nStyle, nSize );
  } // end createFont()


  protected String[] getFontList()
  {
    return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
  }


  public void closeDialog()
  {
    dispose();
  } // end closeDialog()


  protected void processWindowEvent( WindowEvent e )
  {
    super.processWindowEvent( e );
    if( e.getID() == WindowEvent.WINDOW_CLOSING )
    {
      closeDialog();
    }
  } // end processWindowEvent( WindowEvent )

} // End class VTTFontChooser
