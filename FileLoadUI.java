package ws.thurn.dossier;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The FileLoadUI class generates the GUI for letting the user select a class to
 * load from the save file into the program.
 * 
 * @author Derek Thurn
 */
public class FileLoadUI implements ActionListener
{

    private JFrame cFrame;
    private JPanel one;
    JButton cancel;
    JButton load;
    private JPanel two;
    private JList cList;

    private String selectedClass;
    // Which class the user has selected.

    String screen;

    // The previous screen which the user was on.

    /**
     * Creates a new File Load screen, storing which screen the user came from.
     * 
     * @param scr
     */
    public FileLoadUI( String scr )
    {
        display();
        screen = scr;
    }

    /**
     * Displays the GUI
     * 
     * @pre the required GUI objects have been declared
     * @post the GUI has been displayed
     */
    public void display()
    {
        cFrame = new JFrame();
        cFrame.setTitle( "Load Class" );
        cFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        {
            {
                two = new JPanel();
                cFrame.getContentPane().add( two, BorderLayout.NORTH );
                two.setPreferredSize( new java.awt.Dimension( 392, 340 ) );

            }
            one = new JPanel();
            cFrame.getContentPane().add( one, BorderLayout.CENTER );
            one.setPreferredSize( new java.awt.Dimension( 392, 32 ) );
            one.setSize( 392, 32 );
            {
                load = new JButton( "Load Class" );
                load.addActionListener( this );
                one.add( load );

            }
            {
                cancel = new JButton( "Cancel" );
                cancel.addActionListener( this );
                one.add( cancel );
            }
            {
                cList = new JList( GradeBook.getSavedClasses() );
                JScrollPane scrollPane2;
                scrollPane2 = new JScrollPane( cList );
                scrollPane2.setPreferredSize( new Dimension( 200, 200 ) );
                two.add( scrollPane2 );
                ListListener listener = new ListListener();
                cList.addListSelectionListener( listener );
            }
        }

        cFrame.setSize( 400, 400 );
        GradeBook.center( cFrame );
        cFrame.setVisible( true );

    }

    /**
     * Listens for button clicks.
     * 
     * @pre The correct listeners have been added.
     * @post none
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( cancel ) )
        {
            cFrame.dispose();
            if( screen.equals( "classes" ) )
                new ClassesScreen();
            if( screen.equals( "assignments" ) )
                new AssignmentsScreen();
            if( screen.equals( "grades" ) )
                new GradesScreen();
            if( screen.equals( "reports" ) )
                new ReportsScreen();
            if( screen.equals( "help" ) )
                new HelpScreen();
        }
        if( evt.getSource().equals( load ) )
        {
            if( selectedClass == null || selectedClass.equals( "" ) )
            {
                GradeBook.error( "You must select a class to load." );
                return;
            }

            Klass temp = new Klass( selectedClass );
            new FileLoader( GradeBook.getSaveFile(), temp );
            GradeBook.info( "Class loaded successfully." );
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
                if( event.getSource().equals( cList ) )
                {
                    Object selection = cList.getSelectedValue();
                    selectedClass = selection.toString();
                }
            }
        }
    }

}
