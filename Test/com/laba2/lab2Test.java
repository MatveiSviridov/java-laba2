package com.laba2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class lab2Test {


    @Test
    void main() throws Exception {
        String str = "2+(1*2)-3+1";
        str = lab2.CheckForVar(str);
        str = lab2.ToPolish(str);
        assertEquals(2.0,lab2.Calculation(str));
    }



    @Test
    void checkForVar() throws Exception {
        String str = "2+(1*2)-3+1";
        String actual = lab2.CheckForVar(str);
        assertEquals("2+(1*2)-3+1",actual);
    }

    @Test
    void toPolish() throws Exception {
        String str = "2+(1*2)-3+1";
        assertEquals("2 1  2 * +  3 -  1 +", lab2.ToPolish(str));
    }

    @Test
    void isOperator() throws Exception {
        assertEquals(true,lab2.IsOperator('+'));
        assertEquals(false,lab2.IsOperator('1'));
    }

    @Test
    void priority() throws Exception {
        assertEquals(2,lab2.Priority('*'));
        assertEquals(1,lab2.Priority('+'));
    }

    @Test
    void calculation() throws Exception {
        String str = "2 1  2 * +  3 -  1 +";
        assertEquals(2.0,lab2.Calculation("2 1  2 * +  3 -  1 +"));
    }
}