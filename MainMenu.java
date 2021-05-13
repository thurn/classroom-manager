package ws.thurn.dossier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

/*
 * .
 */

/**
 * The MainMenu class generates the GUI for the program's MainMenu and provides
 * access to the other screens.
 * 
 * @author Derek Thurn
 */
public class MainMenu implements ActionListener
{
    private JButton classes;
    private JButton assignments;
    private JLabel title;
    private JPanel right;
    private JTextPane helpText;
    private JPanel lower;
    private JPanel mid;
    private JPanel upper;
    private JPanel main;
    private JPanel left;
    private JPanel bottom;
    private JPanel middle;
    private JPanel top;
    private JButton help;
    private JButton reports;
    private JButton grades;

    JFrame frm;

    /**
     * Creates a new main menu
     */
    public MainMenu()
    {
        setUpGUI();
    }

    /**
     * sets up the GUI for the main menu
     * 
     * @pre the correct objects have been declared
     * @post the GUI is displayed.
     */
    public void setUpGUI()
    {
        frm = new JFrame();
        frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        BoxLayout frmLayout = new BoxLayout( frm.getContentPane(),
                javax.swing.BoxLayout.Y_AXIS );
        frm.getContentPane().setLayout( frmLayout );
        frm.setSize( 800, 650 );
        frm.setTitle( "Welcome to Derek's Gradebook!" );
        GradeBook.center( frm );

        {
            top = new JPanel();
            frm.getContentPane().add( top );
            {
                title = new JLabel();
                top.add( title );
                title.setText( "Main Menu" );
                title.setFont( new java.awt.Font( "Arial Black", 1, 12 ) );
            }
        }
        {
            middle = new JPanel();
            BoxLayout middleLayout = new BoxLayout( middle,
                    javax.swing.BoxLayout.X_AXIS );
            middle.setLayout( middleLayout );
            frm.getContentPane().add( middle );
            {
                left = new JPanel();
                middle.add( left );
            }
            {
                main = new JPanel();
                BoxLayout mainLayout = new BoxLayout( main,
                        javax.swing.BoxLayout.Y_AXIS );
                main.setLayout( mainLayout );
                middle.add( main );
                {
                    upper = new JPanel();
                    BoxLayout upperLayout = new BoxLayout( upper,
                            javax.swing.BoxLayout.X_AXIS );
                    upper.setLayout( upperLayout );
                    main.add( upper );
                    {
                        classes = new JButton();
                        upper.add( classes );
                        try
                        {
                            classes.setIcon( new ImageIcon( getClass()
                                    .getClassLoader()
                                    .getResource( "cl2ass.gif" ) ) );
                        }
                        catch( NullPointerException npe )
                        {
                            GradeBook
                                    .error( "Required Icon could not be found. Please reinstall the program." );
                        }
                        classes.addActionListener( this );
                    }
                    {
                        assignments = new JButton();
                        upper.add( assignments );
                        try
                        {
                            assignments.setIcon( new ImageIcon( getClass()
                                    .getClassLoader()
                                    .getResource( "assign.gif" ) ) );
                        }
                        catch( NullPointerException npe )
                        {
                            GradeBook
                                    .error( "Required Icon could not be found. Please reinstall the program." );
                        }
                        assignments.addActionListener( this );
                    }
                }
                {
                    mid = new JPanel();
                    BoxLayout midLayout = new BoxLayout( mid,
                            javax.swing.BoxLayout.X_AXIS );
                    mid.setLayout( midLayout );
                    main.add( mid );
                    {
                        grades = new JButton();
                        mid.add( grades );
                        try
                        {
                            grades
                                    .setIcon( new ImageIcon( getClass()
                                            .getClassLoader().getResource(
                                                    "grade.gif" ) ) );
                        }
                        catch( NullPointerException npe )
                        {
                            GradeBook
                                    .error( "Required Icon could not be found. Please reinstall the program." );
                        }
                        grades.addActionListener( this );
                    }
                    {
                        reports = new JButton();
                        mid.add( reports );

                        try
                        {
                            reports.setIcon( new ImageIcon( getClass()
                                    .getClassLoader()
                                    .getResource( "report.gif" ) ) );
                        }
                        catch( NullPointerException npe )
                        {
                            GradeBook
                                    .error( "Required Icon could not be found. Please reinstall the program." );
                        }
                        reports.addActionListener( this );
                    }
                }
                {
                    lower = new JPanel();
                    BoxLayout lowerLayout = new BoxLayout( lower,
                            javax.swing.BoxLayout.X_AXIS );
                    lower.setLayout( lowerLayout );
                    main.add( lower );
                    {
                        help = new JButton();
                        lower.add( help );
                        try
                        {
                            help
                                    .setIcon( new ImageIcon( getClass()
                                            .getClassLoader().getResource(
                                                    "help.gif" ) ) );
                        }
                        catch( NullPointerException npe )
                        {
                            GradeBook
                                    .error( "Required Icon could not be found. Please reinstall the program." );
                        }
                        help.addActionListener( this );
                    }
                }
            }
        }
        {
            right = new JPanel();
            middle.add( right );
        }
        {
            bottom = new JPanel();
            frm.getContentPane().add( bottom );
            {
                helpText = new JTextPane();
                bottom.add( helpText );
                helpText
                        .setText( "To get started, create a class by pressing the class button. You can then\n add students to the class. After this, create assignments for the class\n using the Assignments button, then enter grades for the class using the\n Grades button. When you're finished, the Reports button can be used to\n view the information. Press Help for more comprehensive user information." );
            }
        }

        frm.setVisible( true );

        frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    /**
     * Listens for button presses.
     * 
     * @pre the correct listeners have been added.
     * @post the program loads the correct screen.
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( classes ) )
        {
            frm.dispose();
            new ClassesScreen();

        }

        if( evt.getSource().equals( assignments ) )
        {
            frm.dispose();
            new AssignmentsScreen();

        }

        if( evt.getSource().equals( grades ) )
        {
            if( GradeBook.getNumClasses() < 1 )
            {
                GradeBook
                        .error( "You must create a class before adding grades" );
                new MainMenu();
            }
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "No class selected." );
                return;
            }
            if( !GradeBook.getCurrentClass().classGradeReady() )
            {
                GradeBook
                        .error( "The current class does not have students or assignments" );
            }

            frm.dispose();
            new GradesScreen();
        }

        if( evt.getSource().equals( reports ) )
        {
            frm.dispose();
            new ReportsScreen();
        }

        if( evt.getSource().equals( help ) )
        {
            frm.dispose();
            new HelpScreen();
        }

    }

}
