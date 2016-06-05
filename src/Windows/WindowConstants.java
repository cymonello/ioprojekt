package Windows;

/**
 * Ta klasa jest jedynie po to aby w jednym miejscu przechowywać wszystkie stałe
 * parametry dla okna, nie byłoby sensu przechowywania ich w wielu miejscach
 * ewentualana późniejsza zmiana w tym miejscy powoduje zmiane tych parametrów w
 * całym programie
 *
 * @author Bartłomiej
 */
abstract public class WindowConstants {

    /**
     * Wysokość okna w pikselach
     */
    public static final int HEIGHT = 600;
    /**
     * Szerokosć okna w pikselach
     */
    public static final int WIDTH = 1000;
    /**
     * Granica pomięzy lewą a prawą częścią programu, odleglosc mierzona w
     * pikselach o lewej srony okna
     */
    public static final int BORDER = 200;
    /**
     * Aktualny schemat kolorystyczny, możliwy do zmienienia przez urzytkownika
     * w trakcie działania programu
     */
    volatile public static Colors schematKolorow = Colors.SCHEMAT0;
    /**
     * Czcionka dla normlanego tekstu
     */
    public static final String czcionka = "Arial";
    /**
     * Czciona dla tekstu który ma zostać wyróżniony
     */
    public static final String czcionka2 = "Arial Black";
}
