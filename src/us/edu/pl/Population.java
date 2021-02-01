package us.edu.pl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Population {
    public final int POP_SIZE = 10;
    public Individual[] lfsr = new Individual[POP_SIZE];
    public float fitness;
    public int totalSeqLength;

    public void calculateTotalLengthAndFitness(){
        for(Individual individual : lfsr){
            totalSeqLength += individual.max_seq;
        }
        System.out.println("\nTotalSeqLength: " + totalSeqLength);
        for (Individual individual : lfsr){
            individual.fitness=(float)individual.max_seq/this.totalSeqLength;

            System.out.println("\nFintess: " + individual.fitness);
        }

        //find 5 best fintess score (the highest number)
        //for each best fitness find corresponding chromosome (on which we do calculatin of this fitness)
        List<Individual> lsfrDesc = Arrays.stream(lfsr).sorted(Comparator.comparing(i -> i.fitness)).collect(Collectors.toList());
        System.out.println("\n5 best fitness score with chromosome: ");
        for( int i = lsfrDesc.size()-1;i>=5;i--){
            System.out.println("\nScore: " + lsfrDesc.get(i).fitness);
            System.out.print(" Chromosome: ");
            for (int chromos : lsfrDesc.get(i).chromosome) {
                System.out.print(chromos);
            }
        }

    }
}