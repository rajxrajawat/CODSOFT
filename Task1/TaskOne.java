import java.util.Random;
import java.util.*;
public class TaskOne {
    public static void main(String[] args) {
        final String RESET = "\033[0m";
        final String RED = "\033[31m";
        final String BLUE = "\033[34m";
        final String CYAN = "\033[36m";
        Scanner sc = new Scanner(System.in);
        int chances = 7;
        int point = 0;
        int num = 0;
        Random random = new Random();
        boolean playAgain = true;
        System.out.println(RED+"Hello User Welcome to Number Game"+RESET);
        System.out.println(CYAN+"____________________________________________________"+RESET);
        System.out.println("Rule book:-");
        System.out.println("1. Guess Number between 1 to 100");
        System.out.println("2. Keep in mind that you have only " + chances + " to win this game");
        System.out.println("3. Points will be Divided as");
        System.out.println("   Attempt 1: 35\n   Attempt 2: 30\n   Attempt 3: 25\n   Attempt 4: 20\n   Attempt 5: 15\n   Attempt 6: 10\n   Attempt 7: 5"+RESET);
        System.out.println(CYAN+"____________________________________________________"+RESET);
        int score=0;
        int round=0;
        while (playAgain) {
            int randomNumber = random.nextInt(100) + 1;
            round++;
            System.out.println("Round "+round);
            boolean guess = false;
            for (int i = 1; i <=chances; i++) {
                System.out.print("Chance " + (i) + " Guess the number: ");
                num = sc.nextInt();
                score++;
                if (num == randomNumber) {
                    guess = true;
                    System.out.println("You Won");
                    break;
                } else if (num > randomNumber) {
                    System.out.println("Too High");
                } else {
                    System.out.println("Too Low");
                }
            }
            if(num!=randomNumber) {
                score++;
            }
            switch (score) {
                case 1:
                    point=point+35;
                    break;
                case 2:
                    point=point+30;
                    break;
                case 3:
                    point=point+25;
                    break;
                case 4:
                    point=point+20;
                    break;
                case 5:
                    point=point+15;
                    break;
                case 6:
                    point=point+10;
                    break;
                case 7:
                    point=point+5;
                    break;
                default:
                    point=point+0;
            }
                if (guess == false) {
                    System.out.println("OOOPs you lost the game\nThe Random number is "+(randomNumber));
                }
                System.out.print("Do you want to Play Again(true/false) ");
                String mood = sc.next();
                if(mood.equalsIgnoreCase("true")) {
                    playAgain = true;
                }
                else {
                    playAgain = false;
                }
        }
        System.out.println("Thanks for playing game! Your score is "+point+"pts");
    }
}