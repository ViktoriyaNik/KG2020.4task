package task4.third;

import task4.math.Matrix4;
import task4.math.Matrix4Factories;
import task4.math.Vector3;
import task4.math.Vector4;

public class RotateConverter implements VectorConverter {
    Vector3 nv1 = new Vector3(2, 2, 2);
    Vector3 v2 = new Vector3(2, 2, -2);
    Matrix4 matrixDelta = Matrix4.one();
    double corner = 0.01;

    @Override
    public Vector3 v2v(Vector3 vector) {
        Matrix4 matrix = Matrix4.one();
        Matrix4 translation = Matrix4Factories.translation(v2);
        Vector3 v1 = new Vector3(nv1.getX() - v2.getX(), nv1.getY() - v2.getY(), nv1.getZ() - v2.getZ());
        //System.out.println(vector.getX() + " " + vector.getY() + " " + vector.getZ());
        //System.out.println(matrix.getAt(0, 0));
        double t1 = Math.asin(v1.getY() / Math.sqrt(v1.getY() * v1.getY() + v1.getZ() * v1.getZ()));
        double t2 = Math.asin(v1.getX() / Math.sqrt(v1.getX() * v1.getX()
                + v1.getY() * v1.getY() + v1.getZ() * v1.getZ()));

        Matrix4 matrix4x = Matrix4Factories.rotationXYZ(t1, Matrix4Factories.Axis.X);
        Matrix4 matrix4y = Matrix4Factories.rotationXYZ(t2, Matrix4Factories.Axis.Y);
        Matrix4 matrix4z = Matrix4Factories.rotationXYZ(corner, Matrix4Factories.Axis.Z);
        Vector4 v = new Vector4(vector, 1);
        matrix = matrix.mul(translation);
        matrix = matrix.mul(matrix4x);
        matrix = matrix.mul(matrix4y);
        matrix = matrix.mul(matrix4z);
        matrix4y = Matrix4Factories.rotationXYZ(-t2, Matrix4Factories.Axis.Y);
        matrix4x = Matrix4Factories.rotationXYZ(-t1, Matrix4Factories.Axis.X);
        translation = Matrix4Factories.translation(-v2.getX(), -v2.getY(), -v2.getZ());
        matrix = matrix.mul(matrix4y);
        matrix = matrix.mul(matrix4x);
        matrix = matrix.mul(translation);

        v = matrixDelta.mul(v);
        /*for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix.getAt(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println();
        //System.out.println(vector.getX() + " " + vector.getY() + " " + vector.getZ());
        //System.out.println(matrix.getAt(0, 0));
        //matrixDelta = matrixDelta.mul(matrix);
        
         */
        return new Vector3(v.getX(), v.getY(), v.getZ());
    }

    public void changeMatrix() {
        Matrix4 matrix = Matrix4.one();
        Matrix4 translation = Matrix4Factories.translation(v2);
        Vector3 v1 = new Vector3(nv1.getX() - v2.getX(), nv1.getY() - v2.getY(), nv1.getZ() - v2.getZ());
        double t1 = Math.asin(v1.getY() / Math.sqrt(v1.getY() * v1.getY() + v1.getZ() * v1.getZ()));
        double t2 = Math.asin(v1.getX() / Math.sqrt(v1.getX() * v1.getX()
                + v1.getY() * v1.getY() + v1.getZ() * v1.getZ()));

        Matrix4 matrix4x = Matrix4Factories.rotationXYZ(t1, Matrix4Factories.Axis.X);
        Matrix4 matrix4y = Matrix4Factories.rotationXYZ(t2, Matrix4Factories.Axis.Y);
        Matrix4 matrix4z = Matrix4Factories.rotationXYZ(corner, Matrix4Factories.Axis.Z);
        matrix = matrix.mul(translation);
        matrix = matrix.mul(matrix4x);
        matrix = matrix.mul(matrix4y);
        matrix = matrix.mul(matrix4z);
        matrix4y = Matrix4Factories.rotationXYZ(-t2, Matrix4Factories.Axis.Y);
        matrix4x = Matrix4Factories.rotationXYZ(-t1, Matrix4Factories.Axis.X);
        translation = Matrix4Factories.translation(-v2.getX(), -v2.getY(), -v2.getZ());
        matrix = matrix.mul(matrix4y);
        matrix = matrix.mul(matrix4x);
        matrix = matrix.mul(translation);
        matrixDelta = matrixDelta.mul(matrix);
    }
}
