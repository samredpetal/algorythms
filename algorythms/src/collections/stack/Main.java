package collections.stack;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        new Main().testingStack();
//        new Main().brackets();
//        new Main().solvePostfix();
//        new Main().infixToPostfix();
//        new Main().reverseWord();
        new Main().palindrome();
    }

    void testingStack() {
        Stack<String> stack = new Stack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        stack.push("Erke", 2);
        stack.push("Syi", 2);

        stack.push("fourth");
        stack.push("fivth");
        stack.push("sixth");
        stack.push("seventh");
        stack.push("eighth");
        stack.push("fourth");


        System.out.println(stack.size());

        for (int i = 0; i < stack.size(); i++) {
            System.out.println(stack.get(i));
        }

        stack = stack.getInversed();
        System.out.println("\nInversing...\n");
        for (int i = 0; i < stack.size(); i++) {
            System.out.println(stack.get(i));
        }
    }

    void brackets() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите текст:");
        String input = sc.nextLine();
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (currentChar == '{') {
                stack.push(Integer.toString(i + 1));
                stack.push("}");
                continue;
            } else if (currentChar == '[') {
                stack.push(Integer.toString(i + 1));
                stack.push("]");
                continue;
            } else if (currentChar == '(') {
                stack.push(Integer.toString(i + 1));
                stack.push(")");
                continue;
            }

            if (stack.peek() != null) {
                if (currentChar == stack.peek().charAt(0)) {
                    stack.pop();
                    stack.pop();
                    continue;
                }
            }

            if (currentChar == '}') {
                stack.push(Integer.toString(i + 1));
                stack.push(Character.toString('{'));
                break;
            }
            if (currentChar == ')') {
                stack.push(Integer.toString(i + 1));
                stack.push(Character.toString('('));
                break;
            }
            if (currentChar == ']') {
                stack.push(Integer.toString(i + 1));
                stack.push(Character.toString('['));
                break;
            }

        }


        if (stack.isEmpty()) {
            System.out.println("В тексте \"" + input + "\" скобки сбалансированы.");
        } else {
            if (stack.peek().charAt(0) == '}') {
                stack.pop();
                stack.push("{");
            } else if (stack.peek().charAt(0) == '{') {
                stack.pop();
                stack.push("}");
            }
            if (stack.peek().charAt(0) == ')') {
                stack.pop();
                stack.push("(");
            } else if (stack.peek().charAt(0) == '(') {
                stack.pop();
                stack.push(")");
            }
            if (stack.peek().charAt(0) == ']') {
                stack.pop();
                stack.push("[");
            } else if (stack.peek().charAt(0) == '[') {
                stack.pop();
                stack.push("]");
            }
            System.out.println("В тексте \"" + input + "\" имеется лишняя '" + stack.pop() + "' в позиции " + stack.pop() + ". Скобки не сбалансированы.");
        }
    }


    void solvePostfix() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = sc.nextLine();
        Stack<Double> stack = new Stack<>();
        for (int i = 0; i < input.length(); i++) {
            String currentChar = Character.toString(input.charAt(i));
            try {
                double number = Double.parseDouble(currentChar);
                stack.push(number);
            } catch (Exception e) {
                try {
                    if (currentChar.charAt(0) == '+') {
                        stack.push(stack.pop() + stack.pop());
                    } else if (currentChar.charAt(0) == '-') {
                        stack = stack.getInversed();
                        stack.push(stack.pop() - stack.pop());
                    } else if (currentChar.charAt(0) == '*') {
                        stack.push(stack.pop() * stack.pop());
                    } else if (currentChar.charAt(0) == '/') {
                        stack = stack.getInversed();
                        stack.push(stack.pop() / stack.pop());
                    } else {
                        System.out.println("Введены неправильные операторы");
                    }
                } catch (NumberFormatException e1) {
                    System.out.println("Введено неверное выражение");
                }
            }
        }
        if (!stack.isEmpty()) {
            System.out.println("Ответ выражения " + stack.pop());
        }
    }

    void infixToPostfix() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите инфиксное выражение:");
        String input = sc.nextLine();
        Stack<Character> stack = new Stack<>();
        String output = "";
        for (int i = 0; i < input.length(); i++) {
            String currentChar = Character.toString(input.charAt(i));
            if (currentChar.charAt(0) == '(') {
                stack.push(currentChar.charAt(0));
            } else if (currentChar.charAt(0) == ')' && stack.contains('(')) {
                for (int j = stack.size() - 1; j >= 0; j--) {
                    if (stack.get(j) == '(') {
                        stack.pop();
                        break;
                    } else {
                        output += stack.pop();
                    }
                }
            } else if (currentChar.charAt(0) == '+' || currentChar.charAt(0) == '-'
                    || currentChar.charAt(0) == '*' || currentChar.charAt(0) == '/') {
                stack.push(currentChar.charAt(0));
            } else if (currentChar.matches("[a-z0-9]")) {
                output += currentChar.charAt(0);
            } else {
                System.out.println("Введенное выражение не верно");
                return;
            }
        }
        if (stack.contains('(')) {
            System.out.println("Введенное выражение не верно");
            return;
        }
        for (int i = stack.size() - 1; i >= 0; i--) {
            output += stack.pop();
        }

        System.out.println("Постфиксное выражение:");
        System.out.println(output);

    }

    void reverseWord() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите слово: ");
        String word = sc.nextLine();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < word.length(); i++) {
            stack.push(word.charAt(i));
        }
        System.out.println("Инверсия слова '" + word + "' равна:");
        for (int i = 0; i < word.length(); i++) {
            System.out.print(stack.pop());
        }
        System.out.println();
    }

    void palindrome() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите слово: ");
        String string = sc.nextLine();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < string.length(); i++) {
            if (!stack.isEmpty() && string.charAt(i) == stack.peek()) {
                stack.pop();
                continue;
            }
            stack.push(string.charAt(i));
        }
        if (stack.isEmpty()) {
            System.out.println("Слово '" + string + "' является палиндромом.");
        } else {
            System.out.println("Слово '" + string + "' не является палиндромом.");
        }

    }
}
