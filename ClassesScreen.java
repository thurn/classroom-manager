package ws.thurn.dossier;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * The ClassesScreen class generates the GUI for one of the primary classes
 * which can be accessed from the Main Menu. It handles all of the interactions
 * with classes and students.
 * 
 * @author Derek Thurn
 */
public class ClassesScreen extends Screen implements ActionListener
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
    JPanel mid;
    JPanel low;
    JPanel doIt;
    JPanel classInfo;
    JFrame newClass = new JFrame();

    Object selected;

    JButton newClss;
    JButton deleteClass;
    JButton modifyStudents;
    JButton modifyClass;
    JButton selectClass;

    JList cList;
    DefaultListModel model;

    /**
     * Creates a new Classes Screen.
     */
    public ClassesScreen()
    {
        super.init();
        GUI();
        initScreen();
    }

    /**
     * Displays the GUI
     * 
     * @pre specific GUI objects have been declared
     * @post the GUI has been displayed.
     */
    public void GUI()
    {
        frm.setTitle( "Classes" );

        BoxLayout buttonLayout = new BoxLayout( content,
                javax.swing.BoxLayout.Y_AXIS );

        content.setLayout( buttonLayout );

        newClss = new JButton( "Create New Class" );
        newClss.addActionListener( this );
        content.add( newClss );

        deleteClass = new JButton( "Delete Selected Class" );
        deleteClass.addActionListener( this );
        content.add( deleteClass );

        modifyClass = new JButton( "Modify Selected Class" );
        modifyClass.addActionListener( this );
        content.add( modifyClass );

        modifyStudents = new JButton( "Modify Students" );
        modifyStudents.addActionListener( this );
        content.add( modifyStudents );

        selectClass = new JButton( "Select This Class" );
        selectClass.addActionListener( this );
        content.add( selectClass );

    }

    /**
     * Displays more GUI
     * 
     * @pre additional specific GUI objects are declared
     * @post the GUI is generated
     */
    public void initScreen()
    {
        if( GradeBook.getNumClasses() == 0 )
        {
            frm.dispose();
            new NewClassScreen();
            return;
        }

        mid = new JPanel();
        content.add( mid );
        JPanel tempMidRep = new JPanel();
        mid.add( tempMidRep );

        tempMidRep.setLayout( new BorderLayout() );
        mid.setLayout( null );
        tempMidRep.setBounds( 0, 0, 200, 200 );
        mid.setPreferredSize( new Dimension( 200, 200 ) );
        // low = new JPanel();
        // frm.add(low);
        // low.setPreferredSize(new Dimension(1000, 1000));
        // low.setSize(new Dimension(1000, 1000));

        String[] entries = GradeBook.getClassesStringArray();

        cList = new JList( entries );
        JScrollPane scrollPane2;
        scrollPane2 = new JScrollPane();
        scrollPane2.setViewportView( cList );
        tempMidRep.add( scrollPane2, BorderLayout.CENTER );
        MyListListener listener = new MyListListener();
        cList.addListSelectionListener( listener );
        cList.setFixedCellHeight( 15 );

    }

    /**
     * @author Derek Thurn Listens for changes in the JList
     */
    private class MyListListener implements ListSelectionListener
    {

        public void valueChanged( ListSelectionEvent event )
        {

            if( !event.getValueIsAdjusting() )
            {
                Object selection = cList.getSelectedValue();
                selected = selection;

            }

        }

    }

    /**
     * Listens for button clicks.
     * 
     * @pre The listeners have been added.
     * @post none
     */
    public void actionPerformed( ActionEvent evt )
    {

        if( evt.getSource().equals( back ) )
        {
            frm.dispose();
            new MainMenu();
        }

        if( evt.getSource().equals( newClss ) )
        {
            frm.dispose();
            new NewClassScreen();
        }

        if( evt.getSource().equals( deleteClass ) )
        {
            if( selected == null )
            {
                JOptionPane.showMessageDialog( null,
                        "You must select a class to delete.", "Error",
                        JOptionPane.ERROR_MESSAGE );
                return;
            }

            GradeBook.deleteClass( (String) ( selected ) );
            selected = null;
            frm.dispose();
            if( GradeBook.getNumClasses() > 0 )
                new ClassesScreen();

            if( GradeBook.getNumClasses() == 0 )
                new NewClassScreen();
        }

        if( evt.getSource().equals( modifyStudents ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook
                        .error( "You must select a class to modify its students." );
                return;
            }

            frm.dispose();
            Klass temp = new Klass();
            temp = GradeBook.getCurrentClass();
            new ModifyStudentsScreen( temp );
        }

        if( evt.getSource().equals( modifyClass ) )
        {
            if( selected == null )
            {
                GradeBook
                        .error( "You must select a class to modify its information." );
                return;
            }
            frm.dispose();
            Klass temp = new Klass();
            temp = GradeBook.getClassFromString( selected.toString() );
            new ModifyClassScreen( temp );
        }

        if( evt.getSource().equals( selectClass ) )
        {
            if( selected == null )
            {
                GradeBook
                        .error( "You must select a class to mark it as selected for the program." );
                return;
            }

            Klass temp = new Klass();
            temp = GradeBook.getClassFromString( selected.toString() );

            GradeBook.setCurrentClass( temp );
            JOptionPane
                    .showMessageDialog(
                            null,
                            "The class "
                                    + temp
                                    + " has been selected. You can now modify grades and assignments for it.",
                            "Done", JOptionPane.INFORMATION_MESSAGE );
            currentCombo.setSelectedIndex( GradeBook.getCurrentClassLocation() );
        }

        if( evt.getSource().equals( currentCombo ) )
        {
            if( currentCombo.getSelectedItem() == null )
                return;

            Klass temp2 = GradeBook.getCurrentClass();
            String cl = currentCombo.getSelectedItem().toString();
            Klass temp;
            temp = GradeBook.getClassFromString( cl );
            GradeBook.setCurrentClass( temp );
            if( !temp.equals( temp2 ) )
            {
                frm.dispose();
                new ClassesScreen();
            }
        }
        if( evt.getSource().equals( save ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "You must select a class to save." );
                return;
            }

            File f = GradeBook.getSaveFile();
            try
            {
                RandomAccessFile io = new RandomAccessFile( f, "rw" );
                long classAt = GradeBook.fileFindClass( GradeBook
                        .getCurrentClass() );
                if( classAt == -1 )
                {
                    new FileSaver( io, GradeBook.getCurrentClass() );
                    GradeBook.info( "The class "
                            + GradeBook.getCurrentClass().toString()
                            + " has been sucessfully saved." );
                }
                else
                {
                    new FileSaver( io, GradeBook.getCurrentClass(), classAt );
                    GradeBook.info( "The class "
                            + GradeBook.getCurrentClass().toString()
                            + " has been sucessfully saved." );
                }
            }
            catch( IOException e )
            {
                GradeBook.error( "IO Exception in CLASS SCREEN!" );

            }
        }
        if( evt.getSource().equals( load ) )
        {
            if( GradeBook.getSaveFile().length() < 100 )
            {
                GradeBook.error( "No classed saved." );
                return;
            }

            frm.dispose();
            new FileLoadUI( "classes" );
        }

        if( evt.getSource().equals( knew ) )
        {
            frm.dispose();
            new NewClassScreen();
        }

    }

}