package numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    static private final String[] propertyList = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD"};
    static private final List<String> properties = new ArrayList<>();
    static private final List<Integer> happySad = new ArrayList<>();
    static private final List<String> badProperties = new ArrayList<>();
    static private long num;
    static private long totalNumbers = -1;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Amazing Numbers!\n");
        printInstructions();

        while (true) {
            System.out.print("Enter a request: ");
            String query = scan.nextLine();
            System.out.println();
            String[] params = query.split(" ");
            if (query.isBlank()) {
                printInstructions();
            } else if (Long.parseLong(params[0]) == 0) {
                break;
            }
            if (!assignValues(query)) {
                continue;
            }
            while (totalNumbers > 0 || params.length == 1) {
                boolean flag = true;
                if (params.length == 1) {
                    printProperties(num);
                    break;
                } else if (params.length == 2) {
                    printPropertiesForList(num);
                    num++;
                    totalNumbers--;
                } else if (params.length > 2) {
                    for (String property : properties) {
                        if (property.charAt(0) != '-') {
                            if (!findNumbersWithProperty(num, property)) {
                                flag = false;
                                break;
                            }
                        } else {
                            if (!findNumbersWithoutProperty(num, property)) {
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (flag) {
                        printPropertiesForList(num);
                        totalNumbers--;
                    }
                    num++;
                }
            }
            properties.clear();
            badProperties.clear();
        }
        System.out.println("Goodbye!");
    }

    static boolean findNumbersWithoutProperty(long num, String property) {
        switch (property.toUpperCase()) {
            case "-EVEN":
                if (isEven(num)) return false;
                break;
            case "-ODD":
                if (!isEven(num)) return false;
                break;
            case "-BUZZ":
                if (isBuzz(num)) return false;
                break;
            case "-DUCK":
                if (isDuck(num)) return false;
                break;
            case "-PALINDROMIC":
                if (isPalindrome(num)) return false;
                break;
            case "-GAPFUL":
                if (isGapful(num)) return false;
                break;
            case "-SPY":
                if (isSpy(num)) return false;
                break;
            case "-SUNNY":
                if (isSunny(num)) return false;
                break;
            case "-SQUARE":
                if (isSquare(num)) return false;
                break;
            case "-JUMPING":
                if (isJumping(num)) return false;
                break;
            case "-HAPPY":
                if (isHappy(num)) return false;
                break;
            case "-SAD":
                if (isSad(num)) return false;
                break;
        }
        return true;
    }

    static void printInstructions() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("\t* the first parameter represents a starting number;");
        System.out.println("\t* the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.\n");
    }

    static boolean assignValues(String query) {
        try {
            String[] params = query.split(" ");

            if (query.split(" ").length > 0) {
                if (isNatural(Long.parseLong(params[0]))) {
                    num = Long.parseLong(params[0]);
                } else {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    return false;
                }
            }
            if (params.length > 1) {
                if (isNatural(Long.parseLong(params[1]))) {
                    totalNumbers = Long.parseLong(params[1]);
                } else {
                    System.out.println("The second parameter should be a natural number.\n");
                    return false;
                }
            }
            if (params.length > 2) {
                for (int i = 2; i < params.length; i++) {
                    if (params[i].charAt(0) == '-') {
                        if (isValidProperty(params[i].substring(1))) {
                            badProperties.add(params[i]);
                        } else {
                            properties.add(params[i]);
                        }
                    } else {
                        if (isValidProperty(params[i])) {
                            badProperties.add(params[i]);
                        } else {
                            properties.add(params[i]);
                        }
                    }
                }
                if (badProperties.size() != 0) {
                    if (badProperties.size() == 1) {
                        System.out.printf("The property %s is wrong.\n", badProperties.get(0));
                        System.out.printf("Available properties: %s\n", Arrays.toString(propertyList));
                    } else {
                        System.out.printf("The properties %s are wrong.\n", Arrays.toString(badProperties.toArray()));
                        System.out.printf("Available properties: %s\n", Arrays.toString(propertyList));
                    }
                    badProperties.clear();
                    return false;
                }
                for (String i : properties) {
                    for (String j : properties) {
                        if (areMutuallyExclusive(i, j)) {
                            System.out.printf("The request contains mutually exclusive properties: [%s, %s]\n", i, j);
                            System.out.println("There are no numbers with these properties.\n");
                            return false;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("The first parameter should be a natural number or zero.\n");
            return false;
        }
        return true;
    }

    static void printProperties(long num) {
        System.out.printf("Properties of %d\n", num);
        System.out.printf("\t\teven: %b\n", isEven(num));
        System.out.printf("\t\todd: %b\n", isOdd(num));
        System.out.printf("\t\tbuzz: %b\n", isBuzz(num));
        System.out.printf("\t\tduck: %b\n", isDuck(num));
        System.out.printf("\t\tpalindromic: %b\n", isPalindrome(num));
        System.out.printf("\t\tgapful: %b\n", isGapful(num));
        System.out.printf("\t\tspy: %b\n", isSpy(num));
        System.out.printf("\t\tsunny: %b\n", isSunny(num));
        System.out.printf("\t\tsquare: %b\n", isSquare(num));
        System.out.printf("\t\tjumping: %b\n", isJumping(num));
        System.out.printf("\t\thappy: %b\n", isHappy(num));
        System.out.printf("\t\tsad: %b\n", isSad(num));
    }

    static void printPropertiesForList(long num) {
        System.out.printf("%d is", num);
        if (isEven(num)) {
            System.out.print(" even");
        } else {
            System.out.print(" odd");
        }
        if (isBuzz(num)) {
            System.out.print(", buzz");
        }
        if (isDuck(num)) {
            System.out.print(", duck");
        }
        if (isPalindrome(num)) {
            System.out.print(", palindromic");
        }
        if (isGapful(num)) {
            System.out.print(", gapful");
        }
        if (isSpy(num)) {
            System.out.print(", spy");
        }
        if (isSunny(num)) {
            System.out.print(", sunny");
        }
        if (isSquare(num)) {
            System.out.print(", square");
        }
        if (isJumping(num)) {
            System.out.print(", jumping");
        }
        if (isHappy(num)) {
            System.out.print(", happy");
        }
        if (isSad(num)) {
            System.out.print(", sad");
        }
        System.out.println();
    }

    static boolean findNumbersWithProperty(long num, String property) {
        switch (property.toUpperCase()) {
            case "EVEN":
                if (isEven(num)) return true;
                break;
            case "ODD":
                if (!isEven(num)) return true;
                break;
            case "BUZZ":
                if (isBuzz(num)) return true;
                break;
            case "DUCK":
                if (isDuck(num)) return true;
                break;
            case "PALINDROMIC":
                if (isPalindrome(num)) return true;
                break;
            case "GAPFUL":
                if (isGapful(num)) return true;
                break;
            case "SPY":
                if (isSpy(num)) return true;
                break;
            case "SUNNY":
                if (isSunny(num)) return true;
                break;
            case "SQUARE":
                if (isSquare(num)) return true;
                break;
            case "JUMPING":
                if (isJumping(num)) return true;
                break;
            case "HAPPY":
                if (isHappy(num)) return true;
                break;
            case "SAD":
                if (isSad(num)) return true;
                break;
        }
        return false;
    }

    static boolean isValidProperty(String property) {
        for (String i : propertyList) {
            if (i.equals(property.toUpperCase())) {
                return false;
            }
        }
        return true;
    }

    static boolean isNatural(long num) {
        return num > 0;
    }

    static boolean isEven(long num) {
        return num % 2 == 0;
    }
    static boolean isOdd(long num) {
        return num % 2 == 1;
    }

    static boolean isBuzz(long num) {
        return num % 7 == 0 || num % 10 == 7;
    }

    static boolean isDuck(long num) {
        return Long.toString(num).contains("0");
    }

    static boolean isPalindrome(long num) {
        StringBuilder number = new StringBuilder();
        number.append(num);
        number.reverse();
        String reversedNumber = number.toString();
        return reversedNumber.equals(Long.toString(num));
    }

    static boolean isGapful(long num) {
        if (num < 100) {
            return false;
        }
        int divisor = Integer.parseInt(Long.toString(num).substring(0, 1).concat(Long.toString(num).substring(Long.toString(num).length() - 1)));
        return num % divisor == 0;
    }

    static boolean isSpy(long num) {
        long sum = 0;
        long product = 1;
        while (num > 0) {
            int digit = (int) (num % 10);
            sum += digit;
            product *= digit;
            num /= 10;
        }
        return sum == product;
    }

    static boolean isSunny(long num) {
        long square = num + 1;
        long sqrt = (long) Math.floor(Math.sqrt(square));
        return sqrt * sqrt == square;
    }

    static boolean isSquare(long num) {
        long sqrt = (long) Math.floor(Math.sqrt(num));
        return sqrt * sqrt == num;
    }

    static boolean isJumping(long num) {
        String number = Long.toString(num);
        for (int i = 0; i + 1 < number.length(); i++) {
            int temp = Math.abs(Integer.parseInt(number.substring(i, i + 1)) - Integer.parseInt(number.substring(i + 1, i + 2)));
            if (temp != 1) {
                return false;
            }
        }
        return true;
    }

    static boolean isHappy(long num) {
        while (true) {
            int sum = 0;
            while (num > 0) {
                long temp = num % 10;
                sum += (temp * temp);
                num /= 10;
            }
            if (sum == 1) {
                happySad.clear();
                return true;
            }
            for (int i : happySad) {
                if (i == sum) {
                    happySad.clear();
                    return false;
                }
            }
            happySad.add(sum);
            num = sum;
        }
    }
    static boolean isSad(long num) {
        while (true) {
            int sum = 0;
            while (num > 0) {
                long temp = num % 10;
                sum += (temp * temp);
                num /= 10;
            }
            if (sum == 1) {
                happySad.clear();
                return false;
            }
            for (int i : happySad) {
                if (i == sum) {
                    happySad.clear();
                    return true;
                }
            }
            happySad.add(sum);
            num = sum;
        }

    }

    static boolean areMutuallyExclusive(String property1, String property2) {
        boolean minusOne = false;
        boolean minusTwo = false;
        String p1, p2;
        if (property1.charAt(0) == '-') {
            minusOne = true;
            p1 = property1.substring(1).toUpperCase();
        } else {
            p1 = property1.toUpperCase();
        }
        if (property2.charAt(0) == '-') {
            minusTwo = true;
            p2 = property2.substring(1).toUpperCase();
        } else {
            p2 = property2.toUpperCase();
        }

        if (p1.equals("ODD") && p2.equals("EVEN") || p1.equals("EVEN") && p2.equals("ODD")) {
            if (minusOne || minusTwo) {
                return minusOne && minusTwo;
            }
            return true;
        }

        if (p1.equals("DUCK") && p2.equals("SPY") || p1.equals("SPY") && p2.equals("DUCK")) {
            if (minusOne || minusTwo) {
                return minusOne && minusTwo;
            }
            return true;
        }
        if (p1.equals("HAPPY") && p2.equals("SAD") || p1.equals("SAD") && p2.equals("HAPPY")) {
            if (minusOne || minusTwo) {
                return minusOne && minusTwo;
            }
            return true;
        }

        if (p1.equals("SUNNY") && p2.equals("SQUARE") || p1.equals("SQUARE") && p2.equals("SUNNY")) {
            if (minusOne || minusTwo) {
                return minusOne && minusTwo;
            }
            return true;
        }

        if (p1.equals(p2)) {
            return minusOne ^ minusTwo;
        }
        return false;
    }
}
