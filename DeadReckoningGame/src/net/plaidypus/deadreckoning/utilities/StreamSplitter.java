package net.plaidypus.deadreckoning.utilities;

import java.io.PrintStream;

public class StreamSplitter extends PrintStream{
	
	PrintStream[] outs;
	
	public StreamSplitter(PrintStream[] outs){
		super(outs[0]);
		this.outs = outs;
	}
	
	@Override
	public void write(int data) {
		for(int i=0; i<outs.length; i++){
			outs[i].write(data);
		}
	}

	@Override
	public void print(String s) {
		for(int i=0; i<outs.length; i++){
			outs[i].print(s);
		}
	}
	
	@Override
	public void close(){
		super.close();
		for(int i=0; i<outs.length; i++){
			outs[i].close();
		}
	}
	
	public void newLine(){
		for(int i=0; i<outs.length; i++){
			outs[i].println();
		}
	}
	
	@Override
	public void println(){
		newLine();
	}

}
