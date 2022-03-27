import java.util.*;
import java.math.BigInteger;
import java.lang.*;
public class RSAKeygen
{
	public static void main(String args[])
	{
		Random r1 = new Random(System.currentTimeMillis());
		Random r2 = new Random(System.currentTimeMillis()*10);
		int e=Integer.parseInt(args[0]);
		BigInteger p=BigInteger.probablePrime(32, r1);
		BigInteger q=BigInteger.probablePrime(32, r2);
		BigInteger n=p.multiply(q);
		BigInteger p1=p.subtract(new BigInteger("1"));
		BigInteger q1=q.subtract(new BigInteger("1"));
		BigInteger phi=p1.multiply(q1);
		while(true)
		{
			BigInteger GCD1=phi.gcd(new BigInteger(""+e));
			if(GCD1.equals(BigInteger.ONE))
			{
				break;
			}
			e++;
		}
		BigInteger pubkey=new BigInteger(""+e);
		BigInteger prvkey=pubkey.modInverse(phi);
		System.out.println("public key : "+pubkey+","+n);
		System.out.println("private key : "+prvkey+","+n);
	}
}

