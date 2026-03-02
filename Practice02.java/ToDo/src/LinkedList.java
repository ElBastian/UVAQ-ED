public class LinkedList {
    private Node head;
    public LinkedList() { this.head = null; }
    public Node getHead() { return head; }
    public void addUrgentTask(String task, String details, String date) {
        Node node = new Node(task, details, date);
        node.setNext(head); 
        head = node;
    }
    public void removeTask(String task) {
        if (head == null) return;
        if (head.getTask().equals(task)) {
            head = head.getNext();
            return;
        }
        Node tmp = head;
        while (tmp.getNext() != null && !tmp.getNext().getTask().equals(task)) {
            tmp = tmp.getNext();
        }
        if (tmp.getNext() != null) {
            tmp.setNext(tmp.getNext().getNext());
        }
    }
}