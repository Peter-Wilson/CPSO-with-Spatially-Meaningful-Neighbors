package GUI;

import cpso_h_k.CPSO_H_k;
import cpso_r_k.CPSO_R_k;
import cpso_s.CPSO_S;
import cpso_s_k.CPSO_S_k;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Peter
 */
public class Display extends javax.swing.JPanel {

    /**
     * Creates new form Display
     */
    public Display() {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        btnCPSOS = new javax.swing.JButton();
        btnCPSOSK = new javax.swing.JButton();
        btnCPSOHK = new javax.swing.JButton();
        btnCPSORK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfDimensionSize = new javax.swing.JTextField();
        tfMaxLoops = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfNumParticles = new javax.swing.JTextField();
        tfInertia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfNumSwarms = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tfC1 = new javax.swing.JTextField();
        tfC2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rbDT = new javax.swing.JRadioButton();
        rbNoDT = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taOutput = new javax.swing.JTextArea();
        rbSchaffer = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        rbRastrigin = new javax.swing.JRadioButton();
        rbRosenbrock = new javax.swing.JRadioButton();
        rbGriewanck = new javax.swing.JRadioButton();
        rbAckley = new javax.swing.JRadioButton();
        rbLog = new javax.swing.JRadioButton();

        btnCPSOS.setText("CPSO-S");
        btnCPSOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPSOSActionPerformed(evt);
            }
        });

        btnCPSOSK.setText("CPSO-Sk");
        btnCPSOSK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPSOSKActionPerformed(evt);
            }
        });

        btnCPSOHK.setText("CPSO-Hk");
        btnCPSOHK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPSOHKActionPerformed(evt);
            }
        });

        btnCPSORK.setText("CPSO-Rk");
        btnCPSORK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCPSORKActionPerformed(evt);
            }
        });

        jLabel1.setText("Dimension Size:");

        jLabel2.setText("Max Loops:");

        tfDimensionSize.setText("6");

        tfMaxLoops.setText("10000");

        jLabel3.setText("# of Particles:");

        jLabel4.setText("Inertia:");

        tfNumParticles.setText("20");

        tfInertia.setText("1.0");

        jLabel5.setText("Delaunay Traingulate?");

        tfNumSwarms.setText("6");

        jLabel6.setText("PBest Influence (0-1):");

        jLabel7.setText("GBest Influence (0-1):");

        tfC1.setText("1.49618");

        tfC2.setText("1.49618");

        jLabel8.setText("# of Swarms to divide into:");

        buttonGroup1.add(rbDT);
        rbDT.setSelected(true);
        rbDT.setText("Yes");

        buttonGroup1.add(rbNoDT);
        rbNoDT.setText("No");

        taOutput.setColumns(20);
        taOutput.setRows(5);
        jScrollPane1.setViewportView(taOutput);

        buttonGroup2.add(rbSchaffer);
        rbSchaffer.setText("Schaffer");

        jLabel9.setText("Function:");

        buttonGroup2.add(rbRastrigin);
        rbRastrigin.setText("Rastrigin");

        buttonGroup2.add(rbRosenbrock);
        rbRosenbrock.setText("Rosenbrock");

        buttonGroup2.add(rbGriewanck);
        rbGriewanck.setText("Griewanck");

        buttonGroup2.add(rbAckley);
        rbAckley.setText("Ackley");

        buttonGroup2.add(rbLog);
        rbLog.setSelected(true);
        rbLog.setText("Sum of Log");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(rbLog)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(rbSchaffer)
                        .addGap(18, 18, 18)
                        .addComponent(rbRastrigin)
                        .addGap(18, 18, 18)
                        .addComponent(rbRosenbrock)
                        .addGap(18, 18, 18)
                        .addComponent(rbGriewanck)
                        .addGap(18, 18, 18)
                        .addComponent(rbAckley)
                        .addGap(33, 33, 33))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                    .addComponent(btnCPSOS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnCPSOSK, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                                    .addComponent(tfDimensionSize)
                                    .addComponent(tfMaxLoops)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfInertia)
                                    .addComponent(tfNumParticles))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCPSOHK, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCPSORK, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfC2)
                                    .addComponent(tfC1)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfNumSwarms)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rbDT)
                                        .addGap(18, 18, 18)
                                        .addComponent(rbNoDT)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfDimensionSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tfMaxLoops, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfNumParticles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(tfInertia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tfC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(tfC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(tfNumSwarms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(rbDT)
                            .addComponent(rbNoDT))))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(rbLog))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbRosenbrock)
                        .addComponent(rbRastrigin)
                        .addComponent(rbSchaffer)
                        .addComponent(rbGriewanck)
                        .addComponent(rbAckley)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnCPSORK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCPSOHK, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnCPSOSK, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                        .addComponent(btnCPSOS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCPSOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPSOSActionPerformed
        int dimensionSize = Integer.parseInt(this.tfDimensionSize.getText());
        int maxLoops = Integer.parseInt(this.tfMaxLoops.getText());
        int swarmSize = Integer.parseInt(this.tfNumParticles.getText());
        double Inertia = Double.parseDouble(this.tfInertia.getText());
        double C1 = Double.parseDouble(this.tfC1.getText());
        double C2 = Double.parseDouble(this.tfC2.getText());
        boolean DT = this.rbDT.isSelected();
        //boolean Delaunay = this.rbDT
        CPSO_S cpso = new CPSO_S(dimensionSize, maxLoops, swarmSize, 
                                Inertia, C1, C2, DT, getSelectedFunction(), true, this.taOutput);
        this.taOutput.setText("");
        cpso.start();
    }//GEN-LAST:event_btnCPSOSActionPerformed

    private void btnCPSOSKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPSOSKActionPerformed
        int dimensionSize = Integer.parseInt(this.tfDimensionSize.getText());
        int maxLoops = Integer.parseInt(this.tfMaxLoops.getText());
        int swarmSize = Integer.parseInt(this.tfNumParticles.getText());
        double Inertia = Double.parseDouble(this.tfInertia.getText());
        double C1 = Double.parseDouble(this.tfC1.getText());
        double C2 = Double.parseDouble(this.tfC2.getText());
        int numSwarms = Integer.parseInt(this.tfNumSwarms.getText());
        boolean DT = this.rbDT.isSelected();
        //boolean Delaunay = this.rbDT
        if(Math.ceil((double)dimensionSize/numSwarms) > 3 && DT)
        {
            this.taOutput.append("WARNING: CANNOT PERFORM DT ON SWARMS LARGER THAN 3. CHANGE THE SWARM OR DIMENSION SIZE\n");
            return;
        }
        CPSO_S_k cpso = new CPSO_S_k(dimensionSize, maxLoops, swarmSize, 
                                Inertia, C1, C2, numSwarms, DT, getSelectedFunction(), true, this.taOutput);
        this.taOutput.setText("");
        cpso.start();
    }//GEN-LAST:event_btnCPSOSKActionPerformed

    private void btnCPSOHKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPSOHKActionPerformed
        int dimensionSize = Integer.parseInt(this.tfDimensionSize.getText());
        int maxLoops = Integer.parseInt(this.tfMaxLoops.getText());
        int swarmSize = Integer.parseInt(this.tfNumParticles.getText());
        double Inertia = Double.parseDouble(this.tfInertia.getText());
        double C1 = Double.parseDouble(this.tfC1.getText());
        double C2 = Double.parseDouble(this.tfC2.getText());
        int numSwarms = Integer.parseInt(this.tfNumSwarms.getText());
        boolean DT = this.rbDT.isSelected();
        if(Math.ceil((double)dimensionSize/numSwarms) > 3 && DT)
        {
            this.taOutput.append("WARNING: CANNOT PERFORM DT ON SWARMS LARGER THAN 3. CHANGE THE DIMENSION SIZE OR K VALUE\n");
            return;
        }
        //boolean Delaunay = this.rbDT
        CPSO_H_k cpso = new CPSO_H_k(dimensionSize, maxLoops, swarmSize, 
                                Inertia, C1, C2, numSwarms, DT, getSelectedFunction(), true, this.taOutput);
        this.taOutput.setText("");
        cpso.start();
    }//GEN-LAST:event_btnCPSOHKActionPerformed

    private void btnCPSORKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCPSORKActionPerformed
        int dimensionSize = Integer.parseInt(this.tfDimensionSize.getText());
        int maxLoops = Integer.parseInt(this.tfMaxLoops.getText());
        int swarmSize = Integer.parseInt(this.tfNumParticles.getText());
        double Inertia = Double.parseDouble(this.tfInertia.getText());
        double C1 = Double.parseDouble(this.tfC1.getText());
        double C2 = Double.parseDouble(this.tfC2.getText());
        int numSwarms = Integer.parseInt(this.tfNumSwarms.getText());
        boolean DT = this.rbDT.isSelected();
        if(Math.ceil((double)dimensionSize/numSwarms) > 2 && DT)
        {
            this.taOutput.append("WARNING: CANNOT PERFORM DT ON SWARMS LARGER THAN 3 which is possible with the current number of swarms\n");
            return;
        }
        //boolean Delaunay = this.rbDT
        CPSO_R_k cpso = new CPSO_R_k(dimensionSize, maxLoops, swarmSize, 
                                Inertia, C1, C2, numSwarms, DT, getSelectedFunction(), true, this.taOutput);
        this.taOutput.setText("");
        cpso.start();
    }//GEN-LAST:event_btnCPSORKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCPSOHK;
    private javax.swing.JButton btnCPSORK;
    private javax.swing.JButton btnCPSOS;
    private javax.swing.JButton btnCPSOSK;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JRadioButton rbAckley;
    private javax.swing.JRadioButton rbDT;
    public javax.swing.JRadioButton rbGriewanck;
    public javax.swing.JRadioButton rbLog;
    private javax.swing.JRadioButton rbNoDT;
    public javax.swing.JRadioButton rbRastrigin;
    public javax.swing.JRadioButton rbRosenbrock;
    public javax.swing.JRadioButton rbSchaffer;
    private javax.swing.JTextArea taOutput;
    private javax.swing.JTextField tfC1;
    private javax.swing.JTextField tfC2;
    private javax.swing.JTextField tfDimensionSize;
    private javax.swing.JTextField tfInertia;
    private javax.swing.JTextField tfMaxLoops;
    private javax.swing.JTextField tfNumParticles;
    private javax.swing.JTextField tfNumSwarms;
    // End of variables declaration//GEN-END:variables

    public int getSelectedFunction() {
        if(rbSchaffer.isSelected()) return 1;
        if(rbRastrigin.isSelected()) return 2;
        if(rbRosenbrock.isSelected()) return 3;
        if(rbGriewanck.isSelected()) return 4;
        if(rbAckley.isSelected()) return 5;
        return 0;
    }
}
