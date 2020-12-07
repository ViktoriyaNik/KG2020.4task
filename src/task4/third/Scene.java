package task4.third;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import models.Line3D;
import task4.draw.IDrawer;
import task4.math.Vector3;

/**
 * Описывает трёхмерную со всеми объектами на ней
 *
 * @author Alexey
 */
public class Scene {
    private List<IModel> models = new ArrayList<>();

    public List<IModel> getModelsList() {
        return models;
    }

    RotateConverter rc = new RotateConverter();


    private int backgroundColor;

    /**
     * Создаём сцену с заданным фоном
     *
     * @param backgroundColorRGB цвет фона.
     */
    public Scene(int backgroundColorRGB) {
        this.backgroundColor = backgroundColorRGB;
        this.showAxes = false;
    }

    public  RotateConverter getRc(){
        return rc;
    }

    private boolean showAxes;

    public boolean isShowAxes() {
        return showAxes;
    }

    public void setShowAxes(boolean showAxis) {
        this.showAxes = showAxis;
    }

    public void showAxes() {
        this.showAxes = true;
    }

    public void hideAxes() {
        this.showAxes = false;
    }

    private static final List<Line3D> axes = Arrays.asList(
            new Line3D(new Vector3(0, 0, 0), new Vector3(1, 0, 0)),
            new Line3D(new Vector3(0, 0, 0), new Vector3(0, 1, 0)),
            new Line3D(new Vector3(0, 0, 0), new Vector3(0, 0, 1))
    );

    /**
     * Рисуем сцену со всеми моделями
     *
     * @param drawer то, с помощью чего будем рисовать
     * @param cam    камера для преобразования координат
     */
    public void drawScene(IDrawer drawer, ICamera cam) {
        List<PolyLine3D> lines1 = new LinkedList<>();
        List<PolyLine3D> lines2 = new LinkedList<>();
        LinkedList<Collection<? extends IModel>> allModels = new LinkedList<>();
        LinkedList<Collection<? extends IModel>> listAxes = new LinkedList<>();
        allModels.add(models);
        //  listAxes.add(models);
        /*Если требуется, то добавляем оси координат*/
        if (isShowAxes())
            listAxes.add(axes);
        for (Collection<? extends IModel> mc : listAxes)
            for (IModel m : mc) {
                for (PolyLine3D pl : m.getLines()) {
                    /*Все точки конвертируем с помощью камеры*/
                    List<Vector3> points = new LinkedList<>();
                    for (Vector3 v : pl.getPoints()) { // преобразования
                        points.add(cam.w2s(v));
                    }
                    /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                    lines1.add(new PolyLine3D(points, pl.isClosed()));
                }
            }
        /*перебираем все полилинии во всех моделях*/
        for (Collection<? extends IModel> mc : allModels)
            for (IModel m : mc) {
                for (PolyLine3D pl : m.getLines()) {
                    /*Все точки конвертируем с помощью камеры*/
                    List<Vector3> points = new LinkedList<>();
                    for (Vector3 v : pl.getPoints()) { // преобразования
                        v = rc.v2v(v);
                        points.add(cam.w2s(v));
                        //points.add(cam.w2s(v));
                        //points.add(cam.w2s(rc.v2v(v)));
                    }

                    /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                    lines2.add(new PolyLine3D(points, pl.isClosed()));
                }
            }
        //rc.changeMatrix();
        /*Закрашиваем фон*/
        drawer.clear(backgroundColor);
        /*Рисуем все линии*/
        drawer.draw(lines1);
        drawer.draw(lines2);
    }
}
