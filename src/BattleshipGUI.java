import javafx.scene.control.ComboBox;

import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.border.BevelBorder;

public class BattleshipGUI
{

    private JFrame frame;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JButton userButton[][] = new JButton[10][10];
    private JButton oppButton[][] = new JButton[10][10];
    private JComboBox shipComboBox;
    private String[] Ships = {"Battleship", "Destroyer", "Cruiser", "Carrier", "Submarine"};
    private int btnRow;
    private int btnCol;
    private String btnValue;
    private String shipOrientation = "Horizantal";
    private String SelectedShip = "";




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
        //Create the combo box, select item at index 4.
        //Indices start at 0, so 4 specifies the pig.
        //shipComboBox.setSelectedIndex();

        shipComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                String ships = (String)cb.getSelectedItem();
                //shipAttributes(ships);
                //System.out.println(ships);
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

        JButton btnNewButton = new JButton("Submit");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                                                                .addComponent(btnNewButton))
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
                                                .addComponent(btnNewButton))
                                        .addComponent(yourBoard, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(26, Short.MAX_VALUE))
        );
        frame.getContentPane().setLayout(groupLayout);
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

            if( endRow < 9) {
                for (int i = getBtnRow(); i < endRow; i++) {
                    userButton[i][getBtnCol()].setName(getBtnRow() + "" + getBtnCol() + shipName);
                    System.out.println(getBtnRow() + "" + getBtnCol() + "" + endRow + "" + i);
                    userButton[i][getBtnCol()].setBackground(Color.green);
                    shipComboBox.removeItem(shipName);
                    userButton[i][getBtnCol()].setEnabled(false);

                }
            }
            else {
                System.out.println("Bad");
            }


        } else if (shipOrientation == "Horizantal") {

                endCol = getBtnCol() + shipSize;
            if (endCol < 9) {
                for (int i = getBtnCol(); i < endCol; i++) {
                    userButton[getBtnRow()][i].setName(getBtnCol() + "" + getBtnCol() + shipName);
                    userButton[getBtnRow()][i].setBackground(Color.green);
                    shipComboBox.removeItem(shipName);
                    userButton[getBtnRow()][i].setEnabled(false);
                }
            }
        } else {
            System.out.println("bad!");
        }

    }

    //getters
    public int getBtnRow() {
        return this.btnRow;
    }

    public int getBtnCol() {
        return  this.btnCol;
    }

    public String getBtnValue() {
        return this.btnValue;
    }
}
