
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;

import javax.swing.SwingUtilities;

public class MandelbrotSet extends ImagePanel {

    private final Complex Z0 = new Complex(0, 0);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainApp(new MandelbrotSet());
            }
        });
    }

    public MandelbrotSet() {
        super(400, 400, new Complex(-0.5, 0.0));
    }

    protected BufferedImage computeImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // x, y are pixels
        pixels.stream().map(p -> new SimpleEntry<Point, Complex>(p, getComplex(p))).forEach(entry -> {
            Point p = entry.getKey();
            Complex c = entry.getValue();
            // on part de 0, 0
            Complex z = Z0;
            int i = MAX_ITER;
            while (z.module_square() < 4 && i > 0) {
                z = z.multiply(z).add(c);
                i--;
            }
            int color = Color.HSBtoRGB((i % 117) / 116f, (i % 17) / 16f, i / (float)MAX_ITER);
            image.setRGB((int) p.getX(), (int) p.getY(), color);
        });

        return image;
    }

    @Override
    protected String getTitle() {
        return "Mandelbrot set";
    }
}
