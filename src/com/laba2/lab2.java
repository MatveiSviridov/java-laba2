package com.laba2;

import java.io.*;
import java.util.*;

public class lab2 {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str;
        System.out.println("Введите выражение для расчета:");
        str = reader.readLine();
        str = CheckForVar(str);
        str = ToPolish(str);
        System.out.println(Calculation(str));
    }

    /**
     * Checking for variables and replaces them with numbers
     * @param str Original string
     * @return Modified string
     */
    public static String CheckForVar(String str) throws Exception {
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
            {
                System.out.println("Введите значение переменной " + str.charAt(i) + ": ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String tmp = reader.readLine();
                double dtmp = Double.parseDouble(tmp);
                char sym = str.charAt(i);
                if (dtmp < 0)
                {
                    str = str.substring(0,i)+"(0" + tmp +")" + str.substring(i+1);
                    i=i+3;
                }
                else {
                    str = str.substring(0, i) + tmp + str.substring(i + 1);
                }

                if (str.indexOf(sym) != -1)
                {

                    for (int j = i; j < str.length(); j++)
                    {
                        if (str.charAt(j) == sym)
                        {
                            if (dtmp < 0)
                            {
                                str = str.substring(0,j)+"(0" + tmp +")" + str.substring(j+1);
                                j=j+3;
                                i=i+3;
                            }
                            else {
                                str = str.substring(0,j)+ tmp + str.substring(j+1);
                            }
                        }
                    }

                }
            }

        }
        return str;
    }

    /**
     * Convert string to reverse polish view
     * @param str Original string
     * @return Modified string in reverse polish view
     */
    public static String ToPolish(String str) throws Exception {
        StringBuilder sbStack = new StringBuilder(""), sbOut = new StringBuilder("");
        char cIn, cTmp;

        for (int i = 0; i < str.length(); i++) {
            cIn = str.charAt(i);
            if (IsOperator(cIn)) {
                while (sbStack.length() > 0) {
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                    if (IsOperator(cTmp) && (Priority(cIn) <= Priority(cTmp))) {
                        sbOut.append(" ").append(cTmp).append(" ");
                        sbStack.setLength(sbStack.length()-1);
                    } else {
                        sbOut.append(" ");
                        break;
                    }
                }
                sbOut.append(" ");
                sbStack.append(cIn);
            } else if ('(' == cIn) {
                sbStack.append(cIn);
            } else if (')' == cIn) {
                cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                while ('(' != cTmp) {
                    if (sbStack.length() < 1) {
                        throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                    }
                    sbOut.append(" ").append(cTmp);
                    sbStack.setLength(sbStack.length()-1);
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                }
                sbStack.setLength(sbStack.length()-1);
            } else {
                // Если символ не оператор - добавляем в выходную последовательность
                sbOut.append(cIn);
            }
        }

        // Если в стеке остались операторы, добавляем их в входную строку
        while (sbStack.length() > 0) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length()-1));
            sbStack.setLength(sbStack.length()-1);
        }

        return  sbOut.toString();
    }

    /**
     * Checking for symbol is an operator
     */
    public static boolean IsOperator(char c) throws Exception {
        if (c == '-' || c == '+' || c == '*' || c == '/') {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Gives operation priority
     * @param op operation symbol
     * @return operation priority
     */
    public static byte Priority(char op) {
        if (op == '*' || op == '/')
        {
            return 2;
        }
        else{
            return 1;
        }
    }

    /**
     * Counts an expression written in reverse polish view
     * @param str Original string
     * @return result
     */
    public static double Calculation(String str) throws Exception {
        double dA = 0, dB = 0;
        String stmp;
        Deque<Double> stack = new ArrayDeque<Double>();
        StringTokenizer st = new StringTokenizer(str);
        while(st.hasMoreTokens()) {
            try {
                stmp = st.nextToken().trim();
                if (1 == stmp.length() && IsOperator(stmp.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("Неверное количество данных в стеке для операции " + stmp);
                    }
                    dB = stack.pop();
                    dA = stack.pop();
                    switch (stmp.charAt(0)) {
                        case '+':
                            dA += dB;
                            break;
                        case '-':
                            dA -= dB;
                            break;
                        case '/':
                            dA /= dB;
                            break;
                        case '*':
                            dA *= dB;
                            break;
                        default:
                            throw new Exception("Недопустимая операция " + stmp);
                    }
                    stack.push(dA);
                } else {
                    dA = Double.parseDouble(stmp);
                    stack.push(dA);
                }
            } catch (Exception e) {
                throw new Exception("Недопустимый символ в выражении");
            }
        }

        if (stack.size() > 1) {
            throw new Exception("Количество операторов не соответствует количеству операндов");
        }

        return stack.pop();
    }
}
