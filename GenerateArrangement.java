/**
 * @funcation Generate all the arrangement of a given sequence
 * @author Seleven
 * @date 2016.4.18
 */
public class GenerateArrangement {

	public static void main(String[] args) {
		String[] sequences = {"A","B","C","D","E","F","G"};//
		Element[] es = getAllElements(sequences);
		getAllArrangement(es,sequences);
	}

	/**
	 * get all arrangement
	 * @param es
	 * @param sequences
	 */
	public static void getAllArrangement(Element[] es,String[] sequences) {
		int maxValueIndex = es.length-1;
		int secondMaxValueIndex;
		int i = 1;
		while(true){
			System.out.println(i++ +":\t" + esToString(es,sequences));
			//System.out.println(i++ + ":\t" + esToString(es,true)); //output sequence
			if(!check(es)) break; //terminal condition
			else {
				if(es[maxValueIndex].getDir().equals(DIRECTION.RIGHT)){
					if(maxValueIndex == es.length - 1){
						secondMaxValueIndex = searchSecondMaxIndex(es,maxValueIndex); 
						//System.out.println("——> : " + secondMaxValueIndex);
						swap(es,secondMaxValueIndex);
					}else{
						maxValueIndex = swapr(es,maxValueIndex);
					}
				}else{//es[maxValueIndex].getDir().equals(DIRECTION.LEFT)
					if(maxValueIndex == 0){
						secondMaxValueIndex = searchSecondMaxIndex(es,maxValueIndex);
						//System.out.println("——> : " + secondMaxValueIndex);
						swap(es,secondMaxValueIndex);
					}else{
						maxValueIndex = swapr(es,maxValueIndex);
					}
				} 
			}
		}
	}
	
	/**
	 * @param es
	 * @param secondMaxIndex
	 */
	public static void setAllDirection(Element[] es,int secondMaxIndex){
		for(int i = 0; i < es.length; i++){
			if(es[i].getValue() > es[secondMaxIndex].getValue()) es[i].setInverseDir();
		}
	}
	
	/**
	 * find the maximum value which active index
	 * @param es
	 * @return
	 */
	public static int searchSecondMaxIndex(Element[] es,int maxValueIndex){
		int index = findIndexOfMinValue(es);	
		for(int i = 0; i < es.length; i++){
			if(i == 0){
				if(es[0].getDir().equals(DIRECTION.RIGHT)){
					if((es[0].getValue() > es[1].getValue())){
						if(es[0].getValue() > es[index].getValue() ) index = 0;
					}
				}
			}else if(i == es.length - 1){
				if(es[es.length - 1].getDir().equals(DIRECTION.LEFT)){
					if((es[es.length - 1].getValue() > es[es.length - 2].getValue())){
						if(es[es.length - 1].getValue() > es[index].getValue() ) index = es.length - 1;
					}
				}
			}else{
				if(es[i].getDir().equals(DIRECTION.LEFT) && es[i].getValue() > es[i - 1].getValue()){
					if(es[i].getValue() > es[index].getValue()) index = i;
				}else{
					if(es[i].getDir().equals(DIRECTION.RIGHT) && es[i].getValue() > es[i + 1].getValue()){
						if(es[i].getValue() > es[index].getValue()) index = i;
					}
				}
			}
		}	
		
		return index;
	}
	
	/**
	 * find the index of minimum value
	 * @param es
	 * @return
	 */
	public static int findIndexOfMinValue(Element[] es){
		int minIndex = 0;
		for(int i = 1; i < es.length; i++){
			if(es[i].getValue() < es[minIndex].getValue()) minIndex = i;
		}
		return minIndex;
	}
	
	/**
	 * swap two element
	 * @param es
	 * @param index
	 */
	public static void swap(Element[] es, int index){
		setAllDirection(es,index);
		if(es[index].getDir().equals(DIRECTION.LEFT)){
			es[index].swap(es[index - 1]);
		}else{
			es[index].swap(es[index + 1]);
		}
	}
	
	/**
	 * swap the maximum value and adjacent element
	 * @param es
	 * @param maxValueIndex
	 * @return
	 */
	public static int swapr(Element[] es, int maxValueIndex){
		if(es[maxValueIndex].getDir().equals(DIRECTION.LEFT)){
			es[maxValueIndex].swap(es[maxValueIndex - 1]);
			return maxValueIndex - 1;
		}else{
			es[maxValueIndex].swap(es[maxValueIndex + 1]);
			return maxValueIndex + 1;
		}
	}
	
	/**
	 * check swap continue whether
	 * @param es
	 * @return
	 */
	public static Boolean check(Element[] es){
		if(es.length < 2) return false;
		Boolean flag = false;
		if(es[0].getDir().equals(DIRECTION.RIGHT)){
			if((es[0].getValue() - es[1].getValue()) > 0) flag = true;
		}
		if(es[es.length - 1].getDir().equals(DIRECTION.LEFT)){
			if((es[es.length - 1].getValue() - es[es.length - 2].getValue()) > 0) flag = true;
		}
		for(int i = 1; i < es.length - 1; i++){
			if(es[i].getDir().equals(DIRECTION.LEFT)){
				if(es[i].getValue() - es[i - 1].getValue() > 0)
				flag =  true;
			}else{
				if(es[i].getValue() - es[i + 1].getValue() > 0)
					flag =  true;
			}
		}
		return flag;
	}
	
	/**
	 * output sequence with direction
	 * @param es
	 * @return
	 */
	public static String esToString(Element[] es,Boolean dirFlag){
		StringBuffer sb = new StringBuffer();
		if(dirFlag){
			StringBuffer dirstr = new StringBuffer();
			for(int i = 0; i < es.length; i++){
				sb.append(es[i].getValue() + "  ");
				if(es[i].getDir().equals(DIRECTION.LEFT)){
					dirstr.append("<-  ");
				}else{
					dirstr.append("->  ");
				}
			}
			return dirstr.toString() + "\n\t" + sb.toString();
		}else{
			for(int i = 0; i < es.length; i++){
				sb.append(es[i].getValue() + "  ");
			}
			return sb.toString();
		}
		
	}
	
	/**
	 * @param es
	 * @param sequences
	 * @return
	 */
	public static String esToString(Element[] es,String[] sequences){
		if(es.length != sequences.length) {
			System.out.println("Output ERROR！！！");
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < es.length; i++){
			sb.append(sequences[es[i].getValue()] + "  ");
		}
		return sb.toString();
	}
	
	/**
	 * transform the sequence by Element
	 * @param sequences
	 * @return
	 */
	public static Element[] getAllElements(String[] sequences){
		Element[] es = new Element[sequences.length];
		for(int i = 0; i < sequences.length; i++){
			es[i] = new Element(i,DIRECTION.LEFT);
		}
		return es;
	}
	
	/**
	 * @funcation define element and implements Comparable interface 
	 * @author Seleven
	 * @date 2016年4月19日
	 */
	static class Element implements Comparable<Element> {
		private DIRECTION DIRECTION;
		private int value;
		
		public Element(){}
		public Element(int value,DIRECTION DIRECTION){
			this.value = value;
			this.DIRECTION = DIRECTION;
		}
		
		@Override
		public int compareTo(Element e){
			return this.getValue() - e.getValue();
		}
		
		public int getValue(){
			return this.value;
		}
		
		public void setValue(int value){
			this.value = value;
		}
		
		public DIRECTION getDir(){
			return this.DIRECTION;
		}
		
		public void setDir(DIRECTION DIRECTION){
			this.DIRECTION = DIRECTION;
		}
		
		public void setInverseDir(){
			if(this.getDir().equals(com.interview.GenerateArrangement.DIRECTION.LEFT)) 
				this.setDir(com.interview.GenerateArrangement.DIRECTION.RIGHT);
			else
				this.setDir(com.interview.GenerateArrangement.DIRECTION.LEFT);
		}
		//swap two element
		public void swap(Element e){
			DIRECTION tempDIRECTION = e.getDir();
			int tempValue = e.getValue();
			e.setDir(this.getDir());
			e.setValue(this.value);
			this.setDir(tempDIRECTION);
			this.setValue(tempValue);
		}
		
	}
	
	/**
	 * @funcation direction of element 
	 * @author Seleven
	 * @date 2016.4.19
	 */
	enum DIRECTION{
		LEFT,RIGHT;
	}
}
