//package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		if (poly1 == null)
		{
			return poly2;
		}
		else if (poly2 == null)
		{
			return poly1;
		}
		
		Node newNode = null;
		
		if (poly1.term.degree < poly2.term.degree)
		{
			newNode = poly1;
			poly1=poly1.next;
		}
		else if (poly1.term.degree > poly2.term.degree)
		{
			newNode = poly2;
			poly2=poly2.next;
		}
		else
		{
			newNode = poly1;
			newNode.term.coeff = poly1.term.coeff + poly2.term.coeff;
			poly1 = poly1.next;
			poly2 = poly2.next;
		}
		Node first = newNode;
		newNode.next = null;


		while(true)
		{
			if(poly2 == null && poly1 == null)
			{
				break;
			}
			else if (poly2 == null)
			{
				newNode.next = poly1;
				break;
			}
			else if(poly1 == null)
			{
				newNode.next = poly2;
				break;
			}


			if (poly1.term.degree < poly2.term.degree)
			{
				newNode.next = poly1;
				poly1=poly1.next;
			}
			else if (poly1.term.degree > poly2.term.degree)
			{
				newNode.next = poly2;
				poly2=poly2.next;
			}
			else
			{
				newNode.next = poly1;
				newNode.next.term.coeff = poly1.term.coeff + poly2.term.coeff;
				poly1 = poly1.next;
				poly2 = poly2.next;
			}
			
			newNode = newNode.next;
			newNode.next=null;
		}
		
		return removeZeros(first);
		
	}
	
	
	private static Node removeZeros(Node p1)
	{
		Node head = p1;
		while (p1 != null && p1.term.coeff == 0.0)
		{
			p1 = p1.next;
			head = p1;
		}
		Node myNode = head.next;
		p1 = p1.next;
		while (p1 != null)
		{
			if (p1.next != null && p1.next.term.coeff == 0.0)
			{
				p1 = p1.next.next;
			}
			else
			{
				p1 = p1.next;
			}
			myNode.next = p1;
			myNode = myNode.next;
			
		}
		
		return head;
	}
	
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node newNode = new Node(0,0,null);
		Node poly1save = poly1;
		
		while (poly2 != null) 
		{
			int coeff = (int)poly2.term.coeff;
			int degree = (int)poly2.term.degree;
			if (coeff == 0)
			{
				continue;
			}
			
			Node myNode = new Node(0,0,null);
			Node first = null;
			boolean isFirst = true;
			while (poly1 != null ) 
			{
				myNode.term.coeff = poly1.term.coeff * coeff;
				myNode.term.degree = poly1.term.degree + degree;	
				myNode.next = new Node(0,0,null);		
				if (isFirst == true)
				{
					first = myNode;
					isFirst = false;
				}
				myNode = myNode.next;
				poly1 = poly1.next;
				
			}
			newNode = add(newNode, first);
			poly2 = poly2.next;
			poly1 = poly1save;
		}
		
		return removeZeros(newNode);
		
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		float total = 0;
		
		for (Node ptr = poly; ptr != null; ptr = ptr.next)
		{
			total += (Math.pow(x,ptr.term.degree))*(ptr.term.coeff);
		}
		
		return total;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
