package ws.thurn.dossier;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * .
 */

/**
 * The HelpScreen class generates the GUI for the Help Screen, allowing for user
 * assistance.
 * 
 * @author Derek Thurn
 */
public class HelpScreen extends Screen
{

    private JPanel right;
    private JButton html;
    private JButton pdf;
    private JButton doc;
    private JPanel jPanel2;
    private JPanel jPanel1;
    private JButton gettingStarted;
    private JPanel left;
    private JPanel middle;
    private JPanel top;
    private JPanel center;
    private JLabel infoBox;

    /**
     * Creates a new Help Screen.
     */
    public HelpScreen()
    {
        super.init();
        GUI();
    }

    /**
     * Displays the program's GUI
     * 
     * @pre the correct objects have been declared
     * @post the GUI is displayed
     */
    public void GUI()
    {
        {
            BoxLayout contentLayout = new BoxLayout( content,
                    javax.swing.BoxLayout.X_AXIS );
            content.setLayout( contentLayout );
            frm.setTitle( "Help" );
            frm.getContentPane().add( content, BorderLayout.CENTER );
            content.setPreferredSize( new java.awt.Dimension( 792, 573 ) );
            {
                left = new JPanel();
                content.add( left );
            }
            {
                center = new JPanel();
                content.add( center );
                BoxLayout centerLayout = new BoxLayout( center,
                        javax.swing.BoxLayout.Y_AXIS );
                center.setLayout( centerLayout );
                center.setPreferredSize( new java.awt.Dimension( 238, 573 ) );
                {
                    top = new JPanel();
                    BoxLayout topLayout = new BoxLayout( top,
                            javax.swing.BoxLayout.Y_AXIS );
                    top.setLayout( topLayout );
                    center.add( top );
                    top.setPreferredSize( new java.awt.Dimension( 659, 184 ) );
                    top.setSize( 200, 324 );
                    {
                        infoBox = new JLabel();
                        top.add( infoBox );
                        infoBox.setText( "Select a help option:" );
                        infoBox.setFont( new java.awt.Font( "Tahoma", 0, 18 ) );
                    }
                }
                {
                    middle = new JPanel();
                    BoxLayout middleLayout = new BoxLayout( middle,
                            javax.swing.BoxLayout.Y_AXIS );
                    middle.setLayout( middleLayout );
                    center.add( middle );
                    middle
                            .setPreferredSize( new java.awt.Dimension( 109, 117 ) );
                    {
                        gettingStarted = new JButton();
                        middle.add( gettingStarted );
                        gettingStarted.setText( "Getting Started" );
                        gettingStarted.addActionListener( this );
                    }
                    {
                        html = new JButton();
                        middle.add( html );
                        html.setText( "View Help Guide (HTML)" );
                        html.addActionListener( this );
                    }
                    {
                        doc = new JButton();
                        middle.add( doc );
                        doc.setText( "View Help Guide (doc)" );
                        doc.addActionListener( this );
                    }
                    {
                        pdf = new JButton();
                        middle.add( pdf );
                        pdf.setText( "View Help Guide (pdf)" );
                        pdf.addActionListener( this );
                    }
                }
                {
                    jPanel2 = new JPanel();
                    center.add( jPanel2 );
                }
                {
                    jPanel1 = new JPanel();
                    center.add( jPanel1 );
                }
            }
            {
                right = new JPanel();
                content.add( right );
            }
        }

    }

    /**
     * Listens for button clicks.
     * 
     * @pre the listeners have been added
     * @post the correct response is taken.
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( back ) )
        {
            frm.dispose();
            new MainMenu();
            return;
        }

        if( evt.getSource().equals( currentCombo ) )
        {
            if( currentCombo.getSelectedItem() == null )
                return;

            String cl = currentCombo.getSelectedItem().toString();
            Klass temp;
            temp = GradeBook.getClassFromString( cl );
            GradeBook.setCurrentClass( temp );

        }
        if( evt.getSource().equals( save ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "You must select a class to save." );
                return;
            }

            File f = GradeBook.getSaveFile();
            try
            {
                RandomAccessFile io = new RandomAccessFile( f, "rw" );
                long classAt = GradeBook.fileFindClass( GradeBook
                        .getCurrentClass() );
                if( classAt == -1 )
                {
                    new FileSaver( io, GradeBook.getCurrentClass() );
                    GradeBook.info( "The class "
                            + GradeBook.getCurrentClass().toString()
                            + " has been sucessfully saved." );
                }
                else
                {
                    new FileSaver( io, GradeBook.getCurrentClass(), classAt );
                    GradeBook.info( "The class "
                            + GradeBook.getCurrentClass().toString()
                            + " has been sucessfully saved." );
                }
            }
            catch( IOException e )
            {
                GradeBook.error( "IO Exception in CLASS SCREEN!" );

            }
        }
        if( evt.getSource().equals( load ) )
        {
            if( GradeBook.getSaveFile().length() < 100 )
            {
                GradeBook.error( "No classed saved." );
                return;
            }

            frm.dispose();
            new FileLoadUI( "help" );
        }

        if( evt.getSource().equals( knew ) )
        {
            frm.dispose();
            new NewClassScreen();
        }

        if( evt.getSource().equals( html ) )
        {
            new Help( "html" );
        }
        if( evt.getSource().equals( doc ) )
        {
            new Help( "doc" );
        }
        if( evt.getSource().equals( pdf ) )
        {
            new Help( "pdf" );
        }
        if( evt.getSource().equals( gettingStarted ) )
        {
            new Help( "start" );
        }
    }

}
