import weka.core.Instances;
import java.io.*;
public class WeightsofFeatures {
    public static void main(String args[])throws Exception
    {
        BufferedReader reader =new BufferedReader(new FileReader("D:\\Actual training and testing dataset\\Training and testing with normal and attack classes\\training_r2l.arff"));
        Instances data=new Instances(reader);
        reader.close();
        data.deleteAttributeAt(1);
        
        data.deleteAttributeAt(1);
        data.deleteAttributeAt(1);
        
        data.setClassIndex(data.numAttributes()-1);
        double[] weights=new double[data.numAttributes()-1];
        int encode[][]=new int[data.numInstances()][data.numAttributes()];
        for(int i=0;i<data.numInstances();i++)
        {
            for(int j=0;j<data.numAttributes();j++)
            {
                if(data.instance(i).value(j)>0)
                    encode[i][j]=1;
                else 
                    encode[i][j]=0;
            }    
        }
        int count=0;
        for(int i=0;i<data.numInstances();i++)
        {
                if(encode[i][1]==1)
                    count++;
            
        }
        double freq1_u2r[]=new double[]{37,40,50,0,0,1,28,1,46,23,26,0,10,23,5,1,0,0,0,52,52,2,0,2,1,52,6,0,52,52,48,13,43,12,0,1,7,7};
        double freq0_u2r[]=new double[data.numAttributes()-1];
        double freq1_r2l[]=new double[]{387,965,400,0,0,2,423,52,909,4,6,1,5,9,3,9,0,0,313,995,995,15,16,53,61,995,7,25,995,995,989,397,737,526,212,163,212,61};
        double freq0_r2l[]=new double[data.numAttributes()-1];
        double entropy_u2r[]=new double[data.numAttributes()-1];
        double entropy_r2l[]=new double[data.numAttributes()-1];
        for(int i=0;i<data.numAttributes()-1;i++)
        {
            freq0_u2r[i]=52-freq1_u2r[i];
            freq0_r2l[i]=995-freq1_r2l[i];
        }
        for(int i=0;i<data.numAttributes()-1;i++)
        {    
            double d1=freq1_u2r[i];
            double d2=freq0_u2r[i];
            double iGain = (-1*((d1/(d1+d2))*Math.log(((d1==0)?1:d1)/(d1+d2))
								+ (d2/(d1+d2))*Math.log(((d2==0)?1:d2)/(d1+d2))))
								/ Math.log(2);
            entropy_u2r[i]=iGain;

        } 
        System.out.println();
        double weights_u2r[]=new double[data.numAttributes()-1];
        double weights_r2l[]=new double[data.numAttributes()-1];
        double max_entropy=0.0;
        for(int i=0;i<data.numAttributes()-1;i++)
        {
        if(max_entropy<entropy_u2r[i])
            max_entropy=entropy_u2r[i];
        }
        System.out.println(max_entropy);
        for(int i=0;i<data.numAttributes()-1;i++)
        {
           if((freq1_u2r[i]/52)<=0.5)
               weights_u2r[i]=(entropy_u2r[i])/2;
           else
               weights_u2r[i]=(2*max_entropy-entropy_u2r[i])/2;
        }    
        double probability_u2r[]=new double[data.numAttributes()];
        for(int i=0;i<data.numAttributes()-1;i++)
        {
            probability_u2r[i]=freq1_u2r[i]/52;
        }    
        for(int i=0;i<data.numAttributes()-1;i++)
           System.out.println(probability_u2r[i]+"   "+weights_u2r[i]);
        
    }
    }
    

