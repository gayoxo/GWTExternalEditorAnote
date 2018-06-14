package ucm.fdi.ilsa.client;

public class ElementTempCoordenadasAnotacion {

	private Long x;
	private Long y;
	private Long width;
	private Long height;

	public ElementTempCoordenadasAnotacion() {
	}

	public ElementTempCoordenadasAnotacion(Long x, Long y, Long width, Long height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getX() {
		return x;
	}

	public void setX(Long x) {
		this.x = x;
	}

	public Long getY() {
		return y;
	}

	public void setY(Long y) {
		this.y = y;
	}

	public String getTextCoordinates() {
		return ("(" + x + "," + y + ")");

	}
}
