import javax.swing.JFrame;
import javax.swing.JLabel;
import src.Debugger;
import src.NavaHook;
import src.NavaRuntime;


public class WindowExtension extends NavaHook{  
    private static String hookName;
    private static String[] incompatibleExtensions;
    private static int[] portsUsed = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    public void setupExtension(){
        hookName = "WindowExtension";
        incompatibleExtensions = new String[]{};
        NavaRuntime.addToHookRegister(this);
        NavaRuntime.addToHookLibrary(hookName);
        Debugger.log("Window Extension Setup");
    }

    public void process(){
        Debugger.log("Window Extension Hook Process");
        int[] compilerVariables = NavaRuntime.compilerIntVariables;
        if(compilerVariables[0] == 1){
            int[] windowSize = {compilerVariables[1], compilerVariables[2]};
            Debugger.log(compilerVariables[1] + " " + compilerVariables[2]);
            JFrame frame = new JFrame("Nava Compiler");
            frame.setSize(windowSize[0], windowSize[1]);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            boolean hasLabel = compilerVariables[3] == 1;
            if(hasLabel){
                int[] labelPosition = {compilerVariables[4], compilerVariables[5]};
                int[] labelSize = {compilerVariables[6], compilerVariables[7]};
                JLabel label = new JLabel(convertToText(compilerVariables[8]));
                label.setBounds(labelPosition[0], labelPosition[1], labelSize[0], labelSize[1]);
            }

            compilerVariables[0]++;
        } else if(compilerVariables[0] == 0){
            Debugger.log("Window is inactive");
        } else {
            Debugger.log("Window Extension has been activated");
        }
    }

    private String convertToText(int ascii){
        return Character.toString((char) ascii);
    }

    public String getHookName(){return hookName;}
    public String[] getIncompatibleExtensions(){return incompatibleExtensions;}
    public int[] getPortsUsed(){return portsUsed;}
}
