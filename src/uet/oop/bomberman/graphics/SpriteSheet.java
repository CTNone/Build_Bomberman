package uet.oop.bomberman.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 */
public class SpriteSheet {

	private String _path; // đường dẫn tới ảnh chứa tất cả sprite của game.
	public final int SIZE; // kích thước của ảnh sprite.
	public int[] _pixels; // một mảng chứa tất cả giá trị màu của các pixel trong ảnh sprite.
	public BufferedImage image; // ảnh sprite được lưu trữ trong một đối tượng `BufferedImage`.

	public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);

	public SpriteSheet(String path, int size) { // đường dẫn đến ảnh sprite và kích thước của sprite.
		_path = path;
		SIZE = size;
		_pixels = new int[SIZE * SIZE];
		load();
	}
	
	private void load() {
		try {
			URL a = SpriteSheet.class.getResource(_path); // đọc ảnh sprite
			image = ImageIO.read(a); // lưu vào image
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, _pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
