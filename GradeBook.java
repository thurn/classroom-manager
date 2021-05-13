package ws.thurn.dossier;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The GradeBook class launches the program and contains methods which must be
 * program-wide by necessity, such as those dealing with multiple classes.
 * 
 * @author Derek Thurn
 */
public class GradeBook
{
    private static LinkedList classes = new LinkedList();
    // A linked list to contain the classes in the program.

    public static final int PROGRAM_LIMIT = 50;
    // the program limit constant controls a number of parts of the program,
    // including
    // the max # of assignmments and students, the max # of grades, and the
    // length at which
    // a string will be chopped off by the file saver.

    private static Klass currentClass = new Klass( "Computer Science" );
    // The program's current class to be modified.

    private static File saveFile = new File( "gradebook.dat" );

    // The program's save file.

    /**
     * Intatiates the program's Main Menu, may add test data to the program.
     * 
     * @param args
     */
    public static void main( String[] args )
    {
        boolean DEBUG = true;
        // If true, add test data.
        if( DEBUG )
        {
            addClass( currentClass );

            currentClass
                    .addStudent( new Student( "Thurn, Derek", currentClass ) );
            currentClass.addStudent( new Student( "Kassam, Noorez",
                    currentClass ) );
            currentClass
                    .addStudent( new Student( "Kelln, Graeme", currentClass ) );
            currentClass.addStudent( new Student( "De Yaegher, Michael",
                    currentClass ) );
            currentClass.addStudent( new Student( "Derksen-Hatt, Justin",
                    currentClass ) );

            Category on = new Category( "Odd Numbers", 1 );
            Category en = new Category( "Even Numbers", 10 );

            currentClass.addCategory( on );
            currentClass.addCategory( en );

            currentClass.addAssignment( new Assignment( "Assignment 1", 5, 1,
                    on ) );
            currentClass.addAssignment( new Assignment( "Assignment 2", 5, 1,
                    en ) );
            currentClass.addAssignment( new Assignment( "Assignment 3", 5, 1,
                    on ) );
            currentClass.addAssignment( new Assignment( "Assignment 4", 5, 1,
                    en ) );
            currentClass.addAssignment( new Assignment( "Assignment 5", 5, 1,
                    on ) );

            currentClass.setGradeData( 0, 1, 4 );
            currentClass.setGradeData( 0, 2, 3 );
            currentClass.setGradeData( 0, 3, 4 );
            currentClass.setGradeData( 0, 4, 5 );
            currentClass.setGradeData( 0, 5, 4 );

            currentClass.setGradeData( 1, 1, 3 );
            currentClass.setGradeData( 1, 2, 2 );
            currentClass.setGradeData( 1, 3, 1 );
            currentClass.setGradeData( 1, 4, 2 );
            currentClass.setGradeData( 1, 5, 3 );

            currentClass.setGradeData( 2, 1, 4 );
            currentClass.setGradeData( 2, 2, 5 );
            currentClass.setGradeData( 2, 3, 5 );
            currentClass.setGradeData( 2, 4, 5 );
            currentClass.setGradeData( 2, 5, 4 );

            currentClass.setGradeData( 3, 1, 3 );
            currentClass.setGradeData( 3, 2, 4 );
            currentClass.setGradeData( 3, 3, 5 );
            currentClass.setGradeData( 3, 4, 5 );
            currentClass.setGradeData( 3, 5, 5 );

            currentClass.setGradeData( 4, 1, 1 );
            currentClass.setGradeData( 4, 2, 2 );
            currentClass.setGradeData( 4, 3, 1 );
            currentClass.setGradeData( 4, 4, 3 );
            currentClass.setGradeData( 4, 5, 4 );

            Klass temp1 = new Klass( "Math", "Ancelin", "SWC" );
            Klass temp2 = new Klass( "Social", "Oldfield", "SWC" );

            addClass( temp1 );
            addClass( temp2 );

            temp1.addStudent( new Student( "Thurn, Derek", temp1 ) );
            temp1.addStudent( new Student( "Kassam, Noorez", temp1 ) );

            on = new Category( "Odd Numbers", 1 );
            en = new Category( "Even Numbers", 10 );

            temp1.addCategory( on );
            temp1.addCategory( en );

            temp1.addAssignment( new Assignment( "Assignment 1", 5, 1, on ) );
            temp1.addAssignment( new Assignment( "Assignment 2", 5, 1, en ) );

            temp1.setGradeData( 0, 1, 4 );
            temp1.setGradeData( 0, 2, 3 );
            temp1.setGradeData( 0, 3, 4 );
            temp1.setGradeData( 0, 4, 5 );
            temp1.setGradeData( 0, 5, 4 );

            temp1.setGradeData( 1, 1, 3 );
            temp1.setGradeData( 1, 2, 2 );
            temp1.setGradeData( 1, 3, 1 );
            temp1.setGradeData( 1, 4, 2 );
            temp1.setGradeData( 1, 5, 3 );

            temp2.addStudent( new Student( "Thurn, Derek", temp2 ) );
            temp2.addStudent( new Student( "Kassam, Noorez", temp2 ) );
            temp2.addStudent( new Student( "Kelln, Graeme", temp2 ) );
            temp2.addStudent( new Student( "De Yaegher, Michael", temp2 ) );

            on = new Category( "Odd Numbers", 1 );
            en = new Category( "Even Numbers", 10 );

            temp2.addCategory( on );
            temp2.addCategory( en );

            temp2.addAssignment( new Assignment( "Assignment 1", 5, 1, on ) );
            temp2.addAssignment( new Assignment( "Assignment 2", 5, 1, en ) );

            temp2.setGradeData( 0, 1, 4 );
            temp2.setGradeData( 0, 2, 3 );
            temp2.setGradeData( 0, 3, 4 );
            temp2.setGradeData( 0, 4, 5 );
            temp2.setGradeData( 0, 5, 4 );

            temp2.setGradeData( 1, 1, 3 );
            temp2.setGradeData( 1, 2, 2 );
            temp2.setGradeData( 1, 3, 1 );
            temp2.setGradeData( 1, 4, 2 );
            temp2.setGradeData( 1, 5, 3 );

            temp2.setGradeData( 2, 1, 4 );
            temp2.setGradeData( 2, 2, 5 );
            temp2.setGradeData( 2, 3, 5 );
            temp2.setGradeData( 2, 4, 5 );
            temp2.setGradeData( 2, 5, 4 );

            temp2.setGradeData( 3, 1, 3 );
            temp2.setGradeData( 3, 2, 4 );
            temp2.setGradeData( 3, 3, 5 );
            temp2.setGradeData( 3, 4, 5 );
            temp2.setGradeData( 3, 5, 5 );

            temp2.setGradeData( 4, 1, 1 );
            temp2.setGradeData( 4, 2, 2 );
            temp2.setGradeData( 4, 3, 1 );
            temp2.setGradeData( 4, 4, 3 );
            temp2.setGradeData( 4, 5, 4 );

            File f = GradeBook.getSaveFile();
            try
            {
                RandomAccessFile io = new RandomAccessFile( f, "rw" );
                new FileSaver( io, currentClass );
                new FileSaver( io, temp1 );
                new FileSaver( io, temp2 );
                currentClass.setGradeData( 0, 1, 2 );
                new FileSaver( io, currentClass );

                new FileLoader( f, currentClass );
                int result = currentClass.getGradeValue( 0, 1 );

                if( result == 2 )
                {
                    /*
                     * GradeBook.info("Success!");
                     */
                }

            }
            catch( IOException e )
            {
                GradeBook.error( "Test failed." );
            }

        }

        new MainMenu();

    }

    /**
     * Centers a JFrame.
     * 
     * @pre the frame exists
     * @post the frame is centered.
     * @param frame
     */
    public static void center( JFrame frame )
    {
        /*
         * Andre (2002). How to center a frame from Scratchbook for Java
         * Development Kit Retrieved February 2, 2006, from
         * http://home.tiscali.nl/~bmc88/java/sbook/0110.html
         */

        frame.setVisible( false );
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = frame.getSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        size.height = size.height / 2;
        size.width = size.width / 2;
        int y = screenSize.height - size.height;
        int x = screenSize.width - size.width;
        frame.setLocation( x, y );
        frame.setVisible( true );
    }

    /**
     * Sets the current class.
     * 
     * @pre none
     * @post the class is set.
     * @param currentClass The currentClass to set.
     */
    public static void setCurrentClass( Klass cc )
    {
        currentClass = cc;
    }

    /**
     * Gets the program's save file.
     * 
     * @pre the file exists
     * @post it has been returned.
     * @return the program's save file.
     */
    public static File getSaveFile()
    {
        return saveFile;
    }

    /**
     * Deletes a specified saved class.
     * 
     * @pre The class to be deleted exists on file
     * @post The class has been deleted.
     * @param k the class to delete
     * @throws IOException
     */
    public static void deleteSavedClass( Klass k ) throws IOException
    {
        long location = fileFindClass( k );
        RandomAccessFile io = new RandomAccessFile( saveFile, "rw" );

        io.seek( location );
        io.writeChars( "This Slot Is Currently Empty" );

        while( io.getFilePointer() < location + 30900 )
        {
            io.writeChar( ' ' );
        }

    }

    /**
     * Returns a vector object with the program's saved classes.
     * 
     * @pre none
     * @post the saved classes have been returned.
     * @return
     */
    public static Vector getSavedClasses()
    {
        Vector<String> classV = new Vector( 5, 1 );

        try
        {
            RandomAccessFile io = new RandomAccessFile( saveFile, "rw" );

            if( io.length() == 0 )
            {
                classV.addElement( "No Classes Have Been Saved" );
                return classV;
            }

            int location = 0;

            while( location < io.length() )
            {
                classV.addElement( readString( location ) );
                location = location + 30900;
            }

            return classV;

        }

        catch( IOException E )
        {
            GradeBook.error( "IO Exception" );
            return classV;
        }
    }

    /**
     * Find the pointer location of a specified class.
     * 
     * @pre the class exists in the save file
     * @post the location has been returned.
     * @param k the class to find.
     * @return
     * @throws IOException
     */
    public static long fileFindClass( Klass k ) throws IOException
    {
        String name = k.toString();
        RandomAccessFile io = new RandomAccessFile( saveFile, "rw" );

        if( io.length() == 0 )
            return -1;

        if( readString( 0 ).equals( name ) )
            return 0;

        long location = 0;

        while( location < io.length() )
        {
            io.seek( location );
            String theString = readString( location );
            if( theString.equals( name ) )
            {
                return location;
            }
            location = location + 30900;
        }

        return -1;
    }

    /**
     * Return the current class
     * 
     * @pre none
     * @post none
     * @return Returns the currentClass.
     */
    public static Klass getCurrentClass()
    {
        return currentClass;
    }

    /**
     * Gets the linked list with the program's classes.
     * 
     * @pre the list exists
     * @post none
     * @return Returns the classes linked list.
     */
    public static LinkedList getClasses()
    {
        return classes;
    }

    /**
     * Adds a class to the program.
     * 
     * @pre none
     * @post the class has been added.
     * @param k the class to add.
     */
    public static void addClass( Klass k )
    {
        classes.addLast( k );
        k.initGradeData();
    }

    /**
     * Checks to see if a specific class has the same name as another.
     * 
     * @pre some classes have been added.
     * @post none
     * @param k the class to check.
     * @return true or false
     */
    public static boolean isDuplicate( Klass k )
    {
        String dupe = k.toString();
        ListIterator ite = classes.listIterator();
        while( ite.hasNext() )
        {
            if( ite.hasNext() == false )
                break;

            String c = ite.next().toString();
            if( c == null )
            {
                return true;
            }

            if( c.equals( dupe ) )
                return true;
        }
        return false;
    }

    /**
     * Get the number of classes in the program
     * 
     * @pre none
     * @post none
     * @return Returns the numClasses.
     */
    public static int getNumClasses()
    {
        return classes.getLength();
    }

    /**
     * Get the location (in the linked list) of the current class.
     * 
     * @pre the current class has been specified
     * @post none
     * @return an integer representing the location
     */
    public static int getCurrentClassLocation()
    {
        if( currentClass == null )
            return -1;

        ListIterator ite = classes.listIterator();
        int i = 0;
        while( ite.hasNext() )
        {
            String a = currentClass.toString();
            String b = ite.next().toString();
            if( a.equals( b ) )
                return i;

            i++;
        }

        return -1;
    }

    /**
     * Returns a string array with the program's classes.
     * 
     * @pre a classes linked list exists
     * @post a string array with the classes has been returned.
     * @return the classes string array.
     */
    public static String[] getClassesStringArray()
    {
        String temp[] = new String[ 100 ];
        ListIterator ite = classes.listIterator();

        for( int i = 0; i < 100; i++ )
        {
            temp[ i ] = "EMPTY STRING HERE";
        }

        for( int i = 0; ite.hasNext(); i++ )
        {
            temp[ i ] = ite.next().toString();
        }
        int stopHere = 0;

        for( int i = 0; i < 100; i++ )
        {
            if( temp[ i ].equals( "EMPTY STRING HERE" ) )
            {
                stopHere = i;
                break;
            }
        }
        if( stopHere == 0 )
        {

            String temp2[] = new String[ 1 ];
            temp2[ 0 ] = "";
            return temp2;

        }

        int index = 0;
        String ret[] = new String[ stopHere ];
        while( index != stopHere )
        {
            ret[ index ] = temp[ index ];
            index = index + 1;
        }

        return ret;

    }

    /**
     * Deletes a specific class.
     * 
     * @pre The class to be deleted is in the program
     * @post the class has been deleted
     * @param k the class to delete.
     */
    public static void deleteClass( String k )
    {
        ListIterator lite = classes.listIterator();
        lite = classes.listIterator();

        lite = classes.listIterator();
        while( lite.hasNext() )
        {
            String compare = lite.next().toString();

            if( compare.equals( k ) )
            {
                lite.remove();
            }
        }
    }

    /**
     * Returns a class based on its name.
     * 
     * @pre a class by that name already exists
     * @post the klass object has been returned.
     * @param k The name of the class to find.
     * @return The klass object.
     */
    public static Klass getClassFromString( String k )
    {
        return (Klass) ( classes.find( k ) );
    }

    /**
     * Returns a student based on its name.
     * 
     * @pre a student by that name already exists
     * @post the student object has been returned.
     * @param k The name of the student to find.
     * @return The student object.
     */
    public static Student getStudentFromString( String studentName )
    {
        ListIterator ite = classes.listIterator();

        while( ite.hasNext() )
        {
            Klass k = (Klass) ite.next();
            Student st = k.findStudent( new Student( studentName ) );

            if( st != null )
                return st;
        }

        return null;
    }

    /**
     * Returns an assignment based on its name.
     * 
     * @pre an assignment by that name already exists
     * @post the assignment object has been returned.
     * @param k The name of the assignment to find.
     * @return The assignment object.
     */
    public static Assignment getAssignmentFromString( String assignmentName )
    {
        ListIterator ite = classes.listIterator();

        while( ite.hasNext() )
        {
            Klass k = (Klass) ite.next();
            Assignment st = k.findAssignment( new Assignment( assignmentName ) );

            if( st != null )
                return st;
        }

        return null;
    }

    /**
     * Returns a category based on its name.
     * 
     * @pre a category by that name already exists
     * @post the category object has been returned.
     * @param k The name of the category to find.
     * @return The category object.
     */
    public static Klass getStudentClassFromString( String studentName )
    {
        ListIterator ite = classes.listIterator();

        while( ite.hasNext() )
        {
            Klass k = (Klass) ite.next();
            Student st = k.findStudent( new Student( studentName ) );

            if( st != null )
                return k;
        }

        return null;
    }

    /**
     * Given an assignment's name, find which class it is in.
     * 
     * @pre the assignment and class both exist
     * @post the correct class has been returned.
     * @param assignmentName the assignment to find the class for
     * @return the klass object owning the assignment.
     */
    public static Klass getAssignmentClassFromString( String assignmentName )
    {
        ListIterator ite = classes.listIterator();

        while( ite.hasNext() )
        {
            Klass k = (Klass) ite.next();
            Assignment b = new Assignment( assignmentName );
            Assignment a = k.findAssignment( b );

            if( a != null )
                return k;
        }

        return null;
    }

    /**
     * Displays an error message
     * 
     * @pre none
     * @post none
     * @param e the message to display.
     */
    public static void error( String e )
    {
        JOptionPane.showMessageDialog( null, e, "Error",
                JOptionPane.ERROR_MESSAGE );
    }

    /**
     * Checks to see if the program has sufficient data to begin a report
     * 
     * @pre none
     * @post the correct response has been returned
     * @return true if the program is ready for a report, false otherwise.
     */
    public static boolean programReportReady()
    {
        ListIterator lite = classes.listIterator();

        while( lite.hasNext() )
        {
            if( !( (Klass) lite.next() ).classReportReady() )
            {
                return false;
            }
        }

        if( classes.getLength() < 1 )
            return false;

        return true;
    }

    /**
     * Displays an informative message
     * 
     * @pre none
     * @post none
     * @param s the message to display.
     */
    public static void info( String s )
    {
        JOptionPane.showMessageDialog( null, s, "Grade Book",
                JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Given an io file pointer location, read in a string
     * 
     * @param location the location to be read from
     * @pre the location is within the file, the string is formatted to
     *      GradeBook.PROGRAM_LIMIT characters.
     * @post the string has been correctly parsed.
     * @return the string at that location, minus its padding
     * @throws IOException
     */
    public static String readString( long location ) throws IOException
    {
        String temp = "";
        RandomAccessFile io = new RandomAccessFile( saveFile, "rw" );
        io.seek( location );
        for( int i = 0; i < GradeBook.PROGRAM_LIMIT; i++ )
        {
            temp = temp + io.readChar();
        }

        for( int i = GradeBook.PROGRAM_LIMIT; i > 0; i-- )
        {
            if( temp.charAt( i - 1 ) != ' ' )
                break;

            temp = temp.substring( 0, i - 1 );
        }

        return temp;
    }

}
