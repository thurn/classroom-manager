package ws.thurn.dossier;

import java.util.NoSuchElementException;

/*
 * Originally based on code from Horstmann, C. (2006). <u>Big Java</u> (Ch. 20)
 * John Wiley & Sons, Inc.
 */

/**
 * A linked list is a sequence of nodes with efficient element insertion and
 * removal. It stores data in a sequential fashion.
 * 
 * @author Derek Thurn
 */
public class LinkedList
{

    private int listLength = 0;
    // the number of nodes in the linked list

    private Node first;
    // the first node of the linked list.

    private Node last;

    // the last node of the linked list.

    /**
     * Constructs an empty linked list.
     */
    public LinkedList()
    {
        first = null;
        last = null;
    }

    /**
     * Gets the length of the list.
     * 
     * @return the length.
     */
    public int getLength()
    {
        return listLength;
    }

    /**
     * Returns the first element in the linked list.
     * 
     * @return the first element in the linked list
     */
    public Object getFirst()
    {
        if( first == null )
            throw new NoSuchElementException();
        return first.data;
    }

    /**
     * Removes the first element in the linked list.
     * 
     * @pre the first element exists
     * @post the first element has been removed.
     * @return the removed element
     */
    public Object removeFirst()
    {
        if( first == null )
            throw new NoSuchElementException();
        if( first == last )
            last = null;
        Object element = first.data;
        first = first.next;
        listLength--;
        return element;
    }

    /**
     * Removes the last element in the linked list.
     * 
     * @pre the last element exists
     * @post the last element has been removed.
     * @return the removed element
     */
    public Object removeLast()
    {
        if( last == null )
            throw new NoSuchElementException();
        Object element = last.data;
        if( first == last )
        {
            first = null;
            last = null;
        }
        else
        {
            Node current = first;
            while( current.next.next != null )
                current = current.next;
            last = current;
            last.next = null;
        }
        listLength--;
        return element;
    }

    /**
     * Adds an element to the front of the linked list.
     * 
     * @pre the linked list has been created
     * @post the node has been added to the front.
     * @param element the element to add
     */
    public void addFirst( Object element )
    {
        Node newNode = new Node();
        newNode.data = element;
        newNode.next = first;
        newNode.previous = null;
        first = newNode;
        if( last == null )
            last = first;

        listLength++;
    }

    /**
     * Adds an element to the end of the linked list.
     * 
     * @pre the linked list has been created
     * @post the node has been added to the back.
     * @param element the element to add
     */
    public void addLast( Object element )
    {
        Node newNode = new Node();
        newNode.data = element;
        newNode.next = null;
        if( last != null )
        {
            newNode.previous = last.next;
            last.next = newNode;
        }
        last = newNode;
        if( first == null )
        {
            first = last;
            newNode.previous = null;
        }

        listLength++;
    }

    /**
     * Assuming the list contains integers, arrive at a sum.
     * 
     * @pre a list containing integers has been created
     * @post a sum has been found
     * @return the sum of the integers.
     */
    public int sumIntegers()
    {
        int sum = 0;
        Node searchNode = new Node();
        searchNode = first;

        while( searchNode.next != null )
        {
            sum = sum + (Integer) ( searchNode.data   );
            searchNode = searchNode.next;
        }
        sum = sum + (Integer) ( searchNode.data   );

        return sum;

    }

    /**
     * Locates an object in the Linked List
     * 
     * @pre the object has a toString method.
     * @post the ojbect has been located
     * @param search the object to find
     * @return the object which was desired.
     */
    public Object find( Object search )
    {
        Node searchNode = new Node();
        searchNode = first;

        if( search == null || first == null )
            return null;

        while( searchNode.next != null )
        {

            if( ( searchNode.data.toString() ).equals( search.toString() ) )
            {
                return searchNode.data;
            }
            searchNode = searchNode.next;
        }

        if( ( searchNode.data.toString() ).equals( search.toString() ) )
            return searchNode.data;

        return null;
    }

    /**
     * Find an object based on its sequential list location.
     * 
     * @pre the location is actually in the list
     * @post the correct object is returned
     * @param location the location of the object.
     * @return the object at that location.
     */
    public Object findFromLocation( int location )
    {
        Node searchNode = new Node();
        searchNode = first;
        int runs = 0;

        if( location == 0 )
            return first.data;

        if( location >= listLength - 1 )
            return last.data;

        while( true )
        {
            searchNode = searchNode.next;

            runs++;

            if( runs == location )
                return searchNode.data;

        }

    }

    /**
     * Alphabetically adds an object to the list
     * 
     * @pre the list has been created, the object has a toString method.
     * @post the object is now alphabetically in order (by toString) in the
     *       list.
     * @param element the object to be added.
     */
    public void addAlpha( Object element )
    {
        Node newNode = new Node();
        newNode.data = element;
        String eString = element.toString();

        if( first == null )
        {
            newNode.next = first;
            first = newNode;
            if( last == null )
                last = first;

            newNode.previous = null;
            listLength++;
            return;
        }
        if( listLength == 1 )
        {
            String fst;

            fst = first.data.toString();
            int comparison = eString.compareToIgnoreCase( fst );
            comparison = Integer.signum( comparison );

            if( comparison == -1 )
            {
                // el is before fst

                first.previous = newNode;

                newNode.next = first;
                first = newNode;
                if( last == null )
                    last = first;

                listLength++;

                newNode.previous = null;
            }
            else
            {
                // el is after fst
                newNode.next = null;
                if( last != null )
                    last.next = newNode;
                last = newNode;
                if( first == null )
                    first = last;

                listLength++;

                newNode.previous = first;
            }

            return;
        }

        // More than one item in list

        Node ite = new Node();
        ite = first;

        while( true )
        {
            String itty = ite.data.toString();

            int comparator;
            comparator = eString.compareToIgnoreCase( itty );
            comparator = Integer.signum( comparator );

            if( comparator == -1 ) // if eString is before itty in case
            {
                Node temp = new Node();
                if( ite.previous == null )
                {
                    first.previous = newNode;
                    newNode.next = first;
                    newNode.previous = null;
                    first = newNode;
                    if( last == null )
                        last = first;

                    listLength++;

                    return;

                }

                temp = ite.previous;
                temp.next = newNode;
                ite.previous = newNode;
                newNode.next = ite;
                newNode.previous = temp;

                listLength++;
                return;

            }

            if( ite.equals( last ) || ite.next == null )
                break;

            ite = ite.next;
        }

        newNode.next = null;
        newNode.previous = last;
        if( last != null )
            last.next = newNode;
        last = newNode;
        if( first == null )
            first = last;

        listLength++;

    }

    /**
     * Returns a string representation of the list
     * 
     * @pre the list exists and contains objects with a toString method
     * @post a string representing the list is returned
     * @return a string representing the list.
     */
    public String toString()
    {
        Node searchNode = new Node();
        searchNode = first;
        String save = "";

        while( searchNode.next != null )
        {
            save = save + searchNode.data.toString() + "\n";
            searchNode = searchNode.next;
        }
        save = save + searchNode.data.toString();

        return save;
    }

    /**
     * Returns an iterator for iterating through this list.
     * 
     * @pre the list has been created
     * @post an iterator object is created for the list.
     * @return an iterator for iterating through this list
     */
    public ListIterator listIterator()
    {
        return new LinkedListIterator();
    }

    /**
     * @author Derek Thurn The Node class which makes up all linked lists.
     */
    private class Node
    {
        public Object data;
        public Node next;
        public Node previous;
    }

    /**
     * @author Derek Thurn The LinkedListIterator class allows for iteration
     *         through a linked list.
     */
    private class LinkedListIterator implements ListIterator
    {

        private Node position;
        private Node previous;

        /**
         * Constructs an iterator that points to the front of the linked list.
         */
        public LinkedListIterator()
        {
            position = null;
            previous = null;
        }

        /**
         * Moves the iterator past the next element.
         * 
         * @pre there is a next element
         * @post the pointer has moved over by one element.
         * @return the traversed element
         */
        public Object next()
        {
            if( !hasNext() )
                throw new NoSuchElementException();

            previous = position; // Remember for remove

            if( position == null )
                position = first;
            else
                position = position.next;

            if( position == null )
            {
                GradeBook.error( "position == null" );
            }

            if( position.data == null )
            {
                GradeBook.error( "position.data == null" );
            }

            return position.data;
        }

        /**
         * Tests if there is an element after the iterator position.
         * 
         * @pre the pointer is at a correct position
         * @post the correct response is returned.
         * @return true if there is an element after the iterator position
         */
        public boolean hasNext()
        {
            if( position == null )
                return first != null;
            else
                return position.next != null;
        }

        /**
         * Removes the last traversed element. This method may only be called
         * after a call to the next() method.
         * 
         * @pre next() has been called.
         * @post the previous element is removed
         */
        public void remove()
        {
            if( previous == position )
                throw new IllegalStateException();

            if( position == first )
            {
                removeFirst();
            }
            else
            {
                previous.next = position.next;
                if( previous.next == null )
                    last = previous;
                listLength--;
            }
            position = previous;

        }

    }
}