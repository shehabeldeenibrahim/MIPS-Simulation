public class ALUControl {
    int ALUSelector = 0;
    public void ALUControlHandler(int funct, int ALUOp) {
        if (ALUOp == 2) // R-format instruction
        {
            switch (funct) {
            case 32:
                ALUSelector = 2;
                break;
            case 34:
                ALUSelector = 6;
                break;
            case 36:
                ALUSelector = 0;
                break;
            case 37:
                ALUSelector = 1;
                break;
            case 42:
                ALUSelector = 7;
                break;
            case 0:
                ALUSelector = 8;
                break;
            case 2:
                ALUSelector = 9;
                break;
            } 
        } else // I-format instruction
        {
            if (ALUOp == 0)
                ALUSelector = 2;
            else if (ALUOp == 1)
                ALUSelector = 6;
        }
        
    }
    public int getALUSelector() {
        return ALUSelector;
    }
    public void setALUSelector(int selector) {
        ALUSelector = selector;
    }
}