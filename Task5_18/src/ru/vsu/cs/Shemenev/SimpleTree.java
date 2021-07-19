package ru.vsu.cs.Shemenev;

public class SimpleTree<T extends Comparable<? super T>> {
    private class SimpleTreeNode<T> {
        private T value;
        private SimpleTreeNode<T> left;
        private SimpleTreeNode<T> right;

        public void setValue(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setLeft(SimpleTreeNode<T> left) {
            this.left = left;
        }

        public SimpleTreeNode<T> getLeft() {
            return left;
        }

        public void setRight(SimpleTreeNode<T> right) {
            this.right = right;
        }

        public SimpleTreeNode<T> getRight() {
            return right;
        }

        public String toString() {
            return value + "(" + left + ", " + right + ")";
        }
    }

    private SimpleTreeNode<T> root = null;

    public SimpleTreeNode<T> getRoot() {
        return root;
    }

    public void insertNode(T value) {
        SimpleTreeNode<T> newNode = new SimpleTreeNode<>();
        newNode.setValue(value);
        if (root == null) {
            root = newNode;
        } else {
            SimpleTreeNode<T> currNode = root;
            SimpleTreeNode<T> parentNode;
            while (true) {
                parentNode = currNode;
                int res = value.compareTo(currNode.getValue());
                if (res == 0) {
                    return;
                } else if (res < 0) {
                    currNode = currNode.getLeft();
                    if (currNode == null) {
                        parentNode.setLeft(newNode);
                        return;
                    }
                } else {
                    currNode = currNode.getRight();
                    if (currNode == null) {
                        parentNode.setRight(newNode);
                        return;
                    }
                }
            }
        }
    }

    public boolean deleteNode(T value) {
        SimpleTreeNode<T> currNode = root;
        SimpleTreeNode<T> parentNode = root;
        boolean isLeft = true;
        while (currNode.getValue() != value) {
            parentNode = currNode;
            int res = value.compareTo(currNode.getValue());
            if (res < 0) {
                isLeft = true;
                currNode = currNode.getLeft();
            } else {
                isLeft = false;
                currNode = currNode.getRight();
            }
            if (currNode == null) {
                return false;
            }
        }
        if (currNode.getLeft() == null && currNode.getRight() == null) {
            if (currNode == root) {
                root = null;
            } else if (isLeft) {
                parentNode.setLeft(null);
            } else {
                parentNode.setRight(null);
            }
        } else if (currNode.getRight() == null) {
            if (currNode == root) {
                root = currNode.getLeft();
            } else if (isLeft) {
                parentNode.setLeft(currNode.getLeft());
            } else {
                parentNode.setRight(currNode.getLeft());
            }
        } else if (currNode.getLeft() == null) {
            if (currNode == root) {
                root = currNode.getRight();
            } else if (isLeft) {
                parentNode.setLeft(currNode.getRight());
            } else {
                parentNode.setRight(currNode.getRight());
            }
        } else {
            SimpleTreeNode<T> heir = searchHeir(currNode);
            if (currNode == root) {
                root = heir;
            } else if (isLeft) {
                parentNode.setLeft(heir);
            } else {
                parentNode.setRight(heir);
            }
        }
        return true;
    }

    private SimpleTreeNode<T> searchHeir(SimpleTreeNode<T> node) {
        SimpleTreeNode<T> parentNode = node;
        SimpleTreeNode<T> heir = node;
        SimpleTreeNode<T> currNode = node.getRight();
        while (currNode != null) {
            parentNode = heir;
            heir = currNode;
            currNode = currNode.getLeft();
        }
        if (heir != node.getRight()) {
            parentNode.setLeft(heir.getRight());
            heir.setRight(node.getRight());
        }
        heir.setLeft(node.getLeft());
        return heir;
    }

    public SimpleTreeNode<T> searchTreeNodeByValue(T value) {
        SimpleTreeNode<T> currNode = root;
        while (currNode.getValue() != value) {
            int res = value.compareTo(currNode.getValue());
            if (res < 0) {
                currNode = currNode.getLeft();
            } else {
                currNode = currNode.getRight();
            }
            if (currNode == null) {
                return null;
            }
        }
        return currNode;
    }

    public void printSimpleTree(SimpleTreeNode<T> node, int level) {
        if (node == null) {
            return;
        }
        System.out.println(node.getValue() + " (уровень " + level + ")");
        printSimpleTree(node.left, level + 1);
        printSimpleTree(node.right, level + 1);
    }

    public void printSimpleTreeNode(SimpleTreeNode<T> node) {
        System.out.println(node.toString());
    }

    public boolean equivalence(SimpleTreeNode<T> treeNode) {
        MySimpleDeque<SimpleTreeNode<T>> dequeTree1 = new MySimpleDeque<>();
        MySimpleDeque<SimpleTreeNode<T>> dequeTree2 = new MySimpleDeque<>();
        dequeTree1.addLast(root);
        dequeTree2.addLast(treeNode);
        while (!dequeTree1.isEmpty() || !dequeTree2.isEmpty()) {
            if (dequeTree1.isEmpty() || dequeTree2.isEmpty()) {
                return false;
            }
            SimpleTreeNode<T> currTreeNode1 = dequeTree1.pollFirst();
            SimpleTreeNode<T> currTreeNode2 = dequeTree2.pollFirst();
            int res = currTreeNode1.getValue().compareTo(currTreeNode2.getValue());
            if (res == 0) {
                if (currTreeNode1.getLeft() != null) {
                    dequeTree1.addLast(currTreeNode1.getLeft());
                }
                if (currTreeNode1.getRight() != null) {
                    dequeTree1.addLast(currTreeNode1.getRight());
                }
                if (currTreeNode2.getLeft() != null) {
                    dequeTree2.addLast(currTreeNode2.getLeft());
                }
                if (currTreeNode2.getRight() != null) {
                    dequeTree2.addLast(currTreeNode2.getRight());
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public T searchMinValue(T value) {
        SimpleTreeNode<T> currNode = getStart(value);
        T min = currNode.value;
        if (min.compareTo(value) == 0) {
            return min;
        }
        currNode = currNode.getLeft();
        while (currNode != null && min.compareTo(currNode.value) > 0 && value.compareTo(currNode.value) < 0) {
            min = currNode.value;
            currNode = currNode.getLeft();
        }
        return min;
    }

    private SimpleTreeNode<T> getStart(T value) {
        SimpleTreeNode<T> currNode = root;
        while (currNode != null && value.compareTo(currNode.value) > 0) {
            currNode = currNode.getRight();
        }
        return currNode;
    }
}
