public class linkedListB {
    nodeB head;

    public void add(String path) {
        nodeB newNode = new nodeB(path);
        if (head == null) {
            head = newNode;
            head.next = head; 
        } else {
            nodeB temp = head;
            while (temp.next != head) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = head;
        }
    }
}