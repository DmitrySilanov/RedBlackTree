public class Main {
    public static void main(String[] args) {
    }
        private Node root; //Налало работы

        public boolean add(int value) {
            if (root != null) {
                boolean result = addNode(root, value);
                root = rebalnce(root);
                return result;
            } else {
                root = new Node();
                root.color = Color.BLACK;
                root.value = value;
                return true;
            }
        }

        // Добавления новых, уникальных НОД

        private boolean addNode(Node node, int value) {
            if (node.value == value) {
                return false;
            } else {
                if (node.value > value) {
                    if (node.leftChild != null) {
                        //рекурсия
                        boolean result = addNode(node.leftChild, value);
                        node.leftChild = rebalnce(node.leftChild);
                        return result;
                    } else {
                        //Создание новой левой ноды, если такой нет
                        node.leftChild = new Node();
                        node.leftChild.color = Color.RED;
                        node.leftChild.value = value;
                        return true;
                    }
                } else {
                    //то же самое для правой ноды
                    if (node.rightChild != null) {
                        boolean result = addNode(node.rightChild, value);
                        node.rightChild = rebalnce(node.rightChild);
                        return result;
                    } else {
                        node.rightChild = new Node();
                        node.rightChild.color = Color.RED;
                        node.rightChild.value = value;
                        return true;
                    }
                }
            }
        }

        // Ребалансировка и повороты
        private Node rebalnce(Node node) {
            Node result = node;
            boolean needRebalance;
            do {
                needRebalance = false;
                //правый поворот
                if (result.rightChild != null && result.rightChild.color == Color.RED &&
                        (result.leftChild == null || result.leftChild.color == Color.BLACK)) {
                    needRebalance = true;
                    result = rightSwap(result);
                }
                //левый поворот
                if (result.leftChild != null && result.leftChild.color == Color.RED &&
                        result.leftChild.leftChild == null || result.leftChild.leftChild.color == Color.RED) {
                    needRebalance = true;
                    result = leftSwap(result);
                }

                //Смена цвета при одинаковом цвете правой и левой ноды
                if (result.leftChild != null && result.leftChild.color == Color.RED &&
                        result.rightChild != null && result.rightChild.color == Color.RED) {
                    needRebalance = true;
                    result = leftSwap(result);
                }
            }
            while (needRebalance);
            return result;
        }

        // Правый обмен
        private Node rightSwap(Node node) {
            Node rightChild = node.rightChild;
            Node betweenChild = rightChild.leftChild;
            rightChild.leftChild = node;
            node.rightChild = betweenChild;
            rightChild.color = node.color;
            node.color = Color.RED;
            return rightChild;
        }

        //Левый обмен
        private Node leftSwap(Node node) {
            Node leftChild = node.leftChild;
            Node betweenChild = leftChild.rightChild;
            leftChild.rightChild = node;
            node.leftChild = betweenChild;
            leftChild.color = node.color;
            node.color = Color.RED;
            return leftChild;
        }

        //Смена цвета
        private void colorSwap(Node node) {
            node.rightChild.color = Color.BLACK;
            node.leftChild.color = Color.BLACK;
            node.color = Color.RED;
        }

        private class Node {

            private int value;
            private Color color;
            private Node leftChild;
            private Node rightChild;

            @Override
            public String toString() {
                return "Node{" +
                        "value=" + value +
                        ", color=" + color +
                        '}';
            }
        }

        private enum Color {
            RED, BLACK
        }

}