import java.math.BigInteger;
import java.util.*;
class RSAEncDec
{
	public static void main(String args[])
	{
		BigInteger pubkey = new BigInteger(args[0]);
		BigInteger prvkey = new BigInteger(args[1]);
		BigInteger n = new BigInteger(args[2]);
		int m=Integer.parseInt(args[3]);
		BigInteger val=new BigInteger(""+m);
		BigInteger cipher=val.modPow(pubkey,n);
		System.out.println("Cipher text: " + cipher);
		BigInteger plain=cipher.modPow(prvkey,n);
		int plainVal=plain.intValue();
		System.out.println("Plain text:" + plainVal);
	}
}

