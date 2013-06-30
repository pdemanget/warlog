package fr.warlog.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some utilities for command-line application.
 * 
 * MainHelper loads commmand line arguments AND conf.properties in resources and current filepath.
 * 
 */
public class MainUtils {

    protected static final String UTF_8 = "UTF-8";

    public static boolean isWindows() {
        return System.getProperty( "os.name" ).toLowerCase().indexOf( "win" ) >= 0;
    }

    /* ======== FILEUTILS SECTION ==============*/

    public static class WildCardFileFilter implements FilenameFilter, FileFilter {

        private String _pattern;

        public WildCardFileFilter( String pattern ) {
            _pattern = pattern.replace( ".", "\\." );
            _pattern = pattern.replace( "*", ".*" ).replace( "?", "." );
        }

        public boolean accept( File file ) {
            return Pattern.compile( _pattern ).matcher( file.getName() ).find();
        }

        public boolean accept( File pDir, String pName ) {
            logError( "filter ", pName );
            return Pattern.compile( _pattern, isWindows() ? Pattern.CASE_INSENSITIVE : 0 ).matcher( pName ).find();
        }
    }

    /**
     * Resolve path with a wildcard or a comma.
     * example: file1,file2,file3 or xxx*.ext 
     */
    public static String[] listFiles( String pWildcardPath ) {
        if ( pWildcardPath.contains( "," ) ) {
            String[] files = pWildcardPath.split( "," );
            return files;
        }
        if ( pWildcardPath.contains( "*" ) ) {

            return dirList( pWildcardPath );
        }
        return new String[] { pWildcardPath };
    }

    private static String[] dirList( String fname ) {
        if ( isWindows() ) {
            fname = fname.replace( '\\', '/' );
        }
        String dirPath = "";
        if ( fname.contains( "/" ) ) {
            int lPosFilename = fname.lastIndexOf( "/" );
            dirPath = fname.substring( 0, lPosFilename + 1 );
            fname = fname.substring( lPosFilename + 1 );
        }
        File dir = new File( dirPath );
        return dir.list( new WildCardFileFilter( fname ) );
    }

    /**
     * Contraire du String_split.
     */
    public static String merge( String[] src, String sep ) {
        StringBuffer result = new StringBuffer();
        String lsep = "";
        for ( String s : src ) {
            result.append( lsep );
            result.append( s );
            lsep = sep;
        }
        return result.toString();
    }

    /*========================= END OF FILEUTILS SECTINO =======================*/

    /**
     * parse arguments nix-style.
     */
    public static Map<String,String> parseArgs( String[] args ) {
        Map<String,String> param = new HashMap<String,String>();
        String otherArgs = "";
        String sep = "";
        for ( int i = 0 ; i < args.length - 1 ; i++ ) {
            if ( args[ i ].startsWith( "--" ) ) {
                param.put( args[ i ].substring( 2 ), "" );
            }
            else if ( args[ i ].startsWith( "-" ) ) {
                param.put( args[ i ].substring( 1 ), args[ i + 1 ] );
                i++;
            }
            else {
                otherArgs += sep + args[ i ];//bouh, c'est mal, tant pis.
                sep = ",";
            }
        }
        if (args.length > 0) {
          if (args[args.length - 1].startsWith("--")) {
    
            param.put(args[args.length - 1].substring(2), "");
          } else {
            otherArgs += sep + args[args.length - 1];// bouh, c'est mal, tant pis.
            sep = ",";
          }
        }
        param.put( "zargs", otherArgs );
        return param;

    }

    public static Properties loadProperties( String pProp ) {
        if ( pProp == null ) {
            pProp = "conf.properties";
        }
        Properties lProp = new Properties();
        ClassLoader lClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            InputStream lResourceAsStream = lClassLoader.getResourceAsStream( "conf.properties" );
            if ( lResourceAsStream != null ) {
                lProp.load( lResourceAsStream );
            }
        }
        catch ( FileNotFoundException e ) {
            logError( "Warning, expected properties file:", pProp );
        }
        catch ( IOException e ) {
            logError( "IOException :", lProp, e );
        }
        return lProp;
    }

    public static Properties loadPropertiesFile( String pProp ) {
        if ( pProp == null ) {
            pProp = "conf.properties";
        }
        Properties lProp = new Properties();
        try {
            lProp.load( new FileInputStream( pProp ) );
        }
        catch ( FileNotFoundException e ) {
            logError( "Warning, seeking file:", pProp );
        }
        catch ( IOException e ) {
            logError( "IOException :", lProp, e );
        }
        return lProp;
    }

    private static StringBuilder getMsg( Object... pObjects ) {
        StringBuilder lMsg = new StringBuilder();
        for ( Object o : pObjects ) {
            if ( o != null ) {
                if ( o instanceof Throwable ) {
                    Throwable e = (Throwable)o;
                    StringWriter lStack = new StringWriter();
                    e.printStackTrace( new PrintWriter( lStack ) );
                    lMsg.append( e.getClass().getSimpleName() );
                    lMsg.append( ":" );
                    lMsg.append( e.getMessage() );
                    lMsg.append( '\n' );
                    lMsg.append( lStack );
                }
                lMsg.append( o );
                lMsg.append( ' ' );
            }
        }
        return lMsg;
    }

    public static void logError( Object... pObjects ) {
        System.err.println( getMsg( pObjects ) );
    }

    /**
     * Ecrit un fichier (initulis� cf. MessageFormat)
     */
    protected void write( String pOut, byte[] pData ) {
        if ( pOut == null ) {
            pOut = "out.xml";
        }

        try {
            FileOutputStream lOut;
            lOut = new FileOutputStream( pOut );
            lOut.write( pData );
        }
        catch ( FileNotFoundException e ) {
            logError( "File not found ", pOut, e );
            return;
        }
        catch ( IOException e ) {
            logError( "File not writable ", pOut, e );
            return;
        }

        //done
        logError( "File created:", pOut );
    }

    /**
         * inutilis� (copi� dans MessageFormat)
         */
    protected Map<String,String> parseVars( String pVar ) {
        logError( "var:", pVar );
        String[] vars = pVar.split( "," );
        Map<String,String> result = new HashMap<String,String>();
        for ( String var : vars ) {
            String[] vals = var.split( "=" );
            if ( vals.length >= 2 ) {
                result.put( vals[ 0 ], vals[ 1 ] );
            }
        }
        return result;
    }

    /**
     * Lit le fichier � envoyer (jms ou MQ pour l'instant)
     */
    public static String readFile( String pMsg ) {
        StringBuffer result = new StringBuffer();
        BufferedReader lBis = null;
        try {
            String line;

            lBis = new BufferedReader( new InputStreamReader( new FileInputStream( pMsg ), UTF_8 ) );
            while ( ( line = lBis.readLine() ) != null ) {
                result.append( line );
                result.append( "\n" );
            }

        }
        catch ( IOException e ) {
            logError( "Can't read file ", pMsg, e );
        }
        finally {
            try {
                if ( lBis != null ) {
                    lBis.close();
                }

            }
            catch ( IOException e ) {
                logError( "Can't close file ", pMsg, e );
            }
        }

        return result.toString();
    }

    /**
     * inutilis� (copi� dans MessageFormat)
     */
    protected byte[] readReplace( String pMsg, Map<String,String> pVars ) {
        byte[] lData = null;
        ByteArrayOutputStream lBaos = new ByteArrayOutputStream( 1024 );
        BufferedReader lBis = null;

        try {
            final byte[] CRLF = "\n".getBytes( UTF_8 );
            String line;

            lBis = new BufferedReader( new InputStreamReader( new FileInputStream( pMsg ), UTF_8 ) );
            while ( ( line = lBis.readLine() ) != null ) {
                if ( line.matches( ".*\\$\\{[^\\}]+\\}.*" ) ) {
                    String pattern = "\\$\\{([^\\}]+)\\}";
                    Pattern pat = Pattern.compile( pattern );
                    Matcher mat = pat.matcher( line );
                    int lPrevMatch = 0;
                    while ( mat.find() ) {
                        String lVar = mat.group( 1 );
                        String lReplace = pVars.get( lVar );
                        if ( lReplace == null ) {
                            logError( "Undeclared variable:", lVar );
                            lReplace = "UNDEFINED";
                        }
                        //output
                        lBaos.write( line.substring( lPrevMatch, mat.start() ).getBytes( UTF_8 ) );
                        lBaos.write( lReplace.getBytes( UTF_8 ) );
                        lPrevMatch = mat.end();
                    }
                    //end of the line
                    lBaos.write( line.substring( lPrevMatch ).getBytes( UTF_8 ) );
                    lBaos.write( CRLF );
                }
                else {
                    lBaos.write( line.getBytes( UTF_8 ) );
                    lBaos.write( CRLF );
                }

            }
            lData = lBaos.toByteArray();
        }
        catch ( IOException e ) {
            logError( "Can't read file ", pMsg, e );
        }
        finally {
            try {
                if ( lBis != null ) {
                    lBis.close();
                }

                lBaos.close();
            }
            catch ( IOException e ) {
                logError( "Can't close file ", pMsg, e );
            }
        }

        return lData;

    }

    public static void writeFile( String pOut, String pData, boolean pAppend ) {
        if ( pOut == null ) {
            pOut = "out.xml";
        }

        try {
            FileWriter lOut;
            lOut = new FileWriter( pOut, pAppend );
            lOut.write( pData );
            lOut.close();
        }
        catch ( FileNotFoundException e ) {
            MainUtils.logError( "File not found ", pOut, e );
            return;
        }
        catch ( IOException e ) {
            MainUtils.logError( "File not writable ", pOut, e );
            return;
        }

        //done
        MainUtils.logError( "File created:", pOut );
    }

    public static void showUsage( String pAppName, String pUsage1, String pUsage2 ) {
        pAppName = ( pAppName + "                     " ).substring( 0, 20 );
        pUsage1 = ( pUsage1 + "                                                                                 " )
            .substring( 0, 75 );
        pUsage2 = ( pUsage2 + "                                                                                 " )
            .substring( 0, 75 );
        if ( isWindows() ) {
            logError(

            "����������������������������������������������������������������������������Ŀ\n"
                + "�                     " + pAppName + "  � SLIB 2010                      �\n"
                + "� Usage :                                   ��������������������������������Ĵ\n"

                + "� " + pUsage1 + "�\n" + "� " + pUsage2 + "�\n"
                + "������������������������������������������������������������������������������\n" );
        }
        else {
            logError(

            "/----------------------------------------------------------------------------\\\n" + "|                  "
                + pAppName + "     | SLIB 2010                      |\n"
                + "| Usage :                                   \\--------------------------------|\n" + "| " + pUsage1
                + "|\n" + "| " + pUsage2 + "|\n"
                + "\\----------------------------------------------------------------------------/\n" );
        }

    }

    public static void putAll( Map<String,String> pDest, Properties pProp ) {
        for ( Object lO : pProp.keySet() ) {
            pDest.put( (String)lO, (String)pProp.get( lO ) );
        }
    }

    /**
     * Main delegation for parsing arguments and display usage.
     *  2 default arguments:
     *      -conf file the loaded configuration file
     *      --help display usage
     *      
     *      zargs contains unnames arguments.
     */
    public static Map<String,String> mainHelper( String[] args, String pAppName, String pUsage1, String pUsage2 ) {
        try {
            Map<String,String> lParseArgs = MainUtils.parseArgs( args );
            Map<String,String> lParam = new HashMap<String,String>();
            Properties lLoadProperties = MainUtils.loadProperties( lParseArgs.get( "conf" ) );
            Properties lLoadProperties2 = MainUtils.loadPropertiesFile( lParseArgs.get( "conf" ) );

            putAll( lParam, lLoadProperties );
            putAll( lParam, lLoadProperties2 );//les valeurs du fichiers �crasent celle du properties
            lParam.putAll( lParseArgs );//les arguments du programme �crase les valeurs des fichiers.
            if ( lParseArgs.get( "help" ) != null ) {
                MainUtils.showUsage( pAppName, pUsage1, pUsage2 );
                return null;
            }
            return lParam;

        }
        catch ( Exception e ) {
            MainUtils.showUsage( pAppName, pUsage1, pUsage2 );
            MainUtils.logError( e );
            return null;
        }

    }
}
