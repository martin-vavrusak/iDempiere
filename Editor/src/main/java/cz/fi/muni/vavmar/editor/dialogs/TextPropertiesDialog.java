/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.fi.muni.vavmar.editor.dialogs;

import cz.fi.muni.vavmar.editor.MainScene;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 * Dialog generated by widget to change its text properities
 * @author Martin
 */
public class TextPropertiesDialog extends JDialog {
    private static final Logger logger = LogManager.getLogger(TextPropertiesDialog.class);
    private Widget ownerWidget; // widget which properities should be affected by this dialog
    private boolean isMultipleSelection;
    private Font oldWidgetFontP;
    private Map<Widget, Font> selectionOldFontsP = new HashMap<Widget, Font>();
    private Set<Widget> selectedWidgetsP;
    
    /**
     * 
     * @param widget Widget whose text parameters souhould be changed
     * @param isMultiple determine if changes should be aplied only on this
     * widget or to all widgets selected in the scene
     */
    public TextPropertiesDialog(Widget widget, boolean isMultiple) {
        super();
        ownerWidget = widget;
        isMultipleSelection = isMultiple;
        selectedWidgetsP = ((MainScene) ownerWidget.getScene()).getSelectedWidgets();
        
        oldWidgetFontP = ownerWidget.getFont();
        if (isMultipleSelection) {
            for(Widget w : selectedWidgetsP){
                selectionOldFontsP.put(w, w.getFont());
            }
        }
        
        
        setLayout(new BorderLayout());
        add(new TextDialogPanel());
        setModal(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() { //nikdy nebude zavirano bez inicializace obsahu proto je mozne proves uz tady a ne az v TextSialogPanelu
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    logger.trace("is closing!");
                                    ownerWidget.setFont(oldWidgetFontP);
                                    if(isMultipleSelection){
                                        for(Widget w : selectedWidgetsP ){
                                            w.setFont( selectionOldFontsP.get(w) );
                                        }
                                    }
                                    logger.trace("And close.");
                                    dispose();
                                }
                            });
        
//        setBounds(new Rectangle(400, 325));     //TODO Smazat!!! Nechat vypocitat dynamicky
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(screenSize.width / 2, screenSize.height / 2, 450, 325);

//        JTextPane textPane = new JTextPane();
//        JTextField textField = new JTextField("--- AaBbCc ---");
//        textField.setHorizontalAlignment(JTextField.CENTER);
//        Font f = new Font(null, WIDTH, WIDTH);


    }



/**
 *
 * @author Martin
 */
private class TextDialogPanel extends javax.swing.JPanel {
//    private JDialog parent;
    /**
     * Creates new form TextPropertiesDialogPanel
     */
    
    private final Logger logger = LogManager.getLogger(TextDialogPanel.class);
    private GraphicsEnvironment graphicEnvironment;
    private Font oldWidgetFont;
    private Map<Widget, Font> selectionOldFonts = new HashMap<Widget, Font>();
    private Set<Widget> selectedWidgets = ((MainScene) ownerWidget.getScene()).getSelectedWidgets();
    
    public TextDialogPanel() {
        logger.trace("New Instance created.");
        oldWidgetFont = ownerWidget.getFont();
        if (isMultipleSelection) {
            for(Widget w : selectedWidgets){
                selectionOldFonts.put(w, w.getFont());
            }
        }
        
//        this.parent = parent;
        initComponents();        
        
        //Initializing list for Font selection
        graphicEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //Get all available fonts and set it to list
        String[] availableFontsName = graphicEnvironment.getAvailableFontFamilyNames();
        logger.trace("Available fonts: " + availableFontsName.length);
        
        DefaultListModel<String> model = new DefaultListModel<String>();
        for(String font: availableFontsName){
            model.addElement(font);
        }
        fontSelectionList.setModel(model);
        
        //Save old font to be able to cancel changes
        
        
        //Set actual font of widget to preview window and all items
        setFromWidget();
        previewPanel.setFont(oldWidgetFont);
        
    }

    private void setFromWidget(){
        previewPanel.setFont(oldWidgetFont);        //Its safe because at every change new font instance is created
                                                    //so old font will stay preserved
        
        fontBoldCheckBox.setSelected( oldWidgetFont.isBold() );
        fontItalicCheckBox.setSelected( oldWidgetFont.isItalic() );
        
        //searching index in font selection list of ownerWidget's font
        int i;
        String oldFontName = oldWidgetFont.getFamily();
        ListModel model = fontSelectionList.getModel();
        for(i = 0; i < model.getSize(); i++){
            String fontName = (String) model.getElementAt(i);
//            logger.trace(i + " " + oldFontName + " ? " + fontName);
            if ( oldFontName.equals(fontName) ) {
                break;      //when found end and set
            }
            //otherwise continue searching
        }
        fontSelectionList.setSelectedIndex(i);      //select font name in list
        fontSelectionList.ensureIndexIsVisible(i);  //scroll to selected item
        
        //set size of font
        Integer fontSize = oldWidgetFont.getSize();
        logger.trace("Original font size: " + fontSize);
        fontSizeComboBox.setSelectedItem(fontSize.toString());
        
//        oldWidgetFont.
////        oldWidgetFont
//        FontRenderContext frc = new FontRenderContext
//        TextLayout tl = new TextLayout("Nejaky text", oldWidgetFont, null);
    }
    
    /**
     * Set or clear Italic style of font. Preserves other style settings (bold)
     * @param f font to be changed
     * @param setItalic true if italic should be set, false if italic should be cleared
     * @return new font with desired value set.
     */
    private Font setItalic(Font f, boolean setItalic){
        
        int fontStyle = f.isBold()? Font.BOLD : Font.PLAIN;    //need to preserve previously set value
        fontStyle |= setItalic ? Font.ITALIC : fontStyle;   
        return f.deriveFont(fontStyle);
    }
    
       /**
     * Set or clear Bold style of font. Preserves other style settings (italic)
     * @param f font to be changed
     * @param setItalic true if bold should be set, false if bold should be cleared
     * @return new font with desired value set.
     */
    private Font setBold(Font f, boolean setBold){
        
        int fontStyle = f.isItalic() ? Font.ITALIC : Font.PLAIN;    //need to preserve previously set value
        fontStyle |= setBold ? Font.BOLD : fontStyle;   
        return f.deriveFont(fontStyle);
    }
    
    private void revertChanges(){
        for(Widget w : selectedWidgets){
            w.setFont( selectionOldFonts.get(w) );
        }
    }
    
    private void setToSelection(Font f){
        for(Widget w : selectedWidgets){
            w.setFont( f );
        }
    }
    //Pokus o vyreseni orezu etxtu kdyz je nastaven Italic
//    private void setPrefferedSizeAdjusted(Widget w){
//        String labelText = ((LabelWidget) w).getLabel();
//        Font setFont = w.getFont();
//        FontRenderContext frc = new FontRenderContext( setFont.getTransform(), true, true );
//        
//        Rectangle2D textBounds = setFont.getStringBounds(labelText, frc);
//        logger.trace("Measured:" + textBounds);
//        
//        w.setPreferredBounds(textBounds.getBounds());
//        logger.trace("Measured string width:" + textBounds.getBounds());
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        javax.swing.JTextPane jTextPane1 = new javax.swing.JTextPane();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        applyDirectlyCeckBox = new javax.swing.JCheckBox();
        fontSizeComboBoxPanel = new javax.swing.JPanel();
        fontSizeComboBox = new javax.swing.JComboBox();
        fontSelectionPanel = new javax.swing.JPanel();
        fontSelectionScrollPane = new javax.swing.JScrollPane();
        fontSelectionList = new javax.swing.JList();
        previewPanel = new javax.swing.JPanel();
        previewTextField = new javax.swing.JTextField();
        fontSettingsPanel = new javax.swing.JPanel();
        fontBoldCheckBox = new javax.swing.JCheckBox();
        fontItalicCheckBox = new javax.swing.JCheckBox();

        jTextPane1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTextPane1.setText(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.jTextPane1.text")); // NOI18N
        jTextPane1.setAutoscrolls(false);
        jTextPane1.setMaximumSize(new java.awt.Dimension(50, 6));
        jTextPane1.setMinimumSize(new java.awt.Dimension(50, 6));
        jTextPane1.setPreferredSize(new java.awt.Dimension(50, 6));
        jScrollPane1.setViewportView(jTextPane1);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setAlignment (attributes, StyleConstants.ALIGN_JUSTIFIED);
        StyledDocument doc = (StyledDocument) jTextPane1.getDocument();
        doc.setParagraphAttributes(0, doc.getLength()-1, attributes, false);

        cancelButton.setText(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.cancelButton.text")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.okButton.text")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        applyDirectlyCeckBox.setSelected(true);
        applyDirectlyCeckBox.setText(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.applyDirectlyCeckBox.text")); // NOI18N
        applyDirectlyCeckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                applyDirectlyCeckBoxItemStateChanged(evt);
            }
        });

        fontSizeComboBoxPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.fontSizeComboBoxPanel.border.title"))); // NOI18N
        fontSizeComboBoxPanel.setLayout(new javax.swing.BoxLayout(fontSizeComboBoxPanel, javax.swing.BoxLayout.LINE_AXIS));

        fontSizeComboBox.setEditable(true);
        fontSizeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "6", "8", "10", "11", "12", "14", "16", "18", "20", "24", "28", "32", "36", "40", "46", "52" }));
        fontSizeComboBox.setSelectedIndex(4);
        fontSizeComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.fontSizeComboBox.toolTipText")); // NOI18N
        fontSizeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fontSizeComboBoxItemStateChanged(evt);
            }
        });
        fontSizeComboBoxPanel.add(fontSizeComboBox);

        fontSelectionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.fontSelectionPanel.border.title"))); // NOI18N
        fontSelectionPanel.setLayout(new javax.swing.BoxLayout(fontSelectionPanel, javax.swing.BoxLayout.LINE_AXIS));

        fontSelectionList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        fontSelectionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        fontSelectionList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fontSelectionListMouseClicked(evt);
            }
        });
        fontSelectionScrollPane.setViewportView(fontSelectionList);

        fontSelectionPanel.add(fontSelectionScrollPane);

        previewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.previewPanel.border.title"))); // NOI18N
        previewPanel.setMaximumSize(new java.awt.Dimension(130, 46));
        previewPanel.setLayout(new javax.swing.BoxLayout(previewPanel, javax.swing.BoxLayout.LINE_AXIS));

        previewTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        previewTextField.setFont(oldWidgetFont);
        previewTextField.setText(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.previewTextField.text")); // NOI18N
        previewTextField.setBorder(null);
        previewTextField.setHorizontalAlignment (JTextField.CENTER);
        previewPanel.add(previewTextField);

        fontSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.fontSettingsPanel.border.title"))); // NOI18N

        fontBoldCheckBox.setText(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.fontBoldCheckBox.text")); // NOI18N
        fontBoldCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fontBoldCheckBoxItemStateChanged(evt);
            }
        });

        fontItalicCheckBox.setText(org.openide.util.NbBundle.getMessage(TextPropertiesDialog.class, "TextPropertiesDialog.fontItalicCheckBox.text")); // NOI18N
        fontItalicCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fontItalicCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout fontSettingsPanelLayout = new javax.swing.GroupLayout(fontSettingsPanel);
        fontSettingsPanel.setLayout(fontSettingsPanelLayout);
        fontSettingsPanelLayout.setHorizontalGroup(
            fontSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fontSettingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fontBoldCheckBox)
                .addGap(10, 10, 10)
                .addComponent(fontItalicCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        fontSettingsPanelLayout.setVerticalGroup(
            fontSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fontSettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(fontBoldCheckBox)
                .addComponent(fontItalicCheckBox))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(fontSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(fontSizeComboBoxPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fontSettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(previewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(applyDirectlyCeckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(previewPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 83, Short.MAX_VALUE)
                        .addComponent(fontSizeComboBoxPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fontSettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fontSelectionPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton)
                    .addComponent(applyDirectlyCeckBox))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed

        if(applyDirectlyCeckBox.getSelectedObjects() != null ) {
            ownerWidget.setFont(oldWidgetFont);     //when we apply changes directly we need to undo all changes
            if(isMultipleSelection){
                revertChanges();
            }
        }
        
        TextPropertiesDialog.this.dispose();  //Close this dialog by calling outerns class method
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void fontSelectionListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fontSelectionListMouseClicked

        //nastaveni pisma previewBoxu
        JList list = (JList) evt.getComponent();
        String fontName = (String) list.getModel().getElementAt(list.getSelectedIndex());
        
        Object o = fontSizeComboBox.getModel().getSelectedItem();
        logger.trace("Polozka vybraná v Size Combo Boxu: " + o);
        
        //Get size of font
        int fontHeigth = 10;
        if( o != null){
            String value = (String ) o;
            fontHeigth = Integer.parseInt( value );
        } else {
            logger.warn("Item in combo boc setting font size is null!!!");
        }
        
        //Get font style Bold, Italic
                              //returns label of checkbox if selected or null if not selected
        int fontModifiers = (fontBoldCheckBox.getSelectedObjects() != null) ? Font.BOLD : Font.PLAIN ;  
        fontModifiers |= (fontItalicCheckBox.getSelectedObjects() != null) ? Font.ITALIC : Font.PLAIN ;
        
        //create new font and set it
        Font f = new Font(fontName, fontModifiers, fontHeigth);
        logger.trace("Nastavuju font: " + f);
        
        previewTextField.setFont(f);
        if(applyDirectlyCeckBox.getSelectedObjects() != null){
            ownerWidget.setFont(f);
            if(isMultipleSelection) { setToSelection(f); }
            
            ownerWidget.getScene().validate();
        }
    }//GEN-LAST:event_fontSelectionListMouseClicked

    private void fontSizeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fontSizeComboBoxItemStateChanged
        logger.trace("Item:" + evt.getItem());
        String value = (String) evt.getItem();
        
        Font oldFont = previewTextField.getFont();
        Font newFont = oldFont.deriveFont(Float.parseFloat(value));
        previewTextField.setFont( newFont );
        
        if(applyDirectlyCeckBox.getSelectedObjects() != null){
            
            ownerWidget.setFont(newFont);
            if(isMultipleSelection) { setToSelection(newFont); }
            
            ownerWidget.getScene().validate();
        }
    }//GEN-LAST:event_fontSizeComboBoxItemStateChanged

    private void fontBoldCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fontBoldCheckBoxItemStateChanged
        
        Font newFont;
        if( evt.getStateChange() == ItemEvent.SELECTED ){
            newFont = setBold(previewTextField.getFont(), true);
            previewTextField.setFont(newFont);
            
        } else if ( evt.getStateChange() == ItemEvent.DESELECTED ){
            newFont = setBold(previewTextField.getFont(), false);
            previewTextField.setFont(newFont);
            
        } else {
            newFont = previewTextField.getFont();
            logger.warn("Something really weird happend when checkbox setting bold properties of text has been changed!!!");
        }
        
       if(applyDirectlyCeckBox.getSelectedObjects() != null){
            ownerWidget.setFont(newFont);
            if(isMultipleSelection) { setToSelection(newFont); }
            
            ownerWidget.getScene().validate();
        }
    }//GEN-LAST:event_fontBoldCheckBoxItemStateChanged

    private void fontItalicCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fontItalicCheckBoxItemStateChanged
         
        Font newFont;
        if( evt.getStateChange() == ItemEvent.SELECTED ){
            newFont = setItalic(previewTextField.getFont(), true);
            previewTextField.setFont(newFont);
            
        } else if ( evt.getStateChange() == ItemEvent.DESELECTED ){
            newFont = setItalic(previewTextField.getFont(), false);
            previewTextField.setFont(newFont);
            
        } else {
            newFont = previewTextField.getFont();
            logger.warn("Something really weird happend when checkbox setting bold properties of text has been changed!!!");
        }
         
        if(applyDirectlyCeckBox.getSelectedObjects() != null){
            ownerWidget.setFont(newFont);
            if(isMultipleSelection) { setToSelection(newFont); }
            
            ownerWidget.getScene().validate();
        }
    }//GEN-LAST:event_fontItalicCheckBoxItemStateChanged

    private void applyDirectlyCeckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_applyDirectlyCeckBoxItemStateChanged
        
        if( evt.getStateChange() == ItemEvent.SELECTED ){
            ownerWidget.setFont( previewTextField.getFont() );
            if(isMultipleSelection) { setToSelection(previewTextField.getFont()); } 
            
            ownerWidget.revalidate();
            ownerWidget.getScene().validate();
            
        } else if ( evt.getStateChange() == ItemEvent.DESELECTED ){
            ownerWidget.setFont(oldWidgetFont);
            if(isMultipleSelection) { revertChanges(); }
            
            ownerWidget.revalidate();
            ownerWidget.getScene().validate();
            
        } else {
            logger.warn("Something really weird happend when setting direct modification to widget!!!");
        }
    }//GEN-LAST:event_applyDirectlyCeckBoxItemStateChanged

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        logger.trace("Ok button action performed");
//        logger.trace("Bounds: " + ownerWidget.getBounds() + " Preffered bounds: " + ownerWidget.getPreferredBounds());
        Font f = previewTextField.getFont();
        ownerWidget.setFont( f );      //Ordering is important!!!!
//        setPrefferedSizeAdjusted(ownerWidget);                  //Adjust bounds to new text
        if(isMultipleSelection) { setToSelection( f ); }
        
        logger.trace("After font set: Bounds: " + ownerWidget.getBounds() + " Preffered bounds: " + ownerWidget.getPreferredBounds());
//        DebugGraphics dg = new DebugGraphics();
//        dg.create();
//        FontMetrics metric = dg.getFontMetrics(ownerWidget.getFont());
//        int width = metric.stringWidth( ((LabelWidget) ownerWidget).getLabel() );
//        logger.trace("width by metrics:" + width);
        
//        String labelText = ((LabelWidget) ownerWidget).getLabel();
//        Font setFont = previewTextField.getFont();
//        FontRenderContext frc = new FontRenderContext(setFont.getTransform(), true, true);
//        
//        Rectangle2D textBounds = setFont.getStringBounds(labelText, frc);
//        logger.trace("Measured string width:" + textBounds);
        
//        FontMetrics fm = previewTextField.getFontMetrics(previewTextField.getFont());
//        int width = fm.stringWidth( ((LabelWidget) ownerWidget).getLabel() );
//        logger.trace("Width by FontMetrics:" + width);
        
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox applyDirectlyCeckBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox fontBoldCheckBox;
    private javax.swing.JCheckBox fontItalicCheckBox;
    private javax.swing.JList fontSelectionList;
    private javax.swing.JPanel fontSelectionPanel;
    private javax.swing.JScrollPane fontSelectionScrollPane;
    private javax.swing.JPanel fontSettingsPanel;
    private javax.swing.JComboBox fontSizeComboBox;
    private javax.swing.JPanel fontSizeComboBoxPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel previewPanel;
    private javax.swing.JTextField previewTextField;
    // End of variables declaration//GEN-END:variables

//    public static void main(String args[]) {
////        /* Set the Nimbus look and feel */
////        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
////        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
////         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
////         */
////        try {
////            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
////                if ("Nimbus".equals(info.getName())) {
////                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
////                    break;
////                }
////            }
////        } catch (ClassNotFoundException ex) {
////            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (InstantiationException ex) {
////            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (IllegalAccessException ex) {
////            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
////            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
////        }
////        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                JFrame frame = new JFrame("TextDialogPanel");
//                frame.setLayout(new BorderLayout());
//                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                frame.add(new TextDialogPanel(null));
//                frame.setVisible(true);
//            }
//        });
//    }
    }
//End of TextDialogPanel class


}