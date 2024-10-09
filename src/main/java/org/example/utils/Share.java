package org.example.utils;

public class Share {
    public static int fact(int n) {
        if (n<=0)
            return 1;
        else
            return n*fact(n-1);
    }
}
