package presenter;

import java.io.Serializable;
/**
 *Properties  class - Serializable the Observer
 * manage the Properties of the program
 */
public class Properties implements Serializable {
	
	private static final long serialVersionUID = 42L;
	int xSize;
	int ySize;
	int zSize;
	int port;
	String ip;
	String name;
	String ui;
	
/**
 * Constructor of Properties
 */
	public Properties() {
		this.xSize = 7;
		this.ySize = 7;
		this.zSize = 7;
		this.port = 5400;
		this.ip = "127.0.0.1";
		this.ui = "GUI";
		this.name = "DefualtName";
	}
	
	/**
	 * get the ui
	 * @return -ui
	 */

	public String getUi() {
		return ui;
	}


	/**
	 * set the ui
	 * @param -ui
	 */
	public void setUi(String ui) {
		this.ui = ui;
	}


	/**
	 * get the x size
	 * @return int x
	 */
	public int getXSize() {
		return xSize;
	}
	/**
	 * set the x size
	 * @param int x size
	 */

	public void setXSize(int xSize) {
		this.xSize = xSize;
	}

	/**
	 * get the y size
	 * @return int y
	 */
	public int getYSize() {
		return ySize;
	}

	/**
	 * set the x size
	 * @param int x size
	 */
	public void setYSize(int ySize) {
		this.ySize = ySize;
	}

	/**
	 * get the z size
	 * @return int z
	 */
	public int getZSize() {
		return zSize;
	}
	/**
	 * set the x size
	 * @param int x size
	 */
	public void setZSize(int zSize) {
		this.zSize = zSize;
	}

	/**
	 * set maze name
	 * @param String name
	 */
	public void setName(String name) {
		this.name = name;		
	}
	/**
	 * get maze name
	 * @return String name
	 */
	public String getName() { return this.name; }

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	

}
