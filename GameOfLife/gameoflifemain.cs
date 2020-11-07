/**
Chris Poole
10/31/2020

This program is an implementation of John Conway's Game of Life
on a finite 2D grid:
https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
it requires an init text file as an argument, containing the dimensions 
of the 2D grid (first line, two inputs) and the indices of the location 
of the initial live cells on the following lines. 
**/

using System;
using System.IO;

namespace GameOfLife {
    class MainClass {
        public static void Main(string[] args) {
            int generations = 0;
            char continuous = ' ';
            int steadyCount = 0;
            int loopCount = 0;

            try {
                //check for GOLInit file name argument
                if(args.Length < 1) {
                    Console.WriteLine("You must enter an GOLInit filename argument. Exiting.");
                }
                else {
                    //initialize gameboard
                    char[,] gameBoard = GameBoard.Init(args[0]);
                    //display initial gameboard
                    GameBoard.Display(generations, gameBoard);

                    Console.WriteLine("How many generations would you like to see?");
                    //input value error handling
                    generations = GameBoard.ValidateNumber();
                    while(generations < 0) {
                        Console.WriteLine("Please enter a valid positive integer.");
                        generations = GameBoard.ValidateNumber();
                    }

                    Console.WriteLine("Would you like to see the generations continuously?");
                    continuous = Convert.ToChar(Console.ReadLine().ToUpper());
                    //input value error handling
                    while(continuous != 'Y' && continuous != 'N') {
                        Console.WriteLine("Please enter a Y for yes or N for no.");
                        continuous = Convert.ToChar(Console.ReadLine().ToUpper());
                    }

                    //run the game for n generations
                    for(int i = 1; i <= generations; i++) {
                        gameBoard = GameBoard.NextGeneration(gameBoard);
                        GameBoard.Display(i, gameBoard);
                        //prompt to see next generation
                        if(continuous != 'Y') {
                            Console.WriteLine("Please press any key to see the next generation.");
                            Console.ReadKey();
                        }
                        //if all cels die, terminate early
                        if(GameBoard.CellCount(gameBoard) == 0) {
                            Console.WriteLine("All of the cells on the gameboard are dead.");
                            break;
                        }
                        //if the game reaches a steady state, terminate early
                        if(GameBoard.Equals(gameBoard, GameBoard.NextGeneration(gameBoard))) {
                            if(steadyCount > 0) {
                                Console.WriteLine("The game has reached a steady state.");
                                break;
                            }
                            steadyCount++;
                            continue;
                        }
                        //if the game becomes an infinite loop, terminate early
                        if(GameBoard.Equals(gameBoard, GameBoard.NextGeneration(GameBoard.NextGeneration(gameBoard))) && 
                            GameBoard.Equals(GameBoard.NextGeneration(gameBoard), GameBoard.NextGeneration(GameBoard.NextGeneration(
                            GameBoard.NextGeneration(gameBoard))))) {
                            if(loopCount > 2) {
                                Console.WriteLine("The game is in an infinite loop.");
                                break;
                            }
                            loopCount++;
                            continue;
                        }
                    }
                }
            //non numeric or negative GOLInit file data error handling    
            } catch(FormatException ex) {
                Console.WriteLine("Corrupt GOLInit file data. Exiting.");
            }
        }
    }
}