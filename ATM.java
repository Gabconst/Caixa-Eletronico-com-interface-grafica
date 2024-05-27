public class ATM {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ATMGUI gui = new ATMGUI();
                gui.createAndShowGUI();
            }
        });
    }
}