package common.data;


public class car {
	private Boolean cool ;
	public car(Boolean cool) {
		this.cool = cool;
	}
	public boolean getCool() {
		return cool;
	}
	@Override
    public String toString() {
		if (cool == true) {
			return (" has a cool car");
		}
		else {
			return (" doesn't have a cool car");
		}   
    }
    

}
