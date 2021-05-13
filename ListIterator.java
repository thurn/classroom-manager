package ws.thurn.dossier;

/*
 * Based on code from Horstmann, C. (2006). <u>Big Java</u> (Ch. 20)
 * John Wiley & Sons, Inc.
 */

/**
 * A list iterator allows access of a position in a linked list. It is used to
 * move through the list sequentially.
 * 
 * @author Derek Thurn
 */
public interface ListIterator
{
    /**
     * Moves the iterator past the next element.
     * 
     * @return the traversed element
     */
    Object next();

    /**
     * Tests if there is an element after the iterator position.
     * 
     * @return true if there is an element after the iterator position
     */
    boolean hasNext();

    /**
     * Removes the last traversed element. This method may only be called after
     * a call to the next() method.
     */
    void remove();

}
