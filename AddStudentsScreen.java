package ws.thurn.dossier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * .
 */

/**
 * The AddStudentsScreen class displays a GUI that adds students to the program.
 * 
 * @author Derek Thurn
 */
public class AddStudentsScreen implements ActionListener
{

    Klass theClass;
    // The class to which the students are being added

    JFrame addStudentsFrame;

    int numAdding;
    // The number of students to be added.

    JButton create;
    JButton cancel;

    JTextField nameF;
    JTextField idF;
    JTextField numToAdd;

    /**
     * Add students by specifying the number of students to add and the class to
     * which they are being added.
     * 
     * @param na
     * @param tc
     */
    public AddStudentsScreen( int na, Klass tc )
    {
        numAdding = na;
        theClass = tc;
        displayScreen();
    }

    /**
     * Displays the GUI.
     * 
     * @pre Specific GUI objects have been declared.
     * @post GUI is displayed
     */
    public void displayScreen()
    {

        addStudentsFrame = new JFrame();
        addStudentsFrame.setTitle( "Add Students Screen" );
        addStudentsFrame.setSize( 300, 300 );

        JLabel nameL = new JLabel( "Student Name:" );
        JLabel idL = new JLabel( "Student ID (Optional):" );
        JLabel numToAddL = new JLabel( "Students left to add." );

        nameF = new JTextField( 15 );
        idF = new JTextField( 15 );
        numToAdd = new JTextField( 5 );

        numToAdd.setText( "" + numAdding );
        numToAdd.setEditable( false );

        create = new JButton( "Add" );
        cancel = new JButton( "Stop Adding" );

        create.addActionListener( this );
        cancel.addActionListener( this );

        JPanel addedPanel = new JPanel();
        JPanel namePanel = new JPanel();
        JPanel idPanel = new JPanel();
        JPanel doItPanel = new JPanel();

        addedPanel.add( numToAdd );
        addedPanel.add( numToAddL );

        namePanel.add( nameL );
        namePanel.add( nameF );

        idPanel.add( idL );
        idPanel.add( idF );

        doItPanel.add( create );
        doItPanel.add( cancel );

        BoxLayout studentsFrameLayout = new BoxLayout( addStudentsFrame
                .getContentPane(), javax.swing.BoxLayout.Y_AXIS );
        addStudentsFrame.setLayout( studentsFrameLayout );

        GradeBook.center( addStudentsFrame );

        addStudentsFrame.add( addedPanel );
        addStudentsFrame.add( namePanel );
        addStudentsFrame.add( idPanel );
        addStudentsFrame.add( doItPanel );

        addStudentsFrame.setVisible( true );
    }

    /**
     * Detects the user's button clicks
     * 
     * @pre GUI has been displayed, action listeners have been registered
     * @post Appropriate response has been carried out.
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( cancel ) )
        {
            addStudentsFrame.dispose();
            new ModifyStudentsScreen( theClass );
        }

        if( evt.getSource().equals( create ) )
        {
            Student temp;

            if( nameF.getText().equals( "" ) )
            {
                GradeBook.error( "You must enter a student name." );
                return;
            }
            int id;

            if( idF.getText().equals( "" ) )
            {
                temp = new Student( nameF.getText(), theClass );
            }
            else
            {

                try
                {
                    id = Integer.parseInt( idF.getText() );
                }
                catch( Exception e )
                {
                    GradeBook.error( "The student ID must be an integer." );
                    return;
                }

                if( id < 0 )
                {
                    GradeBook.error( "The ID must be a positive integer." );
                    return;
                }

                temp = new Student( nameF.getText(), Integer.parseInt( idF
                        .getText() ), theClass );

            }

            if( theClass.isDuplicateStudent( temp ) )
            {
                GradeBook.error( "A student with that name already exists." );
                return;
            }

            theClass.addStudent( temp );

            addStudentsFrame.dispose();

            if( numAdding > 1 )
                new AddStudentsScreen( numAdding - 1, theClass );
            else
                new ModifyStudentsScreen( theClass );
        }

    }

}
