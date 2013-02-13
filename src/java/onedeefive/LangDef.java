package onedeefive;

/** This class provides an interface that defines all the regex patterns
* for the language supported by 1D5. Any class that needs to use one or more
* of these patterns can either 'implement' this class or import as usual.
*/
public interface LangDef
{
/* +++++++++++++++   UTILTY DEFINITIONS +++++++++++++++++++++ */
    /** The standard separator for tokens after a script has been cleaned */
    public static final String TOKEN_SEPARATOR = " ";
    
    /** The separator for fixed die rolls. */
    public static final String DIE_SEPARATOR = "[dD]";
    
    /** The pattern that sarts the beginning of a comment */
    public static final String COMMENT_STRING = "#";
    
    /** Number format for fixed die rolls. */
    public static final String NUMBER_STRING = "[1-9][0-9]*";
    
    /** Pattern string for variable names */
    public static final String VARIABLE_STRING = "_[a-zA-Z0-9][a-zA-Z0-9_]{0,19}";
    
    /** Pattern String for function names */
    public static final String FUNCTION_STRING = "@[a-zA-Z0-9_]+";
    
    /** String to match line endings across platforms */
    public static final String ENDLINE_STRING = "\r\n|\n";
    
/* +++++++++++++++   MATH DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the addition operator */
    public static final String ADD_STRING = "\\+";
    
    /** Pattern string for the subtraction operator */
    public static final String SUB_STRING = "-";
    
    /** Pattern string for the multiplication operator */
    public static final String MUL_STRING = "\\*";
    
    /** Pattern string for the division operator */
    public static final String DIV_STRING = "/";
    
    /** Pattern string for the modulo operator */
    public static final String MOD_STRING = "%";
    
    /** Pattern string to match any math operator */
    public static final String MATH_STRING = 
        ADD_STRING + "|" + MUL_STRING + "|" +
        SUB_STRING + "|" + DIV_STRING + "|" + MOD_STRING;
    
/* +++++++++++++++   COMPARISON DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the less than operator */
    public static final String LT_STRING = "<";
    
    /** Pattern string for the greater than operator */
    public static final String GT_STRING = ">";
    
    /** Pattern string for the numeric equality operator */
    public static final String EQ_STRING = "=";
    
    /** Pattern string for the less than or equal operator */
    public static final String LTE_STRING = "<=";
    
    /** Pattern string for the greater than or equal operator */
    public static final String GTE_STRING = ">=";
    
    /** Pattern string for the not equal operator */
    public static final String NEQ_STRING = "!=";
    
    /** Pattern string to match any comparison operator */
    public static final String COMPARE_STRING =
        LT_STRING + "|" + GT_STRING + "|" +
        EQ_STRING + "|" + LTE_STRING + "|" +
        GTE_STRING + "|" + NEQ_STRING;
    
/* +++++++++++++++   BOOLEAN DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the boolean and operator */
    public static final String AND_STRING = "(?i)and(?i)";
    
    /** Pattern string for the boolean or operator */
    public static final String OR_STRING = "(?i)or(?i)";
    
    /** Pattern string for the boolean not operator */
    public static final String NOT_STRING = "(?i)not(?i)";
    
    /** Pattern string for the boolean xor operator */
    public static final String XOR_STRING = "(?i)xor(?i)";
    
    /** Pattern string to match any boolean operator */
    public static final String BOOLEAN_STRING =
        AND_STRING + "|" + OR_STRING + "|" +
        NOT_STRING + "|" + XOR_STRING;
    
/* +++++++++++++++   DICE ROLL DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the dice roll function */
    public static final String DICE_STRING = "(?i)dice(?i)";
    
    /** Pattern string for the fixed dice roll */
    public static final String DICEROLL_STRING = 
        NUMBER_STRING + DIE_SEPARATOR + NUMBER_STRING;
        
    /** Pattern string for any dice roll */
    public static final String ROLL_STRING = 
        DICE_STRING + "|(" + DICEROLL_STRING + ")";
        
/* +++++++++++++++   STACK OPERATION DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the stack popping function */
    public static final String POP_STRING = "(?i)pop(?i)";
    
    /** Pattern string for the stack clearing function */
    public static final String CLEAR_STRING = "(?i)clear(?i)";
    
    /** Pattern string for any stack removal operation functions */
    public static final String STACK_STRING = 
        CLEAR_STRING + "|" + POP_STRING;
    
    /** Pattern string for the duplicate stack top function */
    public static final String DUP_STRING = "(?i)dup(?i)";
    
/* +++++++++++++++   PRINT FUNCTION DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the print endline function */
    public static final String ENDL_STRING = ";";
    
    /** Pattern string for the print number function */
    public static final String OUT_STRING = "\\.";
    
    /** Pattern string for the print explanation function */
    public static final String EXPLAIN_STRING = "\\.[eE]";
    
    /** Pattern string for the peek and print number function */
    public static final String PEEK_STRING = "\\^";
    
    /** Pattern string for any print function */
    public static final String OUTPUT_STRING =
        ENDL_STRING + "|" + OUT_STRING + "|" +
        EXPLAIN_STRING + "|" + PEEK_STRING;
    
/* +++++++++++++++   ASSIGNMENT FUNCTION DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the assign and push function */
    public static final String ASSIGN_STRING = "(?i)assign(?i)";
    
    /** Pattern string for the assign only function */
    public static final String SHOVE_STRING = "(?i)put(?i)";
    
/* +++++++++++++++ GROUP OPERATION FUNCTION DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the create group function */
    public static final String GROUP_STRING = "(?i)group(?i)";
    
    /** Pattern string for the group size function */
    public static final String SIZE_STRING = "(?i)size(?i)";
    
    /** Pattern string for the min selection function */
    public static final String MIN_STRING = "(?i)min(?i)";
    
    /** Pattern string for the max selection function */
    public static final String MAX_STRING = "(?i)max(?i)";
    
/* +++++++++++++++   LOOP DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the begin loop statement */
    public static final String LOOP_STRING = "(?i)repeat(?i)";
    
    /** Pattern string for the end loop statement */
    public static final String ENDLOOP_STRING = "(?i)end repeat(?i)";
    
/* +++++++++++++++   FUNCTION CREATION DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the begin function definition statement */
    public static final String DEFUN_STRING =
        "(?i)fun(?i)" + TOKEN_SEPARATOR + FUNCTION_STRING;
    
    /** Pattern string for the end function definition statement */
    public static final String ENDFUN_STRING = "(?i)end fun(?i)";
    
/* +++++++++++++++   CONDITIONAL DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the begin conditional statement */
    public static final String IF_STRING = "(?i)if(?i)";
    
    /** Pattern string for the end conditional statement */
    public static final String ENDIF_STRING = "(?i)end if(?i)";
    
/* +++++++++++++++   FILE INCLUSION DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the include text file statement */
    public static final String INCLUDE_STRING =
    "(?i)include(?i)" + TOKEN_SEPARATOR + "[\\.a-zA-Z0-9_/:]+";
    
/* +++++++++++++++   PRINT STATEMENT DEFINITIONS +++++++++++++++++++++ */
    /** Pattern string for the print statement */
    public static final String PRINT_STRING = "(?i)print(?i)( .+)?";
    
    /** Length of the constant print statement header,
    * This number is used to measure the first character to print, or decide
    * if a line return should be output instead.
    */
    public static final int PRINT_LENGTH = 6;
}
