package ws.thurn.dossier;

/**
 * The Category class stores information about a single category within the
 * program. It keeps the category name, its weight, and which assignments are in
 * this category.
 * 
 * @author Derek Thurn
 */
public class Category
{

    private String name;
    // The name of the category.

    private int weight = 1;
    // The weight of the category.

    private LinkedList assignments = new LinkedList();

    // A list of the Linked Lists in the category.

    /**
     * Creates an empty category
     */
    public Category()
    {
        name = "";
    }

    /**
     * Creates a category by name
     * 
     * @param n
     */
    public Category( String n )
    {
        name = n;
    }

    /**
     * Creates a category with a name and weight
     * 
     * @param n
     * @param w
     */
    public Category( String n, int w )
    {
        name = n;
        weight = w;
    }

    /**
     * Creates a full category including name, weight, and which assignments are
     * in it.
     * 
     * @param n
     * @param w
     * @param ll
     */
    public Category( String n, int w, LinkedList ll )
    {
        name = n;
        weight = w;
        assignments = ll;
    }

    /**
     * Adds an assignment to the category.
     * 
     * @param a
     * @pre none
     * @post the assignment has been added to the category.
     */
    public void addAssignment( Assignment a )
    {
        assignments.addLast( a );
    }

    /**
     * Returns the assignments LinkedList.
     * 
     * @return Returns the assignments.
     * @pre assignments have been specified
     * @post the assignments list has been returned.
     */
    public LinkedList getAssignments()
    {
        return assignments;
    }

    /**
     * Returns the category's name.
     * 
     * @return Returns the name.
     * @pre a name has been specified
     * @post none
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the category weight.
     * 
     * @return Returns the weight.
     * @pre a weight has been specified
     * @post none
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * Sets the category's name
     * 
     * @param name The name to set.
     * @pre none
     * @post a name has been set
     */
    public void setName( String n )
    {
        name = n;
    }

    /**
     * Sets the category's weight.
     * 
     * @param weight The weight to set.
     * @pre none
     * @post a weight has been set
     */
    public void setWeight( int w )
    {
        weight = w;
    }

    /**
     * Returns the category name
     * 
     * @pre A name has been set
     * @post none
     */
    public String toString()
    {
        return name;
    }

}
