package com.megavil.cheleditor.core;

import java.util.ArrayList;

public class Node {
	
	protected ArrayList<Node> childs = new ArrayList<>();
	protected ArrayList<ComponentNode> components = new ArrayList<>();
	
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
	
	public ComponentNode addComponent(ComponentNode component) {
		components.add(component);
		return component;
	}
	
	
	public void removeComponent(ComponentNode component) {
		components.remove(component);
	}
	
	public void update(float dt) {
		
	}
	
	public void clear() {
		childs.forEach(node -> {
			node.clear();
		});
		childs = null;
		
		components.forEach(component -> {
			component.clear();
		});
		components = null;
	}

	public ArrayList<Node> getChilds() {
		return childs;
	}

	public ArrayList<ComponentNode> getComponents() {
		return components;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public void render() {
		
	}
}
