package ws.thurn.dossier;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/*
 * .
 */

/**
 * The GradesScreen class generates the GUI for entering student grades into the
 * program, and contains the methods for doing so.
 * 
 * @author Derek Thurn
 */
public class GradesScreen extends Screen
{

    private boolean DEBUG = false;
    // If true, print out useful debug information

    Klass theClass = GradeBook.getCurrentClass();

    // The current class.

    /**
     * Creates a new grades screen.
     */
    public GradesScreen()
    {
        super.init();
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
        frm.setTitle( "Grades Screen" );

        JTable table = new JTable( new MyTableModel() );
        table.setPreferredScrollableViewportSize( new Dimension( 500, 70 ) );

        JScrollPane scrollPane = new JScrollPane( table );

        content.add( scrollPane );
    }

    /**
     * Listens for button clicks and dropdown menu changes
     * 
     * @pre the correct listeners have been added
     * @post the correct response is enacted.
     */
    public void actionPerformed( ActionEvent evt )
    {
        if( evt.getSource().equals( back ) )
        {
            frm.dispose();
            new MainMenu();
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
            new FileLoadUI( "grades" );
        }

        if( evt.getSource().equals( knew ) )
        {
            frm.dispose();
            new NewClassScreen();
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
                new GradesScreen();
            }

        }
    }

    /**
     * @author Derek Thurn Implements the table for the Grades screen.
     */
    class MyTableModel extends AbstractTableModel
    {

        private String columnNames[] = getColumnNames();

        // An array containing the table's column names.

        /**
         * Creates a new table model.
         */
        public MyTableModel()
        {
            theClass.initGradeData();
        }

        public Object[][] data = theClass.getGradeData();

        // the data to be placed in the table.

        /**
         * Create a string array witht he correct column names.
         * 
         * @pre the class with at least one student exists
         * @post a string array with column names has been created.
         * @return Returns the columnNames.
         */
        public String[] getColumnNames()
        {
            String temp[] = theClass.getAssignmentsStringArray();
            int len = temp.length;
            len = len + 1;
            String cN[] = new String[ len ];
            cN[ 0 ] = "Student Name";
            for( int i = 1; i < cN.length; i++ )
            {
                cN[ i ] = temp[ i - 1 ];
            }
            return cN;
        }

        /**
         * Get the number of columns in the program.
         * 
         * @pre the columnNames array has been created
         * @post the number of columns has been returned.
         * @return the number of columns.
         */
        public int getColumnCount()
        {
            return columnNames.length;
        }

        /**
         * Get the number of rows in the program
         * 
         * @pre the data array has been created
         * @post the number of rows has been returned.
         * @return the number of rows.
         */
        public int getRowCount()
        {
            return data.length;
        }

        /**
         * Returns the name of a specific column
         * 
         * @pre the specified column exists
         * @post the correct name is returned.
         * @param col the column number to ge the name of.
         * @return the name of the column.
         */
        public String getColumnName( int col )
        {
            return columnNames[ col ];
        }

        /**
         * Returns the grade value at a specific location
         * 
         * @pre the data array has been declared
         * @post the correct object representing the data is returned
         * @param row the row number to get the value at
         * @param col the column number to get the value at.
         * @return the object representing the value.
         */
        public Object getValueAt( int row, int col )
        {
            return data[ row ][ col ];
        }

        /**
         * Returns the entire data two dimensional object array
         * 
         * @pre the data array has been declared
         * @post the data has been returned.
         * @return Returns the data.
         */
        public String[][] getData()
        {
            return (String[][]) data;
        }

        /**
         * Checks whether the specified cell is an editable one.
         * 
         * @pre the row and column are within the table bounds
         * @post the state of editability is returned
         * @param row the row number of the cell to check
         * @param col the column number of the cell to check
         * @return true of the cell can be edited, false otherwise.
         */
        public boolean isCellEditable( int row, int col )
        {
            // Note that the data/cell address is constant,
            // no matter where the cell appears onscreen.
            if( col == 0 )
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        /**
         * Sets a value at specified coordinates.
         * 
         * @pre the data array has been created, the values are within the
         *      boundaries
         * @pos the object has been stored in the right cell.
         * @param value the Object to be placed at these coordinates
         * @param row the row number of the desired cell
         * @param col the column number of the desired cell
         */
        public void setValueAt( Object value, int row, int col )
        {
            // long due to large debugging information.
            if( DEBUG )
            {
                System.out.println( "Setting value at " + row + "," + col
                        + " to " + value + " (an instance of "
                        + value.getClass() + ")" );
            }

            int intValue;
            try
            {
                if( value.toString().equals( "" ) || value == null )
                    intValue = 0;
                else
                {
                    intValue = Integer.parseInt( value.toString() );
                }
            }
            catch( NumberFormatException e )
            {
                GradeBook.error( "You must enter an integer." );
                return;
            }
            catch( NullPointerException e )
            {
                intValue = 0;
            }

            if( intValue < 0 )
            {
                GradeBook.error( "All scores must be greater than zero." );
                return;
            }

            Assignment as;
            as = theClass.findAssignment( new Assignment( getColumnName( col ),
                    0 ) );

            if( intValue > as.getTotalMarks() )
            {
                JOptionPane.showMessageDialog( null, "This grade exceeds "
                        + as.getTotalMarks()
                        + ", the number of marks the assignment is out of.",
                        "Warning", JOptionPane.INFORMATION_MESSAGE );
            }

            if( value.toString().equals( "" ) )
            {
                return;
            }

            if( Integer.parseInt( value.toString() ) != 0 )
                data[ row ][ col ] = value;
            else
                data[ row ][ col ] = 0;

            fireTableCellUpdated( row, col );

            if( DEBUG )
            {
                System.out.println( "New value of data:" );
                printDebugData();
            }

            theClass.setGradeData( row, col, intValue );

        }

        /**
         * Prints out useful data for debugging the program
         * 
         * @pre the data object has been created
         * @post the data is printed out.
         */
        private void printDebugData()
        {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for( int i = 0; i < numRows; i++ )
            {
                System.out.print( "    row " + i + ":" );
                for( int j = 0; j < numCols; j++ )
                {
                    System.out.print( "  " + data[ i ][ j ] );
                }
                System.out.println();
            }
            System.out.println( "--------------------------" );
        }
    }

}
