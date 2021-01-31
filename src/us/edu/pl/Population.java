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
        System.out.println("\nTotalSeqLength: " + totalSeqLength);
        for (Individual individual : lfsr){
            individual.fitness=(float)individual.max_seq/this.totalSeqLength;

            System.out.println("\nFintess: " + individual.fitness);
        }

        //find 5 best fintess score (the highest number)
        //for each best fitness I need to find corresponding chromosome (on which we do calculatin of this fitness)


    }
}