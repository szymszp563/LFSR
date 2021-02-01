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
        List<Individual> lsfrAsc = Arrays.stream(lfsr).sorted(Comparator.comparing(i -> i.fitness)).collect(Collectors.toList());
        System.out.println("\n5 best fitness score with chromosome: ");
        for( int i = lsfrAsc.size()-1;i>=5;i--){
            System.out.println("\nScore: " + lsfrAsc.get(i).fitness);
            System.out.print(" Chromosome: ");
            for (int chromos : lsfrAsc.get(i).chromosome) {
                System.out.print(chromos);
            }
        }

        //combine chromosome for next iteration
        nextGenerationChromosome(lsfrAsc);
    }

    private void nextGenerationChromosome(List<Individual> lsfrAsc) {
        System.out.println("\n\nNext generation chromosome: ");
        System.out.println(chromosomeToString(lsfrAsc.get(9).chromosome));
        System.out.println(chromosomeToString(lsfrAsc.get(8).chromosome));
        System.out.println(chromosomeToString(lsfrAsc.get(7).chromosome));
        System.out.println(chromosomeToString(lsfrAsc.get(6).chromosome));
        System.out.println(chromosomeToString(lsfrAsc.get(5).chromosome));

        int[] top1 = new int[3];

        top1[0]= lsfrAsc.get(9).chromosome[6];
        top1[1]= lsfrAsc.get(9).chromosome[7];
        top1[2]= lsfrAsc.get(9).chromosome[8];


        int[] top2 = new int[3];
        top2[0]= lsfrAsc.get(8).chromosome[6];
        top2[1]= lsfrAsc.get(8).chromosome[7];
        top2[2]= lsfrAsc.get(8).chromosome[8];

        int[] chromosome1 = lsfrAsc.get(9).chromosome;
        chromosome1[6]= top2[0];
        chromosome1[7]= top2[1];
        chromosome1[8]= top2[2];
        System.out.println(chromosomeToString(chromosome1));

        int[] chromosome2 = lsfrAsc.get(8).chromosome;
        chromosome2[6]= top1[0];
        chromosome2[7]= top1[1];
        chromosome2[8]= top1[2];
        System.out.println(chromosomeToString(chromosome2));

        int[] top3 = new int[5];
        top3[0]= lsfrAsc.get(7).chromosome[2];
        top3[1]= lsfrAsc.get(7).chromosome[3];
        top3[2]= lsfrAsc.get(7).chromosome[4];
        top3[3]= lsfrAsc.get(7).chromosome[5];
        top3[4]= lsfrAsc.get(7).chromosome[6];


        int[] top4 = new int[5];
        top4[0]= lsfrAsc.get(6).chromosome[2];
        top4[1]= lsfrAsc.get(6).chromosome[3];
        top4[2]= lsfrAsc.get(6).chromosome[4];
        top4[3]= lsfrAsc.get(6).chromosome[5];
        top4[4]= lsfrAsc.get(6).chromosome[6];


        int[] chromosome3 = lsfrAsc.get(7).chromosome;
        chromosome3[2]= top4[0];
        chromosome3[3]= top4[1];
        chromosome3[4]= top4[2];
        chromosome3[5]= top4[3];
        chromosome3[6]= top4[4];
        System.out.println(chromosomeToString(chromosome3));

        int[] chromosome4 = lsfrAsc.get(6).chromosome;
        chromosome4[2]= top3[0];
        chromosome4[3]= top3[1];
        chromosome4[4]= top3[2];
        chromosome4[5]= top3[3];
        chromosome4[6]= top3[4];
        System.out.println(chromosomeToString(chromosome4));


        int[] chromosome5 = lsfrAsc.get(5).chromosome;
        for (int i = 0; i < chromosome5.length; i++) {
            if(chromosome5[i]==0){
                chromosome5[i]=1;
                break;
            }
        }
        System.out.println(chromosomeToString(chromosome5));
    }

    private String chromosomeToString(int [] tab){
        String h="";
        for (int i : tab) {
            h+=i;
        }
        return h;
    }
}