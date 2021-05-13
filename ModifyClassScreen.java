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
 * The ModifyClassScreen class implements the GUI for saving modifications to a
 * class.
 * 
 * @author Derek Thurn
 */
public class ModifyClassScreen implements ActionListener
{

    Klass theClass;
    // the class being modified.

    JFrame modifyClassFrame;

    JTextField classField;
    JTextField teacherField;
    JTextField schoolField;

    JButton create;
    JButton cancel;

    /**
     * Creates a new screen for making changes to a class.
     * 
     * @param tc the class to be modified.
     */
    public ModifyClassScreen( Klass tc )
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
        modifyClassFrame = new JFrame();
        modifyClassFrame.setTitle( "Modify Class" );
        modifyClassFrame.setSize( 300, 300 );
        modifyClassFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        JLabel className = new JLabel( "Class Name:" );
        JLabel teacherName = new JLabel( "Teacher Name(Optional):" );
        JLabel schoolName = new JLabel( "School Name(Optional):" );

        classField = new JTextField( 15 );
        teacherField = new JTextField( 15 );
        schoolField = new JTextField( 15 );

        classField.setText( theClass.getClassName() );
        teacherField.setText( theClass.getTeacherName() );
        schoolField.setText( theClass.getSchoolName() );

        create = new JButton( "Save Changes" );
        cancel = new JButton( "Cancel" );

        create.addActionListener( this );
        cancel.addActionListener( this );

        JPanel namePanel = new JPanel();
        JPanel teacherPanel = new JPanel();
        JPanel schoolPanel = new JPanel();
        JPanel doItPanel = new JPanel();

        namePanel.add( className );
        namePanel.add( classField );

        teacherPanel.add( teacherName );
        teacherPanel.add( teacherField );

        schoolPanel.add( schoolName );
        schoolPanel.add( schoolField );

        doItPanel.add( create );
        doItPanel.add( cancel );

        BoxLayout studentsFrameLayout = new BoxLayout( modifyClassFrame
                .getContentPane(), javax.swing.BoxLayout.Y_AXIS );
        modifyClassFrame.setLayout( studentsFrameLayout );

        GradeBook.center( modifyClassFrame );

        modifyClassFrame.add( namePanel );
        modifyClassFrame.add( teacherPanel );
        modifyClassFrame.add( schoolPanel );
        modifyClassFrame.add( doItPanel );

        modifyClassFrame.setVisible( true );
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
            modifyClassFrame.dispose();
            new ClassesScreen();
        }

        if( evt.getSource().equals( create ) )
        {
            String cn = classField.getText();
            String tn = teacherField.getText();
            String sn = schoolField.getText();

            if( cn.equals( "" ) )
            {
                GradeBook.error( "You must specify a class name." );
                return;
            }
            theClass.setClassName( cn );
            theClass.setTeacherName( tn );
            theClass.setSchoolName( sn );

            modifyClassFrame.dispose();
            new ClassesScreen();
        }

    }

}
