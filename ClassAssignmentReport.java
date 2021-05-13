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

/**
 * The ClassAssignmentReport class generates the GUI for allowing the user to
 * create a Class Assignment Report, and also contains code to generate that
 * report.
 * 
 * @author Derek Thurn
 */
public class ClassAssignmentReport implements ActionListener
{

    JFrame frm = new JFrame();
    private JPanel info;
    private JLabel infoLabel;
    private JButton cancel;
    private JButton view;
    private JButton print;
    private JButton export;
    private JPanel buttons;
    private JPanel assignmentPanel;
    private JPanel classPanel;
    private JComboBox classCombo;
    private JComboBox assignmentCombo;

    private boolean printIt = false;
    // If true, the report is sent to the printer.

    private boolean viewIt = false;
    // If true, the report is displayed in a view window.

    private Klass theClass;
    // The class which the report is being created for.

    private Assignment theAssignment;

    // The assignment which the report is being created for.

    /**
     * Creates the new object.
     */
    public ClassAssignmentReport()
    {
        displayScreen();
    }

    /**
     * Displays the GUI.
     * 
     * @pre Specific GUI objects have been declared.
     * @post the GUI has been created
     */
    public void displayScreen()
    {
        frm.setSize( 500, 500 );
        frm.setTitle( "Class Assignment Report" );
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
                        .setText( "Select the class and the assignment which you'd like to generate a progress report for." );
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
            assignmentPanel = new JPanel();
            frm.getContentPane().add( assignmentPanel );

            assignmentCombo = new JComboBox();
            assignmentCombo.setVisible( false );
            assignmentPanel.add( assignmentCombo );
        }
        {
            buttons = new JPanel();
            frm.getContentPane().add( buttons );
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
                export = new JButton();
                buttons.add( export );
                export.setText( "Export Only" );
                export.addActionListener( this );
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
     * Generates a Class Assignment Report for the specified class.
     * 
     * @param theClass
     * @pre the class has the required student, grade, and assignment data
     * @post Classes have been generated for each of the class' assignments
     */
    public void reportForClass( Klass theClass )
    {
        LinkedList assignments = theClass.getAssignments();
        ListIterator iterator = assignments.listIterator();

        while( iterator.hasNext() )
        {
            try
            {
                createReport( (Assignment) iterator.next() );
            }
            catch( IOException e )
            {
                GradeBook.error( "IO Exception Occured" );
            }
        }
    }

    /**
     * Prints a Class Assignment Report of the specified name.
     * 
     * @param reportName
     * @pre the report exists
     * @post the report has been sent to the printer
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
     * Views a Class Assignment Report of the specified name.
     * 
     * @param reportName
     * @pre the report exists
     * @post a viewbox for the report has been created.
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
     * Creates a report for the specified assignment.
     * 
     * @param theAssignment
     * @throws IOException
     * @pre the required class, student, assignment, and grade data exists
     * @post the requested report has been generated.
     */
    public void createReport( Assignment theAssignment ) throws IOException
    {
        PrintWriter out = new PrintWriter( new BufferedWriter( new FileWriter(
                "Reports/" + theAssignment.toString()
                        + " Assignment Report.html" ) ) );

        String nameOfReport = theAssignment.toString()
                + " Assignment Report.html";
        NumberFormat percentage = new DecimalFormat( "00.00" );

        String studentName;
        Klass tClass = GradeBook.getAssignmentClassFromString( theAssignment
                .toString() );

        out.println( "<html>" );
        out.println( "<head>" );
        out
                .println( "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">" );
        out.println( "<title>" );
        out.println( "Assignmenet Report for " + theAssignment.toString() );
        out.println( "</title>" );
        out.println( "</head>" );
        out.println( "<body>" );

        out
                .println( "<table><tr><th colspan=2><strong>Class Assignment Report</strong></th></tr><tr><td>Class Name:</td><td>"
                        + tClass.toString()
                        + "</td></tr><tr><td>Teacher Name:</td><td>"
                        + tClass.getTeacherName()
                        + "</td></tr><tr><td>School Name:</td><td>"
                        + tClass.getSchoolName()
                        + "</td></tr><tr><td>Assignment Name:</td><td>"
                        + theAssignment.toString() + "</td></tr></table><br>" );

        out.println( "<table border=1>" );

        out.println( "<tr>" );
        out.println( "<th><strong>Student:</strong></th>" );
        out.println( "<th><strong>Score:</strong></th>" );
        out.println( "<th><strong>Weight:</strong></th>" );
        out.println( "</tr>" );

        LinkedList students = tClass.getStudents();
        ListIterator sIte = students.listIterator();
        while( sIte.hasNext() )
        {
            studentName = sIte.next().toString();
            out.println( "<tr><td>"
                    + studentName
                    + "</td><td>"
                    + percentage.format( tClass.getPercentage( new Student(
                            studentName ), theAssignment ) ) + "%</td>" );
        }

        out.println( "<tr>" );
        out.println( "<td><strong>Class Average:</strong></td>" );
        out.println( "<td><strong>"
                + percentage.format( tClass
                        .getClassAssignmentAverage( theAssignment.toString() ) )
                + "%</strong></td>" );
        out.println( "</tr>" );

        out.println( "</table>" );
        out.println( "</body>" );
        out.println( "</html>" );
        out.close();

        if( printIt == false && viewIt == true )
        {
            viewReport( nameOfReport );
        }
        if( printIt == true && viewIt == false )
        {
            printReport( nameOfReport );
        }
    }

    /**
     * Initializes the report, generates prompts and error checking.
     * 
     * @pre none
     * @post the requested operations have been performed.
     */
    public void setupReport()
    {
        if( theClass == null )
        {
            GradeBook.error( "You must specify a class." );
            return;
        }

        if( theAssignment == null )
        {
            GradeBook.error( "You must specify an assignment" );
            return;
        }

        if( theAssignment.toString().equals( "All Assignments Are Selected" ) )
        {
            if( theClass.toString().equals( "All Classes Are Selected" ) )
            {
                LinkedList classes = GradeBook.getClasses();
                ListIterator iterator = classes.listIterator();

                while( iterator.hasNext() )
                {
                    reportForClass( (Klass) iterator.next() );
                }
                if( printIt == false && viewIt == false )
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "The report(s) have been saved in the Reports directory.",
                                    "Done", JOptionPane.INFORMATION_MESSAGE );

                return;
            }

            reportForClass( theClass );

            if( printIt == false && viewIt == false )
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
                createReport( theAssignment );

                if( printIt == false && viewIt == false )
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

    /**
     * Listens for button clicks nad list changes
     * 
     * @pre the listeners have been added.
     * @post none.
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( cancel ) )
        {
            frm.dispose();
            new ReportsScreen();
        }

        if( evt.getSource().equals( classCombo ) )
        {
            if( classCombo.getSelectedItem() == null )
                return;

            Klass as = GradeBook.getClassFromString( classCombo
                    .getSelectedItem().toString() );
            if( as != null && !as.classReportReady() )
            {
                GradeBook
                        .error( "The selected class lacks the grade information a report would required." );
                classCombo.setSelectedIndex( -1 );
                return;
            }

            theAssignment = null;

            String cl = classCombo.getSelectedItem().toString();

            if( cl.equals( "All Classes" ) )
            {
                theClass = new Klass( "All Classes Are Selected" );
            }
            else
            {
                theClass = GradeBook.getClassFromString( cl );
            }

            // Enable assignment combo box
            String assignments[];
            Vector assignmentsV;
            ComboBoxModel assignmentComboModel;

            if( theClass.toString().equals( "All Classes Are Selected" ) )
            { // if all classes are selected.
                LinkedList classes = GradeBook.getClasses();
                ListIterator lite = classes.listIterator();
                assignmentsV = new Vector( 5, 5 );
                assignmentsV.addElement( "All Assignments" );
                while( lite.hasNext() )
                {

                    Klass k = (Klass) lite.next();
                    LinkedList asList = k.getAssignments();
                    ListIterator ite = asList.listIterator();

                    while( ite.hasNext() )
                    {
                        assignmentsV.addElement( ite.next().toString() );
                    }
                }

                assignmentComboModel = new DefaultComboBoxModel( assignmentsV );
            }
            else
            {
                String temp[] = theClass.getAssignmentsStringArray();
                assignments = new String[ temp.length + 1 ];
                assignments[ 0 ] = "All Assignments";
                for( int i = 1; i <= temp.length; i++ )
                {
                    assignments[ i ] = temp[ i - 1 ];
                }
                assignmentComboModel = new DefaultComboBoxModel( assignments );
            }
            assignmentCombo.setVisible( true );
            assignmentCombo.setModel( assignmentComboModel );
            assignmentCombo.addActionListener( this );
            assignmentCombo.setSelectedIndex( -1 );
        }

        if( evt.getSource().equals( assignmentCombo ) )
        {
            if( assignmentCombo.getSelectedItem() == null )
                return;

            String st = assignmentCombo.getSelectedItem().toString();
            if( st.equals( "All Assignments" ) )
            {
                theAssignment = new Assignment( "All Assignments Are Selected" );
            }
            else
            {
                theAssignment = GradeBook.getAssignmentFromString( st );
            }
        }

        if( evt.getSource().equals( view ) )
        {
            viewIt = true;
            printIt = false;
            setupReport();
        }

        if( evt.getSource().equals( print ) )
        {
            printIt = true;
            viewIt = false;
            setupReport();
        }

        if( evt.getSource().equals( export ) )
        {
            printIt = false;
            viewIt = false;
            setupReport();
        }

    }

}