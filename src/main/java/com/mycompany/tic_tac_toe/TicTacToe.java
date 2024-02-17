package com.mycompany.tic_tac_toe;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 *
 * @author Dell
 */
public final class TicTacToe extends javax.swing.JFrame {

    JButton[][] Button;
    Timer Time;
    Integer Second, Delay = 10;
    JTextArea History;
    boolean isXturn = true, isPvP = true;
    int Count_Game = 1, Count_Turn = 0;
    int[] TurnPlay;
    char[][] Board;
    String ChooseXorY = " ";
    JScrollPane Scroll;
    JFrame Notification = null;

    static class MovePos {
        int Row, Col;
    };

    public void GeneratedBoard() {

        Panel.setLayout(new GridLayout(3, 3));

        Second = Delay;

        Time = new Timer(1000, (ActionEvent e) -> {
            Second--;
            String temp = Second.toString();
            if (temp.length() == 1) {
                temp = "0" + temp;
            }
            Count_Time.setText(temp + " giây");
            if (Second == 0) {
                if (isXturn) {
                    WinGame("O");
                } else {
                    WinGame("X");
                }
            }
        });

        History = new JTextArea();
        History.setBackground(Color.white);
        History.setEditable(false);
        History.setFont(new Font("Cascadia Code", 0, 16));
        Scroll = new JScrollPane(History);
        Scroll.setBounds(532, 80, 350, 300);

        TurnPlay = new int[9];
        Board = new char[3][3];
        Button = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Board[i][j] = ' ';
                Button[i][j] = new JButton(" ");
                Button[i][j].setFont(new Font("Antique", 1, 120));
                Button[i][j].setBackground(new Color(153, 255, 255));
                Button[i][j].setBorder(BorderFactory.createLineBorder(new Color(0, 125, 125), 2));
                Panel.add(Button[i][j]);
                int a = i, b = j;
                Button[a][b].addActionListener((ActionEvent e) -> {
                    if (" ".equals(Button[a][b].getText())) {
                        if (" ".equals(ChooseXorY) && !isPvP) {
                            JOptionPane.showMessageDialog(Notification, "Vui lòng chọn quân cờ ?",
                                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                        } else {
                            PlaySound("Press_BT");
                            Move(a, b);
                            if (isPvP) {
                                Change_Turns();
                            }
                            isXturn = !isXturn;
                            if (!isPvP) {
                                if (Count_Turn <= 8) {
                                    Move(ComputerMove(Board).Row, ComputerMove(Board).Col);
                                    isXturn = !isXturn;
                                }
                            }
                        }
                    }
                });
            }
        }
    }

    public void Change_Turns() {
        if (isXturn) {
            Current_Turn.setText("O");
            Current_Turn.setForeground(Color.RED);
        } else {
            Current_Turn.setText("X");
            Current_Turn.setForeground(Color.BLUE);
        }
    }

    public void PlaySound(String TypeSound) {
        String URL = null;
        if ("Press_BT".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tic_tac_toe/Sound_BT.wav";
        }
        if ("Win".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tic_tac_toe/Sound_Win.wav";
        }
        if ("Lose".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tic_tac_toe/Sound_Lose.wav";
        }
        if ("Draw".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tic_tac_toe/Sound_Draw.wav";
        }
        if ("Welcome".equals(TypeSound)) {
            URL = "./src/main/java/com/mycompany/tic_tac_toe/Sound_Welcome.wav";
        }
        try {
            URL soundbyte = new File(URL).toURI().toURL();
            java.applet.AudioClip Sound = java.applet.Applet.newAudioClip(soundbyte);
            Sound.play();
        } catch (MalformedURLException ex) {
        }
    }

    public void MakeNewGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button[i][j].setEnabled(true);
                Button[i][j].setText(" ");
                Board[i][j] = ' ';
            }
        }
        isXturn = true;
        Count_Time.setText(Delay + " giây");
        Time.stop();
        Second = Delay;
        TurnPlay = new int[9];
        Count_Turn = 0;
        ChooseXorY = " ";
        X.setBackground(Color.white);
        O.setBackground(Color.white);
    }

    public void Move(int x, int y) {
        if (isXturn) {
            Button[x][y].setText("X");
            Button[x][y].setForeground(Color.BLUE);
            TurnPlay[Count_Turn] = x * 10 + y;
            Board[x][y] = 'X';
        } else {
            Button[x][y].setText("O");
            Button[x][y].setForeground(Color.RED);
            TurnPlay[Count_Turn] = x * 10 + y;
            Board[x][y] = 'O';
        }
        Second = Delay;
        Time.start();
        Count_Time.setText(Delay + " giây");
        Count_Turn++;
        WinGame(CheckWin());
    }

    public String CheckWin() {

        for (int i = 0; i < 3; i++) {
            if (!" ".equals(Button[i][0].getText()) && (Button[i][0].getText() == null ? Button[i][1].getText() == null : Button[i][0].getText().equals(Button[i][1].getText())) && (Button[i][1].getText() == null ? Button[i][2].getText() == null : Button[i][1].getText().equals(Button[i][2].getText()))) {
                return Button[i][0].getText();
            }
            if (!" ".equals(Button[0][i].getText()) && (Button[0][i].getText() == null ? Button[1][i].getText() == null : Button[0][i].getText().equals(Button[1][i].getText())) && (Button[1][i].getText() == null ? Button[2][i].getText() == null : Button[1][i].getText().equals(Button[2][i].getText()))) {
                return Button[0][i].getText();
            }
        }

        if (!" ".equals(Button[0][0].getText()) && (Button[0][0].getText() == null ? Button[1][1].getText() == null : Button[0][0].getText().equals(Button[1][1].getText())) && (Button[1][1].getText() == null ? Button[2][2].getText() == null : Button[1][1].getText().equals(Button[2][2].getText()))) {
            return Button[0][0].getText();
        }

        if (!" ".equals(Button[0][2].getText()) && (Button[0][2].getText() == null ? Button[1][1].getText() == null : Button[0][2].getText().equals(Button[1][1].getText())) && (Button[1][1].getText() == null ? Button[2][0].getText() == null : Button[1][1].getText().equals(Button[2][0].getText()))) {
            return Button[0][2].getText();
        }

        return "N";

    }

    public void WinGame(String N) {

        String Winner = N;

        if (!"N".equals(Winner) || Count_Turn == 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Button[i][j].setEnabled(false);
                }
            }

            HighlightWin();

            History.setText(History.getText()
                    + " ======= Ván " + Count_Game + " =======\n");
            if (isPvP) {
                History.setText(History.getText() + "  Chế độ chơi:\n    Chơi với người\n");
            } else {
                History.setText(History.getText() + "  Chế độ chơi:\n    Chơi với máy\n");
            }
            if (!"N".equals(Winner)) {
                History.setText(History.getText() + "  " + Winner + " Thắng\n");
            } else {
                History.setText(History.getText() + "  Hòa\n");
            }
            History.setText(History.getText() + "  Thế cờ:\n"
                    + "     -------------\n"
                    + "     | " + Button[0][0].getText() + " | " + Button[0][1].getText() + " | " + Button[0][2].getText() + " |\n"
                    + "     -------------\n"
                    + "     | " + Button[1][0].getText() + " | " + Button[1][1].getText() + " | " + Button[1][2].getText() + " |\n"
                    + "     -------------\n"
                    + "     | " + Button[2][0].getText() + " | " + Button[2][1].getText() + " | " + Button[2][2].getText() + " |\n"
                    + "     -------------\n");
            if ("O".equals(Winner) && Second == 0) {
                History.setText(History.getText() + "  X quá thời gian\n");
            }
            if ("X".equals(Winner) && Second == 0) {
                History.setText(History.getText() + "  O quá thời gian\n");
            }

            Count_Game++;
            Time.stop();
            if (!"N".equals(Winner)) {
                if (!isPvP) {
                    if (ChooseXorY == null ? Winner == null : ChooseXorY.equals(Winner)) {
                        Winner = "Bạn đã thắng";
                        PlaySound("Win");
                    } else {
                        Winner = "Máy đã thắng";
                        PlaySound("Lose");
                    }
                } else {
                    Winner = Winner + " Thắng";
                    PlaySound("Win");
                }
                
            } else {
                Winner = "Hòa";
                PlaySound("Draw");
            }
            int m = JOptionPane.showConfirmDialog(this, Winner + ". Bạn có muốn tiếp tục không ?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (m == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }

    public void HighlightWin() {

        for (int i = 0; i < 3; i++) {
            if (!" ".equals(Button[i][0].getText()) && (Button[i][0].getText() == null ? Button[i][1].getText() == null : Button[i][0].getText().equals(Button[i][1].getText())) && (Button[i][1].getText() == null ? Button[i][2].getText() == null : Button[i][1].getText().equals(Button[i][2].getText()))) {
                Button[i][0].setEnabled(true);
                Button[i][1].setEnabled(true);
                Button[i][2].setEnabled(true);
            }
            if (!" ".equals(Button[0][i].getText()) && Button[0][i].getText().equals(Button[1][i].getText()) && Button[1][i].getText().equals(Button[2][i].getText())) {
                Button[0][i].setEnabled(true);
                Button[1][i].setEnabled(true);
                Button[2][i].setEnabled(true);
            }
        }

        if (!" ".equals(Button[0][0].getText()) && (Button[0][0].getText() == null ? Button[1][1].getText() == null : Button[0][0].getText().equals(Button[1][1].getText())) && (Button[1][1].getText() == null ? Button[2][2].getText() == null : Button[1][1].getText().equals(Button[2][2].getText()))) {
            Button[0][0].setEnabled(true);
            Button[1][1].setEnabled(true);
            Button[2][2].setEnabled(true);
        }

        if (!" ".equals(Button[0][2].getText()) && (Button[0][2].getText() == null ? Button[1][1].getText() == null : Button[0][2].getText().equals(Button[1][1].getText())) && (Button[1][1].getText() == null ? Button[2][0].getText() == null : Button[1][1].getText().equals(Button[2][0].getText()))) {
            Button[0][2].setEnabled(true);
            Button[1][1].setEnabled(true);
            Button[2][0].setEnabled(true);
        }

    }

    //==========================================================================
    
    public char CheckWinnerInMinimax(char[][] board) {

        if (board[0][0] != ' ' && board[0][0] == board[0][1] && board[0][1] == board[0][2]) {
            return board[0][0];
        } else if (board[1][0] != ' ' && board[1][0] == board[1][1] && board[1][1] == board[1][2]) {
            return board[1][0];
        } else if (board[2][0] != ' ' && board[2][0] == board[2][1] && board[2][1] == board[2][2]) {
            return board[2][0];
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][0] && board[1][0] == board[2][0]) {
            return board[0][0];
        } else if (board[0][1] != ' ' && board[0][1] == board[1][1] && board[1][1] == board[2][1]) {
            return board[0][1];
        } else if (board[0][2] != ' ' && board[0][2] == board[1][2] && board[1][2] == board[2][2]) {
            return board[0][2];
        }

        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        } else if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == ' ') {
                    return ' ';
                }
            }
        }

        return 'I';
    }

    public MovePos ComputerMove(char[][] Board) {

        MovePos BestMove = new MovePos();
        char Letter;

        if ("O".equals(ChooseXorY)) {
            Letter = 'X';
        } else {
            Letter = 'O';
        }

        int BestMoveScore = -1000000000;

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (Board[x][y] == ' ') {
                    Board[x][y] = Letter;
                    int CurrentMoveScore = Minimax(Board, Letter, 0, false);
                    Board[x][y] = ' ';
                    if (CurrentMoveScore > BestMoveScore) {
                        BestMoveScore = CurrentMoveScore;
                        BestMove.Row = x;
                        BestMove.Col = y;
                    }
                }
            }
        }
        return BestMove;
    }

    public int Minimax(char[][] Board, char Letter, int Depth, boolean isMaximum) {

        char Result = CheckWinnerInMinimax(Board);

        if (Letter == 'X') {
            if (Result == 'X') {
                return 10 - Depth;
            } else if (Result == 'O') {
                return Depth - 10;
            }
        } else {
            if (Result == 'O') {
                return 10 - Depth;
            } else if (Result == 'X') {
                return Depth - 10;
            }
        }
        if (Result == 'I') {
            return 0;
        }

        if (isMaximum) {
            int BestMoveScoreMax = -1000000;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if (Board[x][y] == ' ') {
                        Board[x][y] = Letter;
                        BestMoveScoreMax = Math.max(BestMoveScoreMax, Minimax(Board, Letter, Depth + 1, !isMaximum));
                        Board[x][y] = ' ';
                    }
                }
            }
            return BestMoveScoreMax;
        } else {
            int BestMoveScoreMin = 1000000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (Board[i][j] == ' ') {
                        if (Letter == 'O') {
                            Board[i][j] = 'X';
                        } else {
                            Board[i][j] = 'O';
                        }
                        BestMoveScoreMin = Math.min(BestMoveScoreMin, Minimax(Board, Letter, Depth + 1, !isMaximum));
                        Board[i][j] = ' ';
                    }
                }
            }
            return BestMoveScoreMin;
        }
    }
    
    //==========================================================================

    public TicTacToe() {
        initComponents();
        this.setTitle("Tic Tac Toe");
        this.setIconImage(new ImageIcon("./src/main/java/com/mycompany/tic_tac_toe/Icon.png").getImage());
        this.getContentPane().setBackground(new Color(162, 226, 248));
        this.setLocationRelativeTo(null);
        Label_ChosseXorO.setVisible(false);
        X.setVisible(false);
        O.setVisible(false);
        Stop.setVisible(false);
        Continue.setVisible(false);
        Undo.setVisible(false);
        Custom.remove(Cheat);
        GeneratedBoard();
        PlaySound("Welcome");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        Panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        Label_Title = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        TimeRemain = new javax.swing.JLabel();
        Count_Time = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        Label_GameMode = new javax.swing.JLabel();
        GameModePVP = new javax.swing.JButton();
        GameModePVC = new javax.swing.JButton();
        X = new javax.swing.JButton();
        O = new javax.swing.JButton();
        Label_ChosseXorO = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        ShowHistory = new javax.swing.JButton();
        Newgame = new javax.swing.JButton();
        Stop = new javax.swing.JButton();
        Continue = new javax.swing.JButton();
        Undo = new javax.swing.JButton();
        Exit_Button = new javax.swing.JButton();
        Label_Button = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        Label_Current_Turn = new javax.swing.JLabel();
        Current_Turn = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        Custom = new javax.swing.JMenu();
        delay = new javax.swing.JMenuItem();
        Exit_Menu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        Cheat = new javax.swing.JMenu();
        Activite = new javax.swing.JMenuItem();
        Reset = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        Help = new javax.swing.JMenu();
        Rule = new javax.swing.JMenuItem();
        About = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Panel.setBackground(new java.awt.Color(0, 125, 125));
        Panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 230, 230));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 125, 125), 4));

        Label_Title.setFont(new java.awt.Font("Algerian", 1, 75)); // NOI18N
        Label_Title.setForeground(new java.awt.Color(51, 51, 255));
        Label_Title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_Title.setText("TIC TAC TOE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Label_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Label_Title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(162, 226, 248));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 125, 125), 3));

        TimeRemain.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        TimeRemain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TimeRemain.setText("Thời gian còn lại");

        Count_Time.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        Count_Time.setForeground(Color.RED);
        Count_Time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Count_Time.setText("10 giây");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TimeRemain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Count_Time, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(TimeRemain, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Count_Time, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(162, 226, 248));
        jPanel15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 125, 125), 3));

        Label_GameMode.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Label_GameMode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_GameMode.setText("Chế độ chơi");

        GameModePVP.setBackground(new java.awt.Color(159, 168, 218));
        GameModePVP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        GameModePVP.setText("Chơi với người");
        GameModePVP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameModePVPActionPerformed(evt);
            }
        });

        GameModePVC.setBackground(Color.GRAY
        );
        GameModePVC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        GameModePVC.setText("Chơi với máy");
        GameModePVC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameModePVCActionPerformed(evt);
            }
        });

        X.setFont(new Font("Antique", 1, 20));
        X.setForeground(Color.BLUE);
        X.setText("X");
        X.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XActionPerformed(evt);
            }
        });

        O.setFont(new Font("Antique", 1, 20));
        O.setForeground(Color.RED);
        O.setText("O");
        O.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OActionPerformed(evt);
            }
        });

        Label_ChosseXorO.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Label_ChosseXorO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_ChosseXorO.setText("Chọn quân của bạn");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Label_GameMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(Label_ChosseXorO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(GameModePVP)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                                .addComponent(X, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19)))
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addComponent(GameModePVC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(jPanel15Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(O, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(Label_GameMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GameModePVP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(GameModePVC, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(Label_ChosseXorO, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(X, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(O, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(162, 226, 248));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 125, 125), 3));

        ShowHistory.setBackground(new java.awt.Color(159, 168, 218));
        ShowHistory.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ShowHistory.setText("Lịch sử");
        ShowHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowHistoryActionPerformed(evt);
            }
        });

        Newgame.setBackground(new java.awt.Color(159, 168, 218));
        Newgame.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Newgame.setText("Ván mới");
        Newgame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewgameActionPerformed(evt);
            }
        });

        Stop.setBackground(new java.awt.Color(159, 168, 218));
        Stop.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Stop.setText("Dừng lại");
        Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopActionPerformed(evt);
            }
        });

        Continue.setBackground(new java.awt.Color(159, 168, 218));
        Continue.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Continue.setText("Tiếp tục");
        Continue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContinueActionPerformed(evt);
            }
        });

        Undo.setBackground(new java.awt.Color(159, 168, 218));
        Undo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Undo.setText("Quay lại");
        Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoActionPerformed(evt);
            }
        });

        Exit_Button.setBackground(new java.awt.Color(159, 168, 218));
        Exit_Button.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Exit_Button.setText("Thoát");
        Exit_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Exit_ButtonActionPerformed(evt);
            }
        });

        Label_Button.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Label_Button.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_Button.setText("Nút điều khiển");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Label_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Exit_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(ShowHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Newgame, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Continue, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Undo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(Label_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ShowHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Newgame, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Stop, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Continue, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Undo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Exit_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(162, 226, 248));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 125, 125), 3));

        Label_Current_Turn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Label_Current_Turn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label_Current_Turn.setText("Lượt đi hiện tại");

        Current_Turn.setFont(new java.awt.Font("Segoe UI", 1, 40)); // NOI18N
        Current_Turn.setForeground(Color.BLUE);
        Current_Turn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Current_Turn.setText("X");
        Current_Turn.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Current_Turn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Label_Current_Turn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Current_Turn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(Label_Current_Turn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Current_Turn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE))
        );

        Custom.setText("Tùy chỉnh");

        delay.setText("Thời gian");
        delay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delayActionPerformed(evt);
            }
        });
        Custom.add(delay);

        Exit_Menu.setText("Thoát");
        Exit_Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Exit_MenuActionPerformed(evt);
            }
        });
        Custom.add(Exit_Menu);
        Custom.add(jSeparator1);

        Cheat.setText("Gian lận");

        Activite.setText("Kích hoạt");
        Activite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActiviteActionPerformed(evt);
            }
        });
        Cheat.add(Activite);

        Reset.setText("Đặt lại");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetActionPerformed(evt);
            }
        });
        Cheat.add(Reset);
        Cheat.add(jSeparator2);

        Custom.add(Cheat);

        MenuBar.add(Custom);

        Help.setText("Hỗ trợ");

        Rule.setText("Luật chơi");
        Rule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RuleActionPerformed(evt);
            }
        });
        Help.add(Rule);

        About.setText("Thông tin");
        About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutActionPerformed(evt);
            }
        });
        Help.add(About);
        Help.add(jSeparator3);

        MenuBar.add(Help);

        setJMenuBar(MenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void delayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delayActionPerformed
        if (Count_Turn > 0 && "N".equals(CheckWin())) {
            JOptionPane.showMessageDialog(this, "Trận đấu vẫn đang diễn ra. Bạn không thể thay đổi thời gian giữa lượt trong lúc này",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        } else {
            String Setdelay;
            Setdelay = JOptionPane.showInputDialog("Vui lòng nhập thời gian chờ:", 10);
            if (Setdelay != null) {
                if (Setdelay.matches("-?\\d+(\\.\\d+)?")) {
                    Delay = Integer.valueOf(Setdelay);
                    Second = Delay;
                    Count_Time.setText(Delay + " giây");
                }else
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập thời gian là chữ số!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_delayActionPerformed

    private void Exit_MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Exit_MenuActionPerformed
        if (Count_Turn > 0 && "N".equals(CheckWin())) {
            int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn thoát không ?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (m == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else
            System.exit(0);
    }//GEN-LAST:event_Exit_MenuActionPerformed

    private void ActiviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActiviteActionPerformed
        String CheatCode;
        CheatCode = JOptionPane.showInputDialog("Vui lòng mã gian lận để kích hoạt tính năng quay lại nước đi hoặc dừng thời gian:");
        if ("123abc".equals(CheatCode)) {
            Undo.setVisible(true);
            JOptionPane.showMessageDialog(this,
                    "Đã kích hoạt chức năng Quay lại", "Thông tin",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if ("456def".equals(CheatCode)) {
            Stop.setVisible(true);
            Continue.setVisible(true);
            JOptionPane.showMessageDialog(this,
                    "Đã kích hoạt chức năng dừng và tiếp tục thời gian", "Thông tin",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (CheatCode != null) {
            JOptionPane.showMessageDialog(this, "Sai mã gian lận!",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ActiviteActionPerformed

    private void ResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetActionPerformed
        Undo.setVisible(false);
        Stop.setVisible(false);
        Continue.setVisible(false);
        JOptionPane.showMessageDialog(this,
                "Đã tắt các chức năng hỗ trợ", "Thông tin",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_ResetActionPerformed

    private void RuleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RuleActionPerformed
        JOptionPane.showConfirmDialog(this, """
                                            Tic Tac Toe l\u00e0 m\u1ed9t tr\u00f2 ch\u01a1i tr\u00ean b\u00e0n c\u1edd \u0111\u01a1n gi\u1ea3n.
                                             - C\u00f3 2 ch\u1ebf \u0111\u1ed9 ch\u01a1i: Ch\u01a1i v\u1edbi ng\u01b0\u1eddi v\u00e0 ch\u01a1i v\u1edbi m\u00e1y.
                                             - Tr\u00f2 ch\u01a1i \u0111\u01b0\u1ee3c ch\u01a1i tr\u00ean m\u1ed9t b\u1ea3ng g\u1ed3m 9 \u00f4 \u0111\u01b0\u1ee3c chia th\u00e0nh 3 h\u00e0ng v\u00e0 3 c\u1ed9t.
                                             - C\u00e1c ng\u01b0\u1eddi ch\u01a1i l\u1ea7n l\u01b0\u1ee3t \u0111\u00e1nh X ho\u1eb7c O v\u00e0o \u00f4 tr\u1ed1ng, qu\u00e2n X \u0111\u00e1nh tr\u01b0\u1edbc.
                                             - M\u1ed7i l\u01b0\u1ee3t ch\u01a1i ng\u01b0\u1eddi ch\u01a1i c\u00f3 10 gi\u00e2y \u0111\u1ec3 suy ngh\u0129.
                                             - H\u1ebft 10 gi\u00e2y v\u1eabn ch\u01b0a \u0111\u00e1nh n\u01b0\u1edbc ti\u1ebfp theo ng\u01b0\u1eddi ch\u01a1i s\u1ebd thua.
                                             - B\u1ea1n c\u00f3 th\u1ec3 thay \u0111\u1ed5i th\u1eddi gian gi\u1eefa c\u00e1c l\u01b0\u1ee3t trong ph\u1ea7n t\u00f9y ch\u1ec9nh.
                                             - Ng\u01b0\u1eddi ch\u01a1i n\u00e0o t\u1ea1o \u0111\u01b0\u1ee3c 3 k\u00ed t\u1ef1 gi\u1ed1ng nhau tr\u00ean c\u00f9ng m\u1ed9t h\u00e0ng ngang, d\u1ecdc, ch\u00e9o l\u00e0 ng\u01b0\u1eddi th\u1eafng cu\u1ed9c.
                                             - N\u1ebfu \u0111\u00e1nh h\u1ebft b\u00e0n c\u1edd kh\u00f4ng c\u00f3 ng\u01b0\u1eddi chi\u00ean th\u1eafng th\u00ec tr\u00f2 ch\u01a1i s\u1ebd k\u1ebft th\u00fac v\u1edbi k\u1ebft qu\u1ea3 h\u00f2a.
                                            """, "Luật Chơi",
                JOptionPane.CLOSED_OPTION);
    }//GEN-LAST:event_RuleActionPerformed

    private void AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutActionPerformed
        JOptionPane.showMessageDialog(this,
                "Thiết kế và hoàn thiện bởi: Huỳnh Vĩnh Phúc\n Mã số sinh viên: B2012129", "Thông tin",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AboutActionPerformed

    private void GameModePVPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GameModePVPActionPerformed
        if (!isPvP) {
            boolean Confirm = true;
            if (Count_Turn > 0 && "N".equals(CheckWin()) && Count_Turn != 9) {
                int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn đổi chế độ chơi và chơi ván mới không ?",
                        "Thông báo", JOptionPane.YES_NO_OPTION);
                if (m == JOptionPane.NO_OPTION) {
                    Confirm = false;
                }
            }
            if (Confirm) {
                isPvP = !isPvP;
                Label_ChosseXorO.setVisible(false);
                X.setVisible(false);
                O.setVisible(false);
                Stop.setVisible(false);
                Continue.setVisible(false);
                Undo.setVisible(false);
                GameModePVP.setBackground(new Color(159, 168, 218));
                GameModePVC.setBackground(Color.gray);
                Label_Current_Turn.setText("Lượt đi hiện tại");
                MakeNewGame();
            }
        }
        Custom.remove(Cheat);
    }//GEN-LAST:event_GameModePVPActionPerformed

    private void GameModePVCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GameModePVCActionPerformed
        if (isPvP) {
            boolean Confirm = true;
            if (Count_Turn > 0 && "N".equals(CheckWin()) && Count_Turn != 9) {
                int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn đổi chế độ chơi và chơi ván mới không ?",
                        "Thông báo", JOptionPane.YES_NO_OPTION);
                if (m == JOptionPane.NO_OPTION) {
                    Confirm = false;
                }
            }
            if (Confirm) {
                Label_ChosseXorO.setVisible(true);
                X.setVisible(true);
                O.setVisible(true);
                isPvP = !isPvP;
                GameModePVC.setBackground(new Color(159, 168, 218));
                GameModePVP.setBackground(Color.gray);
                Label_Current_Turn.setText("Lượt đi của bạn");
                MakeNewGame();
            }
        }
        Custom.add(Cheat);
    }//GEN-LAST:event_GameModePVCActionPerformed

    private void XActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XActionPerformed
        if (" ".equals(ChooseXorY)) {
            O.setBackground(Color.gray);
            ChooseXorY = "X";
            Current_Turn.setText("X");
            Current_Turn.setForeground(Color.BLUE);
        }
    }//GEN-LAST:event_XActionPerformed

    private void OActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OActionPerformed
        if (" ".equals(ChooseXorY)) {
            X.setBackground(Color.gray);
            ChooseXorY = "O";
            Random generator = new Random();
            Move(generator.nextInt(3), generator.nextInt(3));
            Current_Turn.setText("O");
            Current_Turn.setForeground(Color.RED);
            isXturn = !isXturn;
        }
    }//GEN-LAST:event_OActionPerformed

    private void ShowHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowHistoryActionPerformed
        JDialog d = new JDialog(this, "Lịch sử");
        d.add(Scroll);
        d.setSize(350, 300);
        d.setBounds(200, 200, 275, 400);
        d.setVisible(true);
    }//GEN-LAST:event_ShowHistoryActionPerformed

    private void NewgameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewgameActionPerformed
        boolean Confirm = true;
        if (Count_Turn > 0 && "N".equals(CheckWin()) && Count_Turn < 9 && Second != 0) {
            int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn tạo ván mới không ?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (m == JOptionPane.NO_OPTION) {
                Confirm = false;
            }
        }
        if (Confirm) {
            MakeNewGame();
        }
    }//GEN-LAST:event_NewgameActionPerformed

    private void StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopActionPerformed
        Time.stop();
    }//GEN-LAST:event_StopActionPerformed

    private void ContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContinueActionPerformed
        Time.start();
    }//GEN-LAST:event_ContinueActionPerformed

    private void UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoActionPerformed
        for (int i = 0; i < 2; i++) {
            if (Count_Turn != 9) {
                if ((Count_Turn > 0 && "X".equals(ChooseXorY)) || (Count_Turn > 1 && "O".equals(ChooseXorY))) {
                    Count_Turn--;
                    Button[TurnPlay[Count_Turn] / 10][TurnPlay[Count_Turn] % 10].setText(" ");
                    Button[TurnPlay[Count_Turn] / 10][TurnPlay[Count_Turn] % 10].updateUI();
                }
            }
        }
    }//GEN-LAST:event_UndoActionPerformed

    private void Exit_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Exit_ButtonActionPerformed
        if (Count_Turn > 0 && "N".equals(CheckWin()) && Count_Turn != 9) {
            int m = JOptionPane.showConfirmDialog(this, "Trận đấu vẫn đang diễn ra. Bạn có chắc là muốn thoát không ?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (m == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else
            System.exit(0);
    }//GEN-LAST:event_Exit_ButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TicTacToe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TicTacToe().setVisible(true);
            JFrame Notification1 = null;
            JOptionPane.showConfirmDialog(Notification1, """
                                                         Tic Tac Toe l\u00e0 m\u1ed9t tr\u00f2 ch\u01a1i tr\u00ean b\u00e0n c\u1edd \u0111\u01a1n gi\u1ea3n.
                                                          - C\u00f3 2 ch\u1ebf \u0111\u1ed9 ch\u01a1i: Ch\u01a1i v\u1edbi ng\u01b0\u1eddi v\u00e0 ch\u01a1i v\u1edbi m\u00e1y.
                                                          - Tr\u00f2 ch\u01a1i \u0111\u01b0\u1ee3c ch\u01a1i tr\u00ean m\u1ed9t b\u1ea3ng g\u1ed3m 9 \u00f4 \u0111\u01b0\u1ee3c chia th\u00e0nh 3 h\u00e0ng v\u00e0 3 c\u1ed9t.
                                                          - C\u00e1c ng\u01b0\u1eddi ch\u01a1i l\u1ea7n l\u01b0\u1ee3t \u0111\u00e1nh X ho\u1eb7c O v\u00e0o \u00f4 tr\u1ed1ng, qu\u00e2n X \u0111\u00e1nh tr\u01b0\u1edbc.
                                                          - M\u1ed7i l\u01b0\u1ee3t ch\u01a1i ng\u01b0\u1eddi ch\u01a1i c\u00f3 10 gi\u00e2y \u0111\u1ec3 suy ngh\u0129.
                                                          - H\u1ebft 10 gi\u00e2y v\u1eabn ch\u01b0a \u0111\u00e1nh n\u01b0\u1edbc ti\u1ebfp theo ng\u01b0\u1eddi ch\u01a1i s\u1ebd thua.
                                                          - B\u1ea1n c\u00f3 th\u1ec3 thay \u0111\u1ed5i th\u1eddi gian gi\u1eefa c\u00e1c l\u01b0\u1ee3t trong ph\u1ea7n t\u00f9y ch\u1ec9nh.
                                                          - Ng\u01b0\u1eddi ch\u01a1i n\u00e0o t\u1ea1o \u0111\u01b0\u1ee3c 3 k\u00ed t\u1ef1 gi\u1ed1ng nhau tr\u00ean c\u00f9ng m\u1ed9t h\u00e0ng ngang, d\u1ecdc, ch\u00e9o l\u00e0 ng\u01b0\u1eddi th\u1eafng cu\u1ed9c.
                                                          - N\u1ebfu \u0111\u00e1nh h\u1ebft b\u00e0n c\u1edd kh\u00f4ng c\u00f3 ng\u01b0\u1eddi chi\u00ean th\u1eafng th\u00ec tr\u00f2 ch\u01a1i s\u1ebd k\u1ebft th\u00fac v\u1edbi k\u1ebft qu\u1ea3 h\u00f2a.
                                                         """, "Luật Chơi", JOptionPane.CLOSED_OPTION);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem About;
    private javax.swing.JMenuItem Activite;
    private javax.swing.JMenu Cheat;
    private javax.swing.JButton Continue;
    private javax.swing.JLabel Count_Time;
    private javax.swing.JLabel Current_Turn;
    private javax.swing.JMenu Custom;
    private javax.swing.JButton Exit_Button;
    private javax.swing.JMenuItem Exit_Menu;
    private javax.swing.JButton GameModePVC;
    private javax.swing.JButton GameModePVP;
    private javax.swing.JMenu Help;
    private javax.swing.JLabel Label_Button;
    private javax.swing.JLabel Label_ChosseXorO;
    private javax.swing.JLabel Label_Current_Turn;
    private javax.swing.JLabel Label_GameMode;
    private javax.swing.JLabel Label_Title;
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JButton Newgame;
    private javax.swing.JButton O;
    private javax.swing.JPanel Panel;
    private javax.swing.JMenuItem Reset;
    private javax.swing.JMenuItem Rule;
    private javax.swing.JButton ShowHistory;
    private javax.swing.JButton Stop;
    private javax.swing.JLabel TimeRemain;
    private javax.swing.JButton Undo;
    private javax.swing.JButton X;
    private javax.swing.JMenuItem delay;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
