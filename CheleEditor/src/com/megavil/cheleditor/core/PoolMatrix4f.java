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
	private int m_capacity;
	
	public PoolMatrix4f() {
		this(100);
	}
	
	public PoolMatrix4f(int capacity) {
		matrices = new ArrayList<Matrix4f>();
		m_pushes = new ArrayList<Matrix4f>();
		m_capacity = capacity;
		m_chunk = 20;
		createObjects(m_capacity);
	}
	
	private void createObjects(int amount) {
		for (int i = 0; i < amount; i++) {
			matrices.add(new Matrix4f());
		}
	}
	
	public Matrix4f push() {
		int size = matrices.size();
		if (size == 0) {
			createObjects(m_chunk);
			size = m_chunk;
		}
		int index = size - 1;
		Matrix4f m = matrices.remove(index);
		m_pushes.add(m);
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
		int count_pushes = m_pushes.size();
		if (count_pushes > 0) {
			matrices.addAll(m_pushes);
			for (Matrix4f m : m_pushes) {
				m.identity();
			}
			m_pushes.clear();
		}
	}
}
