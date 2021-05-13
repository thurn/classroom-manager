package ws.thurn.dossier;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * .
 */

/**
 * The Screen class provides the template for all of the main screens of the
 * program. It standardizes layout and button placement.
 * 
 * @author Derek Thurn
 */
public abstract class Screen implements ActionListener

{

    public JFrame frm;
    public JButton knew;
    private JLabel currentlyLabel;
    private JPanel topright;
    private JPanel topleft;
    public JButton back;
    public JButton save;
    public JButton load;
    private JPanel Main;
    public JPanel content;
    public JComboBox currentCombo;

    /**
     * Builds the GUI for the screen object.
     * 
     * @pre the correct variables have been declared
     * @post the GUI has been displayed.
     */
    public void init()
    {
        try
        {
            {
                frm = new JFrame();
                BoxLayout frmLayout = new BoxLayout( frm.getContentPane(),
                        javax.swing.BoxLayout.Y_AXIS );
                frm.getContentPane().setLayout( frmLayout );
                frm.setSize( 800, 650 );
                GradeBook.center( frm );
                frm.setVisible( true );

                Main = new JPanel();
                BoxLayout MainLayout = new BoxLayout( Main,
                        javax.swing.BoxLayout.X_AXIS );
                Main.setLayout( MainLayout );
                frm.getContentPane().add( Main, BorderLayout.NORTH );
                {
                    topleft = new JPanel();
                    Main.add( topleft );
                    topleft
                            .setPreferredSize( new java.awt.Dimension( 297, 47 ) );
                    {
                        knew = new JButton();
                        topleft.add( knew );
                        knew.setIcon( new ImageIcon( getClass()
                                .getClassLoader().getResource( "new.gif" ) ) );
                        knew.setToolTipText( "New" );
                    }
                    {
                        load = new JButton();
                        topleft.add( load );
                        load.setIcon( new ImageIcon( getClass()
                                .getClassLoader().getResource( "load.gif" ) ) );
                        load.setToolTipText( "Load" );
                    }
                    {
                        save = new JButton();
                        topleft.add( save );
                        save.setIcon( new ImageIcon( getClass()
                                .getClassLoader().getResource( "save.gif" ) ) );
                        save.setToolTipText( "Save" );
                    }
                }
                {
                    topright = new JPanel();
                    Main.add( topright );
                    topright
                            .setPreferredSize( new java.awt.Dimension( 555, 47 ) );
                    {
                        currentlyLabel = new JLabel();
                        topright.add( currentlyLabel );
                        currentlyLabel.setText( "Currently Selected Class:" );

                    }
                    {
                        String classes[] = GradeBook.getClassesStringArray();
                        ComboBoxModel classComboModel = new DefaultComboBoxModel(
                                classes );
                        currentCombo = new JComboBox();
                        topright.add( currentCombo );
                        currentCombo.setModel( classComboModel );

                        currentCombo.addActionListener( this );

                        if( GradeBook.getCurrentClassLocation() < 0 )
                            currentCombo.setSelectedIndex( -1 );
                        else
                            currentCombo.setSelectedIndex( GradeBook
                                    .getCurrentClassLocation() );
                    }
                    {
                        back = new JButton();
                        topright.add( back );
                        back.setIcon( new ImageIcon( getClass()
                                .getClassLoader().getResource( "back.gif" ) ) );
                        back.setToolTipText( "Return to the Main Menu" );
                    }
                }

                back.addActionListener( this );
                knew.addActionListener( this );
                save.addActionListener( this );
                load.addActionListener( this );

            }
            {
                content = new JPanel();
                BoxLayout ContentLayout = new BoxLayout( content,
                        javax.swing.BoxLayout.Y_AXIS );
                content.setLayout( ContentLayout );

                frm.getContentPane().add( content );
                content.setPreferredSize( new java.awt.Dimension( 386, 569 ) );
                content.setSize( 792, 800 );

                frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                frm.setFocusCycleRoot( false );
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

}
