package demo1;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BattleshipGUI
{

    private JFrame frame;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JButton userButton[][] = new JButton[10][10];
    private JButton oppButton[][] = new JButton[10][10];
    private GridStatus[][] b = new GridStatus[10][10];
    private JComboBox shipComboBox;
    private String[] Ships = {"Battleship", "Destroyer", "Cruiser", "Carrier", "Submarine"};
    private int btnRow;
    private int btnCol;
    private int[] arr = new int[2];
    private String btnValue;
    private String shipOrientation = "Horizantal";
    private String SelectedShip = "";
    private String username = "";
    private String msg = "";
    private GameAction gameAction;
    public enum GameAction {HIT, JOIN, EXIT, MINE, OPPONENT}





    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    BattleshipGUI window = new BattleshipGUI();
                    window.frame.setVisible(true);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public BattleshipGUI()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(100, 100, 650, 460);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputUsername();


        JPanel opponentBoard = new JPanel();
        opponentBoard.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        opponentBoard.setLayout(new GridLayout(10,10,1,1));
        for(int row = 0; row < 10; row++)
        {
            for(int col = 0; col < 10; col++)
            {
                oppButton[row][col] = new JButton();
                opponentBoard.add(oppButton[row][col]);
                oppButton[row][col].setName(row + "" + col + "EMPTY");
                oppButton[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JButton btn = (JButton) e.getSource();
                        //System.out.println(btn.getName() + btn.getName().length());
                        //btnRow = Integer.parseInt(btn.getName().substring(0,1));
                        btnRow = Integer.parseInt(btn.getName().substring(0,1));
                        btnCol = Integer.parseInt(btn.getName().substring(1,2));
                        btnValue = btn.getName().substring(2);
                        playerMove(btnRow,btnCol); //playerMove(btnRow, btnCol)
                        setGameAction(GameAction.HIT);


                    }
                });
            }
        }



        JPanel yourBoard = new JPanel();
        yourBoard.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        yourBoard.setLayout(new GridLayout(10,10,1,1));
        for(int row = 0; row < 10; row++)
        {
            for(int col = 0; col < 10; col++)
            {
                userButton[row][col] = new JButton();
                yourBoard.add(userButton[row][col]);
                userButton[row][col].setName((row + "" + col + "EMPTY"));
                userButton[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton btn = (JButton) e.getSource();
                        btnRow = Integer.parseInt(btn.getName().substring(0,1));
                        btnCol = Integer.parseInt(btn.getName().substring(1,2));
                        btnValue = btn.getName().substring(2);
                        System.out.println(btnRow + "" + btnCol + "" + btnValue);
                        shipAttributes(SelectedShip);
                    }
                });
            }
        }


        shipComboBox = new JComboBox(Ships);
        shipComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String ships = (String)cb.getSelectedItem();
                SelectedShip = ships;
            }
        });



        JRadioButton rdbtnHorizantal = new JRadioButton("Horizontal");
        buttonGroup.add(rdbtnHorizantal);
        rdbtnHorizantal.setSelected(true);
        rdbtnHorizantal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipOrientation = "Horizontal";
                System.out.println(shipOrientation);
            }
        });

        JRadioButton rdbtnVertical = new JRadioButton("Vertical");
        buttonGroup.add(rdbtnVertical);
        rdbtnVertical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shipOrientation = "Vertical";
                System.out.println(shipOrientation);
            }
        });

        JButton btnReady = new JButton("READY");
        btnReady.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board(userButton);

                //System.out.println(board(userButton));
                setGameAction(gameAction.JOIN);
                btnDisable(userButton);
                shipComboBox.setEnabled(false);
                rdbtnVertical.setEnabled(false);
                rdbtnHorizantal.setEnabled(false);
                btnReady.setEnabled(false);


            }
        });

        JLabel lblShip = new JLabel("Select Ship");
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(46)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(opponentBoard, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(yourBoard, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
                                                .addGap(71)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(shipComboBox, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                .addComponent(lblShip))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(10)
                                                                .addComponent(btnReady))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(15)
                                                                .addComponent(rdbtnVertical))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(5)
                                                                .addComponent(rdbtnHorizantal)))))
                                .addContainerGap(70, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(16)
                                .addComponent(opponentBoard, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblShip)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(shipComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(28)
                                                .addComponent(rdbtnHorizantal)
                                                .addGap(12)
                                                .addComponent(rdbtnVertical)
                                                .addGap(18)
                                                .addComponent(btnReady))
                                        .addComponent(yourBoard, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(26, Short.MAX_VALUE))
        );
        frame.getContentPane().setLayout(groupLayout);
        frame.setVisible(true);
        frame.pack();


    }

    private GridStatus[][] board (JButton[][] playerBoard) {
        for (int i = 0; i < 10; i++)
        {
            for (int j =0; j < 10; j++)
            {

                String value = playerBoard[i][j].getName().substring(2);
                System.out.println(value);
                if(value.equals("EMPTY"))
                {
                    b[i][j] = GridStatus.EMPTY;
                }
                if(value.equals("Battleship"))
                {
                    b[i][j] = GridStatus.Battleship;
                }
                if(value.equals("Cruiser"))
                {
                    b[i][j] = GridStatus.Cruiser;
                }
                if(value.equals("Carrier"))
                {
                    b[i][j] = GridStatus.Carrier;
                }
                if(value.equals("Submarine")) {
                    b[i][j] = GridStatus.Submarine;
                }

            }
        }
        return b;

    }

    public void shipAttributes(String shipName) {
        int shipSize = 0;
        int endRow;
        int endCol;


        if(shipName == "Battleship") {
            shipSize = 4;
        }
        if (shipName == "Cruiser") {
            shipSize = 3;
        }
        if (shipName == "Carrier") {
            shipSize = 5;
        }
        if (shipName == "Submarine") {
            shipSize = 3;
        }
        if (shipName == "Destroyer") {
            shipSize = 2;
        }
        if(shipOrientation == "Vertical") {
            endRow = getBtnRow() + shipSize;
            for(int i = getBtnRow(); i < endRow ; i++){
                userButton[i][getBtnCol()].setName(getBtnRow() + "" + getBtnCol() + shipName);
                userButton[i][getBtnCol()].setBackground(Color.green);
                shipComboBox.removeItem(shipName);
                userButton[i][getBtnCol()].setEnabled(false);

            }

        } else if (shipOrientation == "Horizantal") {
            endCol = getBtnCol() +shipSize;
            for(int i = getBtnCol(); i < endCol; i++) {
                userButton[getBtnRow()][i].setName(getBtnCol() + "" + getBtnCol() + shipName);
                userButton[getBtnRow()][i].setBackground(Color.green);
                shipComboBox.removeItem(shipName);
                userButton[getBtnRow()][i].setEnabled(false);
            }
        }

    }

    public int[] getArr() {
        return arr;
    }

    public int[] playerMove(int i, int j)
    {
        return getArr();
    }
    public void btnDisable(JButton[][] disableButtons)
    {
        for(int row = 0; row < 10; row++)
        {
            for(int col = 0; col < 10; col++)
            {
                disableButtons[row][col].setEnabled(false);
            }
        }

    }

    //getters
    public GridStatus[][] getB() {
        return b;
    }

    public int getBtnRow() {
        return this.btnRow;
    }

    public int getBtnCol() {
        return  this.btnCol;
    }

    public String getBtnValue() {
        return this.btnValue;
    }
    String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void inputUsername() {
        setUsername(JOptionPane.showInputDialog(null, "Please enter the username:", "Michael"));
    }

    GameAction getGameAction() {
        return gameAction;
    }

    void setGameAction(GameAction gameAction) {
        this.gameAction = gameAction;
    }
    void showMsg(String msg) {
        System.out.println(msg);
    }

    void showMsgln(String msg) {
        System.out.println(msg);
    }

    String getMsg() {
        return msg;
    }

    void setMsg(String msg) {
        this.msg = msg;
    }

}
