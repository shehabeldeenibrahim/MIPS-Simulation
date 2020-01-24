public class InstructionMemory {
	private String[] instructions;
	private int pointer;
	
	public InstructionMemory(String ins []) {
		this.instructions =  new String[4096];
		for(int i=0;i<ins.length;i++) {
			this.instructions[i]=ins[i];
		}
		this.pointer = 0;
	}
	public String getAtPC(int PC) {
		return instructions[PC];
	}

}