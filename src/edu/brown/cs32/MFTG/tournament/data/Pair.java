package edu.brown.cs32.MFTG.tournament.data;

public class Pair<U,T> {
	
	private U left;
	private T right;
	
	public Pair(U left, T right){
		this.left = left;
		this.right = right;
	}
	
	public U getLeft(){
		return left;
	}
	
	public T getRight(){
		return right;
	}
	
	public void setLeft(U left){
		this.left = left;
	}
	
	public void setRight(T right){
		this.right = right;
	}
	
	@Override
	public String toString(){
		return String.format("(%s, %s)", (left == null) ? "null" : left.toString(), (right == null) ? "null" : right.toString());
	}
}
