package presenter;

import java.io.Serializable;

public class Properties implements Serializable {
	
	private static final long serialVersionUID = 42L;
	int xSize;
	int ySize;
	int zSize;
	int numberOfThreads;
	String algorithemForSolution;
	

	public Properties() {
		this.xSize = 7;
		this.ySize = 7;
		this.zSize = 7;
		this.numberOfThreads = 10;
		algorithemForSolution = "AStar with ManhattanDistance";
	}


	public int getxSize() {
		return xSize;
	}


	public void setxSize(int xSize) {
		this.xSize = xSize;
	}


	public int getySize() {
		return ySize;
	}


	public void setySize(int ySize) {
		this.ySize = ySize;
	}


	public int getzSize() {
		return zSize;
	}


	public void setzSize(int zSize) {
		this.zSize = zSize;
	}


	public int getNumberOfThreads() {
		return numberOfThreads;
	}


	public void setNumberOfThreads(int numberOfThreads) {
		this.numberOfThreads = numberOfThreads;
	}


	public String getAlgorithemForSolution() {
		return algorithemForSolution;
	}


	public void setAlgorithemForSolution(String algorithemForSolution) {
		this.algorithemForSolution = algorithemForSolution;
	}
	
	
	

}
