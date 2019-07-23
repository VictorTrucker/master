/************************************************************
*   Program: VTTPieceWorkFrame.java                         *
*    Author: Victor Trucker                                 *
*      Date: 04/13/2000                                     *
*   Purpose: To provide a Frame in which we can play...     *
************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import java.util.Vector;


public class VTTPieceWorkFrame extends JFrame implements ActionListener
{
  private double
    dTotalAmount  = 0.00;
  private int
    nFont         = 0,
    nFontCount    = 3,
    nPieces       = 0,
    nPayGrade     = 0;
  private String
    strWorker     = new String( "" ),
    strFontMenu[] = new String[nFontCount],
    strStyles[]   = { " Plain", " Bold", " Italic" },
    strGrades[]   = { " Pay Grade A", " Pay Grade B", " Pay Grade C" };
  private JMenuBar
    menuBar       = new JMenuBar();
  private JMenu
    menuFile      = new JMenu( "File" ),
    menuEdit      = new JMenu( "Edit" ),
    menuEditFont  = new JMenu( "Font" ),
    menuHelp      = new JMenu( "Help" );
  private JMenuItem
    menuFileCalc  = new JMenuItem( "Calculate Pay" ),
    menuFileSumm  = new JMenuItem( "Summary" ),
    menuFileExit  = new JMenuItem( "Exit" ),
    menuEditClear = new JMenuItem( "Clear" ),
    menuEditFontItem[] = new JMenuItem[nFontCount],
    menuEditColor = new JMenuItem( "Select Color" ),
    menuHelpAbout = new JMenuItem( "About Piece Work..." );
  private JLabel
    lblSpacer[]   = new JLabel[15],
    lblWorker     = new JLabel( " Worker" ),
    lblGrade      = new JLabel( " Pay Grade" ),
    lblPieces     = new JLabel( " Number of Pieces" ),
    statusBar     = new JLabel( " Ready..." );
  private StringBuffer
    strSummary    = new StringBuffer( "" );
  private JComboBox
    cmbGrade      = new JComboBox( strGrades ),
    cmbWorker     = new JComboBox();
  private JTextField
    txtPieces     = new JTextField();
  private JTextArea
    txtSummary    = new JTextArea( "", 12, 30 );
  private JScrollPane
    scroll        = new JScrollPane( txtSummary );
  private JPanel
    pnlNLabel     = new JPanel(),
    pnlGLabel     = new JPanel(),
    pnlPLabel     = new JPanel(),
    pnlName       = new JPanel(),
    pnlGrade      = new JPanel(),
    pnlPieces     = new JPanel(),
    pnlWorker     = new JPanel(); 
  private Font
    font[]        = new Font[nFontCount];
  private Color
    color         = new Color( 0, 0, 0 );
  private Vector
    workerArray   = new Vector( 10, 10 );
  private VTTWorker
      worker    = new VTTWorker( "", 0, 0 );


  public VTTPieceWorkFrame()
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
  } // end VTTPieceWorkFrame()


  private void initFrame() throws Exception
  {
    int
      nLup = 0;

    for ( nLup = 0; nLup < lblSpacer.length; nLup++ )
      lblSpacer[nLup] = new JLabel();

    font[0] = VTTUtils.getFont( "Arial",           VTTUtils.BOLD, 18 );
    font[1] = VTTUtils.getFont( "Courier",         VTTUtils.BOLD, 18 );
    font[2] = VTTUtils.getFont( "Times New Roman", VTTUtils.BOLD, 18 );
    buildFontMenu();

    workerArray.addElement( new VTTWorker( "No Name", 0, 0 ) );
    txtSummary.setEditable( false );

    setSize( 500, 300 );
    setTitle( "Piece Work" );
    setResizable( false );
    cmbWorker.addItem( "Type in or Select a Worker" );
    cmbWorker.setEditable( true );
    menuFileSumm.setEnabled( false );

    buildMenus();
    addMnemonics();
    addListeners();
    buildPanels();

    setJMenuBar( menuBar );

    pnlWorker.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );
    getContentPane().setLayout( new BorderLayout() );
    getContentPane().add( pnlWorker, BorderLayout.WEST );
    getContentPane().add( statusBar, BorderLayout.SOUTH );

    if ( VTTUtils.askYesNo( "Project 8 - Part 1...\nPeice Work...",
                            "Bits -n- Pieces..." ) == VTTUtils.NO )
    {
      System.exit( 0 );
    }
  } // end initFrame()


  public void actionPerformed( ActionEvent e )
  {
    if ( e.getSource() == menuFileCalc )
    {
      statusBar.setText( " Calculate Pay Based on Current Selections" );
      fileCalc();
    }
    else if ( e.getSource() == menuFileSumm )
    {
      statusBar.setText( " Show Summary Report of current Data" );
      fileSumm();
    }
    else if ( e.getSource() == menuFileExit )
    {
      statusBar.setText( " Exit Software" );
      fileExit();
    }
    else if ( e.getSource() == menuEditClear )
    {
      statusBar.setText( " Reset Form to Default Data" );
      editClear();
    }
    else if ( e.getSource() == menuEditFontItem[0] )
    {
//      editFont();
      statusBar.setText( " Set Font for the Total Pay Text to Arial" );
      nFont = 0;
    }
    else if ( e.getSource() == menuEditFontItem[1] )
    {
      statusBar.setText( " Set Font for the Total Pay Text to Courrier" );
      nFont = 1;
    }
    else if ( e.getSource() == menuEditFontItem[2] )
    {
      statusBar.setText( " Set Font for the Total Pay Text to Times New Roman" );
      nFont = 2;
    }
    else if ( e.getSource() == menuEditColor )
    {
      statusBar.setText( " Set Color for the Total Pay Text" );
      editColor();
    }
    else if ( e.getSource() == menuHelpAbout )
    {
      statusBar.setText( " Program Information, Version Number and copyright" );
      helpAbout();
    }
    else if ( e.getSource() == cmbWorker )
    {
      updateData( cmbWorker.getSelectedItem().toString() );
    }

    nPieces = getGrade( cmbWorker.getSelectedItem().toString(), cmbGrade.getSelectedIndex() );
    txtPieces.setText( "" + nPieces );
    statusBar.setText( " Ready..." );
    System.gc();
  } // end actionPerformed( ActionEvent )


  public void updateList( String strWkr )
  {
    int
      nLup = 0;

    for ( nLup = 1; nLup < cmbWorker.getItemCount(); nLup++ )
    {
      if ( cmbWorker.getItemAt( nLup ).toString().compareToIgnoreCase( strWkr ) == 0 ) return;
    }
    cmbWorker.addItem( strWkr );
  } // end updateList( String )


  public void updateData( String strWkr )
  {
    int
      nLup  = 0,
      nLup2 = 0;

    for ( nLup = 1; nLup < workerArray.size(); nLup++ )
    {
      worker = ( VTTWorker )workerArray.elementAt( nLup );

      if ( worker.strWorker.compareToIgnoreCase( strWkr ) == 0 )
      {
        strWorker = new String( strWkr );
        nPayGrade = cmbGrade.getSelectedIndex();
        nPieces   = worker.nPieces[nPayGrade];
        txtPieces.setText( "" + nPieces );
        repaint();
        nLup = workerArray.size();
      }
    }
  } // end updateData()


  public int getGrade( String strWkr, int nWhich )
  {
    int
      nLup  = 0,
      nLup2 = 0;

    for ( nLup = 1; nLup < workerArray.size(); nLup++ )
    {
      worker = ( VTTWorker )workerArray.elementAt( nLup );

      if ( worker.strWorker.compareToIgnoreCase( strWkr ) == 0 )
      {
        return worker.nPieces[nWhich];
      }
    }
    return 0;
  } // end getGrade( String, int )


  public void fileCalc()
  {
    int
      nLup      = 0,
      nLup2     = 0;
    boolean
      bAdd      = true;
    String
      strPieces = txtPieces.getText();
    Graphics
      gr        = this.getGraphics();

    nPayGrade = cmbGrade.getSelectedIndex();
    strWorker = cmbWorker.getSelectedItem().toString().trim();
    updateList( strWorker );

    if ( strWorker.equals( "" ) || strWorker.equals( "Type in or Select a Worker" ) )
    {
      strWorker = "";
      VTTUtils.say( "Please Type in a Workers Name\n" +
                    "or Select one from the Dropdown List.", "No Worker" );
      cmbWorker.setSelectedIndex( cmbWorker.getSelectedIndex() );
      cmbWorker.requestFocus();
      return;
    }

    if ( strPieces.trim().equals( "" ) || !VTTUtils.validateInt( strPieces ) )
    {
      VTTUtils.say( "Invalid Number of Pieces!\nIntegers Only Please", "Error" );
      txtPieces.requestFocus();
      txtPieces.selectAll();
      return;
    }
    else
    {
      nPieces = Integer.parseInt( strPieces );
    }

    if ( nPieces < 0 || nPieces > 1000 )
    {
      nPieces = 0;
      VTTUtils.say( "Number of Pieces is Out of Range.\nValid Range: 0 to 1000", "Error" );
      txtPieces.requestFocus();
      txtPieces.selectAll();
      return;
    }
    // Whew! we made it!...

    for ( nLup = 1; nLup < workerArray.size(); nLup++ )
    {
      worker = ( VTTWorker )workerArray.elementAt( nLup );

      if ( worker.strWorker.compareToIgnoreCase( strWorker ) == 0 )
      {
        for ( nLup2 = 1; nLup2 < cmbWorker.getItemCount(); nLup2++ )
        {
          if ( cmbWorker.getItemAt( nLup2 ).toString().compareToIgnoreCase( strWorker ) == 0 &&
               cmbWorker.getItemAt( nLup2 ).toString().compareTo( strWorker ) != 0 )
          {
            cmbWorker.removeItemAt( nLup2 );
            cmbWorker.insertItemAt( strWorker, nLup2 );
            worker.strWorker = new String( strWorker );
            nLup2 = cmbWorker.getItemCount();
          }
        }
        bAdd = false;
        nLup = workerArray.size();
        worker.setPieces( nPayGrade, nPieces );
      }
    }
    if ( bAdd )
      workerArray.addElement( new VTTWorker( strWorker, nPayGrade, nPieces ) );


    for ( nLup = 1; nLup < workerArray.size(); nLup++ )
    {
      worker = ( VTTWorker )workerArray.elementAt( nLup );

      if ( worker.strWorker.compareToIgnoreCase( strWorker ) == 0 )
      {
        dTotalAmount = worker.dAmount[0] + worker.dAmount[1] + worker.dAmount[2];
        nLup = workerArray.size();
      }
    }
    menuFileSumm.setEnabled( true );
    repaint();

  } // end fileCalc()


  public void fileSumm()
  {
    buildSummary();
    scroll.getVerticalScrollBar().setValue( -1 );
    VTTUtils.say( scroll, "Summary" );
  } // end fileSumm()


  public void fileExit()
  {
    if ( VTTUtils.askYesNo( "Exit Software, Why?", "Adios..." ) == VTTUtils.YES )
      System.exit(0);
  } // end fileExit()


  public void editClear()
  {
    cmbGrade.setSelectedIndex( 0 );
    cmbWorker.setSelectedIndex( 0 );
    txtPieces.setText( "0" );
  } // end editClear()


  public void editColor()
  {
    final JColorChooser
      jcc  = new JColorChooser( Color.red );

    color = jcc.showDialog( this, "Please Select a Color for the Output Text", Color.red );
    repaint();
  } // end editColor()


  public void editFont()
  {
  }


  public void helpAbout()
  {
    VTTPieceWorkAboutBox
      dlg = new VTTPieceWorkAboutBox( this );
    Dimension
      frmSize = getSize(),
      dlgSize = dlg.getPreferredSize();
    Point
      loc = getLocation();

    dlg.setLocation( ( frmSize.width  - dlgSize.width  ) / 2 + loc.x,
                     ( frmSize.height - dlgSize.height ) / 2 + loc.y );
    dlg.setModal( true );
    dlg.show();
  } // end helpAbout()


  public void buildSummary()
  {
    int
      nLup  = 0,
      nLup2 = 0;
      
    strSummary.setLength( 0 );

    strSummary.append( "==============================\r\n" );
    if ( workerArray.size() < 2 )
    {
      strSummary.append( "No Records to Summarize\r\n" );
      strSummary.append( "==============================\r\n" );
    }

    for ( nLup = 1; nLup < workerArray.size(); nLup++ )
    {
      worker = ( VTTWorker )workerArray.elementAt( nLup );
      strSummary.append( worker.toString() );
      strSummary.append( "==============================\r\n" );
    }

    txtSummary.setText( strSummary.toString() );
  } // end buildSummary()


  public void paint( Graphics gr )
  {
    int
      nLup  = 0,
      nLeft = 210,
      nTop  = 175;

    super.paint( gr );

    gr.setFont( font[nFont] );
    gr.setColor( color );
    gr.drawString( ( worker.strWorker.trim().equals( "" ) )
                     ? "No Name"
                     : worker.strWorker, 210, 150 );

    for ( nLup = 0; nLup < 3; nLup++ )
    {
      gr.drawString( strGrades[nLup].trim() + " Pieces:  " +
                     worker.nPieces[nLup], 210, nTop + ( nLup * 25 ) );
    }

    gr.drawString( "Total Amount:  " +
                   VTTUtils.formatMoney.format( dTotalAmount ),
                   210, ( nTop + 75 ) );
  } // end paint( Graphics )


  private void addMnemonics()
  {
    int
      nLup      = 0,
      nLup2     = 0,
      nPosition = 0;
    StringBuffer
      strHoldMnemonics = new StringBuffer( "" );

    menuFile.setMnemonic( 'F' ); ///File////////
    menuFileCalc.setMnemonic( 'C' );
    menuFileSumm.setMnemonic( 'S' );
    menuFileExit.setMnemonic( 'x' );

    menuEdit.setMnemonic( 'E' ); ///Edit////////
    menuEditClear.setMnemonic( 'C' );
    menuEditFont.setMnemonic(  'F' );

    for ( nLup = 0; nLup < font.length; nLup++ )
    {
      nPosition = 0;

      for ( nLup2 = 0; nLup2 < strFontMenu[nLup].length(); nLup2++ )
      {
        nPosition = nLup2;

        if ( strHoldMnemonics.toString().indexOf( strFontMenu[nLup].charAt( nPosition ) ) == -1 )
          nLup2 = strFontMenu[nLup].length();
      }

      menuEditFontItem[nLup].setMnemonic( strFontMenu[nLup].charAt( nPosition ) );
      strHoldMnemonics.append( strFontMenu[nLup].charAt( nPosition ) );
    }

    menuEditColor.setMnemonic( 'l' );

    menuHelp.setMnemonic( 'H' ); ///Help////////
    menuHelpAbout.setMnemonic( 'A' );
  } // end addMnemonics()


  private void addListeners()
  {
    int
      nLup = 0;

    menuFileCalc.addActionListener( this );
    menuFileSumm.addActionListener( this );
    menuFileExit.addActionListener( this );

    menuEditClear.addActionListener( this );

    for ( nLup = 0; nLup < menuEditFontItem.length; nLup++ )
      menuEditFontItem[nLup].addActionListener( this );

    menuEditColor.addActionListener( this );

    menuHelpAbout.addActionListener( this );

    cmbWorker.addActionListener( this );
    cmbGrade.addActionListener( this );
  } // end addListeners()


  private void buildFontMenu()
  {
    int
      nLup   = 0,
      nLup2  = 0,
      nWhich = 0;

    for ( nLup = 0; nLup < font.length; nLup++ )
    {
      for ( nLup2 = 0; nLup2 < VTTUtils.FONT_STYLE.length; nLup2++ )
      {
        if ( font[nLup].getStyle() == VTTUtils.FONT_STYLE[nLup2] )
        {
          nWhich =  nLup2;
          nLup2  = VTTUtils.FONT_STYLE.length;
        }
      }

      strFontMenu[nLup] = new String(
                                     font[nLup].getName() + " " +
                                     font[nLup].getSize() + " Point " +
                                     strStyles[nWhich]
                                    );

      menuEditFontItem[nLup] = new JMenuItem( strFontMenu[nLup] );
    }
  }


  private void buildMenus()
  {
    int
      nLup = 0;

    menuFile.add( menuFileCalc );
    menuFile.add( menuFileSumm );
    menuFile.addSeparator();
    menuFile.add( menuFileExit );

    menuEdit.add( menuEditClear );
    menuEdit.addSeparator();

    for ( nLup = 0; nLup < menuEditFontItem.length; nLup++ )
      menuEditFont.add( menuEditFontItem[nLup] );

    menuEdit.add( menuEditFont );
    menuEdit.add( menuEditColor );

    menuHelp.add( menuHelpAbout );

    menuBar.add( menuFile );
    menuBar.add( menuEdit );
    menuBar.add( menuHelp );
  } // end buildMenus()


  private void buildPanels()
  {
    pnlNLabel.setLayout( new GridLayout( 1, 3 ) );
    pnlGLabel.setLayout( new GridLayout( 1, 3 ) );
    pnlPLabel.setLayout( new GridLayout( 1, 3 ) );
    pnlName.setLayout( new GridLayout( 1, 3 ) );
    pnlGrade.setLayout( new GridLayout( 1, 3 ) );
    pnlPieces.setLayout( new GridLayout( 1, 3 ) );
    pnlWorker.setLayout( new GridLayout( 9, 1 ) );

    pnlNLabel.add( lblWorker );
    pnlNLabel.add( lblSpacer[0] );
    pnlNLabel.add( lblSpacer[1] );

    pnlName.add( cmbWorker );
    pnlName.add( lblSpacer[2] );
    pnlName.add( lblSpacer[3] );

    pnlGLabel.add( lblGrade );
    pnlGLabel.add( lblSpacer[4] );
    pnlGLabel.add( lblSpacer[5] );

    pnlGrade.add( cmbGrade );
    pnlGrade.add( lblSpacer[6] );
    pnlGrade.add( lblSpacer[7] );

    pnlPLabel.add( lblPieces );
    pnlPLabel.add( lblSpacer[8] );
    pnlPLabel.add( lblSpacer[9] );

    pnlPieces.add( txtPieces );
    pnlPieces.add( lblSpacer[10] );
    pnlPieces.add( lblSpacer[11] );

    pnlWorker.add( pnlNLabel );
    pnlWorker.add( pnlName );
    pnlWorker.add( pnlGLabel );
    pnlWorker.add( pnlGrade );
    pnlWorker.add( pnlPLabel );
    pnlWorker.add( pnlPieces );
  } // end buildPanels()


  protected void processWindowEvent( WindowEvent e )
  {
    if ( WindowEvent.WINDOW_CLOSING == e.getID() )
    {
      fileExit();
    }
  } // end processWindowEvent( WindowEvent )

} // End class VTTPieceWorkFrame
 