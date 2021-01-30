package us.edu.pl;

public class Population {
    public final int POP_SIZE = 10;
    public Individual[] lfsr = new Individual[POP_SIZE];
    public float fitness;
    public int totalSeqLength;

    public void calculateTotalLengthAndFitness(){
        for(Individual individual : lfsr){
            totalSeqLength += individual.max_seq;
        }
        for (Individual individual : lfsr){
            individual.fitness=individual.max_seq/this.fitness;
        }
    }
}