package net.barakiroth.hellostrangeworld.frontend;

import java.util.Scanner;

public class UserPrompter {
	
	public String askWhomTheUserWantsToGreet() {	
		
		System.out.print("Whom do you want to greet? ");
	    Scanner scanner = null;
	    final String greetee;
	    try {
	    	scanner = new Scanner(System.in);
		    greetee = scanner.next();
	    } finally {
	    	scanner.close();
		}

	    
		return greetee;
	}
}
