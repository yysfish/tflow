package tflow.com.yzs.flow.common;

import java.io.PrintStream;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class TflowBanner implements Banner{

	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
		out.println("===============");
		out.println();
		out.println("  欢迎使用流量平台");
		out.println();
		out.println("===============");
		
	}

}
