public class FinallyClass {
	public static void main(String[] args) {
		try{
			System.out.print("1");
			return;
		}catch(RuntimeException re){
			System.out.print("2");
		}finally{
			System.out.print("3");
		}
		System.out.print("4");
	}
}