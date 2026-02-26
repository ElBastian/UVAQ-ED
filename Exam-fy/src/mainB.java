import javax.swing.*;
import java.awt.*;

public class mainB extends JFrame {
    private JLabel background;
    private nodeB currentImage;
    private DefaultListModel<String> listModel; 
    private JList<String> songList;
    private JLabel infoSelection; 
    private final int Wid = 1000;
    private final int Hei = 560;

    public mainB() {
        linkedListB list = new linkedListB();
        list.add("1B.png"); 
        list.add("2B.png");
        currentImage = list.head;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        JPanel principalPane = new JPanel();
        principalPane.setLayout(null);
        principalPane.setPreferredSize(new Dimension(Wid, Hei));

        infoSelection = new JLabel("");
        infoSelection.setBounds(40, 500, 400, 30); //270, 485, 400, 30
        infoSelection.setForeground(Color.WHITE);
        infoSelection.setFont(new Font("Arial", Font.BOLD, 14));
        JButton btnPause = new JButton();
        btnPause.setBounds(810, 340, 75, 75); 
        btnPause.setOpaque(false);
        btnPause.setContentAreaFilled(false);
        btnPause.setBorderPainted(false);
        JButton btnAddSong = new JButton();
        btnAddSong.setBounds(380, 35, 280, 40); //370, 35, 280, 40
        btnAddSong.setOpaque(false);
        btnAddSong.setContentAreaFilled(false);
        listModel = new DefaultListModel<>();
        songList = new JList<>(listModel);
        songList.setBounds(50, 150, 160, 350); 
        songList.setOpaque(false);
        songList.setBackground(new Color(0, 0, 0, 0)); 
        songList.setForeground(Color.WHITE); 
        songList.setFont(new Font("Arial", Font.BOLD, 12));
        songList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String select = songList.getSelectedValue();
                infoSelection.setText(select);
            }
        });
        btnAddSong.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(this, "Song title:");
            if (title != null && !title.isEmpty()) {
                String artist = JOptionPane.showInputDialog(this, "Artist:");
                String time = JOptionPane.showInputDialog(this, "Time:");
                
                String completSong = title + " - " + artist + " (" + time + ")";
                listModel.addElement(completSong); 
            }
        });
        background = new JLabel();
        background.setBounds(0, 0, Wid, Hei);
        actualizarFondo(); 

        btnPause.addActionListener(e -> {
            currentImage = currentImage.next;
            actualizarFondo();
        });
        principalPane.add(songList);
        principalPane.add(infoSelection);
        principalPane.add(btnPause);
        principalPane.add(btnAddSong);
        principalPane.add(background);

        add(principalPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void actualizarFondo() {
        ImageIcon icon = new ImageIcon(currentImage.imageB);
        Image img = icon.getImage().getScaledInstance(Wid, Hei, Image.SCALE_SMOOTH);
        background.setIcon(new ImageIcon(img));
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new mainB());
    }
}