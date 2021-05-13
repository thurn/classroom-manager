package ws.thurn.dossier;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * .
 */

/**
 * The AssignmentsScreen class is one of the main GUI screens that can be
 * reached from the Main Menu. It allows interaction with the functions of the
 * program that deal with categories and assignments.
 * 
 * @author Derek Thurn
 */
public class AssignmentsScreen extends Screen implements ActionListener
{

    JList aList;
    // The linked list containing the current assignments.

    JList cList;
    // The linked list containing the current categories

    Klass theClass;
    // The class to which the assignment is being added.

    String selectedAssignment;
    // The assignment the user has selected.

    String selectedCategory;
    // The category the user has selected.

    JButton newAssignment;
    JButton deleteAssignment;
    JButton modifyAssignment;

    JButton newCategory;
    JButton deleteCategory;
    JButton modifyCategory;

    /**
     * Creates a new AssignmentsScreen object, ensuring that a class has already
     * been created.
     */
    public AssignmentsScreen()
    {
        theClass = GradeBook.getCurrentClass();

        if( GradeBook.getNumClasses() < 1 )
        {
            GradeBook
                    .error( "You must create a class before adding assignments" );
            new MainMenu();
        }
        else
        {
            super.init();
            displayScreen();
        }
    }

    /**
     * Displays the GUI for the Assignments Screen.
     * 
     * @pre Specific GUI objects have been declared.
     * @post GUI has been displayed
     */
    public void displayScreen()
    {
        JPanel aListP = new JPanel();
        JPanel cListP = new JPanel();
        JPanel buttonsP = new JPanel();
        JPanel catButtons = new JPanel();

        frm.setTitle( "Assignments" );

        JLabel aLabel = new JLabel( "Assignments" );
        JLabel cLabel = new JLabel( "Categories" );

        newAssignment = new JButton( "New Assignment" );
        deleteAssignment = new JButton( "Delete Assignment" );
        modifyAssignment = new JButton( "Modify Assignment" );

        newCategory = new JButton( "New Category" );
        deleteCategory = new JButton( "Delete Category" );
        modifyCategory = new JButton( "Modify Category" );

        newAssignment.addActionListener( this );
        deleteAssignment.addActionListener( this );
        modifyAssignment.addActionListener( this );

        newCategory.addActionListener( this );
        deleteCategory.addActionListener( this );
        modifyCategory.addActionListener( this );

        if( theClass != null )
        {
            String assignments[] = theClass.getAssignmentsStringArray();
            String categories[] = theClass.getCategoriesStringArray();
            aList = new JList( assignments );
            cList = new JList( categories );
        }
        else
        {
            String assignments[] = new String[ 0 ];
            String categories[] = new String[ 0 ];
            aList = new JList( assignments );
            cList = new JList( categories );
        }

        ListListener listener = new ListListener();
        aList.addListSelectionListener( listener );
        cList.addListSelectionListener( listener );

        JScrollPane scrollPane;
        scrollPane = new JScrollPane( aList );
        scrollPane.setPreferredSize( new Dimension( 200, 200 ) );

        JScrollPane scrollPane2;
        scrollPane2 = new JScrollPane( cList );
        scrollPane2.setPreferredSize( new Dimension( 200, 200 ) );

        aListP.add( aLabel );
        aListP.add( scrollPane );
        cListP.add( cLabel );
        cListP.add( scrollPane2 );

        buttonsP.add( newAssignment );
        buttonsP.add( deleteAssignment );
        buttonsP.add( modifyAssignment );

        catButtons.add( newCategory );
        catButtons.add( deleteCategory );
        catButtons.add( modifyCategory );

        aList.setEnabled( true );
        cList.setEnabled( true );

        content.add( aListP );
        content.add( buttonsP );
        content.add( cListP );
        content.add( catButtons );

    }

    /**
     * Listens for button presses.
     * 
     * @pre listeners have been registered.
     * @post none
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( back ) )
        {
            frm.dispose();
            new MainMenu();
        }

        if( evt.getSource().equals( newAssignment ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "You must select a class." );
                return;
            }

            frm.dispose();
            new NewAssignmentsScreen( theClass );
        }

        if( evt.getSource().equals( deleteAssignment ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "You must select a class." );
                return;
            }

            if( selectedAssignment == null )
            {
                GradeBook.error( "You must select an assignment." );
                return;
            }
            theClass.deleteAssignment( theClass
                    .getAssignmentFromString( selectedAssignment ) );
            frm.dispose();
            new AssignmentsScreen();
        }

        if( evt.getSource().equals( modifyAssignment ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "You must select a class." );
                return;
            }

            if( selectedAssignment == null )
            {
                GradeBook.error( "You must select an assignment." );
                return;
            }
            Assignment temp;
            temp = theClass.getAssignmentFromString( selectedAssignment );

            if( temp == null )
            {
                GradeBook.error( "Could not find assignment." );
                return;
            }

            frm.dispose();
            new ModifyAssignmentScreen( theClass, temp );
        }

        if( evt.getSource().equals( newCategory ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "You must select a class." );
                return;
            }

            frm.dispose();
            new NewCategoryScreen( theClass );
        }

        if( evt.getSource().equals( deleteCategory ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "You must select a class." );
                return;
            }

            if( selectedCategory == null )
            {
                GradeBook.error( "You must select a category." );
                return;
            }
            theClass.deleteCategory( selectedCategory );
            frm.dispose();
            new AssignmentsScreen();
        }

        if( evt.getSource().equals( modifyCategory ) )
        {
            if( GradeBook.getCurrentClass() == null )
            {
                GradeBook.error( "You must select a class." );
                return;
            }

            if( selectedCategory == null )
            {
                GradeBook.error( "You must select a category." );
                return;
            }
            Category temp;
            temp = theClass.getCategoryFromString( selectedCategory );

            if( temp == null )
            {
                GradeBook.error( "Could not find category." );
                return;
            }

            frm.dispose();
            new ModifyCategoryScreen( theClass, temp );
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
                new AssignmentsScreen();
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
            new FileLoadUI( "assignments" );
        }

        if( evt.getSource().equals( knew ) )
        {
            frm.dispose();
            new NewClassScreen();
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
                if( event.getSource().equals( aList ) )
                {
                    Object selection = aList.getSelectedValue();
                    selectedAssignment = selection.toString();
                }
                if( event.getSource().equals( cList ) )
                {
                    Object selection = cList.getSelectedValue();
                    selectedCategory = selection.toString();
                }
            }
        }
    }

}
