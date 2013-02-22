## @package LangDef
# @brief String pattern constants
#
# This module includes all the required string Patterns for defining the basic
# language that 1D5 supports.
#

# +++++++++++++++   UTILTY DEFINITIONS +++++++++++++++++++++
## @brief The standard separator for tokens after a script has been cleaned
TOKEN_SEPARATOR = " "

## @brief The separator for fixed die rolls.
DIE_SEPARATOR = "[dD]"

## @brief The Pattern that sarts the beginning of a comment
COMMENT_STRING = "#"

## @brief Number format for fixed die rolls.
NUMBER_STRING = "[1-9][0-9]*"

## @brief Integer format for reading regular numbers
INTEGER_STRING = "^-?(0|([1-9][0-9]*))$"

## @brief Pattern string for variable names
VARIABLE_STRING = "^_[a-zA-Z0-9][a-zA-Z0-9_]{0,19}$"

## @brief Pattern String for function names
FUNCTION_STRING = "@[a-zA-Z0-9_]+"

## @brief String to match line endings across platforms
ENDLINE_STRING = "\r\n|\n"



# +++++++++++++++   MATH DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the addition operator
ADD_STRING = "^\\+$"

## @brief Pattern string for the subtraction operator
SUB_STRING = "^-$"

## @brief Pattern string for the multiplication operator
MUL_STRING = "^\\*$"

## @brief Pattern string for the division operator
DIV_STRING = "^/$"

## @brief Pattern string for the modulo operator
MOD_STRING = "^%$"

## @brief Pattern string to match any math operator
MATH_STRING = (ADD_STRING + "|" + MUL_STRING + "|" +
              SUB_STRING + "|" + DIV_STRING + "|" + MOD_STRING)



# +++++++++++++++   COMPARISON DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the less than operator
LT_STRING = "^<$"

## @brief Pattern string for the greater than operator
GT_STRING = "^>$"

## @brief Pattern string for the numeric equality operator
EQ_STRING = "^=$"

## @brief Pattern string for the less than or equal operator
LTE_STRING = "^<=$"

## @brief Pattern string for the greater than or equal operator
GTE_STRING = "^>=$"

## @brief Pattern string for the not equal operator
NEQ_STRING = "^!=$"

## @brief Pattern string to match any comparison operator
COMPARE_STRING = (LT_STRING + "|" + GT_STRING + "|" +
                 EQ_STRING + "|" + LTE_STRING + "|" +
                 GTE_STRING + "|" + NEQ_STRING)



# +++++++++++++++   BOOLEAN DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the boolean and operator
AND_STRING = "^(?i)and(?i)$"

## @brief Pattern string for the boolean or operator
OR_STRING = "^(?i)or(?i)$"

## @brief Pattern string for the boolean not operator
NOT_STRING = "^(?i)not(?i)$"

## @brief Pattern string for the boolean xor operator
XOR_STRING = "^(?i)xor(?i)$"

## @brief Pattern string to match any boolean operator
BOOLEAN_STRING = (AND_STRING + "|" + OR_STRING + "|" +
                 NOT_STRING + "|" + XOR_STRING)



# +++++++++++++++   DICE ROLL DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the dice roll function
DICE_STRING = "^(?i)dice(?i)$"

## @brief Pattern string for the fixed dice roll
DICEROLL_STRING = "^" + NUMBER_STRING + DIE_SEPARATOR + NUMBER_STRING + "$"

## @brief Pattern string for any dice roll
ROLL_STRING = DICE_STRING + "|(" + DICEROLL_STRING + ")"



# +++++++++++++++   STACK OPERATION DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the stack popping function
POP_STRING = "^(?i)pop(?i)$"

## @brief Pattern string for the stack clearing function
CLEAR_STRING = "^(?i)clear(?i)$"

## @brief Pattern string for any stack removal operation functions
STACK_STRING = CLEAR_STRING + "|" + POP_STRING

## @brief Pattern string for the duplicate stack top function
DUP_STRING = "^(?i)dup(?i)$"



# +++++++++++++++   PRINT FUNCTION DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the print endline function
ENDL_STRING = "^;$"

## @brief Pattern string for the print number function
OUT_STRING = "^\\.$"

## @brief Pattern string for the print explanation function
EXPLAIN_STRING = "^\\.[eE]$"

## @brief Pattern string for the peek and print number function
PEEK_STRING = "^\\^$"

## @brief Pattern string for any print function
OUTPUT_STRING = (ENDL_STRING + "|" + OUT_STRING + "|" +
                EXPLAIN_STRING + "|" + PEEK_STRING)



# +++++++++++++++   ASSIGNMENT FUNCTION DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the assign and push function
ASSIGN_STRING = "^(?i)assign(?i)$"

## @brief Pattern string for the assign only function
SHOVE_STRING = "^(?i)put(?i)$"



# +++++++++++++++ GROUP OPERATION FUNCTION DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the create group function
GROUP_STRING = "^(?i)group(?i)$"

## @brief Pattern string for the group size function
SIZE_STRING = "^(?i)size(?i)$"

## @brief Pattern string for the min selection function
MIN_STRING = "^(?i)min(?i)$"

## @brief Pattern string for the max selection function
MAX_STRING = "^(?i)max(?i)$"



# +++++++++++++++   LOOP DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the begin loop statement
LOOP_STRING = "^(?i)repeat(?i)$"

## @brief Pattern string for the end loop statement
ENDLOOP_STRING = "^(?i)end repeat(?i)$"



# +++++++++++++++   FUNCTION CREATION DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the begin function definition statement
DEFUN_STRING = "^(?i)fun(?i)" + TOKEN_SEPARATOR + FUNCTION_STRING + "$"

## @brief Pattern string for the end function definition statement
ENDFUN_STRING = "^(?i)end fun(?i)$"



# +++++++++++++++   CONDITIONAL DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the begin conditional statement
IF_STRING = "^(?i)if(?i)$"

## @brief Pattern string for the end conditional statement
ENDIF_STRING = "^(?i)end if(?i)$"



# +++++++++++++++   FILE INCLUSION DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the include text file statement
INCLUDE_STRING = "^(?i)include(?i)" + TOKEN_SEPARATOR + "[\\.a-zA-Z0-9_/:]+$"



# +++++++++++++++   PRINT STATEMENT DEFINITIONS +++++++++++++++++++++
## @brief Pattern string for the print statement
PRINT_STRING = "^[ \t]*(?i)print(?i)( .+)?$"

## @brief Print header string length.
#
# Length of the constant print statement header,
# This number is used to measure the first character to print, or decide
# if a line return should be output instead.
PRINT_LENGTH = 6

