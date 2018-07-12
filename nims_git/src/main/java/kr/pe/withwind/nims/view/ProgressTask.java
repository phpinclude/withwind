package kr.pe.withwind.nims.view;

import javafx.concurrent.Task;

public class ProgressTask<V> extends Task<V> {
	
	private ProgressTaskCallInf pcInf;
	private String nodeId; 
	
	public ProgressTask(ProgressTaskCallInf pcInf) {
		this.pcInf = pcInf;
	}
	
	public ProgressTask(ProgressTaskCallInf pcInf, String nodeId) {
		this.pcInf = pcInf;
		this.nodeId = nodeId;
	}

	@Override
	protected V call() throws Exception {
		return (V) pcInf.doProgress(this, nodeId);
	}
	
	public void progressUpdate(long cnt, long totCnt){
		updateProgress(cnt, totCnt);
	}
	
	public interface ProgressTaskCallInf<V>{
		public V doProgress(ProgressTask progressTask, String nodeId);
	}
}
