package com.cgvsu.math;

public abstract class Vector {
    protected float[] components;

    public Vector(float[] components) {
        this.components = components;
    }

    public float get(int index) {
        return components[index];
    }

    public void set(int index, float value) {
        components[index] = value;
    }

    public abstract float length();
    public abstract Vector normalize();
    public abstract Vector add(Vector other);
    public abstract Vector deduct(Vector other);

    public Vector multiply(float scalar) {
        float[] result = new float[components.length];
        for (int i = 0; i < components.length; i++) {
            result[i] = this.components[i] * scalar;
        }
        return createInstance(result);
    }

    public Vector divide(float scalar) {
        if (scalar == 0) {
            throw new ArithmeticException("Недопустимо, деление на ноль");
        }
        float[] result = new float[components.length];
        for (int i = 0; i < components.length; i++) {
            result[i] = this.components[i] / scalar;
        }
        return createInstance(result);
    }

    public float dot(Vector other) {
        float result = 0;
        for (int i = 0; i < components.length; i++) {
            result += this.components[i] * other.get(i);
        }
        return result;
    }

    protected abstract Vector createInstance(float[] components);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vector(");
        for (int i = 0; i < components.length; i++) {
            sb.append(components[i]);
            if (i < components.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vector vector = (Vector) obj;
        if (components.length != vector.components.length) return false;
        for (int i = 0; i < components.length; i++) {
            if (Math.abs(components[i] - vector.components[i]) > 1e-6) return false;
        }
        return true;
    }
}
