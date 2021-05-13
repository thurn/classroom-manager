package ws.thurn.dossier;

/**
 * The Klass class stores information on a single class in the program, and
 * contains the methods for most of the basic information interactions that must
 * take place during the operation of the program.
 * 
 * @author Derek Thurn
 */
public class Klass
{

    private String className = "";
    // The name of the class.

    private String teacherName = "";
    // The name of the teacher.

    private String schoolName = "";
    // The name of the school.

    private LinkedList students = new LinkedList();
    // A list containing the students in the class.

    private LinkedList assignments = new LinkedList();
    // A list containing the assignments in the class.

    private LinkedList categories = new LinkedList();
    // A list containing the categories in the class.

    private LinkedList weights = new LinkedList();
    // A list containing the weights of assignments in the class.

    private LinkedList totalMarks = new LinkedList();
    // A list containing the total mark values of assignments in the class.

    private Object[][] data = new Object[ GradeBook.PROGRAM_LIMIT ][ GradeBook.PROGRAM_LIMIT ];

    // The 2D Object array which is used to store grade data for the Grades
    // screen.

    /**
     * Creates a new blank class.
     */
    public Klass()
    {
        className = "";
    }

    /**
     * Creates a class.
     * 
     * @param n The name of the class.
     */
    public Klass( String n )
    {
        className = n;
    }

    /**
     * Creates a new class.
     * 
     * @param n The name of the class.
     * @param tn The name of the teacher.
     * @param sn The name of the school.
     */
    public Klass( String n, String tn, String sn )
    {
        className = n;
        teacherName = tn;
        schoolName = sn;
    }

    /**
     * Returns the teacher's name.
     * 
     * @return Returns the teacherName.
     */
    public String getTeacherName()
    {
        return teacherName;
    }

    /**
     * Returns the school's name.
     * 
     * @return Returns the schoolName.
     */
    public String getSchoolName()
    {
        return schoolName;
    }

    /**
     * Returns the name of the classes.
     * 
     * @return Returns the className.
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Returns the Linked List containing the students.
     * 
     * @return Returns the students.
     */
    public LinkedList getStudents()
    {
        return students;
    }

    /**
     * Returns the Linked List containing the assignments.
     * 
     * @return Returns the assignments.
     */
    public LinkedList getAssignments()
    {
        return assignments;
    }

    /**
     * Returns the Linked List containing weight values.
     * 
     * @return Returns the weights.
     */
    public LinkedList getWeights()
    {
        return weights;
    }

    /**
     * Returns the Linked List containing the Total Marks values.
     * 
     * @return
     */
    public LinkedList getTotalMarks()
    {
        return totalMarks;
    }

    /**
     * Returns the Linked List containing the program's categories.
     * 
     * @return Returns the categories.
     */
    public LinkedList getCategories()
    {
        return categories;
    }

    /**
     * Given a student's name, return his student object.
     * 
     * @pre the student exists
     * @post the correct object is returned
     * @param s The student's name
     * @return the student object.
     */
    public Student getStudentFromString( String s )
    {
        Object temp = students.find( s );
        return (Student) temp;
    }

    /**
     * Given an assignment's name, return it's assignment object.
     * 
     * @pre the assignment exists
     * @post the correct object is returned
     * @param s The assignments's name
     * @return the assignment object.
     */
    public Assignment getAssignmentFromString( String s )
    {
        Object temp = assignments.find( s );
        return (Assignment) temp;
    }

    /**
     * Given a category name, return it's category object.
     * 
     * @pre the category exists
     * @post the correct object is returned
     * @param s The category's name
     * @return the category object.
     */
    public Category getCategoryFromString( String s )
    {
        Object temp = categories.find( s );
        return (Category) temp;
    }

    /**
     * Locate a student object based on a replica of it.
     * 
     * @param std the student to locate the object of.
     * @pre the student is already in the program
     * @post the correct object is returned.
     * @return the object of the specified student.
     */
    public Student findStudent( Student std )
    {
        return (Student) ( students.find( std ) );
    }

    /**
     * Locate an assignment object based on a replica of it.
     * 
     * @param as the assignment to locate the object of.
     * @pre the assignment is already in the program
     * @post the correct object is returned.
     * @return the object of the specified assignment.
     */
    public Assignment findAssignment( Assignment as )
    {
        return (Assignment) ( assignments.find( as ) );
    }

    /**
     * Locate a category object based on a replica of it.
     * 
     * @param c the category to locate the object of.
     * @pre the category is already in the program
     * @post the correct object is returned.
     * @return the object of the specified category.
     */
    public Category findCategory( Category c )
    {
        return (Category) ( categories.find( c ) );
    }

    /**
     * Set the teacher name.
     * 
     * @param teacherName The teacherName to set.
     */
    public void setTeacherName( String tn )
    {
        teacherName = tn;
    }

    /**
     * Check if a specified student is already in the program
     * 
     * @pre a students linked list has been created
     * @post the correct response is returned
     * @param d the student to check if it is duplicate.
     * @return true if it is duplicate, false otherwise.
     */
    public boolean isDuplicateStudent( Student d )
    {
        String dupe = d.toString();
        ListIterator ite = students.listIterator();

        while( ite.hasNext() )
        {
            String c = ite.next().toString();
            if( c.equals( dupe ) )
                return true;
        }

        return false;
    }

    /**
     * Check if a specified assignment is already in the program
     * 
     * @pre an assignments linked list has been created
     * @post the correct response is returned
     * @param a the assignment to check if it is duplicate.
     * @return true if it is duplicate, false otherwise.
     */
    public boolean isDuplicateAssignment( Assignment a )
    {
        String dupe = a.toString();
        ListIterator ite = assignments.listIterator();

        while( ite.hasNext() )
        {
            String c = ite.next().toString();
            if( c.equals( dupe ) )
                return true;
        }

        return false;
    }

    /**
     * Check if a specified category is already in the program
     * 
     * @pre a categories linked list has been created
     * @post the correct response is returned
     * @param c the category to check if it is duplicate.
     * @return true if it is duplicate, false otherwise.
     */
    public boolean isDuplicateCategory( Category c )
    {
        String dupe = c.toString();
        ListIterator ite = categories.listIterator();

        while( ite.hasNext() )
        {
            String d = ite.next().toString();
            if( d.equals( dupe ) )
                return true;
        }

        return false;
    }

    /**
     * Return a string array containing the students in the class.
     * 
     * @pre a linked list of students exists
     * @post a correct one dimensional string array is returned.
     * @return a string array of students.
     */
    public String[] getStudentsStringArray()
    {
        String arr[] = new String[ students.getLength() ];
        ListIterator lite = students.listIterator();
        int i = 0;
        while( lite.hasNext() )
        {
            arr[ i ] = lite.next().toString();
            i++;
        }

        return arr;
    }

    /**
     * Return a string array containing the assignments in the class.
     * 
     * @pre a linked list of assignments exists
     * @post a correct one dimensional string array is returned.
     * @return a string array of assignments.
     */
    public String[] getAssignmentsStringArray()
    {
        String arr[] = new String[ assignments.getLength() ];
        ListIterator lite = assignments.listIterator();
        int i = 0;
        while( lite.hasNext() )
        {
            arr[ i ] = lite.next().toString();
            i++;
        }

        return arr;
    }

    /**
     * Return a string array containing the categories in the class.
     * 
     * @pre a linked list of categories exists
     * @post a correct one dimensional string array is returned.
     * @return a string array of categories.
     */
    public String[] getCategoriesStringArray()
    {
        String arr[] = new String[ categories.getLength() ];
        ListIterator lite = categories.listIterator();
        int i = 0;
        while( lite.hasNext() )
        {
            arr[ i ] = lite.next().toString();
            i++;
        }

        return arr;
    }

    /**
     * Sets the school name.
     * 
     * @param schoolName The schoolName to set.
     */
    public void setSchoolName( String sn )
    {
        schoolName = sn;
    }

    /**
     * Sets the name of the class.
     * 
     * @param className The className to set.
     */
    public void setClassName( String cn )
    {
        className = cn;
    }

    /**
     * Adds a student to the class.
     * 
     * @param s the student to add.
     * @pre there is room for the student
     * @post the student has been added to the class.
     */
    public void addStudent( Student s )
    {
        if( getNumStudents() + 1 >= GradeBook.PROGRAM_LIMIT )
        {
            GradeBook
                    .error( "The maximum number of students has been reached." );
            return;
        }

        students.addLast( s );
        int location = 0;
        ListIterator ite = students.listIterator();
        int i = 0;
        while( ite.hasNext() )
        {
            String a = s.toString();
            String b = ite.next().toString();
            if( a.equals( b ) )
                location = i;

            i++;
        }

        if( location < 0 )
        {
            GradeBook.error( "Error adding student." );
            return;
        }

        insertGradeRowFromZeroAt( location );

    }

    /**
     * Adds a category to the class.
     * 
     * @param c the category
     * @pre there is room for the category
     * @post the category has been added to the class.
     */
    public void addCategory( Category c )
    {
        categories.addLast( c );
    }

    /**
     * Adds an assignment to the class.
     * 
     * @param a the assignment to add.
     * @pre there is room for the assignment
     * @post the assignment has been added to the class.
     */
    public void addAssignment( Assignment a )
    {
        if( getNumAssignments() + 1 >= GradeBook.PROGRAM_LIMIT )
        {
            GradeBook
                    .error( "The maximum number of assignments has been reached." );
            return;
        }

        if( !( a.getCategory().equals( "Assignment Has No Category X" ) ) )
        {
            if( a.getCategory().getWeight() != a.getWeight() )
                a.setWeight( a.getCategory().getWeight() );
        }

        assignments.addLast( a );
        weights.addLast( a.getWeight() );
        totalMarks.addLast( a.getTotalMarks() );

    }

    /**
     * Adds a weight value to the class's list of weights
     * 
     * @param i the weight to add
     * @pre the weights list exists
     * @post the weight has been added.
     */
    public void addWeight( int i )
    {
        weights.addLast( i );
    }

    /**
     * Returns the number of students in the class.
     * 
     * @pre the students linked list exists
     * @post the correct number is returned
     * @return the number of students in the class.
     */
    public int getNumStudents()
    {
        return students.getLength();
    }

    /**
     * Returns the number of categories in the class.
     * 
     * @pre the categories linked list exists
     * @post the correct number is returned
     * @return the number of categories in the class.
     */
    public int getNumCategories()
    {
        return categories.getLength();
    }

    /**
     * Returns the number of weights in the weights list.
     * 
     * @pre the weights linked list exists
     * @post the correct number is returned
     * @return the number of weights that are stored.
     */
    public int getNumWeights()
    {
        return weights.getLength();
    }

    /**
     * Returns the number of total marks values in the total marks list.
     * 
     * @pre the total marks linked list exists
     * @post the correct number is returned
     * @return the number of total marks values. that are stored.
     */
    public int getNumTotalMarks()
    {
        return totalMarks.getLength();
    }

    /**
     * Returns the number of assignments in the class.
     * 
     * @pre the assignments linked list exists
     * @post the correct number is returned
     * @return the number of assignments in the class.
     */
    public int getNumAssignments()
    {
        return assignments.getLength();
    }

    /**
     * Deletes a student based on the student name
     * 
     * @pre the student is already in the program
     * @post the student has been deleted
     * @param s the student to delete.
     */
    public void deleteStudent( String s )
    {
        ListIterator lite = students.listIterator();

        while( lite.hasNext() )
        {
            if( lite.next().equals( s ) )
            {
                lite.remove();
            }
        }
    }

    /**
     * Deletes an assignment based on the assignment name
     * 
     * @pre the assignment is already in the program
     * @post the assignment has been deleted
     * @param as the assignment to delete.
     */
    public void deleteAssignment( Assignment as )
    {
        ListIterator ite = assignments.listIterator();
        ListIterator wite = weights.listIterator();
        ListIterator mite = totalMarks.listIterator();
        String b = as.toString();
        boolean removed = false;

        while( ite.hasNext() )
        {
            String a = ite.next().toString();
            wite.next();
            mite.next();
            if( a.equals( b ) )
            {
                ite.remove();
                wite.remove();
                mite.remove();
                removed = true;
            }
        }

        if( removed = false )
            GradeBook.error( "Error removing assignment" );
    }

    /**
     * Deletes a category based on the category name
     * 
     * @pre the category is already in the program
     * @post the category has been deleted
     * @param s the category to delete.
     */
    public void deleteCategory( String s )
    {
        ListIterator lite = categories.listIterator();

        while( lite.hasNext() )
        {
            if( lite.next().equals( s ) )
            {
                lite.remove();
            }
        }
    }

    /**
     * Returns the entire two dimensional object grade array
     * 
     * @pre the data array exists
     * @post the data array has been returned
     * @return the data array.
     */
    public Object[][] getGradeData()
    {
        return data;
    }

    public int getGradeValue( int r, int c )
    {
        return Integer.parseInt( data[ r ][ c ].toString() );
    }

    /**
     * Sets up the grade data with students in the rightmost column
     * 
     * @pre the data object has been declared
     * @post the students have been listed in the right column
     */
    public void initGradeData()
    {
        int numStudents = students.getLength();

        for( int i = 0; i < numStudents; i++ )
        {
            data[ i ][ 0 ] = students.findFromLocation( i ).toString();
        }

    }

    /**
     * Sets a specific grade to a specific location.
     * 
     * @param row the row number of the cell being set to
     * @param col the column number of the cell being set to
     * @param grade the Object containing the grade integer
     * @pre the data array exists
     * @post the grade has been added at the correct location.
     */
    public void setGradeData( int row, int col, Object grade )
    {

        if( (Integer) grade == null || grade.toString().equals( "" ) )
        {
            data[ row ][ col ] = 0;
        }

        data[ row ][ col ] = Integer.parseInt( grade.toString() );
    }

    /**
     * An algorithm to calculate a student's percentage on an assignment
     * 
     * @param stud the student whose percentage is being found
     * @param assign the assignment whose percentage is being found
     * @pre the correct grade, student, and assignments all exist
     * @post the student's grade is returned
     * @return the student's percentage grade.
     */
    public double getPercentage( Student stud, Assignment assign )
    {
        int sLocation = findStudentLocation( stud.toString() );

        int aLocation = -1;
        ListIterator ite = assignments.listIterator();
        int i = 0;
        while( ite.hasNext() )
        {
            String a = assign.toString();
            String b = ite.next().toString();
            if( a.equals( b ) )
                aLocation = i;

            i++;
        }

        if( aLocation < 0 )
            GradeBook.error( "Error getting percentage assignment" );

        int possibleGrade = (Integer) totalMarks.findFromLocation( aLocation );
        aLocation = aLocation + 1;
        int currentGrade;
        if( data[ sLocation ][ aLocation ] == null )
        {
            currentGrade = 0;
        }
        else
            currentGrade = Integer.parseInt( data[ sLocation ][ aLocation ]
                    .toString() );

        return ( (double) currentGrade / (double) possibleGrade ) * 100.0;
    }

    /**
     * Given a student's name, find its sequential locaiton in the linked list
     * 
     * @pre the students list has been created, the student exists
     * @post the correct student location is found
     * @param studentName the name of the student to find
     * @return the location of the student.
     */
    public int findStudentLocation( String studentName )
    {
        int location = -1;
        for( int i = 0; i < GradeBook.PROGRAM_LIMIT; i++ )
        {
            if( data[ i ][ 0 ] == null )
            {

            }
            else
            {
                if( ( data[ i ][ 0 ].toString() ).equals( studentName ) )
                {
                    location = i;
                    break;
                }
            }
        }

        if( location == -1 )
            GradeBook.error( "Student not found" );

        return location;
    }

    /**
     * An algorithm to calculate a student's average
     * 
     * @pre grades have been correctly entered for each assignments
     * @post the correct average is returned,
     * @param studentName the name of the student to get an average for
     * @return the student's average.
     */
    public double getAverage( String studentName )
    {
        int location = findStudentLocation( studentName );
        // collect average
        int numAssignments = assignments.getLength();
        int currentGrade;
        int totalWeight = weights.sumIntegers();
        int currentWeight;
        int possibleMarks;
        double percentage;
        double weightPercentage;
        double average = 0;

        for( int n = 1; n <= numAssignments; n++ )
        {
            if( data[ location ][ n ] == null )
            {
                currentGrade = 0;
            }
            else
            {
                currentGrade = Integer.parseInt( data[ location ][ n ]
                        .toString() );
            }

            possibleMarks = (Integer) totalMarks.findFromLocation( n - 1 );
            percentage = (double) currentGrade / (double) possibleMarks;
            currentWeight = (Integer) weights.findFromLocation( n - 1 );
            weightPercentage = (double) currentWeight / (double) totalWeight;
            percentage = percentage * weightPercentage;
            average = average + percentage;
        }

        return ( average * 100 );
    }

    /**
     * An algorithm to calculate a student's average in a category.
     * 
     * @pre grades have been entered for each assignment in the category.
     * @post the correct categorical avarage is found
     * @param studentName the name of the student to get a category average for
     * @param categoryName the name of the category which the average is being
     *        found for
     * @return the categorical average.
     */
    public double getStudentCategoryAverage( String studentName,
            String categoryName )
    {
        Category theCategory = (Category) categories.find( categoryName );
        Student theStudent = (Student) students.find( studentName );
        LinkedList assignments = theCategory.getAssignments();
        ListIterator lite = assignments.listIterator();
        double average = 0.0;

        while( lite.hasNext() )
        {
            Assignment a = (Assignment) lite.next();
            average = average + getPercentage( theStudent, a );
        }

        return average / (double) assignments.getLength();
    }

    /**
     * An algorithm to calculate the class's average on an assignment.
     * 
     * @pre all of the students have correct grade data for the assignment.
     * @post the correct class assignment average is found.
     * @param assignmentName the name of the assignment to find the average for.
     * @return the average of the class on the assignment.
     */
    public double getClassAssignmentAverage( String assignmentName )
    {
        Assignment theAssignment = (Assignment) assignments
                .find( assignmentName );
        Student theStudent;
        ListIterator lite = students.listIterator();
        double average = 0.0;

        while( lite.hasNext() )
        {
            theStudent = (Student) lite.next();
            average = average + getPercentage( theStudent, theAssignment );
        }

        return average / (double) students.getLength();
    }

    /**
     * An algorithm to find the class average.
     * 
     * @pre the grade data has been entered for everyone in the class
     * @post the correct class average is returned
     * @return the average of the individual averages.
     */
    public double getClassAverage()
    {
        ListIterator iterator = students.listIterator();
        double classAverage = 0.0;
        while( iterator.hasNext() )
        {
            classAverage = classAverage
                    + getAverage( iterator.next().toString() );
        }
        classAverage = classAverage / (double) ( students.getLength() );

        return classAverage;
    }

    /**
     * Inserts a new row into the data object.
     * 
     * @pre the data array has been declared.
     * @post a new blank row exists at the specified location.
     * @param location the location of the new row (counting from zero)
     */
    public void insertGradeRowFromZeroAt( int location )
    {
        for( int i = students.getLength() + 1; i > location; i-- )
        {
            for( int g = 0; g <= ( assignments.getLength() + 1 ); g++ )
            {
                data[ i + 1 ][ g ] = data[ i ][ g ];
            }
        }

        for( int k = 0; k <= assignments.getLength() + 1; k++ )
        {
            data[ location ][ k ] = null;
        }

    }

    /**
     * Checks to see if the class is ready for a report to be created.
     * 
     * @pre none
     * @post the readiness is reported correctly.
     * @return true if the class is ready for a report, false otherwise.
     */
    public boolean classReportReady()
    {
        if( students.getLength() < 1 )
            return false;

        if( assignments.getLength() < 1 )
            return false;

        if( data[ 0 ][ 1 ] == null )
            return false;

        return true;
    }

    /**
     * Checks to see if the class is ready for grade information
     * 
     * @pre none
     * @post returns the readiness correctly.
     * @return true if the class is ready for grade data, false otherwise.
     */
    public boolean classGradeReady()
    {
        if( students.getLength() < 1 )
            return false;

        if( assignments.getLength() < 1 )
            return false;

        return true;
    }

    /**
     * Returns the name of the class
     * 
     * @pre the name has been specified
     * @post the name has been returned.
     * @return the class name as a string.
     */
    public String toString()
    {
        return className;
    }

}
