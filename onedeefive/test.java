package onedeefive;


import java.util.*;
import java.io.*;

/** A class for testing the reliability and correctness of the
* standard functions and control structrues provided with 1D5.
*/
public class test
{
    public static void main(String[] args)
    {
        test_integer();
        System.out.println();
        
        test_math();
        System.out.println();
        
        test_compare();
        System.out.println();
        
        test_boolean();
        System.out.println();
        
        test_dice();
        System.out.println();
        
        test_group();
        System.out.println();
        
        test_min_max();
        System.out.println();
        
        test_size();
        System.out.println();
        
        test_assign();
        System.out.println();
        
        test_dup();
        System.out.println();
        
        test_print();
        System.out.println();
        
        test_loop();
        System.out.println();
        
        test_if();
        System.out.println();
        
        test_function();
        System.out.println();
        
        test_include();
        System.out.println();
    }
    
/*$$$$$$$$$$$$$$$$$$$$$$$$$ TEST SECTORS $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
    public static void test_integer()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("0")).execute(stack);
        passed &= test(stack,0,"zero integer");
        
        (new Script("-10")).execute(stack);
        passed &= test(stack,-10,"negative");
        
        (new Script("100")).execute(stack);
        passed &= test(stack,100,"positive");
        
        System.out.println("--- Integer test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_math()
    {
        boolean passed = true;
        Stack<StackFrame> stack = new Stack<StackFrame>();
        
        (new Script("1 1 +")).execute(stack);
        passed &= test(stack,2,"add 001");
        
        (new Script("1 100 +")).execute(stack);
        passed &= test(stack,101,"add 002");
        
        (new Script("1 -1 +")).execute(stack);
        passed &= test(stack,0,"add 003");
        
        (new Script("0 0 +")).execute(stack);
        passed &= test(stack,0,"add 004");
        
        (new Script("-1000 -10000 +")).execute(stack);
        passed &= test(stack,-11000,"add 005");
        
        (new Script("1 1 + 4 +")).execute(stack);
        passed &= test(stack,6,"add 001");
        
        (new Script("1 1 -")).execute(stack);
        passed &= test(stack,0,"sub 001");
        
        (new Script("1 -1 -")).execute(stack);
        passed &= test(stack,2,"sub 002");
        
        (new Script("-1 1 -")).execute(stack);
        passed &= test(stack,-2,"sub 003");
        
        (new Script("1 7 *")).execute(stack);
        passed &= test(stack,7,"mul 001");
        
        (new Script("2 8 *")).execute(stack);
        passed &= test(stack,16,"mul 002");
        
        (new Script("87 -1 *")).execute(stack);
        passed &= test(stack,-87,"mul 003");
        
        (new Script("-50 -5 *")).execute(stack);
        passed &= test(stack,250,"mul 004");
        
        (new Script("10 1 /")).execute(stack);
        passed &= test(stack,10,"div 001");
        
        (new Script("10 1 /")).execute(stack);
        passed &= test(stack,10,"div 002");
        
        (new Script("10 2 /")).execute(stack);
        passed &= test(stack,5,"div 003");
        
        (new Script("27 6 /")).execute(stack);
        passed &= test(stack,4,"div 004");
        
        (new Script("30 -6 /")).execute(stack);
        passed &= test(stack,-5,"div 003");
        
        (new Script("10 1 %")).execute(stack);
        passed &= test(stack,0,"mod 001");
        
        (new Script("97 10 %")).execute(stack);
        passed &= test(stack,7,"mod 002");
        
        (new Script("11 2 %")).execute(stack);
        passed &= test(stack,1,"mod 003");
        
        System.out.println("--- Math test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_compare()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("1 2 <")).execute(stack);
        passed &= test(stack,1,"LT 1");
        
        (new Script("-8 20 <")).execute(stack);
        passed &= test(stack,1,"LT 2");
        
        (new Script("8 8 <")).execute(stack);
        passed &= test(stack,0,"LT 3");
        
        (new Script("9 -2 <")).execute(stack);
        passed &= test(stack,0,"LT 4");
        
        (new Script("9 8 <")).execute(stack);
        passed &= test(stack,0,"LT 5");
        
        (new Script("0 0 <")).execute(stack);
        passed &= test(stack,0,"LT 6");
        
        (new Script("1 2 >")).execute(stack);
        passed &= test(stack,0,"GT 1");
        
        (new Script("-8 20 >")).execute(stack);
        passed &= test(stack,0,"GT 2");
        
        (new Script("8 8 >")).execute(stack);
        passed &= test(stack,0,"GT 3");
        
        (new Script("9 -2 >")).execute(stack);
        passed &= test(stack,1,"GT 4");
        
        (new Script("9 8 >")).execute(stack);
        passed &= test(stack,1,"GT 5");
        
        (new Script("0 0 >")).execute(stack);
        passed &= test(stack,0,"GT 6");
        
        (new Script("1 2 =")).execute(stack);
        passed &= test(stack,0,"EQ 1");
        
        (new Script("-8 20 =")).execute(stack);
        passed &= test(stack,0,"EQ 2");
        
        (new Script("8 8 =")).execute(stack);
        passed &= test(stack,1,"EQ 3");
        
        (new Script("9 -2 =")).execute(stack);
        passed &= test(stack,0,"EQ 4");
        
        (new Script("9 8 =")).execute(stack);
        passed &= test(stack,0,"EQ 5");
        
        (new Script("0 0 =")).execute(stack);
        passed &= test(stack,1,"EQ 6");
        
        (new Script("1 2 <=")).execute(stack);
        passed &= test(stack,1,"LTE 1");
        
        (new Script("-8 20 <=")).execute(stack);
        passed &= test(stack,1,"LTE 2");
        
        (new Script("8 8 <=")).execute(stack);
        passed &= test(stack,1,"LTE 3");
        
        (new Script("9 -2 <=")).execute(stack);
        passed &= test(stack,0,"LTE 4");
        
        (new Script("9 8 <=")).execute(stack);
        passed &= test(stack,0,"LTE 5");
        
        (new Script("0 0 <=")).execute(stack);
        passed &= test(stack,1,"LTE 6");
        
        (new Script("1 2 >=")).execute(stack);
        passed &= test(stack,0,"GTE 1");
        
        (new Script("-8 20 >=")).execute(stack);
        passed &= test(stack,0,"GTE 2");
        
        (new Script("8 8 >=")).execute(stack);
        passed &= test(stack,1,"GTE 3");
        
        (new Script("9 -2 >=")).execute(stack);
        passed &= test(stack,1,"GTE 4");
        
        (new Script("9 8 >=")).execute(stack);
        passed &= test(stack,1,"GTE 5");
        
        (new Script("0 0 >=")).execute(stack);
        passed &= test(stack,1,"GTE 6");
        
        (new Script("1 2 !=")).execute(stack);
        passed &= test(stack,1,"NEQ 1");
        
        (new Script("-8 20 !=")).execute(stack);
        passed &= test(stack,1,"NEQ 2");
        
        (new Script("8 8 !=")).execute(stack);
        passed &= test(stack,0,"NEQ 3");
        
        (new Script("9 -2 !=")).execute(stack);
        passed &= test(stack,1,"NEQ 4");
        
        (new Script("9 8 !=")).execute(stack);
        passed &= test(stack,1,"NEQ 5");
        
        (new Script("0 0 !=")).execute(stack);
        passed &= test(stack,0,"NEQ 6");
        
        System.out.println("--- Comparison test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_boolean()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("1 not")).execute(stack);
        passed &= test(stack,0,"NOT 1");
        
        (new Script("6 not")).execute(stack);
        passed &= test(stack,0,"NOT 2");
        
        (new Script("-5 not")).execute(stack);
        passed &= test(stack,0,"NOT 3");
        
        (new Script("0 not")).execute(stack);
        passed &= test(stack,1,"NOT 4");
        
        (new Script("0 0 and")).execute(stack);
        passed &= test(stack,0,"AND 1");
        
        (new Script("0 9 and")).execute(stack);
        passed &= test(stack,0,"AND 2");
        
        (new Script("-13 0 AND")).execute(stack);
        passed &= test(stack,0,"AND 3");
        
        (new Script("45 1 and")).execute(stack);
        passed &= test(stack,1,"AND 4");
        
        (new Script("0 0 OR")).execute(stack);
        passed &= test(stack,0,"OR 1");
        
        (new Script("0 2 or")).execute(stack);
        passed &= test(stack,1,"OR 2");
        
        (new Script("-18 0 OR")).execute(stack);
        passed &= test(stack,1,"OR 3");
        
        (new Script("-57 1000 or")).execute(stack);
        passed &= test(stack,1,"OR 4");
        
        (new Script("0 0 XOR")).execute(stack);
        passed &= test(stack,0,"XOR 1");
        
        (new Script("0 598 xor")).execute(stack);
        passed &= test(stack,1,"XOR 2");
        
        (new Script("-1 0 XoR")).execute(stack);
        passed &= test(stack,1,"XOR 3");
        
        (new Script("-90 1 xOr")).execute(stack);
        passed &= test(stack,0,"XOR 4");
        
        System.out.println("--- Boolean test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_dice()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("1d6")).execute(stack);
        passed &= test(stack,1,6,1,"xdx 1");
        
        (new Script("1d1000")).execute(stack);
        passed &= test(stack,1,1000,1,"xdx 2");
        
        (new Script("100d6")).execute(stack);
        passed &= test(stack,1,6,100,"xdx 3");
        
        (new Script("20d1")).execute(stack);
        passed &= test(stack,1,1,20,"xdx 4");
        
        (new Script("500d2")).execute(stack);
        passed &= test(stack,1,2,500,"xdx 5");
        
        (new Script("1 6 dice")).execute(stack);
        passed &= test(stack,1,6,1,"dice 1");
        
        (new Script("0 6 dice")).execute(stack);
        passed &= test(stack,0,0,1,"dice 2");
        
        (new Script("1 1 dice")).execute(stack);
        passed &= test(stack,1,1,1,"dice 3");
        
        (new Script("100 60 dice")).execute(stack);
        passed &= test(stack,1,60,100,"dice 4");
        
        (new Script("1000 12 dice")).execute(stack);
        passed &= test(stack,1,12,1000,"dice 5");
        
        System.out.println("--- Dice test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_group()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("1d6 5d8 2 3 group")).execute(stack);
        passed &= test(stack,1,8,7,"GROUP 1");
        
        (new Script("10d8 1 group")).execute(stack);
        passed &= test(stack,1,8,10,"GROUP 2");
        
        (new Script("0 group")).execute(stack);
        passed &= test(stack,0,0,1,"GROUP 3");
        
        (new Script("-2000 group")).execute(stack);
        passed &= test(stack,0,0,1,"GROUP 4");
        
        (new Script("1 2 3 4 5 6 7 8 9 0 7 group")).execute(stack);
        passed &= test(stack,0,9,7,"GROUP 5");
        
        (new Script("3 4 dice 5 6 dice 2 group")).execute(stack);
        passed &= test(stack,1,6,8,"GROUP 6");
        
        System.out.println("---  Group test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_min_max()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("10d6 5 min")).execute(stack);
        passed &= test(stack,1,6,5,"min 1");
        
        (new Script("10d6 12 min")).execute(stack);
        passed &= test(stack,1,6,10,"min 2");
        
        (new Script("1 1 MIN")).execute(stack);
        passed &= test(stack,1,1,1,"min 3");
        
        (new Script("1 10 min")).execute(stack);
        passed &= test(stack,1,1,1,"min 4");
        
        (new Script("5d8 -2 min")).execute(stack);
        passed &= test(stack,0,0,1,"min 5");
        
        (new Script("100d8 8 min 4 min")).execute(stack);
        passed &= test(stack,1,8,4,"min 6");
        
        (new Script("0 1 2 3 4 5 6 group 3 min")).execute(stack);
        passed &= test(stack,0,2,3,"min 7");
        
        (new Script("-1 0 1 -1 0 1 -1 0 1 9 group 3 min")).execute(stack);
        passed &= test(stack,-1,-1,3,"min 8");
        
        (new Script("10d6 5 max")).execute(stack);
        passed &= test(stack,1,6,5,"max 1");
        
        (new Script("10d6 12 max")).execute(stack);
        passed &= test(stack,1,6,10,"max 2");
        
        (new Script("1 1 MAX")).execute(stack);
        passed &= test(stack,1,1,1,"max 3");
        
        (new Script("1 10 max")).execute(stack);
        passed &= test(stack,1,1,1,"max 4");
        
        (new Script("5d8 -2 max")).execute(stack);
        passed &= test(stack,0,0,1,"max 5");
        
        (new Script("100d8 8 max 4 max")).execute(stack);
        passed &= test(stack,1,8,4,"max 6");
        
        (new Script("0 1 2 3 4 5 6 group 3 max")).execute(stack);
        passed &= test(stack,3,5,3,"max 7");
        
        (new Script("-1 0 1 -1 0 1 -1 0 1 9 group 3 max")).execute(stack);
        passed &= test(stack,1,1,3,"max 8");
        
        System.out.println("---  Min/Max test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_size()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("0 size")).execute(stack);
        passed &= test(stack,1,"size 1");
        
        (new Script("100 size")).execute(stack);
        passed &= test(stack,1,"size 2");
        
        (new Script("100 30 + size")).execute(stack);
        passed &= test(stack,1,"size 3");
        
        (new Script("1d10 size")).execute(stack);
        passed &= test(stack,1,"size 4");
        
        (new Script("1 2 3 4 4 group size")).execute(stack);
        passed &= test(stack,4,"size 5");
        
        (new Script("-10 group SIZE")).execute(stack);
        passed &= test(stack,1,"size 6");
        
        (new Script("3 4 5 6 7 4 group 2 min size")).execute(stack);
        passed &= test(stack,2,"size 7");
        
        (new Script("1000 5 dice size")).execute(stack);
        passed &= test(stack,1000,"size 8");
        
        System.out.println("--- Size test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_assign()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("5 _tmp1 assign")).execute(stack);
        passed &= test(stack,5,"assign 1");
        
        (new Script("5 _tmp1 assign 6 _tmp1")).execute(stack);
        passed &= test(stack,5,"assign 2");
        
        (new Script("8d6 _tmp assign")).execute(stack);
        passed &= test(stack,1,6,8,"assign 3");
        
        (new Script("7d12 _tmp assign 9 _tmp")).execute(stack);
        passed &= test(stack,1,12,7,"assign 4");
        
        (new Script("0 1 2 3 4 group 2 max _tmp1 assign")).execute(stack);
        passed &= test(stack,2,3,2,"assign 5");
        
        (new Script("0 1 2 3 4 group 2 max _tmp1 assign 0 _tmp1")).execute(stack);
        passed &= test(stack,2,3,2,"assign 6");
        
        (new Script("8 7 + _funnyName assign")).execute(stack);
        passed &= test(stack,15,"assign 7");
        
        (new Script("8 7 + _funnyName assign 0 _funnyName")).execute(stack);
        passed &= test(stack,15,"assign 8");
        
        (new Script("_blergh")).execute(stack);
        passed &= test(stack,0,"assign 9");
        
        System.out.println("--- Assign test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_dup()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("1 dup")).execute(stack);
        passed &= test(stack,1,"dup 1");
        
        (new Script("5 dup dup")).execute(stack);
        passed &= test(stack,5,"dup 2");
        passed &= test(stack,5,"dup 3");
        
        (new Script("1 2 3 4 4 group DUP")).execute(stack);
        passed &= test(stack,10,"dup 4");
        passed &= test(stack,10,"dup 5");
        
        (new Script("0 1 2 3 4 5 group dup 2 max")).execute(stack);
        passed &= test(stack,3,4,2,"dup 6");
        
        System.out.println("--- Dup test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_print()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script(
            "print if you can read this \n" +
            "print and this is on the same line\n" +
            "print\n" + 
            "print And this is on the next line\n" +
            "; 5\n" +
            "print And you can read a five\n" +
            ". ;\n" +
            "print and you see a dup explained\n" +
            " 4 dup .e ;\n" +
            "print I think printing works\nprint"
        )).execute(stack);
        
        System.out.println("---  Print test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_loop()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script(
            "0 _tmp assign clear\n" +
            "12\n" +
            "repeat\n" +
            "_tmp 1 + _tmp assign pop\n" +
            "end repeat\n" +
            "_tmp"
            
        )).execute(stack);
        passed &= test(stack,12,"loop 1");
        
        (new Script(
            "0 _tmp assign clear\n" +
            "0\n" +
            "repeat\n" +
            "_tmp 1 + _tmp assign pop\n" +
            "end repeat\n" +
            "_tmp"
            
        )).execute(stack);
        passed &= test(stack,0,"loop 2");
        
        
        (new Script(
            "0 _tmp assign clear\n" +
            "-1\n" +
            "repeat\n" +
            "_tmp 1 + _tmp assign pop\n" +
            "end repeat\n" +
            "_tmp"
            
        )).execute(stack);
        passed &= test(stack,0,"loop 3");
        
        System.out.println("--- Loop test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_if()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script(
            "1 3 <\n"+
            "IF\n" +
            "8 _tmp ASSIGN\n" +
            "end IF\n" +
            "_tmp"
        )).execute(stack);
        passed &= test(stack,8,"loop 1");
        
        stack.clear();
        (new Script(
            "1 3 >\n"+
            "IF\n" +
            "8 _tmp ASSIGN\n" +
            "end IF\n" +
            "_tmp"
        )).execute(stack);
        passed &= test(stack,0,"loop 2");
        
        stack.clear();
        (new Script(
            "-3\n"+
            "IF\n" +
            "8 _tmp ASSIGN\n" +
            "end IF\n" +
            "_tmp"
        )).execute(stack);
        passed &= test(stack,8,"loop 3");
        
        System.out.println("--- If test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_function()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script(
            "fun @add10\n" +
            "10 +\n" +
            "end fun\n" +
            "20 @add10 @add10\n" +
            "-5 @add10\n"
        )).execute(stack);
        passed &= test(stack,5,"func 1");
        passed &= test(stack,40,"func 2");
        
        (new Script(
            "fun @modifier\n" +
            "_tmp assign 10 - 2 % 0 2 group 1 min\n" +
            "_tmp 10 - 2 / +\n" +
            "end fun\n" +
            "15 @modifier\n" +
            "12 @modifier\n" +
            "7 @modifier\n" +
            "8 @modifier\n" +
            "9 @modifier\n" +
            "10 @modifier\n"
        )).execute(stack);
        passed &= test(stack,0,"func 3");
        passed &= test(stack,-1,"func 4");
        passed &= test(stack,-1,"func 5");
        passed &= test(stack,-2,"func 6");
        passed &= test(stack,1,"func 7");
        passed &= test(stack,2,"func 8");
        
        System.out.println("--- Function test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_include()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script(
            "IncLUDe onedeefive/test_include.die\n" +
            "5 @BIGGER dup @BIGGER\n" +
            "_tmp\n"
        )).execute(stack);
        passed &= test(stack,8,"include 1");
        passed &= test(stack,500,"include 2");
        passed &= test(stack,50,"include 3");
        
        System.out.println("--- Include test: " + (passed? "passed" : "FAILED"));
    }
    
    public static void test_generic()
    {
        Stack<StackFrame> stack = new Stack<StackFrame>();
        boolean passed = true;
        
        (new Script("")).execute(stack);
        passed &= test(stack,1,"");
        
        System.out.println("---  test: " + (passed? "passed" : "FAILED"));
    }
    
/*$$$$$$$$$$$$$$$$$$$$$$$$$ TEST METHODS $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$*/
    public static boolean test(
        Stack<StackFrame> stack, 
        int comp,
        String message
    )
    {
        if(stack.size() == 0)
        {
            System.out.println(
                "FAILED: " + message + "\n Stack was empty."
            );
            return false;
        }
            
        int val = stack.pop().value();
        if(val != comp)
        {
            System.out.println(
                "FAILED: " + message + "\n expected: " + comp + " got: " + val
            );
            return false;
        }
        
        //stack.clear();
        return true;
    }
    
    public static boolean test(
        Stack<StackFrame> stack, 
        int min, int max, int size,
        String message
    )
    {
        if(stack.size() == 0)
        {
            System.out.println(
                "FAILED: " + message + "\n Stack was empty."
            );
            return false;
        }
            
        StackFrame val = stack.pop();
        
        ArrayList<Integer> data = val.rolls();
        if(data.size() != size)
        {
            System.out.println(
                "FAILED: " + message + "\n incorrect dice count. expected: " 
                + size + " got: " + data.size()
            );
            return false;
        }
        
        int sum = 0;
        for(int i = 0; i < size; i++)
        {
            sum += data.get(i);
            if(data.get(i) < min || data.get(i) > max)
            {
                System.out.println(
                    "FAILED: " + message + "\n number " + data.get(i) +
                    " out of range [" + min + "," + max + "]" 
                );
                return false;
            }
        }
        
        if(sum != val.value())
        {
            System.out.println(
                "FAILED: " + message + "\n Sum " + sum + 
                " does not match value() " + val.value()
            );
        }
        
        //stack.clear();
        return true;
    }
}
