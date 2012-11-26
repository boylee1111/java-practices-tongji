package Boy;

public class MenManage {
	public static void main(String[] args) {
		MemFrame backFrame = new MemFrame();
		CtrlFirstFit firstFit = new CtrlFirstFit(backFrame);
		CtrlBestFit bestFit = new CtrlBestFit(backFrame);
		backFrame.initWithAlgo(firstFit, bestFit);
	}
}
