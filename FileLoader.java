package ws.thurn.dossier;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * The FileLoader class is passed a specific class to locate in the program's
 * save file. It loads this class into the program.
 * 
 * @author Derek Thurn
 */
public class FileLoader
{

    private File theFile;
    // The program's save file.

    private RandomAccessFile io;
    // A RandomAccessFile to access the save file.

    long klassLocation;
    // The location, by pointer, of the class to be overwritten.

    private boolean time = false;

    // If true, the program will time how long it takes to load data from the
    // file.

    /**
     * Creates a new FileLoader for a specific class.
     * 
     * @param f
     * @param k
     */
    public FileLoader( File f, Klass k )
    {
        long start = 0;
        if( time )
        {
            start = System.currentTimeMillis();
        }

        try
        {
            klassLocation = GradeBook.fileFindClass( k );

            theFile = f;
            io = new RandomAccessFile( theFile, "rw" );

            loadClass();
        }
        catch( IOException e )
        {
            GradeBook.error( "IO Exception!" );
        }

        if( time )
        {
            long stop = System.currentTimeMillis();
            System.out.println( stop - start );
        }

    }

    /**
     * Loads the specified class.
     * 
     * @throws IOException
     * @pre the specified class location is in the savefile
     * @post the specified class has been loaded into the program.
     */
    public void loadClass() throws IOException
    {
        io.seek( klassLocation );

        // read basic class data
        String className = readString();
        String teacherName = readString();
        String schoolName = readString();

        if( GradeBook.isDuplicate( new Klass( className ) ) )
        {
            GradeBook.deleteClass( className );
        }

        Klass theClass = new Klass( className, teacherName, schoolName );
        GradeBook.addClass( theClass );

        // read student info
        readStudents( theClass );

        // read category info
        readCategories( theClass );

        // read assignment info
        readAssignments( theClass );

        // read grade info
        io.seek( klassLocation + 21100 );
        for( int i = 0; i < GradeBook.PROGRAM_LIMIT; i++ )
        {
            for( int j = 1; j < GradeBook.PROGRAM_LIMIT; j++ )
            {
                int grade = io.readInt();
                if( grade != -1 )
                    theClass.setGradeData( i, j, grade );
            }
        }
    }

    /**
     * Reads the class' students.
     * 
     * @param k
     * @throws IOException
     * @pre the class is correctly saved to the file
     * @post the class' students have been read into the program
     */
    public void readStudents( Klass k ) throws IOException
    {
        String name;
        int id;
        io.seek( klassLocation + 300 );

        for( int i = 0; i < GradeBook.PROGRAM_LIMIT; i++ )
        {
            name = readString();
            if( name.equals( "" ) || name == null )
                return;

            id = io.readInt();
            k.addStudent( new Student( name, id, k ) );
        }
    }

    /**
     * Read the class' categories.
     * 
     * @param k
     * @throws IOException
     * @pre the class is correctly saved to the file
     * @post the class' categories have been read into the program
     */
    public void readCategories( Klass k ) throws IOException
    {
        String name;
        int weight;
        io.seek( klassLocation + 5500 );

        for( int i = 0; i < GradeBook.PROGRAM_LIMIT; i++ )
        {
            name = readString();
            if( name.equals( "" ) || name == null )
                return;

            weight = io.readInt();
            Category temp = new Category( name, weight );
            k.addCategory( temp );
        }
    }

    /**
     * Reads the class' assignments
     * 
     * @param k
     * @throws IOException
     * @pre the class is correctly saved to the file
     * @post the class' assignments have been read into the program
     */
    public void readAssignments( Klass k ) throws IOException
    {
        String name;
        int tm;
        int weight;
        String cat;
        io.seek( klassLocation + 10700 );

        for( int i = 0; i < GradeBook.PROGRAM_LIMIT; i++ )
        {
            name = readString();
            if( name.equals( "" ) || name == null )
                return;

            tm = io.readInt();
            weight = io.readInt();
            cat = readString();
            if( cat.equals( "Assignment Has No Category X" ) )
            {
                k.addAssignment( new Assignment( name, tm, weight ) );
            }
            else
            {
                Category c = k.findCategory( new Category( cat ) );
                k.addAssignment( new Assignment( name, tm, weight, c ) );
            }
        }
    }

    /**
     * Reads in a string from the file.
     * 
     * @return
     * @throws IOException
     * @pre the pointer is in the place where a read is desired, the string is
     *      properly formatted
     * @post the string has been read in and "unpadded".
     */
    public String readString() throws IOException
    {
        String temp = "";
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
