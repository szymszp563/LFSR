package us.edu.pl;

import java.util.Scanner;

import static java.lang.Math.pow;

public class Main {

    static int LFSR_LENGTH = 10;
    static int NUMBER_OF_GENERATION = 10;
    static int POP_SIZE = 10;

    static void printMatrix(int[][] matrix) {
        System.out.println("\n");
        for (int i = 0; i < LFSR_LENGTH; i++) {
            for (int j = 0; j < LFSR_LENGTH; j++)
                System.out.println(matrix[i][j]);
            System.out.println("\n");
        }
        return;
    }

    static void printState(int[] state) {
        System.out.println("\n");
        for (int i = 0; i < LFSR_LENGTH; i++)
            System.out.println(state[i]);
        return;
    }

    static void setTConnectionMatrix(int[][] matrix) {
        for (int i = 0; i < LFSR_LENGTH - 1; i++)
            matrix[i + 1][i] = 1;
        matrix[0][LFSR_LENGTH - 1] = 1;
        return;
    }

    static void setNextState(int[][] matrix, int[] curr, int[] next) {
        for (int i = 0; i < LFSR_LENGTH; i++) {
            for (int j = 0; j < LFSR_LENGTH; j++)
                next[i] += matrix[i][j] * curr[j];
            next[i] %= 2;
        }
        return;
    }

    static int stateToInt(int[] state) {
        int result = 0;
        for (int i = 0; i < LFSR_LENGTH; i++) {
            result += state[i] * (int) pow(2, LFSR_LENGTH - 1 - i);
        }
        return result;
    }

    static void saveSateToRegisterSequence(int[] state, int[][] reg_sequ, int index) {
        reg_sequ[index] = state;
        return;
    }

    static void printRegisterSequnce(int[][] reg_seq, int index) {
        for (int i = 0; i < index; i++) {
            System.out.printf("[%d]->", i);
            printState(reg_seq[i]);
            System.out.println("\n");
        }
        return;
    }

    static void printRegisterSequence(int index, int[][] reg_seq) {
        System.out.printf("[%d]->", index);
        printState(reg_seq[index]);
        return;
    }

    //***************** genetic algorithm operations

    static void setChromosomeToMatrix(int[][] matrix, int[] chrome) {
        //LFSR_LENGTH-1 is the size of chromosome, but size of T row is
        // LFSR_LENGTH
        for (int i = 0; i < LFSR_LENGTH - 1; i++) {
            matrix[0][i] = chrome[i];
        }
        return;
    }

    static void printChromosome(int[] chromo) {
        System.out.printf("\nChromosome -> ");
        for (int i = 0; i < LFSR_LENGTH - 1; i++) {
            System.out.printf("%d", chromo[i]);
        }
        return;
    }

    public static void main(String[] args) {
        int[] current_state = new int[LFSR_LENGTH]; //int current_state[LFSR_LENGTH]
        for (int i = 0; i < LFSR_LENGTH; i++) {
            current_state[i] = 0;
        }
        int[] next_state = new int[LFSR_LENGTH];
        for (int i = 0; i < LFSR_LENGTH; i++) {
            next_state[i] = 0;
        }
        int[][] t_connection_matrix = new int[LFSR_LENGTH][];
        for (int i = 0; i < LFSR_LENGTH; i++) {
            t_connection_matrix[i] = new int[LFSR_LENGTH];
        }
        for (int i = 0; i < LFSR_LENGTH; i++) {
            for (int j = 0; j < LFSR_LENGTH; j++) {
                t_connection_matrix[i][j] = 0;
            }
        }
        setTConnectionMatrix(t_connection_matrix);

        int size = (int) pow(2, 10);//1024
        int[][] register_sequence = new int[size][];
        for (int i = 0; i < size; i++) {
            register_sequence[i] = new int[LFSR_LENGTH];
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < LFSR_LENGTH; j++) {
                register_sequence[i][j] = 0;
            }
        }

        printMatrix(t_connection_matrix);
        //temp. verification of code
        System.out.printf("Enter first state of register...\n");
        char[] line = new char[ LFSR_LENGTH + 1]; // the last mark in string is '\0'
        Scanner scanner = new Scanner(System.in);
        scanner.next().getChars(0, LFSR_LENGTH + 1, line, 0);

        for (int i = 0; i < LFSR_LENGTH; i++) {
            current_state[i] = line[i] - '0'; //in ASCI CODE '0'-48, '1'- 49
        }

        printState(next_state);
        setNextState(t_connection_matrix, current_state, next_state);
        printState(next_state);
        System.out.printf("\n***");
        printState(current_state);
        System.out.printf("\nstateToInt returned that value -> %d\n", stateToInt(current_state));
        saveSateToRegisterSequence(current_state, register_sequence, 0);
        saveSateToRegisterSequence(next_state, register_sequence, 0);
        printRegisterSequnce(register_sequence, 2);
        printRegisterSequence(1, register_sequence);

        // olny external LFSR, 1 row in t-connection_matrix
        int[] chromosome = new int[LFSR_LENGTH - 1];
        for (int i = 0; i < LFSR_LENGTH - 1; i++) {
            chromosome[i] = 1;
        }
        setChromosomeToMatrix(t_connection_matrix, chromosome);
        printMatrix(t_connection_matrix);

        /*
        random chromosome values
        srand((unsigned int)time(NULL));
        rand() -> 0 to RAND_MAX, RAND_MAX - 32 000
        */
        for (int j = 0; j < POP_SIZE; j++) {
            for (int i = 0; i < LFSR_LENGTH - 1; i++)
                chromosome[i] = (Math.random() <= 0.5) ? 1 : 2;
            setChromosomeToMatrix(t_connection_matrix, chromosome);
            printMatrix(t_connection_matrix);
        }

        //creating new population of POP_SIZE individuals
        Population pop = new Population();
        for(int i = 0; i < pop.lfsr.length; i++) {
            pop.lfsr[i] = new Individual();
        }
        float pop_fitness = 0.0f;
        float mp = 0.03f;
        float cp = 0.8f;
        //inital population
        for (int i = 0; i < POP_SIZE; i++) {
            pop.lfsr[i].fitness = 0.0f;
            pop.lfsr[i].max_seq = 0;
            pop.lfsr[i].chromosome = new int[LFSR_LENGTH - 1];
            for (int j = 0; j < LFSR_LENGTH - 1; j++) {
                pop.lfsr[i].chromosome[j] = (Math.random() <= 0.5) ? 1 : 2; //only 0s or 1s
            }
            printChromosome(pop.lfsr[i].chromosome);
            //chrome -> lfsr
            setChromosomeToMatrix(t_connection_matrix, pop.lfsr[i].chromosome);
            //transition graph from lsft with that t_connection_matrix
            //eveal fitness values from max_sequence that will be created
            //by using out lfsr with t_connection_matrix
        }
        for (int k = 0; k < NUMBER_OF_GENERATION; k++) {
        /*
        1. check every individual from population, get sequence length
        2. convert number -> length of sequence to fitness value
        3. evaluation of whole population fitness = sum of all
        individual fitness
        4. we make selection procedure to next population using
        crossover and mutation genetic operators on the
        selected individuals that will be a pair of parents to
        obtain new pair of children. We have to use a random
        procedure to get some crossing point, and after that
        we cen divide parents chromosomes to 2 parts and
        exchange that parts to create 2 children - new 2 individuals
        5. New individuals create new population, and we we can
        realize first point of this algorithm.
        */
        }
    }
}