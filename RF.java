public class RF {
    Register[] registers = new Register[32];
    int ReadReg1;
    int ReadReg2;
    int WriteReg;
    int WriteData;
    int ReadData1;
    int ReadData2;
    int RegWrite;

    public RF() {
        for (int i = 0; i < registers.length; i++)
            registers[i] = new Register(0);
    }

    public Register[] getRegisters() {
        return registers;
    }

    public void setRegisters(Register[] registers) {
        this.registers = registers;
    }

    public int getReadReg1() {
        return ReadReg1;
    }

    public void setReadReg1(int readReg1) {
        ReadReg1 = readReg1;
        ReadData1 = registers[readReg1].getValue();
    }

    public int getReadReg2() {
        return ReadReg2;
    }

    public void setReadReg2(int readReg2) {
        ReadReg2 = readReg2;
        ReadData2 = registers[readReg2].getValue();
    }

    public int getWriteReg() {
        return WriteReg;
    }

    public void setWriteReg(int writeReg) {
        WriteReg = writeReg;
    }

    public int getWriteData() {
        return WriteData;
    }

    public void setWriteData(int writeData) {
        WriteData = writeData;
        if (RegWrite == 1) {
            registers[WriteReg].setValue( WriteData);
        }

    }

    public int getReadData1() {
        return ReadData1;
    }

    public void setReadData1(int readData1) {
        ReadData1 = readData1;
    }

    public int getReadData2() {
        return ReadData2;
    }

    public void setReadData2(int readData2) {
        ReadData2 = readData2;
    }

    public int isRegWrite() {
        return RegWrite;
    }

    public void setRegWrite(int regWrite) {
        RegWrite = regWrite;
    }

    public void printRegFile() {
        System.out.println("##########PRINTING REGISTERS#############");
        for (int i = 0; i < registers.length; i++) {
            System.out.println(i + ":" + registers[i].value);
        }
    }
}
