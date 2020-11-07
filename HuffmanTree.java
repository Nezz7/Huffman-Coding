package huffmancoding;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class HuffmanTree {

    private Node root;
    private TreeMap<Character, Integer> freqMap;
    private TreeMap<Character, String> codeMap;

    public HuffmanTree(TreeMap<Character, Integer> freqMap) {
        this.freqMap = freqMap;
        this.codeMap = new TreeMap<>();
    }

    private Node merge(Node left, Node right) {
        int freq = left.getFreq() + right.getFreq();
        Node node = new Node('@', freq);
        node.insertLeft(left);
        node.insertRight(right);
        return node;
    }

    public void build() {
        int size = freqMap.size() + 1;
        PriorityQueue<Node> minQueue = new PriorityQueue<>(size, (Comparator<? super Node>) new NodeComparator());
        freqMap.entrySet().forEach((cur) -> {
            Node node = new Node(cur.getKey(), cur.getValue());
            minQueue.add(node);
        });
        while (minQueue.size() > 1) {
            Node left = minQueue.poll();
            Node right = minQueue.poll();
            Node parent = merge(left, right);
            minQueue.add(parent);
        }
        if (minQueue.isEmpty()) {
            System.out.println("Frequancy Map is empty !!");
        } else {
            this.root = minQueue.poll();
        }
    }

    private void treeTraversal(Node node, String code) {
        if (node.isLeaf()) {
            this.codeMap.put(node.getC(), code);
        } else {
            treeTraversal(node.getLeft(), code + "0");
            treeTraversal(node.getRight(), code + "1");
        }
    }

    private String dfs(Node node, String code) {
        if (node.isLeaf()) {
            if (code.length() == 0) {
                return node.getC() + "";
            }
            return node.getC() + dfs(root, code);
        }
        if (code.charAt(0) == '0') {
            return dfs(node.getLeft(), code.substring(1));
        }
        if (code.charAt(0) == '1') {
            return dfs(node.getRight(), code.substring(1));
        }
        System.out.println("Code is invalid !!");
        return "";
    }

    public String decode(String code) {
        if (root == null) {
            System.out.println("Tree is empty !!");
            return "";
        }
        return dfs(root, code);
    }

    public void code() {
        if (root == null) {
            System.out.println("Tree is empty !!");
        } else {
            treeTraversal(root, "");
        }
    }

    public String getCode(char c) {
        return codeMap.get(c);
    }

    public Node getRoot() {
        return root;
    }

    public void displayCode() {
        System.out.println("Coding Map :");
        System.out.println(codeMap);
    }

}
