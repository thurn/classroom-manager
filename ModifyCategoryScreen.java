package ws.thurn.dossier;

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
 * The ModifyCategoryScreen class implements the GUI for saving modifications to
 * a category.
 * 
 * @author Derek Thurn
 */
public class ModifyCategoryScreen implements ActionListener
{
    Klass theClass;
    // The class which the category is in.

    Category theAssignment;
    // The category which is being modified.

    String originalName;
    // The original name of the category.

    JFrame aFrame;
    private JLabel nameL;
    private JTextField nameF;
    private JButton cancel;
    private JTextField weightF;
    private JButton save;
    private JLabel weightL;
    private JPanel buttonPanel;
    private JPanel weightPanel;
    private JPanel namePanel;

    /**
     * Creates a new screen for modifying categories
     * 
     * @param k the class which is under modification
     * @param a the category which is being changed.
     */
    public ModifyCategoryScreen( Klass k, Category a )
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
        aFrame.setTitle( "Modify Category" );
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
                nameL.setText( "Category Name:" );
            }
            {
                nameF = new JTextField();
                namePanel.add( nameF );
                nameF.setPreferredSize( new java.awt.Dimension( 180, 20 ) );
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
        weightF.setText( "" + theAssignment.getWeight() );

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
            aFrame.dispose();
            new AssignmentsScreen();
            return;
        }

        if( evt.getSource().equals( save ) )
        {

            if( nameF.getText().equals( "" ) )
            {
                GradeBook.error( "You must specify a name for the category" );
                return;
            }
            int w;

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
            Category temp = new Category( nameF.getText(), w );
            int q = -1;
            if( theClass.isDuplicateCategory( temp )
                    && !( nameF.getText().equals( originalName ) ) )
            {
                q = JOptionPane.showConfirmDialog( null,
                        "Do you want to overwrite the category "
                                + nameF.getText() + "?", "Confirm",
                        JOptionPane.YES_NO_OPTION );

                if( q == 0 )
                {
                    theClass.deleteCategory( nameF.getText() );
                }

                if( q == 1 )
                {
                    return;
                }
            }

            theAssignment.setName( nameF.getText() );

            theAssignment.setWeight( Integer.parseInt( weightF.getText() ) );

            aFrame.dispose();
            new AssignmentsScreen();

        }
    }

}
