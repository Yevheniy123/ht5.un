package service;

import dto.Computer;
import dto.DependencyTable;
import dto.Player;

import org.apache.log4j.*;

import java.time.LocalDate;
import java.util.Scanner;

import static service.Beautify.lineDelimiter;
import static service.PrintStatistics.printStats;
import static service.ToolDefinition.defineTool;
import static service.WinDefinition.defineWin;

public class Controller extends DependencyTable {
    private static final int DASHES = 70;
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getSimpleName());
    private Player player;
    private Computer computer;
    private int countGames;

    public void runGame() {
        try {
            computer = new Computer();
            boolean nextGame = true;
            Scanner scanner = new Scanner(System.in);

            System.out.print("Hello! What's your name? \n-> ");
            String name = scanner.nextLine();

            player = new Player(name);

            LOGGER.info(player.getName() + " authorized and started the game.");

            System.out.println("Nice to meet you, " + player.getName() + " :)");
            System.out.print("How many games do you want to play? \n-> ");
            int plays = Integer.parseInt(scanner.nextLine());

            /* LOGGER LINE */
            LOGGER.info("Amount of plays : " + plays);
            /* END OF LOGGER LINE */

            System.out.println("Nice :) \nLET'S PLAY!");
            do {
                System.out.println(lineDelimiter(DASHES));

                System.out.print("""
                            Tools:\s
                             [R] - Rock\s
                             [P] - Paper\s
                             [S] - Scissors\s
                            Input:\040""");

                /* defining the tools for Player and Computer */
                String toolChoice = scanner.nextLine();
                String computerChoice = computer.getComputerDecision();

                /* setting tools for player and computer */
                player.setTool(toolChoice);
                computer.setTool(computerChoice);

                LOGGER.info("Player choice : " + player.getTool());
                LOGGER.info("Computer choice : " + computer.getTool());

                System.out.println(lineDelimiter(DASHES));

                /* define the winner */
                System.out.println("Result: ");
                String resultChoice = defineWin(player, computer,
                        defineTool(toolChoice), defineTool(computerChoice));

                /*                     LOGGER BLOCK                       */
                LOGGER.debug(LocalDate.now() + " " + lineDelimiter(3) + " P: " +
                                    toolChoice + " C: " + computerChoice);
                /*                  END OF LOGGER BLOCK                   */

                System.out.println(resultChoice);
                System.out.println(lineDelimiter(DASHES));

                /* save number of victories, loses and ties */
                countGames = player.getWin() + player.getLose() + player.getTie();
                if (countGames == plays) break;

                /*                  LOGGER BLOCK                   */
                LOGGER.info("Player victories : " + player.getWin());
                LOGGER.info("Player loses : " + player.getLose());
                LOGGER.info("Player ties : " + player.getTie());

                LOGGER.info("Computer victories : " + computer.getWin());
                LOGGER.info("Computer loses : " + computer.getLose());
                LOGGER.info("Computer ties : " + computer.getTie());

                LOGGER.info("Amount of plays left : " + (plays - countGames));
                /*              END OF LOGGER BLOCK              */

                System.out.print("""
                            Do you want to play again? [N] - to exit.\s
                            Press any [button] to continue.\s
                            Input:\040""");

                String buffer = scanner.nextLine();
                if (buffer.equalsIgnoreCase("N")) nextGame = false;

                } while (nextGame);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("This tool is not existing!");
                LOGGER.error(e);

            } catch (NumberFormatException e) {
                System.err.println("Wrong argument! >:(");
                LOGGER.error(e);

            } finally {
                System.out.println();
                printStats(player, computer);
                LOGGER.info("Statistics were displayed successfully. Player " +
                        player.getName() + " has left the game.");
            }
    }
}
