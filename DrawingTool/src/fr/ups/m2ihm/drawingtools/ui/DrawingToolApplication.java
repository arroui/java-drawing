/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ups.m2ihm.drawingtools.ui;

import fr.ups.m2ihm.drawingtools.drawingmodel.Command;
import fr.ups.m2ihm.drawingtools.drawingmodel.CommandManager;
import fr.ups.m2ihm.drawingtools.drawingmodel.DrawingModel;
import fr.ups.m2ihm.drawingtools.drawingmodel.DrawingView;
import fr.ups.m2ihm.drawingtools.drawingmodel.MacroCommand;
import fr.ups.m2ihm.drawingtools.drawingmodel.MacroManager;
import fr.ups.m2ihm.drawingtools.drawingmodel.Shape;
import fr.ups.m2ihm.drawingtools.toolsmodel.AbstractTool;
import fr.ups.m2ihm.drawingtools.toolsmodel.DrawingEvent;
import fr.ups.m2ihm.drawingtools.toolsmodel.DrawingToolView;
import fr.ups.m2ihm.drawingtools.toolsmodel.ToolManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JToggleButton;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author David
 */
public class DrawingToolApplication extends javax.swing.JFrame {

    /**
     * The model that holds the drawings to display.
     */
    private final DrawingModel model;
    /**
     * The model of drawing tool used as a controller for the DrawingModel
     * instance.
     */
    private final ToolManager tool;
    /**
     * Default color for shape drawing.
     */
    private static final Color DEFAULT_SHAPE_COLOR = Color.BLACK;
    /**
     * Default color for ghost drawing.
     */
    private static final Color DEFAULT_GHOST_COLOR = Color.RED;
    /**
     * Default stroke for shape drawing.
     */
    private static final Stroke DEFAULT_SHAPE_STROKE
            = new BasicStroke(
                    2.0f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER,
                    10.0f);
    /**
     * Default stroke for ghost drawing.
     */
    private static final Stroke DEFAULT_GHOST_STROKE
            = new BasicStroke(
                    2.0f,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_MITER,
                    10.0f,
                    new float[]{10.0f, 3.0f}, 0.0f);

    private final Map<ToolManager.Tool, JToggleButton> toolSelectors;

    private final MacroManager macroManager;

    /**
     * Creates new form DrawingToolApplication.
     */
    public DrawingToolApplication() {

        macroManager = new MacroManager();
        initComponents();
        model = new DrawingModel();
        model.addView(new DrawingViewImpl());

        tool = new ToolManager(model);
        configureToolManager();

        configureDrawingZone();

        toolSelectors = new HashMap<>(ToolManager.Tool.values().length);
        configureToolSelector();

        configureRangeSlider();

    }

    private void configureToolManager() {
        tool.addDrawingController(DrawingControllerImpl.getInstance());
        DrawingControllerImpl.getInstance().setTool(tool);
        tool.addDrawingToolView(new DrawingToolViewImpl());
    }

    private void configureDrawingZone() {
        drawingZone.addMouseListener(DrawingControllerImpl.getInstance());
        drawingZone.addMouseMotionListener(DrawingControllerImpl.getInstance());
        drawingZone.addKeyListener(DrawingControllerImpl.getInstance());
    }

    private void configureToolSelector() {
        toolSelectors.put(ToolManager.Tool.LINE, tglLine);
        toolSelectors.put(ToolManager.Tool.CIRCLE, tglOval);
        toolSelectors.get(tool.getCurrentToolName()).setSelected(true);
    }

    private void configureRangeSlider() {
        //Configure range slider
        rangeSlider.getModel().setMaxAllowed(0);

        model.getCommandManager().addPropertyChangeListener((e) -> {
            rangeSlider.getModel().setMaxAllowed(model.getCommandManager().getAvailableUndo().size());
        });

        rangeSlider.getModel().addPropertyChangeListener((e) -> {
            if (rangeSlider.getModel().getMaxAllowed() - rangeSlider.getModel().getMinAllowed() > 0) {
                actionsLabel.setText("Actions from "
                        + (int) (rangeSlider.getModel().getMinValue() + 1)
                        + " to "
                        + (int) (rangeSlider.getModel().getMaxValue() + 1));
            } else {
                actionsLabel.setText("Actions from 0 to 0");
            }

        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        drawingZone = new fr.ups.m2ihm.drawingtools.ui.DrawingZone();
        sideGroup = new javax.swing.JPanel();
        sideToolBar = new javax.swing.JPanel();
        tglLine = new javax.swing.JToggleButton();
        tglOval = new javax.swing.JToggleButton();
        actionsLabel = new javax.swing.JLabel();
        rangeSlider = new fr.ups.m2ihm.rangeslider.RangeSlider();
        macroGroup = new javax.swing.JPanel();
        macroScroll = new javax.swing.JScrollPane();
        macroList = new javax.swing.JList<>();
        topMenu = new javax.swing.JMenuBar();
        editMenu = new javax.swing.JMenu();
        Undo = new javax.swing.JMenuItem();
        Redo = new javax.swing.JMenuItem();
        UndoRange = new javax.swing.JMenuItem();
        RegisterMacro = new javax.swing.JMenuItem();
        ApplyMacro = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Most Useless Drawing Tool");

        drawingZone.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout drawingZoneLayout = new javax.swing.GroupLayout(drawingZone);
        drawingZone.setLayout(drawingZoneLayout);
        drawingZoneLayout.setHorizontalGroup(
            drawingZoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
        );
        drawingZoneLayout.setVerticalGroup(
            drawingZoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        sideToolBar.setBorder(javax.swing.BorderFactory.createTitledBorder("Tool selector"));
        sideToolBar.setLayout(new java.awt.GridLayout(0, 1, 0, 20));

        tglLine.setText("Line");
        tglLine.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tglLineStateChanged(evt);
            }
        });
        sideToolBar.add(tglLine);

        tglOval.setText("Oval");
        tglOval.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tglOvalStateChanged(evt);
            }
        });
        tglOval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tglOvalActionPerformed(evt);
            }
        });
        sideToolBar.add(tglOval);

        actionsLabel.setText("Actions from 0 to 0");
        sideToolBar.add(actionsLabel);
        sideToolBar.add(rangeSlider);

        javax.swing.GroupLayout sideGroupLayout = new javax.swing.GroupLayout(sideGroup);
        sideGroup.setLayout(sideGroupLayout);
        sideGroupLayout.setHorizontalGroup(
            sideGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        sideGroupLayout.setVerticalGroup(
            sideGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE)
        );

        macroList.setBorder(javax.swing.BorderFactory.createTitledBorder("Macros"));
        macroList.setModel(macroManager);
        macroScroll.setViewportView(macroList);

        javax.swing.GroupLayout macroGroupLayout = new javax.swing.GroupLayout(macroGroup);
        macroGroup.setLayout(macroGroupLayout);
        macroGroupLayout.setHorizontalGroup(
            macroGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(macroScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        macroGroupLayout.setVerticalGroup(
            macroGroupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(macroScroll, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        editMenu.setText("Edit");

        Undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        Undo.setText("Undo");
        Undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoActionPerformed(evt);
            }
        });
        editMenu.add(Undo);

        Redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        Redo.setText("Redo");
        Redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedoActionPerformed(evt);
            }
        });
        editMenu.add(Redo);

        UndoRange.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        UndoRange.setText("UndoRange");
        UndoRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoRangeActionPerformed(evt);
            }
        });
        editMenu.add(UndoRange);

        RegisterMacro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        RegisterMacro.setText("RegisterMacro");
        RegisterMacro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterMacroActionPerformed(evt);
            }
        });
        editMenu.add(RegisterMacro);

        ApplyMacro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        ApplyMacro.setText("ApplyMacro");
        ApplyMacro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyMacroActionPerformed(evt);
            }
        });
        editMenu.add(ApplyMacro);

        topMenu.add(editMenu);

        setJMenuBar(topMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(sideGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(drawingZone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(macroGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(drawingZone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(macroGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tglLineStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tglLineStateChanged
        tool.selectTool(ToolManager.Tool.LINE);
    }//GEN-LAST:event_tglLineStateChanged

    private void tglOvalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tglOvalStateChanged
        tool.selectTool(ToolManager.Tool.CIRCLE);
    }//GEN-LAST:event_tglOvalStateChanged

    private void tglOvalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tglOvalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tglOvalActionPerformed

    private void UndoRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoRangeActionPerformed
        model.getCommandManager().undoRange((int) rangeSlider.getModel().getMinValue(),
                (int) rangeSlider.getModel().getMaxValue());
    }//GEN-LAST:event_UndoRangeActionPerformed

    private void RedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RedoActionPerformed
        model.getCommandManager().redo();
    }//GEN-LAST:event_RedoActionPerformed

    private void UndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoActionPerformed
        model.getCommandManager().undo();
    }//GEN-LAST:event_UndoActionPerformed

    private void RegisterMacroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterMacroActionPerformed
        List<Command> commands = model
                .getCommandManager()
                .getAvailableUndo()
                .subList(
                        (int) rangeSlider.getModel().getMinValue(),
                        (int) rangeSlider.getModel().getMaxValue());
        macroManager.registerMacro(new MacroCommand("Macro #" + macroManager.getSize(), commands));
    }//GEN-LAST:event_RegisterMacroActionPerformed

    private void ApplyMacroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyMacroActionPerformed
        int index = macroList.getSelectedIndex();
        if (index > -1) {
            Command c = macroManager.getElementAt(index);
            c.execute();
        }

    }//GEN-LAST:event_ApplyMacroActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ApplyMacro;
    private javax.swing.JMenuItem Redo;
    private javax.swing.JMenuItem RegisterMacro;
    private javax.swing.JMenuItem Undo;
    private javax.swing.JMenuItem UndoRange;
    private javax.swing.JLabel actionsLabel;
    private fr.ups.m2ihm.drawingtools.ui.DrawingZone drawingZone;
    private javax.swing.JMenu editMenu;
    private javax.swing.JPanel macroGroup;
    private javax.swing.JList<String> macroList;
    private javax.swing.JScrollPane macroScroll;
    private fr.ups.m2ihm.rangeslider.RangeSlider rangeSlider;
    private javax.swing.JPanel sideGroup;
    private javax.swing.JPanel sideToolBar;
    private javax.swing.JToggleButton tglLine;
    private javax.swing.JToggleButton tglOval;
    private javax.swing.JMenuBar topMenu;
    // End of variables declaration//GEN-END:variables

    /**
     * Utility class that aims at controlling the Tool model. This class is a
     * Singleton (Design Pattern).
     */
    private static final class DrawingControllerImpl
            extends MouseInputAdapter
            implements KeyListener,
            fr.ups.m2ihm.drawingtools.toolsmodel.DrawingController {

        /**
         * Class attribute that holds the singleton instance.
         */
        private static final DrawingControllerImpl DEFAULT_INSTANCE;
        /**
         * Collections that maps event names and their enabling.
         */
        private final Map<DrawingEvent, Boolean> eventEnabling;
        /**
         * The tool controlled by the DrawingController instance.
         */
        private AbstractTool tool;

        static {
            DEFAULT_INSTANCE = new DrawingControllerImpl();
        }

        /**
         * Private constructor for utility class.
         */
        private DrawingControllerImpl() {
            eventEnabling = new HashMap<>(DrawingEvent.values().length);
        }

        /**
         * Get the controlled tool.
         *
         * @return the tool
         */
        public AbstractTool getTool() {
            return tool;
        }

        /**
         * Set the controlled tool.
         * <p>
         * Stores the enabling of each event.
         *
         * @param aTool the tool
         */
        public void setTool(final AbstractTool aTool) {
            this.tool = aTool;
            for (DrawingEvent event : DrawingEvent.values()) {
                eventEnabling.put(event, tool.isEventEnabled(event));
            }
        }

        /**
         * Provides the single instance of this class.
         *
         * @return the Drawing Controller.
         */
        public static DrawingControllerImpl getInstance() {
            return DEFAULT_INSTANCE;
        }

        @Override
        public void mouseReleased(final MouseEvent e) {
            if (tool.isEventEnabled(DrawingEvent.BEGIN_DRAW)) {
                tool.acceptEvent(DrawingEvent.BEGIN_DRAW, e.getPoint());
            } else if (tool.isEventEnabled(DrawingEvent.END_DRAW)) {
                tool.acceptEvent(DrawingEvent.END_DRAW, e.getPoint());
            }
        }

        @Override
        public void mouseMoved(final MouseEvent e) {
            if (tool.isEventEnabled(DrawingEvent.DRAW)) {
                tool.acceptEvent(DrawingEvent.DRAW, e.getPoint());
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            if (Objects.equals(KeyEvent.VK_CANCEL, e)) {
                //BREAK key used.
                if (tool.isEventEnabled(DrawingEvent.CANCEL_DRAW)) {
                    tool.acceptEvent(DrawingEvent.CANCEL_DRAW, null);
                }
            }
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            //Do nothing.
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            //Do nothing.
        }

        @Override
        public void eventEnablingChanged(
                final DrawingEvent event,
                final Boolean theEnabling) {
            this.eventEnabling.put(event, theEnabling);
        }
    }

    /**
     * Default implementation of the interface DrawingView.
     */
    private final class DrawingViewImpl implements DrawingView {

        @Override
        public void modelChanged(final DrawingModel aModel) {
            drawingZone.clearAll();
            aModel.getShapes().forEach((shape) -> {
                drawingZone.addShape(
                        shape,
                        DEFAULT_SHAPE_COLOR,
                        DEFAULT_SHAPE_STROKE);
            });
        }
    }

    /**
     * Default implementation of the interface DrawingToolView.
     */
    private final class DrawingToolViewImpl implements DrawingToolView {

        /**
         * Stores the current ghost to allow its modification or removal.
         */
        private Shape currentGhostShape = null;

        @Override
        public void ghostCreated(final Shape shape) {
            assert (Objects.nonNull(shape));
            if (Objects.nonNull(currentGhostShape)) {
                drawingZone.removeShape(
                        currentGhostShape,
                        DEFAULT_GHOST_COLOR,
                        DEFAULT_GHOST_STROKE);
            }
            currentGhostShape = shape;
            drawingZone.addShape(currentGhostShape,
                    DEFAULT_GHOST_COLOR,
                    DEFAULT_GHOST_STROKE);
        }

        @Override
        public void ghostChanged(final Shape shape) {
            assert (Objects.nonNull(shape));
            if (Objects.nonNull(currentGhostShape)) {
                drawingZone.removeShape(currentGhostShape,
                        DEFAULT_GHOST_COLOR,
                        DEFAULT_GHOST_STROKE);
            }
            currentGhostShape = shape;
            drawingZone.addShape(currentGhostShape,
                    DEFAULT_GHOST_COLOR,
                    DEFAULT_GHOST_STROKE);
        }

        @Override
        public void ghostRemoved(final Shape shape) {
            if (Objects.nonNull(currentGhostShape)) {
                drawingZone.removeShape(currentGhostShape,
                        DEFAULT_GHOST_COLOR,
                        DEFAULT_GHOST_STROKE);
            }
            currentGhostShape = null;
        }
    }
}
