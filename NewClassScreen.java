package ws.thurn.dossier;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * .
 */

/**
 * The NewClassScreen class contains the GUI and methods for adding a new class
 * to the program.
 * 
 * @author Derek Thurn
 */
public class NewClassScreen implements ActionListener
{

    JLabel Title;
    JPanel top;
    JPanel opt;
    JLabel info;
    JButton create;
    JButton cancel;
    JPanel upper;
    JTextField schoolNameField;
    JLabel schoolNameLabel;
    JTextField teacherNameField;
    JLabel teacherNameLabel;
    JTextField classNameField;
    JLabel classNameLabel;
    JPanel low;
    JPanel mid;
    JPanel doIt;
    JPanel classInfo;
    JFrame newClass;

    /**
     * Creates a new screen for adding a class to the program.
     */
    public NewClassScreen()
    {
        init();
    }

    /**
     * Displays the GUI for the screen
     * 
     * @pre the correct objects have been declared
     * @post the GUI is displayed.
     */
    public void init()
    {
        newClass = new JFrame();
        newClass.setTitle( "New Class" );
        newClass.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        Container c = newClass.getContentPane();

        BoxLayout thisLayout = new BoxLayout( newClass.getContentPane(),
                javax.swing.BoxLayout.Y_AXIS );

        newClass.setLayout( thisLayout );

        {
            top = new JPanel();
            c.add( top );
            top.setPreferredSize( new java.awt.Dimension( 292, 40 ) );
            {
                Title = new JLabel();
                top.add( Title );
                Title.setLayout( null );
                Title.setText( "Create A New Class" );
                Title.setFont( new java.awt.Font( "Comic Sans MS", 1, 20 ) );
            }
        }
        {
            classInfo = new JPanel();
            BoxLayout classInfoLayout = new BoxLayout( classInfo,
                    javax.swing.BoxLayout.Y_AXIS );
            classInfo.setLayout( classInfoLayout );
            c.add( classInfo );
            classInfo.setSize( 50, 50 );
            {
                upper = new JPanel();
                FlowLayout upperLayout = new FlowLayout();
                upper.setLayout( upperLayout );
                classInfo.add( upper );
                upper.setPreferredSize( new java.awt.Dimension( 292, 60 ) );
                {
                    classNameLabel = new JLabel();
                    upper.add( classNameLabel );
                    classNameLabel.setText( "Class Name:" );
                }
                {
                    classNameField = new JTextField();
                    upper.add( classNameField );
                    classNameField.setPreferredSize( new java.awt.Dimension(
                            139, 20 ) );
                }
            }
            {
                mid = new JPanel();
                FlowLayout midLayout = new FlowLayout();
                mid.setLayout( midLayout );
                classInfo.add( mid );
                mid.setPreferredSize( new java.awt.Dimension( 292, 54 ) );
                {
                    teacherNameLabel = new JLabel();
                    mid.add( teacherNameLabel );
                    teacherNameLabel.setText( "Teacher Name*:" );
                    teacherNameLabel.setOpaque( true );
                }
                {
                    teacherNameField = new JTextField();
                    mid.add( teacherNameField );
                    teacherNameField.setSize( 6, 20 );
                    teacherNameField.setPreferredSize( new java.awt.Dimension(
                            150, 20 ) );
                }
            }
            {
                low = new JPanel();
                FlowLayout lowLayout = new FlowLayout();
                low.setLayout( lowLayout );
                classInfo.add( low );
                low.setPreferredSize( new java.awt.Dimension( 292, 58 ) );
                {
                    schoolNameLabel = new JLabel();
                    low.add( schoolNameLabel );
                    schoolNameLabel.setText( "School Name*:" );
                }
                {
                    schoolNameField = new JTextField();
                    low.add( schoolNameField );
                    schoolNameField.setPreferredSize( new java.awt.Dimension(
                            146, 20 ) );
                }
            }
        }
        {
            opt = new JPanel();
            c.add( opt );
            {
                info = new JLabel();
                opt.add( info );
                info.setText( "* Optional" );
            }
        }
        {
            doIt = new JPanel();
            c.add( doIt );
            doIt.setPreferredSize( new java.awt.Dimension( 292, 43 ) );
            {
                create = new JButton();
                doIt.add( create );
                create.setText( "Create" );
            }
            {
                cancel = new JButton();
                doIt.add( cancel );
                cancel.setText( "Cancel" );
            }
        }
        newClass.setSize( 300, 300 );
        newClass.setVisible( true );
        newClass.setTitle( "Add a new class." );
        GradeBook.center( newClass );

        create.addActionListener( this );
        cancel.addActionListener( this );
        schoolNameField.addActionListener( this );
        teacherNameField.addActionListener( this );
        classNameField.addActionListener( this );

    }

    /**
     * Listens for button presses
     * 
     * @pre listeners have been added
     * @post the correct action is taken after an event.
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( cancel ) )
        {
            newClass.dispose();
            new ClassesScreen();
        }

        if( evt.getSource().equals( create ) )
        {
            if( ( classNameField.getText() ).equals( "" ) )
            {
                JOptionPane.showMessageDialog( null,
                        "The class name field cannot be blank.", "Error",
                        JOptionPane.ERROR_MESSAGE );
                return;
            }
            if( classNameField.getText().length() > GradeBook.PROGRAM_LIMIT )
            {
                GradeBook
                        .error( "The class name is too long. Please shorten it." );
                return;
            }
            else
            {
                String cn = classNameField.getText();
                String tn;
                String sn;

                if( teacherNameField.getText() != null )
                    tn = teacherNameField.getText();
                else
                    tn = "";

                if( schoolNameField.getText() != null )
                    sn = schoolNameField.getText();
                else
                    sn = "";
                Klass temp = new Klass( cn, tn, sn );

                if( GradeBook.isDuplicate( temp ) )
                {
                    JOptionPane.showMessageDialog( null,
                            "A class with this name already exists.", "Error",
                            JOptionPane.ERROR_MESSAGE );
                }
                else
                {
                    GradeBook.addClass( temp );
                    newClass.dispose();
                    new ClassesScreen();
                }
            }

        }

    }

}