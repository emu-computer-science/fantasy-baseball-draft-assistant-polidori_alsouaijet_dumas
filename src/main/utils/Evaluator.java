package main.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import main.models.Player;

public class Evaluator implements Serializable {

	private String expression;
	private Node tree;
	private List<String> variables = new ArrayList<>();
	private OperatorComparator operatorComparator = new OperatorComparator();
	
	public Evaluator() {
	}
	
	public Evaluator(String expression) {
		this.expression = expression;
		buildExpressionTree();
	}
	
	private void buildExpressionTree() {
		variables.clear();
		String lowerCaseExpressionWithNoSpaces = String.join("", expression.toLowerCase().split(" "));
		
		buildExpressionTree(null, lowerCaseExpressionWithNoSpaces, NodeType.ROOT);
	}
	
	private void buildExpressionTree(Node parent, String expression, NodeType child) {
		LowestPriorityOperator lowestPriorityOperator = findLowestPriorityOperator(expression);
		
		Node newNode = null;
		if (lowestPriorityOperator.getOperator() == null) {
			newNode = new Node(expression);
			if (!TypeUtils.isNumber(expression)) {
				variables.add(expression);
			}
		} else {
			newNode = new Node(lowestPriorityOperator.getOperator());
			buildExpressionTree(newNode, expression.substring(0, lowestPriorityOperator.getIndex()), NodeType.LEFT);
			buildExpressionTree(newNode, expression.substring(lowestPriorityOperator.getIndex() + 1, expression.length()), NodeType.RIGHT);
		}
		
		switch (child) {
			case RIGHT:
				parent.setRightChild(newNode);
				break;
			case LEFT:
				parent.setLeftChild(newNode);
				break;
			default:
				tree = newNode;
		}
	}

	private LowestPriorityOperator findLowestPriorityOperator(String expression) {
		int index = 0;
		Character operator = null;
		
		char[] chars = expression.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			
			if (operator == null && TypeUtils.isOperator(c) || 
				TypeUtils.isOperator(c) && operatorComparator.compare(operator, c) >= 0) {
				operator = c;
				index = i;
			}
		}
		
		return new LowestPriorityOperator(operator, index);
	}

	public Double evaluate(Player player) {
		for (String variable : variables) {
			if (!player.getStats().containsKey(variable)) {
				return null;
			}
		}
		
		return tree.evaluate(player);
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
		buildExpressionTree();
	}
	
	private static class Node {
		private String element;
		private Node leftChild;
		private Node rightChild;
		
		public Node(String element) {
			this.element = element;
		}
		
		public void setLeftChild(Node node) {
			this.leftChild = node;
		}
		
		public void setRightChild(Node node) {
			this.rightChild = node;
		}
		
		public Double evaluate(Player player) {
			if (leftChild != null || rightChild != null) {
				double leftValue = leftChild.evaluate(player);
				double rightValue = rightChild.evaluate(player);
				
				return performOperation(leftValue, rightValue, element);
			} else {
				if (player != null && player.getStats().containsKey(element))
					return player.getStats().get(element);
				else
					return Double.parseDouble(element);
			}
		}

		private Double performOperation(double leftValue, double rightValue, String operator) {
			if (operator.equals("+"))
				return leftValue + rightValue;
			else if (operator.equals("-"))
				return leftValue - rightValue;
			else if (operator.equals("/"))
				return leftValue / rightValue;
			else 
				return leftValue * rightValue;
		}
	}
	
	private static enum NodeType {
		LEFT,
		RIGHT,
		ROOT
	}
	
	private static class LowestPriorityOperator {
		private final String operator;
		private final int index;
		
		public LowestPriorityOperator(Character operator, int index) {
			this.operator = operator == null ? null : String.valueOf(operator);
			this.index = index;
		}

		public String getOperator() {
			return operator;
		}

		public int getIndex() {
			return index;
		}
	}
	
	private static class OperatorComparator implements Comparator<Character> {
		private static List<Character> highPriorityOperators = List.of('*', '/');
		private static List<Character> lowPriorityOperators = List.of('+', '-');
		
		@Override
		public int compare(Character c1, Character c2) {
			if (highPriorityOperators.contains(c1) && lowPriorityOperators.contains(c2)) {
				return 1;
			} else if (lowPriorityOperators.contains(c1) && highPriorityOperators.contains(c2)) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}
