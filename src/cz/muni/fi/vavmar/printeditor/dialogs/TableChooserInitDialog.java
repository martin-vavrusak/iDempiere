/**
 *
 * @author Martin Vavrusak
 * @mail vavmar@gmail.com
 */

package cz.muni.fi.vavmar.printeditor.dialogs;

import cz.muni.fi.vavmar.printeditor.DAO.DBManager;
import cz.muni.fi.vavmar.printeditor.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TableChooserInitDialog extends JDialog {
    protected static final Logger logger = LogManager.getLogger( TableChooserInitDialog.class );
    
    protected DBManager dataProvider;
    private String chosenTable = null;
    
    public TableChooserInitDialog(DBManager dbManager) {
        dataProvider = dbManager;
        setModal(true);
        
        add(new TableChooserJPanel());
        
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 2, screenSize.height / 2, 450, 325);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    public String getChosenTable(){
        return chosenTable;
    }
    
    private static class PrintFormat {
        private int id;
        private String name;

        public PrintFormat(int id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return id + " " + name;
        }
        
        public static ListModel<PrintFormat> createListModel(final Map<Integer, String> map){
            final List<PrintFormat> printFormats = new ArrayList<PrintFormat>();
            
            for(Entry<Integer, String> pf : map.entrySet()){
                printFormats.add(new PrintFormat(pf.getKey(), pf.getValue()));
            }
            
            return new AbstractListModel<PrintFormat>() {

                @Override
                public int getSize() {
                    return printFormats.size();
                }

                @Override
                public PrintFormat getElementAt(int index) {
                    return printFormats.get(index);
                }
            };
        }
    }
    
private class TableChooserJPanel extends javax.swing.JPanel {
    private List<String> searchList;
    
    /**
     * Creates new form JPanelExtended
     */
    public TableChooserJPanel() {
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        viewRestrictionCheckbox = new javax.swing.JCheckBox();
        availableTablesAndVievsListSrcollPane = new javax.swing.JScrollPane();
        aviableTablesAndVievsList = new javax.swing.JList();
        searchField = new javax.swing.JTextField();
        credentialsPanel = new javax.swing.JPanel();
        roleTitle = new javax.swing.JLabel();
        userTitle = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        roleLabel = new javax.swing.JLabel();
        buttonOk = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        availablePrintFormats = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();

        titleLabel.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.titleLabel.text")); // NOI18N

        viewRestrictionCheckbox.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.viewRestrictionCheckbox.text")); // NOI18N
        viewRestrictionCheckbox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                viewRestrictionCheckboxItemStateChanged(evt);
            }
        });

        List<String> tablesList = dataProvider.getTables(null);
        aviableTablesAndVievsList.setModel(Utils.createListModel( tablesList ));
        aviableTablesAndVievsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        aviableTablesAndVievsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                aviableTablesAndVievsListMouseClicked(evt);
            }
        });
        availableTablesAndVievsListSrcollPane.setViewportView(aviableTablesAndVievsList);

        searchField.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.searchField.text")); // NOI18N
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFieldFocusGained(evt);
            }
        });
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        roleTitle.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.roleTitle.text")); // NOI18N

        userTitle.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.userTitle.text")); // NOI18N

        userLabel.setBackground(new java.awt.Color(255, 255, 255));
        userLabel.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.userLabel.text")); // NOI18N
        userLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        userLabel.setMaximumSize(new java.awt.Dimension(30, 14));
        userLabel.setMinimumSize(new java.awt.Dimension(30, 14));
        userLabel.setOpaque(true);
        userLabel.setPreferredSize(new java.awt.Dimension(30, 14));

        roleLabel.setBackground(new java.awt.Color(255, 255, 255));
        roleLabel.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.roleLabel.text")); // NOI18N
        roleLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        roleLabel.setMaximumSize(new java.awt.Dimension(30, 14));
        roleLabel.setMinimumSize(new java.awt.Dimension(30, 14));
        roleLabel.setOpaque(true);
        roleLabel.setPreferredSize(new java.awt.Dimension(30, 14));

        javax.swing.GroupLayout credentialsPanelLayout = new javax.swing.GroupLayout(credentialsPanel);
        credentialsPanel.setLayout(credentialsPanelLayout);
        credentialsPanelLayout.setHorizontalGroup(
            credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(credentialsPanelLayout.createSequentialGroup()
                .addGroup(credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(userTitle)
                    .addComponent(roleTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(roleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        credentialsPanelLayout.setVerticalGroup(
            credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(credentialsPanelLayout.createSequentialGroup()
                .addGroup(credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userTitle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(credentialsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roleTitle))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        userLabel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.userLabel.AccessibleContext.accessibleName")); // NOI18N

        buttonOk.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.buttonOk.text")); // NOI18N
        buttonOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonOkMouseReleased(evt);
            }
        });

        buttonCancel.setText(org.openide.util.NbBundle.getMessage(TableChooserInitDialog.class, "TableChooserInitDialog.buttonCancel.text")); // NOI18N
        buttonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonCancelMouseClicked(evt);
            }
        });

        availablePrintFormats.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        availablePrintFormats.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(availablePrintFormats);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(viewRestrictionCheckbox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(searchField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(availableTablesAndVievsListSrcollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(buttonOk)
                        .addGap(18, 18, 18)
                        .addComponent(buttonCancel))
                    .addComponent(credentialsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(viewRestrictionCheckbox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(credentialsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(availableTablesAndVievsListSrcollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonOk)
                    .addComponent(buttonCancel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addGap(71, 71, 71))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void viewRestrictionCheckboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_viewRestrictionCheckboxItemStateChanged
//        JCheckBox checkBox = (JCheckBox) evt.getSource();
//        logger.trace("chceckBox: " + checkBox);
//        logger.trace("Is selected: " + checkBox.isSelected());
        logger.trace("Is selected: " + viewRestrictionCheckbox.isSelected());
        if(viewRestrictionCheckbox.isSelected()){
            aviableTablesAndVievsList.setModel(Utils.createListModel( dataProvider.getViews() ));
        } else {
            aviableTablesAndVievsList.setModel(Utils.createListModel( dataProvider.getTables(null) ));
        }
    }//GEN-LAST:event_viewRestrictionCheckboxItemStateChanged

    private void searchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFieldFocusGained
        JTextField jt = (JTextField) evt.getComponent();
        jt.selectAll();
        if(viewRestrictionCheckbox.isSelected()){
            searchList = dataProvider.getViews();
        } else {
            searchList = dataProvider.getTables(null);
        }
        
    }//GEN-LAST:event_searchFieldFocusGained

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyReleased
        String s = ((JTextField) evt.getComponent()).getText();
        System.out.println("Searching for: " + s);
        
        if(searchList != null){
             List<String> list = new ArrayList<String>();
             List<String> listInnerMatch = new ArrayList<String>();
        
            for(String t: searchList){
                if(t.length() < s.length()) break;

                //compare first s.lenght() characters
                if( t.substring(0, s.length()).equalsIgnoreCase(s) ){
                    list.add(t);
                    
                    
                    
                } //If there is a match of "s" (searched string) anywhere as substring
                else if( t.toLowerCase().contains( s.toLowerCase() ) ){ 
                    
                    listInnerMatch.add(t);
                }
            }
            
            list.addAll(listInnerMatch);
            aviableTablesAndVievsList.setModel(Utils.createListModel(list));
        }
    }//GEN-LAST:event_searchFieldKeyReleased

    private void buttonCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCancelMouseClicked
        chosenTable = null;
        dispose();
    }//GEN-LAST:event_buttonCancelMouseClicked

    private void buttonOkMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonOkMouseReleased
        //TODO Vratit instanci reprezentujici
        chosenTable = (String) aviableTablesAndVievsList.getSelectedValue();
        dispose();
    }//GEN-LAST:event_buttonOkMouseReleased

    private void aviableTablesAndVievsListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aviableTablesAndVievsListMouseClicked
        //retrieve model from list wich fired this event
        JList list = (JList) evt.getComponent();

        String s = (String) list.getSelectedValue();
        logger.trace("Selected table: " + s);

        int tableID = dataProvider.getTableID(s);
        Map<Integer, String> printFormats = dataProvider.getPrintFormats(tableID);
                
        availablePrintFormats.setModel( PrintFormat.createListModel(printFormats) );

    }//GEN-LAST:event_aviableTablesAndVievsListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList availablePrintFormats;
    private javax.swing.JScrollPane availableTablesAndVievsListSrcollPane;
    private javax.swing.JList aviableTablesAndVievsList;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonOk;
    private javax.swing.JPanel credentialsPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel roleLabel;
    private javax.swing.JLabel roleTitle;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel userTitle;
    private javax.swing.JCheckBox viewRestrictionCheckbox;
    // End of variables declaration//GEN-END:variables
    }
}