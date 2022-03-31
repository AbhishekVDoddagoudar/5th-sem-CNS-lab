 import java.io.*;
import java.util.*;
public class CRC
{
    public static void main(String[] args)
    {
        Scanner s=new Scanner (System.in);
        int[] data;
        int[] msg;
        int[] generator;
        int[] rem;
        int[] crc;
        int i,data_bits,generator_bits,tot_length;
        System.out.println("Enter the number of data bits:");
        data_bits =s.nextInt();
        data=new int[data_bits ];
        System.out.println("Enter the data bits");

        for(i=0;i<data_bits;i++)
            data[i]  =s.nextInt();

        System.out.println("Enter number of bits in generator" );
        generator_bits=s.nextInt();
        generator =new int[generator_bits ];
        System.out.println("Enter the generator bits:");

        for(i=0;i<generator_bits;i++)
            generator[i]=s.nextInt();

        tot_length =data_bits+generator_bits-1;
        msg=new int[tot_length ];
        rem=new int[tot_length ];
        crc=new int[tot_length ];
/*------------CRC generation---------------------*/
        for(i=0;i<data.length;i++)
            msg[i]=data[i];

        System.out.println("Message after appending 0's:");

        for(i=0;i<msg.length;i++)
            System.out.print(" "+msg[i]);

        System.out.println();

        for(i=0;i<msg.length;i++)
            rem[i]=msg[i];

        rem=xor(generator,rem);

        for(i=0;i<msg.length;i++)
            crc[i]=msg[i]^rem[i];

        System.out.println();
        System.out.println("CRC code:");

        for(i=0;i<crc.length;i++)
            System.out.print(" "+crc[i]);
/*------------Error detection-------------------*/
        System.out.println();
        System.out.println("Enter crc code of "+tot_length+" bits:");

        for(i=0;i<crc.length;i++)
            rem[i]=s.nextInt();

        rem=xor(generator,rem);

        for(i=0;i<rem.length;i++)
            System.out.print(" "+rem[i]);

        System.out.println();
        for(i=0;i<rem.length;i++)
        {
            if(rem[i]!=0)
            {
                System.out.println("Error");
                break;
            }
            if(i==rem.length-1)
                System.out.println("No error");
        }
        System.out.println("Thank YOU ...... :)");
    }
    static int[] xor(int generator[],int rem[])
    {
        int i,cur=0;
        while(true)
        {
            for(i=0;i<generator.length;i++)
                rem[cur+i]=rem[cur+i]^generator[i];
            while(rem[cur]==0 && cur!=rem.length-1)
                cur++;
            if((rem.length-cur)<generator.length)
                break;
        }
        return rem;
    }
}


