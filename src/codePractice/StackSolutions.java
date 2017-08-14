package codePractice;

import java.util.*;

public class StackSolutions {
	// 227. basic calculator
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int n = s.length(), num = 0;
        char sign = '+';
        for(int i = 0; i < n; i++) {
            if(Character.isDigit(s.charAt(i))) num = num * 10 + s.charAt(i) - '0';
            
            if(!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ' || i == n - 1) {
                if(sign == '+') stack.push(num);
                else if(sign == '-') stack.push(-num);
                else if(sign == '*') stack.push(stack.pop() * num);
                else if(sign == '/') stack.push(stack.pop() / num);
                sign = s.charAt(i);
                num = 0;
            }
        }
        int ret = 0;
        while(!stack.empty()) ret += stack.pop();
        return ret;
    }
}
