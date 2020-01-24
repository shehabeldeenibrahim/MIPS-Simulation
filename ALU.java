public class ALU {
    int result = 0;
    int zFlag = 0;
    public void ALUHandler(int source1, int source2, int ALUSelector, int shmt) {

        // 1. Perform the operation
        switch (ALUSelector) {
        case 0:
            result = source1 & source2;
            break;
        case 1:
            result = source1 | source2;
            break;
        case 2:
            result = source1 + source2;
            break;
        case 6:
            result = source1 - source2;
            break;
        case 7:
            result = (source1 < source2) ? 1 : 0;
            break;
        case 8:
            result = (source2 << shmt);
            break;
        case 9:
            result = (source2 >> shmt );
        }

        // 2. Assign the value of the zero flag
        if (result == 0)
            zFlag = 1;
    }
    public int getZflag() {
        return zFlag;
    }
    public int getALUResult() {
        return result;
    }
}