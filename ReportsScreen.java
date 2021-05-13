package ws.thurn.dossier;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/*
 * .
 */

/**
 * The ReportsScreen class generates the GUI for the screen of the program that
 * allows the user to pick Reports to generate.
 * 
 * @author Derek Thurn
 */
public class ReportsScreen extends Screen
{

    private JPanel right;
    private JPanel middle2;
    private JButton create;
    private ButtonGroup reportGroup;
    private JRadioButton classAssignment;
    private JRadioButton catSum;
    private JPanel left;
    private JPanel bottom;
    private JPanel middle;
    private JPanel top;
    private JRadioButton studentProgress;
    private JRadioButton classSummary;
    private JPanel center;
    private JLabel infoBox;
    private String selected = "no";

    /**
     * Creates a new screen for dealing with reports.
     * 
     * @pre sufficient data for a report has been entered
     * @post a report has been produced.
     */
    public ReportsScreen()
    {
        if( GradeBook.getNumClasses() < 1 )
        {
            GradeBook
                    .error( "You must create a class before creating reports." );
            new MainMenu();
        }
        else
        {
            if( GradeBook.programReportReady() )
            {
                super.init();
                GUI();
            }
            else
            {
                GradeBook
                        .error( "You must enter grade data before creating reports." );
                new MainMenu();
            }
        }
    }

    /**
     * Displays the GUI for the screen
     * 
     * @pre the correct objects have been declared
     * @post the GUI is displayed.
     */
    public void GUI()
    {
        {
            reportGroup = new ButtonGroup();
        }
        BoxLayout contentLayout = new BoxLayout( content,
                javax.swing.BoxLayout.X_AXIS );
        content.setLayout( contentLayout );
        frm.getContentPane().add( content, BorderLayout.CENTER );
        frm.setTitle( "Reports" );
        content.setPreferredSize( new java.awt.Dimension( 792, 573 ) );
        {
            left = new JPanel();
            content.add( left );
        }
        {
            center = new JPanel();
            content.add( center );
            BoxLayout centerLayout = new BoxLayout( center,
                    javax.swing.BoxLayout.Y_AXIS );
            center.setLayout( centerLayout );
            {
                top = new JPanel();
                center.add( top );
                {
                    infoBox = new JLabel();
                    top.add( infoBox );
                    infoBox
                            .setText( "Weights will be applied to reports. All grades will be shown in percentage form." );
                }
            }
            {
                middle2 = new JPanel();
                center.add( middle2 );
                {
                    catSum = new JRadioButton();
                    middle2.add( catSum );
                    catSum.setText( "Student Categorical Summary Report" );
                    reportGroup.add( catSum );
                    catSum.addActionListener( this );
                }
                {
                    classAssignment = new JRadioButton();
                    middle2.add( classAssignment );
                    classAssignment.setText( "Class Assignment Report" );
                    reportGroup.add( classAssignment );
                    classAssignment.addActionListener( this );
                }
            }
            {
                middle = new JPanel();
                FlowLayout middleLayout = new FlowLayout();
                middle.setLayout( middleLayout );
                center.add( middle );
                middle.setPreferredSize( new java.awt.Dimension( 523, 91 ) );
                {
                    classSummary = new JRadioButton();
                    middle.add( classSummary );
                    classSummary.setText( "Class Summary Report" );
                    reportGroup.add( classSummary );
                    classSummary.addActionListener( this );
                }
                {
                    studentProgress = new JRadioButton();
                    middle.add( studentProgress );
                    studentProgress.setText( "Student Progress Report" );
                    reportGroup.add( studentProgress );
                    studentProgress.addActionListener( this );
                }
            }
            {
                bottom = new JPanel();
                center.add( bottom );
                {
                    create = new JButton();
                    bottom.add( create );
                    create.setText( "Create Report" );
                    create.setFont( new java.awt.Font( "Tahoma", 1, 16 ) );
                    create.addActionListener( this );
                }
            }
        }
        {
            right = new JPanel();
            content.add( right );
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
        if( evt.getSource().equals( back ) )
        {
            frm.dispose();
            new MainMenu();
        }
        if( evt.getSource().equals( classSummary ) )
            selected = "classSummary";

        if( evt.getSource().equals( studentProgress ) )
            selected = "studentProgress";

        if( evt.getSource().equals( catSum ) )
            selected = "catSum";

        if( evt.getSource().equals( classAssignment ) )
            selected = "classAssignment";

        if( evt.getSource().equals( create ) )
        {
            if( selected.equals( "no" ) )
            {
                GradeBook.error( "You must select a report type to create." );
            }

            if( selected.equals( "classSummary" ) )
            {
                frm.dispose();
                new ClassSummaryReport();
            }

            if( selected.equals( "studentProgress" ) )
            {
                frm.dispose();
                new StudentProgressReport();
            }

            if( selected.equals( "catSum" ) )
            {
                frm.dispose();
                new CategoricalSummaryReport();
            }

            if( selected.equals( "classAssignment" ) )
            {
                frm.dispose();
                new ClassAssignmentReport();
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
                new FileLoadUI( "reports" );
            }

            if( evt.getSource().equals( knew ) )
            {
                frm.dispose();
                new NewClassScreen();
            }

        }

        if( evt.getSource().equals( currentCombo ) )
        {
            if( currentCombo.getSelectedItem() == null )
                return;

            String cl = currentCombo.getSelectedItem().toString();
            Klass temp;
            temp = GradeBook.getClassFromString( cl );
            GradeBook.setCurrentClass( temp );

        }
    }

}
