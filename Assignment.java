package ws.thurn.dossier;

/**
 * The Assignment class stores information on Assignments, including the
 * assignment name, the assignment weight, the number of marks the assignment is
 * out of, and the category of the assignment.
 * 
 * @author Derek Thurn
 */
public class Assignment
{

    private String name;
    // The name of the assignment

    private int weight = 1;
    // The weight of the assignment

    private int totalMarks;
    // The total marks that the assignment is out of

    private Category category;

    // The category of the assignment.

    /**
     * Specifies a blank assignment.
     */
    public Assignment()
    {
        name = "";
        totalMarks = 0;
    }

    /**
     * Specifies an assignment with just a name.
     * 
     * @param n
     */
    public Assignment( String n )
    {
        name = n;
    }

    /**
     * @param n
     * @param tm Specifies an assignment with a name and total marks.
     */
    public Assignment( String n, int tm )
    {
        name = n;
        totalMarks = tm;
    }

    /**
     * @param n
     * @param tm
     * @param w Specifies an assignment with a name, total marks, and a weight.
     */
    public Assignment( String n, int tm, int w )
    {
        name = n;
        totalMarks = tm;
        weight = w;
    }

    /**
     * @param n
     * @param tm
     * @param w
     * @param c Specifies a full assignment with a name, total marks, weight,
     *        and category.
     */
    public Assignment( String n, int tm, int w, Category c )
    {
        name = n;
        totalMarks = tm;
        weight = w;
        category = c;
        c.addAssignment( this );
    }

    /**
     * Returns the Total Marks the assignment is out of.
     * 
     * @return Returns the totalMarks.
     * @pre totalMarks has a value
     * @post none
     */
    public int getTotalMarks()
    {
        return totalMarks;
    }

    /**
     * Returns the assignment's name
     * 
     * @return Returns the name.
     * @pre none
     * @post none
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the assignment's weight.
     * 
     * @return Returns the weight.
     * @pre none
     * @post none
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Returns the assignment's category.
     * 
     * @return Returns the category.
     * @pre none
     * @post none
     */
    public Category getCategory()
    {
        if( category == null )
            return new Category( "Assignment Has No Category X" );
        else
            return category;
    }

    /**
     * Sets the assignment's category.
     * 
     * @param category The category to set.
     * @pre none
     * @post the category has been set.
     */
    public void setCategory( Category c )
    {
        category = c;
        c.addAssignment( this );
    }

    /**
     * Sets the assignment's total marks.
     * 
     * @param totalMarks The totalMarks to set.
     * @pre none
     * @post the totalMarks have been set
     */
    public void setTotalMarks( int tm )
    {
        totalMarks = tm;
    }

    /**
     * Sets the assignment's name.
     * 
     * @param name The name to set.
     * @pre none
     * @post the name has been set
     */
    public void setName( String n )
    {
        name = n;
    }

    /**
     * Sets the weight of the assignment.
     * 
     * @param weight The weight to set.
     * @pre none
     * @post the weight has been set.
     */
    public void setWeight( int w )
    {
        weight = w;
    }

    /**
     * Returns the name of the assignment.
     * 
     * @pre the assignment has a name.
     */
    public String toString()
    {
        return name;
    }

}
