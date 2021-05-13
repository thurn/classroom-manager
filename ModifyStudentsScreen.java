package ws.thurn.dossier;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * .
 */

/**
 * The ModifyStudentsScreen class generates the GUI and contains the methods for
 * interacting with the students in a specific class.
 * 
 * @author Derek Thurn
 */
public class ModifyStudentsScreen implements ActionListener
{

    Klass theClass;
    // The class whose students you are modifying.

    JFrame screenFrame;

    JButton addStudents;
    JButton deleteSelectedStudent;
    JButton modifySelectedStudent;
    JButton closeButton;

    JList dataList;

    String selected;

    // The currently selected student.

    /**
     * @param k the class whose students you are changing.
     */
    public ModifyStudentsScreen( Klass k )
    {
        theClass = k;
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
        screenFrame = new JFrame();
        screenFrame.setTitle( "Students" );
        screenFrame.setSize( 400, 400 );
        screenFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JPanel topButtons = new JPanel();
        JPanel listPanel = new JPanel();
        JPanel bottomButtons = new JPanel();

        addStudents = new JButton( "Add Students" );
        deleteSelectedStudent = new JButton( "Delete Selected Student" );
        modifySelectedStudent = new JButton( "Modify Selected Student" );
        closeButton = new JButton( "Close" );

        addStudents.addActionListener( this );
        deleteSelectedStudent.addActionListener( this );
        modifySelectedStudent.addActionListener( this );
        closeButton.addActionListener( this );

        topButtons.add( addStudents );
        topButtons.add( deleteSelectedStudent );
        topButtons.add( modifySelectedStudent );
        bottomButtons.add( closeButton );

        String[] data = theClass.getStudentsStringArray();

        dataList = new JList( data );

        ListListener listener = new ListListener();
        dataList.addListSelectionListener( listener );

        JScrollPane scrollPane;
        scrollPane = new JScrollPane( dataList );
        scrollPane.setPreferredSize( new Dimension( 200, 200 ) );

        listPanel.add( scrollPane );

        BoxLayout frameLayout = new BoxLayout( screenFrame.getContentPane(),
                javax.swing.BoxLayout.Y_AXIS );

        screenFrame.setLayout( frameLayout );

        screenFrame.add( topButtons );
        screenFrame.add( listPanel );
        screenFrame.add( bottomButtons );
        GradeBook.center( screenFrame );
        screenFrame.setVisible( true );

    }

    /**
     * Listens for button presses
     * 
     * @pre listeners have been added
     * @post the correct action is taken after an event.
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( closeButton ) )
        {
            screenFrame.dispose();
            new ClassesScreen();
        }
        if( evt.getSource().equals( addStudents ) )
        {
            int addingThisMany;
            try
            {
                String inputValue = JOptionPane
                        .showInputDialog( "How many students do you wish to add?" );
                addingThisMany = Integer.parseInt( inputValue );
            }
            catch( Exception e )
            {
                JOptionPane.showMessageDialog( null,
                        "Please enter an Integer.", "Error",
                        JOptionPane.ERROR_MESSAGE );
                return;
            }

            if( addingThisMany < 1 )
            {
                JOptionPane.showMessageDialog( null,
                        "You must add at least one student.", "Error",
                        JOptionPane.ERROR_MESSAGE );
                return;
            }
            else
            {
                screenFrame.dispose();
                new AddStudentsScreen( addingThisMany, theClass );
            }
        }
        if( evt.getSource().equals( deleteSelectedStudent ) )
        {
            if( selected == null )
            {
                GradeBook.error( "You must select a student to remove." );
                return;
            }

            theClass.deleteStudent( selected );
            screenFrame.dispose();
            new ModifyStudentsScreen( theClass );
        }

        if( evt.getSource().equals( modifySelectedStudent ) )
        {
            if( selected == null )
            {
                GradeBook.error( "You must select a student to modify." );
                return;
            }

            screenFrame.dispose();
            Student temp = new Student();

            temp = theClass.getStudentFromString( selected );

            if( temp == null )
                GradeBook.error( "The selected student couldn't be found." );

            new ModifySelectedStudentScreen( theClass, temp );

        }
    }

    /**
     * @author Derek Thurn Listens for changes in the JList
     */
    private class ListListener implements ListSelectionListener
    {
        public void valueChanged( ListSelectionEvent event )
        {
            if( !event.getValueIsAdjusting() )
            {
                Object selection = dataList.getSelectedValue();
                selected = selection.toString();
            }
        }
    }
}
