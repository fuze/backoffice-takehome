package com.fuze.takehome.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class TreeNode<T> implements Iterable<TreeNode<T>> {

        T data;
        TreeNode<T> parent;
        List<TreeNode<T>> children;

        public TreeNode(T data) {
            this.data = data;
            this.children = new LinkedList<TreeNode<T>>();
        }
        
        public static <T> TreeNode<T> from(T data) {
        		return new TreeNode(data);
        }

        public TreeNode<T> addChild(T child) {
            TreeNode<T> childNode = new TreeNode<T>(child);
            childNode.parent = this;
            this.children.add(childNode);
            return childNode;
        }
        
        public T getData(){
        	return data;
        }
        
        public List<TreeNode<T>> getChildren(){
        	return children;
        }

		public boolean isLeaf() {
			return this.children.size()==0;
		}

		@Override
		public Iterator<TreeNode<T>> iterator() {
			// TODO Auto-generated method stub
			return null;
		}


}