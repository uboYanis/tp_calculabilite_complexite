package org.example.utils;


import static org.example.utils.Share.fact;

public class AST {
    private AST() {
    }

    public sealed interface Expr permits ExprBIN, ExprUNA, ExprINT {
    }

    public record ExprBIN(Expr subLeft, String op, Expr subRight) implements Expr {
        @Override
        public String toString() {
            return String.format("(%s %s %s)", subLeft, op, subRight);
        }
    }

    public record ExprUNA(String op, Expr sub) implements Expr {
        @Override
        public String toString() {
            return String.format("(%s %s)", op, sub);
        }
    }

    public record ExprINT(int leaf) implements Expr {
        @Override
        public String toString() {
            return String.format("%s", leaf);
        }
    }

    public static int eval(Expr expr) {
        return switch (expr) {
            case ExprINT(int leaf) -> leaf;
            case ExprUNA(String op, Expr sub) -> switch (op) {
                case "-" -> -eval(sub);
                case "!" -> fact(eval(sub));
                case "%" -> eval(sub) / 100;
                default -> throw new ArithmeticException("Unknow operator " + op);
            };
            case ExprBIN(Expr subL, String op, Expr subR) -> switch (op) {
                case "+" -> eval(subL) + eval(subR);
                case "-" -> eval(subL) - eval(subR);
                case "*" -> eval(subL) * eval(subR);
                case "/" -> eval(subL) / eval(subR);
                default -> throw new ArithmeticException("Unknow operator " + op);
            };
        };
    }

}