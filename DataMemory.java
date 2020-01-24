public class DataMemory {
	private int [] data = new int[4096];
	private int address;
	private int writeToMemory;
	private int readFromMemory;
	private int toBeWritten;
	private int returnedData;
	public DataMemory () {
		data[1] = 1;
		data[2] = 50;
		data[3] = 3;
	}
	
	public int[] getData() {
		return data;
	}
	public void setData(int[] data) {
		this.data = data;
	}
	public int getAddress() {
		return address;
	}
	public void setAddress(int address) {
		this.address = address;
	}
	public int isWriteToMemory() {
		return writeToMemory;
	}
	public void setWriteToMemory(int writeToMemory) {
		this.writeToMemory = writeToMemory;
	}
	public int isReadFromMemory() {
		return readFromMemory;
	}
	public void setReadFromMemory(int readFromMemory) {
		this.readFromMemory = readFromMemory;
	}
	public int getToBeWritten() {
		return toBeWritten;
	}
	public void setToBeWritten(int toBeWritten) {
		this.toBeWritten = toBeWritten;
	}
	public int getReturnedData() {
		return returnedData;
	}
	public void setReturnedData(int returnedData) {
		this.returnedData = returnedData;
	}
	public int readFromMemory() {
		if(isReadFromMemory() == 1) {
		returnedData=data[address];}
		return returnedData;
				}
	public void writeToMemory() {
	if(isWriteToMemory()==1) {
		data[address]=toBeWritten;
	}
	
	}
	public void print(int address){
		System.out.println("PRINTING DATA MEMORY");
		System.out.println("Address: " + address + "data: " + data[address]);
	}
}