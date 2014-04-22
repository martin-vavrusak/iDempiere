/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.vavmar.printeditor;

import cz.muni.fi.vavmar.printeditor.DAO.DBManager;
import cz.muni.fi.vavmar.printeditor.DAO.DataProviderIdempiere;
import cz.muni.fi.vavmar.printeditor.dialogs.TableChooserInitDialog;
import cz.muni.fi.vavmar.printeditor.tools.ImageTool;
import cz.muni.fi.vavmar.printeditor.tools.TextTool;
import cz.muni.fi.vavmar.printeditor.utils.DataFetcher;
import cz.muni.fi.vavmar.printeditor.utils.Utils;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Martin
 */
public class MainFrame extends javax.swing.JFrame {
    private static final Logger logger = LogManager.getLogger(MainFrame.class);
    
    private MainScene mainScene;
    private DataFetcher dataFetcher = new DataFetcher();    //Simulates connection to database
    private static final int COLUMN_BORDER_TEXT_LENGHT = 10;
    private List<String> tablesList;
    private String selectedTable;
    private int selectedPrintFormatID;
    private DBManager dataProvider;
    
    @Deprecated
    public MainFrame(){
        this(new DataProviderIdempiere(), null, -1);
    }
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame(DBManager dataProvider, String selectedTable, int selectedPrintFormatID) {
    	logger.debug("Starting PrintEditor.");
    	logger.debug("Selected table: " + selectedTable + " selected print format: " + selectedPrintFormatID);
    	
    	this.selectedTable = selectedTable;
    	this.selectedPrintFormatID = selectedPrintFormatID;
        this.dataProvider = dataProvider;
        
        mainScene = new MainScene(dataProvider);
        
        if(selectedPrintFormatID == -1 && selectedTable != null){
        	//Create new print form - Simply set windows and create new MainScene
        	logger.trace("Now we should create new print format. Not implemented yet.");
        	tablesList = new ArrayList<String>();	//set only to selected table
        	tablesList.add(selectedTable);
        	
        } else if (selectedPrintFormatID > 0) {
        	//load print format
        	logger.trace("Now we should load print format. Not implemented yet.");
        	mainScene.loadPrintForm(selectedPrintFormatID);
        	tablesList = new ArrayList<String>();
        	tablesList.add(selectedTable);
        	
        } else {
        	//error otherwise
        	logger.error("Unsupported option. Nether print format nor table was selected!");
        	return;
        }
        
        
        initComponents();
        
        //centering to screen
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle bounds = getBounds();
        setBounds( (screenSize.width - bounds.width)/2,
                    (screenSize.height - bounds.height)/2,
                    bounds.width, bounds.height);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainScenePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        columnsListArea = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablesListArea = new javax.swing.JList();
        tablesSearchField = new javax.swing.JTextField();
        toolPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(org.openide.util.NbBundle.getMessage(MainFrame.class, "MainFrame.title")); // NOI18N

        mainScenePanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mainScenePanel.setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        columnsListArea.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        columnsListArea.setModel(Utils.createListModel(new ArrayList<String>())
        );
        columnsListArea.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        columnsListArea.setTransferHandler( new cz.muni.fi.vavmar.printeditor.DnDColumnListHandler(this) );
        columnsListArea.setDragEnabled(true);
        jScrollPane1.setViewportView(columnsListArea);

        tablesListArea.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MainFrame.class, "MainFrame.tablesListArea.border.title"))); // NOI18N
        tablesListArea.setModel(Utils.createListModel( tablesList ));
        tablesListArea.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablesListArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablesListAreaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablesListArea);

        tablesSearchField.setText(org.openide.util.NbBundle.getMessage(MainFrame.class, "MainFrame.tablesSearchField.text")); // NOI18N
        tablesSearchField.setDragEnabled(true);
        tablesSearchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tablesSearchFieldFocusGained(evt);
            }
        });
        tablesSearchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablesSearchFieldKeyReleased(evt);
            }
        });

        toolPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MainFrame.class, "MainFrame.toolPanel.border.title"))); // NOI18N

        jButton1.setText(org.openide.util.NbBundle.getMessage(MainFrame.class, "MainFrame.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        toolPanel.add(jButton1);

        toolPanel.add (new TextTool("Text", "cz/muni/fi/vavmar/printeditor/images/textTool.png"));
        toolPanel.add (new ImageTool("Image", "cz/muni/fi/vavmar/printeditor/images/imageIcon.png"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(tablesSearchField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(toolPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(toolPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablesSearchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
        );

        mainScenePanel.add(mainScene.getSceneEnvelope());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainScenePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScenePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablesListAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablesListAreaMouseClicked
        logger.trace("Componenta vyvolala klik: " + evt.getComponent());
        
        //retrieve model from list wich fired this event
        JList list = (JList) evt.getComponent();
        ListModel model = list.getModel();
        
        logger.trace("Model: " + model);
        
        //get selected item in list
        int index = list.getSelectedIndex();        
        if(index >= 0){ 
            String s = (String) model.getElementAt( list.getSelectedIndex() );
            logger.trace("Nazev polozky: " + s);

            columnsListArea.setModel( Utils.createListModel( mainScene.getDataProvider().getTable(s).getColumns() ));
            
            if(s.length() > COLUMN_BORDER_TEXT_LENGHT){     //Shortening of border text
                s = s.substring(0, COLUMN_BORDER_TEXT_LENGHT).concat("...");
            }
            ((TitledBorder) columnsListArea.getBorder()).setTitle(s);
        }
    }//GEN-LAST:event_tablesListAreaMouseClicked

    private void tablesSearchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tablesSearchFieldFocusGained
        JTextField jt = (JTextField) evt.getComponent();
        jt.selectAll();
//        if(jt.getText().equals("Hledej...")){
//            jt.setText("");
//        }
    }//GEN-LAST:event_tablesSearchFieldFocusGained

    private void tablesSearchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablesSearchFieldKeyReleased
        String s = ((JTextField) evt.getComponent()).getText();
        System.out.println("Searching for: " + s);
        
        if(tablesList != null){
             List<String> list = new ArrayList<String>();
        
            for(String t: tablesList){
                if(t.length() < s.length()) break;

                if( t.substring(0, s.length()).equalsIgnoreCase(s) ){
                    list.add(t);
                }
            }
            tablesListArea.setModel(Utils.createListModel(list));
        }
    }//GEN-LAST:event_tablesSearchFieldKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

//        for( Widget w : mainScene.getChildren() ){
//            logger.trace(w.getBounds());
//            printChildrens(w.getChildren());
//        }
        String printFormatName = JOptionPane.showInputDialog(this, "Please type name:", "Save print format", JOptionPane.PLAIN_MESSAGE);
        logger.trace("New print format name: '" + printFormatName + "'");
        
        SavePerformer saver = new SavePerformer(dataProvider, mainScene, selectedTable, selectedPrintFormatID);
        saver.saveAsNew(printFormatName);
        
//        mainScene.saveAsNew(tablesList.get(0), printFormatName);    //there should be only one table list is heritage from attempt to make universal reports from more than one table
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void printChildrens (List<Widget> widgets){
        for( Widget w : widgets ){
            logger.trace("| " + w.getBounds());
            printChildrens(w.getChildren());
        }
    }

    public JList getTablesListArea() {
        return tablesListArea;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>

    	runProgram();
        /* Create and display the form */
        
    }

    public static void runProgram(){
    	java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                DataProviderIdempiere dataProvider = new DataProviderIdempiere();
                
                TableChooserInitDialog tableChooser = new TableChooserInitDialog(dataProvider);
                tableChooser.setVisible(true);
                
                String selectedTable = tableChooser.getSelectedTable();
                int selectedPrintFormatID = tableChooser.getSelectedPrintFormatID();
                logger.info( "Selected table: " +  selectedTable);
                logger.info( "Selected print format: " +  selectedPrintFormatID);
                
                //only if something has been selected run program
                if(selectedTable != null || selectedPrintFormatID > 0){
                	//TODO check and create new print format for a table
                	
	                MainFrame mainFrame = new MainFrame(dataProvider, selectedTable, selectedPrintFormatID);
	                mainFrame.setVisible(true);
                }
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList columnsListArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel mainScenePanel;
    private javax.swing.JList tablesListArea;
    private javax.swing.JTextField tablesSearchField;
    private javax.swing.JPanel toolPanel;
    // End of variables declaration//GEN-END:variables
}
