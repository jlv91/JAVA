import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.IntStream;

import javax.swing.JPanel;

public abstract class ImagePanel extends JPanel {

    private final double ZOOM_FACTOR = 1.2;
    protected final int MAX_ITER = 500;
    
    protected final int width;
    protected final int height;

    private Complex translation;
    private int zoomLevel;

    protected Collection<Point> pixels = new LinkedList<>();

    public ImagePanel(int width, int height, Complex translation) {
        this.width = width;
        this.height = height;
        this.translation = translation;
        this.zoomLevel = 0;

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.white);

        IntStream.range(0, width).forEach(x -> IntStream.range(0, height).mapToObj(y -> new Point(x, y)).forEach(pixels::add));
    }

    public Complex getComplex(Point p) {
        // w/2, h/2 is the center of the screen, y is oriented downwards
        return new Complex((2f*p.getX() / width - 1f),  ( 1f - 2f*p.getY() / height)).scale(Math.pow(ZOOM_FACTOR, -zoomLevel)).add(translation);
    }

    public void zoomIn() {
        zoomLevel++;
    }

    public void zoomOut() {
        zoomLevel--;
    }

    public void addTranslation(Point finalPoint, Point initialPoint) {
        Complex zf = getComplex(finalPoint);
        Complex zi = getComplex(initialPoint);
        translation = translation.minus(zf.minus(zi));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        BufferedImage image = computeImage();
        g.drawImage(image, 0, 0, null);
    }
        
    protected abstract BufferedImage computeImage();
}
