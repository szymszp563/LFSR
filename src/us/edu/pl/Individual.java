package us.edu.pl;

import java.util.Arrays;

public class Individual {
    int[] chromosome;
    int max_seq;
    float fitness;

    @Override
    public String toString() {
        return "\nIndividual{" +
                " \n\tchromosome=" + Arrays.toString(chromosome) +
                ",\n\tmax_seq=" + max_seq +
                ",\n\tfitness=" + fitness +
                '}';
    }
}