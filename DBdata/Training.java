package DBdata;

import java.util.Random;

public class Training {

	public static void main(String[] args) {
		String pass="sbfsfljksad",password="";
		for(int i =0;i<pass.length();i++){
			password+="*";

		}
		System.out.println(password);

		Random rand = new Random();
		int addpin = rand.nextInt(100000,999999);
		System.out.println(addpin);

		SignUp il=new SignUp();
		System.out.println(il.Phone_Number);

		// System.out.println("wats wrong");
		// //incrementing & decrementing

		// int m=4;
		// int n=5;
		// n++;//post-increment
		// ++n;//pre-increment
		// m--;//post-increment
		// --m;//pre-increment
		// //how it can be used
		// System.out.print("yours is even obvious you didn't�add�;");
		// System.out.println(n);
		// System.out.println(m);
		// int e=7;

		// if(n%2==0)
		// System.out.println(e+" is an even number");
		// if(n%2!=0)
		// System.out.println(e+" is an odd number");
		// else if(n==0)
		// System.out.println("nothing");
		// else
		// System.out.println(e+" is an odd number");

		// //Ternary operator
		// //?: ->condition?expresion1:expression2:more expressions;
		// if(n%2==0)
		// // System.out.println(e+" is an even number");

		// //Instead
		// e=n%2==0?1:2;
		// System.out.println(e);
		// String hello="hello";
		// hello.toUpperCase();
		// // System.out.println(hello);

		// find meaning and red on it
		// api,jdk,jre,jvm,jdbc,jvn,bytecode,classloader,.javafile,.class
		/*
		 * a jvm is under the jre(java runtime enviroment) and jdk(java development
		 * kit)which helps with debugging and monitoring ,the java class stores the code
		 * and wen the
		 * user runs it the java virtual machine converts or interpret it to the
		 * computer bytecode using the jit (just-in-time) compiler
		 * and the jre comprises of the whole library of ur java software dat keeps all
		 * of ur java virtual machine
		 * API(application programing interface)
		 */

	}

}
