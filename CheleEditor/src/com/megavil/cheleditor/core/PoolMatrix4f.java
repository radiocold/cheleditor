package com.megavil.cheleditor.core;

import java.util.ArrayList;

import org.joml.Matrix4f;

public class PoolMatrix4f implements AutoCloseable  {
	
	private static PoolMatrix4f i = null;
	
	public static PoolMatrix4f instance() {
		if (i == null) {
			i = new PoolMatrix4f();
		}
		return i;
	}
	
	private ArrayList<Matrix4f> matrices;
	private ArrayList<Matrix4f> m_pushes;
	private int m_chunk;
	
	public PoolMatrix4f() {
		this(50);
	}
	
	public PoolMatrix4f(int chunk) {
		matrices = new ArrayList<Matrix4f>();
		m_pushes = new ArrayList<Matrix4f>();
		m_chunk = chunk;
		createChunk();
	}
	
	private void createChunk() {
		for (int i = 0; i < m_chunk; i++) {
			matrices.add(new Matrix4f());
		}
	}
	
	public Matrix4f push() {
		if (matrices.size() == 0) {
			createChunk();
		}
		Matrix4f m = matrices.get(matrices.size() - 1);
		m_pushes.add(m);
		matrices.remove(m);
		return m;
	}
	
	public void pop() {
		Matrix4f m = m_pushes.remove(m_pushes.size() - 1);
		m.identity();
		matrices.add(m);
	}
	
	public void reset() {
		close();
	}

	@Override
	public void close() {
		System.out.println("Close Pool Matrix4f");
		if (m_pushes.size() > 0) {
			matrices.addAll(m_pushes);
			for (Matrix4f m : m_pushes) {
				m.identity();
			}
			m_pushes.clear();
		}
	}
	
}
