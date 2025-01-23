public class Complex {

    private final double re;
    private final double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double module_square() {
        return re*re + im*im;
    }

    public Complex multiply(final Complex other) {
        return new Complex(this.re * other.re - this.im * other.im, this.re * other.im + this.im * other.re);
    }

    public Complex add(final Complex other) {
        return new Complex(this.re + other.re,  this.im + other.im);
    }
    public Complex minus(final Complex other) {
        return new Complex(this.re - other.re,  this.im - other.im);
    }
    public Complex scale(double scale) {
        return new Complex(this.re * scale, this.im * scale);
    }

    @Override
    public String toString() {
        return this.re + " + " + this.im + " i";
    }
}