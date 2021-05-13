package ws.thurn.dossier;

/*
 * Copied from this tutorial:
 *
 * http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-Printing.html
 *
 * And also from a post on the forums at java.swing.com. My apologies that do not have
 * a link to that post, by my hat goes off to the poster because he/she figured out the
 * sticky problem of paging properly when printing a Swing component.
 */
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

/**
 * The Printer class is passed a compontent which it then prints to the printer.
 * 
 * @author Graeme Kelln
 */
public class Printer implements Printable
{
    private Component componentToBePrinted;

    /**
     * Prints a specified component
     * 
     * @pre none
     * @post the component is sent to the printer
     * @param c the component to print.
     */
    public static void printComponent( Component c )
    {
        new Printer( c ).print();
    }

    /**
     * Creates a new printer object to send components to the printer
     * 
     * @param componentToBePrinted the component to be printed.
     */
    public Printer( Component componentToBePrinted )
    {
        this.componentToBePrinted = componentToBePrinted;
    }

    /**
     * Prints the component.
     * 
     * @pre a component has been specified
     * @post the component has been sent to the printer.
     */
    private void print()
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable( this );
        printJob.setCopies( 1 );
        if( printJob.printDialog() )
            try
            {
                printJob.print();
            }
            catch( PrinterException pe )
            {
            }
    }

    /**
     * Prints a page with the specified format to conform to the Printable
     * interface.
     * 
     * @pre a component has been specified
     * @post the component has been printed
     */
    public int print( Graphics g, PageFormat pf, int pageIndex )
    {
        int response = NO_SUCH_PAGE;
        Graphics2D g2 = (Graphics2D) g;
        disableDoubleBuffering( componentToBePrinted );
        Dimension d = componentToBePrinted.getSize();
        double panelWidth = d.width;
        double panelHeight = d.height;
        double pageHeight = pf.getImageableHeight();
        double pageWidth = pf.getImageableWidth();
        double scale = pageWidth / panelWidth;
        int totalNumPages = (int) Math.ceil( scale * panelHeight / pageHeight );
        if( pageIndex >= totalNumPages )
        {
            response = NO_SUCH_PAGE;
        }
        else
        {
            g2.translate( pf.getImageableX(), pf.getImageableY() );
            g2.translate( 0f, -pageIndex * pageHeight );
            g2.scale( scale, scale );
            componentToBePrinted.paint( g2 );
            enableDoubleBuffering( componentToBePrinted );
            response = Printable.PAGE_EXISTS;
        }
        return response;
    }

    /**
     * Disables double buffering on the component.
     * 
     * @param c the component in question.
     */
    public static void disableDoubleBuffering( Component c )
    {
        RepaintManager currentManager = RepaintManager.currentManager( c );
        currentManager.setDoubleBufferingEnabled( false );
    }

    /**
     * Enables double buffering on the component.
     * 
     * @param c the component in question.
     */
    public static void enableDoubleBuffering( Component c )
    {
        RepaintManager currentManager = RepaintManager.currentManager( c );
        currentManager.setDoubleBufferingEnabled( true );
    }
}