package com.megavil.cheleditor.core;

import java.util.ArrayList;

public class Node {
	
	protected ArrayList<Node> childs = new ArrayList<>();
	protected Model model = null;
	
	protected Node parent;

	public Node addChild(Node child) {
		child.setParent(this);
		childs.add(child);
		return child;
	}
	
	public void removeChild(Node child) {
		child.setParent(null);
		childs.remove(child);
	}
	
	public void update(float dt) {
	}
	
	public void clear() {
		childs.forEach(node -> {
			node.clear();
		});
		childs = null;
		model = null;
	}

	public ArrayList<Node> getChilds() {
		return childs;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public Model getRender() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void render() {
		if (model != null) {
			model.render();
		}
	}
}
