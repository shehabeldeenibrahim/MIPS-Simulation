public class Stages {
    RF rfile;
    InstructionMemory iMemory;
    DataMemory dMemory;
    int PC;
    String currentInstruction;
    ControlUnit cu;
    ALU ALUUnit;
    ALUControl ALUControlUnit;

    String opCode;
    String readRegister1;
    String readRegister2;
    String decodeMuxInput2;
    String imm;
    String func;
    String shamt;

    int branch;
    int RegDst;
    int MemRead;
    int MemWrite;
    int MemtoReg;
    int ALUSrc;
    int RegWrite;
    int ALUOp;
    boolean isJumpInstruction;
    int ALUSelector;

    String writeRegister;

    int readData1;
    int readData2;

    String signExtended;

    int ALUResult;
    int zFlag;

    int memReadData;

    int rfWriteData;

    public Stages(String[] ins) {
        // fetching stage
        iMemory = new InstructionMemory(ins);
        PC = 0;

        // decoding stage
        cu = new ControlUnit();
        rfile = new RF();

        // execution stage
        ALUControlUnit = new ALUControl();
        ALUUnit = new ALU();

        // mem stage
        dMemory = new DataMemory();

    }

    public boolean fetch() {

        this.currentInstruction = iMemory.getAtPC(PC);
        this.PC++;

        if (this.currentInstruction == null)
            return false;

        return true;
    }

    public void decode() {
        // reverse string and divide
        this.currentInstruction = reverseString(this.currentInstruction);
        opCode = reverseString(this.currentInstruction.substring(26, 32));
        readRegister1 = reverseString(this.currentInstruction.substring(21, 26));
        readRegister2 = reverseString(this.currentInstruction.substring(16, 21));
        decodeMuxInput2 = reverseString(this.currentInstruction.substring(11, 16));
        imm = reverseString(this.currentInstruction.substring(0, 16));
        func = reverseString(this.currentInstruction.substring(0, 6));
        shamt = reverseString(this.currentInstruction.substring(6, 11));

        // reverse again

        this.cu.handleOpCode(opCode, func);

        // control signals
        branch = cu.isBRANCH();
        RegDst = cu.isRegDst();
        MemRead = cu.isMemRead();
        MemtoReg = cu.isMemtoReg();
        MemWrite = cu.isMemWrite();
        ALUSrc = cu.isALUSrc();
        RegWrite = cu.isRegWrite();
        ALUOp = cu.getALUOp();
        isJumpInstruction = cu.isJp();

        // MUX
        writeRegister = MUX(readRegister2, decodeMuxInput2, RegDst);

        // RF
        rfile.setRegWrite(RegWrite);
        rfile.setReadReg1(Integer.parseInt(readRegister1, 2));
        rfile.setReadReg2(Integer.parseInt(readRegister2, 2));
        rfile.setWriteReg(Integer.parseInt(writeRegister, 2));

        readData1 = rfile.getReadData1();
        readData2 = rfile.getReadData2();

        // sign extend
        signExtended = signExtend(reverseString(this.currentInstruction.substring(0, 16)));

    }

    public void execute() {

        // int shiftedImm = shiftLeftTwo(signExtended);
        // int newPC = PC + shiftedImm;
        int newPC;
        try {
            newPC = PC + Integer.parseInt(signExtended, 2);
        } catch (NumberFormatException er) {
            newPC = 0;
        }
        try {
            if (isJumpInstruction) {
                newPC = Integer.parseInt(signExtended, 2);
            }
        } catch (NumberFormatException er) {
            newPC = 0;
        }

        // int newPC = PC + Integer.parseInt(signExtended,2);
        int decimalSignExtendedValue;
        int shamt_int;

        try {
            decimalSignExtendedValue = Integer.parseInt(signExtended, 2);
        } catch (NumberFormatException er) {
            decimalSignExtendedValue = 0;
        }
        try {
            shamt_int = Integer.parseInt(shamt, 2);
        } catch (NumberFormatException er) {
            shamt_int = 0;
        }

        ALUControlUnit.ALUControlHandler(Integer.parseInt(func, 2), ALUOp);
        ALUSelector = ALUControlUnit.getALUSelector();

        int ALUInput2 = intMUX(readData2, decimalSignExtendedValue, ALUSrc);
        ALUUnit.ALUHandler(readData1, ALUInput2, ALUSelector, shamt_int);

        ALUResult = ALUUnit.getALUResult();
        zFlag = ALUUnit.getZflag();

        PC = intMUX(PC, newPC, (branch & zFlag));

    }

    public void memory() {

        dMemory.setAddress(ALUResult);
        dMemory.setWriteToMemory(MemWrite);
        dMemory.setReadFromMemory(MemRead);
        dMemory.setToBeWritten(readData2);
        dMemory.readFromMemory();
        dMemory.writeToMemory();
        memReadData = dMemory.getReturnedData();
    }

    public void writeBack() {
        rfWriteData = intMUX(ALUResult, memReadData, MemtoReg);
        System.out.println("###################Instruction number :" + PC + "########### \n");
        rfile.setWriteData(rfWriteData);
        rfile.printRegFile();
        System.out.println("################### Control Signals #################### \n");
        System.out.println("Branch:" + branch);
        System.out.println("RegDst:" + RegDst);
        System.out.println("MemRead:" + MemRead);
        System.out.println("MemWrite:" + MemWrite);
        System.out.println("MemtoReg:" + MemtoReg);
        System.out.println("ALUSrc:" + ALUSrc);
        System.out.println("RegWrite:" + RegWrite);
        System.out.println("ALUOp:" + ALUOp);
        System.out.println("isJumpInstruction:" + isJumpInstruction);
        if (MemWrite == 1 || MemRead == 1) {
            System.out.println("################### Memory ################### \n");
            dMemory.print(ALUResult);
        }

    }

    // utils
    public String signExtend(String unsignedString) {
        char signBit1 = unsignedString.charAt(0);
        String signBit = Character.toString(signBit1);
        String signedString = unsignedString;
        for (int i = 0; i < 16; i++) {
            signedString = signBit + signedString;
        }
        return signedString;
    }

    public String MUX(String string0, String string1, int controlBit) {
        if (controlBit == 0)
            return string0;
        else
            return string1;
    }

    public String reverseString(String og) {
        return new StringBuilder(og).reverse().toString();
    }

    public int shiftLeftTwo(String str) {
        int shiftedValue;
        try {
            int decimalValue = Integer.parseInt(str, 2);
            shiftedValue = decimalValue << 2;

        } catch (NumberFormatException er) {
            return 0;
        }
        return shiftedValue;
    }

    public int intMUX(int int0, int int1, int controlBit) {
        if (controlBit == 0)
            return int0;
        else
            return int1;
    }
}