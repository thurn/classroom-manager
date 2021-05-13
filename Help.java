package ws.thurn.dossier;

import java.io.IOException;

/**
 * The Help class produces help for the program in a number of ways depending on
 * what parameter it was passed.
 * 
 * @author Derek Thurn
 */
public class Help
{

    String typeOfHelp;

    // The type of help desired.

    /**
     * Creates the help object and activates the right help process
     * 
     * @pre a correct type of help is passed
     * @post the corresponding action is taken
     * @param hlp the type of help desired
     */
    public Help( String hlp )
    {
        typeOfHelp = hlp;
        help();
    }

    /**
     * Either displays a quick prompt or loads the User Manual
     * 
     * @pre one of the four types of help has been declared, the correct files
     *      are all in place.
     * @post either a prompt is displayed or one of the User Manual formats is
     *       loaded.
     */
    public void help()
    {
        try
        {
            if( typeOfHelp.equals( "start" ) )
            {
                GradeBook
                        .info( "To get started, create a class by pressing the class button. You can then\nadd students to the class. After this, create assignments for the class\nusing the Assignments button, then enter grades for the class using the\nGrades button. When you're finished, the Reports button can be used to\nview the information. See the guide for more comprehensive user information." );
            }
            if( typeOfHelp.equals( "doc" ) )
            {
                Runtime.getRuntime().exec( "doc.bat" );
            }
            if( typeOfHelp.equals( "pdf" ) )
            {
                Runtime.getRuntime().exec( "pdf.bat" );
            }
            if( typeOfHelp.equals( "html" ) )
            {
                Runtime.getRuntime().exec( "html.bat" );
            }
        }
        catch( IOException e )
        {
            GradeBook
                    .error( "Problem finding the required file. Please reinstall the program." );
        }

    }

}
