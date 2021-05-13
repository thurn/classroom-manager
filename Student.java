package ws.thurn.dossier;

/**
 * The Student class stores information on a specific student, including the
 * student's name and the student's ID number.
 * 
 * @author Derek Thurn
 */
public class Student
{

    private String studentName = "";
    // The name of the student.

    private int idNumber = 0;
    // The student's ID number.

    private Klass theClass;

    // The class which the student is in.

    /**
     * Creates a blank student.
     */
    public Student()
    {

    }

    /**
     * Creates a student.
     * 
     * @param n the name of the student.
     */
    public Student( String n )
    {
        studentName = n;
    }

    /**
     * Creates a student
     * 
     * @param n the name of student
     * @param k the class which the student is in.
     */
    public Student( String n, Klass k )
    {
        studentName = n;
        theClass = k;
    }

    /**
     * Creates a student
     * 
     * @param n The name of the student
     * @param i The student's ID number
     * @param k The class the student is in.
     */
    public Student( String n, int i, Klass k )
    {
        studentName = n;
        idNumber = i;
        theClass = k;
    }

    /**
     * Gets the student's ID number
     * 
     * @return idNumber the ID
     */
    public int getID()
    {
        return idNumber;
    }

    /**
     * Gets the student's name
     * 
     * @return studentName the name
     */
    public String getName()
    {
        return studentName;
    }

    /**
     * Sets the student's ID
     * 
     * @param i the ID to set.
     */
    public void setID( int i )
    {
        idNumber = i;
    }

    /**
     * Sets the student's name
     * 
     * @param n the name to send.
     */
    public void setName( String n )
    {
        studentName = n;
    }

    /**
     * Gets the name of the student
     * 
     * @return studentName the student's name.
     */
    public String toString()
    {
        return studentName;
    }

    /**
     * Calculate's the student's average.
     * 
     * @pre grade data has been specified for the student
     * @post the average is returned correctly.
     * @return the student's average.
     */
    public double getAverage()
    {
        return theClass.getAverage( studentName );
    }

}
