package task4.math;

import java.awt.image.renderable.ContextualRenderedImageFactory;

/**
 * Класс, хранящий методы создания особых матриц.
 *
 * @author Alexey
 */
public final class Matrix4Factories {
    /**
     * Продублированный метод создания нулевой матрицы
     *
     * @return нулевая матрица
     */
    public static Matrix4 zero() {
        return Matrix4.zero();
    }

    /**
     * Продублированный метод создания единичной матрицы
     *
     * @return единичная матрица
     */
    public static Matrix4 one() {
        return Matrix4.one();
    }

    /**
     * Создаёт матрицу поворота вокруг одной из осей,
     * заданной с помощью индекса на указанный угол в радианах.
     * Индексы осей: X=0, Y=1, Z=2.
     * В случае некорректного индекса, возваращается единичная матрица.
     * !!! надо кидать в этом случае exception !!!
     *
     * @param alpha     угол поворота
     * @param axisIndex индекс оси
     * @return Матрица поворота или единичная матрица.
     */
    public static Matrix4 rotationXYZ(double alpha, int axisIndex) {
        Matrix4 m = one();
        if (axisIndex < 0 || axisIndex > 2)
            return m;
        int a1 = (axisIndex + 1) % 3;
        int a2 = (axisIndex + 2) % 3;

        m.setAt(a1, a1, (float) Math.cos(alpha));
        m.setAt(a1, a2, (float) Math.sin(alpha));
        m.setAt(a2, a1, -(float) Math.sin(alpha));
        m.setAt(a2, a2, (float) Math.cos(alpha));

        return m;
    }

    public static Matrix4 reversMatrix(Matrix4 matrix) {
        double[][] m = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = matrix.getAt(i, j);
            }
        }
        double[][] minorMatrix = new double[4][4];
        if (detMatrix(matrix) == 0) {
            return null;
        } else {
            System.out.println(1);
            minorMatrix[0][0] = m[1][1] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][1] + m[2][1] * m[3][2] * m[1][3] -
                    m[1][3] * m[2][2] * m[3][1] - m[1][2] * m[2][1] * m[3][3] - m[2][3] * m[3][2] * m[1][1];
            minorMatrix[0][1] = -(m[0][1] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][0] + m[2][0] * m[3][2] * m[1][3] -
                    m[1][3] * m[2][2] * m[3][0] - m[2][3] * m[3][2] * m[1][0] - m[3][3] * m[1][2] * m[2][0]);
            minorMatrix[0][2] = m[1][0] * m[2][1] * m[3][3] + m[1][1] * m[2][3] * m[3][0] + m[2][0] * m[3][1] * m[1][3] -
                    m[1][3] * m[2][1] * m[3][0] - m[2][3] * m[3][1] * m[1][0] - m[1][1] * m[2][0] * m[3][3];
            minorMatrix[0][3] = -(m[1][0] * m[2][1] * m[3][2] + m[2][0] * m[3][1] * m[1][2] + m[1][1] * m[2][2] * m[3][0] -
                    m[1][2] * m[2][1] * m[3][0] - m[2][2] * m[3][1] * m[1][0] - m[1][1] * m[2][0] * m[3][2]);
            minorMatrix[1][0] = -(m[0][1] * m[2][2] * m[3][3] + m[0][2] * m[2][3] * m[3][1] + m[0][3] * m[3][2] * m[2][1] -
                    m[0][3] * m[2][2] * m[3][1] - m[0][2] * m[2][1] * m[3][3] - m[2][3] * m[3][2] * m[0][1]);
            minorMatrix[1][1] = m[0][0] * m[2][2] * m[3][3] + m[0][2] * m[2][3] * m[3][0] + m[2][0] * m[3][2] * m[0][3] -
                    m[0][3] * m[2][2] * m[3][0] - m[2][3] * m[3][2] * m[0][0] - m[3][3] * m[0][2] * m[2][0];
            minorMatrix[1][2] = -(m[0][0] * m[2][1] * m[3][3] + m[0][1] * m[2][3] * m[3][0] + m[2][0] * m[3][1] * m[0][3] -
                    m[0][3] * m[2][1] * m[3][0] - m[2][3] * m[3][1] * m[0][0] - m[0][1] * m[2][0] * m[3][3]);
            minorMatrix[1][3] = m[0][0] * m[2][1] * m[3][2] + m[2][0] * m[3][1] * m[0][2] + m[0][1] * m[2][2] * m[3][0] -
                    m[0][2] * m[2][1] * m[3][0] - m[2][2] * m[3][1] * m[0][0] - m[0][1] * m[2][0] * m[3][2];
            minorMatrix[2][0] = m[0][1] * m[1][2] * m[3][3] + m[0][2] * m[1][3] * m[3][1] + m[0][3] * m[3][2] * m[1][1] -
                    m[0][3] * m[1][2] * m[3][1] - m[0][2] * m[1][1] * m[3][3] - m[1][3] * m[3][2] * m[0][1];
            minorMatrix[2][1] = -(m[0][0] * m[1][2] * m[3][3] + m[0][2] * m[1][3] * m[3][0] + m[1][0] * m[3][2] * m[0][3] -
                    m[0][3] * m[1][2] * m[3][0] - m[1][3] * m[3][2] * m[0][0] - m[3][3] * m[0][2] * m[1][0]);
            minorMatrix[2][2] = m[0][0] * m[1][1] * m[3][3] + m[0][1] * m[1][3] * m[3][0] + m[1][0] * m[3][1] * m[0][3] -
                    m[0][3] * m[1][1] * m[3][0] - m[1][3] * m[3][1] * m[0][0] - m[0][1] * m[1][0] * m[3][3];
            minorMatrix[2][3] = -(m[0][0] * m[1][1] * m[3][2] + m[1][0] * m[3][1] * m[0][2] + m[0][1] * m[1][2] * m[3][0] -
                    m[0][2] * m[1][1] * m[3][0] - m[1][2] * m[3][1] * m[0][0] - m[0][1] * m[1][0] * m[3][2]);
            minorMatrix[3][0] = -(m[0][1] * m[1][2] * m[2][3] + m[0][2] * m[1][3] * m[2][1] + m[0][3] * m[2][2] * m[1][1] -
                    m[0][3] * m[1][2] * m[2][1] - m[0][2] * m[1][1] * m[2][3] - m[1][3] * m[2][2] * m[0][1]);
            minorMatrix[3][1] = m[0][0] * m[1][2] * m[2][3] + m[0][2] * m[1][3] * m[2][0] + m[1][0] * m[2][2] * m[0][3] -
                    m[0][3] * m[1][2] * m[2][0] - m[1][3] * m[2][2] * m[0][0] - m[2][3] * m[0][2] * m[1][0];
            minorMatrix[3][2] = -(m[0][0] * m[1][1] * m[2][3] + m[0][1] * m[1][3] * m[2][0] + m[1][0] * m[2][1] * m[0][3] -
                    m[0][3] * m[1][1] * m[2][0] - m[1][3] * m[2][1] * m[0][0] - m[0][1] * m[1][0] * m[2][3]);
            minorMatrix[3][3] = m[0][0] * m[1][1] * m[2][2] + m[1][0] * m[2][1] * m[0][2] + m[0][1] * m[1][2] * m[2][0] -
                    m[0][2] * m[1][1] * m[2][0] - m[1][2] * m[2][1] * m[0][0] - m[0][1] * m[1][0] * m[2][2];
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix.setAt(i, j, -minorMatrix[j][i]);
               // System.out.print(minorMatrix[j][i]);
            }
            //System.out.println();
        }
        return matrix;
    }

    public static double detMatrix(Matrix4 matrix) {
        double[][] m = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                m[i][j] = matrix.getAt(i, j);
            }
        }
        double det = m[0][0] * (m[1][1] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][1] + m[2][1] * m[3][2] * m[1][3] -
                m[1][3] * m[2][2] * m[3][1] - m[1][2] * m[2][1] * m[3][3] - m[2][3] * m[3][2] * m[1][1]) +
                m[0][1] * (m[0][1] * m[2][2] * m[3][3] + m[1][2] * m[2][3] * m[3][0] + m[2][0] * m[3][2] * m[1][3] -
                        m[1][3] * m[2][2] * m[3][0] - m[2][3] * m[3][2] * m[1][0] - m[3][3] * m[1][2] * m[2][0]) +
                m[0][2] * (m[1][0] * m[2][1] * m[3][3] + m[1][1] * m[2][3] * m[3][0] + m[2][0] * m[3][1] * m[1][3] -
                        m[1][3] * m[2][1] * m[3][0] - m[2][3] * m[3][1] * m[1][0] - m[1][1] * m[2][0] * m[3][3]) +
                m[0][3] * (m[1][0] * m[2][1] * m[3][2] + m[2][0] * m[3][1] * m[1][2] + m[1][1] * m[2][2] * m[3][0] -
                        m[1][2] * m[2][1] * m[3][0] - m[2][2] * m[3][1] * m[1][0] - m[1][1] * m[2][0] * m[3][2]);

        return det;
    }

    public static enum Axis {X, Y, Z}

    ;

    /**
     * Создаёт матрицу поворота вокруго одной из осей на заданный угол в радианах
     *
     * @param alpha угол в радианах
     * @param axis  название оси, вокруг которой происходит вращение
     * @return матрица поворота
     */
    public static Matrix4 rotationXYZ(double alpha, Axis axis) {
        return rotationXYZ(alpha, axis == Axis.X ? 0 : axis == Axis.Y ? 1 : 2);
    }

    /**
     * Создаёт новую матрицу переноса по заданным параметрам
     *
     * @param x X-составялющая смещения
     * @param y Y-составляющая смещения
     * @param z Z-составляющая смещения
     * @return матрица переноса
     */
    public static Matrix4 translation(double x, double y, double z) {
        Matrix4 m = one();
        m.setAt(0, 3, x);
        m.setAt(1, 3, y);
        m.setAt(2, 3, z);
        return m;
    }

    /**
     * Создаёт новую матрицу переноса на указанный вектор
     *
     * @param v вектор, на который производится перенос
     * @return матрица переноса
     */
    public static Matrix4 translation(Vector3 v) {
        return translation(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Создаёт матрицу масштабирования по заданным параметрам
     *
     * @param factorX масштабирование вдоль оси X
     * @param factorY масштабирование вдоль оси Y
     * @param factorZ масштабирование вдоль оси Z
     * @return матрица масштабирования
     */
    public static Matrix4 scale(float factorX, float factorY, float factorZ) {
        Matrix4 m = one();
        m.setAt(0, 0, factorX);
        m.setAt(1, 1, factorY);
        m.setAt(2, 2, factorZ);
        return m;
    }

    /**
     * Создаёт матрицу масштабирования с одинаковыми коэффициентами по всем осям.
     *
     * @param factor коэффициент масштабирования
     * @return матрица масштабирования
     */
    public static Matrix4 scale(float factor) {
        return scale(factor, factor, factor);
    }

    /**
     * Создаёт матрицу центральной проекции вдоль выбранной оси
     *
     * @param point     точка схода на этой оси
     * @param axisIndex номер оси (X=0, Y=1, Z=2)
     * @return Матрица проекции
     */
    public static Matrix4 centralProjection(float point, int axisIndex) {
        Matrix4 m = one();
        if (axisIndex < 0 || axisIndex > 2)
            return m;
        m.setAt(3, axisIndex, 1 / point);
        return m;
    }

    /**
     * Создаёт матрицу центральной проекции вдоль выбранной оси
     *
     * @param point точка схода на этой оси
     * @param axis  название оси
     * @return Матрица проекции
     */
    public static Matrix4 centralProjection(float point, Axis axis) {
        return centralProjection(point, axis == Axis.X ? 0 : axis == Axis.Y ? 1 : 2);
    }
}
