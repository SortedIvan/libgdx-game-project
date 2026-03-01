package utils;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Matrix2 {
    private Vector2 v1;
    private Vector2 v2;

    public Matrix2(Vector2 v1, Vector2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public Matrix2(float x1, float y1, float x2, float y2) {
        this.v1 = new Vector2(x1, y1);
        this.v2 = new Vector2(x2, y2);
    }

    public List<Vector2> getMatrixToVec2() {
        return List.of(v1, v2);
    }

    public void setMatrixWithListOfVec(List<Vector2> vecs) {
        this.v1 = vecs.get(0);
        this.v2 = vecs.get(1);
    }

    public Vector2 applyTransform(Vector2 input) {
        return new Vector2(
            input.x * v1.x + input.y * v2.x,
            input.x * v1.y + input.y * v2.y
        );
    }
}
