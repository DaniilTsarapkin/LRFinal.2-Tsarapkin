import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Expression {
    public static String[] getExpression(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение из дробей, отделяя дроби, скобки и знаки пробелом: ");
        String expression = scanner.nextLine();
        return expression.split(" ");
    }
}

class frac {
    public int num, den;
    public String frac;
    public frac(String frac) throws Exception {
        int len = frac.length();
        StringBuilder num0 = new StringBuilder();
        StringBuilder den0 = new StringBuilder();
        String pattern="[0-9]+/[1-9]+|-[0-9]+/[1-9]+";
        Pattern pt = Pattern.compile(pattern);
        Matcher m = pt.matcher(frac);
        int index = frac.indexOf('/');
        if (m.matches()) {
            for (int i = 0; i < index; i++) {
                num0.append(frac.charAt(i));
            }
            for (int i = index + 1; i < len; i++) {
                den0.append(frac.charAt(i));
            }
        } else { 
            throw new Exception("Выражение введено неверно");
        }
        this.den = Integer.parseInt(den0.toString());
        this.num = Integer.parseInt(num0.toString());
    }
    public static String sum(frac frac0, frac frac1) {
        return (frac0.den * frac1.num + frac0.num * frac1.den) + "/" + frac0.den * frac1.den;
    }
    public static String sub(frac frac0, frac frac1) {
        return (frac0.den * frac1.num - frac0.num * frac1.den) + "/" + frac0.den * frac1.den;
    }
    public static String mul(frac frac0, frac frac1) {
        return frac0.num * frac1.num + "/" + frac0.den * frac1.den;
    }
    public static String seg(frac frac0, frac frac1) {
        return frac0.num * frac1.den + "/" + frac0.den * frac1.num;
    }
}

public class Final{
    public static void main(String[] args) throws Exception{
        while (true){
        System.out.println(FracCalculator.RPNtoAnswer(FracCalculator.ExpressionToRPN(Expression.getExpression())));
        System.out.println("Привет");
    }
}
}

class FracCalculator{

    public static int getP(String token){
        if(Objects.equals(token, "*")||Objects.equals(token, ":")) return 3;
        else if (Objects.equals(token, "+")||Objects.equals(token, "-")) return 2;
        else if (Objects.equals(token, "(")) return 1;
        else if (Objects.equals(token, ")")) return -1;
        else return 0;
        }

    public static Object[] ExpressionToRPN(String[] Expr){
        ArrayList currentExpression = new ArrayList();
        int priority;
        Stack<String> stack = new Stack<>();
        for (int i=0; i<Expr.length; i++){
        priority=getP(Expr[i]);
            if (priority==0)currentExpression.add(Expr[i]);
            if (priority==1)stack.push(Expr[i]);
            if (priority>1){
            while(!stack.empty()){
                if(getP(stack.peek())>= priority) currentExpression.add(stack.pop());
            else break;
        }
        stack.push(Expr[i]);
    }
    if (priority ==-1){
         while(getP(stack.peek())!=1) currentExpression.add(stack.pop());
    stack.pop();
    }
    }
        while(!stack.empty()) currentExpression.add(stack.pop());
        return currentExpression.toArray();
    }
   
public static String RPNtoAnswer(Object[] rpn)throws Exception{
    String chislo = new String();
    Stack<String> stack = new Stack<>();
    for (int i=0; i< rpn.length; i++){
        if (getP(String.valueOf(rpn[i])) == 0){
            chislo = (String) rpn[i];
            stack.push(chislo);
            }
            
    if(getP(String.valueOf(rpn[i])) > 1){
    frac Fr1 = new frac(stack.pop());
    frac Fr2 = new frac(stack.pop());
        if (rpn[i].toString().equals("+")) stack.push(frac.sum(Fr1,Fr2));
        if (rpn[i].toString().equals("-")) stack.push(frac.sub(Fr1,Fr2));
        if (rpn[i].toString().equals("*")) stack.push(frac.mul(Fr1,Fr2));
        if (rpn[i].toString().equals(":")) stack.push(frac.seg(Fr1,Fr2));
            }
}
        return stack.pop();
    }
}