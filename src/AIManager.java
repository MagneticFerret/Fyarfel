/*
 *  Copyright 2019 Maxwell C. Poarch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;

public class AIManager {
	
	private final ArrayList<Thread> aiThreads;
	private final ArrayList<AIBase> ais;
	private final Grid2D            grid2D;
	
	public AIManager( Grid2D grid2D ) {
		this.aiThreads = new ArrayList<Thread>();
		this.ais       = new ArrayList<AIBase>();
		this.grid2D    = grid2D;
	}
	
	public void startAll() {
		for ( Thread aiThread : aiThreads ) {
			aiThread.start();
		}
	}
	
	public void stopAll() {
		for ( AIBase ai : ais ) {
			ai.stop();
		}
		
		try {
			for ( Thread aiThread : aiThreads ) {
				aiThread.join();
			}
		} catch ( InterruptedException ie ) {
			System.out.println( "AIManager interrupted while stopping managed ais!" );
			ie.printStackTrace();
		}
	}
	
	public ArrayList<AIBase> getAIs() {
		return ais;
	}
	
	public void createNewAlphaI() {
		AIAlphaI alphaAI     = new AIAlphaI( grid2D );
		Thread   alphaThread = new Thread( alphaAI );
		ais.add( alphaAI );
		aiThreads.add( alphaThread );
	}
	
	public void createAndStartNewAlphaI() {
		AIAlphaI alphaAI     = new AIAlphaI( grid2D );
		Thread   alphaThread = new Thread( alphaAI );
		alphaThread.start();
		ais.add( alphaAI );
		aiThreads.add( alphaThread );
	}
	
}
