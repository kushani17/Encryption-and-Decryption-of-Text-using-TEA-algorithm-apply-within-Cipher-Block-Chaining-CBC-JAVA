import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.*;


class CBC_Class  {
	long[] Keys = new long[4];
	
	CBC_Class ()
	{
		
	}
	CBC_Class  (long[] Keys)
	{
		this.Keys[0] = Keys[0];
		this.Keys[1] = Keys[1];
		this.Keys[2] = Keys[2];
		this.Keys[3] = Keys[3];
		
		
	}
	

	public long[] TEAEncryption(long[] msg)  // method which used TEAEncryption to performs encryption
	{
		
		long m = msg[0];
		long n = msg[1];
		 long delta = 0x9e3779b9;
		long sum =0;
		long[] result = new long[2];
		
		for(int i=0;i<32;i++)
		{
			sum+= delta;
			m+= ((n << 4) + Keys[0]) ^ (n+sum) ^ ((n >> 5) + Keys[1]);  
			n+= ((m << 4) + Keys[2]) ^ (m+sum) ^((m >> 5) + Keys[3]);  
		}
		
		result[0] = m;
		result[1] = n;
		
		return result;
		
		
		
	}
	
		
	public long[] encryption(long[] OriginalText, long[] PrevCypherText) // Method to do xor operation on block 
	{
		//This method first do encryption operation on previous ciphered text
		
		long xored[] = new long[2];
		xored[0] = OriginalText[0] ^ PrevCypherText[0];
		xored[1] = OriginalText[1] ^ PrevCypherText[1];
		
		return TEAEncryption(xored); // call TEAEnryption to get encrypted text
	}
	
	
	
	public long[] Decryption(long[] DecryptedText, long[] PrevCypherText) // Method to do xor operation on block 
	{
		
		long xored[] = new long[2];
		xored[0] = DecryptedText[0] ^ PrevCypherText[0];
		xored[1] = DecryptedText[1] ^ PrevCypherText[1];
		
		return xored; // return plain text
		
	}
	

	public long[] TEADecryption(long[] EncryptedText, long[] PrevCypherText) // method which used TEADecryption to performs encryption
	{	
		// this method do Decryption of encrypted blocks
		
		long m = EncryptedText[0];
	    long n = EncryptedText[1];
	    long delta = 0x9e3779b9;
	    long sum2 = delta << 5;
	
	long[] result = new long[2];
	
	for(int i=0;i<32;i++)
	{
		
		n-=  ((m << 4) + Keys[2]) ^ (m+sum2) ^((m >> 5) + Keys[3]);  
		m-= ((n << 4) + Keys[0]) ^ (n+sum2) ^((n >> 5) + Keys[1]);  
	    sum2-= delta;
	}
	
	result[0] = m;
	result[1] = n;
	
		return Decryption(result,PrevCypherText); //call method to perform xor operation to get plain text 
	}
	
}
public class TEA_CBC {
	public static void main(String args[]) throws InterruptedException  
	{
		try{
			
		
	System.out.println("enter input string to encrypt a message");
	@SuppressWarnings("resource")
	Scanner sc = new Scanner(System.in);
	String ip = sc.nextLine();
	System.out.println(ip);
	

	long[] TEAKeys= new long[4];
	long[] result = new long[2];
	
	 char[] b11 = new char[8];
	   char[] b22 = new char[8];
	   
	for(int i =0;i<4;i++)
	{
		TEAKeys[i] = 10 + new Random().nextLong();
		//System.out.println("genrated first random keys vector");
		//System.out.println(TEAKeys[i]);
	}
	

	
	ArrayList<String> msg = new ArrayList<String>(); //use to store blocks
	
	int[] PrevCypher = new int[2];
	
	CBC_Class c1 = new CBC_Class(TEAKeys);
	
	StringBuilder s = new StringBuilder();
	int c =0;
	int gc= 0;
	
	ArrayList<Long> encryptedText = new ArrayList<Long>();
	
	// below logic is used to convert message into blocks for certain size
	
	for(int i =0;i<ip.length();i++)
	{
		
			s.append(ip.charAt(i));
			
			gc = ++c;
		    
			if(c == 4)
			{
			   
			   c = 0;
			  // System.out.println(s.toString());
			   
			   msg.add(s.toString());
			   s.setLength(0);
			  
			   
			}
			
			
			
		
	}
	
	if(gc != 4)
	{
		while(s.length() != 4)
		   {
			   s.append(" ");
		   }
		c=0;
		//System.out.println("last string: "+s.toString());
		   msg.add(s.toString());
		   s.setLength(0);
	}
	
	//PrevCypher[0] = "abcd";
	//PrevCypher[1] = "efgh";
	
	b11 = msg.get(0).toCharArray();
	
	if(msg.size() == 1)
	{
		//System.out.println("block should minimum of 64 bits. please enter another message such as 'aaaabbbb'");
		 String s2 = "    "; // if block is not 64 bit then add space make 64 bit block
		   b22 = s2.toCharArray();
		    
		
	}
	
	else
	{
		b22 = msg.get(1).toCharArray();
	}
	
	
	
	
	int[] bk1 = new int[b11.length];
	int[] bk2 = new int[b22.length];
	
	long[] blocks = new long[2];
	
	for(int i =0;i<bk1.length;i++)
	{
		bk1[i] = (int) b11[i];
	}
	
	for(int i =0;i<bk2.length;i++)
	{
		bk2[i] = (int) b22[i];
	}
	
	StringBuilder sb1= new StringBuilder();
	StringBuilder sb2= new StringBuilder();
	for(int n: bk1)
	{
		sb1.append(n);
	}
	for(int n: bk2)
	{
		sb2.append(n);
	}

   
	blocks[0] =  Long.valueOf(sb1.toString());
	   blocks[1] = Long.valueOf(sb2.toString());
   
 //  System.out.println(blocks[0]);
 //  System.out.println(blocks[1]);
   
 //  PrevCypher[0] =50;
  // PrevCypher[1] = 70;
   
   //long[] TEAKeys= new long[4];
	//long[] result = new long[2];
   long ch[] = new long[2];
	for(int i =0;i<2;i++)
	{
		PrevCypher[i] = 10+ new Random().nextInt(70);
		//System.out.println("genrated first random intialization vector");
		//System.out.println(PrevCypher[i]);
	}
   
   // ch array is an initialization vector use for first block encryption and Decryption
	
   ch[0] = new Long(PrevCypher[0]);
   ch[1] = new Long(PrevCypher[1]);
   
   //System.out.println(ch[0]);
   //System.out.println(ch[1]);
   
   result = c1.encryption(blocks,ch); //call encryption method to performs encryption on first block
   
   encryptedText.add(result[0]);
   encryptedText.add(result[1]);
   
   System.out.print("encrypted result:" + result[0]+result[1]);
   
   
   //Encryption for other blocks
   int m =2;
   while(m < msg.size()) // condition is use when text is not mutliple of 64 bits then just encrypt and decypt portion of message which satisfies bits, which are multiple of 64 bits
   {
	   if(m == msg.size() -1)
	   {
		   b11  = msg.get(m).toCharArray();
		   String s2 = "    "; // if block is not 64 bit then add space make 64 bit block
		   b22 = s2.toCharArray();
		    
	   }
	   
	   else
	   {
	   
	   b11 = msg.get(m).toCharArray();
		 b22 = msg.get(m+1).toCharArray();
	   }
	  
		int[] bk11 = new int[b11.length];
		int[] bk22 = new int[b22.length];
		
	
		long[] blocks2 = new long[2];
		
		for(int i =0;i<bk11.length;i++)
		{
			bk11[i] = (int) b11[i];
		}
		
		for(int i =0;i<bk22.length;i++)
		{
			bk22[i] = (int) b22[i];
		}
		
		StringBuilder sb11= new StringBuilder();
		StringBuilder sb22= new StringBuilder();
		for(int n: bk11)
		{
			sb11.append(n);
		}
		for(int n: bk22)
		{
			sb22.append(n);
		}
	 
	   
		blocks2[0] =  Long.valueOf(sb11.toString());
		   blocks2[1] = Long.valueOf(sb22.toString());
		   
		   
		 //  System.out.println(blocks2[0]);
		  // System.out.println(blocks2[1]);
		   
		   ch[0] =result[0]; //use previous block encrypted result.
		   ch[1] = result[1];
		   
		 
		   result = c1.encryption(blocks2,ch);
		   encryptedText.add(result[0]);
		   encryptedText.add(result[1]);
		   
		   System.out.println(result[0]+result[1]);
		   m = m+2;
   }
   
   
   
   System.out.print("Decrypted result:");
   
   
   long[] encr = new long[2];
   encr[0] = encryptedText.get(0);
   encr[1] = encryptedText.get(1);
   
   
   long[] decrypted_result = new long[2];
  
   
  // PrevCypher[0] =50;
  // PrevCypher[1] = 70;
   
   
   ch[0] = new Long(PrevCypher[0]); 
   ch[1] = new Long(PrevCypher[1]);
   
  // System.out.println(ch[0]);
   //System.out.println(ch[1]);
   
   decrypted_result = c1.TEADecryption(encr,ch);
  
  // System.out.println(decrypted_result[0]);
  // System.out.println(decrypted_result[1]);	
   
   
 
 
   
   

  char[] p1 = (Long.toString(decrypted_result[0])).toCharArray();
   int[] tp11 = new int[p1.length];
   for(int i =0;i<p1.length;i++)
   {
	   tp11[i] = Character.getNumericValue(p1[i]);
	
   }
   
  

   

   char[] p2 = (Long.toString(decrypted_result[1])).toCharArray();
   int[] tp22 = new int[p2.length];
   for(int i =0;i<p2.length;i++)
   {
	   tp22[i] = Character.getNumericValue(p2[i]);
	  
   }
   
  
	   int k = 0;
		
		while(k<=tp11.length-2)
		{
		 
		   StringBuilder sb3= new StringBuilder();
		   sb3.append(tp11[k]);
			 sb3.append(tp11[k+1]);
			 int a = Integer.parseInt(sb3.toString());
			 
			 if(a == 32)
			 {
				 System.out.print(" "); // print plain(original) text 
				 k= k+2;
			 }
			 
			 
			 
			 else if((a >= 97 && a<= 122) || (a >=65 && a<= 90))
		 {
		   char c2= (char) a;
		 System.out.print(c2); // print plain(original) text
		 k =k +2;
	   }
		 
		 else
		 {
			sb3.append(tp11[k+2]);
			
			int a2 = Integer.parseInt(sb3.toString());
			
			
			
			
			 if((a2 >= 97 && a2<= 122) || (a2 >=65 && a2<= 90))
			 {
			   char c2= (char) a2;
			 System.out.print(c2);
		   }
			 k =k +3;
		 }
		 
	   
   }

   int k2 =0;
		while(k2<=tp22.length-2)
		{
		
		   StringBuilder sb4= new StringBuilder();
		   sb4.append(tp22[k2]);
			 sb4.append(tp22[k2+1]);
			 int a = Integer.parseInt(sb4.toString());
			 
			 
			 if(a == 32)
			 {
				 System.out.print(" ");
				 k2= k2+2;
			 }
			 
			 
			 
			 else if((a >= 97 && a<= 122) || (a >=65 && a<= 90))
		 {
		   char c2= (char) a;
		 System.out.print(c2);
		 k2= k2+2;
	   }
		 
		 else
		 {
			sb4.append(tp22[k2+2]);
			
			int a2 = Integer.parseInt(sb4.toString());
			 if((a2 >= 97 && a2<= 122) || (a2 >=65 && a2<= 90))
			 {
			   char c2= (char) a2;
			 System.out.print(c2);
		   }
			 k2 =k2 +3;
		 }
		
	  
   }
	   
		
		
		//for other block Decryption
		
		int n =2;
		while(n< encryptedText.size())
		{
			
			
			ch[0] = encr[0];
			   ch[1] = encr[1];
			   encr[0] = encryptedText.get(n);
			   encr[1] = encryptedText.get(n+1);
			  
			   
			   decrypted_result = c1.TEADecryption(encr,ch);
			
			 
			 //  System.out.println(decrypted_result[0]);
			  // System.out.println(decrypted_result[1]);	
			 

			  char[] pn1 = (Long.toString(decrypted_result[0])).toCharArray();
			   int[] tpn11 = new int[pn1.length];
			   for(int i =0;i<pn1.length;i++)
			   {
				   tpn11[i] = Character.getNumericValue(pn1[i]);
				
			   }
			   
			 
			  
			   char[] pn2 = (Long.toString(decrypted_result[1])).toCharArray();
			   int[] tpn22 = new int[pn2.length];
			   for(int i =0;i<pn2.length;i++)
			   {
				   tpn22[i] = Character.getNumericValue(pn2[i]);
				 
			   }
			   
			 
				   int kn = 0;
					
					while(kn<=tpn11.length-2)
					{
					 
					   StringBuilder sb3= new StringBuilder();
					   sb3.append(tpn11[kn]);
						 sb3.append(tpn11[kn+1]);
						
						 int a = Integer.parseInt(sb3.toString());
						 
						 if(a == 32)
						 {
							 System.out.print(" ");
							 kn= kn+2;
						 }
						 
						 else if((a >= 97 && a<= 122) || (a >=65 && a<= 90))
					 {
					   char c2= (char) a;
					 System.out.print(c2);
					 kn =kn +2;
				   }
					 
					 else
					 {
						sb3.append(tpn11[kn+2]);
						
						int a2 = Integer.parseInt(sb3.toString());
						 if((a2 >= 97 && a2<= 122) || (a2 >=65 && a2<= 90))
						 {
						   char c2= (char) a2;
						 System.out.print(c2);
					   }
						 kn =kn +3;
					 }
					 
				   
			   }
				   
			 
			   int kn2 =0;
					while(kn2<=tpn22.length-2)
					{
					 
				   
					   StringBuilder sb4= new StringBuilder();
					   sb4.append(tpn22[kn2]);
						 sb4.append(tpn22[kn2+1]);
						 int a = Integer.parseInt(sb4.toString());
						 
						 
						 if(a == 32)
						 {
							 System.out.print(" ");
							 kn2= kn2+2;
						 }
						 
						 
						 else if((a >= 97 && a<= 122) || (a >=65 && a<= 90))
					 {
					   char c2= (char) a;
					 System.out.print(c2);
					 kn2= kn2+2;
				   }
					 
					 else
					 {
						sb4.append(tpn22[kn2+2]);
						
						int a2 = Integer.parseInt(sb4.toString());
						 if((a2 >= 97 && a2<= 122) || (a2 >=65 && a2<= 90))
						 {
						   char c2= (char) a2;
						 System.out.print(c2);
					   }
						 kn2 =kn2 +3;
					 }
					
				  
			   }
			   
			   
			   
			   n=n+2;
			
		}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
   
	}


}
