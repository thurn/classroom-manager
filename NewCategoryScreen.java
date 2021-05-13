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
 * The NewCategoryScreen class contains the GUI and methods for adding a new
 * category to the program.
 * 
 * @author Derek Thurn
 */
public class NewCategoryScreen implements ActionListener
{

    Klass theClass;
    // The class which you adding a category to.

    private JPanel weightPanel;
    private JLabel nameLabel;
    private JButton cancel;
    private JButton addButton;
    private JTextField weightField;
    private JLabel weightLabel;
    private JTextField nameField;
    private JPanel buttonsPanel;
    private JPanel namePanel;
    JFrame assignFrame;

    /**
     * Creates a new screen for adding a new category.
     * 
     * @param tc the class which you are adding a category to
     */
    public NewCategoryScreen( Klass tc )
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
        assignFrame.setTitle( "New Category" );
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
                nameLabel.setText( "Category Name:" );
            }
            {
                nameField = new JTextField();
                namePanel.add( nameField );
                nameField.setPreferredSize( new java.awt.Dimension( 167, 20 ) );
            }
        }
        {
            weightPanel = new JPanel();
            assignFrame.getContentPane().add( weightPanel );
            {
                weightLabel = new JLabel();
                weightPanel.add( weightLabel );
                weightLabel.setText( "Weight (Optional):" );
            }
            {
                weightField = new JTextField();
                weightPanel.add( weightField );
                weightField.setPreferredSize( new java.awt.Dimension( 48, 20 ) );
            }
        }
        {
            buttonsPanel = new JPanel();
            assignFrame.getContentPane().add( buttonsPanel );
            {
                addButton = new JButton();
                buttonsPanel.add( addButton );
                addButton.setText( "Add Category" );
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
        if( evt.getSource().equals( addButton ) )
        {
            if( nameField.getText().equals( "" ) )
            {
                GradeBook.error( "You must specify a name for the category" );
                return;
            }
            int w;

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
            Category temp = new Category( nameField.getText(), w );

            if( theClass.isDuplicateCategory( temp ) )
            {
                GradeBook.error( "A category with that name already exists." );
                return;
            }
            theClass.addCategory( temp );
            assignFrame.dispose();
            new AssignmentsScreen();

        }
    }

}
