package onedeefive;

public interface LangDef
{
    public static final String TOKEN_SEPARATOR = " ";
    public static final String DIE_SEPARATOR = "[dD]";
    public static final String COMMENT_STRING = "#";
    public static final String NUMBER_STRING = "[1-9][0-9]*";
    public static final String VARIABLE_STRING = "_[a-zA-Z0-9][a-zA-Z0-9_]{0,19}";
    public static final String FUNCTION_STRING = "@[a-zA-Z0-9_]+";
    public static final String ENDLINE_STRING = "\n|\r\n";
    
    public static final String ADD_STRING = "\\+";
    public static final String SUB_STRING = "-";
    public static final String MUL_STRING = "\\*";
    public static final String DIV_STRING = "/";
    public static final String MOD_STRING = "%";
    public static final String MATH_STRING = 
        ADD_STRING + "|" + MUL_STRING + "|" +
        SUB_STRING + "|" + DIV_STRING + "|" + MOD_STRING;
    
    public static final String LT_STRING = "<";
    public static final String GT_STRING = ">";
    public static final String EQ_STRING = "=";
    public static final String LTE_STRING = "<=";
    public static final String GTE_STRING = ">=";
    public static final String NEQ_STRING = "!=";
    public static final String COMPARE_STRING =
        LT_STRING + "|" + GT_STRING + "|" +
        EQ_STRING + "|" + LTE_STRING + "|" +
        GTE_STRING + "|" + NEQ_STRING;
    
    public static final String AND_STRING = "(?i)and(?i)";
    public static final String OR_STRING = "(?i)or(?i)";
    public static final String NOT_STRING = "(?i)not(?i)";
    public static final String XOR_STRING = "(?i)xor(?i)";
    public static final String BOOLEAN_STRING =
        AND_STRING + "|" + OR_STRING + "|" +
        NOT_STRING + "|" + XOR_STRING;
    
    public static final String DICE_STRING = "(?i)dice(?i)";
    public static final String DICEROLL_STRING = 
        NUMBER_STRING + DIE_SEPARATOR + NUMBER_STRING;
    public static final String ROLL_STRING = 
        DICE_STRING + "|(" + DICEROLL_STRING + ")";
        
    public static final String POP_STRING = "(?i)pop(?i)";
    public static final String CLEAR_STRING = "(?i)clear(?i)";
    public static final String STACK_STRING = 
        CLEAR_STRING + "|" + POP_STRING;
    
    public static final String ENDL_STRING = ";";
    public static final String OUT_STRING = "\\.";
    public static final String EXPLAIN_STRING = "\\.[eE]";
    public static final String PEEK_STRING = "\\^";
    public static final String OUTPUT_STRING =
        ENDL_STRING + "|" + OUT_STRING + "|" +
        EXPLAIN_STRING + "|" + PEEK_STRING;
    
    public static final String ASSIGN_STRING = "(?i)assign(?i)";
    public static final String SHOVE_STRING = "(?i)put(?i)";
    
    public static final String GROUP_STRING = "(?i)group(?i)";
    public static final String SIZE_STRING = "(?i)size(?i)";
    public static final String MIN_STRING = "(?i)min(?i)";
    public static final String MAX_STRING = "(?i)max(?i)";
    public static final String DUP_STRING = "(?i)dup(?i)";
    
    public static final String LOOP_STRING = "(?i)repeat(?i)";
    public static final String ENDLOOP_STRING = "(?i)end repeat(?i)";
    
    public static final String DEFUN_STRING =
        "(?i)fun(?i)" + TOKEN_SEPARATOR + FUNCTION_STRING;
    public static final String ENDFUN_STRING = "(?i)end fun(?i)";
    
    public static final String IF_STRING = "(?i)if(?i)";
    public static final String ENDIF_STRING = "(?i)end if(?i)";
    
    public static final String INCLUDE_STRING =
    "(?i)include(?i)" + TOKEN_SEPARATOR + "[\\.a-zA-Z0-9_/:]+";
    
    public static final String PRINT_STRING = "(?i)print(?i)( .+)?";
    public static final int PRINT_LENGTH = 6;
    
    //public static final String DUP_STRING = "DUP";
}
