package ws.thurn.dossier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * .
 */

/**
 * The NewAssignmentScreen class contains the GUI and methods for adding a new
 * assignment to the program.
 * 
 * @author Derek Thurn
 */
public class NewAssignmentsScreen implements ActionListener
{

    Klass theClass;
    // The class you are adding assignments to.

    private JPanel weightPanel;
    private JLabel nameLabel;
    private JLabel totalMarksLabel;
    private JLabel catLabel;
    private JButton cancel;
    private JButton addButton;
    private JTextField weightField;
    private JLabel weightLabel;
    private JTextField totalMarksField;
    private JTextField nameField;
    private JPanel buttonsPanel;
    private JPanel totalMarksPanel;
    private JPanel namePanel;
    private JPanel catPanel;
    private JComboBox catCombo;
    JFrame assignFrame;

    Category theCategory;

    // The category that is selected.

    /**
     * Creates a screen for adding a new assignment.
     * 
     * @param tc the class of the new assignment.
     */
    public NewAssignmentsScreen( Klass tc )
    {
        theClass = tc;
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
        assignFrame = new JFrame();
        assignFrame.setTitle( "New Assignment" );
        assignFrame.setSize( 400, 400 );
        assignFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        BoxLayout assignFrameLayout = new BoxLayout( assignFrame
                .getContentPane(), javax.swing.BoxLayout.Y_AXIS );
        assignFrame.getContentPane().setLayout( assignFrameLayout );
        GradeBook.center( assignFrame );
        assignFrame.setVisible( true );
        {
            namePanel = new JPanel();
            assignFrame.getContentPane().add( namePanel );
            namePanel.setPreferredSize( new java.awt.Dimension( 392, 48 ) );
            {
                nameLabel = new JLabel();
                namePanel.add( nameLabel );
                nameLabel.setText( "Assignment Name:" );
            }
            {
                nameField = new JTextField();
                namePanel.add( nameField );
                nameField.setPreferredSize( new java.awt.Dimension( 167, 20 ) );
            }
        }
        {
            totalMarksPanel = new JPanel();
            assignFrame.getContentPane().add( totalMarksPanel );
            {
                totalMarksLabel = new JLabel();
                totalMarksPanel.add( totalMarksLabel );
                totalMarksLabel.setText( "Maximum Possible Marks:" );
            }
            {
                totalMarksField = new JTextField();
                totalMarksPanel.add( totalMarksField );
                totalMarksField.setPreferredSize( new java.awt.Dimension( 40,
                        20 ) );
            }
        }
        {
            weightPanel = new JPanel();
            assignFrame.getContentPane().add( weightPanel );
            {
                weightLabel = new JLabel();
                weightPanel.add( weightLabel );
                weightLabel.setText( "Assignment Weight (Optional):" );
            }
            {
                weightField = new JTextField();
                weightPanel.add( weightField );
                weightField.setPreferredSize( new java.awt.Dimension( 48, 20 ) );
            }
        }
        {
            catPanel = new JPanel();
            assignFrame.getContentPane().add( catPanel );
            {
                catLabel = new JLabel();
                catPanel.add( catLabel );
                catLabel.setText( "Category:" );
            }
            {
                ComboBoxModel catComboModel = new DefaultComboBoxModel(
                        theClass.getCategoriesStringArray() );
                catCombo = new JComboBox();
                catPanel.add( catCombo );
                catCombo.setModel( catComboModel );

                catCombo.addActionListener( this );

                catCombo.setSelectedIndex( -1 );
            }
        }
        {
            buttonsPanel = new JPanel();
            assignFrame.getContentPane().add( buttonsPanel );
            {
                addButton = new JButton();
                buttonsPanel.add( addButton );
                addButton.setText( "Add Assignment" );
                addButton.addActionListener( this );
            }
            {
                cancel = new JButton();
                buttonsPanel.add( cancel );
                cancel.setText( "Cancel" );
                cancel.addActionListener( this );
            }
        }
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
            assignFrame.dispose();
            new AssignmentsScreen();
        }

        if( evt.getSource().equals( catCombo ) )
        {
            if( catCombo.getSelectedItem() == null )
                return;

            String cat = catCombo.getSelectedItem().toString();
            theCategory = theClass.getCategoryFromString( cat );
        }

        if( evt.getSource().equals( addButton ) )
        {
            if( nameField.getText().equals( "" ) )
            {
                GradeBook.error( "You must specify a name for the assignment" );
                return;
            }
            if( totalMarksField.getText().equals( "" ) )
            {
                GradeBook
                        .error( "You must specify the number of marks the assignment is out of." );
                return;
            }
            int tm;
            int w;
            try
            {
                tm = Integer.parseInt( totalMarksField.getText() );
            }
            catch( Exception e )
            {
                GradeBook
                        .error( "You must specify an integer value for the total marks." );
                return;
            }
            if( tm < 1 )
            {
                GradeBook
                        .error( "The total marks value must be greater than one" );
                return;
            }

            if( weightField.getText().equals( "" ) )
            {
                w = 1;
            }
            else
            {
                try
                {
                    w = Integer.parseInt( weightField.getText() );
                }
                catch( Exception e )
                {
                    GradeBook
                            .error( "You must specify an integer value for the weight." );
                    return;
                }
                if( w < 1 )
                {
                    GradeBook
                            .error( "The weight value must be greater than one" );
                    return;
                }
            }

            Assignment temp;

            if( theCategory == null )
            {
                temp = new Assignment( nameField.getText(), tm, w );
            }
            else
            {
                temp = new Assignment( nameField.getText(), tm, w, theCategory );
            }

            if( theClass.isDuplicateAssignment( temp ) )
            {
                GradeBook
                        .error( "An assignment with that name already exists." );
                return;
            }
            theClass.addAssignment( temp );
            assignFrame.dispose();
            new AssignmentsScreen();

        }
    }

}
