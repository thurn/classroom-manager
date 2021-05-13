package ws.thurn.dossier;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * The FileSaver class saves a specific class to the program's save file.
 * 
 * @author Derek Thurn
 */
public class FileSaver
{

    private RandomAccessFile io;
    // The io file which is being saved to.

    private long overwrite = -1;
    // If -1, the program saves at the first empty slot instead of overwriting.

    private boolean time = false;

    // If true, the program will time how long it takes to save data.

    /**
     * Saves the specified class to file.
     * 
     * @param raf
     * @param k
     */
    public FileSaver( RandomAccessFile raf, Klass k )
    {
        long start = 0;
        if( time )
        {
            start = System.currentTimeMillis();
        }

        io = raf;

        try
        {
            writeClass( k );
        }
        catch( IOException e )
        {
            GradeBook.error( "Input Error." );
        }

        if( time )
        {
            long stop = System.currentTimeMillis();
            System.out.println( stop - start );
        }
    }

    /**
     * Saves the specified class to file, overwriting a specified class.
     * 
     * @param raf
     * @param k
     * @param location
     */
    public FileSaver( RandomAccessFile raf, Klass k, long location ) // overwrite
    {
        io = raf;
        overwrite = location;

        try
        {
            writeClass( k );
        }
        catch( IOException e )
        {
            GradeBook.error( "Input Error." );
        }
    }

    /**
     * Writes a class to file.
     * 
     * @param theClass
     * @throws IOException
     * @pre the save file exists and there is hard-drive space.
     * @post the class has been saved to disk.
     */
    public void writeClass( Klass theClass ) throws IOException
    {
        LinkedList data = new LinkedList();
        ListIterator ite;

        if( overwrite == -1 )
        {
            long loc = findFirstEmptySlot();
            io.seek( loc );
        }
        else
        {
            io.seek( overwrite );
        }

        long start = io.getFilePointer();

        // write basic class data
        io.writeChars( pad( theClass.getClassName() ) );
        io.writeChars( pad( theClass.getTeacherName() ) );
        io.writeChars( pad( theClass.getSchoolName() ) );

        // write student info
        data = theClass.getStudents();
        ite = data.listIterator();
        while( ite.hasNext() )
            writeStudent( (Student) ite.next() );

        writeNullBytesTo( start + 300 + 5200 );

        // write category info
        data = theClass.getCategories();
        ite = data.listIterator();
        while( ite.hasNext() )
            writeCategory( (Category) ite.next() );

        writeNullBytesTo( start + 300 + 10400 );

        // write assignment info
        data = theClass.getAssignments();
        ite = data.listIterator();
        while( ite.hasNext() )
            writeAssignment( (Assignment) ite.next() );

        writeNullBytesTo( start + 300 + 20800 );

        Object grades[][] = ( theClass.getGradeData() );
        // write grade integer data
        for( int i = 0; i < GradeBook.PROGRAM_LIMIT; i++ )
        {
            for( int j = 1; j < GradeBook.PROGRAM_LIMIT; j++ )
            {
                if( grades[ i ][ j ] == null )
                {
                    io.writeInt( -1 );
                }
                else
                {
                    io.writeInt( Integer.parseInt( grades[ i ][ j ] + "" ) );
                }
            }
        }
    }

    /**
     * Writes a class' student to file.
     * 
     * @param theStudent
     * @throws IOException
     * @pre the student data is intact
     * @post the student has been saved to file.
     */
    public void writeStudent( Student theStudent ) throws IOException // 2
    // lines
    {
        io.writeChars( pad( theStudent.getName() ) );
        io.writeInt( theStudent.getID() );
    }

    /**
     * Writes a class' category to file.
     * 
     * @param theCategory
     * @throws IOException
     * @pre the category data is intact
     * @post the category has been saved to file.
     */
    public void writeCategory( Category theCategory ) throws IOException // 2
    // lines
    {
        io.writeChars( pad( theCategory.getName() ) );
        io.writeInt( theCategory.getWeight() );
    }

    /**
     * Writes a class' assignment to file.
     * 
     * @param theAssignment
     * @throws IOException
     * @pre the assignment data is intact
     * @post the assignment has been saved to file.
     */
    public void writeAssignment( Assignment theAssignment ) throws IOException // 4
    // lines
    {
        io.writeChars( pad( theAssignment.getName() ) );
        io.writeInt( theAssignment.getTotalMarks() );
        io.writeInt( theAssignment.getWeight() );
        io.writeChars( pad( theAssignment.getCategory().toString() ) );
    }

    /**
     * Writes a series of spaces to the file to pad the record to the correct
     * size.
     * 
     * @param location
     * @throws IOException
     * @pre the save file exists
     * @post null bytes have been written to the save file.
     */
    public void writeNullBytesTo( long location ) throws IOException
    {
        while( io.getFilePointer() < location )
        {
            io.writeChar( ' ' );
        }
    }

    /**
     * Prepares a string for saving by padding it.
     * 
     * @param s the string to pad
     * @return
     * @pre a string object is correctled passed
     * @post a string padded to GradeBook.PROGRAM_LIMIT characters is returned.
     */
    public String pad( String s )
    {
        StringBuffer sBuffer;

        if( s != null )
        {
            sBuffer = new StringBuffer( s );
        }
        else
        {
            sBuffer = new StringBuffer( GradeBook.PROGRAM_LIMIT );
        }
        sBuffer.setLength( GradeBook.PROGRAM_LIMIT );
        return sBuffer.toString().replace( '\0', ' ' );
    }

    /**
     * Locates the first empty slot in the file.
     * 
     * @return the slot location
     * @throws IOException
     * @pre the save file exists
     * @post the pointer location of the first empty slot is returned.
     */
    public long findFirstEmptySlot() throws IOException
    {
        long l = GradeBook.fileFindClass( new Klass(
                "This Slot Is Currently Empty" ) );
        if( l != -1 )
            return l;
        else
            return io.length();

    }

}
