import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class mainB extends JFrame {
    private JLabel background;
    private nodeB currentImage;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private ArrayList<String[]> rawData = new ArrayList<>();
    private JList<String> songJList;
    private JLabel infoSelection;
    private JPanel playlistPanel;
    private JSlider progressSlider;
    private Timer playbackTimer;
    private boolean isPlaying = false;
    private linkedListSong.songList activePlaylist = null;
    private final int Wid = 1000;
    private final int Hei = 560;

    public mainB() {
        linkedListB listB = new linkedListB();
        listB.add("1B.png");
        listB.add("2B.png");
        currentImage = listB.head;

        setTitle("Bastian-fy Music Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel principalPane = new JPanel(null);
        principalPane.setPreferredSize(new Dimension(Wid, Hei));

        progressSlider = new JSlider(0, 100, 0);
        progressSlider.setBounds(205, 510, 560, 10);
        progressSlider.setOpaque(false);

        playlistPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        playlistPanel.setBounds(259, 230, 400, 230);
        playlistPanel.setOpaque(false);

        infoSelection = new JLabel("");
        infoSelection.setBounds(40, 500, 400, 30);
        infoSelection.setForeground(Color.WHITE);

        playbackTimer = new Timer(100, e -> {
            if (progressSlider.getValue() < 100) {
                progressSlider.setValue(progressSlider.getValue() + 1);
            } else {
                progressSlider.setValue(0);
                if (activePlaylist != null && activePlaylist.current != null) {
                    activePlaylist.current = activePlaylist.current.next;
                    updateLabel();
                }
            }
        });
        JButton btnPrev = new JButton();
        btnPrev.setBounds(720, 352, 75, 75);
        btnPrev.setOpaque(false);
        btnPrev.setContentAreaFilled(false);
        btnPrev.setBorderPainted(false);
        btnPrev.addActionListener(e -> {
            if (activePlaylist != null && activePlaylist.current != null) {
                nodeSong temp = activePlaylist.current;
                while (temp.next != activePlaylist.current) {
                    temp = temp.next;
                }
                activePlaylist.current = temp;
                progressSlider.setValue(0);
                updateLabel();
                playMusic(isPlaying);
            }
        });
        JButton btnPause = new JButton();
        btnPause.setBounds(802, 352, 75, 75);
        btnPause.setOpaque(false);
        btnPause.setContentAreaFilled(false);
        btnPause.setBorderPainted(false);
        btnPause.addActionListener(e -> {
            playMusic(!isPlaying);
            if (currentImage != null) {
                currentImage = currentImage.next;
                updateBackground();
            }
        });
        JButton btnNext = new JButton();
        btnNext.setBounds(884, 352, 75, 75);
        btnNext.setOpaque(false);
        btnNext.setContentAreaFilled(false);
        btnNext.setBorderPainted(false);
        btnNext.addActionListener(e -> {
            if (activePlaylist != null && activePlaylist.current != null) {
                activePlaylist.current = activePlaylist.current.next;

                progressSlider.setValue(0);
                updateLabel();
                playMusic(isPlaying);
            }
        });
        JButton btnAddSong = new JButton();
        btnAddSong.setBounds(380, 35, 280, 40);
        btnAddSong.setOpaque(false);
        btnAddSong.setContentAreaFilled(false);
        btnAddSong.setBorderPainted(false);
        btnAddSong.addActionListener(e -> {
            String t = JOptionPane.showInputDialog(this, "Title:");
            String a = JOptionPane.showInputDialog(this, "Artist:");
            String d = JOptionPane.showInputDialog(this, "Duration:");
            if (t != null && a != null) {
                listModel.addElement(t + " - " + a);
                rawData.add(new String[] { t, a, d });
            }
        });
        JButton btnNewPlaylist = new JButton();
        btnNewPlaylist.setBounds(272, 165, 60, 60);
        btnNewPlaylist.setOpaque(false);
        btnNewPlaylist.setContentAreaFilled(false);
        btnNewPlaylist.setBorderPainted(false);
        btnNewPlaylist.addActionListener(e -> {
            if (listModel.isEmpty())
                return;
            String name = JOptionPane.showInputDialog(this, "Playlist Name:");
            if (name != null && !name.isEmpty()) {
                JList<String> sel = new JList<>(listModel);
                int opt = JOptionPane.showConfirmDialog(this, new JScrollPane(sel), "Select Songs",
                        JOptionPane.OK_CANCEL_OPTION);
                if (opt == JOptionPane.OK_OPTION) {
                    linkedListSong.songList newList = new linkedListSong.songList();
                    for (int i : sel.getSelectedIndices()) {
                        String[] d = rawData.get(i);
                        newList.add(d[0], d[1], d[2]);
                    }
                    createPlaylistButton(name, newList);
                }
            }
        });
        songJList = new JList<>(listModel);
        songJList.setBounds(50, 150, 160, 330);
        songJList.setOpaque(false);
        songJList.setForeground(Color.WHITE);
        background = new JLabel();
        background.setBounds(0, 0, Wid, Hei);
        updateBackground();
        principalPane.add(progressSlider);
        principalPane.add(playlistPanel);
        principalPane.add(btnNewPlaylist);
        principalPane.add(songJList);
        principalPane.add(infoSelection);
        principalPane.add(btnPrev);
        principalPane.add(btnPause);
        principalPane.add(btnNext);
        principalPane.add(btnAddSong);
        principalPane.add(background);
        add(principalPane);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void playMusic(boolean activate) {
        this.isPlaying = activate;
        if (activate) {
            playbackTimer.start();
        } else {
            playbackTimer.stop();
        }
    }

    private void createPlaylistButton(String name, linkedListSong.songList lp) {
        JButton b = new JButton(name);
        b.setPreferredSize(new Dimension(80, 80));
        b.addActionListener(e -> {
            String[] options = { "Play", "Add Song", "Delete Playlist" };
            int choice = JOptionPane.showOptionDialog(this, "Options for: " + name, name,
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
            if (choice == 0) {
                activePlaylist = lp;
                progressSlider.setValue(0);
                updateLabel();
                playMusic(true);
            } else if (choice == 1) {
                JList<String> sel = new JList<>(listModel);
                if (JOptionPane.showConfirmDialog(this, new JScrollPane(sel)) == JOptionPane.OK_OPTION) {
                    for (int i : sel.getSelectedIndices()) {
                        String[] d = rawData.get(i);
                        lp.add(d[0], d[1], d[2]);
                    }
                }
            } else if (choice == 2) {
                playlistPanel.remove(b);
                playlistPanel.revalidate();
                playlistPanel.repaint();
            }
        });
        playlistPanel.add(b);
        playlistPanel.revalidate();
    }

    private void updateLabel() {
        if (activePlaylist != null && activePlaylist.current != null) {
            nodeSong s = activePlaylist.current;
            infoSelection.setText(s.title + " | " + s.artist + " [" + s.duration + "]");
        }
    }

    private void updateBackground() {
        if (currentImage != null) {
            ImageIcon icon = new ImageIcon(currentImage.imageB);
            Image img = icon.getImage().getScaledInstance(Wid, Hei, Image.SCALE_SMOOTH);
            background.setIcon(new ImageIcon(img));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new mainB());
    }
}