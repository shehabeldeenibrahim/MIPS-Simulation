public class ControlUnit {
    int RegDst = 0;
    int Branch = 0;
    int MemRead = 0;
    int MemToReg = 0;
    int MemWrite = 0;
    int ALUSrc = 0;
    int RegWrite = 0;
    int ALUOp = 0;
    boolean isJumpInstruction = false;

	public void handleOpCode(String opCode, String func) {
		reset();
		int opcode=Integer.parseInt(opCode,2); 
        
        int funct=Integer.parseInt(func,2); 
		switch(opcode)
		{
			case 0:  
				if(funct == 0)
					ALUSrc = 0;
				ALUOp = 1 + (RegDst = RegWrite = 1); break;				// R-format instructions
			case 35: MemRead = ALUSrc = RegWrite = MemToReg = 1; break;			// LW instruction
			case 43: MemWrite = ALUSrc = 1; break;								// SW instruction
			case 4:  Branch = ALUOp = 1; break;									// BEQ instruction
			case 8:  RegWrite = ALUSrc = 1; break;				// ADDI
            case 2:	 Branch = ALUOp = 1; isJumpInstruction = true; 				//J instruction
        }
	}
	public boolean isJp() {
		return isJumpInstruction;
	}
	public void setJp(boolean jp) {
		this.isJumpInstruction = jp;
	}
	public void reset() {
        RegDst = 0;
        Branch = 0;
        MemRead = 0;
        MemToReg = 0;
        MemWrite = 0;
        ALUSrc = 0;
        RegWrite = 0;
        ALUOp = 0;
        isJumpInstruction = false;
	}
	public int isBRANCH() {
		return Branch;
	}
	public void setBRANCH(int bRANCH) {
		Branch = bRANCH;
	}
	public int isRegDst() {
		return RegDst;
	}
	public void setRegDst(int regDst) {
		RegDst = regDst;
	}
	public int isMemRead() {
		return MemRead;
	}
	public void setMemRead(int memRead) {
		MemRead = memRead;
	}
	public int isMemtoReg() {
		return MemToReg;
	}
	public void setMemtoReg(int memtoReg) {
		MemToReg = memtoReg;
	}
	public int isMemWrite() {
		return MemWrite;
	}
	public void setMemWrite(int memWrite) {
		MemWrite = memWrite;
	}
	public int isALUSrc() {
		return ALUSrc;
	}
	public void setALUSrc(int aLUSrc) {
		ALUSrc = aLUSrc;
	}
	public int isRegWrite() {
		return RegWrite;
	}
	public int getALUOp() {
		return ALUOp;
	}
	public void setRegWrite(int regWrite) {
		RegWrite = regWrite;
	}
}