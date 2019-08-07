package net.spectre.game.util;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class MathHelper {

	public static Matrix4f createTranslation(Vector3f translation, float roll, float yaw, float pitch, float scale) {
		Matrix4f mat = new Matrix4f();
		mat.setIdentity();
		Matrix4f.translate(translation, mat, mat);
		Matrix4f.rotate((float) Math.toRadians(roll), new Vector3f(1, 0, 0), mat, mat);
		Matrix4f.rotate((float) Math.toRadians(yaw), new Vector3f(0, 1, 0), mat, mat);
		Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(0, 0, 1), mat, mat);
		Matrix4f.scale(new Vector3f(scale, scale, scale), mat, mat);
		return mat;
	}
}
