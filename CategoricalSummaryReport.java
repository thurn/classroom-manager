package ws.thurn.dossier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/*
 * .
 */
/**
 * The CateogricalSummaryReport class generates the GUI allowing the user to
 * specify options for a Categorical Summary Report, and also contains the code
 * which generates that report.
 * 
 * @author Derek Thurn
 */
public class CategoricalSummaryReport implements ActionListener
{

    JFrame frm = new JFrame();
    private JPanel info;
    private JLabel infoLabel;
    private JButton cancel;
    private JButton create;
    private JButton print;
    private JButton view;
    private JPanel buttons;
    private JPanel studentPanel;
    private JPanel classPanel;
    private JComboBox classCombo;
    private JComboBox studentCombo;

    private boolean printIt = false;
    // If true, the report is sent to the printer.

    private boolean viewIt = false;
    // If true, the report is displayed in a view window.

    private Klass theClass;
    // The class which the report is being created for.

    private Student theStudent;

    // The student which the report is being created for.

    /**
     * Creates a new object to produce the report.
     */
    public CategoricalSummaryReport()
    {
        displayScreen();
    }

    /**
     * Displays the GUI.
     * 
     * @pre Specific GUI objects have been declared.
     * @post GUI has been displayed
     */
    public void displayScreen()
    {
        frm.setSize( 500, 500 );
        frm.setTitle( "Categorical Summary Report" );
        frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        GradeBook.center( frm );
        BoxLayout frmLayout = new BoxLayout( frm.getContentPane(),
                javax.swing.BoxLayout.Y_AXIS );
        frm.getContentPane().setLayout( frmLayout );
        frm.setVisible( true );
        {
            info = new JPanel();
            frm.getContentPane().add( info );
            {
                infoLabel = new JLabel();
                info.add( infoLabel );
                infoLabel
                        .setText( "Select the class and the student which you'd like to generate a categorical summary for." );
            }
        }
        {
            classPanel = new JPanel();
            frm.getContentPane().add( classPanel );
            String temp[] = GradeBook.getClassesStringArray();
            String classes[] = new String[ temp.length + 1 ];
            classes[ 0 ] = "All Classes";
            for( int i = 1; i <= temp.length; i++ )
            {
                classes[ i ] = temp[ i - 1 ];
            }
            ComboBoxModel classComboModel = new DefaultComboBoxModel( classes );
            classCombo = new JComboBox();
            classPanel.add( classCombo );
            classCombo.setModel( classComboModel );

            classCombo.addActionListener( this );
            classCombo.setSelectedIndex( -1 );
        }
        {
            studentPanel = new JPanel();
            frm.getContentPane().add( studentPanel );

            studentCombo = new JComboBox();
            studentCombo.setVisible( false );
            studentPanel.add( studentCombo );
        }
        {
            buttons = new JPanel();
            frm.getContentPane().add( buttons );
            {
                create = new JButton();
                buttons.add( create );
                create.setText( "Export Reports" );
                create.addActionListener( this );
            }
            {
                view = new JButton();
                buttons.add( view );
                view.setText( "View Report" );
                view.addActionListener( this );
            }
            {
                print = new JButton();
                buttons.add( print );
                print.setText( "Print Report" );
                print.addActionListener( this );
            }
            {
                cancel = new JButton();
                buttons.add( cancel );
                cancel.setText( "Cancel" );
                cancel.addActionListener( this );
            }
        }
    }

    /**
     * Generates a report for the specified class.
     * 
     * @param theClass
     * @pre the class has students, assignments, and grade data
     * @post reports have been created according to specification for each
     *       student
     */
    public void reportForClass( Klass theClass )
    {
        LinkedList students = theClass.getStudents();
        ListIterator iterator = students.listIterator();

        while( iterator.hasNext() )
        {
            try
            {
                createReport( (Student) iterator.next() );
            }
            catch( IOException e )
            {
                GradeBook.error( "IO Exception Occured" );
            }
        }
    }

    /**
     * Prints out the report of the specified name.
     * 
     * @param reportName
     * @pre the report exists
     * @post the report has been reported
     */
    public void printReport( String reportName )
    {
        try
        {
            JEditorPane panel = new JEditorPane();
            File location = new File( "Reports/" + reportName );
            panel.setPage( location.toURL() );
            JDialog temp = new JDialog();
            temp.add( new JScrollPane( panel ) );
            temp.setBounds( -800, -600, 800, 600 );
            temp.setVisible( true );
            Printer.printComponent( panel );
        }
        catch( java.io.IOException a )
        {
            a.printStackTrace();
        }
    }

    /**
     * Displays the specified report.
     * 
     * @param reportName
     * @pre the report exists
     * @post a view-window with the report has been created.
     */
    public void viewReport( String reportName )
    {
        try
        {
            JEditorPane panel = new JEditorPane();
            File location = new File( "Reports/" + reportName );
            panel.setPage( location.toURL() );
            panel.setEditable( false );
            JDialog temp = new JDialog();
            temp.add( new JScrollPane( panel ) );
            temp.setBounds( 0, 0, 800, 600 );
            temp.setVisible( true );
        }
        catch( java.io.IOException a )
        {
        }
    }

    /**
     * Creates a categorical summary report for the specified student.
     * 
     * @param theStudent
     * @throws IOException
     * @pre A student in a class with grade and assignment data has been
     *      selected
     * @post A report for the specified student is created.
     */
    public void createReport( Student theStudent ) throws IOException
    {
        PrintWriter out = new PrintWriter( new BufferedWriter( new FileWriter(
                "Reports/" + theStudent.toString()
                        + " Categorical Summary.html" ) ) );

        String reportN = theStudent.toString() + " Categorical Summary.html";
        NumberFormat percentage = new DecimalFormat( "00.00" );

        String categoryName;
        Klass tClass = GradeBook.getStudentClassFromString( theStudent
                .toString() );

        out.println( "<html>" );
        out.println( "<head>" );
        out
                .println( "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">" );
        out.println( "<title>" );
        out.println( "Categorical Summary for " + theStudent.toString() );
        out.println( "</title>" );
        out.println( "</head>" );
        out.println( "<body>" );

        out
                .println( "<table><tr><th colspan=2><strong>Student Categorical Summary Report</strong></th></tr><tr><td>Class Name:</td><td>"
                        + tClass.toString()
                        + "</td></tr><tr><td>Teacher Name:</td><td>"
                        + tClass.getTeacherName()
                        + "</td></tr><tr><td>School Name:</td><td>"
                        + tClass.getSchoolName()
                        + "</td></tr><tr><td>Student Name:</td><td>"
                        + theStudent.toString() + "</td></tr></table><br>" );

        out.println( "<table border=1>" );

        out.println( "<tr>" );
        out.println( "<th><strong>Category:</strong></th>" );
        out.println( "<th><strong>Grade:</strong></th>" );
        out.println( "<th><strong>Weight:</strong></th>" );
        out.println( "</tr>" );

        LinkedList categories = tClass.getCategories();
        ListIterator aIte = categories.listIterator();

        while( aIte.hasNext() )
        {
            categoryName = aIte.next().toString();
            Category theCategory = (Category) categories.find( categoryName );
            out.println( "<tr><td>"
                    + categoryName
                    + "</td><td>"
                    + percentage.format( tClass.getStudentCategoryAverage(
                            theStudent.toString(), categoryName ) )
                    + "%</td><td>" + theCategory.getWeight() + "</td>" );
        }

        out.println( "<tr>" );
        out.println( "<td><strong>Student Average:</strong></td>" );
        out.println( "<td><strong>"
                + percentage
                        .format( tClass.getAverage( theStudent.toString() ) )
                + "%</strong></td>" );
        out.println( "</tr>" );

        out.println( "</table>" );
        out.println( "</body>" );
        out.println( "</html>" );
        out.close();

        if( printIt )
            printReport( reportN );

        if( viewIt )
            viewReport( reportN );

    }

    /**
     * Listens for button clicks and list selections.
     * 
     * @pre Correct listeners have been added.
     * @post none
     */
    public void actionPerformed( ActionEvent evt )
    {

        if( evt.getSource().equals( classCombo ) )
        {
            if( classCombo.getSelectedItem() == null )
                return;

            theStudent = null;

            String cl = classCombo.getSelectedItem().toString();

            if( cl.equals( "All Classes" ) )
            {
                theClass = new Klass( "All Classes Are Selected" );
            }
            else
            {
                theClass = GradeBook.getClassFromString( cl );
                if( !theClass.classReportReady() )
                {
                    GradeBook
                            .error( "The selected class does not have sufficient information for a report. Add students, assignments, and grade data using the other screens." );
                    theClass = null;
                    classCombo.setSelectedIndex( -1 );
                    return;
                }
            }

            // Enable student combo box
            String students[];
            Vector studentsV;
            ComboBoxModel studentComboModel;

            if( theClass.toString().equals( "All Classes Are Selected" ) )
            { // if all classes are selected.
                LinkedList classes = GradeBook.getClasses();
                ListIterator lite = classes.listIterator();
                studentsV = new Vector( 5, 5 );
                studentsV.addElement( "All Students" );
                while( lite.hasNext() )
                {

                    Klass k = (Klass) lite.next();
                    LinkedList stList = k.getStudents();
                    ListIterator ite = stList.listIterator();

                    while( ite.hasNext() )
                    {
                        studentsV.addElement( ite.next().toString() );
                    }
                }

                studentComboModel = new DefaultComboBoxModel( studentsV );
            }
            else
            {
                String temp[] = theClass.getStudentsStringArray();
                students = new String[ temp.length + 1 ];
                students[ 0 ] = "All Students";
                for( int i = 1; i <= temp.length; i++ )
                {
                    students[ i ] = temp[ i - 1 ];
                }
                studentComboModel = new DefaultComboBoxModel( students );
            }
            studentCombo.setVisible( true );
            studentCombo.setModel( studentComboModel );
            studentCombo.addActionListener( this );
            studentCombo.setSelectedIndex( -1 );
        }

        if( evt.getSource().equals( studentCombo ) )
        {
            if( studentCombo.getSelectedItem() == null )
                return;

            String st = studentCombo.getSelectedItem().toString();
            if( st.equals( "All Students" ) )
            {
                theStudent = new Student( "All Students Are Selected" );
            }
            else
            {
                theStudent = GradeBook.getStudentFromString( st );
            }
        }

        if( evt.getSource().equals( cancel ) )
        {
            frm.dispose();
            new ReportsScreen();

        }

        if( evt.getSource().equals( print ) )
        {
            printIt = true;
            viewIt = false;

            if( theClass == null )
            {
                GradeBook.error( "You must specify a class." );
                return;
            }

            if( theStudent == null )
            {
                GradeBook.error( "You must specify a student" );
                return;
            }

            if( theStudent.toString().equals( "All Students Are Selected" ) )
            {
                if( theClass.toString().equals( "All Classes Are Selected" ) )
                {
                    LinkedList classes = GradeBook.getClasses();
                    ListIterator iterator = classes.listIterator();

                    while( iterator.hasNext() )
                    {
                        reportForClass( (Klass) iterator.next() );
                    }
                    // JOptionPane.showMessageDialog(null, "The report(s) have
                    // been saved in the Reports directory.", "Done",
                    // JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                reportForClass( theClass );
                // JOptionPane.showMessageDialog(null, "The report(s) have been
                // saved in the Reports directory.", "Done",
                // JOptionPane.INFORMATION_MESSAGE);

                return;
            }
            else
            {
                try
                {
                    createReport( theStudent );
                    // JOptionPane.showMessageDialog(null, "The report(s) have
                    // been saved in the Reports directory.", "Done",
                    // JOptionPane.INFORMATION_MESSAGE);
                }
                catch( IOException e )
                {
                    GradeBook.error( "IO Exception Occured" );
                }
            }

        }

        if( evt.getSource().equals( view ) )
        {
            viewIt = true;
            printIt = false;

            if( theClass == null )
            {
                GradeBook.error( "You must specify a class." );
                return;
            }

            if( theStudent == null )
            {
                GradeBook.error( "You must specify a student" );
                return;
            }

            if( theStudent.toString().equals( "All Students Are Selected" ) )
            {
                if( theClass.toString().equals( "All Classes Are Selected" ) )
                {
                    LinkedList classes = GradeBook.getClasses();
                    ListIterator iterator = classes.listIterator();

                    while( iterator.hasNext() )
                    {
                        reportForClass( (Klass) iterator.next() );
                    }
                    // JOptionPane.showMessageDialog(null, "The report(s) have
                    // been saved in the Reports directory.", "Done",
                    // JOptionPane.INFORMATION_MESSAGE);

                    return;
                }

                reportForClass( theClass );
                // JOptionPane.showMessageDialog(null, "The report(s) have been
                // saved in the Reports directory.", "Done",
                // JOptionPane.INFORMATION_MESSAGE);

                return;
            }
            else
            {
                try
                {
                    createReport( theStudent );
                    // JOptionPane.showMessageDialog(null, "The report(s) have
                    // been saved in the Reports directory.", "Done",
                    // JOptionPane.INFORMATION_MESSAGE);
                }
                catch( IOException e )
                {
                    GradeBook.error( "IO Exception Occured" );
                }
            }

        }
        if( evt.getSource().equals( create ) )
        {
            viewIt = false;
            printIt = false;

            if( theClass == null || theClass.toString().equals( "" ) )
            {
                GradeBook.error( "You must specify a class." );
                return;
            }

            if( theStudent == null )
            {
                GradeBook.error( "You must specify a student" );
                return;
            }

            if( theStudent.toString().equals( "All Students Are Selected" ) )
            {
                if( theClass.toString().equals( "All Classes Are Selected" ) )
                {
                    LinkedList classes = GradeBook.getClasses();
                    ListIterator iterator = classes.listIterator();

                    while( iterator.hasNext() )
                    {
                        reportForClass( (Klass) iterator.next() );
                    }
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "The report(s) have been saved in the Reports directory.",
                                    "Done", JOptionPane.INFORMATION_MESSAGE );

                    return;
                }

                reportForClass( theClass );
                JOptionPane
                        .showMessageDialog(
                                null,
                                "The report(s) have been saved in the Reports directory.",
                                "Done", JOptionPane.INFORMATION_MESSAGE );

                return;
            }
            else
            {
                try
                {
                    createReport( theStudent );
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "The report(s) have been saved in the Reports directory.",
                                    "Done", JOptionPane.INFORMATION_MESSAGE );
                }
                catch( IOException e )
                {
                    GradeBook.error( "IO Exception Occured" );
                }
            }

        }

    }

}
