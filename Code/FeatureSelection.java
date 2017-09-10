import weka.core.Instances;
import java.io.*;
import weka.core.Instance;
import weka.core.Attribute;
import weka.filters.unsupervised.instance.RemoveWithValues;
import java.util.Random;

public class FeatureSelection {
    //Global parameters to Genetic Algorithm
    public static String trainingset_path;
    public static double BETA=0.5;
    public static int no_of_gen=0;
    public static final float MUTATION_PROBABILITY=0.05f;
    public static final float CROSSOVER_RATE=1.0f;
    public static float elitism_rate=0.1f;
    public static int generations=0;
    
    public void setData(String path,float a,int gen) {
        this.trainingset_path=path;
        BETA=a;
        no_of_gen=gen;
    }
    
    @SuppressWarnings("empty-statement")
    public static void main(String args[]) throws FileNotFoundException, IOException{
        Instances data;
         //Fetch the training data from the path
        //The instances are stored in the data variable.
                BufferedReader reader = new BufferedReader(new FileReader("D:\\Actual training and testing dataset\\Training and testing with normal and attack classes\\Training_nosuccpred.arff"));
            data = new Instances(reader);
        
        data.setClassIndex(data.numAttributes() - 1);
        //Delete features 2,3,4 i.e protocol,service and flag respectively since they are nominal attributes
        
        data.deleteAttributeAt(1);      //delete protocol
        data.deleteAttributeAt(1);      //delete service
        data.deleteAttributeAt(1);      //delete flag
        //Filter out the R2L and U2R attack instances. Genetic Algorithm works only on these instances
        //Filter used is RemoveWithValues
        //Use Naive Approach, check for class index, if it is found to be normal,Dos, probe then remove instance
        
        for(int i=data.numInstances()-1;i>=0;i--)
       {
          Instance inst = data.instance(i);
          int index=(int)inst.value(data.classIndex());
          String classify=new String();
          switch(index)
          {
              case 0:
              {    
                 classify="normal";
                 break;
              }   
              case 1:
              {
                classify="dos";
                break;
              }    
              case 2:
              {
                classify="u2r";
                break;
              } 
              case 3:
              {
                classify="r2l";
                break;
              }
              case 4:
              {
                classify="probe";
                break;
              }
            }
         if(classify=="normal" || classify=="dos" || classify=="probe" || classify=="r2l")
              data.delete(i);                           //Deletes Dos,probe,normal,r2l attack instances
        }
        System.out.println(data.numInstances());
        //Encoding the chromosomes
        //Initializing the encode vector to 0
        int encode[][]=new int[data.numInstances()][data.numAttributes()-1];
        for(int i=0;i<data.numInstances();i++)
        {
            for(int b=0;b<data.numAttributes()-1;b++)
            {
                encode[i][b]=0;
            }
        }
        /* 
        The chromosomes are encoded into binary format. 1 means that the feature is selected, 0 means
        that the feature is not selected. 1 means that the packet has a non-zero value for that attribute
        0 means that the packet has a zero value for that attribute. 
        */
        int b=0;
        for(int i=0;i<data.numInstances();i++)
        {
            for(b=0;b<data.numAttributes()-1;b++)
            {
                if(data.instance(i).value(b)>0)             //Non-zero value for a particular feature
                    encode[i][b]=1;
                else
                    encode[i][b]=0;                         //Zero value is retained as it is
            }
        }      
        //Chromosomes are encoded in encode[instance][feature]
    //Define Fitness Funtion
    double fitness[]=new double[data.numInstances()];
    //final double BETA=0.3;
    double count_ones[]=new double[data.numInstances()];
    double Mx[]=new double[data.numInstances()];
    double Cx[]=new double[data.numInstances()];
    double weight_r2l[]=new double[]{0.482,0.9,0.486,0.0,0.0,0.01045,0.4918,0.1479,0.7854,0.01889,0.02656,0.00572,0.0228,0.03719,0.01479,0.03719,0.0,0.0,0.4491,0.9976,0.9976,0.0564,0.0594,0.1500,0.1663,0.9976,0.0302,0.0846,0.9976,0.9976,0.971,0.4851,0.5841,0.4988,0.3736,0.3216,0.3736,0.1663};
    double weight_u2r[]=new double[]{0.5666,0.6103,0.8824,0.0,0.0,0.0685,0.5021,0.0685,0.7420,0.4951,0.5,0.0,0.3531,0.4951,0.2283,0.0685,0.0,0.0,0.0,1.0,1.0,0.1175,0.0,0.1175,0.0685,1.0,0.2579,0.0,1.0,1.0,0.8043,0.4056,0.6676,0.3896,0.0,0.0685,0.2849,0.2849};
    final double NO_OF_FEATURES=38.0;
    double sum_of_weights_u2r=14.0473;
    double sum_of_weights_r2l=13.2256;
    //Initializing the fitness, number of ones,high imp features of each chromosome to 0
     while(no_of_gen<300)
     {
    for(int i=0;i<data.numInstances();i++)
    {
        fitness[i]=0.0;
        count_ones[i]=0;
        Cx[i]=0.0;
        Mx[i]=0.0;
    }
    //Weight vector x gene vector
    for(int i=0;i<data.numInstances();i++)
    {
        for(int j=0;j<data.numAttributes()-1;j++)
        {
            Mx[i]+=weight_u2r[j]*encode[i][j];
        }
    }
    for(int i=0;i<data.numInstances();i++)
    {
        for(int j=0;j<data.numAttributes()-1;j++)
        {    
             if(encode[i][j]==1)   
                 count_ones[i]++;
        }
        Cx[i]=count_ones[i]/NO_OF_FEATURES;
        fitness[i]=(Cx[i])+(Mx[i]/sum_of_weights_u2r);
    }    
    for(int i=0;i<data.numInstances();i++)
    {
        for(int j=0;j<data.numAttributes()-1;j++)
        {
            System.out.print(encode[i][j]);
        }    
        System.out.println("\t"+count_ones[i]+"\t"+Mx[i]+"\t"+ fitness[i]);
    }
    double sum=0,max=0,min=0;
    for(int i=0;i<fitness.length;i++)
    {
        sum+=fitness[i];
        
    }    
    System.out.println(sum/fitness.length);
    /* The Fitness of each chromosome is calculated, now use the RANK SELECTION algorithm
    RANK SELECTION ALGORITHM sorts the chromosomes according to the descending value of fitness funtion
    Then the top most chromosomes are used as parents to perform a crossover.
    SELECTION SORT is used as a sorting algorithm to sort the chromosomes according to the fitness funtion
    */        
    //The entire encoded chromosome in encode[][] is now stored as a array of StringBuffer in chromosome[]
    //The entire further process of chromosomes can be done on strings.
    StringBuffer chromosome[]=new StringBuffer[data.numInstances()];
    for(int i=0;i<data.numInstances();i++)
    {
        chromosome[i]=new StringBuffer(data.numAttributes()-1);
    }    
    for(int i=0;i<data.numInstances();i++)
    {
        for(int j=0;j<data.numAttributes()-1;j++)
        {
            chromosome[i].append(encode[i][j]);
        }    
    }    
    for (int i = 0; i < data.numInstances() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < data.numInstances(); j++)
                if (fitness[j] > fitness[index])
                    index = j;
      
            StringBuffer tempchromosome = chromosome[index]; 
            chromosome[index] = chromosome[i];
            chromosome[i] = tempchromosome;
            
            double temp=fitness[index];
            fitness[index]=fitness[i];
            fitness[i]=temp;  
        }
    
    for(int i=0;i<chromosome.length;i++)
        System.out.println(chromosome[i]+"\t"+fitness[i]);
    //Chromosomes are sorted in descending order of fitness values
    //RANK SELECTION comes in place here. Removing 10% of the chromosomes with lowest fitness
    
    
    //Enter Code here
    
    
    /* CROSSOVER
    Use Single Point Crossover with the pivot arbitrarily set to 25
    The parents are choosen to be adjacent chromosomes, i.e chromosome[i] and chromosome[i+1]
    The children are stored in StringBuffer array, children[]
    */
    //Initializing the children[] chromosomes
    StringBuffer children[]=new StringBuffer[data.numInstances()];
    CharSequence cs1,cs2,cs3,cs4;
    cs1=cs2=cs3=cs4=null;
    int pivot=15;
    for(int i=0;i<data.numInstances();i++)
    {
        children[i]=new StringBuffer(data.numAttributes()-1);
    }    
    for(int i=0;i<data.numInstances()-1;i+=2)
    {
        cs1=chromosome[i].subSequence(0, pivot);
        cs2=chromosome[i].subSequence(pivot, chromosome[i].length());
        cs3=chromosome[i+1].subSequence(0, pivot);
        cs4=chromosome[i+1].subSequence(pivot, chromosome[i+1].length());
        //System.out.println(cs1+"\t"+cs2+"\t"+cs3+"\t"+cs4);
        children[i].append(cs1,0,pivot);
        children[i].append(cs4);
        children[i+1].append(cs3,0,pivot);
        children[i+1].append(cs2);
    }    
       //for(int i=0;i<data.numInstances()-1;i++)
       //{
         //  System.out.println(chromosome[i]+"\t"+children[i]);
       //}    
       //Now the entire chromosomes are stored in children[]
       /* MUTATION 
       Mutatiom means changing the bits from 0 to 1 and vice versa.
       It helps in maintaining Genetic Diversity from generation to generation, it should be set very low
       It helps getting better solutions.
       MUTATION_PROBABILITY is set to 0.05
       A random number is choosen between 0 and 1, rand
       if rand<0.05, then flip the bit 
       otherwise
       leave the bit as it is
       */ 
       Random rand=new Random();
       StringBuffer mutated_children[]=new StringBuffer[children.length];
       int count[]=new int[children.length];
       for(int i=0;i<count.length;i++)
       {
         count[i]=0;
       }
       for(int i=0;i<mutated_children.length;i++)
       {
         mutated_children[i]=new StringBuffer(data.numAttributes()-1);
       }    
       for(int i=0;i<children.length;i++)
       {    
        for(int j=0;j<children[i].length();j++)
         {    
            if(rand.nextFloat()<=MUTATION_PROBABILITY)      
            {
                count[i]++;                                     //rand<MUTATION_PROBABILITY so flip bits
                if(children[i].charAt(j)=='0')
                    mutated_children[i].append("1");            //Appends bits in mutated_children
                else if(children[i].charAt(j)=='1')
                    mutated_children[i].append("0");
            
            }
            else
            {
                if(children[i].charAt(j)=='0')                  //Bits are kept as it is
                    mutated_children[i].append("0");
                else if(children[i].charAt(j)=='1')
                    mutated_children[i].append("1");
                    
            }
         }   
       }   
       System.out.println();
     for(int i=0;i<mutated_children.length;i++)
     {
         System.out.println(children[i]+"\t"+mutated_children[i]+"\t"+count[i]);
     }
     
    System.out.println(mutated_children.length);
    int featindex[]=new int[mutated_children[0].length()];
    //int featureindex[]=new int[mutated_children.length];
    int count11=0;
    for(int i=0;i<mutated_children.length-1;i++)   
    {
       
            if(mutated_children[i].charAt(37)=='1')
            count11++;

    }
    System.out.println("\n"+mutated_children[0]);
    System.out.print(count11+"\t");
    for(int i=0;i<mutated_children[0].length();i++)
    {
        if(mutated_children[0].charAt(i)=='1')
            featindex[i]=i+1;
        
    }
    System.out.println("Selected Features are:\t"+featindex[0]+" 2 3 4");
    for(int i=1;i<featindex.length;i++)
    {
       if(featindex[i]>0)
        System.out.print((featindex[i]+3)+" ");
    }
    no_of_gen++;
    }
    }
}
