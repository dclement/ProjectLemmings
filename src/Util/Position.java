package Util;

/**
 * Classe utilitaire pour la position des murs à partir de la lecture du fichier xml
 * @author Clement
 *
 */
public class Position {

	private int x;
	private int y;
	private int width;
	private int height;
	
	/**
	 * Constructeur
	 * @param x position x du wall
	 * @param y podition y du wall
	 * @param width longueur du wall
	 * @param height hauteur du wall
	 */
	public Position(int x, int y, int width, int height) 
	{
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
