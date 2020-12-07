package task4.draw;

import task4.third.PolyLine3D;

import java.util.Collection;

/**
 * Интерфейс, описывающий в общем виде процесс рисования полилинии
 * @author Alexey
 */
public interface IDrawer {
    /**
     * Очищает область заданным цветом
     * @param color цвет
     */
    public void clear(int color);

    /**
     * Рисует все полилинии
     * @param polyline набор рисуемых полилиний.
     */
    public void draw(Collection<PolyLine3D> polyline);
}
