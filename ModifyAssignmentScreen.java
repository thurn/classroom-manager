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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * .
 */

/**
 * The ModifyAssignmentsScreen class implements the GUI for saving modifications
 * to an assignment.
 * 
 * @author Derek Thurn
 */
public class ModifyAssignmentScreen implements ActionListener
{
    Klass theClass;
    // the class which the assignment being modified is in

    Assignment theAssignment;
    // the assignment being modified.

    String originalName;
    // the original name of the assignment being modified

    Category theCategory;
    // the category which the assignment is in.

    JFrame aFrame;
    private JPanel marksPanel;
    private JLabel nameL;
    private JTextField nameF;
    private JComboBox catCombo;
    private JLabel catLabel;
    private JPanel catPanel;
    private JButton cancel;
    private JTextField weightF;
    private JButton save;
    private JLabel weightL;
    private JTextField marksF;
    private JLabel marksL;
    private JPanel buttonPanel;
    private JPanel weightPanel;
    private JPanel namePanel;

    /**
     * @param k
     * @param a
     */
    public ModifyAssignmentScreen( Klass k, Assignment a )
    {
        theClass = k;
        theAssignment = a;
        originalName = theAssignment.getName();
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
        aFrame = new JFrame();
        aFrame.setTitle( "Modify Assignment" );
        aFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        aFrame.setSize( 400, 400 );
        GradeBook.center( aFrame );
        BoxLayout aFrameLayout = new BoxLayout( aFrame.getContentPane(),
                javax.swing.BoxLayout.Y_AXIS );
        aFrame.getContentPane().setLayout( aFrameLayout );
        aFrame.setVisible( true );
        {
            namePanel = new JPanel();
            aFrame.getContentPane().add( namePanel );
            {
                nameL = new JLabel();
                namePanel.add( nameL );
                nameL.setText( "Assignment Name:" );
            }
            {
                nameF = new JTextField();
                namePanel.add( nameF );
                nameF.setPreferredSize( new java.awt.Dimension( 180, 20 ) );
            }
        }
        {
            marksPanel = new JPanel();
            aFrame.getContentPane().add( marksPanel );
            {
                marksL = new JLabel();
                marksPanel.add( marksL );
                marksL.setText( "Total Marks:" );
            }
            {
                marksF = new JTextField();
                marksPanel.add( marksF );
                marksF.setPreferredSize( new java.awt.Dimension( 57, 20 ) );
            }
        }
        {
            weightPanel = new JPanel();
            aFrame.getContentPane().add( weightPanel );
            {
                weightL = new JLabel();
                weightPanel.add( weightL );
                weightL.setText( "Weight:" );
            }
            {
                weightF = new JTextField();
                weightPanel.add( weightF );
                weightF.setPreferredSize( new java.awt.Dimension( 50, 20 ) );
            }
        }
        {
            catPanel = new JPanel();
            aFrame.getContentPane().add( catPanel );
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
            }
        }
        {
            buttonPanel = new JPanel();
            aFrame.getContentPane().add( buttonPanel );
            {
                save = new JButton();
                buttonPanel.add( save );
                save.setText( "Save Changes" );
                save.addActionListener( this );
            }
            {
                cancel = new JButton();
                buttonPanel.add( cancel );
                cancel.setText( "Cancel" );
                cancel.addActionListener( this );
            }
        }

        nameF.setText( theAssignment.getName() );
        marksF.setText( "" + theAssignment.getTotalMarks() );
        weightF.setText( "" + theAssignment.getWeight() );
        try
        {
            catCombo.setSelectedItem( theAssignment.getCategory().toString() );
        }
        catch( Exception e )
        {
            catCombo.setSelectedIndex( -1 );
        }

    }

    /**
     * Listens for button presses
     * 
     * @pre the correct listeners have been added
     * @post the buttons function correctly.
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( cancel ) )
        {
            aFrame.dispose();
            new AssignmentsScreen();
            return;
        }

        if( evt.getSource().equals( catCombo ) )
        {
            if( catCombo.getSelectedItem() == null )
                return;

            String cat = catCombo.getSelectedItem().toString();
            theCategory = theClass.getCategoryFromString( cat );
        }

        if( evt.getSource().equals( save ) )
        {

            if( nameF.getText().equals( "" ) )
            {
                GradeBook.error( "You must specify a name for the assignment" );
                return;
            }
            if( marksF.getText().equals( "" ) )
            {
                GradeBook
                        .error( "You must specify the number of marks the assignment is out of." );
                return;
            }
            int tm;
            int w;
            try
            {
                tm = Integer.parseInt( marksF.getText() );
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

            if( weightF.getText().equals( "" ) )
            {
                w = 1;
            }
            else
            {
                try
                {
                    w = Integer.parseInt( weightF.getText() );
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
            Assignment temp = new Assignment( nameF.getText(), tm, w,
                    theCategory );
            int q = -1;
            if( theClass.isDuplicateAssignment( temp )
                    && !( nameF.getText().equals( originalName ) ) )
            {
                q = JOptionPane.showConfirmDialog( null,
                        "Do you want to overwrite the assignment "
                                + nameF.getText() + "?", "Confirm",
                        JOptionPane.YES_NO_OPTION );

                if( q == 0 )
                {
                    theClass.deleteAssignment( theClass
                            .getAssignmentFromString( nameF.getText() ) );
                }

                if( q == 1 )
                {
                    return;
                }
            }

            theAssignment.setName( nameF.getText() );
            theAssignment.setTotalMarks( Integer.parseInt( marksF.getText() ) );
            theAssignment.setWeight( Integer.parseInt( weightF.getText() ) );
            theAssignment.setCategory( theCategory );

            aFrame.dispose();
            new AssignmentsScreen();

        }
    }

}
