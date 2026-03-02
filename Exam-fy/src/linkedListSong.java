public class linkedListSong {
    public static class songList {
        nodeSong head;
        nodeSong current;

        public void add(String title, String artist, String duration) {
            nodeSong newNode = new nodeSong(title, artist, duration);
            if (head == null) {
                head = newNode;
                head.next = head;
                current = head;
            } else {
                nodeSong temp = head;
                while (temp.next != head) temp = temp.next;
                temp.next = newNode;
                newNode.next = head;
            }
        }
    }
}