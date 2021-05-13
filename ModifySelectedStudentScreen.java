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
 * The ModifySelectedStudentScreen class allows for the modification of one
 * specific student saved in the program.
 * 
 * @author Derek Thurn
 */
public class ModifySelectedStudentScreen implements ActionListener
{

    Klass theClass;
    // The class whose students you are modifying.

    Student theStudent;
    // The studen being modified.

    JFrame modifyStudentsFrame;
    JButton cancel;
    JButton create;
    JTextField nameF;
    JTextField idF;

    /**
     * Creates a new screen for modifying a specific student.
     * 
     * @param tc the class being modified
     * @param s the student to modify
     */
    public ModifySelectedStudentScreen( Klass tc, Student s )
    {
        theClass = tc;
        theStudent = s;
        displayScreen();
    }

    /**
     * Displays the GUI for the screen
     * 
     * @pre the correct objects have been declared
     * @post the GUI is displayed.
     */
    public void displayScreen()
    {
        modifyStudentsFrame = new JFrame();
        modifyStudentsFrame.setTitle( "Modify Student" );
        modifyStudentsFrame.setSize( 300, 300 );
        modifyStudentsFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JLabel nameL = new JLabel( "Student Name:" );
        JLabel idL = new JLabel( "Student ID (Optional):" );

        nameF = new JTextField( 15 );
        idF = new JTextField( 15 );

        nameF.setText( theStudent.getName() );
        idF.setText( "" + theStudent.getID() );

        create = new JButton( "Save Changes" );
        cancel = new JButton( "Cancel" );

        create.addActionListener( this );
        cancel.addActionListener( this );

        JPanel namePanel = new JPanel();
        JPanel idPanel = new JPanel();
        JPanel doItPanel = new JPanel();

        namePanel.add( nameL );
        namePanel.add( nameF );

        idPanel.add( idL );
        idPanel.add( idF );

        doItPanel.add( create );
        doItPanel.add( cancel );

        BoxLayout studentsFrameLayout = new BoxLayout( modifyStudentsFrame
                .getContentPane(), javax.swing.BoxLayout.Y_AXIS );
        modifyStudentsFrame.setLayout( studentsFrameLayout );

        GradeBook.center( modifyStudentsFrame );

        modifyStudentsFrame.add( namePanel );
        modifyStudentsFrame.add( idPanel );
        modifyStudentsFrame.add( doItPanel );

        modifyStudentsFrame.setVisible( true );
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
            modifyStudentsFrame.dispose();
            new ModifyStudentsScreen( theClass );
        }

        if( evt.getSource().equals( create ) )
        {

            if( nameF.getText().equals( "" ) )
            {
                GradeBook.error( "You must enter a student name." );
                return;
            }
            int id;

            if( idF.getText().equals( "" ) )
            {
                theStudent.setName( nameF.getText() );
                return;
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

                if( theClass
                        .isDuplicateStudent( new Student( nameF.getText() ) ) )
                {
                    GradeBook
                            .error( "A student with that name already exists." );
                    return;
                }
                theStudent.setName( nameF.getText() );
                theStudent.setID( Integer.parseInt( idF.getText() ) );
            }

            modifyStudentsFrame.dispose();
            new ModifyStudentsScreen( theClass );
        }

    }

}
