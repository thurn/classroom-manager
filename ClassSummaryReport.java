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
 * The ClassSummaryReport class generates the GUI for user interactions to
 * produce a Class Summary Report, and also contains code to generate that
 * report.
 * 
 * @author Derek Thurn
 */
public class ClassSummaryReport implements ActionListener
{

    JFrame frm = new JFrame();
    private JPanel jPanel1;
    private JButton cancel;
    private JButton create;
    private JButton print;
    private JButton view;
    private JPanel jPanel3;
    private JPanel mid;
    private JLabel selectLabel;
    private JComboBox classCombo;

    private boolean printIt = false;
    // If true, the report is sent to the printer.

    private boolean viewIt = false;
    // If true, the report is displayed in a view window.

    private Klass theClass;

    // The class which the report is being created for.

    /**
     * Creates a new Class Summary Report window
     */
    public ClassSummaryReport()
    {
        displayScreen();
    }

    /**
     * Displays the GUI
     * 
     * @pre the required GUI objects have been declared
     * @post the GUI has been displayed.
     */
    public void displayScreen()
    {
        frm.setSize( 500, 500 );
        frm.setTitle( "Class Summary Report" );
        frm.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        BoxLayout frmLayout = new BoxLayout( frm.getContentPane(),
                javax.swing.BoxLayout.Y_AXIS );
        frm.getContentPane().setLayout( frmLayout );
        GradeBook.center( frm );
        frm.setVisible( true );
        {
            jPanel1 = new JPanel();
            frm.getContentPane().add( jPanel1 );
            {
                selectLabel = new JLabel();
                jPanel1.add( selectLabel );
                selectLabel
                        .setText( "Select the class which you would like to generate a Class Summary for." );
            }
        }
        {
            mid = new JPanel();
            frm.getContentPane().add( mid );
            String temp[] = GradeBook.getClassesStringArray();
            String classes[] = new String[ temp.length + 1 ];
            classes[ 0 ] = "All Classes";
            for( int i = 1; i <= temp.length; i++ )
            {
                classes[ i ] = temp[ i - 1 ];
            }
            ComboBoxModel classComboModel = new DefaultComboBoxModel( classes );
            classCombo = new JComboBox();
            mid.add( classCombo );
            classCombo.setModel( classComboModel );

            classCombo.addActionListener( this );
            classCombo.setSelectedIndex( -1 );
        }
        {
            jPanel3 = new JPanel();
            frm.getContentPane().add( jPanel3 );
            {
                create = new JButton();
                jPanel3.add( create );
                create.setText( "Export Report(s)" );
                create.addActionListener( this );
            }
            {
                view = new JButton();
                jPanel3.add( view );
                view.setText( "View Report" );
                view.addActionListener( this );
            }
            {
                print = new JButton();
                jPanel3.add( print );
                print.setText( "Print Report" );
                print.addActionListener( this );
            }
            {
                cancel = new JButton();
                jPanel3.add( cancel );
                cancel.setText( "Cancel" );
                cancel.addActionListener( this );
            }
        }
    }

    /**
     * Prints the Class Summary Report
     * 
     * @param reportName
     * @pre the report exists
     * @post the report has been sent to the printer.
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
     * Views the Class Summary Report
     * 
     * @param reportName
     * @pre the report exists
     * @post a report viewbox has been displayed.
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
     * Creates a Class Summary Report for a specific class.
     * 
     * @param theClass
     * @throws IOException
     * @pre the required class, student, assignment, and grand data has been
     *      entered
     * @post the specified report has been generated.
     */
    public void createReport( Klass theClass ) throws IOException
    {
        String className = theClass.toString();
        String studentName;

        PrintWriter out = new PrintWriter( new BufferedWriter( new FileWriter(
                "Reports/" + className + " Summary.html" ) ) );

        String reportN = className + " Summary.html";

        NumberFormat percentage = new DecimalFormat( "00.00" );

        out.println( "<html>" );
        out.println( "<head>" );
        out
                .println( "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">" );
        out.println( "<title>" );
        out.println( "Class Summary Report for " + className );
        out.println( "</title>" );
        out.println( "</head>" );
        out.println( "<body>" );

        out
                .println( "<table><tr><th colspan=2><strong>Class Summary Report</strong></th></tr><tr><td>Class Name:</td><td>"
                        + className
                        + "</td></tr><tr><td>Teacher Name:</td><td>"
                        + theClass.getTeacherName()
                        + "</td></tr><tr><td>School Name:</td><td>"
                        + theClass.getSchoolName() + "</td></tr></table><br>" );

        out.println( "<table border=1>" );

        out.println( "<tr>" );
        out.println( "<th><strong>Student Name</strong></th>" );
        out.println( "<th><strong>Average</strong></th>" );
        out.println( "</tr>" );

        LinkedList students = theClass.getStudents();
        ListIterator ite = students.listIterator();

        while( ite.hasNext() )
        {
            studentName = ite.next().toString();
            out.println( "<tr><td>" + studentName + "</td><td>"
                    + percentage.format( theClass.getAverage( studentName ) )
                    + "%</td>" );
        }

        out.println( "<tr>" );
        out.println( "<td><strong>Class Average:</strong></td>" );
        out.println( "<td><strong>"
                + percentage.format( theClass.getClassAverage() )
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
     * Listens for button clicks.
     * 
     * @pre The listeners have been added.
     * @post none
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( classCombo ) )
        {
            if( classCombo.getSelectedItem() == null )
                return;

            String cl = classCombo.getSelectedItem().toString();

            if( cl.equals( "All Classes" ) )
            {
                theClass = new Klass( "All Classes Are Selected" );
            }
            else
            {
                theClass = GradeBook.getClassFromString( cl );
            }

        }

        if( evt.getSource().equals( cancel ) )
        {
            frm.dispose();
            new ReportsScreen();
        }

        if( evt.getSource().equals( view ) )
        {
            viewIt = true;
            printIt = false;

            if( theClass == null )
            {
                GradeBook.error( "You must select a class!" );
                return;
            }

            if( theClass.toString().equals( "All Classes Are Selected" ) )
            {
                LinkedList classes;
                classes = GradeBook.getClasses();
                ListIterator li = classes.listIterator();

                while( li.hasNext() )
                {
                    try
                    {
                        createReport( (Klass) li.next() );
                    }
                    catch( IOException e )
                    {
                        GradeBook.error( "IO Exception Caused" );
                    }
                }

            }
            else
            {
                try
                {
                    createReport( theClass );
                }
                catch( IOException e )
                {
                    GradeBook.error( "IO Exception Caused" );
                }
            }
        }

        if( evt.getSource().equals( print ) )
        {
            printIt = true;
            viewIt = false;

            if( theClass == null )
            {
                GradeBook.error( "You must select a class!" );
                return;
            }

            if( theClass.toString().equals( "All Classes Are Selected" ) )
            {
                LinkedList classes;
                classes = GradeBook.getClasses();
                ListIterator li = classes.listIterator();

                while( li.hasNext() )
                {
                    try
                    {
                        createReport( (Klass) li.next() );
                    }
                    catch( IOException e )
                    {
                        GradeBook.error( "IO Exception Caused" );
                    }
                }

            }
            else
            {
                try
                {
                    createReport( theClass );
                }
                catch( IOException e )
                {
                    GradeBook.error( "IO Exception Caused" );
                }
            }
        }

        if( evt.getSource().equals( create ) )
        {
            printIt = false;
            viewIt = false;

            if( theClass == null )
            {
                GradeBook.error( "You must select a class!" );
                return;
            }

            if( theClass.toString().equals( "All Classes Are Selected" ) )
            {
                LinkedList classes;
                classes = GradeBook.getClasses();
                ListIterator li = classes.listIterator();

                while( li.hasNext() )
                {
                    try
                    {
                        createReport( (Klass) li.next() );
                    }
                    catch( IOException e )
                    {
                        GradeBook.error( "IO Exception Caused" );
                    }
                }

            }
            else
            {
                try
                {
                    createReport( theClass );
                }
                catch( IOException e )
                {
                    GradeBook.error( "IO Exception Caused" );
                }
            }

            JOptionPane.showMessageDialog( null,
                    "The report(s) have been saved in the Reports directory.",
                    "Done", JOptionPane.INFORMATION_MESSAGE );

        }
    }

}
