/**
Chris Poole
10/31/2020

This file contains the rules for the game of life. It accepts the input
from the GOLInit text file, creates and initializes the grid, and processes 
grid for successive generations. Also includes methods to count the number 
of living cells on the grid, determine if two generations are equal, and 
validate the numerical iput for the number of generations. 
**/

using System;
using System.IO;
using System.Collections.Generic;

namespace GameOfLife {
    public class GameBoard {

        /**
        Initializes the gameboard
        **/
        public static char[,] Init(string filename) {

            //create a list to process input values
            List<int> initNumbers = new List<int>();

            //read input values from GOLInit file
            StreamReader file = new StreamReader(filename);
            string line;
            while((line = file.ReadLine()) != null) {
                string[] splitLine = line.Split(' ');
                //raise error for negative input values
                if(Int32.Parse(splitLine[0]) < 0 || Int32.Parse(splitLine[1]) < 0) {
                    throw new FormatException();
                }
                initNumbers.Add(Int32.Parse(splitLine[0]));
                initNumbers.Add(Int32.Parse(splitLine[1]));
            }
            
            //initialize gameboard with default values
            char[,] gameBoard = new char[initNumbers[0] , initNumbers[1]];
            for(int j = 0; j < gameBoard.GetLength(0); j++) {
                for(int k = 0; k < gameBoard.GetLength(1); k++) {
                    gameBoard[j,k] = ' ';
                }
            }
            //add initial living cells
            for(int l = 2; l < initNumbers.Count - 1; l+=2) {
                gameBoard[initNumbers[l], initNumbers[l + 1]] = '*';
            }

            return gameBoard;
        }

        /**
        Displays the gameboard
        **/
        public static void Display(int generations, char[,] grid) {
            Console.WriteLine($"Generation {generations}");
            for(int i = 0; i < grid.GetLength(0); i++) {
                for(int j = 0; j < grid.GetLength(1); j++) {
                    Console.Write("|");
                    Console.Write($"{grid[i,j]}");
                }
                Console.WriteLine("|");
            }
        }

        /**
        Modifies gameboard to next generation
        **/
        public static char[,] NextGeneration(char[,] currGen) {
            char[,] nextGen = new char[currGen.GetLength(0), currGen.GetLength(1)];

            int neighborCount = 0;

            for(int i = 0; i < currGen.GetLength(0); i++) {
                for(int j = 0; j < currGen.GetLength(1); j++) {

                    //count number of live neighboring cells for current cell
                    neighborCount = NeighborCount(i, j, currGen);

                    //death by loneliness
                    if(currGen[i,j] == '*' && neighborCount < 2) {
                        nextGen[i,j] = ' ';
                    }
                    //death by overcrowding
                    else if(currGen[i,j] == '*' && neighborCount > 3) {
                        nextGen[i,j] = ' ';
                    }
                    //birth
                    else if(currGen[i,j] == ' ' && neighborCount == 3) {
                        nextGen[i,j] = '*';
                    }
                    //survival
                    else {
                        nextGen[i,j] = currGen[i,j];
                    }
                }
            }
            return nextGen;
        }

        /**
        Counts number of live neighboring cells for a given cell
        **/
        public static int NeighborCount(int i, int j, char[,] currGen) {
            int neighborCount = 0;
        
            //Top Left Corner
            if(i == 0 && j == 0) {
                //neighbor to right
                if(currGen[i , j + 1] == '*') {
                    neighborCount++;
                }
                //SE neighbor
                if(currGen[i + 1 , j + 1] == '*') {
                    neighborCount++;
                }
                //neighbor below
                if(currGen[i + 1 , j] == '*') {
                    neighborCount++;
                }
            }
            //Top Row
            else if(i == 0 && j > 0 && j < currGen.GetLength(1) - 1) {
                //neighbor to right
                if(currGen[i , j + 1] == '*') {
                    neighborCount++;
                }
                //neighbor to left
                if(currGen[i , j - 1] == '*') {
                    neighborCount++;
                }
                //neighbor below
                if(currGen[i + 1 , j] == '*') {
                    neighborCount++;
                }
                //SE neighbor
                if(currGen[i + 1 , j + 1] == '*') {
                    neighborCount++;
                }
                //SW neighbor
                if(currGen[i + 1 , j - 1] == '*') {
                    neighborCount++;
                }
            }
            //Top Right Corner
            else if(i == 0 && j == currGen.GetLength(1) - 1) {
                //neighbor to left
                if(currGen[i , j - 1] == '*') {
                    neighborCount++;
                }
                //neighbor below
                if(currGen[i + 1 , j] == '*') {
                    neighborCount++;
                }
                //SW neighbor
                if(currGen[i + 1 , j - 1] == '*') {
                    neighborCount++;
                }
            }
            //Left Column
            else if(i > 0 && i != currGen.GetLength(0) - 1 && j == 0) {
                //neighbor to right
                if(currGen[i , j + 1] == '*') {
                    neighborCount++;
                }
                //neighbor above
                if(currGen[i - 1 , j] == '*') {
                    neighborCount++;
                }
                //neighbor below
                if(currGen[i + 1 , j] == '*') {
                    neighborCount++;
                }
                //NE neighbor
                if(currGen[i - 1 , j + 1] == '*') {
                    neighborCount++;
                }
                //SE neighbor
                if(currGen[i + 1 , j + 1] == '*') {
                    neighborCount++;
                }

            }
            //Bottom Left Corner
            else if(i == currGen.GetLength(0) - 1 && j == 0) {
                //neighbor to right
                if(currGen[i , j + 1] == '*') {
                    neighborCount++;
                }
                //neighbor above
                if(currGen[i - 1 , j] == '*') {
                    neighborCount++;
                }
                //NE neighbor
                if(currGen[i - 1 , j + 1] == '*') {
                    neighborCount++;
                }
            }
            //Bottom Row
            else if(i == currGen.GetLength(0) - 1 && j > 0 && j < currGen.GetLength(1) - 1) {
                //neighbor to right
                if(currGen[i , j + 1] == '*') {
                    neighborCount++;
                }
                //neighbor to left
                if(currGen[i , j - 1] == '*') {
                    neighborCount++;
                }
                //neighbor above
                if(currGen[i - 1 , j] == '*') {
                    neighborCount++;
                }
                //NE neighbor
                if(currGen[i - 1 , j + 1] == '*') {
                    neighborCount++;
                }
                //NW neighbor
                if(currGen[i - 1 , j - 1] == '*') {
                    neighborCount++;
                }
            }
            //Bottom Right Corner
            else if(i == currGen.GetLength(0) - 1 && j == currGen.GetLength(1) - 1) {
                //neighbor to left
                if(currGen[i , j - 1] == '*') {
                    neighborCount++;
                }
                //neighbor above
                if(currGen[i - 1 , j] == '*') {
                    neighborCount++;
                }
                //NW neighbor
                if(currGen[i - 1 , j - 1] == '*') {
                    neighborCount++;
                }    
            }
            //Right Column
            else if(i > 0 && i != currGen.GetLength(0) - 1 && j == currGen.GetLength(1) - 1) {
                //neighbor to left
                if(currGen[i , j - 1] == '*') {
                    neighborCount++;
                }
                //neighbor below
                if(currGen[i + 1 , j] == '*') {
                    neighborCount++;
                }
                //neighbor above
                if(currGen[i - 1 , j] == '*') {
                    neighborCount++;
                }
                //NW neighbor
                if(currGen[i - 1 , j - 1] == '*') {
                    neighborCount++;
                }
                //SW neighbor
                if(currGen[i + 1 , j - 1] == '*') {
                    neighborCount++;
                }
            }
            //All other cells
            else {
                //neighbor to right
                if(currGen[i , j + 1] == '*') {
                    neighborCount++;
                }
                //neighbor to left
                if(currGen[i , j - 1] == '*') {
                    neighborCount++;
                }
                //neighbor below
                if(currGen[i + 1 , j] == '*') {
                    neighborCount++;
                }
                //neighbor above
                if(currGen[i - 1 , j] == '*') {
                    neighborCount++;
                }
                //NE neighbor
                if(currGen[i - 1 , j + 1] == '*') {
                    neighborCount++;
                }
                //SE neighbor
                if(currGen[i + 1 , j + 1] == '*') {
                    neighborCount++;
                }
                //NW neighbor
                if(currGen[i - 1 , j - 1] == '*') {
                    neighborCount++;
                }
                //SW neighbor
                if(currGen[i + 1 , j - 1] == '*') {
                    neighborCount++;
                }
            }
            return neighborCount;
        }

        /**
        Counts number of living cells on gameboard for given generation
        **/
        public static int CellCount(char[,] currGen) {
            int cellCount = 0;
            //count the total number of living cells on the gameboard
            for(int i = 0; i < currGen.GetLength(0); i++) {
                for(int j = 0; j < currGen.GetLength(1); j++) {
                    if(currGen[i,j] == '*') {
                        cellCount++;
                    }
                }
            }
            return cellCount;
        }

        /**
        Determines if two gameboards are equal
        **/
        public static bool Equals(char[,] currGen, char[,] nextGen) {
            for(int i = 0; i < currGen.GetLength(0); i++) {
                for(int j = 0; j < currGen.GetLength(1); j++) {
                    if(currGen[i,j] != nextGen[i,j]) {
                        return false;
                    }
                }
            }
            return true;
        }

        /**
        Validates input for number of generations
        **/
        public static int ValidateNumber() {
            int generations;
            try {
                generations = Int32.Parse(Console.ReadLine());
            } catch(FormatException ex) {
                generations = -1;
            }
            return generations;
        }
    }
}