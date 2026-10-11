
public class RoundRobinGameTurns {
    static class Node {
        String name;
        Node next;

        Node(String name) {
            this.name = name;
            this.next = null;
        }
    }

    static class CircularLinkedList {
        Node tail;

        void addLast(String name) {
            Node newNode = new Node(name);

            if (tail == null) {
                tail = newNode;
                tail.next = tail;
            } else {
                newNode.next = tail.next;
                tail.next = newNode;
                tail = newNode;
            }
        }

        void printTurns(int turns) {
            if (tail == null || turns <= 0) {
                return;
            }

            Node current = tail.next;

            for (int i = 0; i < turns; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }

                System.out.print(current.name);
                current = current.next;
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        CircularLinkedList players = new CircularLinkedList();

        players.addLast("Asha");
        players.addLast("Ravi");
        players.addLast("Neha");

        players.printTurns(7);
    }
}

