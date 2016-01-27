import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JLayeredPane;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

public class GameCenter extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton board[][] = new JButton[8][8];	//按鈕棋盤格 
	
	 String alpha[] = {" ","A","B","C","D","E","F","G","H"};
	 String num[] = {" ","1","2","3","4","5","6","7","8"};
	
	/**
	 * Create the frame.
	 */
	public GameCenter() {  //GUI
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFont(new Font("微軟正黑體", Font.BOLD, 15));
		panel.add(toolBar);
		
        JButton bt = new JButton("New Game");
        bt.setBackground(UIManager.getColor("Button.background"));
        toolBar.add(bt);
        
        JButton bt1 = new JButton("Load Game");
        bt1.setBackground(UIManager.getColor("Button.background"));
        toolBar.add(bt1);
        
        JButton bt2 = new JButton("Save Game");
        bt2.setBackground(UIManager.getColor("Button.background"));
        toolBar.add(bt2);
        
        JButton bt3 = new JButton("Human pass");
        bt3.setBackground(UIManager.getColor("Button.background"));
        toolBar.add(bt3);
		
		JPanel p1 = new JPanel();
		contentPane.add(p1, BorderLayout.EAST);
		p1.setLayout(new GridLayout(10, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Playing:     ");
		p1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("White win:   ");
		p1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Black win:   ");
		p1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Round:   ");
		p1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("White time:\u3000");
		p1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Black Time:   ");
		p1.add(lblNewLabel_5);

		JPanel p4 = new JPanel();
		contentPane.add(p4, BorderLayout.CENTER);
		GridBagLayout gbl_p4 = new GridBagLayout();
		gbl_p4.columnWidths = new int[]{0};
		gbl_p4.rowHeights = new int[]{0};
		p4.setLayout(gbl_p4);
		
		GridBagConstraints gbcc = new GridBagConstraints();
				
		for (int i = 0; i < 9; i++) 
		{
			JLabel aLabel = new JLabel(alpha[i]);
			gbcc.gridx = i;
			gbcc.gridy = 0;
			gbcc.weightx = 1.0;
			gbcc.weighty = 1.0;
			gbcc.gridwidth = 1;
			gbcc.gridheight = 1;
	        p4.add(aLabel,gbcc);
	    }
		
		for (int i = 0; i < 9; i++) 
		{
			JLabel nLabel = new JLabel(num[i]);
			gbcc.gridx = 0;
			gbcc.gridy = i;
			gbcc.weightx = 1.0;
			gbcc.weighty = 1.0;
			gbcc.gridwidth = 1;
			gbcc.gridheight = 1;
	        p4.add(nLabel,gbcc);
	    }
		
		for (int i=0; i<8; i++)  //將BUTTON加入視窗(利用迴圈)
		{
			
			for(int j=0; j<8; j++)
			{
				board[i][j] = new JButton("");
				GridBagConstraints gbc = new GridBagConstraints();
				gbc.fill = GridBagConstraints.BOTH;
				gbc.anchor = GridBagConstraints.NORTHWEST;
				gbc.gridwidth = 1;
				gbc.gridheight = 1;
				gbc.weightx = 1.0;
				gbc.weighty = 1.0;
				gbc.gridx = i+1;
				gbc.gridy = j+1;
				gbc.insets = new Insets(3,3,3,3);
				board[i][j].setBackground(Color.WHITE);
				p4.add(board[i][j], gbc);
				board[i][j].addActionListener(this); //全部按鈕都要聽，要不然無法聽按鍵
			}
			
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent e)			//按鈕事件    
	{
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameCenter frame = new GameCenter();
					frame.setSize(500, 500);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
